package me.austin.impl.events

import me.austin.rush.Cancellable
import net.minecraft.network.Packet

class KeyEvent(val key: Int) : Cancellable {
    override var isCancelled = false
}

data class TickEvent(val isInWorld: Boolean)

abstract class PacketEvent(val packet: Packet<*>) : Cancellable {
    override var isCancelled = false

    class PreSend(packet: Packet<*>) : PacketEvent(packet)

    class PostReceive(packet: Packet<*>) : PacketEvent(packet)
}