package me.austin.api

import me.austin.rush.ConcurrentEventBus
import me.austin.rush.ReflectionEventBus
import org.slf4j.Logger
import org.slf4j.LoggerFactory

abstract class Vulcan {
    companion object {
        private const val MOD_ID = "vulcan"
        const val MOD_NAME = "Vulcan"
        const val VERSION = "b1"

        // This logger is used to write text to the console and the log file.
        // It is considered best practice to use your mod id as the logger's name.
        // That way, it's clear which mod wrote info, warnings, and errors.
        // The JvmField annotation allows this field to be accessed directly by .java files
        @JvmField
        val LOGGER: Logger = LoggerFactory.getLogger(MOD_ID)

        @JvmField
        val EVENT_MANAGER: ReflectionEventBus = ConcurrentEventBus()
    }
}