package me.austin

import me.austin.rush.ConcurrentEventBus
import me.austin.rush.ReflectionEventBus
import net.fabricmc.api.ModInitializer

import org.slf4j.Logger
import org.slf4j.LoggerFactory

object Vulcan : ModInitializer {
    val EVENTBUS: ReflectionEventBus = ConcurrentEventBus()

    // This logger is used to write text to the console and the log file.
    // It is considered best practice to use your mod id as the logger's name.
    // That way, it's clear which mod wrote info, warnings, and errors.
    // The JvmField annotation allows this field to be accessed directly by .java files
    @JvmField
    val LOGGER: Logger = LoggerFactory.getLogger("modid")

    override fun onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.

        LOGGER.info("Hello Fabric world!")
    }
}