package me.austin.api.gui.render.layout

import me.austin.api.gui.component.container.Container

interface Layout {
    fun organizeContainer(container: Container<*>)
}