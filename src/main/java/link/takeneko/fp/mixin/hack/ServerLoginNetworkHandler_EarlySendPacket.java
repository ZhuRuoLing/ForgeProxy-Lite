package link.takeneko.fp.mixin.hack;

import com.mojang.authlib.GameProfile;
import net.fabricmc.fabric.impl.networking.NetworkHandlerExtensions;
import net.fabricmc.fabric.impl.networking.server.ServerLoginNetworkAddon;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.login.ServerboundHelloPacket;
import net.minecraft.server.network.ServerLoginPacketListenerImpl;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static link.takeneko.fp.VelocityLib.PLAYER_INFO_CHANNEL;
import static link.takeneko.fp.VelocityLib.PLAYER_INFO_PACKET;

@SuppressWarnings("UnstableApiUsage")
@Mixin(ServerLoginPacketListenerImpl.class)
public class ServerLoginNetworkHandler_EarlySendPacket {
    @Shadow
    @Final
    Connection connection;
    @Shadow
    @Nullable
    public GameProfile gameProfile;

    @Inject(method = "handleHello", at = @At(value = "HEAD"), cancellable = true)
    private void skipKeyPacket(ServerboundHelloPacket p_10047_, CallbackInfo ci) {
        if (gameProfile != null) return; // Already receive profile form velocity.

        ServerLoginNetworkAddon addon = (ServerLoginNetworkAddon) ((NetworkHandlerExtensions) this).getAddon();
        connection.send(addon.createPacket(PLAYER_INFO_CHANNEL, PLAYER_INFO_PACKET));
        ci.cancel();
    }
}
