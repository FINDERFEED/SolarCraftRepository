package com.finderfeed.solarforge.content.world_generation.features;

import com.finderfeed.solarforge.helpers.Helpers;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
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

import java.util.Random;

public class UlderaPylonFeature extends Feature<NoneFeatureConfiguration> {

    private static final ResourceLocation FEATURE = new ResourceLocation("solarforge:worldgen_features/uldera_pylon");
    public UlderaPylonFeature(Codec<NoneFeatureConfiguration> p_65786_) {
        super(p_65786_);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> ctx) {

        WorldGenLevel world = ctx.level();
        RandomSource random = ctx.random();
        BlockPos pos = ctx.origin();
        Rotation rot = Rotation.getRandom(world.getRandom());
        StructureTemplateManager manager = world.getLevel().getStructureManager();

        StructureTemplate templ = manager.getOrCreate(FEATURE);



        StructurePlaceSettings set = new StructurePlaceSettings().addProcessor(BlockIgnoreProcessor.AIR).setRandom(random).setRotation(rot).setBoundingBox(BoundingBox.infinite());

        BlockPos pos1 = findFlatChunkPosition(world,pos,5);

        if (!pos1.equals(Helpers.NULL_POS)) {

            BlockPos blockpos1 = templ.getZeroPositionWithTransform(pos1.offset(-6,1,-6), Mirror.NONE, rot).below(6);
            templ.placeInWorld(world, blockpos1, blockpos1, set, random, 4);
        }
        return true;
    }

    public static BlockPos findFlatChunkPosition(WorldGenLevel world, BlockPos mainpos, int blockDiameter){
        int trueDiameter = blockDiameter-1;

        for (int i = 0;i <= 16-trueDiameter;i++){
            for (int g = 0;g <= 16-trueDiameter;g++){
                BlockPos iteratorf = mainpos.offset(i,0,g);
                BlockPos iterator = new BlockPos(iteratorf.getX(),world.getHeight(Heightmap.Types.WORLD_SURFACE_WG,mainpos.getX()+i,mainpos.getZ()+g)-1,iteratorf.getZ());
                if (checkIfFlat(world,iterator,trueDiameter)){
                    return iterator;
                }
            }
        }
        return Helpers.NULL_POS;
    }

    public static boolean checkIfFlat(WorldGenLevel world,BlockPos whereToCheck,int diameter){
        for (int i = 0;i <= diameter;i++){
            for (int g = 0;g <= diameter;g++){

                if ((world.getBlockState(whereToCheck.offset(i,1,g)).getBlock() != Blocks.AIR) ||
                        (world.getBlockState(whereToCheck.offset(i,0,g)).getBlock() == Blocks.AIR) ||
                        (world.getBlockState(whereToCheck.offset(i,0,g)).getBlock() instanceof LiquidBlock)){
                    return false;
                }
            }
        }
        return true;
    }
}
