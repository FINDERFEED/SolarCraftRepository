package com.finderfeed.solarforge.world_generation.structures.charging_station;

import com.mojang.serialization.Codec;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;

public class ChargingStationStructure extends StructureFeature<NoneFeatureConfiguration> {
    public ChargingStationStructure(Codec<NoneFeatureConfiguration> codec){
        super(codec);
    }

    @Override
    public StructureFeature.StructureStartFactory<NoneFeatureConfiguration> getStartFactory() {
        return ChargingStationStructure.Start::new;
    }

    @Override
    public GenerationStep.Decoration step() {
        return GenerationStep.Decoration.SURFACE_STRUCTURES;
    }

    @Override
    protected boolean isFeatureChunk(ChunkGenerator chunkGenerator, BiomeSource biomeSource, long seed, WorldgenRandom chunkRandom, int chunkX, int chunkZ, Biome biome, ChunkPos chunkPos, NoneFeatureConfiguration featureConfig) {
        BlockPos centerOfChunk = new BlockPos((chunkX << 4) + 7, 0, (chunkZ << 4) + 7);
        int landHeight = chunkGenerator.getFirstOccupiedHeight(centerOfChunk.getX(), centerOfChunk.getZ(), Heightmap.Types.WORLD_SURFACE_WG);
        BlockGetter columnOfBlocks = chunkGenerator.getBaseColumn(centerOfChunk.getX(), centerOfChunk.getZ());
        BlockState topBlock = columnOfBlocks.getBlockState(centerOfChunk.above(landHeight));
        return topBlock.getFluidState().isEmpty();
    }


    public static class Start extends StructureStart<NoneFeatureConfiguration> {

        public Start(StructureFeature<NoneFeatureConfiguration> structureIn, int chunkX, int chunkZ, BoundingBox mutableBoundingBox, int referenceIn, long seedIn) {
            super(structureIn, chunkX, chunkZ, mutableBoundingBox, referenceIn, seedIn);
        }

        @Override
        public void generatePieces(RegistryAccess dynamicRegistryManager, ChunkGenerator chunkGenerator, StructureManager templateManagerIn, int chunkX, int chunkZ, Biome biomeIn, NoneFeatureConfiguration config) {
            Rotation rotation = Rotation.values()[this.random.nextInt(Rotation.values().length)];

            // Turns the chunk coordinates into actual coordinates we can use. (Gets center of that chunk)
            int x = (chunkX << 4) + 7;
            int z = (chunkZ << 4) + 7;

            // Finds the y value of the terrain at location.
            int surfaceY = chunkGenerator.getBaseHeight(x, z, Heightmap.Types.WORLD_SURFACE_WG);
            BlockPos blockpos = new BlockPos(x, surfaceY, z);

            // Now adds the structure pieces to this.components with all details such as where each part goes
            // so that the structure can be added to the world by worldgen.
            ChargingStationPieces.start(templateManagerIn, blockpos, rotation, this.pieces, this.random);

            // Sets the bounds of the structure.
            this.calculateBoundingBox();

            // I use to debug and quickly find out if the structure is spawning or not and where it is.
            //SolarForge.LOGGER.log(Level.DEBUG, "Structure at " + (blockpos.getX()) + " " + blockpos.getY() + " " + (blockpos.getZ()));
        }
    }
}
