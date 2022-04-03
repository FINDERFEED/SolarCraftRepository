package com.finderfeed.solarforge.registries.shader_registry;


import com.finderfeed.solarforge.SolarForge;
import com.finderfeed.solarforge.client.rendering.CoreShaders;
import com.finderfeed.solarforge.client.rendering.rendertypes.RadiantPortalRendertype;
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
        event.registerShader(new ShaderInstance(event.getResourceManager(),new ResourceLocation("solarforge","aoe_gun_projectile"), DefaultVertexFormat.POSITION),
                (instance)->{
                    CoreShaders.AOE_GUN_PROJECTILE_SHADER = instance;
        });
        event.registerShader(new ShaderInstance(event.getResourceManager(),new ResourceLocation(SolarForge.MOD_ID,"runic_energy_flow"), DefaultVertexFormat.POSITION),
                (instance)->{
                    CoreShaders.RUNIC_ENERGY_FLOW_SHADER = instance;
                });
    }

}
