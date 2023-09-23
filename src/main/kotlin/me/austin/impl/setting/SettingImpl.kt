package me.austin.impl.setting

import me.austin.api.setting.NumberSetting
import me.austin.api.setting.Setting
import me.austin.util.round

class BooleanSetting(
    override val name: String, override val default: Boolean
) : Setting<Boolean> {
    override var value = default
}

class EnumSetting<T : Enum<*>>(
    override val name: String,  override val default: T
) : Setting<T> {
    override var value = default

    /**
     * set function but accepts a string instead of an enum constant
     *
     * @param other string to set the enum value to
     * @return true if the string is able to be set to one of the enum constants in this setting
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

class LongSetting(name: String, default: Long) :
    NumberSetting<Long>(name, default, 1L)

class IntSetting(name: String, default: Int) :
    NumberSetting<Int>(name, default, 1)

class ShortSetting(name: String, default: Short) :
    NumberSetting<Short>(name, default, 1)

class ByteSetting(name: String, default: Byte) :
    NumberSetting<Byte>(name, default, 1)

class DoubleSetting(
    name: String, default: Double, increment: Double
) : NumberSetting<Double>(name, default, increment) {
    fun set(other: Double, round: Boolean) = if (round) this.value = other.round(this.increment) else this.value = other
}

class FloatSetting(name: String, default: Float, increment: Float) :
    NumberSetting<Float>(name, default, increment) {
    fun set(other: Float, round: Boolean) = if (round) this.value = other.round(this.increment) else this.value = other
}
