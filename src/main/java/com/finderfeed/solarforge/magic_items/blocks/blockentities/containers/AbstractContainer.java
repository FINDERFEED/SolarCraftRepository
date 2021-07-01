package com.finderfeed.solarforge.magic_items.blocks.blockentities.containers;

import com.finderfeed.solarforge.SolarForge;
import com.finderfeed.solarforge.custom_slots.InputSlot;
import com.finderfeed.solarforge.custom_slots.OutputSlot;
import com.finderfeed.solarforge.solar_forge_block.SolarForgeBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.IntArray;
import net.minecraft.util.math.BlockPos;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractContainer<T extends LockableLootTileEntity> extends Container {
    public List<ItemStack> list = new ArrayList<>(0);
    public T te;
    public IIntArray arr;
    public AbstractContainer(ContainerType<?> type,final int windowId, final PlayerInventory playerInv, final T te, IIntArray array) {
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
    public AbstractContainer(ContainerType<?> type,final int windowId, final PlayerInventory playerInv,T te, final PacketBuffer buf,IIntArray array) {
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
    public ItemStack quickMoveStack(PlayerEntity playerIn, int index) {
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
    public boolean stillValid(PlayerEntity p_75145_1_) {
        return true;
    }
}
