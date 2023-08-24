package me.austin.api.gui.render

import me.austin.api.gui.component.Component
import net.minecraft.client.font.TextRenderer

interface ComponentUI<T : Component> {
    val handledClass: Class<Component>

    fun render(component: T, fontRenderer: TextRenderer)
}