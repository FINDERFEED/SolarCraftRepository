package com.finderfeed.solarcraft.content.blocks.blockentities;

import com.finderfeed.solarcraft.content.blocks.blockentities.projectiles.MortarProjectile;
import com.finderfeed.solarcraft.content.items.solar_wand.wand_actions.structure_check.IStructureOwner;
import com.finderfeed.solarcraft.helpers.multiblock.MultiblockStructure;
import com.finderfeed.solarcraft.helpers.multiblock.Multiblocks;
import com.finderfeed.solarcraft.misc_things.AbstractMortarProjectile;
import com.finderfeed.solarcraft.misc_things.AbstractMortarTileEntity;

import com.finderfeed.solarcraft.registries.entities.SCEntityTypes;
import com.finderfeed.solarcraft.registries.tile_entities.SCTileEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;


public class MortarTileEntity extends AbstractMortarTileEntity implements IStructureOwner {
    public MortarTileEntity(BlockPos p_155229_, BlockState p_155230_) {
        super(SCTileEntities.MORTAR_TILE_ENTITY.get(),p_155229_,p_155230_);
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
        return new MortarProjectile(SCEntityTypes.MORTAR_PROJECTILE.get(),level);
    }


    @Override
    public List<MultiblockStructure> getMultiblocks() {
        return List.of(Multiblocks.SOLAR_MORTAR);
    }
}
