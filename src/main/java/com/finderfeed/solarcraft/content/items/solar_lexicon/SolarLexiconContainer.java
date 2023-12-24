package com.finderfeed.solarcraft.content.items.solar_lexicon;

import com.finderfeed.solarcraft.content.items.AncientFragmentItem;
import com.finderfeed.solarcraft.content.items.solar_lexicon.unlockables.AncientFragmentHelper;
import com.finderfeed.solarcraft.registries.containers.SCContainers;
import com.finderfeed.solarcraft.content.items.solar_lexicon.unlockables.AncientFragment;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.capabilities.Capabilities;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.SlotItemHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;


public class SolarLexiconContainer extends AbstractContainerMenu {

    private final IItemHandler inventory;
    private final ItemStack stack;
    private final List<SlotItemHandler> scrollableSlots = new ArrayList<>();
    private int maxRows = 0;
    public SolarLexiconContainer(int p_i50105_2_, Inventory inv, ItemStack stack) {
        super(SCContainers.SOLAR_LEXICON_CONTAINER.get(), p_i50105_2_);
        this.stack = stack;
        this.inventory = stack.getCapability(Capabilities.ITEM_HANDLER).orElse(null);
        int amount = 0;
        int row = 1;
        int id = 0;

        for (AncientFragment fragment : AncientFragment.getAllFragments()){
            if (amount > 8){
                amount = 0;
                row += 1;
            }
            SlotItemHandler s = new SlotItemHandler(inventory,id,8+amount*18 - 3,-1+row*18){
                @Override
                public boolean mayPlace(@NotNull ItemStack stack) {
                    if (!(stack.getItem() instanceof AncientFragmentItem)) return false;
                    AncientFragment fragment1 = AncientFragmentHelper.getFragmentFromItem(stack);
                    if (fragment1 == null) return false;
                    for (int i = 0; i < inventory.getSlots();i++){
                           AncientFragment fragment2 = AncientFragmentHelper.getFragmentFromItem(inventory.getStackInSlot(i));
                           if (fragment1 == fragment2) return false;
                    }
                    return true;
                }

                @Override
                public int getMaxStackSize() {
                    return 1;
                }

                @Override
                public boolean isActive() {
                    return this.y > -1 && y <= -1 + 18 * 3;
                }
            };
            addSlot(s);
            scrollableSlots.add(s);
            id++;
            amount++;
        }
        maxRows = row;




        for(int l = 0; l < 3; ++l) {
            for(int j1 = 0; j1 < 9; ++j1) {
                this.addSlot(new Slot(inv, j1 + l * 9 + 9,   8+j1 * 18- 3, 103 + l * 18 -19));
            }
        }

        for(int i1 = 0; i1 < 9; ++i1) {
            this.addSlot(new Slot(inv, i1,  8+ i1 * 18 - 3, 161 -19));
        }
    }

    public int getMaxRows() {
        return maxRows;
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

    public List<SlotItemHandler> getScrollableSlots() {
        return scrollableSlots;
    }

    static class Provider implements MenuProvider{


        private final ItemStack stack;

        public Provider(ItemStack stack){
            this.stack = stack;
        }


        @Override
        public Component getDisplayName() {
            return Component.literal("");
        }

        @Nullable
        @Override
        public AbstractContainerMenu createMenu(int p_createMenu_1_, Inventory p_createMenu_2_, Player p_createMenu_3_) {
            return new SolarLexiconContainer(p_createMenu_1_,p_createMenu_2_,stack);
        }
    }


}
