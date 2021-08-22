package com.finderfeed.solarforge.magic_items.blocks.blockentities;

import com.finderfeed.solarforge.registries.tile_entities.TileEntitiesRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class RadiantPortalTile extends BlockEntity {
    public RadiantPortalTile( BlockPos p_155229_, BlockState p_155230_) {
        super(TileEntitiesRegistry.PORTAL.get(), p_155229_, p_155230_);
    }
}
