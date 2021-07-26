package com.finderfeed.solarforge.world_generation.structures.blocks;

import com.finderfeed.solarforge.Helpers;
import com.finderfeed.solarforge.magic_items.items.solar_lexicon.achievements.Achievement;
import com.finderfeed.solarforge.registries.items.ItemsRegister;
import com.finderfeed.solarforge.registries.tile_entities.TileEntitiesRegistry;
import com.finderfeed.solarforge.world_generation.structures.blocks.tile_entities.ColdStarInfuserTile;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class ColdStarInfuser extends Block implements EntityBlock {
    public ColdStarInfuser(Properties p_i48440_1_) {
        super(p_i48440_1_);

    }


    @Override
    public InteractionResult use(BlockState p_225533_1_, Level p_225533_2_, BlockPos p_225533_3_, Player p_225533_4_, InteractionHand p_225533_5_, BlockHitResult p_225533_6_) {

        if (p_225533_4_.getItemInHand(p_225533_5_).getItem() == ItemsRegister.COLD_STAR_PIECE.get()){
            for (int i = 0; i < 96;i++){
                p_225533_2_.addParticle(ParticleTypes.FLAME,p_225533_3_.getX()+0.5f + Math.cos(Math.toRadians(i*30)),p_225533_3_.getY() + i * 0.5,p_225533_3_.getZ()+0.5f + Math.sin(Math.toRadians(i*30)),0,0,0);
            }
        }

        if (!p_225533_2_.isClientSide && Helpers.hasPlayerUnlocked(Achievement.ACQUIRE_COLD_STAR,p_225533_4_)){
            if (p_225533_4_.getItemInHand(p_225533_5_).getItem() == ItemsRegister.COLD_STAR_PIECE.get()){
                p_225533_4_.setItemInHand(p_225533_5_,new ItemStack(ItemsRegister.COLD_STAR_PIECE_ACTIVATED.get(),1));

                p_225533_2_.destroyBlock(p_225533_3_,false);

            }
        }


        return super.use(p_225533_1_, p_225533_2_, p_225533_3_, p_225533_4_, p_225533_5_, p_225533_6_);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return TileEntitiesRegistry.COLD_STAR_INFUSER.get().create(blockPos,blockState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level p_153212_, BlockState p_153213_, BlockEntityType<T> p_153214_) {
        return ((level, blockPos, blockState, t) -> {
            ColdStarInfuserTile.tick(level,blockPos,blockState,(ColdStarInfuserTile)t);
        });
    }
}
