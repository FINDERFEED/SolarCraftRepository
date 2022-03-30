package com.finderfeed.solarforge.magic.blocks.blockentities.containers;

import com.finderfeed.solarforge.Helpers;
import com.finderfeed.solarforge.magic.blocks.blockentities.RunicEnergyChargerTileEntity;
import com.finderfeed.solarforge.magic.items.RuneItem;
import com.finderfeed.solarforge.magic.items.runic_energy.IRunicEnergyUser;
import com.finderfeed.solarforge.registries.containers.Containers;
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
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RunicEnergyChargerContainer extends AbstractContainerMenu {

    public RunicEnergyChargerTileEntity tile;

    public RunicEnergyChargerContainer(int id,Inventory inv,BlockPos pos) {
        super(Containers.RUNIC_ENERGY_CHARGER.get(), id);
        this.tile = (RunicEnergyChargerTileEntity) inv.player.level.getBlockEntity(pos);

        this.addSlot(new SlotItemHandler(tile.getInventory(), 0,   56, 26){
            @Override
            public boolean mayPlace(@NotNull ItemStack stack) {
                return stack.getItem() instanceof RuneItem;
            }
        });
        this.addSlot(new SlotItemHandler(tile.getInventory(), 1, 56+108, 26){
            @Override
            public boolean mayPlace(@NotNull ItemStack stack) {
                return stack.getItem() instanceof IRunicEnergyUser;
            }
        });

        for(int l = 0; l < 3; ++l) {
            for(int j1 = 0; j1 < 9; ++j1) {
                this.addSlot(new Slot(inv, j1 + l * 9 + 9,   8+j1 * 18 + 30, 103 + l * 18 -20));
            }
        }

        for(int i1 = 0; i1 < 9; ++i1) {
            this.addSlot(new Slot(inv, i1,  8+ i1 * 18 + 30, 161 -20));
        }
    }

    public RunicEnergyChargerContainer(int windowid, Inventory inv, FriendlyByteBuf buf) {
        this(windowid,inv,buf.readBlockPos());
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
