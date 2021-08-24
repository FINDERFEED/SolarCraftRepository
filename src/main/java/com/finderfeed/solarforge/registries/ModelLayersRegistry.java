package com.finderfeed.solarforge.registries;


import com.finderfeed.solarforge.SolarAbilities.meteorite.MeteoriteModel;
import com.finderfeed.solarforge.magic_items.blocks.blockentities.projectiles.renderers.AbstractTurretProjectileRenderer;
import com.finderfeed.solarforge.magic_items.blocks.blockentities.projectiles.renderers.MortarProjectileRenderer;
import com.finderfeed.solarforge.magic_items.blocks.rendering_models.AuraHealerModel;
import com.finderfeed.solarforge.magic_items.blocks.rendering_models.RadiantPortal;
import com.finderfeed.solarforge.magic_items.blocks.rendering_models.SolarEnergyGeneratorModel;
import com.finderfeed.solarforge.magic_items.blocks.solar_forge_block.SolarForgeBlockModelTrue;
import com.finderfeed.solarforge.magic_items.blocks.solar_forge_block.SolarForgePetalsTrue;
import com.finderfeed.solarforge.magic_items.items.item_models.SolarGodShield;
import com.finderfeed.solarforge.magic_items.items.projectiles.renderers.UltraCrossbowProjectileRenderer;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = "solarforge",bus = Mod.EventBusSubscriber.Bus.MOD,value = Dist.CLIENT)
public class ModelLayersRegistry {


    public static final ModelLayerLocation METEORITE_LAYER =
            new ModelLayerLocation(new ResourceLocation("solarforge","solar_forge_meteorite_projectile"), "main");

    public static final ModelLayerLocation AURA_HEALER_LAYER =
            new ModelLayerLocation(new ResourceLocation("solarforge","aura_healer"), "main");

    public static final ModelLayerLocation SOLAR_CROSSBOW_LAYER =
            new ModelLayerLocation(new ResourceLocation("solarforge","solar_crossbow"), "main");

    public static final ModelLayerLocation MORTAR_PROJ_LAYER =
            new ModelLayerLocation(new ResourceLocation("solarforge","mortar_proj"), "main");

    public static final ModelLayerLocation TURRET_PROJ_LAYER =
            new ModelLayerLocation(new ResourceLocation("solarforge","turret_proj"), "main");

    public static final ModelLayerLocation SOLAR_ENERGY_GEN_LAYER =
            new ModelLayerLocation(new ResourceLocation("solarforge","solar_energy_gen_layer"), "main");

    public static final ModelLayerLocation SOLAR_FORGE_PETALS =
            new ModelLayerLocation(new ResourceLocation("solarforge","solar_forge_petals"), "main");

    public static final ModelLayerLocation SOLAR_FORGE_MAIN_MODEL =
            new ModelLayerLocation(new ResourceLocation("solarforge","solar_forge_main_model"), "main");

    public static final ModelLayerLocation SOLAR_GOD_SHIELD_MODEL =
            new ModelLayerLocation(new ResourceLocation("solarforge","solar_god_shield"), "main");

    public static final ModelLayerLocation RADIANT_PORTAL_CREATOR_MODEL =
            new ModelLayerLocation(new ResourceLocation("solarforge","radiant_model_creator"), "main");

    @SubscribeEvent
    public static void registerModelLayers(EntityRenderersEvent.RegisterLayerDefinitions event){

        event.registerLayerDefinition(METEORITE_LAYER, MeteoriteModel::createLayer);
        event.registerLayerDefinition(AURA_HEALER_LAYER, AuraHealerModel::createLayer);
        event.registerLayerDefinition(SOLAR_CROSSBOW_LAYER, UltraCrossbowProjectileRenderer::createLayer);
        event.registerLayerDefinition(MORTAR_PROJ_LAYER, MortarProjectileRenderer::createLayer);
        event.registerLayerDefinition(TURRET_PROJ_LAYER, AbstractTurretProjectileRenderer::createLayer);
        event.registerLayerDefinition(SOLAR_ENERGY_GEN_LAYER, SolarEnergyGeneratorModel::createLayers);
        event.registerLayerDefinition(SOLAR_FORGE_PETALS, SolarForgePetalsTrue::createLayer);
        event.registerLayerDefinition(SOLAR_FORGE_MAIN_MODEL, SolarForgeBlockModelTrue::createLayer);
        event.registerLayerDefinition(SOLAR_GOD_SHIELD_MODEL, SolarGodShield::createLayers);
        event.registerLayerDefinition(RADIANT_PORTAL_CREATOR_MODEL, RadiantPortal::createLayers);
    }
}
