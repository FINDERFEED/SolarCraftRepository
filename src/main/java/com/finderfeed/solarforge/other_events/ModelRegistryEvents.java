package com.finderfeed.solarforge.other_events;



import com.finderfeed.solarforge.model_loaders.SolarforgeModelLoader;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "solarforge",bus = Mod.EventBusSubscriber.Bus.MOD,value = Dist.CLIENT)
public class ModelRegistryEvents {

    public static final ResourceLocation SOLAR_CORE_MODEL = new ResourceLocation("solarforge:objmodels/solar_core_modeljson");
    public static final ResourceLocation ANCIENT_FRAGMENT_MODEL = new ResourceLocation("solarforge:toloadmodels/ancient_fragment");
    public static final ResourceLocation ULDORADIUM_ORE = new ResourceLocation("solarforge:progression_ore_models/uldoradium_ore");
    public static final SolarforgeModelLoader LOADER = new SolarforgeModelLoader();


    @SubscribeEvent
    public static void registerModels(final ModelRegistryEvent event){
        ModelLoaderRegistry.registerLoader(new ResourceLocation("solarforge","progression_ore_loader"),LOADER);

        ModelLoader.addSpecialModel(SOLAR_CORE_MODEL);
        ModelLoader.addSpecialModel(ANCIENT_FRAGMENT_MODEL);
    }
}
