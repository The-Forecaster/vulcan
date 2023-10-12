package me.austin.api.gui

import net.minecraft.client.util.math.MatrixStack

abstract class Component(var xPos: Int, var yPos: Int, var width: Int, var height: Int) {
    fun isOver(mouseX: Int, mouseY: Int) =
        mouseX > this.xPos && mouseX < this.xPos + width && mouseY > this.yPos && mouseY < this.yPos + this.xPos

    fun setPos(x: Int, y: Int) {
        this.xPos = x
        this.yPos = y
    }

    abstract fun render(stack: MatrixStack)
}
