package com.finderfeed.solarforge.magic_items.blocks.blockentities;

import com.finderfeed.solarforge.misc_things.RunicEnergy;
import com.finderfeed.solarforge.packet_handler.SolarForgePacketHandler;
import com.finderfeed.solarforge.packet_handler.packets.UpdateTypeOnClientPacket;
import com.finderfeed.solarforge.registries.tile_entities.TileEntitiesRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.network.PacketDistributor;



public class RuneEnergyPylonTile extends TileEntity implements ITickableTileEntity {

    private RunicEnergy.Type type = null;
    private float currentEnergy = 0;
    private float energyPerTick = 0.1f;
    private float maxEnergy = 100000;
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
        nbt.putFloat("energy",currentEnergy);
        nbt.putFloat("energy_tick",energyPerTick);
        nbt.putFloat("maxenergy",maxEnergy);
        return super.save(nbt);
    }

    @Override
    public void load(BlockState p_230337_1_, CompoundNBT nbt) {

        this.type = RunicEnergy.Type.byId(nbt.getString("energy_type"));
        currentEnergy = nbt.getFloat("energy");
        energyPerTick = nbt.getFloat("energy_tick");
        maxEnergy = nbt.getFloat("maxenergy");
        super.load(p_230337_1_, nbt);
    }

    public void givePlayerEnergy(PlayerEntity entity,float amount){
        if (amount <= getCurrentEnergy()){
            this.currentEnergy-=amount;
            float flag = RunicEnergy.givePlayerEnergy(entity,amount,type);
            this.currentEnergy+=flag;

        }
    }


    public void setCurrentEnergy(float currentEnergy) {
        this.currentEnergy = currentEnergy;
    }

    public void setEnergyPerTick(float energyPerTick) {
        this.energyPerTick = energyPerTick;
    }

    public void setMaxEnergy(float maxEnergy) {
        this.maxEnergy = maxEnergy;
    }



    public RunicEnergy.Type getEnergyType() {
        return type;
    }

    public float getEnergyPerTick() {
        return energyPerTick;
    }

    public float getCurrentEnergy() {
        return currentEnergy;
    }

    public float getMaxEnergy() {
        return maxEnergy;
    }
}
