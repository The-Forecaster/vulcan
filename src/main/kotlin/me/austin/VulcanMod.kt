package me.austin

import me.austin.api.Vulcan
import me.austin.impl.command.CommandManager
import me.austin.impl.gui.ClickGuiScreen
import me.austin.impl.hack.HackManager
import net.fabricmc.api.ModInitializer
import net.minecraft.client.MinecraftClient
import java.nio.file.Files
import java.nio.file.Path

object VulcanMod : Vulcan(), ModInitializer {
    private const val CLIENT_FOLDER = "vulcan"
    private const val DEFAULT_CONFIG = "Vulcan_config.json"
    val configFile: Path = Path.of("${MinecraftClient.getInstance().runDirectory}\\${CLIENT_FOLDER}\\${DEFAULT_CONFIG}")

    var start = System.currentTimeMillis()

    override fun onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.

        if (!Files.exists(configFile)) {
            Files.createDirectory(configFile.parent)
            Files.createFile(configFile)
        }

        CommandManager.load()
        HackManager.load()

        EVENT_MANAGER.subscribe(ClickGuiScreen)

        start = System.currentTimeMillis()
    }
}