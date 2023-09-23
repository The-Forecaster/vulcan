package me.austin

import me.austin.api.Vulcan
import me.austin.impl.gui.ClickGuiScreen
import net.fabricmc.api.ClientModInitializer
import net.minecraft.client.MinecraftClient
import java.nio.file.Files
import java.nio.file.Path

object VulcanClient : Vulcan(), ClientModInitializer {
    private const val CLIENT_FOLDER = "vulcan"
    private const val DEFAULT_CONFIG = "Vulcan_config.json"
    val configFile: Path = Path.of("${MinecraftClient.getInstance().runDirectory}\\${CLIENT_FOLDER}\\${DEFAULT_CONFIG}")

    override fun onInitializeClient() {
        if (!Files.exists(configFile)) {
            Files.createDirectory(configFile.parent)
            Files.createFile(configFile)
        }

        EVENT_MANAGER.subscribe(ClickGuiScreen)

        LOGGER.info("Loaded ${MOD_NAME}-${VERSION} in ${System.currentTimeMillis() - VulcanMod.start}ms")
    }
}