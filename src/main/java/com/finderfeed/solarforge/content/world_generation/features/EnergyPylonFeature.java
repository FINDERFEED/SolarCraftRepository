package com.finderfeed.solarforge.content.world_generation.features;

import com.finderfeed.solarforge.Helpers;
import com.finderfeed.solarforge.config.SolarcraftConfig;
import com.mojang.serialization.Codec;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.core.BlockPos;

import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockIgnoreProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;
import net.minecraftforge.fluids.IFluidBlock;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EnergyPylonFeature extends Feature<NoneFeatureConfiguration> {
    private static final ResourceLocation FEATURE = new ResourceLocation("solarforge:worldgen_features/energy_pylon");
    private static final ResourceLocation[] LOCATIONS = {
      new ResourceLocation("solarforge:worldgen_features/energy_pylon_ardo"),
            new ResourceLocation("solarforge:worldgen_features/energy_pylon_fira"),
            new ResourceLocation("solarforge:worldgen_features/energy_pylon_tera"),
            new ResourceLocation("solarforge:worldgen_features/energy_pylon_zeta"),
            new ResourceLocation("solarforge:worldgen_features/energy_pylon_kelda"),
            new ResourceLocation("solarforge:worldgen_features/energy_pylon_urba"),
            new ResourceLocation("solarforge:worldgen_features/energy_pylon_giro"),
            new ResourceLocation("solarforge:worldgen_features/energy_pylon_ultima"),
    };

    public EnergyPylonFeature(Codec<NoneFeatureConfiguration> p_i231953_1_) {
        super(p_i231953_1_);
    }



    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> ctx) {
        Random random = ctx.random();
        if (random.nextFloat() > 1.0f/SolarcraftConfig.ENERGY_PYLON_SPAWN_CHANCE.get()) return false;

        WorldGenLevel world = ctx.level();
        BlockPos pos = ctx.origin();
        Rotation rot = Rotation.NONE;
        StructureManager manager = world.getLevel().getStructureManager();

        StructureTemplate templ = manager.getOrCreate(LOCATIONS[world.getRandom().nextInt(LOCATIONS.length)]);



        StructurePlaceSettings set = new StructurePlaceSettings().addProcessor(BlockIgnoreProcessor.AIR).setRandom(random).setRotation(rot).setBoundingBox(BoundingBox.infinite());

        BlockPos pos1 = findFlatChunkPosition(world,pos,5);

        if (!pos1.equals(Helpers.NULL_POS)) {

            BlockPos blockpos1 = templ.getZeroPositionWithTransform(pos1.offset(0,1,0), Mirror.NONE, rot);
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
