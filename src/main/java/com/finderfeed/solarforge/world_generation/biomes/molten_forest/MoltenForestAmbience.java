package com.finderfeed.solarforge.world_generation.biomes.molten_forest;

import com.finderfeed.solarforge.Helpers;
import com.mojang.serialization.Codec;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
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
import net.minecraft.world.gen.feature.template.*;
import net.minecraftforge.common.util.Constants;
import org.apache.logging.log4j.core.tools.picocli.CommandLine;

import java.util.*;

public class MoltenForestAmbience extends Feature<NoFeatureConfig> {
    private static final ResourceLocation TREE = new ResourceLocation("solarforge:worldgen_features/burnt_forest_ambience1");
    private static final ResourceLocation TREE2 = new ResourceLocation("solarforge:worldgen_features/burnt_biome_ambience2");
    public MoltenForestAmbience(Codec<NoFeatureConfig> p_i231953_1_) {
        super(p_i231953_1_);
    }

    @Override
    public boolean place(ISeedReader world, ChunkGenerator chunkGenerator, Random random, BlockPos pos, NoFeatureConfig cfg) {

        Rotation rot = Rotation.getRandom(random);
        TemplateManager manager = world.getLevel().getStructureManager();
        List<ResourceLocation> list = new ArrayList<>();
        list.add(TREE);
        list.add(TREE2);
        Template templ = manager.getOrCreate(list.get(random.nextInt(2)));

        ChunkPos chunkpos = new ChunkPos(pos);

        PlacementSettings set = new PlacementSettings().addProcessor(BlockIgnoreStructureProcessor.AIR).setRandom(random).setRotation(rot).setBoundingBox(MutableBoundingBox.infinite());




        BlockPos pos1 = findFlatChunkPosition(world,pos,4,Blocks.GRASS_BLOCK);

        if (!pos1.equals(Helpers.NULL_POS)) {

            BlockPos blockpos1 = templ.getZeroPositionWithTransform(pos1.offset(0,1,0), Mirror.NONE, rot);
            templ.placeInWorld(world, blockpos1, blockpos1, set, random, 4);
        }
        return true;
    }

    public static BlockPos findFlatChunkPosition(ISeedReader world, BlockPos mainpos, int blockDiameter, Block block){
        int trueDiameter = blockDiameter-1;

        for (int i = 0;i <= 16-trueDiameter;i++){
            for (int g = 0;g <= 16-trueDiameter;g++){
                BlockPos iterator = mainpos.offset(i,world.getHeight(Heightmap.Type.WORLD_SURFACE_WG,mainpos.getX()+i,mainpos.getZ()+g)-1,g);
                if (checkIfFlat(world,iterator,block,trueDiameter)){
                    return iterator;
                }
            }
        }
        return Helpers.NULL_POS;
    }

    public static boolean checkIfFlat(ISeedReader world,BlockPos whereToCheck,Block block,int diameter){
        for (int i = 0;i <= diameter;i++){
            for (int g = 0;g <= diameter;g++){

                if (world.getBlockState(whereToCheck.offset(i,0,g)).getBlock() != block){
                    return false;
                }
            }
        }
        return true;
    }
}

