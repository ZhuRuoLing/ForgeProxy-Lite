package link.takeneko.fp.mixin.core;

import net.minecraft.network.Connection;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.net.SocketAddress;

@Mixin(Connection.class)
public interface ClientConnection_AddressAccessor {
    @Accessor
    void setAddress(SocketAddress address);
}
