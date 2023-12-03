package link.takeneko.fp.mixin.hack;

import net.minecraft.network.chat.LastSeenMessages;
import net.minecraft.network.chat.PlayerChatMessage;
import net.minecraft.network.protocol.game.ServerboundChatPacket;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerGamePacketListenerImpl.class)
public class ServerPlayNetworkHandler_HackMessageChain {
    @Inject(method = "getSignedMessage", at = @At("RETURN"), cancellable = true)
    private void disableSecureChat(ServerboundChatPacket p_251061_, LastSeenMessages p_250566_, CallbackInfoReturnable<PlayerChatMessage> cir) {
        cir.setReturnValue(PlayerChatMessage.system(p_251061_.message()));
    }
}
