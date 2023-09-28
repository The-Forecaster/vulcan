package me.austin.impl.setting

import me.austin.api.setting.Children
import me.austin.api.setting.Setting

/**
 * For all settings within a module
 *
 * Use only once per module
 */
class Settings(private val values: List<Setting<*>>) {
    constructor(vararg settings: Setting<*>) : this(settings.toList())

    operator fun get(setting: String): Setting<*>? {
        return this.allSettings().find { it.name.lowercase() == setting.lowercase() }
    }

    fun allSettings(): List<Setting<*>> {
        val list = mutableListOf<Setting<*>>()

        fun recurse(settings: List<Setting<*>>) {
            for (setting in settings) {
                if (setting is Children) {
                    recurse(setting.children.toList())
                }
                list.add(setting)
            }
        }

        recurse(this.values)
        return list
    }
}