package net.dyvinia.creeperrebalance;

import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.level.ExplosionEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(CreeperRebalance.MODID)
public class CreeperRebalance {

    // Define mod id in a common place for everything to reference
    public static final String MODID = "creeperrebalance";

    public CreeperRebalance() {
        MinecraftForge.EVENT_BUS.register(this);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }
    
    @Mod.EventBusSubscriber(modid = MODID)
    public static class CreeperEvents {

        @SubscribeEvent
        public static void onExplosionStart(ExplosionEvent.Start event) {
            if (Config.disableGriefing && event.getExplosion().getExploder() instanceof Creeper creeper) {
               event.setCanceled(true);

                // recreate explosion but without breaking blocks
                float radius = (creeper.isPowered() ? 2.0f : 1.0f) * 3.0f;
                Explosion explosion = new Explosion(creeper.level(), creeper, null, null, creeper.getX(), creeper.getY(), creeper.getZ(), radius, false,  Explosion.BlockInteraction.KEEP);
                explosion.explode();
            }
        }

        @SubscribeEvent
        public static void onExplosionDetonate(ExplosionEvent.Detonate event) {
            // add extra knockback
            event.getAffectedEntities().forEach(entity -> {
                if (event.getExplosion().getExploder() instanceof Creeper creeper) {
                    float radius = (creeper.isPowered() ? 2.0f : 1.0f) * 4.0f;
                    float distance = entity.distanceTo(creeper);
                    float power = (float) (1.0f - Math.pow(distance/radius, Config.falloffExponent));

                    if (power > 0) {
                        float knockback = power;
                        if (Config.playerKnockbackMult >= 0 && entity instanceof Player)
                            knockback *= Config.playerKnockbackMult;
                        else
                            knockback *= Config.knockbackMult;

                        Vec3 direction = entity.position().subtract(creeper.position()).normalize();
                        Vec3 velocity = new Vec3(direction.x, Config.knockbackUp, direction.z);
                        velocity = velocity.scale(knockback);
                        entity.addDeltaMovement(velocity);
                    }
                }
            });
        }
    }
}
