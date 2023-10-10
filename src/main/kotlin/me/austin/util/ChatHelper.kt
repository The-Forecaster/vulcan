package me.austin.util

import me.austin.api.Vulcan
import me.austin.api.Wrapper
import net.minecraft.text.Text

const val modifier = "§"

internal const val prefix = "${modifier}9[${Vulcan.MOD_NAME}]${modifier}r "

fun Wrapper.clientSend(text: String, pre: Boolean = true) {
    minecraft.inGameHud.chatHud.addMessage(Text.of(if (pre) {
        "$prefix $text"
    } else {
        text
    }))
}

fun Wrapper.error(text: String, pre: Boolean = true) {
    minecraft.inGameHud.chatHud.addMessage(Text.of(if (pre) {
        "$prefix${modifier}c $text"
    } else {
        "${modifier}c$text"
    }))
}
