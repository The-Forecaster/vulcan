package me.austin.util

import kotlin.math.abs

fun Double.round(increment: Double): Double {
    return if (abs(this - this.floor(increment)) > abs(this - this.ceil(increment))) {
        this.ceil(increment)
    } else {
        this.floor(increment)
    }
}

fun Double.ceil(increment: Double): Double {
    return this.floor(increment) + increment
}

fun Double.floor(increment: Double): Double {
    return this - (this % increment)
}

fun Float.round(increment: Float): Float {
    return if (abs(this - this.floor(increment)) > abs(this - this.ceil(increment))) {
        this.ceil(increment)
    } else {
        this.floor(increment)
    }
}

fun Float.ceil(increment: Float): Float {
    return this.floor(increment) + increment
}

fun Float.floor(increment: Float): Float {
    return this - (this % increment)
}