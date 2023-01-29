package com.finderfeed.solarcraft.content.runic_network.repeater;

import com.finderfeed.solarcraft.misc_things.RunicEnergy;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Set;

public class RunicEnergyRepeaterTile extends BaseRepeaterTile {

    public RunicEnergyRepeaterTile(BlockPos p_155229_, BlockState p_155230_) {
        super(p_155229_, p_155230_);
    }



    public static void tick(Level world, BlockState state, BlockPos pos, RunicEnergyRepeaterTile tile){
        tick(world,pos,state,tile);
        if (world.getGameTime() % 20 == 0) {
            Block block = world.getBlockState(pos.below()).getBlock();
            tile.setAcceptedRunicEnergyTypes(RunicEnergy.BLOCK_TO_RUNE_ENERGY_TYPE_ARRAY.get(block));
        }
    }

    @Override
    public Set<RunicEnergy.Type> getAcceptedEnergyTypes() {
        if (super.getAcceptedEnergyTypes().isEmpty()){
            Block block = level.getBlockState(worldPosition.below()).getBlock();
            this.setAcceptedRunicEnergyTypes(RunicEnergy.BLOCK_TO_RUNE_ENERGY_TYPE_ARRAY.get(block));
        }
        return super.getAcceptedEnergyTypes();
    }
}
