package com.finderfeed.solarcraft.content.entities.projectiles;

import com.finderfeed.solarcraft.client.particles.SCParticleTypes;
import com.finderfeed.solarcraft.content.blocks.blockentities.TemporaryLightTile;
import com.finderfeed.solarcraft.content.blocks.primitive.TemporaryLightBlock;
import com.finderfeed.solarcraft.helpers.ClientHelpers;
import com.finderfeed.solarcraft.helpers.Helpers;
import com.finderfeed.solarcraft.registries.blocks.SolarcraftBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public class ThrownLightProjectile extends NormalProjectile{



    public ThrownLightProjectile(EntityType<? extends AbstractHurtingProjectile> p_36833_, Level p_36834_) {
        super(p_36833_, p_36834_);
    }

    public ThrownLightProjectile(EntityType<? extends AbstractHurtingProjectile> p_36817_, double p_36818_, double p_36819_, double p_36820_, double p_36821_, double p_36822_, double p_36823_, Level p_36824_) {
        super(p_36817_, p_36818_, p_36819_, p_36820_, p_36821_, p_36822_, p_36823_, p_36824_);
    }

    public ThrownLightProjectile(EntityType<? extends AbstractHurtingProjectile> p_36826_, LivingEntity p_36827_, double p_36828_, double p_36829_, double p_36830_, Level p_36831_) {
        super(p_36826_, p_36827_, p_36828_, p_36829_, p_36830_, p_36831_);
    }


    @Override
    public void tick() {
        super.tick();
        if (level.isClientSide){
            BlockPos pos = Helpers.vecToPos(this.position());
            BlockState state = level.getBlockState(pos);
            if (state.isAir() || state.getBlock() == SolarcraftBlocks.TEMPORARY_LIGHT.get()){
                level.setBlock(pos, TemporaryLightBlock.create(15),3);
                if (level.getBlockEntity(pos) instanceof TemporaryLightTile tile){
                    tile.setLivingTime(5);
                }
            }
            double rx = random.nextDouble() * 0.5 - 0.25f;
            double ry = random.nextDouble() * 0.5 - 0.25f;
            double rz = random.nextDouble() * 0.5 - 0.25f;
            Vec3 p = this.position().add(rx,ry,rz);
            ClientHelpers.Particles.createParticle(SCParticleTypes.SMALL_SOLAR_STRIKE_PARTICLE.get(),
                    p.x,p.y,p.z,0,0.05,0,random.nextInt(20) + 200,random.nextInt(20) + 200,20,
                    random.nextFloat() * 0.25f + 0.25f);
        }else{
            this.setDeltaMovement(this.getDeltaMovement().add(0,-0.05,0));
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult res) {
        super.onHitBlock(res);
        if (!level.isClientSide) {
            BlockPos pos = new BlockPos(Helpers.vecToPos(this.position()));
            BlockState state = level.getBlockState(pos);
            if (state.isAir() || state.getBlock() == SolarcraftBlocks.TEMPORARY_LIGHT.get()){
                level.setBlock(pos,SolarcraftBlocks.THROWN_LIGHT.get().defaultBlockState(),3);
            }
        }
        BlockState s = level.getBlockState(res.getBlockPos());
        if (s.isAir() || s.is(SolarcraftBlocks.TEMPORARY_LIGHT.get())) return;
        this.remove(RemovalReason.KILLED);
    }


}
