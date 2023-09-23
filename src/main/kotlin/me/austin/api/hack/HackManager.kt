package me.austin.api.hack

import com.mojang.brigadier.CommandDispatcher
import me.austin.VulcanClient
import me.austin.api.Manager
import me.austin.impl.hack.AntiFabric
import me.austin.impl.hack.AntiKick
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource
import net.minecraft.command.CommandRegistryAccess
import java.nio.file.Files
import java.nio.file.Path

object HackManager : Manager<AbstractHack, List<AbstractHack>> {
    internal val directory: Path = Path.of("${VulcanClient.configFile}/hacks")

    override val values = listOf(AntiFabric, AntiKick)

    override fun load() {
        if (Files.notExists(directory)) {
            Files.createDirectory(directory)
        }

        for (hack in values) {
            hack.load()

            ClientCommandRegistrationCallback.EVENT.register { commandDispatcher: CommandDispatcher<FabricClientCommandSource>, _: CommandRegistryAccess ->
                hack.register(commandDispatcher)
            }
        }
    }

    override fun unload() {
        for (hack in values) {
            hack.unload()
        }
    }
}
