package com.finderfeed.solarforge.structures;

import com.finderfeed.solarforge.structures.charging_station.ChargingStationStructure;
import com.finderfeed.solarforge.structures.dimensional_shard_structure.DimensionalShardStructure;
import com.finderfeed.solarforge.structures.magician_tower.MagicianTowerStructure;
import com.finderfeed.solarforge.structures.maze_key_keeper.MazeStructure;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.gen.settings.StructureSeparationSettings;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashMap;
import java.util.Map;

public class SolarForgeStructures {
    public static final DeferredRegister<Structure<?>> STRUCTURES = DeferredRegister.create(ForgeRegistries.STRUCTURE_FEATURES,"solarforge");

    public static final RegistryObject<Structure<NoFeatureConfig>> DUNGEON_ONE_KEY_LOCK = STRUCTURES.register("solarforge_dungeon_one",()-> new AbstractStructure(NoFeatureConfig.CODEC));
    public static final RegistryObject<Structure<NoFeatureConfig>> DUNGEON_MAZE = STRUCTURES.register("labyrinth",()-> new MazeStructure(NoFeatureConfig.CODEC));
    public static final RegistryObject<Structure<NoFeatureConfig>> CHARGING_STATION = STRUCTURES.register("cold_star_charging_station",()-> new ChargingStationStructure(NoFeatureConfig.CODEC));
    public static final RegistryObject<Structure<NoFeatureConfig>> MAGICIAN_TOWER = STRUCTURES.register("magician_tower",()-> new MagicianTowerStructure(NoFeatureConfig.CODEC));
    public static final RegistryObject<Structure<NoFeatureConfig>> DIM_SHARD_STRUCTURE = STRUCTURES.register("dimensional_shard_structure",()-> new DimensionalShardStructure(NoFeatureConfig.CODEC));
    public static void setupStructures() {
        setupMapSpacingAndLand(
                DUNGEON_ONE_KEY_LOCK.get(),
                new StructureSeparationSettings(50 ,
                        40,
                        21313214 ),
                false);

        setupMapSpacingAndLand(
                DUNGEON_MAZE.get(),
                new StructureSeparationSettings(50 ,
                        40,
                        4234252 ),
                false);
        setupMapSpacingAndLand(
                CHARGING_STATION.get(),
                new StructureSeparationSettings(50 ,
                        40,
                        739048430 ),
                true);
        setupMapSpacingAndLand(
                MAGICIAN_TOWER.get(),
                new StructureSeparationSettings(200 ,
                        150,
                        31157935 ),
                true);
        setupMapSpacingAndLand(
                DIM_SHARD_STRUCTURE.get(),
                new StructureSeparationSettings(300 ,
                        250,
                        556143134 ),
                true);
        // Add more structures here and so on
    }

    public static <F extends Structure<?>> void setupMapSpacingAndLand(F structure,StructureSeparationSettings set,boolean transformGround){
        Structure.STRUCTURES_REGISTRY.put(structure.getRegistryName().toString(),structure);
        if(transformGround){
            Structure.NOISE_AFFECTING_FEATURES =
                    ImmutableList.<Structure<?>>builder()
                            .addAll(Structure.NOISE_AFFECTING_FEATURES)
                            .add(structure)
                            .build();
        }

        DimensionStructuresSettings.DEFAULTS =
                ImmutableMap.<Structure<?>, StructureSeparationSettings>builder()
                        .putAll(DimensionStructuresSettings.DEFAULTS)
                        .put(structure, set)
                        .build();

        WorldGenRegistries.NOISE_GENERATOR_SETTINGS.entrySet().forEach(settings -> {
            Map<Structure<?>, StructureSeparationSettings> structureMap = settings.getValue().structureSettings().structureConfig();
            if(structureMap instanceof ImmutableMap){
                Map<Structure<?>, StructureSeparationSettings> tempMap = new HashMap<>(structureMap);
                tempMap.put(structure, set);
                settings.getValue().structureSettings().structureConfig = tempMap;
            }
            else{
                structureMap.put(structure, set);
            }
        });
    }
}
