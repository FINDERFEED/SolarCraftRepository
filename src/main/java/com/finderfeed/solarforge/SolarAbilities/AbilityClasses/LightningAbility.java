package com.finderfeed.solarforge.SolarAbilities.AbilityClasses;

import com.finderfeed.solarforge.Helpers;
import com.finderfeed.solarforge.capabilities.capability_mana.CapabilitySolarMana;
import com.finderfeed.solarforge.entities.MyFallingBlockEntity;
import com.finderfeed.solarforge.misc_things.RunicEnergy;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.server.level.ServerLevel;

import java.util.Optional;

public class LightningAbility extends AbstractAbility{
    public LightningAbility() {
        super("lightning",50,new RunicEnergyCostConstructor()
        .addRunicEnergy(RunicEnergy.Type.KELDA,400)
        .addRunicEnergy(RunicEnergy.Type.URBA,250),30000);
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
                    Explosion explosion = new Explosion(world, null, null, StoneDestroyerCalculator.INSTANCE_01, pos.getX(), pos.getY()-3, pos.getZ(), 6, true, Explosion.BlockInteraction.BREAK);
                    spawnFallingBlocks(world,pos.below(3),explosion);

                    world.explode(entity,null,StoneDestroyerCalculator.INSTANCE_01, pos.getX(), pos.getY()-1, pos.getZ(), 6, true, Explosion.BlockInteraction.BREAK);
                    world.explode(entity,null,StoneDestroyerCalculator.INSTANCE_01, pos.getX(), pos.getY()-5, pos.getZ(), 4, true, Explosion.BlockInteraction.BREAK);
                }else{
                    if (!entity.isCreative()) {
                        refund(entity);
                    }
                }

            }

        }

    }


    public void spawnFallingBlocks(Level world,BlockPos mainpos,Explosion expl){
        Vec3 center = Helpers.getBlockCenter(mainpos);
        for (int i = 0;i < 20;i++){
            int rndX = world.random.nextInt(7)-3;
            int rndZ = world.random.nextInt(7)-3;
            int rndY = 0;

            double rndSpeed = world.random.nextDouble()*0.3+0.2;
            BlockPos offsettedPos = mainpos.offset(rndX,rndY,rndZ);
            Vec3 position = Helpers.getBlockCenter(offsettedPos);
            Vec3 speed = position.subtract(center).normalize().multiply(rndSpeed,0.5,rndSpeed).add(0,1,0);
            BlockState state = world.getBlockState(offsettedPos);
            if (state.getExplosionResistance(world,mainpos,expl) <= 6) {
                MyFallingBlockEntity fallingBlock = new MyFallingBlockEntity(world, position.x, position.y + 3, position.z, state);
                fallingBlock.setDeltaMovement(speed);
                world.addFreshEntity(fallingBlock);
            }
        }
    }
}


