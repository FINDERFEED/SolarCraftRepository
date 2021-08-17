package com.finderfeed.solarforge.registries.dimension_related;


import com.finderfeed.solarforge.world_generation.dimension_related.radiant_land.RadiantLandDimEffects;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = "solarforge",bus = Mod.EventBusSubscriber.Bus.MOD,value = Dist.CLIENT)
public class ClientRegistry {

    public static final DimensionSpecialEffects RADIANT_LAND  = new RadiantLandDimEffects();

    @SubscribeEvent
    public static void registerRendering(FMLClientSetupEvent event){
        event.enqueueWork(()->{
            DimensionSpecialEffects.EFFECTS.put(new ResourceLocation("solarforge","radiant_land"),RADIANT_LAND);
        });
    }
}
