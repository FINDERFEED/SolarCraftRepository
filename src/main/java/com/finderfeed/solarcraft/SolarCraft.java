package com.finderfeed.solarcraft;


import com.finderfeed.solarcraft.config.JsonConfig;
import com.finderfeed.solarcraft.config.enchanter_config.EnchanterConfigInit;
import com.finderfeed.solarcraft.config.JsonFragmentsHelper;
import com.finderfeed.solarcraft.config.SolarcraftClientConfig;
import com.finderfeed.solarcraft.config.SolarcraftConfig;

import com.finderfeed.solarcraft.content.world_generation.structures.SolarcraftStructureTypes;
import com.finderfeed.solarcraft.content.world_generation.structures.StructurePieces;
import com.finderfeed.solarcraft.events.RenderEventsHandler;
import com.finderfeed.solarcraft.content.blocks.infusing_table_things.infusing_pool.InfusingStand;
import com.finderfeed.solarcraft.content.items.item_tiers.SolarCraftToolTiers;
import com.finderfeed.solarcraft.client.particles.SCParticleTypes;
import com.finderfeed.solarcraft.registries.*;
import com.finderfeed.solarcraft.registries.abilities.AbilitiesRegistry;
import com.finderfeed.solarcraft.registries.attributes.AttributesRegistry;
import com.finderfeed.solarcraft.registries.blocks.SCBlocks;
import com.finderfeed.solarcraft.registries.command_argument_types.SolarcraftCommandArgumentTypes;
import com.finderfeed.solarcraft.registries.data_serializers.FDEntityDataSerializers;
import com.finderfeed.solarcraft.registries.entities.SCEntityTypes;

import com.finderfeed.solarcraft.packet_handler.SCPacketHandler;
import com.finderfeed.solarcraft.registries.containers.SCContainers;
import com.finderfeed.solarcraft.registries.effects.SCEffects;
import com.finderfeed.solarcraft.registries.loot_modifiers.SolarcraftLootModifiers;
import com.finderfeed.solarcraft.registries.recipe_types.SCRecipeTypes;
import com.finderfeed.solarcraft.registries.tile_entities.SCTileEntities;
import com.finderfeed.solarcraft.registries.wand_actions.SolarCraftWandActionRegistry;

import com.finderfeed.solarcraft.registries.items.SCItems;
import com.finderfeed.solarcraft.registries.sounds.SCSounds;


import com.finderfeed.solarcraft.content.world_generation.structures.SolarcraftStructureHolders;
import com.finderfeed.solarcraft.content.world_generation.features.FeaturesRegistry;
import com.finderfeed.solarcraft.content.world_generation.features.foliage_placers.FoliagePlacerRegistry;
import com.finderfeed.solarcraft.content.world_generation.features.trunk_placers.TrunkPlacersRegistry;
import net.minecraft.world.item.Tiers;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.TierSortingRegistry;


import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file

import java.util.List;

@Mod(SolarCraft.MOD_ID)
public class SolarCraft {
    public static final int ENCHANTER_CONFIG_VERSION = 1;

    public static final String MOD_ID = "solarcraft";

    public static final Logger LOGGER = LogManager.getLogger("SOLARCRAFT");

    public SolarCraft(IEventBus bus) {
        SCParticleTypes.PARTICLES.register(bus);
        SCCreativeTabs.REGISTRY.register(bus);
        SCItems.ITEMS.register(bus);
        SCBlocks.BLOCKS.register(bus);
        SCTileEntities.TILE_ENTITY_TYPE.register(bus);
        SCEntityTypes.ENTITY_TYPE_REGISTER.register(bus);
        SCEffects.EFFECTS.register(bus);
        SCSounds.SOUND_EVENTS.register(bus);
        SCContainers.CONTAINER_TYPE.register(bus);
        FoliagePlacerRegistry.DEFERRED_REGISTER.register(bus);
        AttributesRegistry.DEF_REG.register(bus);
        FDEntityDataSerializers.DEF_REG.register(bus);
        FeaturesRegistry.FEATURES.register(bus);
        SCRecipeTypes.RECIPE_SERIALIZERS.register(bus);
        SCRecipeTypes.RECIPE_TYPES.register(bus);
        SolarcraftLootModifiers.MODIFIERS.register(bus);
        SolarcraftCommandArgumentTypes.ARGUMENT_TYPES.register(bus);
        SCItemPredicates.PREDICATES.register(bus);
        SolarcraftGamerules.init();
        ConfigRegistry.init();
        SolarcraftStructureTypes.STRUCTURE_TYPES.register(bus);
        StructurePieces.STRUCTURE_PIECES.register(bus);
        SCAttachmentTypes.ATTACHMENT_TYPES.register(bus);
//        SolarcraftStructureTypes.init();
        SolarcraftStructureHolders.init();
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, SolarcraftConfig.SPEC,"solarcraft-config.toml");
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, SolarcraftClientConfig.SPEC,"solarcraft-client-config.toml");
//        SCPacketHandler.registerMessages();

        for (JsonConfig early : ConfigRegistry.EARLY_LOAD_CONFIGS.values()){
            early.init();
        }
    }

}
