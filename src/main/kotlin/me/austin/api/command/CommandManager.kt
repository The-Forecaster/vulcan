package me.austin.api.command

import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.exceptions.CommandSyntaxException
import me.austin.api.Manager
import me.austin.api.Wrapper
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource
import net.minecraft.client.MinecraftClient
import net.minecraft.client.network.ClientCommandSource
import net.minecraft.command.CommandRegistryAccess

object CommandManager : Manager<AbstractCommand, List<AbstractCommand>>, Wrapper {
    override val values = listOf<AbstractCommand>()
    private val dispatcher = CommandDispatcher<ClientCommandSource>()

    private val commandSource = ChatCommandSource(minecraft)

    var prefix = '.'

    override fun load() {
        ClientCommandRegistrationCallback.EVENT.register { commandDispatcher: CommandDispatcher<FabricClientCommandSource>, _: CommandRegistryAccess ->
            for (command in values) {
                command.register(commandDispatcher)
            }
        }
    }

    @JvmOverloads
    @Throws(CommandSyntaxException::class)
    fun dispatch(message: String, source: ClientCommandSource = commandSource) {
        dispatcher.execute(dispatcher.parse(message, source))
    }


    override fun unload() {}

    class ChatCommandSource(mc: MinecraftClient) : ClientCommandSource(mc.networkHandler, mc)
}