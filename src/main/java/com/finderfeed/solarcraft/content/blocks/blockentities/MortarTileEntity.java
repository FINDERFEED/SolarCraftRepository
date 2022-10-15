package com.finderfeed.solarcraft.content.blocks.blockentities;

import com.finderfeed.solarcraft.content.blocks.blockentities.projectiles.MortarProjectile;
import com.finderfeed.solarcraft.helpers.multiblock.Multiblocks;
import com.finderfeed.solarcraft.misc_things.AbstractMortarProjectile;
import com.finderfeed.solarcraft.misc_things.AbstractMortarTileEntity;

import com.finderfeed.solarcraft.registries.entities.SolarcraftEntityTypes;
import com.finderfeed.solarcraft.registries.tile_entities.SolarcraftTileEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;


public class MortarTileEntity extends AbstractMortarTileEntity {
    public MortarTileEntity(BlockPos p_155229_, BlockState p_155230_) {
        super(SolarcraftTileEntityTypes.MORTAR_TILE_ENTITY.get(),p_155229_,p_155230_);
    }

    @Override
    public boolean getConditionToFunction() {
        return Multiblocks.SOLAR_MORTAR.check(level,worldPosition,true);
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
