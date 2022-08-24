package com.finderfeed.solarforge.content.world_generation.features.carvers;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.chunk.CarvingMask;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.Aquifer;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import net.minecraft.world.level.levelgen.carver.CarvingContext;
import net.minecraft.world.level.levelgen.carver.CaveCarverConfiguration;
import net.minecraft.world.level.levelgen.carver.WorldCarver;
import net.minecraft.world.level.levelgen.synth.PerlinNoise;
import org.apache.commons.lang3.mutable.MutableBoolean;

import java.util.List;
import java.util.Random;
import java.util.function.Function;

public class TestCarver extends WorldCarver<CaveCarverConfiguration> {

    public PerlinNoise noise;

    public TestCarver(Codec<CaveCarverConfiguration> p_159366_) {
        super(p_159366_);
        this.noise = PerlinNoise.create(new LegacyRandomSource(new Random().nextLong()), 3,1.0,0.5,0.34312);
    }

    @Override
    public boolean carve(CarvingContext ctx, CaveCarverConfiguration conf, ChunkAccess mainchunk, Function<BlockPos, Holder<Biome>> biomegetter,
                         Random rnd, Aquifer uselessshit, ChunkPos pos, CarvingMask mask) {

        int xcpos = pos.getBlockX(0);
        int zcpos = pos.getBlockZ(0);
        BlockPos.MutableBlockPos p = new BlockPos.MutableBlockPos();
        for (int x = xcpos; x <= xcpos + 16;x++){
            for (int y = -50; y <= 100;y++){
                for (int z = zcpos; z <= zcpos + 16;z++){
                    double val = noise.getValue(x,y,z)*10;
                    if (val > -0.1 && val < 0.1){
                        p.set(x,y,z);
                        mainchunk.setBlockState(p, Blocks.CAVE_AIR.defaultBlockState(),false);
                    }
                }
            }
        }

        return true;
    }

    @Override
    public boolean isStartChunk(CaveCarverConfiguration c, Random rnd) {
        return rnd.nextDouble() < c.probability;
    }
}
