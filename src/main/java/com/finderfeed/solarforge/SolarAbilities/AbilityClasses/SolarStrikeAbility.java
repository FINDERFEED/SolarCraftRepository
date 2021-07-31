package com.finderfeed.solarforge.SolarAbilities.AbilityClasses;

import com.finderfeed.solarforge.SolarAbilities.SolarStrikeEntity;
import com.finderfeed.solarforge.SolarForge;
import com.finderfeed.solarforge.capabilities.capability_mana.CapabilitySolarMana;
import com.finderfeed.solarforge.misc_things.RunicEnergy;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.server.level.ServerLevel;

public class SolarStrikeAbility extends AbstractAbility{
    public SolarStrikeAbility() {
        super("solar_strike",1000,new RunicEnergyCostConstructor()
        .addRunicEnergy(RunicEnergy.Type.FIRA,5000)
        .addRunicEnergy(RunicEnergy.Type.ARDO,2000)
        .addRunicEnergy(RunicEnergy.Type.KELDA,1000),30000);
    }

    @Override
    public void cast(ServerPlayer entity, ServerLevel world) {
        super.cast(entity, world);
        if (allowed) {

            Vec3 vec = entity.getLookAngle().multiply(200, 200, 200);
            ClipContext ctx = new ClipContext(entity.position().add(0, 1.5, 0), entity.position().add(0, 1.5, 0).add(vec), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, entity);
            BlockHitResult result = world.clip(ctx);

            if (result.getType() == HitResult.Type.BLOCK) {

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
