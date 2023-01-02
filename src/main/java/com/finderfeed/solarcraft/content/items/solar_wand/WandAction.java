package com.finderfeed.solarcraft.content.items.solar_wand;

import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;

public interface WandAction<T extends WandData<T>> {

    InteractionResult run(WandUseContext context,WandData<T> data);

    /**
     * return EmptyWandData.SERIALIZER if action requires no data.
     */
    WandDataSerializer<T> getWandDataSerializer();

    WandActionType getActionType();

    Component getActionName();

}
