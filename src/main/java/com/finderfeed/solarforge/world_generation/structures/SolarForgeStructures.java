package com.finderfeed.solarforge.world_generation.structures;

import com.finderfeed.solarforge.world_generation.structures.charging_station.ChargingStationStructure;
import com.finderfeed.solarforge.world_generation.structures.crystal_boss_room.CrystalBossRoomStructure;
import com.finderfeed.solarforge.world_generation.structures.dimensional_shard_structure.DimensionalShardStructure;
import com.finderfeed.solarforge.world_generation.structures.magician_tower.MagicianTowerStructure;
import com.finderfeed.solarforge.world_generation.structures.maze_key_keeper.MazeStructure;
import com.finderfeed.solarforge.world_generation.structures.runic_elemental_arena.RunicElementalArenaStructure;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SolarForgeStructures {
    public static final DeferredRegister<StructureFeature<?>> STRUCTURES = DeferredRegister.create(ForgeRegistries.STRUCTURE_FEATURES,"solarforge");

    public static final RegistryObject<StructureFeature<NoneFeatureConfiguration>> DUNGEON_ONE_KEY_LOCK = STRUCTURES.register("solarforge_dungeon_one",()-> new DungeonOne(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<StructureFeature<NoneFeatureConfiguration>> DUNGEON_MAZE = STRUCTURES.register("labyrinth",()-> new MazeStructure(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<StructureFeature<NoneFeatureConfiguration>> CHARGING_STATION = STRUCTURES.register("cold_star_charging_station",()-> new ChargingStationStructure(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<StructureFeature<NoneFeatureConfiguration>> MAGICIAN_TOWER = STRUCTURES.register("magician_tower",()-> new MagicianTowerStructure(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<StructureFeature<NoneFeatureConfiguration>> DIM_SHARD_STRUCTURE = STRUCTURES.register("dimensional_shard_structure",()-> new DimensionalShardStructure(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<StructureFeature<NoneFeatureConfiguration>> CRYSTAL_BOSS_ROOM = STRUCTURES.register("crystal_boss_room",()-> new CrystalBossRoomStructure(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<StructureFeature<NoneFeatureConfiguration>> RUNIC_ELEMENTAL_ARENA = STRUCTURES.register("runic_elemental_arena",()-> new RunicElementalArenaStructure(NoneFeatureConfiguration.CODEC));
//    public static void setupStructures() {
//        setupMapSpacingAndLand(
//                DUNGEON_ONE_KEY_LOCK.get(),
//                new StructureFeatureConfiguration(50 ,
//                        40,
//                        21313214 ),
//                false);
//
//        setupMapSpacingAndLand(
//                DUNGEON_MAZE.get(),
//                new StructureFeatureConfiguration(50 ,
//                        40,
//                        4234252 ),
//                false);
//        setupMapSpacingAndLand(
//                CHARGING_STATION.get(),
//                new StructureFeatureConfiguration(50 ,
//                        40,
//                        739048430 ),
//                true);
//        setupMapSpacingAndLand(
//                MAGICIAN_TOWER.get(),
//                new StructureFeatureConfiguration(200 ,
//                        150,
//                        31157935 ),
//                true);
//        setupMapSpacingAndLand(
//                DIM_SHARD_STRUCTURE.get(),
//                new StructureFeatureConfiguration(300 ,
//                        250,
//                        556143134 ),
//                true);
//        setupMapSpacingAndLand(
//                CRYSTAL_BOSS_ROOM.get(),
//                new StructureFeatureConfiguration(100 ,
//                        80,
//                        956143769 ),
//                true);
//
//        setupMapSpacingAndLand(
//                RUNIC_ELEMENTAL_ARENA.get(),
//                new StructureFeatureConfiguration(200 ,
//                        150,
//                        442382084 ),
//                true);
//        // Add more structures here and so on
//    }
//
//    public static <F extends StructureFeature<?>> void setupMapSpacingAndLand(F structure,StructureFeatureConfiguration set,boolean transformGround){
//        StructureFeature.STRUCTURES_REGISTRY.put(structure.getRegistryName().toString(),structure);
//        if(transformGround){
//            StructureFeature.NOISE_AFFECTING_FEATURES =
//                    ImmutableList.<StructureFeature<?>>builder()
//                            .addAll(StructureFeature.NOISE_AFFECTING_FEATURES)
//                            .add(structure)
//                            .build();
//        }
//
//        StructureSettings.DEFAULTS =
//                ImmutableMap.<StructureFeature<?>, StructureFeatureConfiguration>builder()
//                        .putAll(StructureSettings.DEFAULTS)
//                        .put(structure, set)
//                        .build();
//
//        BuiltinRegistries.NOISE_GENERATOR_SETTINGS.entrySet().forEach(settings -> {
//            Map<StructureFeature<?>, StructureFeatureConfiguration> structureMap = settings.getValue().structureSettings().structureConfig();
//            if(structureMap instanceof ImmutableMap){
//                Map<StructureFeature<?>, StructureFeatureConfiguration> tempMap = new HashMap<>(structureMap);
//                tempMap.put(structure, set);
//                settings.getValue().structureSettings().structureConfig = tempMap;
//            }
//            else{
//                structureMap.put(structure, set);
//            }
//        });
//    }
}
