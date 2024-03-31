package com.finderfeed.solarcraft.content.blocks;

import com.finderfeed.solarcraft.local_library.helpers.FDMathHelper;
import com.finderfeed.solarcraft.registries.blocks.SCBlocks;
import com.finderfeed.solarcraft.registries.damage_sources.SCDamageSources;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RedStoneWireBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import org.jetbrains.annotations.Nullable;

public class HeaterBlock extends Block {

    public static BooleanProperty ACTIVE = BooleanProperty.create("active");
    public static BooleanProperty ALWAYS_ACTIVE = BooleanProperty.create("always_active");
    public HeaterBlock(Properties props) {
        super(props);
        this.registerDefaultState(this.getStateDefinition().any().setValue(ACTIVE,false).setValue(ALWAYS_ACTIVE,false));
    }


    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        BlockPos pos = ctx.getClickedPos();
        Level level = ctx.getLevel();
        return this.defaultBlockState().setValue(ACTIVE,level.hasNeighborSignal(pos));
    }

    @Override
    public void stepOn(Level level, BlockPos pos, BlockState state, Entity entity) {
        super.stepOn(level, pos, state, entity);
        if (!level.isClientSide && (state.getValue(ACTIVE) || state.getValue(ALWAYS_ACTIVE)) && entity instanceof LivingEntity && !entity.isInWater()){
            int bestSignal = level.getBestNeighborSignal(pos);
            float p = bestSignal / 15f;
            float damage = state.getValue(ALWAYS_ACTIVE) ? 2f : (float)Mth.clamp(2 * p,0.1,1);
            entity.hurt(SCDamageSources.HEAT, damage);
        }
    }

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos neighbor, boolean smth) {
        if (!level.isClientSide){
            if (level.hasNeighborSignal(pos)){
                level.setBlock(pos, state.setValue(ACTIVE,true),3);
            }else{
                level.setBlock(pos, state.setValue(ACTIVE,false),3);
            }
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> b) {
        super.createBlockStateDefinition(b);
        b.add(ACTIVE);
        b.add(ALWAYS_ACTIVE);
    }
}
