package com.finderfeed.solarcraft.misc_things;

import com.finderfeed.solarcraft.SolarCraftTags;
import com.finderfeed.solarcraft.content.blocks.blockentities.RuneEnergyPylonTile;
import net.minecraft.world.entity.item.ItemEntity;

public interface IImbuableItem {

    double getCost();

    int getImbueTime();

    boolean imbue(ItemEntity entity, RuneEnergyPylonTile tile);

    default int getCurrentTime(ItemEntity item){
        return item.getPersistentData().getInt(SolarCraftTags.IMBUE_TIME_TAG);
    }

    default void setCurrentTime(ItemEntity item,int time){
        item.getPersistentData().putInt(SolarCraftTags.IMBUE_TIME_TAG,time);
    }
}
