package com.finderfeed.solarforge.misc_things;

import com.finderfeed.solarforge.Helpers;
import com.finderfeed.solarforge.packet_handler.SolarForgePacketHandler;
import com.finderfeed.solarforge.packets.RepeaterParentUpdateOnClient;
import com.finderfeed.solarforge.packets.TileEnergyGeneratorUpdate;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.fml.network.PacketDistributor;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

public abstract class AbstractEnergyGeneratorTileEntity extends TileEntity implements ITickableTileEntity,IBindable,ISolarEnergyContainer{

    public int update_tick = 0;
    public int SOLAR_ENERGY = 0;
    public List<BlockPos> poslist = new ArrayList<>();
    public int count = 0;
    public AbstractEnergyGeneratorTileEntity(TileEntityType<?> p_i48289_1_) {
        super(p_i48289_1_);
    }

    @Override
    public void tick() {

        if (!this.level.isClientSide && getConditionToFunction()){
            if (SOLAR_ENERGY+getEnergyPerSecond() <= getEnergyCap()) {
                SOLAR_ENERGY += getEnergyPerSecond();
            }


        }

        if (!this.level.isClientSide){


            count = poslist.size();
            List<BlockPos> toRemove = new ArrayList<>();
            for (BlockPos CONNECTED_TO : poslist) {
                boolean a = false;

                if (CONNECTED_TO != Helpers.NULL_POS && (level.getBlockEntity(CONNECTED_TO) instanceof AbstractSolarNetworkRepeater
                        || level.getBlockEntity(CONNECTED_TO) instanceof IEnergyUser
                        || level.getBlockEntity(CONNECTED_TO) instanceof AbstractSolarCore)) {
                    if (level.getBlockEntity(CONNECTED_TO) instanceof IEnergyUser) {
                        IEnergyUser user = (IEnergyUser) level.getBlockEntity(CONNECTED_TO);

                        if (SOLAR_ENERGY >= getEnergyFlowAmount() && user.requriesEnergy()) {
                            user.giveEnergy(getEnergyFlowAmount());
                            SOLAR_ENERGY -= getEnergyFlowAmount();
                        }

                    } else if (level.getBlockEntity(CONNECTED_TO) instanceof AbstractSolarNetworkRepeater) {
                        AbstractSolarNetworkRepeater rep = (AbstractSolarNetworkRepeater) level.getBlockEntity(CONNECTED_TO);
                        List<BlockPos > visited = new ArrayList<>();
                        rep.tryTransmitEnergy(this, getEnergyFlowAmount(),visited);
                    }else if (level.getBlockEntity(CONNECTED_TO) instanceof AbstractSolarCore) {
                        AbstractSolarCore core = (AbstractSolarCore) level.getBlockEntity(CONNECTED_TO);
                        if (SOLAR_ENERGY >= getEnergyFlowAmount()){
                            core.energy+=getEnergyFlowAmount();
                            SOLAR_ENERGY -= getEnergyFlowAmount();
                        }
                    }
                } else {

                    toRemove.add(CONNECTED_TO);
                    a = true;
                }

                    SolarForgePacketHandler.INSTANCE.send(PacketDistributor.NEAR.with(PacketDistributor.TargetPoint.p(worldPosition.getX(), worldPosition.getY(), worldPosition.getZ(), 40, level.dimension())),
                            new TileEnergyGeneratorUpdate(worldPosition, CONNECTED_TO, poslist.indexOf(CONNECTED_TO), a));


            }
            poslist.removeAll(toRemove);
        }
    }

    public abstract int getEnergyFlowAmount();
    public abstract int getEnergyCap();
    public abstract int getRadius();
    public abstract int getEnergyPerSecond();
    public abstract boolean getConditionToFunction();

    @Override
    public CompoundNBT save(CompoundNBT p_189515_1_) {
        p_189515_1_.putInt("sizepos",count);
        for (int i = 0;i<poslist.size();i++) {
            p_189515_1_.putInt("xpos"+i, poslist.get(i).getX());
            p_189515_1_.putInt("ypos"+i, poslist.get(i).getY());
            p_189515_1_.putInt("zpos"+i, poslist.get(i).getZ());
        }
        p_189515_1_.putInt("energy_level",SOLAR_ENERGY);
        return super.save(p_189515_1_);
    }

    @Override
    public void load(BlockState p_230337_1_, CompoundNBT p_230337_2_) {
        count = p_230337_2_.getInt("sizepos");
        for (int i = 0;i<count;i++) {
            poslist.add(new BlockPos(p_230337_2_.getInt("xpos"+i), p_230337_2_.getInt("ypos"+i), p_230337_2_.getInt("zpos"+i)));
        }
        SOLAR_ENERGY = p_230337_2_.getInt("energy_level");
        super.load(p_230337_1_, p_230337_2_);
    }

    @Override
    public void bindPos(BlockPos pos) {
        System.out.println("a");
        if (!poslist.contains(pos) && !(this.level.getBlockEntity(pos) instanceof AbstractEnergyGeneratorTileEntity) && Helpers.isReachable(level,worldPosition,pos,getRadius())){
            poslist.add(pos);

            System.out.println("b");
        }

    }


//    public void update(){
//        SolarForgePacketHandler.INSTANCE.send(PacketDistributor.NEAR.with(PacketDistributor.TargetPoint.p(worldPosition.getX(),worldPosition.getY(),worldPosition.getZ(),20,level.dimension())),
//                new TileEnergyGeneratorUpdate(SOLAR_ENERGY,worldPosition));
//    }


    @Override
    public double getEnergy() {
        return SOLAR_ENERGY;
    }
}
