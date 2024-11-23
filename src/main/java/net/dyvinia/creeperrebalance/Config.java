package net.dyvinia.creeperrebalance;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

// An example config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Forge's config APIs
@Mod.EventBusSubscriber(modid = CreeperRebalance.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config
{
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    private static final ForgeConfigSpec.ConfigValue<Float> KNOCKBACK_MULT = BUILDER
            .comment("How much knockback is added to the explosion")
            .define("knockbackMult", 4.0f);

    static final ForgeConfigSpec SPEC = BUILDER.build();

    public static Float knockbackMult;

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event)
    {
        knockbackMult = KNOCKBACK_MULT.get();
    }
}
