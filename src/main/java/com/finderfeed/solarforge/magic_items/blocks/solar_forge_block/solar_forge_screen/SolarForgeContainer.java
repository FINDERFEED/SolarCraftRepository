package com.finderfeed.solarforge.magic_items.blocks.solar_forge_block.solar_forge_screen;


import com.finderfeed.solarforge.SolarForge;
import com.finderfeed.solarforge.custom_slots.InputSlot;
import com.finderfeed.solarforge.custom_slots.OutputSlot;
import com.finderfeed.solarforge.magic_items.blocks.solar_forge_block.SolarForgeBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.IntArray;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SolarForgeContainer extends Container {

    public List<ItemStack> list = new ArrayList<>(0);
    public SolarForgeBlockEntity te;
    private IWorldPosCallable canInteractWithCallable;
    private IIntArray arr;
    public SolarForgeContainer(final int windowId, final PlayerInventory playerInv, final SolarForgeBlockEntity te, IIntArray array) {
        super(SolarForge.SOLAR_FORGE_CONTAINER.get(), windowId);
        this.te = te;
        this.canInteractWithCallable = IWorldPosCallable.create(te.getLevel(), te.getBlockPos());
        this.arr = array;

        int i = (6 - 4) * 18;

        // Tile Entity

        list.add(SolarForge.TEST_ITEM.get().getDefaultInstance());
        this.addSlot(new InputSlot((IInventory) te, 0, 135, 33,list));
        this.addSlot(new OutputSlot((IInventory) te, 1, 152, 63));

        for(int l = 0; l < 3; ++l) {
            for(int j1 = 0; j1 < 9; ++j1) {
                this.addSlot(new Slot(playerInv, j1 + l * 9 + 9,   8+j1 * 18, 103 + l * 18 -19));
            }
        }

        for(int i1 = 0; i1 < 9; ++i1) {
            this.addSlot(new Slot(playerInv, i1,  8+ i1 * 18, 161 -19));
        }
        addDataSlots(arr);

    }
    public SolarForgeContainer(final int windowId, final PlayerInventory playerInv, final PacketBuffer buf) {
        this(windowId, playerInv, getTileEntity(playerInv, buf.readBlockPos()),new IntArray(1));

    }

    public int getCurrentCharge(){
        return arr.get(0);
    }


    private static SolarForgeBlockEntity getTileEntity(final PlayerInventory playerInv,final BlockPos pos) {
        Objects.requireNonNull(playerInv, "Player Inventory cannot be null.");
        Objects.requireNonNull(pos, "Pos cannot be null.");

        final TileEntity te = playerInv.player.level.getBlockEntity(pos);

        if (te instanceof SolarForgeBlockEntity) {
            return (SolarForgeBlockEntity) te;
        }
        throw new IllegalStateException("Tile Entity Is Not Correct");
    }

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
