package me.austin.api.hack

import com.mojang.brigadier.Command.SINGLE_SUCCESS
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.StringArgumentType.getString
import com.mojang.brigadier.arguments.StringArgumentType.word
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.builder.LiteralArgumentBuilder.literal
import com.mojang.brigadier.builder.RequiredArgumentBuilder.argument
import com.mojang.brigadier.exceptions.BuiltInExceptions
import kotlinx.serialization.json.*
import me.austin.api.Description
import me.austin.api.Name
import me.austin.api.Vulcan
import me.austin.api.Vulcan.Companion.LOGGER
import me.austin.api.Wrapper
import me.austin.api.setting.NumberSetting
import me.austin.impl.command.argument.getSetting
import me.austin.impl.command.argument.setting
import me.austin.impl.events.KeyEvent
import me.austin.impl.hack.HackManager
import me.austin.impl.setting.*
import me.austin.rush.Listener
import me.austin.rush.listener
import me.austin.util.*
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource
import java.nio.file.Files
import java.nio.file.Path

abstract class AbstractHack(final override val name: String, final override val description: String, key: Int = -1) :
    Name, Description, Wrapper {
    private val path: Path = Path.of("${HackManager.directory}/${this.name}.json")

    private val keyBind: IntSetting = IntSettingBuilder("KeyBind").default(key).build()

    private val keyListener = listener<KeyEvent> {
        if (it.key == this.keyBind.value) {
            this.toggle()
            it.cancel()
        }
    }

    // This is the list of settings for the hack
    // if a setting isn't contained here then the client won't be able to find it
    open val settings = Settings(this.keyBind)

    // These will be registered every time this hack is enabled
    open val listeners = listOf<Listener>(keyListener)

    var isEnabled = false
        private set

    private fun enable() {
        if (!this.isEnabled) {
            Vulcan.EVENT_MANAGER.subscribeAll(this.listeners)

            this.onEnable()

            this.isEnabled = true
        }
    }

    protected fun disable() {
        if (this.isEnabled) {
            Vulcan.EVENT_MANAGER.unsubscribeAll(this.listeners)

            this.onDisable()

            this.isEnabled = false
        }
    }

    fun toggle() {
        if (this.isEnabled) {
            this.disable()
        } else {
            this.enable()
        }
    }

    open fun onEnable() {}

    open fun onDisable() {}

    fun load(path: Path = this.path) {
        if (!Files.exists(path)) {
            Files.createFile(path)
            this.save()
            return
        }

        if (path.fromJson().isEmpty()) {
            this.save()
            return
        }

        try {
            val json = path.fromJson(true)

            this.isEnabled = json["enabled"]!!.jsonPrimitive.boolean

            for (setting in this.settings.allSettings()) {
                when (setting) {
                    is BooleanSetting -> setting.set(json[setting.name]!!.jsonPrimitive.boolean)
                    is DoubleSetting -> setting.set(json[setting.name]!!.jsonPrimitive.double)
                    is FloatSetting -> setting.set(json[setting.name]!!.jsonPrimitive.float)
                    is IntSetting -> setting.set(json[setting.name]!!.jsonPrimitive.int)
                    is LongSetting -> setting.set(json[setting.name]!!.jsonPrimitive.long)
                    is EnumSetting -> setting.set(json[setting.name]!!.jsonPrimitive.toString())
                }
            }
            if (this.isEnabled) {
                Vulcan.EVENT_MANAGER.subscribeAll(this.listeners)
            }
        } catch (e: Exception) {
            path.clearJson()

            LOGGER.error("${this.name} failed to load settings from file")

            e.printStackTrace()
        }
    }

    private fun save(path: Path = this.path) {
        try {
            val map = this.settings.allSettings().associateTo(mutableMapOf()) {
                it.name to when (it) {
                    is BooleanSetting -> JsonPrimitive(it.value)
                    is NumberSetting<*> -> JsonPrimitive(it.value)
                    else -> JsonPrimitive(it.value.toString())
                }
            }

            map["enabled"] = JsonPrimitive(isEnabled)

            path.writeToJson(JsonObject(map))
        } catch (e: Exception) {
            LOGGER.error("${this.name} failed to save")

            e.printStackTrace()
        }
    }

    fun unload(path: Path = this.path) {
        if (this.isEnabled) {
            Vulcan.EVENT_MANAGER.unsubscribeAll(this.listeners)
        }

        this.save(path)
    }

    fun register(dispatcher: CommandDispatcher<FabricClientCommandSource>) {
        dispatcher.register(this.build(
            literal<FabricClientCommandSource>(this.name).then(
                argument("setting", setting(this))
            ).executes {
                this.clientSend(
                    "§a${this.name} is set to ${
                        getSetting(
                            it, "setting", this
                        )?.value
                    }"
                )

                SINGLE_SUCCESS
            }.then(argument("value", word()))
        ).executes {
            val input = getString(it, "value")
            val setting =
                getSetting(it, "setting", this) ?: throw BuiltInExceptions().dispatcherUnknownArgument().create()

            try {
                when (setting) {
                    is BooleanSetting -> setting.set(input.toBoolean())
                    is DoubleSetting -> setting.set(input.toDouble())
                    is FloatSetting -> setting.set(input.toFloat())
                    is IntSetting -> setting.set(input.toInt())
                    is LongSetting -> setting.set(input.toLong())
                    is EnumSetting<*> -> if (!setting.set(input)) {
                        throw BuiltInExceptions().dispatcherUnknownArgument().create()
                    }

                    else -> {
                        this.error("You cannot set that setting like this")
                        throw BuiltInExceptions().dispatcherUnknownArgument().create()
                    }
                }

                this.clientSend("§a${setting.name} set to $input")
            } catch (e: Exception) {
                throw BuiltInExceptions().dispatcherUnknownArgument().create()
            }

            SINGLE_SUCCESS
        })
    }

    open fun build(builder: LiteralArgumentBuilder<FabricClientCommandSource>): LiteralArgumentBuilder<FabricClientCommandSource> {
        return builder
    }
}
