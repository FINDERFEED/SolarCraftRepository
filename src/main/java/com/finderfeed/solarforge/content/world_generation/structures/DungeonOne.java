package com.finderfeed.solarforge.content.world_generation.structures;

import com.finderfeed.solarforge.content.world_generation.structures.dungeon_one_key_lock.DungeonOnePieces;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;

import java.util.Optional;


public class DungeonOne extends Structure {

    public static final Codec<DungeonOne> CODEC = simpleCodec(DungeonOne::new);

    protected DungeonOne(StructureSettings p_226558_) {
        super(p_226558_);
    }

    private static void generatePieces(StructurePiecesBuilder p_197089_, GenerationContext ctx) {
        int x = (ctx.chunkPos().x << 4) + 7;
        int z = (ctx.chunkPos().z << 4) + 7;
//        int surfaceY = ctx.chunkGenerator().getBaseHeight(x,z, Heightmap.Types.WORLD_SURFACE_WG,ctx.heightAccessor());
        BlockPos blockpos = new BlockPos(x, 90, z);
        Rotation rotation = Rotation.getRandom(ctx.random());
        DungeonOnePieces.start(ctx.structureTemplateManager(), blockpos, rotation, p_197089_);
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
        return SolarForgeStructures.DUNGEON_KEY_LOCK;
    }


}

