package com.finderfeed.solarcraft.content.blocks.primitive;

import com.finderfeed.solarcraft.content.blocks.blockentities.BonemealerTileEntity;
import com.finderfeed.solarcraft.content.blocks.blockentities.RunicEnergyCoreTile;
import com.finderfeed.solarcraft.registries.tile_entities.SolarcraftTileEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class RunicEnergyCoreBlock extends Block implements EntityBlock {
    public RunicEnergyCoreBlock(Properties p_49795_) {
        super(p_49795_);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return SolarcraftTileEntityTypes.RUNIC_ENERGY_CORE.get().create(pos,state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState pos, BlockEntityType<T> type) {
        return (level,blockPos,blockstate,tile)->{
            RunicEnergyCoreTile.tick(level,(RunicEnergyCoreTile) tile,blockPos,blockstate);
        };
    }

    @Override
    public void onRemove(BlockState p_196243_1_, Level world, BlockPos p_196243_3_, BlockState p_196243_4_, boolean p_196243_5_) {
        BlockEntity te = world.getBlockEntity(p_196243_3_);
        if (te instanceof RunicEnergyCoreTile tileEntity){
            tileEntity.resetAllRepeaters();
        }
        super.onRemove(p_196243_1_, world, p_196243_3_, p_196243_4_, p_196243_5_);
    }

    @Override
    public RenderShape getRenderShape(BlockState p_60550_) {
        return RenderShape.MODEL;
    }
}
