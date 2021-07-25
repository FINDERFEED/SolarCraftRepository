package com.finderfeed.solarforge.magic_items.blocks.blockentities.containers;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.inventory.ContainerData;

import java.util.ArrayList;
import java.util.List;

import RandomizableContainerBlockEntity;

public abstract class AbstractContainer<T extends RandomizableContainerBlockEntity> extends AbstractContainerMenu {
    public List<ItemStack> list = new ArrayList<>(0);
    public T te;
    public ContainerData arr;
    public AbstractContainer(MenuType<?> type,final int windowId, final Inventory playerInv, final T te, ContainerData array) {
        super(type, windowId);
        this.te = te;
        this.arr = array;
//        for(int l = 0; l < 3; ++l) {
//            for(int j1 = 0; j1 < 9; ++j1) {
//                this.addSlot(new Slot(playerInv, j1 + l * 9 + 9,   8+j1 * 18, 103 + l * 18 -19));
//            }
//        }
//
//        for(int i1 = 0; i1 < 9; ++i1) {
//            this.addSlot(new Slot(playerInv, i1,  8+ i1 * 18, 161 -19));
//        }
//        addDataSlots(arr);

    }
    public AbstractContainer(MenuType<?> type,final int windowId, final Inventory playerInv,T te, final FriendlyByteBuf buf,ContainerData array) {
        this(type,windowId, playerInv, te,array);

    }




//    private static SolarForgeBlockEntity getTileEntity(final PlayerInventory playerInv, final BlockPos pos) {
//        Objects.requireNonNull(playerInv, "Player Inventory cannot be null.");
//        Objects.requireNonNull(pos, "Pos cannot be null.");
//
//        final TileEntity te = playerInv.player.level.getBlockEntity(pos);
//
//        if (te instanceof SolarForgeBlockEntity) {
//            return (SolarForgeBlockEntity) te;
//        }
//        throw new IllegalStateException("Tile Entity Is Not Correct");
//    }

    @Override
    public ItemStack quickMoveStack(Player playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (index < this.te.getContainerSize()) {
                if (!this.moveItemStackTo(itemstack1, this.te.getContainerSize(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemstack1, 0, this.te.getContainerSize(), false)) {
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



    @Override
    public boolean stillValid(Player p_75145_1_) {
        return true;
    }
}
