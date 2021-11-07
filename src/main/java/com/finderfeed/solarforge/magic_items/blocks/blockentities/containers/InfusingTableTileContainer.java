package com.finderfeed.solarforge.magic_items.blocks.blockentities.containers;

import com.finderfeed.solarforge.capabilities.InfusingTableInventory;
import com.finderfeed.solarforge.magic_items.blocks.blockentities.InfusingTableTile;
import com.finderfeed.solarforge.registries.containers.Containers;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.Container;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nullable;

public class InfusingTableTileContainer extends AbstractContainerMenu {

    public InfusingTableTile tile;
    public IItemHandler inventory;

    public InfusingTableTileContainer( int p_38852_,Inventory inv, BlockPos tilepos) {
        super(Containers.INFUSING_TABLE_TILE.get(), p_38852_);
        Level world= inv.player.level;
        this.tile = (InfusingTableTile) world.getBlockEntity(tilepos);
        this.inventory = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElse(null);


        for(int l = 0; l < 3; ++l) {
            for(int j1 = 0; j1 < 9; ++j1) {
                this.addSlot(new Slot(inv, j1 + l * 9 + 9,   8+j1 * 18, 103 + l * 18 -19));
            }
        }

        for(int i1 = 0; i1 < 9; ++i1) {
            this.addSlot(new Slot(inv, i1,  8+ i1 * 18, 161 -19));
        }
    }

    public IItemHandler getInventory() {
        return inventory;
    }


    @Override
    public void slotsChanged(Container container) {
        super.slotsChanged(container);
    }

    public InfusingTableTileContainer(int p_38852_, Inventory inv, FriendlyByteBuf buf) {
        this(p_38852_,inv,buf.readBlockPos());
    }

    @Override
    public boolean stillValid(Player player) {
        if (player.isDeadOrDying()){
            return false;
        }
        return player.distanceToSqr(tile.getBlockPos().getX(),tile.getBlockPos().getY(),tile.getBlockPos().getZ()) <= 100;
    }


    public static class Provider implements MenuProvider{

        private final InfusingTableTile tile;

        public Provider(InfusingTableTile tile){
            this.tile = tile;
        }

        @Override
        public Component getDisplayName() {
            return new TextComponent("");
        }

        @Nullable
        @Override
        public AbstractContainerMenu createMenu(int id, Inventory inv, Player player) {
            return new InfusingTableTileContainer(id,inv,tile.getBlockPos());
        }
    }
}
