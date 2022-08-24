package com.finderfeed.solarforge.content.blocks.blockentities;

import com.finderfeed.solarforge.local_library.tile_entities.abstracts.ItemStackHandlerTile;
import com.finderfeed.solarforge.registries.tile_entities.SolarcraftTileEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.state.BlockState;

public class RunicTableTileEntity extends ItemStackHandlerTile {


//    private SimpleContainerData arr = new SimpleContainerData(6);


    public RunicTableTileEntity( BlockPos p_155630_, BlockState p_155631_) {
        super(SolarcraftTileEntityTypes.RUNIC_TABLE_TILE.get(), p_155630_, p_155631_);
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
    }
}
