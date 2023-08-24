package me.austin

import me.austin.api.Vulcan
import net.fabricmc.api.ClientModInitializer
import net.minecraft.client.MinecraftClient
import java.nio.file.Files
import java.nio.file.Path

object VulcanClient : Vulcan(), ClientModInitializer {
    private const val CLIENT_FOLDER = "vulcan"
    private const val DEFAULT_CONFIG = "Vulcan_config.json"

    override fun onInitializeClient() {
        val configFolder = Path.of("${MinecraftClient.getInstance().runDirectory}\\${CLIENT_FOLDER}")

        if (!Files.exists(configFolder)) {
            Files.createDirectory(configFolder)
            Files.createFile(Path.of("${MinecraftClient.getInstance().runDirectory}\\${CLIENT_FOLDER}\\${DEFAULT_CONFIG}"))
        }

        LOGGER.info("Loaded ${MOD_NAME}-${VERSION} in ${System.currentTimeMillis() - VulcanMod.time}ms")
    }
}