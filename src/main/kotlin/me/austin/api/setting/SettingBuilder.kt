package me.austin.api.setting

abstract class AbstractSettingBuilder<T, S : Setting<*>> internal constructor(val name: String, protected val default: T) {
    protected var description: String? = null
    protected var children: Array<out Setting<*>>? = null

    fun withDescription(description: String): AbstractSettingBuilder<T, S> {
        this.description = description

        return this
    }

    fun withChildren(vararg children: Setting<*>): AbstractSettingBuilder<T, S> {
        this.children = children

        return this
    }

    abstract fun build(): S
}

abstract class NumberSettingBuilder<T : Number, S : NumberSetting<T>> internal constructor(name: String, default: T) : AbstractSettingBuilder<T, S>(name, default) {
    protected var increment: T? = null
    protected var minumum: T? = null
    protected var maximum: T? = null

    fun withIncrement(increment: T): NumberSettingBuilder<T, S> {
        this.increment = increment

        return this
    }

    fun withMinimum(minimum: T): NumberSettingBuilder<T, S> {
        this.minumum = minimum

        return this
    }

    fun withMaximum(maximum: T): NumberSettingBuilder<T, S> {
        this.maximum = maximum

        return this
    }
}