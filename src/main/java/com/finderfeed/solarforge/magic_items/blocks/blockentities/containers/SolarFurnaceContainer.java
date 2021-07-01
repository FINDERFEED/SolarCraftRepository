package com.finderfeed.solarforge.magic_items.blocks.blockentities.containers;

import com.finderfeed.solarforge.magic_items.blocks.blockentities.SolarEnergyFurnaceTile;
import com.finderfeed.solarforge.registries.containers.Containers;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.FurnaceResultSlot;
import net.minecraft.inventory.container.Slot;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;
import net.minecraft.util.math.BlockPos;

import java.util.Objects;

public class SolarFurnaceContainer extends AbstractContainer<SolarEnergyFurnaceTile>{

    public SolarFurnaceContainer( int windowId, PlayerInventory playerInv, SolarEnergyFurnaceTile te, IIntArray array) {
        super(Containers.SOLAR_FURNACE_CONTAINER.get(), windowId, playerInv, te, array);
        this.addSlot(new Slot(te,0,48,35));
        this.addSlot(new FurnaceResultSlot(playerInv.player,te,1,108,35));

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

    public int getEnergy(){
        return arr.get(0);
    }
    public int getRecipeProgress(){return arr.get(1);}
    public int getMaxRecipeProgress(){return arr.get(2);}


    public SolarFurnaceContainer( int windowId, PlayerInventory playerInv, PacketBuffer buf) {
        this(windowId, playerInv,getTileEntity(playerInv,buf.readBlockPos()),new IntArray(3));
    }



    private static SolarEnergyFurnaceTile getTileEntity(final PlayerInventory playerInv, final BlockPos pos) {
        Objects.requireNonNull(playerInv, "Player Inventory cannot be null.");
        Objects.requireNonNull(pos, "Pos cannot be null.");

        final TileEntity te = playerInv.player.level.getBlockEntity(pos);

        if (te instanceof SolarEnergyFurnaceTile) {
            return (SolarEnergyFurnaceTile) te;
        }
        throw new IllegalStateException("Tile Entity Is Not Correct");
    }

}
