package com.finderfeed.solarforge.content.world_generation.features;

import com.finderfeed.solarforge.SolarForge;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockIgnoreProcessor;

import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

import java.util.Random;

public class ClearingCrystalFeature extends Feature<NoneFeatureConfiguration> {

    public static final ResourceLocation CRYSTAL_LOCATION = new ResourceLocation(SolarForge.MOD_ID,"worldgen_features/clearing_crystal");

    public ClearingCrystalFeature(Codec<NoneFeatureConfiguration> c) {
        super(c);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> ctx) {
        WorldGenLevel world = ctx.level();

        BlockPos pos = ctx.origin();

        RandomSource random = ctx.random();


        Rotation rot = Rotation.getRandom(random);
        StructureTemplateManager manager = world.getLevel().getStructureManager();
        StructureTemplate templ = manager.getOrCreate(CRYSTAL_LOCATION);
        StructurePlaceSettings set = new StructurePlaceSettings().addProcessor(BlockIgnoreProcessor.AIR).setRandom(random).setRotation(rot).setBoundingBox(BoundingBox.infinite());
        BlockPos blockpos1 = templ.getZeroPositionWithTransform(pos.offset(-1,0,-1), Mirror.NONE, rot);
        templ.placeInWorld(world, blockpos1, blockpos1, set, random, 4);

        return true;
    }
}
