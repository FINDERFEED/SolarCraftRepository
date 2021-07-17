package com.finderfeed.solarforge.magic_items.blocks.blockentities;

import com.finderfeed.solarforge.misc_things.RunicEnergy;
import com.finderfeed.solarforge.registries.tile_entities.TileEntitiesRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

import javax.annotation.Nullable;

public class RuneEnergyPylonTile extends TileEntity implements ITickableTileEntity {

    private RunicEnergy.Type type = null;
    private int currentEnergy = 0;
    private int energyPerTick = 2;
    private int maxEnergy = 100000;

    public RuneEnergyPylonTile() {
        super(TileEntitiesRegistry.RUNE_ENERGY_PYLON.get());
    }





    public void setType(RunicEnergy.Type type) {
        this.type = type;
    }

    @Override
    public void tick() {
        if (!this.level.isClientSide){
            if (this.type == null){
                this.type = RunicEnergy.Type.values()[level.random.nextInt(RunicEnergy.Type.values().length-1)];
            }
            if (currentEnergy+energyPerTick <= maxEnergy){
                currentEnergy+=energyPerTick;
            }else{
                currentEnergy = maxEnergy;
            }

        }
            System.out.println(type);

    }



    @Override
    public CompoundNBT save(CompoundNBT nbt) {
        if (this.type != null) {
            nbt.putString("energy_type", type.id);
        }
        nbt.putInt("energy",currentEnergy);
        nbt.putInt("energy_tick",energyPerTick);
        nbt.putInt("maxenergy",maxEnergy);
        return super.save(nbt);
    }

    @Override
    public void load(BlockState p_230337_1_, CompoundNBT nbt) {

        this.type = RunicEnergy.Type.byId(nbt.getString("energy_type"));
        currentEnergy = nbt.getInt("energy");
        energyPerTick = nbt.getInt("energy_tick");
        maxEnergy = nbt.getInt("maxenergy");
        super.load(p_230337_1_, nbt);
    }

    public void setCurrentEnergy(int currentEnergy) {
        this.currentEnergy = currentEnergy;
    }

    public void setEnergyPerTick(int energyPerTick) {
        this.energyPerTick = energyPerTick;
    }

    public void setMaxEnergy(int maxEnergy) {
        this.maxEnergy = maxEnergy;
    }



    public RunicEnergy.Type getEnergyType() {
        return type;
    }

    public int getEnergyPerTick() {
        return energyPerTick;
    }

    public int getCurrentEnergy() {
        return currentEnergy;
    }

    public int getMaxEnergy() {
        return maxEnergy;
    }
}
