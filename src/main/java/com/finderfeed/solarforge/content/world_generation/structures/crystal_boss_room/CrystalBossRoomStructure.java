package com.finderfeed.solarforge.content.world_generation.structures.crystal_boss_room;

import com.finderfeed.solarforge.content.world_generation.structures.SolarForgeStructures;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pieces.PieceGenerator;
import net.minecraft.world.level.levelgen.structure.pieces.PieceGeneratorSupplier;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;

import java.util.Optional;

public class CrystalBossRoomStructure extends Structure {

    public static final Codec<CrystalBossRoomStructure> CODEC = simpleCodec(CrystalBossRoomStructure::new);

    protected CrystalBossRoomStructure(StructureSettings p_226558_) {
        super(p_226558_);
    }

    private static void generatePieces(StructurePiecesBuilder p_197089_, GenerationContext ctx) {
        int x = (ctx.chunkPos().x << 4) + 7;
        int z = (ctx.chunkPos().z << 4) + 7;
//        int surfaceY = ctx.chunkGenerator().getBaseHeight(x,z, Heightmap.Types.WORLD_SURFACE_WG,ctx.heightAccessor());
        BlockPos blockpos = new BlockPos(x, 90, z);
        Rotation rotation = Rotation.getRandom(ctx.random());
        CrystalBossRoomStructurePieces.start(ctx.structureTemplateManager(), blockpos, rotation, p_197089_);
    }

    @Override
    public GenerationStep.Decoration step() {
        return GenerationStep.Decoration.SURFACE_STRUCTURES;
    }

    @Override
    public Optional<GenerationStub> findGenerationPoint(GenerationContext ctx) {
        return onTopOfChunkCenter(ctx, Heightmap.Types.WORLD_SURFACE_WG, (p_227598_) -> {
            generatePieces(p_227598_, ctx);
        });
    }

    @Override
    public StructureType<?> type() {
        return SolarForgeStructures.CRYSTAL_BOSS_ROOM_STRUCTURE_STRUCTURE_TYPE;
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
//        public void generatePieces(RegistryAccess dynamicRegistryManager, ChunkGenerator chunkGenerator, StructureTemplateManager templateManagerIn, ChunkPos pos, Biome biomeIn, NoneFeatureConfiguration config, LevelHeightAccessor a) {
//            Rotation rotation = Rotation.values()[this.random.nextInt(Rotation.values().length)];
//            int x = (pos.x << 4) + 7;
//            int z = (pos.z << 4) + 7;
//            int surfaceY = chunkGenerator.getBaseHeight(x, z, Heightmap.Types.WORLD_SURFACE_WG,a);
//            BlockPos blockpos = new BlockPos(x, surfaceY, z);
//            CrystalBossRoomStructurePieces.start(templateManagerIn, blockpos, rotation, this.pieces, this.random);
//        }
//    }
}
