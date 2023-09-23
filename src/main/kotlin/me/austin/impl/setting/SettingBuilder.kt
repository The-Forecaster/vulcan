package me.austin.impl.setting

import me.austin.api.Description
import me.austin.api.setting.Constrained
import me.austin.api.setting.NumberSetting
import me.austin.api.setting.Setting

abstract class SettingBuilder<T>(val name: String, protected val default: T) {
    protected var description: String? = null

    fun withDescription(description: String): SettingBuilder<T> {
        this.description = description
        return this
    }

    abstract fun build(): Setting<T>
}

class NumberSettingBuilder<T : Number>(name: String, default: T) : SettingBuilder<T>(name, default) {
    private var increment: T? = null
    private var minumum: T? = null
    private var maximum: T? = null

    fun withIncrement(increment: T): NumberSettingBuilder<T> {
        this.increment = increment

        return this
    }

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
            is Int -> {
                if (this.minumum != null || this.maximum != null) {
                    if (this.description != null) {
                        class ConstrainedIntSettingWithDescription(
                            name: String,
                            default: Int,
                            increment: Int,
                            override val description: String,
                            override val minimum: Int,
                            override val maximum: Int
                        ) : NumberSetting<Int>(name, default, increment),
                            Constrained<Int, ConstrainedIntSettingWithDescription>, Description

                        return ConstrainedIntSettingWithDescription(
                            this.name,
                            this.default,
                            this.increment ?: 1,
                            this.description,
                            this.minumum ?: -100,
                            this.maximum ?: 100
                        )
                    }
                }
            }

            is Long -> {

            }

            is Float -> {

            }

            is Double -> {

            }
        }
    }
}
