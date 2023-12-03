package link.takeneko.fp;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

@Mod.EventBusSubscriber(modid = ForgeProxyLite.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    private static final ForgeConfigSpec.BooleanValue HACK_ONLINE_MODE = BUILDER
            .comment("Force online mode to false.")
            .define("hackOnlineMode", true);

    private static final ForgeConfigSpec.BooleanValue HACK_EARLY_SEND = BUILDER
            .comment("Enable hackEarlySend will use mixin for early send packet to velocity.")
            .define("hackEarlySend", false);

    private static final ForgeConfigSpec.BooleanValue HACK_MESSAGE_CHAIN = BUILDER
            .define("hackMessageChain", true);

    private static final ForgeConfigSpec.ConfigValue<String> DISCONNECT_MESSAGE = BUILDER
            .define("disconnectMessage", "This server requires you to connect with Velocity.");

    private static final ForgeConfigSpec.ConfigValue<String> SECRET = BUILDER
            .define("secret", "");


    static final ForgeConfigSpec SPEC = BUILDER.build();

    private static boolean hackOnlineMode = true;
    private static boolean hackEarlySend = false;
    private static boolean hackMessageChain = true;
    private static String disconnectMessage = "This server requires you to connect with Velocity.";
    private static String secret = "";


    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
        hackOnlineMode = HACK_ONLINE_MODE.get();
        hackEarlySend = HACK_EARLY_SEND.get();
        hackMessageChain = HACK_MESSAGE_CHAIN.get();
        disconnectMessage = DISCONNECT_MESSAGE.get();
        secret = SECRET.get();
    }

    public static String getAbortedMessage() {
        String env = System.getenv("FABRIC_PROXY_MESSAGE");
        if (env == null) {
            return disconnectMessage;
        } else {
            return env;
        }
    }

    public static boolean getHackOnlineMode() {
        String env = System.getenv("FABRIC_PROXY_HACK_ONLINE_MODE");
        if (env == null) {
            return hackOnlineMode;
        } else {
            return Boolean.parseBoolean(env);
        }
    }

    public static boolean getHackEarlySend() {
        String env = System.getenv("FABRIC_PROXY_HACK_FABRIC_API");
        if (env == null) {
            return hackEarlySend;
        } else {
            return Boolean.parseBoolean(env);
        }
    }

    public static boolean getHackMessageChain() {
        String env = System.getenv("FABRIC_PROXY_HACK_MESSAGE_CHAIN");
        if (env == null) {
            return hackMessageChain;
        } else {
            return Boolean.parseBoolean(env);
        }
    }

    public static String getSecret() {
        String env = System.getenv("FABRIC_PROXY_SECRET");
        if (env == null) {
            return secret;
        } else {
            return env;
        }
    }

}
