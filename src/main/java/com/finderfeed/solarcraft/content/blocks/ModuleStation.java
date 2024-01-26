package com.finderfeed.solarcraft.content.blocks;

import com.finderfeed.solarcraft.content.blocks.blockentities.containers.ModuleApplierMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;



public class ModuleStation extends Block {
    public ModuleStation(Properties p_49795_) {
        super(p_49795_);
    }



    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (!world.isClientSide){
            ((ServerPlayer) player).openMenu(getMenuProvider(world,pos),(buf)->buf.writeBlockPos(pos));
            return InteractionResult.CONSUME;
        }
        return InteractionResult.SUCCESS;
    }

    public MenuProvider getMenuProvider(Level world, BlockPos p_56437_) {
        return new SimpleMenuProvider((id, inventory, player) -> {
            return new ModuleApplierMenu(id, inventory, ContainerLevelAccess.create(world, p_56437_));
        }, Component.translatable("solarcraft.module_station"));
    }
}
