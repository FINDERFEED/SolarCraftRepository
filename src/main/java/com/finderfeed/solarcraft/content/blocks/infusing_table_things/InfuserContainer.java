package com.finderfeed.solarcraft.content.blocks.infusing_table_things;

import com.finderfeed.solarcraft.content.blocks.blockentities.containers.misc.TESlotItemHandler;
import com.finderfeed.solarcraft.content.blocks.infusing_table_things.infusing_pool.InfusingStandTileEntity;
import com.finderfeed.solarcraft.content.world_generation.structures.NotStructures;
import com.finderfeed.solarcraft.helpers.Helpers;
import com.finderfeed.solarcraft.client.screens.custom_slots.OutputSlot;
import com.finderfeed.solarcraft.registries.containers.SCContainers;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.SlotItemHandler;
import net.minecraft.core.BlockPos;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

public class InfuserContainer extends AbstractContainerMenu {
    public InfuserTileEntity te;
    public ItemStackHandler inventory;

    public static final int[][] slotPositions = {
            {33 + 7 + 34,  -24 + 20 - 8},
            {33 + 7 + 80,  -24 + 13 - 8},
            {33 + 7 + 126, -24 +  20 - 8},
            {33 + 7 + 54,  -24 + 40 - 8},
            {33 + 7 + 106, -24 +  40 - 8},
            {33 + 7 + 27,  -24 + 66 - 8},
            {33 + 7 + 133, -24 +  66 - 8},
            {33 + 7 + 54,  -24 + 92 - 8},
            {33 + 7 + 106, -24 +  92 - 8},
            {33 + 7 + 34,  -24 + 112 - 8},
            {33 + 7 + 80,  -24 + 119 - 8},
            {33 + 7 + 126, -24 +  112 - 8},
    };

    public InfuserContainer(final int windowId, final Inventory playerInv, final InfuserTileEntity te) {
        super(SCContainers.INFUSING_TABLE_CONTAINER.get(), windowId);
        this.te = te;
        this.inventory = te.getInventory();



        int offsx = 0;
        List<BlockEntity> list = NotStructures.getInfuserPools(te.getBlockPos(),te.getLevel());

        for (int i = 0; i < list.size();i++){
            BlockEntity tile = list.get(i);
            if (tile instanceof InfusingStandTileEntity stand){
                this.addSlot(new TESlotItemHandler(stand,stand.getInventory(), 0, slotPositions[i][0], slotPositions[i][1]));
            }
        }


        // Tile Entity
        //for (int kj = 0; kj <= te.getContainerSize()-1;kj++){
            this.addSlot(new TESlotItemHandler(te,inventory, te.inputSlot(), 120 + offsx, 34));
            this.addSlot(new OutputSlot.ItemHandler(inventory, te.outputSlot(), 195 + offsx, -22){
                @Override
                public void setChanged() {
                    super.setChanged();
                    te.setChanged();
                    Helpers.updateTile(te);
                }
            });

        //}

        int modx = 40;
        int mody = 30;
        for(int l = 0; l < 3; ++l) {
            for(int j1 = 0; j1 < 9; ++j1) {
                this.addSlot(new Slot(playerInv, j1 + l * 9 + 9,   8+j1 * 18+modx + offsx, 103 + l * 18 -19 +mody));
            }
        }

        for(int i1 = 0; i1 < 9; ++i1) {
            this.addSlot(new Slot(playerInv, i1,  8+ i1 * 18+modx + offsx, 161 -19+mody));
        }


    }
    public InfuserContainer(final int windowId, final Inventory playerInv, final FriendlyByteBuf buf) {
        this(windowId, playerInv, getTileEntity(playerInv, buf.readBlockPos()));

    }




    private static InfuserTileEntity getTileEntity(final Inventory playerInv, final BlockPos pos) {
        Objects.requireNonNull(playerInv, "Player Inventory cannot be null.");
        Objects.requireNonNull(pos, "Pos cannot be null.");

        final BlockEntity te = playerInv.player.level().getBlockEntity(pos);

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
    public boolean stillValid(Player player) {
        return this.te != null && this.te.getInventory() != null && player.distanceToSqr(Helpers.getBlockCenter(te.getBlockPos())) <= 256;
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
        public AbstractContainerMenu createMenu(int id, Inventory playerinv, Player player) {
            return new InfuserContainer(id,playerinv,(InfuserTileEntity) player.level.getBlockEntity(pos));
        }
    }
}
