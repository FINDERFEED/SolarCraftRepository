package com.finderfeed.solarcraft.events.other_events;



import com.finderfeed.solarcraft.SolarCraft;
import com.finderfeed.solarcraft.client.model_loaders.SolarforgeModelLoader;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;


import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;



@Mod.EventBusSubscriber(modid = "solarcraft",bus = Mod.EventBusSubscriber.Bus.MOD,value = Dist.CLIENT)
public class OBJModels {

    public static final ResourceLocation CRYSTAL_1 = new ResourceLocation(SolarCraft.MOD_ID,"objmodels/crystal_1");
    public static final ResourceLocation EXPLOSIVE_CRYSTAL = new ResourceLocation(SolarCraft.MOD_ID,"objmodels/explosive_crystal");

    public static final ResourceLocation SOLAR_CORE_MODEL = new ResourceLocation("solarcraft:objmodels/solar_core_modeljson");
    public static final ResourceLocation CRYSTAL_BOSS = new ResourceLocation("solarcraft:objmodels/crystal_boss");

    public static final ResourceLocation SHIELDING_CRYSTAL = new ResourceLocation("solarcraft:objmodels/crystal_boss_shielding_crystal");
    public static final ResourceLocation SHIELDING_CRYSTAL_SHIELD = new ResourceLocation("solarcraft:objmodels/crystal_boss_shielding_crystal_shield");
    public static final ResourceLocation GET_OFF_MEEE = new ResourceLocation("solarcraft:objmodels/crystal_boss_getoffme");
    public static final ResourceLocation RIP_RAY_GENERATOR = new ResourceLocation("solarcraft:objmodels/rip_ray_generator");
    public static final ResourceLocation CLEARING_RITUAL_CRYSTAL = new ResourceLocation(SolarCraft.MOD_ID,"objmodels/clearing_ritual_crystal");

    public static final ResourceLocation CLEARING_RITUAL_MAIN_BLOCK_LOWER = new ResourceLocation(SolarCraft.MOD_ID,"objmodels/clearing_ritual_main_block_lower");
    public static final ResourceLocation CLEARING_RITUAL_MAIN_BLOCK_PETALS = new ResourceLocation(SolarCraft.MOD_ID,"objmodels/clearing_ritual_main_block_petals");
    public static final ResourceLocation CLEARING_RITUAL_MAIN_BLOCK_TOP = new ResourceLocation(SolarCraft.MOD_ID,"objmodels/clearing_ritual_main_block_top");

    public static final ResourceLocation HOLE_MODEL = new ResourceLocation("solarcraft:objmodels/hole");
    public static final ResourceLocation ANCIENT_FRAGMENT_MODEL = new ResourceLocation("solarcraft:toloadmodels/ancient_fragment");
    public static final ResourceLocation PORTAL_SPHERE = new ResourceLocation("solarcraft:objmodels/portal_sphere");



    public static final SolarforgeModelLoader LOADER = new SolarforgeModelLoader();


    @SubscribeEvent
    public static void registerLoaders(ModelEvent.RegisterGeometryLoaders event){
        event.register("progression_ore_loader",LOADER);
    }

    @SubscribeEvent
    public static void registerModels(final ModelEvent.RegisterAdditional event){
//        ModelLoaderRegistry.registerLoader(new ResourceLocation(SolarForge.MOD_ID,"progression_ore_loader"),LOADER);
//        ForgeModelBakery.addSpecialModel(CRYSTAL_BOSS);
//        ForgeModelBakery.addSpecialModel(SOLAR_CORE_MODEL);
//        ForgeModelBakery.addSpecialModel(ANCIENT_FRAGMENT_MODEL);
//        ForgeModelBakery.addSpecialModel(HOLE_MODEL);
//        ForgeModelBakery.addSpecialModel(SHIELDING_CRYSTAL_SHIELD);
//        ForgeModelBakery.addSpecialModel(SHIELDING_CRYSTAL);
//        ForgeModelBakery.addSpecialModel(GET_OFF_MEEE);
//        ForgeModelBakery.addSpecialModel(RIP_RAY_GENERATOR);
//        ForgeModelBakery.addSpecialModel(CRYSTAL_1);
//        ForgeModelBakery.addSpecialModel(EXPLOSIVE_CRYSTAL);
//        ForgeModelBakery.addSpecialModel(CLEARING_RITUAL_CRYSTAL);
//        ForgeModelBakery.addSpecialModel(CLEARING_RITUAL_MAIN_BLOCK_LOWER);
//        ForgeModelBakery.addSpecialModel(CLEARING_RITUAL_MAIN_BLOCK_PETALS);
//        ForgeModelBakery.addSpecialModel(CLEARING_RITUAL_MAIN_BLOCK_TOP);
//        ForgeModelBakery.addSpecialModel(PORTAL_SPHERE);
        event.register(CRYSTAL_BOSS);
        event.register(SOLAR_CORE_MODEL);
        event.register(ANCIENT_FRAGMENT_MODEL);
        event.register(HOLE_MODEL);
        event.register(SHIELDING_CRYSTAL_SHIELD);
        event.register(SHIELDING_CRYSTAL);
        event.register(GET_OFF_MEEE);
        event.register(RIP_RAY_GENERATOR);
        event.register(CRYSTAL_1);
        event.register(EXPLOSIVE_CRYSTAL);
        event.register(CLEARING_RITUAL_CRYSTAL);
        event.register(CLEARING_RITUAL_MAIN_BLOCK_LOWER);
        event.register(CLEARING_RITUAL_MAIN_BLOCK_PETALS);
        event.register(CLEARING_RITUAL_MAIN_BLOCK_TOP);
        event.register(PORTAL_SPHERE);
    }
}