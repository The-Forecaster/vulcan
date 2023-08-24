package me.austin.api

import org.slf4j.Logger
import org.slf4j.LoggerFactory

abstract class Vulcan {
    companion object {
        const val MODID = "vulcan"
        const val MOD_NAME = "Vulcan"
        const val VERSION = "b1"
        const val NAME_UNICODE = "\\u0056\\u0075\\u006c\\u0063\\u0061\\u006e"

        // This logger is used to write text to the console and the log file.
        // It is considered best practice to use your mod id as the logger's name.
        // That way, it's clear which mod wrote info, warnings, and errors.
        // The JvmField annotation allows this field to be accessed directly by .java files
        @JvmField
        val LOGGER: Logger = LoggerFactory.getLogger(MODID)
    }
}