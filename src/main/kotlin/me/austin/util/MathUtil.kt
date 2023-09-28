package me.austin.util

import kotlin.math.abs

fun Double.round(increment: Double) = if (abs(this - this.floor(increment)) > abs(this - this.ceil(increment))) {
    this.ceil(increment)
} else {
    this.floor(increment)
}

fun Double.ceil(increment: Double) = this.floor(increment) + increment

fun Double.floor(increment: Double) = this - (this % increment)

fun Float.round(increment: Float) = if (abs(this - this.floor(increment)) > abs(this - this.ceil(increment))) {
    this.ceil(increment)
} else {
    this.floor(increment)
}
fun Float.ceil(increment: Float) = this.floor(increment) + increment

fun Float.floor(increment: Float) = this - (this % increment)