package com.finderfeed.solarcraft.registries;

import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.pipeline.TextureTarget;
import net.minecraft.client.Minecraft;

import javax.annotation.Nullable;
import java.util.HashMap;

public class SCRenderTargets {

    private static final HashMap<String, RenderTarget> RENDER_TARGETS = new HashMap<>();


    public static RenderTarget BLOOM_OUT_TARGET;



    public static void init(int width, int height){
        BLOOM_OUT_TARGET = register("bloom_out_target",new TextureTarget(width,height,true, Minecraft.ON_OSX));
    }

    public static RenderTarget register(String name,RenderTarget target){
        RENDER_TARGETS.put("solarcraft:"+name,target);
        return target;
    }

    @Nullable
    public static RenderTarget getTarget(String name){
        return RENDER_TARGETS.get(name);
    }

}
