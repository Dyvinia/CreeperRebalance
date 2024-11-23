package net.dyvinia.creeperrebalance;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

@Mod.EventBusSubscriber(modid = CreeperRebalance.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    private static final ForgeConfigSpec.BooleanValue DISABLE_GRIEFING = BUILDER
            .comment("\n Prevents creeper explosions from breaking blocks. \n Default: true")
            .define("disableGriefing", true);
    private static final ForgeConfigSpec.ConfigValue<Double> KNOCKBACK_MULT = BUILDER
            .comment("\n Multiplier for extra knockback caused by the explosion.\n Set to 0.0 for vanilla behavior.\n Default: 3.0")
            .define("knockbackMult", 3.0);
    private static final ForgeConfigSpec.ConfigValue<Double> PLAYER_KNOCKBACK_MULT = BUILDER
            .comment("\n Multiplier for extra knockback caused by the explosion to players.\n Set to -1.0 to default to the value of knockbackMult.\n Default: 2.0")
            .define("playerKnockbackMult", 2.0);

    private static final ForgeConfigSpec.ConfigValue<Double> FALLOFF_EXPONENT = BUILDER
            .comment("\n Exponent used for calculating the falloff.\n Default: 2.0")
            .define("falloffExponent", 2.0);

    public static boolean disableGriefing;
    public static double knockbackMult;
    public static double playerKnockbackMult;
    public static double falloffExponent;

    static final ForgeConfigSpec SPEC = BUILDER.build();

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
        disableGriefing = DISABLE_GRIEFING.get();
        knockbackMult = KNOCKBACK_MULT.get();
        playerKnockbackMult = PLAYER_KNOCKBACK_MULT.get();
        falloffExponent = FALLOFF_EXPONENT.get();
    }
}
