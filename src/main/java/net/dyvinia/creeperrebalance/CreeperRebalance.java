package net.dyvinia.creeperrebalance;

import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.level.ExplosionEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod(CreeperRebalance.MODID)
public class CreeperRebalance {

    // Define mod id in a common place for everything to reference
    public static final String MODID = "creeperrebalance";

    public CreeperRebalance() {
        MinecraftForge.EVENT_BUS.register(this);
        
        //ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, Config.SPEC);
    }
    
    @Mod.EventBusSubscriber(modid = MODID)
    public static class CreeperEvents {

        @SubscribeEvent
        public static void onExplosionStart(ExplosionEvent.Start event) {
            if (event.getExplosion().getExploder() instanceof Creeper creeper) {
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
                    float power = 1.0f - (distance/radius);

                    if (power > 0) {
                        power *= 3.0f;
                        Vec3 relative = entity.position().subtract(creeper.position());
                        relative.normalize();
                        Vec3 knockback = new Vec3(relative.x, 0.25f, relative.z);
                        knockback = knockback.scale(power);
                        entity.addDeltaMovement(knockback);
                    }
                }
            });
        }
    }
}
