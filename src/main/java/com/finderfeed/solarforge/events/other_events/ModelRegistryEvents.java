package com.finderfeed.solarforge.events.other_events;



import com.finderfeed.solarforge.model_loaders.SolarforgeModelLoader;
import com.finderfeed.solarforge.rendering.RadiantBlocksAtlasSprite;
import com.finderfeed.solarforge.rendering.RadiantTextureAtlasSpriteLoader;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.ModelLoaderRegistry;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;



@Mod.EventBusSubscriber(modid = "solarforge",bus = Mod.EventBusSubscriber.Bus.MOD,value = Dist.CLIENT)
public class ModelRegistryEvents {

    public static final ResourceLocation SOLAR_CORE_MODEL = new ResourceLocation("solarforge:objmodels/solar_core_modeljson");
    public static final ResourceLocation CRYSTAL_BOSS = new ResourceLocation("solarforge:objmodels/crystal_boss");

    public static final ResourceLocation SHIELDING_CRYSTAL = new ResourceLocation("solarforge:objmodels/crystal_boss_shielding_crystal");
    public static final ResourceLocation SHIELDING_CRYSTAL_SHIELD = new ResourceLocation("solarforge:objmodels/crystal_boss_shielding_crystal_shield");

    public static final ResourceLocation HOLE_MODEL = new ResourceLocation("solarforge:objmodels/hole");
    public static final ResourceLocation ANCIENT_FRAGMENT_MODEL = new ResourceLocation("solarforge:toloadmodels/ancient_fragment");
    public static final ResourceLocation ULDORADIUM_ORE = new ResourceLocation("solarforge:progression_ore_models/uldoradium_ore");


    public static final SolarforgeModelLoader LOADER = new SolarforgeModelLoader();


    @SubscribeEvent
    public static void registerModels(final ModelRegistryEvent event){
        ModelLoaderRegistry.registerLoader(new ResourceLocation("solarforge","progression_ore_loader"),LOADER);

        ModelLoader.addSpecialModel(CRYSTAL_BOSS);
        ModelLoader.addSpecialModel(SOLAR_CORE_MODEL);
        ModelLoader.addSpecialModel(ANCIENT_FRAGMENT_MODEL);
        ModelLoader.addSpecialModel(HOLE_MODEL);
        ModelLoader.addSpecialModel(SHIELDING_CRYSTAL_SHIELD);
        ModelLoader.addSpecialModel(SHIELDING_CRYSTAL);
    }
}
