package me.austin.impl.command.argument

import com.mojang.brigadier.StringReader
import com.mojang.brigadier.arguments.ArgumentType
import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.exceptions.BuiltInExceptions
import com.mojang.brigadier.exceptions.CommandSyntaxException
import com.mojang.brigadier.suggestion.Suggestions
import com.mojang.brigadier.suggestion.SuggestionsBuilder
import me.austin.api.hack.AbstractHack
import me.austin.api.hack.HackManager
import me.austin.api.setting.Setting
import net.minecraft.command.CommandSource
import java.util.concurrent.CompletableFuture

class HackArgumentType internal constructor() : ArgumentType<AbstractHack> {
    @Throws(CommandSyntaxException::class)
    override fun parse(reader: StringReader): AbstractHack =
        HackManager.values.find { reader.readString().lowercase() == it.name.lowercase() }
            ?: throw BuiltInExceptions().dispatcherUnknownArgument().create()

    override fun <S : Any> listSuggestions(
        context: CommandContext<S>?, builder: SuggestionsBuilder?
    ): CompletableFuture<Suggestions> =
        CommandSource.suggestMatching(HackManager.values.map(AbstractHack::name), builder)
}

fun hack() = HackArgumentType()

fun getHack(context: CommandContext<*>, name: String): AbstractHack =
    context.getArgument(name, AbstractHack::class.java)

class SettingArgumentType internal constructor(private val hack: AbstractHack) : ArgumentType<Setting<*>> {
    @Throws(CommandSyntaxException::class)
    override fun parse(reader: StringReader): Setting<*> =
        hack.settings.get(reader.readString()) ?: throw BuiltInExceptions().dispatcherUnknownArgument().create()

    override fun <S : Any> listSuggestions(
        context: CommandContext<S>, builder: SuggestionsBuilder?
    ): CompletableFuture<Suggestions> =
        CommandSource.suggestMatching(this.hack.settings.allSettings().map(Setting<*>::name), builder)
}

fun setting(hack: AbstractHack) = SettingArgumentType(hack)

fun getSetting(context: CommandContext<*>, name: String, hack: AbstractHack = getHack(context, "hack")): Setting<*>? =
    hack.settings.get(name)