package link.takeneko.fp;

import com.mojang.logging.LogUtils;
import net.fabricmc.fabric.api.networking.v1.ServerLoginConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerLoginNetworking;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import org.slf4j.Logger;

import static link.takeneko.fp.VelocityLib.PLAYER_INFO_CHANNEL;
import static link.takeneko.fp.VelocityLib.PLAYER_INFO_PACKET;


// The value here should match an entry in the META-INF/mods.toml file
@Mod(ForgeProxyLite.MODID)
public class ForgeProxyLite {
    public static final String MODID = "forgeproxylite";
    private static final Logger LOGGER = LogUtils.getLogger();

    public ForgeProxyLite() {
        ServerLoginNetworking.registerGlobalReceiver(PLAYER_INFO_CHANNEL, new PacketHandler()::handleVelocityPacket);
        if (Config.getHackEarlySend()){
            ServerLoginConnectionEvents.QUERY_START.register((handler, server, sender, synchronizer) -> sender.sendPacket(PLAYER_INFO_CHANNEL, PLAYER_INFO_PACKET));
        }
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

}
