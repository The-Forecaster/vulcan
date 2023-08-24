package me.austin.api.gui.component.container

import me.austin.api.gui.component.Component

interface Container<L : Collection<Component>> : Component {
    val children: L
}