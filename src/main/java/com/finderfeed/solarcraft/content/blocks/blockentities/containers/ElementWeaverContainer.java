package com.finderfeed.solarcraft.content.blocks.blockentities.containers;

import com.finderfeed.solarcraft.content.blocks.blockentities.ElementWeaverTileEntity;
import com.finderfeed.solarcraft.content.blocks.blockentities.containers.misc.TESlotItemHandler;
import com.finderfeed.solarcraft.helpers.Helpers;
import com.finderfeed.solarcraft.registries.ConfigRegistry;
import com.finderfeed.solarcraft.registries.containers.SolarcraftContainers;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ElementWeaverContainer extends AbstractContainerMenu {

    private ElementWeaverTileEntity tile;
    private ItemStackHandler inventory;

    public ElementWeaverContainer(int p_38852_, Inventory inv, BlockPos tilepos) {
        super(SolarcraftContainers.ELEMENT_WEAVER.get(), p_38852_);
        Level world = inv.player.level;
        this.tile = (ElementWeaverTileEntity) world.getBlockEntity(tilepos);
        this.inventory = tile.getInventory();
        int offsX = -37;
        int offsY = -33;

        this.addSlot(new TESlotItemHandler(tile,this.inventory, 0, 134 + 26 - 118 + offsX, 12 + offsY){
            @Override
            public boolean mayPlace(@NotNull ItemStack stack) {
                return ConfigRegistry.ITEM_RE_CONFIG.getItemCost(stack.getItem()) != null;
            }
        });

        offsX = -80;
        offsY = 23;

        this.addSlot(new TESlotItemHandler(tile,this.inventory, 1, 134 + 26 + offsX, 12 + offsY){
            @Override
            public boolean mayPlace(@NotNull ItemStack stack) {
                return false;
            }
        });

        offsX = 38;
        offsY = 29;
        for(int l = 0; l < 3; ++l) {
            for(int j1 = 0; j1 < 9; ++j1) {
                this.addSlot(new Slot(inv, j1 + l * 9 + 9 ,   8+j1 * 18 + 30 - 68  + offsX, 103 + l * 18 -20 + 3 + offsY));
            }
        }

        for(int i1 = 0; i1 < 9; ++i1) {
            this.addSlot(new Slot(inv, i1,  8+ i1 * 18 + 30 - 68 + offsX, 161 -20 + 3 + offsY));
        }
    }

    public ElementWeaverContainer(int containerId, Inventory inv, FriendlyByteBuf buf){
        this(containerId,inv,buf.readBlockPos());
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

    public ElementWeaverTileEntity getTile() {
        return tile;
    }

    public ItemStackHandler getInventory() {
        return inventory;
    }

    @Override
    public boolean stillValid(Player player) {
        return player.position().distanceTo(Helpers.posToVec(tile.getBlockPos())) < 20;
    }

    public static class Provider implements MenuProvider{

        private BlockPos pos;

        public Provider(BlockPos pos){
            this.pos = pos;
        }

        @Override
        public Component getDisplayName() {
            return Component.literal("");
        }

        @Nullable
        @Override
        public AbstractContainerMenu createMenu(int id, Inventory inv, Player player) {
            return new ElementWeaverContainer(id,inv,pos);
        }
    }
}
