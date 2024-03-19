package com.finderfeed.solarcraft.content.blocks;

import com.finderfeed.solarcraft.local_library.helpers.FDMathHelper;
import com.finderfeed.solarcraft.registries.blocks.SCBlocks;
import com.finderfeed.solarcraft.registries.damage_sources.SCDamageSources;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RedStoneWireBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

public class HeaterBlock extends Block {

    public static BooleanProperty ACTIVE = BooleanProperty.create("active");
    public HeaterBlock(Properties props) {
        super(props);
    }


    @Override
    public void stepOn(Level level, BlockPos pos, BlockState state, Entity entity) {
        super.stepOn(level, pos, state, entity);
        if (!level.isClientSide && state.getValue(ACTIVE)){
            int bestSignal = level.getBestNeighborSignal(pos);
            float p = FDMathHelper.easeInOut(bestSignal / 15f);
            System.out.println(p);
            entity.hurt(SCDamageSources.HEAT,2 * p);
        }
    }

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos neighbor, boolean smth) {
        if (!level.isClientSide){
            if (level.hasNeighborSignal(pos)){
                level.setBlock(pos, this.defaultBlockState().setValue(ACTIVE,true),3);
            }else{
                level.setBlock(pos, this.defaultBlockState().setValue(ACTIVE,false),3);
            }
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> b) {
        super.createBlockStateDefinition(b);
        b.add(ACTIVE);
    }
}
