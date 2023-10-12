package me.austin.impl.hack

import com.mojang.brigadier.CommandDispatcher
import me.austin.VulcanMod
import me.austin.api.Manager
import me.austin.api.hack.AbstractHack
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource
import net.minecraft.command.CommandRegistryAccess
import java.nio.file.Files
import java.nio.file.Path

object HackManager : Manager<AbstractHack, List<AbstractHack>> {
    internal val directory: Path = Path.of("${VulcanMod.configFile}\\hacks")

    override val values = listOf(AntiFabric, AntiKick, CrystalAura)

    fun getEnabled(): List<AbstractHack> {
        return this.values.filter { it.isEnabled }
    }

    override fun load() {
        if (Files.notExists(directory)) {
            Files.createDirectory(directory)
            return
        }

        for (hack in values) {
            hack.load()

            ClientCommandRegistrationCallback.EVENT.register { commandDispatcher: CommandDispatcher<FabricClientCommandSource>, _ ->
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
