package me.austin

import me.austin.api.Vulcan
import me.austin.impl.gui.ClickGuiScreen
import net.fabricmc.api.ClientModInitializer
import net.minecraft.client.MinecraftClient
import java.nio.file.Files
import java.nio.file.Path

object VulcanClient : Vulcan(), ClientModInitializer {
    override fun onInitializeClient() {
        EVENT_MANAGER.subscribe(ClickGuiScreen)

        LOGGER.info("Loaded ${MOD_NAME}-${VERSION} in ${System.currentTimeMillis() - VulcanMod.start}ms")
    }
}