package com.finderfeed.solarcraft.content.blocks.blockentities.containers;

import com.finderfeed.solarcraft.content.blocks.blockentities.InfusingCraftingTableTile;
import com.finderfeed.solarcraft.content.blocks.blockentities.containers.misc.TESlotItemHandler;
import com.finderfeed.solarcraft.registries.containers.SCContainers;
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
import net.neoforged.neoforge.capabilities.Capabilities;

import net.neoforged.neoforge.items.IItemHandler;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class InfusingTableTileContainer extends AbstractContainerMenu {

    public InfusingCraftingTableTile tile;
    public IItemHandler inventory;

    public InfusingTableTileContainer( int p_38852_,Inventory inv, BlockPos tilepos) {
        super(SCContainers.INFUSING_TABLE_TILE.get(), p_38852_);
        Level world= inv.player.level();
        this.tile = (InfusingCraftingTableTile) world.getBlockEntity(tilepos);
        this.inventory = tile.getInventory();


        for(int i = 0; i < 3; ++i) {
            for(int j = 0; j < 3; ++j) {
                this.addSlot(new TESlotItemHandler(tile,this.inventory, j + i * 3, 64 + j * 18 - 2, 17 + i * 18 + 2));
            }
        }
        this.addSlot(new TESlotItemHandler(tile,inventory,9,150,10){
            @Override
            public boolean mayPlace(@Nonnull ItemStack stack) {
                return false;
            }
        });


        for(int l = 0; l < 3; ++l) {
            for(int j1 = 0; j1 < 9; ++j1) {
                this.addSlot(new Slot(inv, j1 + l * 9 + 9,   8+j1 * 18, 103 + l * 18 -19));
            }
        }

        for(int i1 = 0; i1 < 9; ++i1) {
            this.addSlot(new Slot(inv, i1,  8+ i1 * 18, 161 -19));
        }
    }


    public IItemHandler getInventory() {
        return inventory;
    }




    @Override
    public void slotsChanged(Container container) {

        super.slotsChanged(container);
    }

    public InfusingTableTileContainer(int p_38852_, Inventory inv, FriendlyByteBuf buf) {
        this(p_38852_,inv,buf.readBlockPos());
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

        private final InfusingCraftingTableTile tile;

        public Provider(InfusingCraftingTableTile tile){
            this.tile = tile;
        }

        @Override
        public Component getDisplayName() {
            return Component.translatable("solarcraft.infusing_crafting_table");
        }

        @Nullable
        @Override
        public AbstractContainerMenu createMenu(int id, Inventory inv, Player player) {
            return new InfusingTableTileContainer(id,inv,tile.getBlockPos());
        }
    }
}
