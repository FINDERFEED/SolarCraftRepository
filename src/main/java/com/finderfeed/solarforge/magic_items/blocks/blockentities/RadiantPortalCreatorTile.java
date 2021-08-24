package com.finderfeed.solarforge.magic_items.blocks.blockentities;

import com.finderfeed.solarforge.registries.blocks.BlocksRegistry;
import com.finderfeed.solarforge.registries.tile_entities.TileEntitiesRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.util.Constants;

public class RadiantPortalCreatorTile extends BlockEntity {

    private boolean active = false;

    public RadiantPortalCreatorTile( BlockPos p_155229_, BlockState p_155230_) {
        super(TileEntitiesRegistry.PORTAL_CREATOR.get(), p_155229_, p_155230_);
    }


    public void activate() {
        this.active = true;
    }

    public void deactivate(){
        this.active = false;
    }

    public static void tick(RadiantPortalCreatorTile tile, BlockState state, BlockPos pos, Level world){
        if (!world.isClientSide && tile.active){
            if (!(world.getBlockState(pos.above()).getBlock() == BlocksRegistry.RADIANT_LAND_PORTAL.get())) {
                world.setBlockAndUpdate(pos.above(), BlocksRegistry.RADIANT_LAND_PORTAL.get().defaultBlockState());
            }
        }
    }


    @Override
    public CompoundTag save(CompoundTag cmp) {
        cmp.putBoolean("active",active);
        return super.save(cmp);
    }

    @Override
    public void load(CompoundTag cmp) {
        this.active = cmp.getBoolean("active");
        super.load(cmp);
    }
}
