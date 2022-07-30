package com.finderfeed.solarforge.magic.blocks.blockentities.containers;

import com.finderfeed.solarforge.Helpers;
import com.finderfeed.solarforge.magic.blocks.blockentities.RunicEnergyChargerTileEntity;
import com.finderfeed.solarforge.magic.items.RuneItem;
import com.finderfeed.solarforge.magic.items.runic_energy.IRunicEnergyUser;
import com.finderfeed.solarforge.registries.containers.SolarcraftContainers;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

public class RunicEnergyChargerContainer extends AbstractContainerMenu {

    public RunicEnergyChargerTileEntity tile;

    public RunicEnergyChargerContainer(int id,Inventory inv,BlockPos pos) {
        super(SolarcraftContainers.RUNIC_ENERGY_CHARGER.get(), id);
        this.tile = (RunicEnergyChargerTileEntity) inv.player.level.getBlockEntity(pos);

        this.addSlot(new SlotItemHandler(tile.getInventory(), 0,   25, 35-4){
            @Override
            public boolean mayPlace(@NotNull ItemStack stack) {
                return stack.getItem() instanceof RuneItem;
            }
        });
        this.addSlot(new SlotItemHandler(tile.getInventory(), 1, 25+108, 35-4){
            @Override
            public boolean mayPlace(@NotNull ItemStack stack) {
                return stack.getItem() instanceof IRunicEnergyUser;
            }

            @Override
            public void set(@Nonnull ItemStack stack) {
                super.set(stack);
                if (!inv.player.level.isClientSide){
                    tile.resetAllRepeaters();
                    tile.clearWays();
                    Helpers.updateTile(tile);
                }
            }
        });

        for(int l = 0; l < 3; ++l) {
            for(int j1 = 0; j1 < 9; ++j1) {
                this.addSlot(new Slot(inv, j1 + l * 9 + 9,   8+j1 * 18 -1, 103 + l * 18 -20));
            }
        }

        for(int i1 = 0; i1 < 9; ++i1) {
            this.addSlot(new Slot(inv, i1,  8+ i1 * 18 -1, 161 -20));
        }
    }

    public RunicEnergyChargerContainer(int windowid, Inventory inv, FriendlyByteBuf buf) {
        this(windowid,inv,buf.readBlockPos());
    }

    @Override
    public ItemStack quickMoveStack(Player playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            IItemHandler inv = tile.getInventory();
            if (index < inv.getSlots()) {
                if (!this.moveItemStackTo(itemstack1, inv.getSlots(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemstack1, 0, inv.getSlots(), false)) {
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
        return player.distanceToSqr(Helpers.getBlockCenter(tile.getBlockPos())) <= 16*16;
    }


    public static class Provider implements MenuProvider{

        private BlockPos pos;

        public Provider(BlockPos pos){
            this.pos = pos;
        }

        @Override
        public Component getDisplayName() {
            return new TextComponent("");
        }

        @Nullable
        @Override
        public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
            return new RunicEnergyChargerContainer(id,inventory,pos);
        }
    }
}
