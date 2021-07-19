package com.finderfeed.solarforge.events.other_events;

import com.finderfeed.solarforge.SolarForge;
import com.finderfeed.solarforge.registries.blocks.BlocksRegistry;
import com.finderfeed.solarforge.registries.features.configured.ConfiguredFeatures;
import com.finderfeed.solarforge.world_generation.structures.SolarForgeStructureFeatures;
import com.finderfeed.solarforge.world_generation.structures.SolarForgeStructures;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.FlatChunkGenerator;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.placement.TopSolidRangeConfig;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.gen.settings.StructureSeparationSettings;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber(modid = "solarforge",bus = Mod.EventBusSubscriber.Bus.FORGE)
public class OresGeneration {

    @SubscribeEvent
    public static void genOres(final BiomeLoadingEvent event){


        if (!event.getCategory().equals(Biome.Category.NETHER) && !event.getCategory().equals(Biome.Category.THEEND)){
            event.getGeneration().addFeature(GenerationStage.Decoration.UNDERGROUND_ORES,
                    Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, SolarForge.SOLAR_ORE.get().defaultBlockState(),4))
                    .decorated(Placement.RANGE.configured(new TopSolidRangeConfig(0,0,30)).squared().count(10)));
        }
        if (!event.getCategory().equals(Biome.Category.NETHER) && !event.getCategory().equals(Biome.Category.THEEND)){
            event.getGeneration().addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, BlocksRegistry.SOLAR_STONE.get().defaultBlockState(),10))
                    .decorated(Placement.RANGE.configured(new TopSolidRangeConfig(0,0,80)).squared().count(7)));
        }
        if (event.getCategory().equals(Biome.Category.DESERT)) {
            event.getGeneration().getStructures().add(() -> SolarForgeStructureFeatures.CONF_DUNGEON_ONE);
        }
        if (event.getCategory().equals(Biome.Category.SAVANNA) || event.getCategory().equals(Biome.Category.MESA)) {
            event.getGeneration().getStructures().add(() -> SolarForgeStructureFeatures.CONF_DUNGEON_MAZE);
        }

        if (event.getCategory().equals(Biome.Category.PLAINS) || event.getCategory().equals(Biome.Category.EXTREME_HILLS)) {
            event.getGeneration().getStructures().add(() -> SolarForgeStructureFeatures.CONF_DUNGEON_CHARGING_STATION);
        }

        if (event.getCategory().equals(Biome.Category.DESERT)) {
            event.getGeneration().addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, ConfiguredFeatures.SOLAR_FLOWER_FEATURE);
        }
        if (event.getCategory().equals(Biome.Category.EXTREME_HILLS) ) {
            event.getGeneration().getStructures().add(() -> SolarForgeStructureFeatures.CONF_MAGICIAN_TOWER);
        }

        if (event.getCategory().equals(Biome.Category.JUNGLE) ) {
            event.getGeneration().getStructures().add(() -> SolarForgeStructureFeatures.CONF_DIM_SHARD_STRUCT);
        }
    }


    @SubscribeEvent
    public void addDimensionalSpacing(final WorldEvent.Load event) {
        if(event.getWorld() instanceof ServerWorld){
            ServerWorld serverWorld = (ServerWorld)event.getWorld();

            /*
             * Skip Terraforged's chunk generator as they are a special case of a mod locking down their chunkgenerator.
             * They will handle your structure spacing for your if you add to WorldGenRegistries.NOISE_GENERATOR_SETTINGS in your structure's registration.
             * This here is done with reflection as this tutorial is not about setting up and using Mixins.
             * If you are using mixins, you can call the codec method with an invoker mixin instead of using reflection.
             */


            /*
             * Prevent spawning our structure in Vanilla's superflat world as
             * people seem to want their superflat worlds free of modded structures.
             * Also that vanilla superflat is really tricky and buggy to work with in my experience.
             */
            if(serverWorld.getChunkSource().getGenerator() instanceof FlatChunkGenerator &&
                    serverWorld.dimension().equals(World.OVERWORLD)){
                return;
            }

            /*
             * putIfAbsent so people can override the spacing with dimension datapacks themselves if they wish to customize spacing more precisely per dimension.
             * Requires AccessTransformer  (see resources/META-INF/accesstransformer.cfg)
             *
             * NOTE: if you add per-dimension spacing configs, you can't use putIfAbsent as WorldGenRegistries.NOISE_GENERATOR_SETTINGS in FMLCommonSetupEvent
             * already added your default structure spacing to some dimensions. You would need to override the spacing with .put(...)
             * And if you want to do dimension blacklisting, you need to remove the spacing entry entirely from the map below to prevent generation safely.
             */
            Map<Structure<?>, StructureSeparationSettings> tempMap = new HashMap<>(serverWorld.getChunkSource().generator.getSettings().structureConfig());
            tempMap.putIfAbsent(SolarForgeStructures.DUNGEON_ONE_KEY_LOCK.get(), DimensionStructuresSettings.DEFAULTS.get(SolarForgeStructures.DUNGEON_ONE_KEY_LOCK.get()));
            serverWorld.getChunkSource().generator.getSettings().structureConfig = tempMap;
        }
    }
}
