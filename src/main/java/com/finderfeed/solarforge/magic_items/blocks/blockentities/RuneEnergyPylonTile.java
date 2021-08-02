package com.finderfeed.solarforge.magic_items.blocks.blockentities;

import com.finderfeed.solarforge.Helpers;
import com.finderfeed.solarforge.magic_items.items.solar_lexicon.achievements.Achievement;
import com.finderfeed.solarforge.magic_items.runic_network.repeater.IRunicEnergyContainer;
import com.finderfeed.solarforge.magic_items.runic_network.repeater.IRunicEnergyReciever;
import com.finderfeed.solarforge.misc_things.AbstractEnergyGeneratorTileEntity;
import com.finderfeed.solarforge.misc_things.RunicEnergy;
import com.finderfeed.solarforge.packet_handler.SolarForgePacketHandler;
import com.finderfeed.solarforge.packet_handler.packets.UpdateTypeOnClientPacket;
import com.finderfeed.solarforge.registries.tile_entities.TileEntitiesRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.player.Player;
import net.minecraft.nbt.CompoundTag;

import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.AABB;

import net.minecraftforge.fmllegacy.network.PacketDistributor;

import javax.annotation.Nullable;


public class RuneEnergyPylonTile extends BlockEntity implements IRunicEnergyContainer {

    private RunicEnergy.Type type = null;
    private float currentEnergy = 0;
    private float energyPerTick = 0.1f;
    private float maxEnergy = 100000;
    private int updateTick = 40;

    public RuneEnergyPylonTile(BlockPos p_155229_, BlockState p_155230_) {
        super(TileEntitiesRegistry.RUNE_ENERGY_PYLON.get(), p_155229_, p_155230_);
    }


    public void setType(RunicEnergy.Type type) {
        this.type = type;
    }


    public static void tick(Level world, BlockPos pos, BlockState blockState, RuneEnergyPylonTile tile) {
        if (!tile.level.isClientSide){
            if (tile.type == null){
                tile.type = RunicEnergy.Type.values()[tile.level.random.nextInt(RunicEnergy.Type.values().length-1)];
            }

            if (tile.currentEnergy+tile.energyPerTick <= tile.maxEnergy){
                tile.currentEnergy+=tile.energyPerTick;
            }else{
                tile.currentEnergy = tile.maxEnergy;
            }
            tile.updateTick++;

            if (tile.updateTick >= 40){
                SolarForgePacketHandler.INSTANCE.send(PacketDistributor.NEAR.with(PacketDistributor.TargetPoint.p(tile.worldPosition.getX(), tile.worldPosition.getY(), tile.worldPosition.getZ(), 40, tile.level.dimension())),
                        new UpdateTypeOnClientPacket(tile.worldPosition, tile.type.id));
                tile.updateTick = 0;
            }

            AABB box = new AABB(tile.worldPosition.offset(-2,-2,-2),tile.worldPosition.offset(2,2,2));
            tile.level.getEntitiesOfClass(Player.class,box).forEach((player)->{
                Helpers.fireProgressionEvent(player, Achievement.RUNE_ENERGY_DEPOSIT);
            });
        }


    }



    @Override
    public CompoundTag save(CompoundTag nbt) {
        if (this.type != null) {
            nbt.putString("energy_type", type.id);
        }
        nbt.putFloat("energy",currentEnergy);
        nbt.putFloat("energy_tick",energyPerTick);
        nbt.putFloat("maxenergy",maxEnergy);
        return super.save(nbt);
    }

    @Override
    public void load( CompoundTag nbt) {

        this.type = RunicEnergy.Type.byId(nbt.getString("energy_type"));
        currentEnergy = nbt.getFloat("energy");
        energyPerTick = nbt.getFloat("energy_tick");
        maxEnergy = nbt.getFloat("maxenergy");
        super.load( nbt);
    }

    public void givePlayerEnergy(Player entity,float amount){
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

    @Override
    public double extractEnergy(RunicEnergy.Type type, double maxAmount) {
        if (getCurrentEnergy() >= maxAmount){
            this.currentEnergy-=maxAmount;
            return maxAmount;
        }else{
            double b = getCurrentEnergy();
            this.currentEnergy = 0;
            return b;
        }
    }

    @Override
    public double addEnergy(@Nullable RunicEnergy.Type type, double amount) {
        if (amount + getCurrentEnergy() <= getMaxEnergy()){
            this.currentEnergy += amount;
            return 0;
        }else{
            double raznitsa = (getCurrentEnergy()+amount) - getMaxEnergy();
            this.currentEnergy = getMaxEnergy();
            return raznitsa;
        }
    }

    @Override
    public double getRunicEnergyEnergy(@Nullable RunicEnergy.Type amount) {
        return currentEnergy;
    }

    @Override
    public BlockPos getPos() {
        return worldPosition;
    }
}
