package com.finderfeed.solarcraft;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;

public class SolarCraftTags {

    public static final TagKey<Block> ILLIDIUM_TAG = BlockTags.create(new ResourceLocation(SolarCraft.MOD_ID,"illidium"));
    public static final TagKey<Block> QUALADIUM_TAG = BlockTags.create(new ResourceLocation(SolarCraft.MOD_ID,"qualadium"));
    public static final TagKey<Block> CHARGED_QUALADIUM_TAG = BlockTags.create(new ResourceLocation(SolarCraft.MOD_ID,"charged_qualadium"));
    public static final TagKey<Block> SOLAR_GOD_TAG = BlockTags.create(new ResourceLocation(SolarCraft.MOD_ID,"solar_god"));
    public static final TagKey<Block> DIVINE = BlockTags.create(new ResourceLocation(SolarCraft.MOD_ID,"divine"));

    public static final String SOLAR_GOD_SWORD_TAG = "solarcraft_solar_god_sword";
    public static final String SOLAR_GOD_SWORD_LEVEL_TAG = "solarcraft_solar_god_sword_level";

    public static final String SOLAR_GOD_PICKAXE_TAG = "solarcraft_solar_god_pickaxe";
    public static final String SOLAR_GOD_PICKAXE_LEVEL_TAG = "solarcraft_solar_god_pickaxe_level";

    public static final String SOLAR_GOD_BOW_TAG = "solarcraft_solar_god_bow";
    public static final String SOLAR_GOD_BOW_LEVEL_TAG = "solarcraft_solar_god_bow_level";

    public static final String IMBUE_TIME_TAG = "solarcraft_imbue_item_time";

    public static final String EXPERIENCE_CRYSTAL_SUBTAG = "solarcraft_experience_crystal";
    public static final String EXPERIENCE_CRYSTAL_CONTAINED_XP = "solarcraft_experience_crystal_contained_xp";

    public static final String FURY_SWIPES_DAMAGE = "solarcraft_fury_swipes";

    public static final String RAW_SOLAR_ENERGY = "solarcraft_raw_solar_energy";

    public static final String CAN_PLAYER_USE = "solar_forge_can_player_use_";

    public static final String DIVINE_RAPIER_NBT = "divine_rapier_tag";

    public static final String DIVINE_ARMOR_TAG = "divine_armor_tag";

    public static class SBiomes {
        public static final TagKey<Biome> IS_DESERT =
                TagKey.create(Registry.BIOME_REGISTRY,
                new ResourceLocation(SolarCraft.MOD_ID,"is_desert"));

        public static final TagKey<Biome> IS_SAVANNA =
                TagKey.create(Registry.BIOME_REGISTRY,
                        new ResourceLocation(SolarCraft.MOD_ID,"is_savanna"));

        public static final TagKey<Biome> IS_PLAINS =
                TagKey.create(Registry.BIOME_REGISTRY,
                        new ResourceLocation(SolarCraft.MOD_ID,"is_plains"));

        public static final TagKey<Biome> IS_RADIANT_LAND =
                TagKey.create(Registry.BIOME_REGISTRY,
                        new ResourceLocation(SolarCraft.MOD_ID,"is_radiant_land"));
    }

    public static class SStructures{

        public static final TagKey<Biome> HAS_CHARGING_STATION = create("charging_station");
        public static final TagKey<Biome> HAS_CRYSTAL_BOSS_ROOM = create("crystal_boss_room");
        public static final TagKey<Biome> HAS_DIM_SHARD_DUNGEON = create("dim_shard_dungeon");
        public static final TagKey<Biome> HAS_DUNGEON_ONE = create("dungeon_one");
        public static final TagKey<Biome> HAS_TRAP_DUNGEON = create("has_trap_dungeon");
        public static final TagKey<Biome> HAS_MAGICIAN_TOWER = create("magician_tower");
        public static final TagKey<Biome> HAS_RUNIC_ELEMENTAL_ARENA = create("runic_elemental_arena");
        public static final TagKey<Biome> HAS_CLEARING_RITUAL_STRUCTURE = create("clearing_ritual_structure");
        public static final TagKey<Biome> HAS_SUN_SHARD_PUZZLE = create("sun_shard_puzzle");


        public static TagKey<Biome> create(String name){
            return TagKey.create(Registry.BIOME_REGISTRY,new ResourceLocation(SolarCraft.MOD_ID,"has_structure/"+name));
        }
    }

}
