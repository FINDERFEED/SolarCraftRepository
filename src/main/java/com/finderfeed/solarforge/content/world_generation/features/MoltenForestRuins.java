package com.finderfeed.solarforge.content.world_generation.features;

import com.finderfeed.solarforge.Helpers;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockIgnoreProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MoltenForestRuins extends Feature<NoneFeatureConfiguration> {
    private static final ResourceLocation RUINS1 = new ResourceLocation("solarforge:worldgen_features/ruins1");
    private static final ResourceLocation RUINS2 = new ResourceLocation("solarforge:worldgen_features/ruins2");
    private static final ResourceLocation RUINS3 = new ResourceLocation("solarforge:worldgen_features/ruins3");
    public MoltenForestRuins(Codec<NoneFeatureConfiguration> p_i231953_1_) {
        super(p_i231953_1_);
    }



    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> ctx) {

        BlockPos pos = ctx.origin();
        WorldGenLevel world = ctx.level();
        Random random = ctx.random();

        Rotation rot = Rotation.getRandom(random);
        StructureManager manager = world.getLevel().getStructureManager();
        ResourceLocation[] LOC = {RUINS1,RUINS2,RUINS3};
        StructureTemplate templ = manager.getOrCreate(LOC[random.nextInt(3)]);
        StructurePlaceSettings set = new StructurePlaceSettings().addProcessor(BlockIgnoreProcessor.AIR).setRandom(random).setRotation(rot).setBoundingBox(BoundingBox.infinite());
        BlockPos pos1 = findFlatChunkPosition(world,pos,4, Blocks.GRASS_BLOCK);

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
                BlockPos iteratorf = mainpos.offset(i,0,g);
                BlockPos iterator = new BlockPos(iteratorf.getX(),world.getHeight(Heightmap.Types.WORLD_SURFACE_WG,mainpos.getX()+i,mainpos.getZ()+g)-1,iteratorf.getZ());
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
