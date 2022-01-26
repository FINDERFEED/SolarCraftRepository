package com.finderfeed.solarforge.magic.items.solar_lexicon;

import com.finderfeed.solarforge.registries.containers.Containers;
import com.finderfeed.solarforge.magic.items.solar_lexicon.unlockables.AncientFragment;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nullable;


public class SolarLexiconContainer extends AbstractContainerMenu {

    private final IItemHandler inventory;
    private final ItemStack stack;

    public SolarLexiconContainer(int p_i50105_2_, Inventory inv, ItemStack stack) {
        super(Containers.SOLAR_LEXICON_CONTAINER.get(), p_i50105_2_);
        this.stack = stack;
        this.inventory = stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElse(null);
        int kolvo = 0;
        int row = 1;
        int id = 0;

        for (AncientFragment frag : AncientFragment.getAllFragments()){
            if (kolvo > 8){
                kolvo = 0;
                if (row < 3) {
                    row += 1;
                }else{
                    row = 50000;
                }
            }
            addSlot(new SolarLexiconContainerSlot(inventory,id,8+kolvo*18,-1+row*18,frag));
            id++;
            kolvo++;
        }





        for(int l = 0; l < 3; ++l) {
            for(int j1 = 0; j1 < 9; ++j1) {
                this.addSlot(new Slot(inv, j1 + l * 9 + 9,   8+j1 * 18, 103 + l * 18 -19));
            }
        }

        for(int i1 = 0; i1 < 9; ++i1) {
            this.addSlot(new Slot(inv, i1,  8+ i1 * 18, 161 -19));
        }
    }


    public SolarLexiconContainer(int windowId, Inventory inv, FriendlyByteBuf buf){
        this(windowId,inv,buf.readItem());
    }

    @Override
    public ItemStack quickMoveStack(Player p_82846_1_, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (index < this.inventory.getSlots()) {
                if (!this.moveItemStackTo(itemstack1, this.inventory.getSlots(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemstack1, 0, this.inventory.getSlots(), false)) {
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
        return !player.isDeadOrDying() && player.getInventory().contains(stack);
    }


    static class Provider implements MenuProvider{


        private final ItemStack stack;

        public Provider(ItemStack stack){
            this.stack = stack;
        }


        @Override
        public Component getDisplayName() {
            return new TranslatableComponent("");
        }

        @Nullable
        @Override
        public AbstractContainerMenu createMenu(int p_createMenu_1_, Inventory p_createMenu_2_, Player p_createMenu_3_) {
            return new SolarLexiconContainer(p_createMenu_1_,p_createMenu_2_,stack);
        }
    }


}
