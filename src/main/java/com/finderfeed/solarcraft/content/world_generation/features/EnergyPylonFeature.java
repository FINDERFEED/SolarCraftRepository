package com.finderfeed.solarcraft.content.world_generation.features;

import com.finderfeed.solarcraft.SolarCraft;
import com.finderfeed.solarcraft.helpers.Helpers;
import com.finderfeed.solarcraft.config.SolarcraftConfig;
import com.mojang.serialization.Codec;

import net.minecraft.core.Holder;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.biome.Biome;
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

import java.util.HashMap;
import java.util.Map;

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

//        if (random.nextFloat() > 1.0f/SolarcraftConfig.ENERGY_PYLON_SPAWN_CHANCE.get()) return false;
        WorldGenLevel world = ctx.level();
        BlockPos pos = ctx.origin();

        Holder<Biome> biomeHolder = world.getBiome(pos);

        if (biomeHolder.is(BiomeTags.IS_OCEAN) || biomeHolder.is(BiomeTags.IS_DEEP_OCEAN)) return false;

        Rotation rot = Rotation.NONE;
        StructureTemplateManager manager = world.getLevel().getStructureManager();

        StructureTemplate templ = manager.getOrCreate(LOCATIONS[world.getRandom().nextInt(LOCATIONS.length)]);



        StructurePlaceSettings set = new StructurePlaceSettings().addProcessor(BlockIgnoreProcessor.AIR).setRandom(random).setRotation(rot)
                .setBoundingBox(BoundingBox.infinite());

//        BlockPos pos1 = findFlatChunkPosition(world,pos,5);
        BlockPos pos1 = findMostFlatPosition(world,pos);

        if (!pos1.equals(Helpers.NULL_POS)) {

            BlockPos blockpos1 = templ.getZeroPositionWithTransform(pos1.offset(0,1,0), Mirror.NONE, rot);
            templ.placeInWorld(world, blockpos1, blockpos1, set, random, 4);
            this.generatePlatformBeneath(world,blockpos1.below());
        }
        return true;
    }

    private void generatePlatformBeneath(WorldGenLevel level,BlockPos spawnPos){
        BlockState mostCommonState = this.getMostCommonBlockstate(level,spawnPos);
        if (mostCommonState.getBlock() instanceof SnowyDirtBlock){
            mostCommonState = Blocks.DIRT.defaultBlockState();
        }
        for (int x = 0; x < 5; x++){
            for (int z = 0; z < 5; z++){
                for (int y = 0; y < 2; y++){
                    if (!isInRadius(x,y,z)) continue;

                    BlockPos setBlockPos = spawnPos.offset(x,-y,z);
                    BlockState state = level.getBlockState(setBlockPos);
                    if (this.isBlockReplaceable(state)){
                        level.setBlock(setBlockPos,mostCommonState,4);
                        if (level.getBlockState(setBlockPos.below()).getBlock() instanceof SnowyDirtBlock){
                            level.setBlock(setBlockPos.below(),Blocks.DIRT.defaultBlockState(),4);
                        }
                    }
                }
            }
        }
    }

    private BlockState getMostCommonBlockstate(WorldGenLevel level,BlockPos spawnPos){
        Map<BlockState,Integer> states = new HashMap<>();
        int currentCount = 0;
        BlockState finalState = Blocks.DIRT.defaultBlockState();
        for (int x = 0; x < 5; x++){
            for (int z = 0; z < 5; z++){
                for (int y = 0; y < 2; y++){
                    if (!isInRadius(x,y,z)) continue;
                    BlockPos setBlockPos = spawnPos.offset(x,-y,z);
                    BlockState state = level.getBlockState(setBlockPos);
                    if (!this.isBlockReplaceable(state)){
                        int statesCount;
                        if (states.containsKey(state)){
                            states.put(state,statesCount = states.get(state)+1);
                        }else{
                            states.put(state,statesCount = 1);
                        }
                        if (statesCount > currentCount){
                            currentCount = statesCount;
                            finalState = state;
                        }
                    }
                }
            }
        }
        return finalState;
    }

    private boolean isInRadius(int x,int y,int z){
        float rad = -2.7f * (0.25f*y) + 2.7f;
        float xt = x - 2;
        float zt = z - 2;
        return !(xt*xt + zt*zt > rad*rad);
    }



    private boolean isBlockReplaceable(BlockState state){
        Block block = state.getBlock();
        return block == Blocks.AIR || block instanceof BushBlock || block instanceof SnowLayerBlock || block instanceof LiquidBlock;

    }



    private BlockPos findMostFlatPosition(WorldGenLevel level,BlockPos genPos){
        int currentIncorrect = Integer.MAX_VALUE;
        BlockPos bestPos = Helpers.NULL_POS;
        for (int x = 0; x < 11;x++){
            for (int z = 0; z < 11;z++){
                int xt = genPos.getX() + x;
                int zt = genPos.getZ() + z;
                BlockPos toTest = new BlockPos(xt,level.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,xt,zt),zt).below();
                int flatness = this.testFlatness(level,toTest);
                if (flatness < 20 && flatness < currentIncorrect && flatness != -1){
                    currentIncorrect = flatness;
                    bestPos = toTest;
                }
            }
        }
        return bestPos;
    }

    private int testFlatness(WorldGenLevel level,BlockPos test){
        int incorrect = 0;
        for (int x = 0; x < 5;x++){
            for (int z = 0; z < 5;z++){
                BlockPos testPos = test.offset(x,0,z);
                BlockState current = level.getBlockState(testPos);
                BlockState above = level.getBlockState(testPos.above());
                int testResult = testBlocks(current,above);
                if (testResult == 0){
                    incorrect++;
                }else if (testResult == -1){
                    return -1;
                }
            }
        }
        return incorrect;
    }

    private int testBlocks(BlockState current,BlockState above){
        Block bcurrent = current.getBlock();
        Block babove = above.getBlock();
        if (bcurrent instanceof LiquidBlock){
            return -1;
        }
        return (babove == Blocks.AIR || babove instanceof BushBlock || babove instanceof SnowLayerBlock)
                && (bcurrent != Blocks.AIR) ? 1 : 0;
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
