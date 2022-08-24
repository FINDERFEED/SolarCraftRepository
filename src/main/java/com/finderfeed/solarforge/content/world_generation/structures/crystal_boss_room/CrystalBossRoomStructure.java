package com.finderfeed.solarforge.content.world_generation.structures.crystal_boss_room;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.pieces.PieceGenerator;
import net.minecraft.world.level.levelgen.structure.pieces.PieceGeneratorSupplier;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;

public class CrystalBossRoomStructure extends StructureFeature<NoneFeatureConfiguration> {
    public CrystalBossRoomStructure(Codec<NoneFeatureConfiguration> p_197165_) {
        super(p_197165_,  PieceGeneratorSupplier.simple(PieceGeneratorSupplier.checkForBiomeOnTop(Heightmap.Types.WORLD_SURFACE_WG), CrystalBossRoomStructure::generatePieces));
    }


    private static void generatePieces(StructurePiecesBuilder p_197089_, PieceGenerator.Context<NoneFeatureConfiguration> ctx) {
        int x = (ctx.chunkPos().x << 4) + 7;
        int z = (ctx.chunkPos().z << 4) + 7;
        int surfaceY = ctx.chunkGenerator().getBaseHeight(x,z, Heightmap.Types.WORLD_SURFACE_WG,ctx.heightAccessor());
        BlockPos blockpos = new BlockPos(x, surfaceY, z);
        Rotation rotation = Rotation.getRandom(ctx.random());
        CrystalBossRoomStructurePieces.start(ctx.structureManager(), blockpos, rotation, p_197089_, ctx.random());
    }

    @Override
    public GenerationStep.Decoration step() {
        return GenerationStep.Decoration.SURFACE_STRUCTURES;
    }
//    public CrystalBossRoomStructure(Codec<NoneFeatureConfiguration> codec){
//        super(codec);
//    }
//
//    @Override
//    public StructureFeature.StructureStartFactory<NoneFeatureConfiguration> getStartFactory() {
//        return CrystalBossRoomStructure.Start::new;
//    }
//
//    @Override
//    public GenerationStep.Decoration step() {
//        return GenerationStep.Decoration.SURFACE_STRUCTURES;
//    }
//
//    @Override
//    protected boolean isFeatureChunk(ChunkGenerator chunkGenerator, BiomeSource biomeSource, long seed, WorldgenRandom chunkRandom, ChunkPos pos, Biome biome, ChunkPos chunkPos, NoneFeatureConfiguration featureConfig, LevelHeightAccessor a) {
//        BlockPos centerOfChunk = new BlockPos((pos.x << 4) + 7, 0, (pos.z << 4) + 7);
//        int landHeight = chunkGenerator.getFirstOccupiedHeight(centerOfChunk.getX(), centerOfChunk.getZ(), Heightmap.Types.WORLD_SURFACE_WG,a);
//        NoiseColumn columnOfBlocks = chunkGenerator.getBaseColumn(centerOfChunk.getX(), centerOfChunk.getZ(),a);
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
//        @Override
//        public void generatePieces(RegistryAccess dynamicRegistryManager, ChunkGenerator chunkGenerator, StructureManager templateManagerIn, ChunkPos pos, Biome biomeIn, NoneFeatureConfiguration config, LevelHeightAccessor a) {
//            Rotation rotation = Rotation.values()[this.random.nextInt(Rotation.values().length)];
//            int x = (pos.x << 4) + 7;
//            int z = (pos.z << 4) + 7;
//            int surfaceY = chunkGenerator.getBaseHeight(x, z, Heightmap.Types.WORLD_SURFACE_WG,a);
//            BlockPos blockpos = new BlockPos(x, surfaceY, z);
//            CrystalBossRoomStructurePieces.start(templateManagerIn, blockpos, rotation, this.pieces, this.random);
//        }
//    }
}
