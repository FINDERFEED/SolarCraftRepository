package com.finderfeed.solarforge.AbilityClasses;

import com.finderfeed.solarforge.SolarAbilities.MeteoriteProjectile;
import com.finderfeed.solarforge.capabilities.capability_mana.CapabilitySolarMana;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.server.ServerWorld;

public class MeteoriteAbility extends AbstractAbility{
    public MeteoriteAbility(String id, int manacost) {
        super(id, manacost);
    }

    @Override
    public void cast(ServerPlayerEntity entity, ServerWorld world) {
        if (entity.getPersistentData().getBoolean("solar_forge_can_player_use_"+id) &&
                (CapabilitySolarMana.getSolarMana(entity).orElseThrow(RuntimeException::new).getMana() > manacost || entity.isCreative())) {

            Vector3d vec = entity.getLookAngle().multiply(200, 200, 200);

            RayTraceContext ctx = new RayTraceContext(entity.position().add(0, 1.5, 0), entity.position().add(0, 1.5, 0).add(vec), RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, entity);
            BlockRayTraceResult result = world.clip(ctx);

            if (result.getType() == RayTraceResult.Type.BLOCK) {

                BlockPos pos = result.getBlockPos();
                if (world.canSeeSky(pos.above())) {
                    MeteoriteProjectile proj = new MeteoriteProjectile(entity, world);
                    Vector3f posVect = new Vector3f((float) entity.position().x - (float) entity.getLookAngle().x * 20, (float) entity.position().y + 64, (float) entity.position().z - (float) entity.getLookAngle().z * 20);
                    proj.setPos(posVect.x(), posVect.y(), posVect.z());
                    Vector3d velocity = new Vector3d(pos.offset(0.5, 0, 0.5).getX() - posVect.x(), pos.offset(0.5, 0, 0.5).getY() - posVect.y(), pos.offset(0.5, 0, 0.5).getZ() - posVect.z());
                    proj.setDeltaMovement(velocity.normalize());
                    world.addFreshEntity(proj);
                }

            }
            super.cast(entity, world);
        }

    }
}
