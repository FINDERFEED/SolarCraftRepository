package com.finderfeed.solarforge.magic.blocks.blockentities;

import com.finderfeed.solarforge.Helpers;
import com.finderfeed.solarforge.misc_things.AbstractEnergyGeneratorTileEntity;
import com.finderfeed.solarforge.multiblocks.Multiblocks;
import com.finderfeed.solarforge.registries.tile_entities.SolarcraftTileEntityTypes;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class EnergyGeneratorTile extends AbstractEnergyGeneratorTileEntity {





    public EnergyGeneratorTile(BlockPos pos, BlockState state) {
        super(SolarcraftTileEntityTypes.ENERGY_GENERATOR_TILE.get(),pos,state);
    }



    @Override
    public int getEnergyFlowAmount() {
        return 100;
    }

    @Override
    public int getEnergyCap() {
        return 50000;
    }

    @Override
    public int getRadius() {
        return 16;
    }

    @Override
    public int getEnergyPerSecond() {
        return 5;
    }

    @Override
    public boolean getConditionToFunction() {
        return Helpers.checkStructure(level,worldPosition.offset(-2,-2,-2), Multiblocks.SOLAR_ENERGY_GENERATOR.getM(),true) && level.canSeeSky(worldPosition.above()) && (level.getDayTime() % 24000 <= 13000);

    }

    @Override
    public AABB getRenderBoundingBox(){
        return new AABB(getBlockPos().offset(-16,-1,-16),getBlockPos().offset(16,16,16));
    }
}
