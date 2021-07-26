package com.finderfeed.solarforge.world_generation.biomes.molten_forest;

import com.finderfeed.solarforge.Helpers;
import com.mojang.serialization.Codec;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import net.minecraft.world.level.block.Mirror;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.WorldGenLevel;

import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;


import java.util.*;

import net.minecraft.world.level.levelgen.structure.templatesystem.BlockIgnoreProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

public class MoltenForestAmbience extends Feature<NoneFeatureConfiguration> {
    private static final ResourceLocation TREE = new ResourceLocation("solarforge:worldgen_features/burnt_forest_ambience1");
    private static final ResourceLocation TREE2 = new ResourceLocation("solarforge:worldgen_features/burnt_biome_ambience2");
    public MoltenForestAmbience(Codec<NoneFeatureConfiguration> p_i231953_1_) {
        super(p_i231953_1_);
    }



    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> ctx) {

        BlockPos pos = ctx.origin();
        WorldGenLevel world = ctx.level();
        Random random = ctx.random();

        Rotation rot = Rotation.getRandom(random);
        StructureManager manager = world.getLevel().getStructureManager();
        List<ResourceLocation> list = new ArrayList<>();
        list.add(TREE);
        list.add(TREE2);
        StructureTemplate templ = manager.getOrCreate(list.get(random.nextInt(2)));

        ChunkPos chunkpos = new ChunkPos(pos);

        StructurePlaceSettings set = new StructurePlaceSettings().addProcessor(BlockIgnoreProcessor.AIR).setRandom(random).setRotation(rot).setBoundingBox(BoundingBox.infinite());




        BlockPos pos1 = findFlatChunkPosition(world,pos,4,Blocks.GRASS_BLOCK);

        if (!pos1.equals(Helpers.NULL_POS)) {

            BlockPos blockpos1 = templ.getZeroPositionWithTransform(pos1.offset(0,1,0), Mirror.NONE, rot);
            templ.placeInWorld(world, blockpos1, blockpos1, set, random, 4);
        }
        return true;
    }

    public static BlockPos findFlatChunkPosition(WorldGenLevel world, BlockPos mainpos, int blockDiameter, Block block){
        int trueDiameter = blockDiameter-1;

        for (int i = 0;i <= 16-trueDiameter;i++){
            for (int g = 0;g <= 16-trueDiameter;g++){
                BlockPos iterator = mainpos.offset(i,world.getHeight(Heightmap.Types.WORLD_SURFACE_WG,mainpos.getX()+i,mainpos.getZ()+g)-1,g);
                if (checkIfFlat(world,iterator,block,trueDiameter)){
                    return iterator;
                }
            }
        }
        return Helpers.NULL_POS;
    }

    public static boolean checkIfFlat(WorldGenLevel world,BlockPos whereToCheck,Block block,int diameter){
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

