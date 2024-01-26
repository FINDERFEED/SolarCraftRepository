package com.finderfeed.solarcraft.content.blocks;

import com.finderfeed.solarcraft.content.blocks.blockentities.SolarEnergyFurnaceTile;
import com.finderfeed.solarcraft.content.items.solar_wand.SolarWandItem;
import com.finderfeed.solarcraft.registries.tile_entities.SCTileEntities;

import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.GlazedTerracottaBlock;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Containers;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.BlockHitResult;

import net.minecraft.world.level.Level;


import javax.annotation.Nullable;


public class SolarEnergyFurnace extends GlazedTerracottaBlock implements EntityBlock {

    public SolarEnergyFurnace(Properties p_i48440_1_) {
        super(p_i48440_1_);
    }



    @Override
    public InteractionResult use(BlockState p_225533_1_, Level world, BlockPos p_225533_3_, Player player, InteractionHand p_225533_5_, BlockHitResult p_225533_6_) {
            if (!world.isClientSide && (world.getBlockEntity(p_225533_3_) instanceof SolarEnergyFurnaceTile) ){
                if (!(player.getMainHandItem().getItem() instanceof SolarWandItem)) {
                    NetworkHooks.openScreen((ServerPlayer) player, (SolarEnergyFurnaceTile) world.getBlockEntity(p_225533_3_),
                            (buf) -> buf.writeBlockPos(p_225533_3_)
                    );
                    return InteractionResult.CONSUME;
                }
            }
        return super.use(p_225533_1_, world, p_225533_3_, player, p_225533_5_, p_225533_6_);
    }

    @Override
    public void onRemove(BlockState state, Level world, BlockPos pos, BlockState state2, boolean hz) {
        BlockEntity test = world.getBlockEntity(pos);
        if (!world.isClientSide && (test instanceof SolarEnergyFurnaceTile)){
            Containers.dropContents(world,pos,(SolarEnergyFurnaceTile) test);
        }
        super.onRemove(state, world, pos, state2, hz);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return SCTileEntities.SOLAR_FURNACE_TILE_ENTITY.get().create(blockPos,blockState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level p_153212_, BlockState p_153213_, BlockEntityType<T> p_153214_) {
        return (((level, blockPos, blockState, t) -> {

                SolarEnergyFurnaceTile.tick(level, blockPos, blockState, (SolarEnergyFurnaceTile) t);

        }));
    }
}
