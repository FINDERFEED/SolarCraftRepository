package com.finderfeed.solarforge.content.blocks.primitive;

import com.finderfeed.solarforge.client.particles.SolarcraftParticleTypes;
import com.finderfeed.solarforge.helpers.ClientHelpers;
import com.finderfeed.solarforge.helpers.Helpers;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Random;

public class ThrownLightBlock extends Block {

    public static final VoxelShape SHAPE = Block.box(6.0D, 6.0D, 6.0D, 10.0D, 10.0D, 10.0D);

    public ThrownLightBlock(Properties p_49795_) {
        super(p_49795_);
    }


    @Override
    public VoxelShape getShape(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
        return SHAPE;
    }

    @Override
    public RenderShape getRenderShape(BlockState p_60550_) {
        return RenderShape.INVISIBLE;
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        double rx = random.nextDouble() * 0.5 - 0.25f;
        double ry = random.nextDouble() * 0.5 - 0.25f;
        double rz = random.nextDouble() * 0.5 - 0.25f;
        Vec3 p = Helpers.getBlockCenter(pos).add(rx,ry,rz);
        ClientHelpers.Particles.createParticle(SolarcraftParticleTypes.SMALL_SOLAR_STRIKE_PARTICLE.get(),
                p.x,p.y,p.z,0,0.05,0,random.nextInt(20) + 200,random.nextInt(20) + 200,20,
                random.nextFloat() * 0.25f + 0.25f);

        super.animateTick(state, level, pos, random);
    }

}
