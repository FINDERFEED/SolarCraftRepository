package com.finderfeed.solarforge.magic_items.blocks.blockentities;

import com.finderfeed.solarforge.Helpers;
import com.finderfeed.solarforge.magic_items.blocks.blockentities.projectiles.MortarProjectile;
import com.finderfeed.solarforge.misc_things.AbstractMortarProjectile;
import com.finderfeed.solarforge.misc_things.AbstractMortarTileEntity;
import com.finderfeed.solarforge.multiblocks.Multiblocks;
import com.finderfeed.solarforge.registries.projectiles.Projectiles;
import com.finderfeed.solarforge.registries.tile_entities.TileEntitiesRegistry;
import net.minecraft.tileentity.TileEntityType;


public class MortarTileEntity extends AbstractMortarTileEntity {
    public MortarTileEntity() {
        super(TileEntitiesRegistry.MORTAR_TILE_ENTITY.get());
    }

    @Override
    public boolean getConditionToFunction() {
        return Helpers.checkStructure(level,worldPosition.offset(-2,-12,-2), Multiblocks.SOLAR_MORTAR.getM());
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
        return new MortarProjectile(Projectiles.MORTAR_PROJECTILE.get(),level);
    }


}
