package com.finderfeed.solarforge.magic_items.blocks.blockentities;

import com.finderfeed.solarforge.registries.tile_entities.TileEntitiesRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class WormholeTileEntity extends BlockEntity {
    public WormholeTileEntity( BlockPos p_155229_, BlockState p_155230_) {
        super(TileEntitiesRegistry.WORMHOLE.get(), p_155229_, p_155230_);
    }


    public static void tick(Level world,BlockState state, BlockPos pos, WormholeTileEntity tile){

    }

    @Override
    public AABB getRenderBoundingBox() {
        return new AABB(getBlockPos(),getBlockPos().offset(1,1,1));
    }
}
