package me.austin.impl.setting

import me.austin.api.setting.NumberSetting
import me.austin.api.setting.Setting

abstract class SettingBuilder<T, S : Setting<*>> internal constructor(val name: String, protected val default: T) {
    private var description: String? = null

    fun withDescription(description: String): SettingBuilder<T, S> {
        this.description = description

        return this
    }

    abstract fun build(): S
}

class BooleanSettingBuilder(name: String, default: Boolean) : SettingBuilder<Boolean, BooleanSetting>(name, default) {
    override fun build(): BooleanSetting {
        TODO("Not yet implemented")
    }
}

abstract class NumberSettingBuilder<T : Number, S : NumberSetting<T>> internal constructor(name: String, default: T) : SettingBuilder<T, S>(name, default) {
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

class IntSettingBuilder(name: String, default: Int) : NumberSettingBuilder<Int, IntSetting>(name, default) {
    override fun build(): IntSetting {
        if (this.minumum == null) {
            this.minumum = -20
        }

        if (this.maximum == null) {
            this.maximum = 20
        }

        return IntSetting(this.name, this.default, this.minumum!!, this.maximum!!)
    }
}

class LongSettingBuilder(name: String, default: Long) : NumberSettingBuilder<Long, LongSetting>(name, default) {
    override fun build(): LongSetting {
        if (this.minumum == null) {
            this.minumum = -20
        }

        if (this.maximum == null) {
            this.maximum = 20
        }

        return LongSetting(this.name, this.default, this.minumum!!, this.maximum!!)
    }
}

class FloatSettingBuilder(name: String, default: Float) : NumberSettingBuilder<Float, FloatSetting>(name, default) {
    override fun build(): FloatSetting {
        if (this.increment == null) {
            this.increment = 0.1f
        }

        if (this.minumum == null) {
            this.minumum = -20f
        }

        if (this.maximum == null) {
            this.maximum = 20f
        }

        return FloatSetting(this.name, this.default, this.increment!!, this.minumum!!, this.maximum!!)
    }
}

class DoubleSettingBuilder(name: String, default: Double) : NumberSettingBuilder<Double, DoubleSetting>(name, default) {
    override fun build(): DoubleSetting {
        if (this.increment == null) {
            this.increment = 0.1
        }

        if (this.minumum == null) {
            this.minumum = -20.0
        }

        if (this.maximum == null) {
            this.maximum = 20.0
        }

        return DoubleSetting(this.name, this.default, this.increment!!, this.minumum!!, this.maximum!!)
    }
}
