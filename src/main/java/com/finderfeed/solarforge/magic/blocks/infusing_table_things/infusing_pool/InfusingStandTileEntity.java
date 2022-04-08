package com.finderfeed.solarforge.magic.blocks.infusing_table_things.infusing_pool;

import com.finderfeed.solarforge.Helpers;
import com.finderfeed.solarforge.local_library.tile_entities.abstracts.ItemStackHandlerTile;
import com.finderfeed.solarforge.packet_handler.SolarForgePacketHandler;
import com.finderfeed.solarforge.registries.tile_entities.TileEntitiesRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;

import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.network.PacketDistributor;


import javax.annotation.Nullable;


public class InfusingStandTileEntity extends ItemStackHandlerTile {


    private boolean shouldRenderItem = true;


    public InfusingStandTileEntity(BlockPos p_155630_, BlockState p_155631_) {
        super(TileEntitiesRegistry.INFUSING_POOL_BLOCKENTITY.get(), p_155630_, p_155631_);
    }

    public ItemStackHandler getInventory(){
        return (ItemStackHandler) this.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElse(null);
    }

    public ItemStack getStackInSlot(int i){
        ItemStackHandler inv = getInventory();
        if (inv == null) return ItemStack.EMPTY;
        return inv.getStackInSlot(i);
    }
    public void setStackInSlot(int i, ItemStack stack){
        ItemStackHandler inv = getInventory();
        if (inv == null)return;
        inv.setStackInSlot(i,stack);
    }

    public void shouldRenderItem(boolean e){
        this.shouldRenderItem = e;
    }

    public boolean isRenderingItem() {
        return shouldRenderItem;
    }



//    public static void tick(Level world, BlockPos pos, BlockState state, InfusingStandTileEntity tile) {
//        if (!tile.level.isClientSide) {
//            tile.setChanged();
//            world.sendBlockUpdated(pos,state,state,3);
//        }
//    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        super.onDataPacket(net, pkt);
        this.load(pkt.getTag());
    }

    @Nullable
    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        CompoundTag tag = saveWithFullMetadata();
        return Helpers.createTilePacket(this,tag);
    }
}
