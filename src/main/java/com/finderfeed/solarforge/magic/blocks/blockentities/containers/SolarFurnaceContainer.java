package com.finderfeed.solarforge.magic.blocks.blockentities.containers;

import com.finderfeed.solarforge.magic.blocks.blockentities.SolarEnergyFurnaceTile;
import com.finderfeed.solarforge.registries.containers.SolarcraftContainers;
import net.minecraft.world.entity.player.Inventory;

import net.minecraft.world.inventory.FurnaceResultSlot;
import net.minecraft.world.inventory.Slot;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.core.BlockPos;

import java.util.Objects;

public class SolarFurnaceContainer extends AbstractContainer<SolarEnergyFurnaceTile>{

    public SolarFurnaceContainer( int windowId, Inventory playerInv, SolarEnergyFurnaceTile te, ContainerData array) {
        super(SolarcraftContainers.SOLAR_FURNACE_CONTAINER.get(), windowId, playerInv, te, array);
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


    public SolarFurnaceContainer( int windowId, Inventory playerInv, FriendlyByteBuf buf) {
        this(windowId, playerInv,getTileEntity(playerInv,buf.readBlockPos()),new SimpleContainerData(3));
    }



    private static SolarEnergyFurnaceTile getTileEntity(final Inventory playerInv, final BlockPos pos) {
        Objects.requireNonNull(playerInv, "Player Inventory cannot be null.");
        Objects.requireNonNull(pos, "Pos cannot be null.");

        final BlockEntity te = playerInv.player.level.getBlockEntity(pos);

        if (te instanceof SolarEnergyFurnaceTile) {
            return (SolarEnergyFurnaceTile) te;
        }
        throw new IllegalStateException("Tile Entity Is Not Correct");
    }

}
