package com.finderfeed.solarcraft.content.blocks.blockentities.containers;

import com.finderfeed.solarcraft.config.enchanter_config.EnchanterConfig;
import com.finderfeed.solarcraft.content.blocks.blockentities.EnchanterBlockEntity;
import com.finderfeed.solarcraft.content.blocks.blockentities.containers.misc.TESlotItemHandler;
import com.finderfeed.solarcraft.registries.containers.SCContainers;
import com.google.gson.JsonParser;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

public class EnchanterContainer extends AbstractContainerMenu {

    public final EnchanterBlockEntity tile;
    public final ItemStackHandler inventory;
    public final EnchanterConfig config;

    public EnchanterContainer( int p_38852_,Inventory inv, BlockPos tilepos,String json) {
        super(SCContainers.ENCHANTER.get(), p_38852_);
        Level world= inv.player.level();
        if (world.isClientSide){
            config = new EnchanterConfig(JsonParser.parseString(json).getAsJsonObject());
        }else{
            config = EnchanterBlockEntity.SERVERSIDE_CONFIG;
        }
        this.tile = (EnchanterBlockEntity) world.getBlockEntity(tilepos);
        this.inventory = tile.getInventory();

        this.addSlot(new TESlotItemHandler(tile,this.inventory, 0, 134 + 26 - 118, 12));

        for(int l = 0; l < 3; ++l) {
            for(int j1 = 0; j1 < 9; ++j1) {
                this.addSlot(new Slot(inv, j1 + l * 9 + 9,   8+j1 * 18 + 30 - 68, 103 + l * 18 -20 + 3));
            }
        }

        for(int i1 = 0; i1 < 9; ++i1) {
            this.addSlot(new Slot(inv, i1,  8+ i1 * 18 + 30 - 68, 161 -20 + 3));
        }
    }


    public ItemStackHandler getInventory() {
        return inventory;
    }




    @Override
    public void slotsChanged(Container container) {
        super.slotsChanged(container);
    }

    public EnchanterContainer(int p_38852_, Inventory inv, FriendlyByteBuf buf) {
        this(p_38852_,inv,buf.readBlockPos(),buf.readUtf());
    }

    @Override
    public boolean stillValid(Player player) {
        if (player.isDeadOrDying()){
            return false;
        }
        return player.distanceToSqr(tile.getBlockPos().getX(),tile.getBlockPos().getY(),tile.getBlockPos().getZ()) <= 100;
    }

    @Override
    public ItemStack quickMoveStack(Player playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (index < inventory.getSlots()) {
                if (!this.moveItemStackTo(itemstack1, inventory.getSlots(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemstack1, 0, inventory.getSlots(), false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }

        return itemstack;
    }

    public static class Provider implements MenuProvider{

        public BlockPos pos;
        private String json;

        public Provider(BlockPos pos,String json){
            this.pos = pos;
            this.json = json;
        }

        @Override
        public Component getDisplayName() {
            return Component.literal("");
        }

        @Nullable
        @Override
        public AbstractContainerMenu createMenu(int windowid, Inventory inventory, Player player) {
            return new EnchanterContainer(windowid,inventory,pos,json);
        }
    }
}
