package com.finderfeed.solarcraft.content.world_generation.features.foliage_placers;

import com.finderfeed.solarcraft.SolarCraft;
import com.finderfeed.solarcraft.content.world_generation.dimension_related.radiant_land.RadiantSmallTreeFoliagePlacer;
import com.finderfeed.solarcraft.content.world_generation.dimension_related.radiant_land.RadiantTreeFoliagePlacer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class FoliagePlacerRegistry {
    public static final DeferredRegister<FoliagePlacerType<?>> DEFERRED_REGISTER = DeferredRegister.create(BuiltInRegistries.FOLIAGE_PLACER_TYPE, SolarCraft.MOD_ID);

    public static final DeferredHolder<FoliagePlacerType<?>,FoliagePlacerType<BurntTreeFoliagePlacer>> BURNT_TREE_PLACER = DEFERRED_REGISTER.register("burnt_tree_foliage",()->
            new FoliagePlacerType<>(BurntTreeFoliagePlacer.CODEC));

    public static final DeferredHolder<FoliagePlacerType<?>,FoliagePlacerType<RadiantTreeFoliagePlacer>> RADIANT_PLACER = DEFERRED_REGISTER.register("radiant_tree_foliage",()->
            new FoliagePlacerType<>(RadiantTreeFoliagePlacer.CODEC));

    public static final DeferredHolder<FoliagePlacerType<?>,FoliagePlacerType<RadiantSmallTreeFoliagePlacer>> RADIANT_SMALL_PLACER = DEFERRED_REGISTER.register("radiant_tree_small_foliage",()->
            new FoliagePlacerType<>(RadiantSmallTreeFoliagePlacer.CODEC));
}
