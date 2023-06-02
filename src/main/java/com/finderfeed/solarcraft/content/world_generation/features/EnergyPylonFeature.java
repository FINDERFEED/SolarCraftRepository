package com.finderfeed.solarcraft.content.world_generation.features;

import com.finderfeed.solarcraft.SolarCraft;
import com.finderfeed.solarcraft.helpers.Helpers;
import com.finderfeed.solarcraft.config.SolarcraftConfig;
import com.mojang.serialization.Codec;

import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockIgnoreProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

public class EnergyPylonFeature extends Feature<NoneFeatureConfiguration> {

    private static final ResourceLocation[] LOCATIONS = {
      new ResourceLocation("solarcraft:worldgen_features/energy_pylon_ardo"),
            new ResourceLocation("solarcraft:worldgen_features/energy_pylon_fira"),
            new ResourceLocation("solarcraft:worldgen_features/energy_pylon_tera"),
            new ResourceLocation("solarcraft:worldgen_features/energy_pylon_zeta"),
            new ResourceLocation("solarcraft:worldgen_features/energy_pylon_kelda"),
            new ResourceLocation("solarcraft:worldgen_features/energy_pylon_urba"),
            new ResourceLocation("solarcraft:worldgen_features/energy_pylon_giro"),
            new ResourceLocation("solarcraft:worldgen_features/energy_pylon_ultima"),
    };

    public EnergyPylonFeature(Codec<NoneFeatureConfiguration> p_i231953_1_) {
        super(p_i231953_1_);
    }



    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> ctx) {
        RandomSource random = ctx.random();

        if (random.nextFloat() > 1.0f/SolarcraftConfig.ENERGY_PYLON_SPAWN_CHANCE.get()) return false;
        WorldGenLevel world = ctx.level();
        BlockPos pos = ctx.origin();
        Rotation rot = Rotation.NONE;
        StructureTemplateManager manager = world.getLevel().getStructureManager();

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
                Block above = world.getBlockState(whereToCheck.offset(i,1,g)).getBlock();
                Block current = world.getBlockState(whereToCheck.offset(i,0,g)).getBlock();
                if ((above != Blocks.AIR && !(above instanceof BushBlock) && !(above instanceof SnowLayerBlock))
                        || (current == Blocks.AIR) || (current instanceof LiquidBlock)){
                    return false;
                }
            }
        }
        return true;
    }
}
