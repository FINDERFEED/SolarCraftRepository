package com.finderfeed.solarforge.SolarAbilities.AbilityClasses;

import com.finderfeed.solarforge.SolarAbilities.SolarStrikeEntity;
import com.finderfeed.solarforge.SolarForge;
import com.finderfeed.solarforge.capabilities.capability_mana.CapabilitySolarMana;
import com.finderfeed.solarforge.misc_things.RunicEnergy;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.server.ServerWorld;

public class SolarStrikeAbility extends AbstractAbility{
    public SolarStrikeAbility() {
        super("solar_strike",1000,new RunicEnergyCostConstructor()
        .addRunicEnergy(RunicEnergy.Type.FIRA,5000)
        .addRunicEnergy(RunicEnergy.Type.ARDO,2000)
        .addRunicEnergy(RunicEnergy.Type.KELDA,1000));
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
                    SolarStrikeEntity entityBolt = new SolarStrikeEntity(SolarForge.SOLAR_STRIKE_ENTITY_REG.get(), world);
                    entityBolt.setPos(pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5);
                    world.addFreshEntity(entityBolt);
                }else{
                    refund(entity);
                }
            }

        }

    }
}
