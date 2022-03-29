package com.finderfeed.solarforge.magic.blocks;

import com.finderfeed.solarforge.magic.blocks.blockentities.RunicEnergyChargerTileEntity;
import com.finderfeed.solarforge.registries.tile_entities.TileEntitiesRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class RunicEnergyChargerBlock extends Block implements EntityBlock {
    public RunicEnergyChargerBlock(Properties p_49795_) {
        super(p_49795_);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return TileEntitiesRegistry.RUNIC_ENERGY_CHARGER.get().create(pos,state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState state, BlockEntityType<T> type) {
        return (a,b,c,d)->{
            RunicEnergyChargerTileEntity.tick((RunicEnergyChargerTileEntity) d,a,c,b);
        };
    }
}
