package com.finderfeed.solarcraft.content.blocks.solar_forge_block.solar_forge_screen;


import com.finderfeed.solarcraft.SolarCraft;
import com.finderfeed.solarcraft.client.screens.custom_slots.InputSlot;
import com.finderfeed.solarcraft.client.screens.custom_slots.OutputSlot;
import com.finderfeed.solarcraft.content.blocks.solar_forge_block.SolarForgeBlockEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.core.BlockPos;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SolarForgeContainer extends AbstractContainerMenu {

    public List<ItemStack> list = new ArrayList<>(0);
    public SolarForgeBlockEntity te;
    private ContainerLevelAccess canInteractWithCallable;
    private ContainerData arr;
    public SolarForgeContainer(final int windowId, final Inventory playerInv, final SolarForgeBlockEntity te, ContainerData array) {
        super(SolarCraft.SOLAR_FORGE_CONTAINER.get(), windowId);
        this.te = te;
        this.canInteractWithCallable = ContainerLevelAccess.create(te.getLevel(), te.getBlockPos());
        this.arr = array;

        int i = (6 - 4) * 18;

        // Tile Entity

        list.add(SolarCraft.TEST_ITEM.get().getDefaultInstance());
        this.addSlot(new InputSlot((Container) te, 0, 135, 33,list));
        this.addSlot(new OutputSlot((Container) te, 1, 152, 63));

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
    public SolarForgeContainer(final int windowId, final Inventory playerInv, final FriendlyByteBuf buf) {
        this(windowId, playerInv, getTileEntity(playerInv, buf.readBlockPos()),new SimpleContainerData(1));

    }

    public int getCurrentCharge(){
        return arr.get(0);
    }


    private static SolarForgeBlockEntity getTileEntity(final Inventory playerInv,final BlockPos pos) {
        Objects.requireNonNull(playerInv, "Player Inventory cannot be null.");
        Objects.requireNonNull(pos, "Pos cannot be null.");

        final BlockEntity te = playerInv.player.level.getBlockEntity(pos);

        if (te instanceof SolarForgeBlockEntity) {
            return (SolarForgeBlockEntity) te;
        }
        throw new IllegalStateException("Tile Entity Is Not Correct");
    }

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
