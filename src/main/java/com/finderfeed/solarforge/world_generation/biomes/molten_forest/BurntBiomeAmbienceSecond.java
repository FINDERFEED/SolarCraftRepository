package com.finderfeed.solarforge.world_generation.biomes.molten_forest;

import com.mojang.serialization.Codec;
import net.minecraft.block.GrassBlock;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.template.BlockIgnoreStructureProcessor;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.feature.template.TemplateManager;

import java.util.Random;

public class BurntBiomeAmbienceSecond extends Feature<NoFeatureConfig> {
    private static final ResourceLocation TREE = new ResourceLocation("solarforge:worldgen_features/burnt_biome_ambience2");
    public BurntBiomeAmbienceSecond(Codec<NoFeatureConfig> p_i231953_1_) {
        super(p_i231953_1_);
    }

    @Override
    public boolean place(ISeedReader world, ChunkGenerator chunkGenerator, Random random, BlockPos pos, NoFeatureConfig cfg) {

        Rotation rot = Rotation.getRandom(random);
        TemplateManager manager = world.getLevel().getStructureManager();
        Template templ = manager.getOrCreate(TREE);

        ChunkPos chunkpos = new ChunkPos(pos);

        PlacementSettings set = new PlacementSettings().addProcessor(BlockIgnoreStructureProcessor.AIR).setRandom(random).setRotation(rot).setBoundingBox(MutableBoundingBox.infinite());



        BlockPos blockpos1 = templ.getZeroPositionWithTransform(pos.offset(0, 0,0), Mirror.NONE, rot);
        BlockPos[] poslits = {pos.below().south(2),
                pos.below().east(2),
                pos.below().east(2).south(2),
                pos.below()};
        boolean a = true;
        for (BlockPos hj : poslits){
            if ( !(world.getBlockState(hj).getBlock() instanceof GrassBlock)){

                a = false;
                break;
            }
        }

        if (a) {

            templ.placeInWorld(world, blockpos1, blockpos1, set, random, 4);
        }
        return true;
    }
}