package com.finderfeed.solarforge.registries.shader_registry;


import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterShadersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "solarforge",bus = Mod.EventBusSubscriber.Bus.MOD,value = Dist.CLIENT)
public class CoreShaderRegistry {



    @SubscribeEvent
    public static void register(RegisterShadersEvent event){
        //register wave shader here
    }

}
