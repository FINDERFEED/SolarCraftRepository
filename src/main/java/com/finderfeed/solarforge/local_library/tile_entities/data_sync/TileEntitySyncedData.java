package com.finderfeed.solarforge.local_library.tile_entities.data_sync;

import net.minecraft.nbt.CompoundTag;

public abstract class TileEntitySyncedData<T> {

    private T data;
    private boolean changed = false;
    private String id;
    public TileEntitySyncedData(String id,T data){
        this.data = data;
    }

    public abstract void writeToTag(CompoundTag tag);

    public abstract void loadFromTag(CompoundTag tag);

    public abstract boolean isEqual(T value);

    public final void setValue(T data){
        if (!isEqual(data)) {
            this.data = data;
            this.changed = true;
        }
    }

    public final T getValue(){
        return data;
    }

    public boolean hasChanged() {
        return changed;
    }

    public String getSaveId() {
        return id;
    }

    public void setChanged(boolean changed) {
        this.changed = changed;
    }
}
