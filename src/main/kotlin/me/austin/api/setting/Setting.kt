package me.austin.api.setting

import me.austin.api.Name

interface Setting<T> : Name {
    val default: T

    var value: T

    fun set(value: T) {
        this.value = value
    }
}

interface Children {
    val children: Array<Setting<*>>
}

abstract class NumberSetting<T : Number>(
    final override val name: String, final override val default: T, protected val increment: T, val minimum: T, val maximum: T
) : Setting<T> {
    final override var value = default
}
