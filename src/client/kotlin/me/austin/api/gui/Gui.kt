package me.austin.api.gui

import me.austin.api.gui.component.Component
import me.austin.api.gui.component.container.AbstractContainer
import me.austin.api.gui.render.theme.Theme

abstract class Gui(them: Theme) : AbstractContainer<List<Component>>() {

}