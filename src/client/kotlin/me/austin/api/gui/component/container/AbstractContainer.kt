package me.austin.api.gui.component.container

import me.austin.api.gui.component.AbstractComponent
import me.austin.api.gui.component.Component

abstract class AbstractContainer<L : Collection<Component>> : AbstractComponent(), Container<L> {
}