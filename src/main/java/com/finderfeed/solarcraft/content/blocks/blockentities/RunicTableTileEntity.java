package com.finderfeed.solarcraft.content.blocks.blockentities;

import com.finderfeed.solarcraft.registries.SCAttachmentTypes;
import com.finderfeed.solarcraft.registries.tile_entities.SCTileEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.items.ItemStackHandler;

import java.util.function.Supplier;

public class RunicTableTileEntity extends ItemStackHandlerTile {


//    private SimpleContainerData arr = new SimpleContainerData(6);


    public RunicTableTileEntity( BlockPos p_155630_, BlockState p_155631_) {
        super(SCTileEntities.RUNIC_TABLE_TILE.get(), p_155630_, p_155631_);
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
    }

    @Override
    public Supplier<AttachmentType<ItemStackHandler>> getAttachmentType() {
        return SCAttachmentTypes.INVENTORY_9;
    }
}
