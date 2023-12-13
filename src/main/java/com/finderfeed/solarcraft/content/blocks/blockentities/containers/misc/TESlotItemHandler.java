package com.finderfeed.solarcraft.content.blocks.blockentities.containers.misc;

import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.SlotItemHandler;

public class TESlotItemHandler extends SlotItemHandler {

    private BlockEntity tile;

    public TESlotItemHandler(BlockEntity tile,IItemHandler itemHandler, int index, int xPosition, int yPosition) {
        super(itemHandler, index, xPosition, yPosition);
        this.tile = tile;
    }


    public BlockEntity getTile() {
        return tile;
    }

    @Override
    public void setChanged() {
        super.setChanged();
        this.tile.setChanged();
    }
}
