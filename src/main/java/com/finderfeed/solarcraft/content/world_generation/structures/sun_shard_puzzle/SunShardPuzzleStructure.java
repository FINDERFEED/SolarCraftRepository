package com.finderfeed.solarcraft.content.world_generation.structures.sun_shard_puzzle;

import com.finderfeed.solarcraft.content.world_generation.structures.SolarcraftStructureTypes;
import com.finderfeed.solarcraft.content.world_generation.structures.runic_elemental_arena.RunicElementalArenaStructure;
import com.finderfeed.solarcraft.content.world_generation.structures.runic_elemental_arena.RunicElementalArenaStructurePieces;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;

import java.util.Optional;

public class SunShardPuzzleStructure extends Structure {

    public static final Codec<SunShardPuzzleStructure> CODEC = simpleCodec(SunShardPuzzleStructure::new);

    public SunShardPuzzleStructure(StructureSettings p_226558_) {
        super(p_226558_);
    }

    private static void generatePieces(StructurePiecesBuilder p_197089_, GenerationContext ctx) {
        int x = (ctx.chunkPos().x << 4) + 7;
        int z = (ctx.chunkPos().z << 4) + 7;
        int y = ctx.chunkGenerator().getBaseHeight(x-17,z-10, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                ctx.heightAccessor(), ctx.randomState());
        BlockPos blockpos = new BlockPos(x-17, y, z-10);
        SunShardPuzzleStructurePieces.start(ctx.structureTemplateManager(), blockpos,Rotation.NONE, p_197089_);
    }

    @Override
    public GenerationStep.Decoration step() {
        return GenerationStep.Decoration.SURFACE_STRUCTURES;
    }


    @Override
    public Optional<GenerationStub> findGenerationPoint(Structure.GenerationContext ctx) {
        return onTopOfChunkCenter(ctx, Heightmap.Types.WORLD_SURFACE_WG, (p_227598_) -> {
            generatePieces(p_227598_, ctx);
        });
    }

    @Override
    public StructureType<?> type() {
        return SolarcraftStructureTypes.SUN_SHARD_PUZZLE_STRUCTURE_TYPE;
    }

}