package com.finderfeed.solarforge.magic.blocks.blockentities;

import com.finderfeed.solarforge.registries.tile_entities.TileEntitiesRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class RunicTableTileEntity extends BlockEntity {


//    private SimpleContainerData arr = new SimpleContainerData(6);


    public RunicTableTileEntity( BlockPos p_155630_, BlockState p_155631_) {
        super(TileEntitiesRegistry.RUNIC_TABLE_TILE.get(), p_155630_, p_155631_);
    }


    public IItemHandler getInventory(){
        IItemHandler handler = this.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElse(null);
        return handler;
    }



}
