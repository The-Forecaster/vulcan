package me.austin.api.gui.components

import me.austin.api.Wrapper
import me.austin.api.gui.Component
import me.austin.api.gui.Container
import me.austin.api.gui.components.buttons.Button
import net.minecraft.client.util.math.MatrixStack

open class Frame<T>(xPos: Int, yPos: Int, width: Int, height: Int, override val children: List<Button<T>>) :
    Component(xPos, yPos, width, height), Wrapper, Container {
    override fun render(stack: MatrixStack) {
        for (button in children) {
            button.render(stack)
        }
    }
}