package com.finderfeed.solarcraft.content.blocks.blockentities;

import com.finderfeed.solarcraft.helpers.Helpers;
import com.finderfeed.solarcraft.misc_things.PhantomInventory;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.capabilities.ForgeCapabilities;
import net.neoforged.neoforge.items.ItemStackHandler;

import javax.annotation.Nullable;

public abstract class ItemStackHandlerTile extends BlockEntity {
    public ItemStackHandlerTile(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public ItemStackHandler getInventory(){
        return (ItemStackHandler) this.getCapability(ForgeCapabilities.ITEM_HANDLER).orElse(null);
    }

    public void setStackInSlot(int i, ItemStack stack){
        ItemStackHandler handler = getInventory();
        if (handler == null) return;
        this.getInventory().setStackInSlot(i,stack);
        if (!level.isClientSide){
            Helpers.updateTile(this);
        }
    }

    public Container wrapInContainer(){
        ItemStackHandler handler = getInventory();
        if (handler == null) return null;
        return new PhantomInventory(handler);
    }

    public ItemStack getStackInSlot(int i){
        ItemStackHandler handler = getInventory();
        if (handler == null) return null;
        return handler.getStackInSlot(i);
    }

    public int getSize(){
        ItemStackHandler handler = getInventory();
        if (handler == null) return 0;
        return  handler.getSlots();
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        super.onDataPacket(net, pkt);
    }

    @Nullable
    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        CompoundTag tag = saveWithFullMetadata();
        return Helpers.createTilePacket(this,tag);
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = super.getUpdateTag();
        ItemStackHandler g = this.getInventory();
        if (g != null) {
            tag.merge(g.serializeNBT());
        }
        return tag;
    }

    @Override
    public void handleUpdateTag(CompoundTag tag) {
        super.handleUpdateTag(tag);
        ItemStackHandler h = getInventory();
        if (h != null){
            h.deserializeNBT(tag);
        }
    }

}
