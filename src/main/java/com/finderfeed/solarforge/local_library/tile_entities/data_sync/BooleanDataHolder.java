package com.finderfeed.solarforge.local_library.tile_entities.data_sync;

import net.minecraft.nbt.CompoundTag;

public class BooleanDataHolder extends TileEntitySyncedData<Boolean>{
    public BooleanDataHolder(String id,Boolean data) {
        super(id,data);
    }

    @Override
    public void writeToTag(CompoundTag tag) {
        tag.putBoolean(this.getSaveId(),this.getValue());
    }

    @Override
    public void loadFromTag(CompoundTag tag) {
        this.setValue(tag.getBoolean(this.getSaveId()));
    }

    @Override
    public boolean isEqual(Boolean value) {
        return this.getValue() == value;
    }
}
