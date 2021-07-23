package com.finderfeed.solarforge.magic_items.items.solar_lexicon;

import com.finderfeed.solarforge.registries.containers.Containers;
import com.finderfeed.solarforge.magic_items.items.solar_lexicon.unlockables.AncientFragment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nullable;


public class SolarLexiconContainer extends Container {

    private final IItemHandler inventory;
    private final ItemStack stack;

    public SolarLexiconContainer(int p_i50105_2_, PlayerInventory inv, ItemStack stack) {
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


    public SolarLexiconContainer(int windowId, PlayerInventory inv, PacketBuffer buf){
        this(windowId,inv,buf.readItem());
    }

    @Override
    public ItemStack quickMoveStack(PlayerEntity p_82846_1_, int index) {
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
    public boolean stillValid(PlayerEntity player) {
        return !player.isDeadOrDying() && player.inventory.contains(stack);
    }


    static class Provider implements INamedContainerProvider{


        private final ItemStack stack;

        public Provider(ItemStack stack){
            this.stack = stack;
        }


        @Override
        public ITextComponent getDisplayName() {
            return new TranslationTextComponent("");
        }

        @Nullable
        @Override
        public Container createMenu(int p_createMenu_1_, PlayerInventory p_createMenu_2_, PlayerEntity p_createMenu_3_) {
            return new SolarLexiconContainer(p_createMenu_1_,p_createMenu_2_,stack);
        }
    }


}
