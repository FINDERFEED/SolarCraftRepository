package com.finderfeed.solarforge.magic.blocks.blockentities;

import com.finderfeed.solarforge.ClientHelpers;
import com.finderfeed.solarforge.Helpers;
import com.finderfeed.solarforge.client.particles.ParticleTypesRegistry;
import com.finderfeed.solarforge.local_library.helpers.FinderfeedMathHelper;
import com.finderfeed.solarforge.magic.blocks.blockentities.projectiles.ShadowBolt;
import com.finderfeed.solarforge.registries.entities.EntityTypes;
import com.finderfeed.solarforge.registries.tile_entities.TileEntitiesRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class UlderaPylon extends BlockEntity {


    public UlderaPylon(BlockPos p_155229_, BlockState p_155230_) {
        super(TileEntitiesRegistry.ULDERA_PYLON.get(), p_155229_, p_155230_);
    }

    public static void tick(UlderaPylon tile, BlockPos pos, BlockState state, Level world){
        if (!world.isClientSide){
            if (world.getGameTime() % 20 == 0){
                AABB aabb = new AABB(-20,-20,-20,20,20,20).move(Helpers.getBlockCenter(pos));
                Vec3 p = Helpers.getBlockCenter(pos);

                List<LivingEntity> livings = world.getEntitiesOfClass(LivingEntity.class,aabb,(e)->{
                    if (e.isDeadOrDying()) return false;
                    if (e instanceof Player player){
                        if (player.isCreative() || player.isSpectator()){
                            return false;
                        }
                    }
                    if (!(e.distanceToSqr(p) <= 20 * 20)){
                        return false;
                    }
                    Vec3 v = e.position().add(0,e.getBbHeight()/2,0).subtract(p).normalize().multiply(0.5,0.5,0.5);
                    ClipContext clipContext = new ClipContext(p.add(v),e.position().add(0,e.getBbHeight()/2,0), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE,null);
                    return world.clip(clipContext).getType() == HitResult.Type.MISS;
                });
                if (!livings.isEmpty()) {
                    LivingEntity target = livings.get(world.random.nextInt(livings.size()));
                    Vec3 ePos = target.position().add(0,target.getBbHeight()/2,0);
                    Vec3 between = ePos.subtract(p);
                    ShadowBolt bolt = new ShadowBolt(EntityTypes.SHADOW_BOLT.get(),world);
                    bolt.setPos(p.add(between.normalize().multiply(0.5,0.5,0.5)));
                    bolt.setDeltaMovement(between.normalize().multiply(2,2,2));
                    world.addFreshEntity(bolt);
                }
            }
        }else{
            if (world.getGameTime() % 4 == 0) {
                Vec3 p = Helpers.getBlockCenter(pos).add(Helpers.randomVector().multiply(0.5,0.5,0.5));
                ClientHelpers.ParticleAnimationHelper.createParticle(ParticleTypesRegistry.SMALL_SOLAR_STRIKE_PARTICLE.get(),
                        p.x, p.y, p.z, 0, 0.05, 0, () -> 50, () -> 0, () -> 130, 0.4f);
            }
        }
    }


}
