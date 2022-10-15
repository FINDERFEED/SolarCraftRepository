package com.finderfeed.solarcraft.content.blocks.blockentities;

import com.finderfeed.solarcraft.registries.tile_entities.SolarcraftTileEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class TemporaryLightTile extends BlockEntity {

    private int livingTime = 20;

    public TemporaryLightTile( BlockPos p_155229_, BlockState p_155230_) {
        super(SolarcraftTileEntityTypes.TEMPORARY_LIGHT.get(), p_155229_, p_155230_);
    }


    public static void tick(TemporaryLightTile tile, BlockState state, BlockPos pos, Level world){
        if (world.isClientSide){
            if (tile.livingTime-- <= 0){
                world.removeBlock(pos,false);
            }
        }else{
            world.removeBlock(pos,false);
        }
    }

    public void setLivingTime(int livingTime) {
        this.livingTime = livingTime;
    }
}
