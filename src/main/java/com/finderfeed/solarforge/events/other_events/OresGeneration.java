package com.finderfeed.solarforge.events.other_events;

import com.finderfeed.solarforge.content.world_generation.features.FeaturesRegistry;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "solarforge",bus = Mod.EventBusSubscriber.Bus.FORGE)
public class OresGeneration {

//    @SubscribeEvent
//    public static void genOres(final BiomeLoadingEvent event){
//
////.rangeUniform(VerticalAnchor.bottom(),VerticalAnchor.absolute(30)).squared().count(10)
//        if (!event.getCategory().equals(Biome.BiomeCategory.NETHER) && !event.getCategory().equals(Biome.BiomeCategory.THEEND) && notNone(event)){
//            event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES,FeaturesRegistry.SOLAR_ORE);
//        }
////        .rangeUniform(VerticalAnchor.bottom(),VerticalAnchor.absolute(80)).squared().count(7)
//        if (!event.getCategory().equals(Biome.BiomeCategory.NETHER) && !event.getCategory().equals(Biome.BiomeCategory.THEEND) && notNone(event)){
//            event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, FeaturesRegistry.SOLAR_STONE);
//            //TODO:remove when adding overworld biomes will be possible
//            event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, FeaturesRegistry.ULDORADIUM_ORE_PLACED_FEATURE);
//        }

//        if (event.getCategory().equals(Biome.BiomeCategory.DESERT) ) {
//            event.getGeneration().getStructures().add(() -> SolarForgeStructureFeatures.CONF_DUNGEON_ONE);
//        }
//        if (event.getCategory().equals(Biome.BiomeCategory.SAVANNA) || event.getCategory().equals(Biome.BiomeCategory.MESA)) {
//            event.getGeneration().getStructures().add(() -> SolarForgeStructureFeatures.CONF_DUNGEON_MAZE);
//        }
//
//        if (event.getCategory().equals(Biome.BiomeCategory.PLAINS) || event.getCategory().equals(Biome.BiomeCategory.EXTREME_HILLS)) {
//            event.getGeneration().getStructures().add(() -> SolarForgeStructureFeatures.CONF_DUNGEON_CHARGING_STATION);
//        }
//
//        if (event.getCategory().equals(Biome.BiomeCategory.DESERT)) {
//            event.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ConfiguredFeatures.SOLAR_FLOWER_FEATURE);
//        }
//        if (event.getCategory().equals(Biome.BiomeCategory.EXTREME_HILLS) ) {
//            event.getGeneration().getStructures().add(() -> SolarForgeStructureFeatures.CONF_MAGICIAN_TOWER);
//        }
//
//        if (event.getCategory().equals(Biome.BiomeCategory.JUNGLE) ) {
//            event.getGeneration().getStructures().add(() -> SolarForgeStructureFeatures.CONF_DIM_SHARD_STRUCT);
//        }


//    }
//
//    private static boolean notNone(BiomeLoadingEvent event){
//        return event.getCategory() != Biome.BiomeCategory.NONE;
//    }

//    @SubscribeEvent
//    public void addDimensionalSpacing(final WorldEvent.Load event) {
//        if(event.getWorld() instanceof ServerLevel){
//            ServerLevel serverWorld = (ServerLevel)event.getWorld();
//
//            /*
//             * Skip Terraforged's chunk generator as they are a special case of a mod locking down their chunkgenerator.
//             * They will handle your structure spacing for your if you add to WorldGenRegistries.NOISE_GENERATOR_SETTINGS in your structure's registration.
//             * This here is done with reflection as this tutorial is not about setting up and using Mixins.
//             * If you are using mixins, you can call the codec method with an invoker mixin instead of using reflection.
//             */
//
//
//            /*
//             * Prevent spawning our structure in Vanilla's superflat world as
//             * people seem to want their superflat worlds free of modded structures.
//             * Also that vanilla superflat is really tricky and buggy to work with in my experience.
//             */
//            if(serverWorld.getChunkSource().getGenerator() instanceof FlatLevelSource &&
//                    serverWorld.dimension().equals(Level.OVERWORLD)){
//                return;
//            }
//
//            /*
//             * putIfAbsent so people can override the spacing with dimension datapacks themselves if they wish to customize spacing more precisely per dimension.
//             * Requires AccessTransformer  (see resources/META-INF/accesstransformer.cfg)
//             *
//             * NOTE: if you add per-dimension spacing configs, you can't use putIfAbsent as WorldGenRegistries.NOISE_GENERATOR_SETTINGS in FMLCommonSetupEvent
//             * already added your default structure spacing to some dimensions. You would need to override the spacing with .put(...)
//             * And if you want to do dimension blacklisting, you need to remove the spacing entry entirely from the map below to prevent generation safely.
//             */
//            Map<StructureFeature<?>, StructureFeatureConfiguration> tempMap = new HashMap<>(serverWorld.getChunkSource().getGenerator().getSettings().structureConfig());
//            tempMap.putIfAbsent(SolarForgeStructures.DUNGEON_ONE_KEY_LOCK.get(), StructureSettings.DEFAULTS.get(SolarForgeStructures.DUNGEON_ONE_KEY_LOCK.get()));
//            serverWorld.getChunkSource().getGenerator().getSettings().structureConfig = tempMap;
//        }
//    }
}
