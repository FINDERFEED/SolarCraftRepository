package com.finderfeed.solarforge.magic.blocks;

import com.finderfeed.solarforge.magic.blocks.blockentities.MagnetBlockTile;
import com.finderfeed.solarforge.registries.tile_entities.SolarcraftTileEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.BlockGetter;

import javax.annotation.Nullable;
import java.util.List;

public class MagnetBlock extends Block implements EntityBlock {
    public MagnetBlock(Properties p_i48440_1_) {
        super(p_i48440_1_);
    }



    @Override
    public void appendHoverText(ItemStack p_190948_1_, @Nullable BlockGetter p_190948_2_, List<Component> p_190948_3_, TooltipFlag p_190948_4_) {
        p_190948_3_.add(new TranslatableComponent("solarforge.magnet_block").withStyle(ChatFormatting.GOLD));
        super.appendHoverText(p_190948_1_, p_190948_2_, p_190948_3_, p_190948_4_);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level p_153212_, BlockState p_153213_, BlockEntityType<T> p_153214_) {
        return ((level, blockPos, blockState, t) -> {
            MagnetBlockTile.tick(level,blockPos,blockState,(MagnetBlockTile) t);
        });

    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return SolarcraftTileEntityTypes.MAGNET_BLOCK_TILE.get().create(blockPos,blockState);
    }
}
