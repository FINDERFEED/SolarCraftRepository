package com.finderfeed.solarforge.content.blocks.blockentities;

import com.finderfeed.solarforge.Helpers;
import com.finderfeed.solarforge.content.blocks.blockentities.projectiles.MortarProjectile;
import com.finderfeed.solarforge.misc_things.AbstractMortarProjectile;
import com.finderfeed.solarforge.misc_things.AbstractMortarTileEntity;
import com.finderfeed.solarforge.multiblocks.Multiblocks;
import com.finderfeed.solarforge.registries.entities.SolarcraftEntityTypes;
import com.finderfeed.solarforge.registries.tile_entities.SolarcraftTileEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;


public class MortarTileEntity extends AbstractMortarTileEntity {
    public MortarTileEntity(BlockPos p_155229_, BlockState p_155230_) {
        super(SolarcraftTileEntityTypes.MORTAR_TILE_ENTITY.get(),p_155229_,p_155230_);
    }

    @Override
    public boolean getConditionToFunction() {
        return Helpers.checkStructure(level,worldPosition.offset(-2,-12,-2), Multiblocks.SOLAR_MORTAR.getM(),true);
    }

    @Override
    public double getAttackRadius() {
        return 50;
    }

    @Override
    public double getAttackInterval() {
        return 150;
    }

    @Override
    public AbstractMortarProjectile getMortarProjectile() {
        return new MortarProjectile(SolarcraftEntityTypes.MORTAR_PROJECTILE.get(),level);
    }


}
