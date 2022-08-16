package com.finderfeed.solarforge.abilities.ability_classes;

import com.finderfeed.solarforge.Helpers;
import com.finderfeed.solarforge.abilities.AbilityHelper;
import com.finderfeed.solarforge.entities.not_alive.MyFallingBlockEntity;
import com.finderfeed.solarforge.magic.items.runic_energy.RunicEnergyCost;
import com.finderfeed.solarforge.misc_things.RunicEnergy;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.server.level.ServerLevel;

public class LightningAbility extends AbstractAbility{
    public LightningAbility() {
        super("lightning",new RunicEnergyCost()
        .set(RunicEnergy.Type.KELDA,800)
        .set(RunicEnergy.Type.URBA,250),30000);
    }

    @Override
    public void cast(ServerPlayer entity, ServerLevel world) {
        if (AbilityHelper.isAbilityUsable(entity,this,true)) {

            Vec3 vec = entity.getLookAngle().multiply(200, 200, 200);
            ClipContext ctx = new ClipContext(entity.position().add(0, 1.5, 0), entity.position().add(0, 1.5, 0).add(vec), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, entity);
            BlockHitResult result = world.clip(ctx);

            if (result.getType() == HitResult.Type.BLOCK) {
                BlockPos pos = result.getBlockPos();
                if (world.canSeeSky(pos.above())) {
                    LightningBolt entityBolt = new LightningBolt(EntityType.LIGHTNING_BOLT, world);
                    entityBolt.setPos(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);

                    Explosion explosion = new Explosion(world, null, null, StoneDestroyerCalculator.INSTANCE_01, pos.getX(), pos.getY()-3, pos.getZ(), 6, true, Explosion.BlockInteraction.BREAK);
                    spawnFallingBlocks(world,pos.below(3),explosion);

                    world.explode(entity,null,StoneDestroyerCalculator.INSTANCE_01, pos.getX(), pos.getY()-1, pos.getZ(), 6, true, Explosion.BlockInteraction.BREAK);
                    world.explode(entity,null,StoneDestroyerCalculator.INSTANCE_01, pos.getX(), pos.getY()-5, pos.getZ(), 4, true, Explosion.BlockInteraction.BREAK);
                    AbilityHelper.spendAbilityEnergy(entity,this);
                    world.addFreshEntity(entityBolt);
                }/*else{*/
//                    if (!entity.isCreative()) {
//                        AbilityHelper.refundEnergy(entity,this);
//                    }
//                }

            }

        }

    }


    public void spawnFallingBlocks(Level world,BlockPos mainpos,Explosion expl){
        Vec3 center = Helpers.getBlockCenter(mainpos);

        for (int x = -2; x <= 2;x++){
            for (int y = -2; y <= 2;y++){
                for (int z = -2; z <= 2;z++){
                    BlockPos offsettedPos = mainpos.offset(x,y,z);
                    Vec3 position = Helpers.getBlockCenter(offsettedPos);
                    Vec3 speed = position.subtract(center).multiply(0.3*world.random.nextDouble(),0.1*world.random.nextDouble(),0.3*world.random.nextDouble()).add(0,1.5,0);
                    BlockState state = world.getBlockState(offsettedPos);
                    if (state.getExplosionResistance(world,mainpos,expl) <= 6) {
                        MyFallingBlockEntity fallingBlock = new MyFallingBlockEntity(world, position.x, position.y + 3, position.z, state);
                        fallingBlock.setDeltaMovement(speed);
                        world.addFreshEntity(fallingBlock);
                    }
                }
            }
        }

    }
}


