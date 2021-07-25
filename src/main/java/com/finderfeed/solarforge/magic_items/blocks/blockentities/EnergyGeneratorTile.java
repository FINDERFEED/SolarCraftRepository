package com.finderfeed.solarforge.magic_items.blocks.blockentities;

import com.finderfeed.solarforge.Helpers;
import com.finderfeed.solarforge.misc_things.AbstractEnergyGeneratorTileEntity;
import com.finderfeed.solarforge.multiblocks.Multiblocks;
import com.finderfeed.solarforge.registries.tile_entities.TileEntitiesRegistry;

import net.minecraft.world.phys.AABB;

import java.util.Collection;
import java.util.Collections;

public class EnergyGeneratorTile extends AbstractEnergyGeneratorTileEntity {




    public EnergyGeneratorTile() {
        super(TileEntitiesRegistry.ENERGY_GENERATOR_TILE.get());
    }

    @Override
    public void tick() {
        super.tick();
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
        return Helpers.checkStructure(level,worldPosition.offset(-2,-2,-2), Multiblocks.SOLAR_ENERGY_GENERATOR.getM()) && level.canSeeSky(worldPosition.above()) && (level.getDayTime() % 24000 <= 13000);

    }

    @Override
    public AABB getRenderBoundingBox(){
        return new AABB(getBlockPos().offset(-16,-16,-16),getBlockPos().offset(16,16,16));
    }
}
