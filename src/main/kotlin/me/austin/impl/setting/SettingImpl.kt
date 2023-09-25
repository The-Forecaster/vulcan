package me.austin.impl.setting

import me.austin.api.setting.NumberSetting
import me.austin.api.setting.Setting
import me.austin.util.round

class BooleanSetting internal constructor(
    override val name: String, override val default: Boolean
) : Setting<Boolean> {
    override var value = default
}

class EnumSetting<T : Enum<*>> internal constructor(
    override val name: String,  override val default: T
) : Setting<T> {
    override var value = default

    /**
     * set function but accepts a string instead of an enum constant
     *
     * @param other string to set the enum value to
     * @return true if the string matches one of the enum constants in this setting
     */
    fun set(other: String): Boolean {
        for (value in this.default.javaClass.enumConstants) {
            if (other.lowercase() == value.toString().lowercase()) {
                this.value = value
                return true
            }
        }
        return false
    }
}

class LongSetting internal constructor(name: String, default: Long, minimum: Long, maximum: Long) :
    NumberSetting<Long>(name, default, 1L, minimum, maximum)

class IntSetting internal constructor(name: String, default: Int, minimum: Int, maximum: Int) :
    NumberSetting<Int>(name, default, 1, minimum, maximum)

class DoubleSetting internal constructor(
    name: String, default: Double, increment: Double, minimum: Double, maximum: Double
) : NumberSetting<Double>(name, default, increment, minimum, maximum) {
    fun set(other: Double, round: Boolean) = if (round) this.value = other.round(this.increment) else this.value = other
}

class FloatSetting internal constructor(name: String, default: Float, increment: Float, minimum: Float, maximum: Float) :
    NumberSetting<Float>(name, default, increment, minimum, maximum) {
    fun set(other: Float, round: Boolean) = if (round) this.value = other.round(this.increment) else this.value = other
}