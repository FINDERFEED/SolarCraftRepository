package com.finderfeed.solarforge.events.other_events;



import com.finderfeed.solarforge.SolarForge;
import com.finderfeed.solarforge.client.model_loaders.SolarforgeModelLoader;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraftforge.client.event.ModelRegistryEvent;

import net.minecraftforge.client.model.ForgeModelBakery;
import net.minecraftforge.client.model.ModelLoaderRegistry;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;



@Mod.EventBusSubscriber(modid = "solarforge",bus = Mod.EventBusSubscriber.Bus.MOD,value = Dist.CLIENT)
public class OBJModels {

    public static final ResourceLocation CRYSTAL_1 = new ResourceLocation(SolarForge.MOD_ID,"objmodels/crystal_1");
    public static final ResourceLocation EXPLOSIVE_CRYSTAL = new ResourceLocation(SolarForge.MOD_ID,"objmodels/explosive_crystal");

    public static final ResourceLocation SOLAR_CORE_MODEL = new ResourceLocation("solarforge:objmodels/solar_core_modeljson");
    public static final ResourceLocation CRYSTAL_BOSS = new ResourceLocation("solarforge:objmodels/crystal_boss");

    public static final ResourceLocation SHIELDING_CRYSTAL = new ResourceLocation("solarforge:objmodels/crystal_boss_shielding_crystal");
    public static final ResourceLocation SHIELDING_CRYSTAL_SHIELD = new ResourceLocation("solarforge:objmodels/crystal_boss_shielding_crystal_shield");
    public static final ResourceLocation GET_OFF_MEEE = new ResourceLocation("solarforge:objmodels/crystal_boss_getoffme");
    public static final ResourceLocation RIP_RAY_GENERATOR = new ResourceLocation("solarforge:objmodels/rip_ray_generator");

    public static final ResourceLocation HOLE_MODEL = new ResourceLocation("solarforge:objmodels/hole");
    public static final ResourceLocation ANCIENT_FRAGMENT_MODEL = new ResourceLocation("solarforge:toloadmodels/ancient_fragment");
    public static final ResourceLocation ULDORADIUM_ORE = new ResourceLocation("solarforge:progression_ore_models/uldoradium_ore");


    public static final SolarforgeModelLoader LOADER = new SolarforgeModelLoader();


    @SubscribeEvent
    public static void registerModels(final ModelRegistryEvent event){
        ModelLoaderRegistry.registerLoader(new ResourceLocation(SolarForge.MOD_ID,"progression_ore_loader"),LOADER);
        ForgeModelBakery.addSpecialModel(CRYSTAL_BOSS);
        ForgeModelBakery.addSpecialModel(SOLAR_CORE_MODEL);
        ForgeModelBakery.addSpecialModel(ANCIENT_FRAGMENT_MODEL);
        ForgeModelBakery.addSpecialModel(HOLE_MODEL);
        ForgeModelBakery.addSpecialModel(SHIELDING_CRYSTAL_SHIELD);
        ForgeModelBakery.addSpecialModel(SHIELDING_CRYSTAL);
        ForgeModelBakery.addSpecialModel(GET_OFF_MEEE);
        ForgeModelBakery.addSpecialModel(RIP_RAY_GENERATOR);
        ForgeModelBakery.addSpecialModel(CRYSTAL_1);
        ForgeModelBakery.addSpecialModel(EXPLOSIVE_CRYSTAL);
    }
}
