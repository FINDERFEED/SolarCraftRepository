package com.finderfeed.solarforge.local_library.tile_entities.data_sync;

import net.minecraft.nbt.CompoundTag;

import java.util.ArrayList;
import java.util.List;

//maybe i will finish it sometime
public class TileEntityDataHolder {

    private List<TileEntitySyncedData<?>> DATA_SYNCERS;

    public TileEntityDataHolder(){
        this.DATA_SYNCERS = new ArrayList<>();
    }


    public CompoundTag writeToTag(boolean checkIfChanged){
        CompoundTag tag = new CompoundTag();
        for (TileEntitySyncedData<?> data : DATA_SYNCERS){
            if (checkIfChanged && data.hasChanged()){
                data.writeToTag(tag);
            }
            data.setChanged(false);
        }
        return tag;
    }

    public void loadFromTag(CompoundTag tag){
        for (TileEntitySyncedData<?> data : DATA_SYNCERS){
            data.loadFromTag(tag);
        }
    }

    public void registerSyncedData(TileEntitySyncedData<?> dataSyncer){
        if (!DATA_SYNCERS.contains(dataSyncer)){
            DATA_SYNCERS.add(dataSyncer);
        }
    }


}
