package me.austin

import me.austin.api.Vulcan
import me.austin.rush.ConcurrentEventBus
import me.austin.rush.ReflectionEventBus
import net.fabricmc.api.ModInitializer

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import kotlin.properties.Delegates

object VulcanMod : Vulcan(), ModInitializer {
    var time by Delegates.notNull<Long>()

    val EVENTBUS: ReflectionEventBus = ConcurrentEventBus()

    override fun onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.

        time = System.currentTimeMillis()
    }
}