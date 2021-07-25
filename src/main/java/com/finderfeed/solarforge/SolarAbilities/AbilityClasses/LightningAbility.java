package com.finderfeed.solarforge.SolarAbilities.AbilityClasses;

import com.finderfeed.solarforge.capabilities.capability_mana.CapabilitySolarMana;
import com.finderfeed.solarforge.misc_things.RunicEnergy;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.Explosion;
import net.minecraft.server.level.ServerLevel;

public class LightningAbility extends AbstractAbility{
    public LightningAbility() {
        super("lightning",50,new RunicEnergyCostConstructor()
        .addRunicEnergy(RunicEnergy.Type.KELDA,400)
        .addRunicEnergy(RunicEnergy.Type.URBA,250));
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
                    LightningBolt entityBolt = new LightningBolt(EntityType.LIGHTNING_BOLT, world);
                    entityBolt.setPos(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
                    world.addFreshEntity(entityBolt);
                    world.explode(entity, pos.getX(), pos.getY(), pos.getZ(), 6, true, Explosion.BlockInteraction.BREAK);

                }else{
                    refund(entity);
                }

            }

        }

    }
}
