package com.finderfeed.solarcraft.content.world_generation.structures.blocks;

import com.finderfeed.solarcraft.content.world_generation.structures.blocks.tile_entities.KeyLockStructureTile;
import com.finderfeed.solarcraft.registries.items.SCItems;
import com.finderfeed.solarcraft.registries.tile_entities.SCTileEntities;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.GlazedTerracottaBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class KeyLockBlock extends GlazedTerracottaBlock implements EntityBlock {

    public KeyLockBlock() {
        super(BlockBehaviour.Properties.copy(Blocks.BEDROCK).pushReaction(PushReaction.IGNORE));
    }


    @Override
    public InteractionResult use(BlockState p_225533_1_, Level p_225533_2_, BlockPos p_225533_3_, Player p_225533_4_, InteractionHand p_225533_5_, BlockHitResult p_225533_6_) {

        if (!p_225533_2_.isClientSide){
            if (p_225533_4_.getItemInHand(p_225533_5_).getItem() == SCItems.SOLAR_KEY.get()){
                p_225533_2_.destroyBlock(p_225533_3_,false);
                p_225533_2_.destroyBlock(p_225533_3_.below(),false);
                p_225533_2_.destroyBlock(p_225533_3_.above(),false);
                p_225533_4_.setItemInHand(p_225533_5_, Items.AIR.getDefaultInstance());
            }
        }

        return super.use(p_225533_1_, p_225533_2_, p_225533_3_, p_225533_4_, p_225533_5_, p_225533_6_);
    }

//    @Override
//    public PushReaction getPistonPushReaction(BlockState p_149656_1_) {
//        return PushReaction.IGNORE;
//    }


    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return SCTileEntities.KEY_LOCK_TILE.get().create(blockPos,blockState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level p_153212_, BlockState p_153213_, BlockEntityType<T> p_153214_) {
        return ((level, blockPos, blockState, t) -> {
            KeyLockStructureTile.tick(level,blockPos,blockState,(KeyLockStructureTile)t);
        });
    }
}
