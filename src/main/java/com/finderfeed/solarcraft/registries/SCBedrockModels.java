package com.finderfeed.solarcraft.registries;

import com.finderfeed.solarcraft.SolarCraft;
import com.finderfeed.solarcraft.local_library.bedrock_loader.model_components.FDModelInfo;
import net.minecraft.resources.ResourceLocation;

public class SCBedrockModels {

    public static final FDModelInfo ULDERA_CRYSTAL = new FDModelInfo(new ResourceLocation(SolarCraft.MOD_ID,"models/bedrock/uldera_crystal.geo.json"),1f);
    public static final FDModelInfo EFFECT_CRYSTAL = new FDModelInfo(new ResourceLocation(SolarCraft.MOD_ID,"models/bedrock/effect_crystal.geo.json"),1f);


    public static void init(){}
}
