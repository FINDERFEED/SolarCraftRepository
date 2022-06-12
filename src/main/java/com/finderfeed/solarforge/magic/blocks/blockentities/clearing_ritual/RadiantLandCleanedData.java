package com.finderfeed.solarforge.magic.blocks.blockentities.clearing_ritual;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.saveddata.SavedData;

public class RadiantLandCleanedData extends SavedData {

    private boolean cleaned;

    public RadiantLandCleanedData(boolean cleaned){
        this.cleaned = cleaned;
    }

    @Override
    public CompoundTag save(CompoundTag tag) {
        tag.putBoolean("cleaned",cleaned);
        return tag;
    }

    public static RadiantLandCleanedData load(CompoundTag tag){
        return new RadiantLandCleanedData(tag.getBoolean("cleaned"));
    }

    public boolean isCleaned() {
        return cleaned;
    }

    public void setCleaned(boolean cleaned) {
        this.cleaned = cleaned;
    }
}
