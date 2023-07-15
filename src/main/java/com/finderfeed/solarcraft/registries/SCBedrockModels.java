package com.finderfeed.solarcraft.registries;

import com.finderfeed.solarcraft.SolarCraft;
import com.finderfeed.solarcraft.local_library.bedrock_loader.model_components.FDModelInfo;
import net.minecraft.resources.ResourceLocation;

public class SCBedrockModels {

    public static final FDModelInfo TEST_MODEL = new FDModelInfo(new ResourceLocation(SolarCraft.MOD_ID,"models/bedrock/model.geo.json"),1f);
    public static final FDModelInfo TEST_MODEL2 = new FDModelInfo(new ResourceLocation(SolarCraft.MOD_ID,"models/bedrock/model.geo.json"),1.5f);
    public static final FDModelInfo TEST_MODEL3 = new FDModelInfo(new ResourceLocation(SolarCraft.MOD_ID,"models/bedrock/model.geo.json"),0.5f);



    public static void init(){}
}
