package com.finderfeed.solarforge.SolarAbilities.AbilityClasses;

import com.finderfeed.solarforge.capabilities.capability_mana.CapabilitySolarMana;
import com.finderfeed.solarforge.misc_things.RunicEnergy;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.server.ServerWorld;

public class LightningAbility extends AbstractAbility{
    public LightningAbility() {
        super("lightning",50,new RunicEnergyCostConstructor()
        .addRunicEnergy(RunicEnergy.Type.KELDA,400)
        .addRunicEnergy(RunicEnergy.Type.URBA,250));
    }

    @Override
    public void cast(ServerPlayerEntity entity, ServerWorld world) {
        super.cast(entity, world);
        if (allowed) {

            Vector3d vec = entity.getLookAngle().multiply(200, 200, 200);
            RayTraceContext ctx = new RayTraceContext(entity.position().add(0, 1.5, 0), entity.position().add(0, 1.5, 0).add(vec), RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, entity);
            BlockRayTraceResult result = world.clip(ctx);

            if (result.getType() == RayTraceResult.Type.BLOCK) {
                BlockPos pos = result.getBlockPos();
                if (world.canSeeSky(pos.above())) {
                    LightningBoltEntity entityBolt = new LightningBoltEntity(EntityType.LIGHTNING_BOLT, world);
                    entityBolt.setPos(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
                    world.addFreshEntity(entityBolt);
                    world.explode(entity, pos.getX(), pos.getY(), pos.getZ(), 6, true, Explosion.Mode.BREAK);

                }else{
                    refund(entity);
                }

            }

        }

    }
}
