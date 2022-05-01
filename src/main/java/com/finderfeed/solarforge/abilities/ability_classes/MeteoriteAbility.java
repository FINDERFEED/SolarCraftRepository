package com.finderfeed.solarforge.abilities.ability_classes;

import com.finderfeed.solarforge.abilities.AbilityHelper;
import com.finderfeed.solarforge.abilities.meteorite.MeteoriteProjectile;
import com.finderfeed.solarforge.magic.items.runic_energy.RunicEnergyCost;
import com.finderfeed.solarforge.misc_things.RunicEnergy;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import com.mojang.math.Vector3f;
import net.minecraft.server.level.ServerLevel;

public class MeteoriteAbility extends AbstractAbility{
    public MeteoriteAbility() {
        super("meteorite",new RunicEnergyCost()
        .set(RunicEnergy.Type.ZETA,500)
        .set(RunicEnergy.Type.KELDA,300),50000);
    }

    @Override
    public void cast(ServerPlayer entity, ServerLevel world) {
        if (AbilityHelper.isAbilityUsable(entity,this)) {
            Vec3 vec = entity.getLookAngle().multiply(200, 200, 200);

            ClipContext ctx = new ClipContext(entity.position().add(0, 1.5, 0), entity.position().add(0, 1.5, 0).add(vec), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, entity);
            BlockHitResult result = world.clip(ctx);

            if (result.getType() == HitResult.Type.BLOCK) {

                BlockPos pos = result.getBlockPos();
                if (world.canSeeSky(pos.above())) {
                    MeteoriteProjectile proj = new MeteoriteProjectile(entity, world);
                    Vector3f posVect = new Vector3f((float) entity.position().x - (float) entity.getLookAngle().x * 20, (float) entity.position().y + 64, (float) entity.position().z - (float) entity.getLookAngle().z * 20);
                    proj.setPos(posVect.x(), posVect.y(), posVect.z());
                    Vec3 velocity = new Vec3(pos.offset(0.5, 0, 0.5).getX() - posVect.x(), pos.offset(0.5, 0, 0.5).getY() - posVect.y(), pos.offset(0.5, 0, 0.5).getZ() - posVect.z());
                    proj.setDeltaMovement(velocity.normalize());
                    world.addFreshEntity(proj);
                }else{
                    if (!entity.isCreative()) {
                        AbilityHelper.refundEnergy(entity,this);
                    }
                }

            }

        }

    }
}
