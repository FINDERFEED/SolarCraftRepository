package com.finderfeed.solarforge.world_generation.structures.blocks;

import com.finderfeed.solarforge.registries.items.ItemsRegister;
import com.finderfeed.solarforge.registries.tile_entities.TileEntitiesRegistry;
import net.minecraft.block.*;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.GlazedTerracottaBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class KeyLockBlock extends GlazedTerracottaBlock  {

    public KeyLockBlock() {
        super(BlockBehaviour.Properties.copy(Blocks.BEDROCK));
    }


    @Override
    public InteractionResult use(BlockState p_225533_1_, Level p_225533_2_, BlockPos p_225533_3_, Player p_225533_4_, InteractionHand p_225533_5_, BlockHitResult p_225533_6_) {

        if (!p_225533_2_.isClientSide){
            if (p_225533_4_.getItemInHand(p_225533_5_).getItem() == ItemsRegister.SOLAR_KEY.get()){
                p_225533_2_.destroyBlock(p_225533_3_,false);
                p_225533_2_.destroyBlock(p_225533_3_.below(),false);
                p_225533_2_.destroyBlock(p_225533_3_.above(),false);
                p_225533_4_.setItemInHand(p_225533_5_, Items.AIR.getDefaultInstance());
            }
        }

        return super.use(p_225533_1_, p_225533_2_, p_225533_3_, p_225533_4_, p_225533_5_, p_225533_6_);
    }

    @Override
    public PushReaction getPistonPushReaction(BlockState p_149656_1_) {
        return PushReaction.IGNORE;
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public BlockEntity createTileEntity(BlockState state, BlockGetter world) {
        return TileEntitiesRegistry.KEY_LOCK_TILE.get().create();
    }
}
