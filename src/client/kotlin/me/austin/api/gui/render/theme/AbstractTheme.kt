package me.austin.api.gui.render.theme

import me.austin.api.gui.component.Component
import me.austin.api.gui.render.ComponentUI
import me.austin.api.gui.render.layout.Layout

abstract class AbstractTheme : Theme {
    protected val uiMap = mutableMapOf<Class<Component>, ComponentUI<*>>()
    protected val layoutMap = mutableMapOf<Class<Component>, Class<Layout>>()
}