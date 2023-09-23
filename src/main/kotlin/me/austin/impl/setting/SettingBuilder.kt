package me.austin.impl.setting

import me.austin.api.setting.Minimum
import me.austin.api.setting.NumberSetting
import me.austin.api.setting.Setting

abstract class SettingBuilder<T>(val name: String, protected val default: T) {
    protected var description: String? = null

    abstract fun build(): Setting<T>
}

class NumberSettingBuilder<T : Number>(name: String, default: T) : SettingBuilder<T>(name, default) {
    private var minumum: T? = null
    private var maximum: T? = null

    fun withMinimum(minimum: T): NumberSettingBuilder<T> {
        this.minumum = minimum

        return this
    }

    fun withMaximum(maximum: T): NumberSettingBuilder<T> {
        this.maximum = maximum

        return this
    }

    override fun build(): Setting<T> {
        when (this.default) {
            is Short -> {
                if (this.maximum != null) {
                }
            }
        }
    }
}
