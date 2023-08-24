package me.austin.api.gui.component

import me.austin.api.gui.component.container.Container
import me.austin.api.gui.render.ComponentUI
import me.austin.api.gui.render.theme.Theme

interface Component {
    var x: Int
    var y: Int
    var width: Int
    var height: Int

    var minimumWidth: Int
    var minimumHeight: Int
    var maximumWidth: Int
    var maximumHeight: Int

    var opacity: Float

    var parent: Container<*>

    var visible: Boolean
    var focused: Boolean
    var isEffectLayoutActive: Boolean

    var them: Theme

    val componentUI: ComponentUI<*>

    fun liesIn()
}