package link.takeneko.fp;

import com.mojang.authlib.GameProfile;
import link.takeneko.fp.mixin.core.ServerLoginNetworkHandlerAccessor;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerLoginNetworking;
import net.minecraft.network.Connection;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.login.ServerboundHelloPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerLoginPacketListenerImpl;
import link.takeneko.fp.mixin.core.ClientConnection_AddressAccessor;
import org.apache.logging.log4j.LogManager;

import java.util.Optional;

class PacketHandler {

    PacketHandler() {
    }

    void handleVelocityPacket(MinecraftServer server, ServerLoginPacketListenerImpl handler, boolean understood, FriendlyByteBuf buf, ServerLoginNetworking.LoginSynchronizer synchronizer, PacketSender ignored) {
        if (!understood) {
            handler.disconnect(Component.literal(Config.getAbortedMessage()));
            return;
        }

        synchronizer.waitFor(server.submit(() -> {
            try {
                if (!VelocityLib.checkIntegrity(buf)) {
                    handler.disconnect(Component.literal("Unable to verify player details"));
                    return;
                }
                VelocityLib.checkVersion(buf);
            } catch (Throwable e) {
                LogManager.getLogger().error("Secret check failed.", e);
                handler.disconnect(Component.literal("Unable to verify player details"));
                return;
            }

            Connection connection = ((ServerLoginNetworkHandlerAccessor) handler).getConnection();
            ((ClientConnection_AddressAccessor) connection).setAddress(new java.net.InetSocketAddress(VelocityLib.readAddress(buf), ((java.net.InetSocketAddress) (connection.getRemoteAddress())).getPort()));

            GameProfile profile;
            try {
                profile = VelocityLib.createProfile(buf);
            } catch (Exception e) {
                LogManager.getLogger().error("Profile create failed.", e);
                handler.disconnect(Component.literal("Unable to read player profile"));
                return;
            }

            if (Config.getHackEarlySend()) {
                handler.handleHello(new ServerboundHelloPacket(profile.getName(), Optional.of(profile.getId())));
            }

            handler.gameProfile = profile;
        }));
    }
}
