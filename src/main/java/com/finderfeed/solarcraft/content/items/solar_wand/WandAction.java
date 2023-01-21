package com.finderfeed.solarcraft.content.items.solar_wand;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public interface WandAction<T extends WandData<T>> {

    InteractionResult run(WandUseContext context,T data);

    default InteractionResult hackyRun(WandUseContext context,WandData<?> data){
        return this.run(context,(T)data);
    }

    /**
     * return EmptyWandData.SERIALIZER if action requires no data.
     */
    WandDataSerializer<T> getWandDataSerializer();

    WandActionType getActionType(Player player);

    Component getActionDescription();

    ResourceLocation getRegistryName();

    ItemStack getIcon();

}
