package me.austin.api.gui.render.theme

import me.austin.api.gui.component.Component
import net.minecraft.client.font.TextRenderer

interface Theme {
    val textRenderer: TextRenderer

    fun getUIforComponent(component: Component)
}