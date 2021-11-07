package com.finderfeed.solarforge.magic_items.blocks.infusing_table_things;

import com.finderfeed.solarforge.SolarForge;
import com.finderfeed.solarforge.custom_slots.OutputSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.core.BlockPos;

import java.util.Objects;

public class InfuserContainer extends AbstractContainerMenu {
    public InfuserTileEntity te;


    public InfuserContainer(final int windowId, final Inventory playerInv, final InfuserTileEntity te) {
        super(SolarForge.INFUSING_TABLE_CONTAINER.get(), windowId);
        this.te = te;



        int i = (6 - 4) * 18;

        // Tile Entity
        //for (int kj = 0; kj <= te.getContainerSize()-1;kj++){
            this.addSlot(new Slot((Container) te, 0, 120, 34));
            this.addSlot(new OutputSlot((Container) te, 9, 195, -22));

        //}

        int modx = 40;
        int mody = 30;
        for(int l = 0; l < 3; ++l) {
            for(int j1 = 0; j1 < 9; ++j1) {
                this.addSlot(new Slot(playerInv, j1 + l * 9 + 9,   8+j1 * 18+modx, 103 + l * 18 -19 +mody));
            }
        }

        for(int i1 = 0; i1 < 9; ++i1) {
            this.addSlot(new Slot(playerInv, i1,  8+ i1 * 18+modx, 161 -19+mody));
        }


    }
    public InfuserContainer(final int windowId, final Inventory playerInv, final FriendlyByteBuf buf) {
        this(windowId, playerInv, getTileEntity(playerInv, buf.readBlockPos()));

    }




    private static InfuserTileEntity getTileEntity(final Inventory playerInv, final BlockPos pos) {
        Objects.requireNonNull(playerInv, "Player Inventory cannot be null.");
        Objects.requireNonNull(pos, "Pos cannot be null.");

        final BlockEntity te = playerInv.player.level.getBlockEntity(pos);

        if (te instanceof InfuserTileEntity) {
            return (InfuserTileEntity) te;
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
