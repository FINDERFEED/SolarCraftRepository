package com.finderfeed.solarforge.content.world_generation.structures.magician_tower;

import com.mojang.serialization.Codec;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.structure.pieces.PieceGenerator;
import net.minecraft.world.level.levelgen.structure.pieces.PieceGeneratorSupplier;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;

public class MagicianTowerStructure extends StructureFeature<NoneFeatureConfiguration> {
    public MagicianTowerStructure(Codec<NoneFeatureConfiguration> p_197165_) {
        super(p_197165_,PieceGeneratorSupplier.simple(PieceGeneratorSupplier.checkForBiomeOnTop(Heightmap.Types.WORLD_SURFACE_WG), MagicianTowerStructure::generatePieces));
    }


    private static void generatePieces(StructurePiecesBuilder p_197089_, PieceGenerator.Context<NoneFeatureConfiguration> ctx) {
        int x = (ctx.chunkPos().x << 4) + 7;
        int z = (ctx.chunkPos().z << 4) + 7;
        int surfaceY = ctx.chunkGenerator().getBaseHeight(x,z, Heightmap.Types.WORLD_SURFACE_WG,ctx.heightAccessor());
        BlockPos blockpos = new BlockPos(x, surfaceY, z);
        Rotation rotation = Rotation.getRandom(ctx.random());
        MagicianTowerPieces.start(ctx.structureManager(), blockpos, rotation, p_197089_, ctx.random());
    }

    @Override
    public GenerationStep.Decoration step() {
        return GenerationStep.Decoration.SURFACE_STRUCTURES;
    }

//    public MagicianTowerStructure(Codec<NoneFeatureConfiguration> codec){
//        super(codec);
//    }
//
//    @Override
//    public StructureFeature.StructureStartFactory<NoneFeatureConfiguration> getStartFactory() {
//        return MagicianTowerStructure.Start::new;
//    }
//
//    @Override
//    public GenerationStep.Decoration step() {
//        return GenerationStep.Decoration.SURFACE_STRUCTURES;
//    }
//
//    @Override
//    protected boolean isFeatureChunk(ChunkGenerator chunkGenerator, BiomeSource biomeSource, long seed, WorldgenRandom chunkRandom,ChunkPos pos, Biome biome, ChunkPos chunkPos, NoneFeatureConfiguration featureConfig,LevelHeightAccessor access) {
//        BlockPos centerOfChunk = new BlockPos((pos.x << 4) + 7, 0, (pos.z << 4) + 7);
//        int landHeight = chunkGenerator.getFirstOccupiedHeight(centerOfChunk.getX(), centerOfChunk.getZ(), Heightmap.Types.WORLD_SURFACE_WG,access);
//        NoiseColumn columnOfBlocks = chunkGenerator.getBaseColumn(centerOfChunk.getX(), centerOfChunk.getZ(),access);
//        BlockState topBlock = columnOfBlocks.getBlockState(centerOfChunk.above(landHeight));
//        return topBlock.getFluidState().isEmpty();
//    }
//
//
//    public static class Start extends StructureStart<NoneFeatureConfiguration> {
//
//
//        public Start(StructureFeature<NoneFeatureConfiguration> p_163595_, ChunkPos p_163596_, int p_163597_, long p_163598_) {
//            super(p_163595_, p_163596_, p_163597_, p_163598_);
//        }
//
//
//        @Override
//        public void generatePieces(RegistryAccess dynamicRegistryManager, ChunkGenerator chunkGenerator, StructureManager templateManagerIn, ChunkPos pos, Biome biomeIn, NoneFeatureConfiguration config, LevelHeightAccessor accessor) {
//            Rotation rotation = Rotation.values()[this.random.nextInt(Rotation.values().length)];
//
//            // Turns the chunk coordinates into actual coordinates we can use. (Gets center of that chunk)
//            int x = (pos.x << 4) + 7;
//            int z = (pos.z << 4) + 7;
//
//            // Finds the y value of the terrain at location.
//            int surfaceY = chunkGenerator.getBaseHeight(x, z, Heightmap.Types.WORLD_SURFACE_WG,accessor);
//            BlockPos blockpos = new BlockPos(x, surfaceY, z);
//
//            // Now adds the structure pieces to this.components with all details such as where each part goes
//            // so that the structure can be added to the world by worldgen.
//            MagicianTowerPieces.start(templateManagerIn, blockpos, rotation, this.pieces, this.random);
//
//
//            // I use to debug and quickly find out if the structure is spawning or not and where it is.
//            //SolarForge.LOGGER.log(Level.DEBUG, "Structure at " + (blockpos.getX()) + " " + blockpos.getY() + " " + (blockpos.getZ()));
//        }
//    }
}
