package com.finderfeed.solarforge.magic.blocks.blockentities.containers;

import com.finderfeed.solarforge.magic.blocks.blockentities.EnchanterBlockEntity;
import com.finderfeed.solarforge.misc_things.RunicEnergy;
import com.finderfeed.solarforge.registries.containers.Containers;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.Container;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class EnchanterContainer extends AbstractContainerMenu {

    public final EnchanterBlockEntity tile;
    public final ItemStackHandler inventory;
    public final Map<Enchantment, Map<RunicEnergy.Type,Double>> costsAndAvailableEnchantments;

    public EnchanterContainer( int p_38852_,Inventory inv, BlockPos tilepos,String json) {
        super(Containers.ENCHANTER.get(), p_38852_);
        Level world= inv.player.level;
        if (world.isClientSide){
            costsAndAvailableEnchantments = EnchanterBlockEntity.parseJson(JsonParser.parseString(json).getAsJsonObject());
        }else{
            costsAndAvailableEnchantments = EnchanterBlockEntity.SERVERSIDE_CONFIG;
        }
        this.tile = (EnchanterBlockEntity) world.getBlockEntity(tilepos);
        this.inventory = tile.getInventory();

        this.addSlot(new SlotItemHandler(this.inventory, 0, 134 + 20, 17));

        for(int l = 0; l < 3; ++l) {
            for(int j1 = 0; j1 < 9; ++j1) {
                this.addSlot(new Slot(inv, j1 + l * 9 + 9,   8+j1 * 18 + 20, 103 + l * 18 -19));
            }
        }

        for(int i1 = 0; i1 < 9; ++i1) {
            this.addSlot(new Slot(inv, i1,  8+ i1 * 18 + 20, 161 -19));
        }
    }


    public ItemStackHandler getInventory() {
        return inventory;
    }




    @Override
    public void slotsChanged(Container container) {
        super.slotsChanged(container);
    }

    public EnchanterContainer(int p_38852_, Inventory inv, FriendlyByteBuf buf) {
        this(p_38852_,inv,buf.readBlockPos(),buf.readUtf());
    }

    @Override
    public boolean stillValid(Player player) {
        if (player.isDeadOrDying()){
            return false;
        }
        return player.distanceToSqr(tile.getBlockPos().getX(),tile.getBlockPos().getY(),tile.getBlockPos().getZ()) <= 100;
    }

    @Override
    public ItemStack quickMoveStack(Player playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (index < inventory.getSlots()) {
                if (!this.moveItemStackTo(itemstack1, inventory.getSlots(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemstack1, 0, inventory.getSlots(), false)) {
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

    public static class Provider implements MenuProvider{

        public BlockPos pos;
        private String json;

        public Provider(BlockPos pos,String json){
            this.pos = pos;
            this.json = json;
        }

        @Override
        public Component getDisplayName() {
            return new TextComponent("");
        }

        @Nullable
        @Override
        public AbstractContainerMenu createMenu(int windowid, Inventory inventory, Player player) {
            return new EnchanterContainer(windowid,inventory,pos,json);
        }
    }
}
