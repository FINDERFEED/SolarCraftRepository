package com.finderfeed.solarcraft.content.blocks.progression_ores;

import com.finderfeed.solarcraft.client.particles.ball_particle.BallParticleOptions;
import com.finderfeed.solarcraft.client.particles.lightning_particle.LightningParticleOptions;
import com.finderfeed.solarcraft.content.blocks.primitive.ProgressionBlock;
import com.finderfeed.solarcraft.content.items.solar_lexicon.progressions.Progression;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import java.util.function.Supplier;

public class CorruptedShardOre extends ProgressionBlock {

    public CorruptedShardOre(Properties p_49795_, Supplier<Progression> prog, Block locked) {
        super(p_49795_, prog, locked);
    }



    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource rsrc) {
        for (int i = 0; i < 2; i ++) {
            Direction direction = Direction.getRandom(rsrc);
            Vec3i d = direction.getNormal();
            Vec3 v = new Vec3(
                    d.getX() == 1 ? d.getX() - (rsrc.nextInt(2) == 1 ? 1.2 : -0.2) : rsrc.nextFloat(),
                    d.getY() == 1 ? d.getY() - (rsrc.nextInt(2) == 1 ? 1.2 : -0.2) : rsrc.nextFloat(),
                    d.getZ() == 1 ? d.getZ() - (rsrc.nextInt(2) == 1 ? 1.2 : -0.2) : rsrc.nextFloat()
            ).add(pos.getX(), pos.getY(), pos.getZ());
            if (rsrc.nextFloat() > 0.5) {
                level.addParticle(BallParticleOptions.Builder.begin()
                        .setLifetime(60)
                        .setSize(0.25f)
                        .setRGB(90, 0, 186)
                        .setShouldShrink(true)
                        .setPhysics(false)
                        .build(), v.x, v.y, v.z, 0, 0, 0);
            } else {
                level.addParticle(new LightningParticleOptions(1f, 90, 0, 186, -1, 40, false)
                        , v.x, v.y, v.z, 0, 0, 0);
            }
        }
        super.animateTick(state, level, pos, rsrc);

    }
}
