package com.finderfeed.solarforge.world_generation.features.foliage_placers;

import com.finderfeed.solarforge.world_generation.dimension_related.radiant_land.RadiantSmallTreeFoliagePlacer;
import com.finderfeed.solarforge.world_generation.dimension_related.radiant_land.RadiantTreeFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class FoliagePlacerRegistry {

    public static FoliagePlacerType<BurntTreeFoliagePlacer> BURNT_TREE_PLACER = new FoliagePlacerType<>(BurntTreeFoliagePlacer.CODEC);
    public static FoliagePlacerType<RadiantTreeFoliagePlacer> RADIANT_PLACER = new FoliagePlacerType<>(RadiantTreeFoliagePlacer.CODEC);
    public static FoliagePlacerType<RadiantSmallTreeFoliagePlacer> RADIANT_SMALL_PLACER = new FoliagePlacerType<>(RadiantSmallTreeFoliagePlacer.CODEC);

    public static final DeferredRegister<FoliagePlacerType<?>> DEFERRED_REGISTER = DeferredRegister.create(ForgeRegistries.FOLIAGE_PLACER_TYPES,"solarforge");

//    public static final RegistryObject<FoliagePlacerType<BurntTreeFoliagePlacer>> BURNT_TREE_FOLIAGE_PLACER_FOLIAGE_PLACER_TYPE = DEFERRED_REGISTER.register("burnt_tree_foliage",()->
//            BURNT_TREE_PLACER);
//
//    public static final RegistryObject<FoliagePlacerType<RadiantTreeFoliagePlacer>> RADIANT_TREE_FOLIAGE_PLACER = DEFERRED_REGISTER.register("radiant_tree_foliage",()->
//            RADIANT_PLACER);
//
//    public static final RegistryObject<FoliagePlacerType<RadiantSmallTreeFoliagePlacer>> RADIANT_TREE_SMALL_FOLIAGE_PLACER = DEFERRED_REGISTER.register("radiant_tree_small_foliage",()->
//            RADIANT_SMALL_PLACER);
}
