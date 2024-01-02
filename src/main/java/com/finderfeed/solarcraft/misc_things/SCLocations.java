package com.finderfeed.solarcraft.misc_things;

import com.finderfeed.solarcraft.SolarCraft;
import net.minecraft.resources.ResourceLocation;

public class SCLocations {

    public static final ResourceLocation NEXT_PAGE = create("textures/gui/widgets/next_page.png");
    public static final ResourceLocation PREV_PAGE = create("textures/gui/widgets/previous_page.png");


    public static ResourceLocation create(String path){
        return new ResourceLocation(SolarCraft.MOD_ID,path);
    }

}
