package com.finderfeed.solarcraft.content.world_generation.features;

import com.finderfeed.solarcraft.SolarCraft;
import com.finderfeed.solarcraft.content.world_generation.dimension_related.radiant_land.CrystallizedOreVeinFeature;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

//I hate you with every ounce of being mojang!
public class FeaturesRegistry {

    public static final RuleTest END_STONE = new TagMatchTest(Tags.Blocks.END_STONES);

    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(Registries.FEATURE, SolarCraft.MOD_ID);

    public static final DeferredHolder<Feature<?>,Feature<NoneFeatureConfiguration>> FRAGMENT_RUINS = FEATURES.register("ruins_feature",()->new FragmentRuins(NoneFeatureConfiguration.CODEC));
    public static final DeferredHolder<Feature<?>,Feature<NoneFeatureConfiguration>> ENERGY_PYLON                       = FEATURES.register("energy_pylon_feature",()->new EnergyPylonFeature(NoneFeatureConfiguration.CODEC));
    public static final DeferredHolder<Feature<?>,Feature<NoneFeatureConfiguration>> FLOATING_ISLANDS_RADIANT_LAND      = FEATURES.register("floating_islands",()->new RadiantLandFloatingIslands(NoneFeatureConfiguration.CODEC));
    public static final DeferredHolder<Feature<?>,Feature<NoneFeatureConfiguration>> CRYSTALLIZED_ORE_VEIN_RADIANT_LAND = FEATURES.register("crystallized_ore_vein",()->new CrystallizedOreVeinFeature(NoneFeatureConfiguration.CODEC));
    public static final DeferredHolder<Feature<?>,Feature<NoneFeatureConfiguration>> CRYSTAL_CAVE_ORE_CRYSTAL           = FEATURES.register("crystal_cave_ore_crystal",()->new CrystalCaveOreCrystal(NoneFeatureConfiguration.CODEC));
    public static final DeferredHolder<Feature<?>,Feature<NoneFeatureConfiguration>> CEILING_FLOOR_CRYSTALS             = FEATURES.register("ceiling_floor_crystals",()->new WallCrystalsCrystalCave(NoneFeatureConfiguration.CODEC));
    public static final DeferredHolder<Feature<?>,Feature<SimpleBlockConfiguration>> STONE_FLOWERS                      = FEATURES.register("stone_flowers",()->new StoneFlowersFeature(SimpleBlockConfiguration.CODEC));
    public static final DeferredHolder<Feature<?>,Feature<NoneFeatureConfiguration>> CEILING_DRIPSTONE_LIKE_CRYSTALS    = FEATURES.register("ceiling_dripstonelike_crystals",()->new CeilingDripstoneLikeCrystals(NoneFeatureConfiguration.CODEC));
    public static final DeferredHolder<Feature<?>,Feature<SimpleBlockConfiguration>> CRYSTALS_ORE                       = FEATURES.register("crystallized_runic_energy",()->new CrystalsOreFeature(SimpleBlockConfiguration.CODEC));
    public static final DeferredHolder<Feature<?>,Feature<NoneFeatureConfiguration>> ULDERA_OBELISK                     = FEATURES.register("uldera_obelisk",()->new UlderaObeliskFeature(NoneFeatureConfiguration.CODEC));
    public static final DeferredHolder<Feature<?>,Feature<NoneFeatureConfiguration>> ULDERA_PYLON                       = FEATURES.register("uldera_pylon",()->new UlderaPylonFeature(NoneFeatureConfiguration.CODEC));
    public static final DeferredHolder<Feature<?>,Feature<NoneFeatureConfiguration>> CLEARING_CRYSTAL                   = FEATURES.register("clearing_crystal",()->new ClearingCrystalFeature(NoneFeatureConfiguration.CODEC));



}
