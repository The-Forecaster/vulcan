package me.austin.impl.gui

import me.austin.VulcanMod
import me.austin.api.Manager
import me.austin.api.Vulcan
import me.austin.api.Wrapper
import me.austin.api.gui.components.Frame
import me.austin.api.gui.components.buttons.Button
import me.austin.api.hack.AbstractHack
import me.austin.impl.events.KeyEvent
import me.austin.impl.friend.Friend
import me.austin.impl.friend.FriendManager
import me.austin.impl.hack.HackManager
import me.austin.rush.listener
import net.minecraft.client.gui.DrawableHelper
import net.minecraft.client.gui.screen.Screen
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.text.Text
import java.awt.Color
import java.nio.file.Files
import java.nio.file.Path

object ClickGuiScreen : Screen(Text.of(Vulcan.MOD_NAME)), Wrapper, Manager<Frame<*>, List<Frame<*>>> {
    override val values: List<Frame<*>>

    private val file: Path = Path.of("${VulcanMod.configFile}/clickguiscreen.json")

    private var shouldCloseOnEsc = true

    private var key = 'y'

    private val keyListener = listener<KeyEvent>(Integer.MAX_VALUE) {
        if (it.key == key.code) {
            minecraft.setScreen(ClickGuiScreen)
            it.cancel()
        }
    }

    init {
        var offset = 0

        this.values = listOf(object :
            Frame<AbstractHack>(20, 20, 60, HackManager.values.size * 20, HackManager.values.map { friend ->
                offset += 20

                object : Button<AbstractHack>(20, offset, 60, 20, friend) {
                    override fun render(stack: MatrixStack) {
                        DrawableHelper.fill(
                            stack,
                            this.xPos,
                            this.yPos,
                            this.width + this.xPos,
                            this.height + this.yPos,
                            Color.LIGHT_GRAY.rgb
                        )
                    }
                }
            }) {
            override fun render(stack: MatrixStack) {
                DrawableHelper.fill(
                    stack, this.xPos, this.yPos, this.width + this.xPos, this.height + this.yPos, Color.CYAN.rgb
                )

                for (button in this.children) {
                    button.render(stack)
                }
            }
        }, object : Frame<Friend>(100, 20, 60, FriendManager.values.size * 20, FriendManager.values.map { friend ->
            offset += 20

            object : Button<Friend>(20, offset, 60, 20, friend) {
                override fun render(stack: MatrixStack) {
                    DrawableHelper.fill(
                        stack, this.xPos, this.yPos, this.width + this.xPos, this.height + this.yPos, Color.YELLOW.rgb
                    )
                }
            }
        }) {
            override fun render(stack: MatrixStack) {
                DrawableHelper.fill(
                    stack, this.xPos, this.yPos, this.width + this.xPos, this.height + this.yPos, Color.LIGHT_GRAY.rgb
                )

                for (button in this.children) {
                    button.render(stack)
                }
            }
        })
    }

    override fun render(matrices: MatrixStack, mouseX: Int, mouseY: Int, delta: Float) {
        for (frame in values) {
            frame.render(matrices)
        }
    }

    override fun shouldPause(): Boolean {
        return false
    }

    override fun shouldCloseOnEsc(): Boolean {
        return shouldCloseOnEsc
    }

    override fun load() {
        if (!Files.exists(file)) {
            Files.createFile(file)
        }

        Vulcan.EVENT_MANAGER.subscribe(keyListener)
    }

    override fun unload() {
        Vulcan.EVENT_MANAGER.unsubscribe(keyListener)
    }
}
