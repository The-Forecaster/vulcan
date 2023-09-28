package me.austin.impl.hack

import me.austin.api.hack.AbstractHack
import me.austin.impl.events.PacketEvent
import me.austin.impl.setting.*
import me.austin.rush.listener
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket.Full
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket.LookAndOnGround

object CrystalAura : AbstractHack("CrystalAura", "Automatically places and breaks end-crystals") {
    private val placeRange = FloatSettingBuilder("Place-Range").default(3.5f).minimum(0f).maximum(255f).build()
    private val minPlaceDamage = FloatSettingBuilder("Minimum-damage").default(5f).minimum(0f).maximum(20f).build()
    private val place = BooleanSettingBuilder("Place").default(true).children(placeRange, minPlaceDamage).build()

    private val swap = BooleanSettingBuilder("Switch").default(true).build()
    private val weakness = BooleanSettingBuilder("Anti-weakness").default(true).build()
    private val delay = IntSettingBuilder("Break-delay").default(4).minimum(0).maximum(20).build()
    private val minBreakDamage =
        FloatSettingBuilder("Minimum-break-damage").default(2f).minimum(0f).maximum(20f).build()
    private val breakRange = FloatSettingBuilder("Break-range").default(5.5f).minimum(0f).maximum(255f).build()
    private val rayTrace = BooleanSettingBuilder("Raytrace").default(true).build()
    private val destroy = BooleanSettingBuilder("Break").default(true)
        .children(swap, weakness, delay, minBreakDamage, breakRange, rayTrace).build()

    private val spoofRotations = BooleanSettingBuilder("Spoof-rotations").default(true).build()
    private val chat = BooleanSettingBuilder("Announce-usage").default(true).build()

    private val mode = EnumSettingBuilder("Mode", RenderMode.FULL).build()
    private val red = IntSettingBuilder("Red").default(100).minimum(0).maximum(255).build()
    private val green = IntSettingBuilder("Green").default(100).minimum(0).maximum(255).build()
    private val blue = IntSettingBuilder("Blue").default(100).minimum(0).maximum(255).build()
    private val alpha = IntSettingBuilder("Alpha").default(100).minimum(0).maximum(255).build()
    private val render = BooleanSettingBuilder("Render").default(true).children(mode, red, green, blue, alpha).build()

    private var yaw = 0.0
    private var pitch = 0.0
    private var isSpoofing = false

    override val settings = Settings(place, destroy, spoofRotations, chat, render)
    override val listeners = listOf(listener<PacketEvent.PreSend> { event ->
        if (spoofRotations.value) {
            if (event.packet is PlayerMoveC2SPacket && isSpoofing) {
                when (event.packet) {
                    is LookAndOnGround -> {
                        event.cancel()
                        minecraft.networkHandler?.connection?.send(
                            LookAndOnGround(
                                yaw.toFloat(), pitch.toFloat(), event.packet.isOnGround
                            )
                        )
                    }

                    is Full -> {
                        event.cancel()
                        minecraft.networkHandler?.connection?.send(
                            Full(
                                player?.x ?: 0.0,
                                player?.y ?: 0.0,
                                player?.z ?: 0.0,
                                yaw.toFloat(),
                                pitch.toFloat(),
                                event.packet.isOnGround
                            )
                        )
                    }
                }
            }
        }
    })

    private enum class RenderMode {
        TOP, FULL
    }
}