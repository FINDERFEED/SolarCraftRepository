package com.finderfeed.solarcraft.content.items.solar_wand;

import net.minecraft.world.item.ItemStack;

/**
 * You will be given an instance of this data in run method in WandAction
 * Change any fields in it, and they will be saved to an item.
 * If you are using it outside WandAction you will need to save it to item
 * manually by using your serializer singleton.
 */
public interface WandData<T extends WandData<T>> {

    WandDataSerializer<T> getSerializer();

}
