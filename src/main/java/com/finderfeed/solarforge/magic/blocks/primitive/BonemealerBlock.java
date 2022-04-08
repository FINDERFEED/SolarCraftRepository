package com.finderfeed.solarforge.magic.blocks.primitive;

import com.finderfeed.solarforge.magic.blocks.blockentities.BonemealerTileEntity;
import com.finderfeed.solarforge.misc_things.RunicEnergy;
import com.finderfeed.solarforge.registries.tile_entities.TileEntitiesRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.List;

public class BonemealerBlock extends RunicEnergySaverBlock implements EntityBlock {
    public BonemealerBlock(Properties p_49795_) {
        super(p_49795_);
    }

    @Override
    public RenderShape getRenderShape(BlockState p_60550_) {
        return super.getRenderShape(p_60550_);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos p_153215_, BlockState p_153216_) {
        return TileEntitiesRegistry.BONEMEALER.get().create(p_153215_,p_153216_);
    }

    @Override
    public void onRemove(BlockState p_196243_1_, Level p_196243_2_, BlockPos p_196243_3_, BlockState p_196243_4_, boolean p_196243_5_) {
        BlockEntity te = p_196243_2_.getBlockEntity(p_196243_3_);
        if (te instanceof BonemealerTileEntity tileEntity){
            tileEntity.onRemove();
        }
        super.onRemove(p_196243_1_, p_196243_2_, p_196243_3_, p_196243_4_, p_196243_5_);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level p_153212_, BlockState p_153213_, BlockEntityType<T> p_153214_) {
        return (level,blockpos,blockstate,tile)->{
            BonemealerTileEntity.tick(level,blockpos,blockstate,(BonemealerTileEntity) tile);
        };
    }

    @Override
    public List<RunicEnergy.Type> getTooltipEnergies() {
        return List.of(RunicEnergy.Type.TERA);
    }
}
