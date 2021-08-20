package com.finderfeed.solarforge.magic_items.blocks;

import com.finderfeed.solarforge.magic_items.blocks.blockentities.containers.ModuleApplierMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.SmithingMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.fmllegacy.network.NetworkHooks;

public class ModuleStation extends Block {
    public ModuleStation(Properties p_49795_) {
        super(p_49795_);
    }



    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (!world.isClientSide){
            NetworkHooks.openGui((ServerPlayer) player,getMenuProvider(world,pos),(buf)->buf.writeBlockPos(pos));
            return InteractionResult.CONSUME;
        }
        return InteractionResult.SUCCESS;
    }

    public MenuProvider getMenuProvider(Level world, BlockPos p_56437_) {
        return new SimpleMenuProvider((id, inventory, player) -> {
            return new ModuleApplierMenu(id, inventory, ContainerLevelAccess.create(world, p_56437_));
        }, new TranslatableComponent("solarcraft.module_station"));
    }
}
