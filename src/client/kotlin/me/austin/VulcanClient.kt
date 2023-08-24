package me.austin

import net.fabricmc.api.ClientModInitializer

object VulcanClient : ClientModInitializer {
    const val defaultConfig = "Vulcan_config.json"

    override fun onInitializeClient() {
        // This entrypoint is suitable for setting up client-specific logic, such as rendering.
    }
}