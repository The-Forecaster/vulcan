package me.austin

import me.austin.api.Vulcan
import me.austin.rush.ConcurrentEventBus
import me.austin.rush.ReflectionEventBus
import net.fabricmc.api.ModInitializer

object VulcanMod : Vulcan(), ModInitializer {
    var start = System.currentTimeMillis()

    val EVENTBUS: ReflectionEventBus = ConcurrentEventBus()

    override fun onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.

        start = System.currentTimeMillis()
    }
}