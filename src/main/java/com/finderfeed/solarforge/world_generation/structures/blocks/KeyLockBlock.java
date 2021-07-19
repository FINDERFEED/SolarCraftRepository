package com.finderfeed.solarforge.world_generation.structures.blocks;

import com.finderfeed.solarforge.registries.items.ItemsRegister;
import com.finderfeed.solarforge.registries.tile_entities.TileEntitiesRegistry;
import net.minecraft.block.*;
import net.minecraft.block.material.PushReaction;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class KeyLockBlock extends GlazedTerracottaBlock  {

    public KeyLockBlock() {
        super(AbstractBlock.Properties.copy(Blocks.BEDROCK));
    }


    @Override
    public ActionResultType use(BlockState p_225533_1_, World p_225533_2_, BlockPos p_225533_3_, PlayerEntity p_225533_4_, Hand p_225533_5_, BlockRayTraceResult p_225533_6_) {

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
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return TileEntitiesRegistry.KEY_LOCK_TILE.get().create();
    }
}
