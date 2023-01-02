package com.finderfeed.solarcraft.registries.wand_actions;

import com.finderfeed.solarcraft.SolarCraft;
import com.finderfeed.solarcraft.content.items.solar_wand.SolarWandItem;
import com.finderfeed.solarcraft.content.items.solar_wand.WandAction;
import com.finderfeed.solarcraft.content.items.solar_wand.wand_actions.DefaultWandBlockUseAction;
import net.minecraft.resources.ResourceLocation;

public class SolarCraftWandActionRegistry {


    public static final DefaultWandBlockUseAction ON_BLOCK_USE = register(new ResourceLocation(SolarCraft.MOD_ID,"on_block_use"),new DefaultWandBlockUseAction());



    public static <T extends WandAction<?>> T register(ResourceLocation location, T action){
        SolarWandItem.registerWandAction(location,action);
        return action;
    }

    public static void init(){}

}
