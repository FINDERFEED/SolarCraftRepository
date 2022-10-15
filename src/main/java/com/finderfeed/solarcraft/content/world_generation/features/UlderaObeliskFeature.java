package com.finderfeed.solarcraft.content.world_generation.features;

import com.finderfeed.solarcraft.SolarCraft;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockIgnoreProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

public class UlderaObeliskFeature extends Feature<NoneFeatureConfiguration> {

    public static final ResourceLocation OBELISK_LOCATION = new ResourceLocation(SolarCraft.MOD_ID,"worldgen_features/uldera_obelisk_sneak_peek");

    public UlderaObeliskFeature(Codec<NoneFeatureConfiguration> c) {
        super(c);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> ctx) {
        WorldGenLevel world = ctx.level();

        BlockPos pos = ctx.origin();
        int y = ctx.level().getHeight(Heightmap.Types.WORLD_SURFACE_WG,pos.getX(),pos.getZ());
        pos = new BlockPos(pos.getX(),y,pos.getZ()).above(30 + ctx.random().nextInt(10));
        RandomSource random = ctx.random();


        Rotation rot = Rotation.getRandom(random);
        StructureTemplateManager manager = world.getLevel().getStructureManager();
        StructureTemplate templ = manager.getOrCreate(OBELISK_LOCATION);
        StructurePlaceSettings set = new StructurePlaceSettings().addProcessor(BlockIgnoreProcessor.AIR).setRandom(random).setRotation(rot).setBoundingBox(BoundingBox.infinite());
        BlockPos blockpos1 = templ.getZeroPositionWithTransform(pos.offset(0,1,0), Mirror.NONE, rot);

        templ.placeInWorld(world, blockpos1, blockpos1, set, random, 4);
        return true;
    }
}
