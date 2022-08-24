package com.finderfeed.solarforge.content.world_generation.structures.charging_station;

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

public class ChargingStationStructure extends StructureFeature<NoneFeatureConfiguration> {
    public ChargingStationStructure(Codec<NoneFeatureConfiguration> p_197165_) {
        super(p_197165_,PieceGeneratorSupplier.simple(PieceGeneratorSupplier.checkForBiomeOnTop(Heightmap.Types.WORLD_SURFACE_WG), ChargingStationStructure::generatePieces));
    }


    private static void generatePieces(StructurePiecesBuilder p_197089_, PieceGenerator.Context<NoneFeatureConfiguration> ctx) {
        int x = (ctx.chunkPos().x << 4) + 7;
        int z = (ctx.chunkPos().z << 4) + 7;
        int surfaceY = ctx.chunkGenerator().getBaseHeight(x,z, Heightmap.Types.WORLD_SURFACE_WG,ctx.heightAccessor());
        BlockPos blockpos = new BlockPos(x, surfaceY, z);
        Rotation rotation = Rotation.getRandom(ctx.random());
        ChargingStationPieces.start(ctx.structureManager(), blockpos, rotation, p_197089_, ctx.random());
    }

    @Override
    public GenerationStep.Decoration step() {
        return GenerationStep.Decoration.SURFACE_STRUCTURES;
    }
}
