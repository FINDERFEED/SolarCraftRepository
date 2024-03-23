package com.finderfeed.solarcraft.registries;


import com.finderfeed.solarcraft.SolarCraft;
import com.finderfeed.solarcraft.content.abilities.meteorite.MeteoriteModel;
import com.finderfeed.solarcraft.client.models.divine_armor.DivineBootsModel;
import com.finderfeed.solarcraft.client.models.divine_armor.DivineChestplateModel;
import com.finderfeed.solarcraft.client.models.divine_armor.DivineHelmetModel;
import com.finderfeed.solarcraft.client.models.divine_armor.DivineLeggingsModel;
import com.finderfeed.solarcraft.content.entities.models.RunicElementalModel;
import com.finderfeed.solarcraft.content.entities.models.RunicWarriorModel;
import com.finderfeed.solarcraft.content.blocks.blockentities.clearing_ritual.clearing_ritual_crystal.corruption_wisp.CorruptionWispModel;
import com.finderfeed.solarcraft.content.blocks.blockentities.projectiles.renderers.AbstractTurretProjectileRenderer;
import com.finderfeed.solarcraft.content.blocks.blockentities.projectiles.renderers.MortarProjectileRenderer;
import com.finderfeed.solarcraft.content.blocks.rendering_models.AuraHealerModel;
import com.finderfeed.solarcraft.content.blocks.rendering_models.SolarEnergyGeneratorModel;
import com.finderfeed.solarcraft.content.blocks.solar_forge_block.SolarForgeBlockModel;
import com.finderfeed.solarcraft.content.blocks.solar_forge_block.SolarForgePetalsModel;
import com.finderfeed.solarcraft.content.items.item_models.SolarGodShield;
import com.finderfeed.solarcraft.content.entities.projectiles.renderers.UltraCrossbowProjectileRenderer;

import com.finderfeed.solarcraft.content.entities.projectiles.renderers.models.RunicHammerModel;
import net.minecraft.client.model.ZombieModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@Mod.EventBusSubscriber(modid = "solarcraft",bus = Mod.EventBusSubscriber.Bus.MOD,value = Dist.CLIENT)
public class ModelLayersRegistry {


    public static final ModelLayerLocation METEORITE_LAYER =
            new ModelLayerLocation(new ResourceLocation("solarcraft","solar_forge_meteorite_projectile"), "main");

    public static final ModelLayerLocation AURA_HEALER_LAYER =
            new ModelLayerLocation(new ResourceLocation("solarcraft","aura_healer"), "main");

    public static final ModelLayerLocation SOLAR_CROSSBOW_LAYER =
            new ModelLayerLocation(new ResourceLocation("solarcraft","solar_crossbow"), "main");

    public static final ModelLayerLocation MORTAR_PROJ_LAYER =
            new ModelLayerLocation(new ResourceLocation("solarcraft","mortar_proj"), "main");

    public static final ModelLayerLocation TURRET_PROJ_LAYER =
            new ModelLayerLocation(new ResourceLocation("solarcraft","turret_proj"), "main");

    public static final ModelLayerLocation SOLAR_ENERGY_GEN_LAYER =
            new ModelLayerLocation(new ResourceLocation("solarcraft","solar_energy_gen_layer"), "main");

    public static final ModelLayerLocation SOLAR_FORGE_PETALS =
            new ModelLayerLocation(new ResourceLocation("solarcraft","solar_forge_petals"), "main");

    public static final ModelLayerLocation SOLAR_FORGE_MAIN_MODEL =
            new ModelLayerLocation(new ResourceLocation("solarcraft","solar_forge_main_model"), "main");

    public static final ModelLayerLocation SOLAR_GOD_SHIELD_MODEL =
            new ModelLayerLocation(new ResourceLocation("solarcraft","solar_god_shield"), "main");

    public static final ModelLayerLocation SHADOW_ZOMBIE_LAYER = new ModelLayerLocation(new ResourceLocation(SolarCraft.MOD_ID,"shadow_zombie_layer"),"main");
    public static final ModelLayerLocation CORRUPTION_WISP_LAYER = new ModelLayerLocation(new ResourceLocation(SolarCraft.MOD_ID,"corruption_wisp_layer"),"main");

    @SubscribeEvent
    public static void registerModelLayers(EntityRenderersEvent.RegisterLayerDefinitions event){
        event.registerLayerDefinition(METEORITE_LAYER, MeteoriteModel::createLayer);
        event.registerLayerDefinition(AURA_HEALER_LAYER, AuraHealerModel::createLayer);
        event.registerLayerDefinition(SOLAR_CROSSBOW_LAYER, UltraCrossbowProjectileRenderer::createLayer);
        event.registerLayerDefinition(MORTAR_PROJ_LAYER, MortarProjectileRenderer::createLayer);
        event.registerLayerDefinition(TURRET_PROJ_LAYER, AbstractTurretProjectileRenderer::createLayer);
        event.registerLayerDefinition(SOLAR_ENERGY_GEN_LAYER, SolarEnergyGeneratorModel::createLayers);
        event.registerLayerDefinition(SOLAR_FORGE_PETALS, SolarForgePetalsModel::createLayer);
        event.registerLayerDefinition(SOLAR_FORGE_MAIN_MODEL, SolarForgeBlockModel::createLayer);
        event.registerLayerDefinition(SOLAR_GOD_SHIELD_MODEL, SolarGodShield::createLayers);
        event.registerLayerDefinition(RunicElementalModel.LAYER_LOCATION, RunicElementalModel::createBodyLayer);
        event.registerLayerDefinition(RunicWarriorModel.LAYER_LOCATION, RunicWarriorModel::createBodyLayer);
        event.registerLayerDefinition(RunicHammerModel.LAYER_LOCATION, RunicHammerModel::createBodyLayer);
        event.registerLayerDefinition(DivineChestplateModel.LAYER_LOCATION, DivineChestplateModel::createBodyLayer);
        event.registerLayerDefinition(DivineLeggingsModel.LAYER_LOCATION, DivineLeggingsModel::createBodyLayer);
        event.registerLayerDefinition(DivineHelmetModel.LAYER_LOCATION, DivineHelmetModel::createBodyLayer);
        event.registerLayerDefinition(DivineBootsModel.LAYER_LOCATION, DivineBootsModel::createBodyLayer);
        event.registerLayerDefinition(SHADOW_ZOMBIE_LAYER, ()->
                LayerDefinition.create(ZombieModel.createMesh(new CubeDeformation(0.5f),0.0f),64,64));
        event.registerLayerDefinition(CORRUPTION_WISP_LAYER,()-> CorruptionWispModel.createBodyLayer(0.25f));
        event.registerLayerDefinition(CorruptionWispModel.LAYER_LOCATION,()->CorruptionWispModel.createBodyLayer(0.0f));
    }
}
