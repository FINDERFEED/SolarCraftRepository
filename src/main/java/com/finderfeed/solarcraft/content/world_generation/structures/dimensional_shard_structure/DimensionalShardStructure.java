package com.finderfeed.solarcraft.content.world_generation.structures.dimensional_shard_structure;

import com.finderfeed.solarcraft.content.world_generation.structures.SolarcraftStructureTypes;
import com.mojang.serialization.Codec;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;

import java.util.Optional;

public class DimensionalShardStructure extends Structure {

    public static final Codec<DimensionalShardStructure> CODEC = simpleCodec(DimensionalShardStructure::new);

    public DimensionalShardStructure(StructureSettings p_226558_) {
        super(p_226558_);
    }

    private static void generatePieces(StructurePiecesBuilder p_197089_, GenerationContext ctx) {
        int x = (ctx.chunkPos().x << 4) + 7 - 15;
        int z = (ctx.chunkPos().z << 4) + 7 - 15;
//        int surfaceY = ctx.chunkGenerator().getBaseHeight(x,z, Heightmap.Types.WORLD_SURFACE_WG,ctx.heightAccessor());
        int y = ctx.chunkGenerator().getBaseHeight(x,z, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                ctx.heightAccessor(), ctx.randomState());
        BlockPos blockpos = new BlockPos(x, y, z);
        Rotation rotation = Rotation.NONE;
        DimStructPieces.start(ctx.structureTemplateManager(), blockpos, rotation, p_197089_);
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
        return SolarcraftStructureTypes.DIMENSIONAL_SHARD_STRUCTURE_STRUCTURE_TYPE.get();
    }


    //    public DimensionalShardStructure(Codec<NoneFeatureConfiguration> codec){
//        super(codec);
//    }
//
//    @Override
//    public StructureFeature.StructureStartFactory<NoneFeatureConfiguration> getStartFactory() {
//        return DimensionalShardStructure.Start::new;
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
//        public void generatePieces(RegistryAccess dynamicRegistryManager, ChunkGenerator chunkGenerator, StructureTemplateManager templateManagerIn, ChunkPos pos, Biome biomeIn, NoneFeatureConfiguration config,LevelHeightAccessor a) {
//            Rotation rotation = Rotation.NONE;
//
//            // Turns the chunk coordinates into actual coordinates we can use. (Gets center of that chunk)
//            int x = (pos.x << 4) + 7;
//            int z = (pos.z << 4) + 7;
//
//            // Finds the y value of the terrain at location.
//
//            int surfaceY = chunkGenerator.getBaseHeight(x, z, Heightmap.Types.WORLD_SURFACE_WG,a);
//            BlockPos blockpos = new BlockPos(x, surfaceY, z);
//
//            // Now adds the structure pieces to this.components with all details such as where each part goes
//            // so that the structure can be added to the world by worldgen.
//            DimStructPieces.start(templateManagerIn, blockpos, rotation, this.pieces, this.random);
//
//
//        }
//    }
}
