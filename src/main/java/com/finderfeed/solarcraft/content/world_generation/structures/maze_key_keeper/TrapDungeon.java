package com.finderfeed.solarcraft.content.world_generation.structures.maze_key_keeper;

import com.finderfeed.solarcraft.content.world_generation.structures.SolarcraftStructureTypes;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;

import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;

import java.util.Optional;

public class TrapDungeon extends Structure {

    public static final Codec<TrapDungeon> CODEC = simpleCodec(TrapDungeon::new);

    public TrapDungeon(StructureSettings settings) {
        super(settings);
    }

    @Override
    public Optional<GenerationStub> findGenerationPoint(GenerationContext ctx) {
        return onTopOfChunkCenter(ctx, Heightmap.Types.WORLD_SURFACE_WG, (p_227598_) -> {
            generatePieces(p_227598_, ctx);
        });
    }

    private static void generatePieces(StructurePiecesBuilder p_197089_, GenerationContext ctx) {
        int x = (ctx.chunkPos().x << 4) + 7;
        int z = (ctx.chunkPos().z << 4) + 7;
        int y = ctx.chunkGenerator().getBaseHeight(x,z, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                ctx.heightAccessor(), ctx.randomState());
        BlockPos blockpos = new BlockPos(x, y, z);
        Rotation rotation = Rotation.getRandom(ctx.random());
        TrapStructurePieces.start(ctx.structureTemplateManager(), blockpos, rotation, p_197089_);
    }

    @Override
    public GenerationStep.Decoration step() {
        return GenerationStep.Decoration.SURFACE_STRUCTURES;
    }



    @Override
    public StructureType<?> type() {
        return SolarcraftStructureTypes.TRAP_DUNGEON.get();
    }

}
