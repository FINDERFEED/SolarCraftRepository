package com.finderfeed.solarforge;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.level.block.Block;

public class SolarCraftTags {

    public static final Tag.Named<Block> ILLIDIUM_TAG = BlockTags.createOptional(new ResourceLocation(SolarForge.MOD_ID,"illidium"));
    public static final Tag.Named<Block> QUALADIUM_TAG = BlockTags.createOptional(new ResourceLocation(SolarForge.MOD_ID,"qualadium"));
    public static final Tag.Named<Block> CHARGED_QUALADIUM_TAG = BlockTags.createOptional(new ResourceLocation(SolarForge.MOD_ID,"charged_qualadium"));
    public static final Tag.Named<Block> SOLAR_GOD_TAG = BlockTags.createOptional(new ResourceLocation(SolarForge.MOD_ID,"solar_god"));

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
}
