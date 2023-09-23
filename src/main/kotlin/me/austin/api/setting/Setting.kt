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
    override val name: String, final override val default: T, protected val increment: T
) : Setting<T> {
    override var value = default
}

interface Minimum<T : Number> {
    val minimum: T
}

interface Maximum<T : Number> {
    val minimum: T
}