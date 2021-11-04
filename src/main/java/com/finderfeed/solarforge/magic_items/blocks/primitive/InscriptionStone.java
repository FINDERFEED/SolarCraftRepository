package com.finderfeed.solarforge.magic_items.blocks.primitive;

import com.finderfeed.solarforge.magic_items.blocks.blockstate_properties.RuneEnergyTypeProperty;
import com.finderfeed.solarforge.misc_things.RunicEnergy;
import com.finderfeed.solarforge.registries.blocks.BlocksRegistry;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;

public class InscriptionStone extends Block {

    public static RuneEnergyTypeProperty PROP = new RuneEnergyTypeProperty("type");

    public InscriptionStone(Properties p_49795_) {
        super(p_49795_);
        registerDefaultState(this.getStateDefinition().any().setValue(PROP, RunicEnergy.Type.NONE));
    }


    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_49915_) {
        p_49915_.add(PROP);
        super.createBlockStateDefinition(p_49915_);
    }
}
