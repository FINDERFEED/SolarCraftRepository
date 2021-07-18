package com.finderfeed.solarforge.magic_items.blocks.blockentities;

import com.finderfeed.solarforge.misc_things.RunicEnergy;
import com.finderfeed.solarforge.packet_handler.SolarForgePacketHandler;
import com.finderfeed.solarforge.packets.UpdateTypeOnClientPacket;
import com.finderfeed.solarforge.registries.tile_entities.TileEntitiesRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.network.PacketDistributor;



public class RuneEnergyPylonTile extends TileEntity implements ITickableTileEntity {

    private RunicEnergy.Type type = null;
    private int currentEnergy = 0;
    private int energyPerTick = 2;
    private int maxEnergy = 100000;
    private int updateTick = 40;

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
            updateTick++;

            if (updateTick >= 40){
                SolarForgePacketHandler.INSTANCE.send(PacketDistributor.NEAR.with(PacketDistributor.TargetPoint.p(worldPosition.getX(), worldPosition.getY(), worldPosition.getZ(), 40, level.dimension())),
                        new UpdateTypeOnClientPacket(worldPosition, type.id));
                updateTick = 0;
            }
        }


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
