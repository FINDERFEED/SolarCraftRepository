package com.finderfeed.solarforge.magic.blocks.infusing_table_things.infusing_pool;

import com.finderfeed.solarforge.Helpers;
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


public class InfusingStandTileEntity extends BlockEntity {


    private boolean shouldRenderItem = true;


    public InfusingStandTileEntity(BlockPos p_155630_, BlockState p_155631_) {
        super(TileEntitiesRegistry.INFUSING_POOL_BLOCKENTITY.get(), p_155630_, p_155631_);
    }



//    @Override
//    protected Component getDefaultName() {
//        return new TranslatableComponent("container.solarforge.infusing_pool");
//    }
//
//    @Override
//    protected AbstractContainerMenu createMenu(int x, Inventory inv) {
//        return new InfusingStandContainer(x,inv,this);
//    }
//
//    @Override
//    protected NonNullList<ItemStack> getItems() {
//        return this.items;
//    }
//
//    @Override
//    protected void setItems(NonNullList<ItemStack> items) {
//        this.items = items;
//    }
//
//
//
//    @Override
//    public int getContainerSize() {
//        return this.items.size();
//    }
    public ItemStackHandler getInventory(){
        return (ItemStackHandler) this.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElse(null);
    }

    public ItemStack getItem(int i){
        return getInventory().getStackInSlot(i);
    }
    public void setItem(int i,ItemStack stack){
        this.getInventory().setStackInSlot(i,stack);
    }

    public void shouldRenderItem(boolean e){
        this.shouldRenderItem = e;
    }

    public boolean isRenderingItem() {
        return shouldRenderItem;
    }

    @Override
    public void saveAdditional(CompoundTag cmp){
        super.saveAdditional(cmp);

//        if (!this.trySaveLootTable(cmp)) {
//            ContainerHelper.saveAllItems(cmp, this.items);
//        }

    }

    @Override
    public void load( CompoundTag cmp) {
        super.load(cmp);

//        this.items = NonNullList.withSize(getContainerSize(), ItemStack.EMPTY);
//        if (!this.tryLoadLootTable(cmp)) {
//            ContainerHelper.loadAllItems(cmp, this.items);
//        }
    }


    public static void tick(Level world, BlockPos pos, BlockState state, InfusingStandTileEntity tile) {
        if (!tile.level.isClientSide) {
            tile.setChanged();
            world.sendBlockUpdated(pos,state,state,3);
//            SolarForgePacketHandler.INSTANCE.send(PacketDistributor.NEAR.with(PacketDistributor.TargetPoint.p(tile.worldPosition.getX(),tile.worldPosition.getY(),tile.worldPosition.getZ(),20,tile.level.dimension())),
//                    new UpdateStacksOnClientPacketPool(tile.getItem(0),tile.worldPosition));

        }
    }


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
