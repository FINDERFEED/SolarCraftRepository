package com.finderfeed.solarforge.registries.shader_registry;


import com.finderfeed.solarforge.magic_items.blocks.render.RadiantPortalTileRender;
import com.finderfeed.solarforge.rendering.rendertypes.RadiantPortalRendertype;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterShadersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.io.IOException;

@Mod.EventBusSubscriber(modid = "solarforge",bus = Mod.EventBusSubscriber.Bus.MOD,value = Dist.CLIENT)
public class CoreShaderRegistry {



    @SubscribeEvent
    public static void register(RegisterShadersEvent event) throws IOException {
        event.registerShader(new ShaderInstance(event.getResourceManager(),new ResourceLocation("solarforge","water"), DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP),
                (instance)->{
            RadiantPortalRendertype.WATER_SHADER = instance;
        });
        event.registerShader(new ShaderInstance(event.getResourceManager(),new ResourceLocation("solarforge","ray"), DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP),
                (instance)->{
                    RadiantPortalRendertype.RAY_SHADER = instance;
                });
    }

}
