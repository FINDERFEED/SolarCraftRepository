package com.finderfeed.solarcraft.content.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class SpeedRoadBlock extends Block {


    public SpeedRoadBlock(Properties p_49795_) {
        super(p_49795_);
    }


    @Override
    public void stepOn(Level world, BlockPos pos, BlockState state, Entity entity) {
        if (!world.isClientSide) {
            if (entity instanceof Player pl) {
                if(!pl.hasEffect(MobEffects.MOVEMENT_SPEED)){
                    pl.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED,200,0));
                }else{
                    MobEffectInstance i = pl.getEffect(MobEffects.MOVEMENT_SPEED);
                    if ((i.getAmplifier() <= 0) && (i.getDuration() <= 10) ){
                        pl.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED,200,0));
                    }
                }
            }
        }
        super.stepOn(world, pos, state, entity);
    }
}
