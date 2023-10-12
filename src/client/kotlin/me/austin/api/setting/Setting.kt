package me.austin.api.setting

import me.austin.api.Name
import me.austin.api.Children as VulcanChildren

interface Setting<T> : Name {
    val default: T

    var value: T

    fun set(value: T) {
        this.value = value
    }
}

interface Children : VulcanChildren<Setting<*>> {
    override val children: Array<out Setting<*>>
}

interface Constrained<T : Number> {
    val minimum: T
    val maximum: T
}

abstract class NumberSetting<T : Number>(
    final override val name: String, final override val default: T, protected val increment: T
) : Setting<T> {
    final override var value = default
}
