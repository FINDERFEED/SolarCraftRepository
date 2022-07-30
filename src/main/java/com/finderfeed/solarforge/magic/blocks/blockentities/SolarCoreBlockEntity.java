package com.finderfeed.solarforge.magic.blocks.blockentities;

import com.finderfeed.solarforge.Helpers;
import com.finderfeed.solarforge.misc_things.AbstractSolarCore;
import com.finderfeed.solarforge.multiblocks.Multiblocks;
import com.finderfeed.solarforge.registries.tile_entities.SolarcraftTileEntityTypes;


import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class SolarCoreBlockEntity extends AbstractSolarCore {

    public boolean IS_STRUCT_CORRECT = false;

    public SolarCoreBlockEntity(BlockPos pos,BlockState state) {
        super(SolarcraftTileEntityTypes.SOLAR_CORE_TILE.get(),pos,state);
    }



    public static void tick(Level world, BlockPos pos, BlockState blockState, SolarCoreBlockEntity tile) {
        AbstractSolarCore.tick(world,pos,blockState,tile);
        tile.IS_STRUCT_CORRECT = Helpers.checkStructure(tile.level,tile.worldPosition.offset(-2,-4,-2), Multiblocks.SOLAR_CORE.getM(),true);
    }


    @Override
    public AABB getRenderBoundingBox(){
        return new AABB(getBlockPos().offset(-16,-16,-16),getBlockPos().offset(16,16,16));
    }

    @Override
    public int getMaxEnergy() {
        return 1000000;
    }

    @Override
    public int getMaxEnergyFlowPerSec() {
        return 100;
    }

    @Override
    public int getRadius() {
        return 24;
    }

    @Override
    public boolean getConditionToFunction() {
        return getStructureState();
    }

    public boolean getStructureState(){
        return IS_STRUCT_CORRECT;
    }
}
