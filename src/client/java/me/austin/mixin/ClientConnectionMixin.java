package me.austin.mixin;

import me.austin.api.Vulcan;
import me.austin.impl.events.PacketEvent;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.Packet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientConnection.class)
public class ClientConnectionMixin {
    @Inject(at = @At("HEAD"), method = "send*", cancellable = true)
    private void onSendPacketHead(final Packet<?> packet, final CallbackInfo info) {
        if (Vulcan.EVENT_MANAGER.post(new PacketEvent.PreSend(packet)).isCancelled()) {
            info.cancel();
        }
    }
}
