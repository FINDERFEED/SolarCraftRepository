package com.finderfeed.solarforge.misc_things;

import com.finderfeed.solarforge.Helpers;

import com.finderfeed.solarforge.local_library.helpers.CompoundNBTHelper;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.nbt.CompoundTag;

import net.minecraft.world.level.block.entity.BlockEntity;

import net.minecraft.core.BlockPos;



import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractEnergyGeneratorTileEntity extends BlockEntity implements IBindable,ISolarEnergyContainer{

    public int SOLAR_ENERGY = 0;
    public List<BlockPos> poslist = new ArrayList<>();
    public int count = 0;

    public AbstractEnergyGeneratorTileEntity(BlockEntityType<?> type,BlockPos p_155229_, BlockState p_155230_) {
        super(type, p_155229_, p_155230_);
    }



    public static void tick(Level world, BlockPos pos, BlockState blockState, AbstractEnergyGeneratorTileEntity tile) {

        if (!world.isClientSide && tile.getConditionToFunction()){

            if (tile.SOLAR_ENERGY+tile.getEnergyPerSecond() <= tile.getEnergyCap()) {
                tile.SOLAR_ENERGY += tile.getEnergyPerSecond();
            }else{
                tile.SOLAR_ENERGY = tile.getEnergyCap();
            }
            if (tile.level.getGameTime() % 20 == 0){
                tile.setChanged();
                tile.level.sendBlockUpdated(pos,blockState,blockState,3);
            }
        }

        if (!world.isClientSide){
            tile.count = tile.poslist.size();
            List<BlockPos> toRemove = new ArrayList<>();
            for (BlockPos CONNECTED_TO : tile.poslist) {
                if (CONNECTED_TO != Helpers.NULL_POS && (tile.level.getBlockEntity(CONNECTED_TO) instanceof AbstractSolarNetworkRepeater
                        || tile.level.getBlockEntity(CONNECTED_TO) instanceof IEnergyUser
                        || tile.level.getBlockEntity(CONNECTED_TO) instanceof AbstractSolarCore)) {
                    if (tile.level.getBlockEntity(CONNECTED_TO) instanceof IEnergyUser) {
                        IEnergyUser user = (IEnergyUser) tile.level.getBlockEntity(CONNECTED_TO);
                        if (user.requriesEnergy()) {
                            if (tile.getEnergy() >= tile.getEnergyFlowAmount()) {
                                int flag = user.giveEnergy(tile.getEnergyFlowAmount());
                                tile.SOLAR_ENERGY-= tile.getEnergyFlowAmount();
                                tile.SOLAR_ENERGY+=flag;
                            } else if (tile.getEnergy() > 0) {
                                int flag = user.giveEnergy((int) tile.getEnergy());
                                tile.SOLAR_ENERGY-=tile.getEnergy();
                                tile.SOLAR_ENERGY+=flag;
                            }
                        }

                    } else if (tile.level.getBlockEntity(CONNECTED_TO) instanceof AbstractSolarNetworkRepeater) {
                        AbstractSolarNetworkRepeater rep = (AbstractSolarNetworkRepeater) tile.level.getBlockEntity(CONNECTED_TO);
                        List<BlockPos > visited = new ArrayList<>();
                        rep.tryTransmitEnergy(tile, tile.getEnergyFlowAmount(),visited);
                    }else if (tile.level.getBlockEntity(CONNECTED_TO) instanceof AbstractSolarCore) {
                        AbstractSolarCore core = (AbstractSolarCore) tile.level.getBlockEntity(CONNECTED_TO);
                        if (tile.getEnergy() >= tile.getEnergyFlowAmount()){
                            int flag = core.giveEnergy(tile.getEnergyFlowAmount());
                            tile.SOLAR_ENERGY-=tile.getEnergyFlowAmount();
                            tile.SOLAR_ENERGY+=flag;
                        }else if (tile.getEnergy() > 0){
                            int flag = core.giveEnergy((int)tile.getEnergy());
                            tile.SOLAR_ENERGY-=tile.SOLAR_ENERGY;
                            tile.SOLAR_ENERGY+=flag;
                        }
                    }
                } else {
                    toRemove.add(CONNECTED_TO);
                }
//                    SolarForgePacketHandler.INSTANCE.send(PacketDistributor.NEAR.with(PacketDistributor.TargetPoint.p(pos.getX(), pos.getY(), pos.getZ(), 40, world.dimension())),
//                            new TileEnergyGeneratorUpdate(pos, CONNECTED_TO, tile.poslist.indexOf(CONNECTED_TO), a));
            }
            tile.poslist.removeAll(toRemove);
        }
    }

    public abstract int getEnergyFlowAmount();
    public abstract int getEnergyCap();
    public abstract int getRadius();
    public abstract int getEnergyPerSecond();
    public abstract boolean getConditionToFunction();

    @Nullable
    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        CompoundTag tag = new CompoundTag();
        this.saveAdditional(tag);

        return Helpers.createTilePacket(this,tag);
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        this.load(pkt.getTag());
    }

    @Override
    public void saveAdditional(CompoundTag p_189515_1_) {
        CompoundNBTHelper.writeBlockPosList("positions",poslist,p_189515_1_);
        p_189515_1_.putInt("energy_level",SOLAR_ENERGY);
        super.saveAdditional(p_189515_1_);
    }

    @Override
    public void load( CompoundTag p_230337_2_) {
        poslist = CompoundNBTHelper.getBlockPosList("positions",p_230337_2_);
        SOLAR_ENERGY = p_230337_2_.getInt("energy_level");
        super.load( p_230337_2_);
    }

    @Override
    public void bindPos(BlockPos pos) {

        if (!poslist.contains(pos) && !(this.level.getBlockEntity(pos) instanceof AbstractEnergyGeneratorTileEntity) && Helpers.isReachable(level,worldPosition,pos,getRadius())){
            poslist.add(pos);


        }

    }





    @Override
    public double getEnergy() {
        return SOLAR_ENERGY;
    }
}
