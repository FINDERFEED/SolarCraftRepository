package com.finderfeed.solarcraft.registries;

import com.finderfeed.solarcraft.SolarCraft;
import com.finderfeed.solarcraft.local_library.bedrock_loader.animations.Animation;
import net.minecraft.resources.ResourceLocation;

public class SCBedrockAnimations {


    public static final Animation TEST = Animation.loadAnimation(new ResourceLocation(SolarCraft.MOD_ID,"animations/unknown.animation.json"),"animation.unknown.new");


    public static void init(){}

}
