package me.austin

import net.fabricmc.api.ClientModInitializer
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

object VulcanClient : ClientModInitializer {
    const val MODID = "vulcan"
    const val MODNAME = "Vulcan"
    const val VERSION = "b1"
    const val NAME_UNICODE = "\\u0056\\u0075\\u006c\\u0063\\u0061\\u006e"

    val LOGGER: Logger = LogManager.getLogger(MODID)

    override fun onInitializeClient() {
        // This entrypoint is suitable for setting up client-specific logic, such as rendering.
    }
}