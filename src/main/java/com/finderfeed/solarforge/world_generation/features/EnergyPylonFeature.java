package com.finderfeed.solarforge.world_generation.features;

import com.finderfeed.solarforge.Helpers;
import com.mojang.serialization.Codec;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowingFluidBlock;
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
import net.minecraftforge.fluids.IFluidBlock;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EnergyPylonFeature extends Feature<NoFeatureConfig> {
    private static final ResourceLocation FEATURE = new ResourceLocation("solarforge:worldgen_features/energy_pylon");

    public EnergyPylonFeature(Codec<NoFeatureConfig> p_i231953_1_) {
        super(p_i231953_1_);
    }

    @Override
    public boolean place(ISeedReader world, ChunkGenerator chunkGenerator, Random random, BlockPos pos, NoFeatureConfig cfg) {

        Rotation rot = Rotation.getRandom(random);
        TemplateManager manager = world.getLevel().getStructureManager();
        List<ResourceLocation> list = new ArrayList<>();

        Template templ = manager.getOrCreate(FEATURE);



        PlacementSettings set = new PlacementSettings().addProcessor(BlockIgnoreStructureProcessor.AIR).setRandom(random).setRotation(rot).setBoundingBox(MutableBoundingBox.infinite());

        BlockPos pos1 = findFlatChunkPosition(world,pos,5);

        if (!pos1.equals(Helpers.NULL_POS)) {

            BlockPos blockpos1 = templ.getZeroPositionWithTransform(pos1.offset(0,1,0), Mirror.NONE, rot);
            templ.placeInWorld(world, blockpos1, blockpos1, set, random, 4);
        }
        return true;
    }

    public static BlockPos findFlatChunkPosition(ISeedReader world, BlockPos mainpos, int blockDiameter){
        int trueDiameter = blockDiameter-1;

        for (int i = 0;i <= 16-trueDiameter;i++){
            for (int g = 0;g <= 16-trueDiameter;g++){
                BlockPos iterator = mainpos.offset(i,world.getHeight(Heightmap.Type.WORLD_SURFACE_WG,mainpos.getX()+i,mainpos.getZ()+g)-1,g);
                if (checkIfFlat(world,iterator,trueDiameter)){
                    return iterator;
                }
            }
        }
        return Helpers.NULL_POS;
    }

    public static boolean checkIfFlat(ISeedReader world,BlockPos whereToCheck,int diameter){
        for (int i = 0;i <= diameter;i++){
            for (int g = 0;g <= diameter;g++){

                if ((world.getBlockState(whereToCheck.offset(i,1,g)).getBlock() != Blocks.AIR) ||
                        (world.getBlockState(whereToCheck.offset(i,0,g)).getBlock() == Blocks.AIR) ||
                        (world.getBlockState(whereToCheck.offset(i,0,g)).getBlock() instanceof FlowingFluidBlock)){
                    return false;
                }
            }
        }
        return true;
    }
}
