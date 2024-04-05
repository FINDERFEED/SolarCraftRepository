package com.finderfeed.solarcraft.content.blocks.blockentities.clearing_ritual;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.world.entity.raid.Raids;
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

    public static SavedData.Factory<RadiantLandCleanedData> factory(boolean cleaned) {
        return new SavedData.Factory<>(() -> new RadiantLandCleanedData(cleaned), RadiantLandCleanedData::load, DataFixTypes.LEVEL);
    }

    public boolean isCleaned() {
        return cleaned;
    }

    public void setCleaned(boolean cleaned) {
        this.cleaned = cleaned;
    }
}
