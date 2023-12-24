package com.finderfeed.solarcraft.events.other_events;



import com.finderfeed.solarcraft.SolarCraft;
import com.finderfeed.solarcraft.client.model_loaders.SolarCraftModelLoader;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.ModelEvent;



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
    public static final ResourceLocation SOLAR_WAND_MODEL = new ResourceLocation("solarcraft:toloadmodels/solar_wand");
    public static final ResourceLocation PORTAL_SPHERE = new ResourceLocation("solarcraft:objmodels/portal_sphere");
    public static final ResourceLocation ORBITAL_EXPLOSION_SPHERE = new ResourceLocation("solarcraft:objmodels/orbital_explosion_sphere");



    public static final SolarCraftModelLoader LOADER = new SolarCraftModelLoader();


    @SubscribeEvent
    public static void registerLoaders(ModelEvent.RegisterGeometryLoaders event){
        event.register(new ResourceLocation(SolarCraft.MOD_ID,"progression_ore_loader"), LOADER);
    }

    @SubscribeEvent
    public static void registerModels(final ModelEvent.RegisterAdditional event){
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
        event.register(SOLAR_WAND_MODEL);
        event.register(ORBITAL_EXPLOSION_SPHERE);
    }
}
