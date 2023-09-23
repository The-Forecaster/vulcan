package me.austin.api

interface Name {
    val name: String
}

interface Description {
    val description: String
}

interface Manager<out T, out L : Collection<T>> {
    val values: L

    fun load()

    fun unload()
}

abstract class Modular(final override val name: String, final override val description: String) : Name, Description