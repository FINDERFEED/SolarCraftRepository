package com.finderfeed.solarforge.misc_things;

import com.finderfeed.solarforge.helpers.Helpers;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.nbt.CompoundTag;

import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.core.BlockPos;


import java.util.List;

public abstract class AbstractSolarNetworkRepeater extends BlockEntity implements IRepeater,IBindable {

    public BlockPos connectedTo = Helpers.NULL_POS;

    public AbstractSolarNetworkRepeater(BlockEntityType<?> p_155228_, BlockPos p_155229_, BlockState p_155230_) {
        super(p_155228_, p_155229_, p_155230_);
    }


    public static void tick(Level world, BlockPos pos, BlockState blockState, AbstractSolarNetworkRepeater tile) {
        if (!tile.level.isClientSide){

            if (tile.level.getBlockEntity(tile.connectedTo) == null){

                tile.connectedTo = Helpers.NULL_POS;
            }else if (!((tile.level.getBlockEntity(tile.connectedTo)) instanceof AbstractSolarNetworkRepeater) &&
                    !((tile.level.getBlockEntity(tile.connectedTo)) instanceof AbstractSolarCore) &&
                    !((tile.level.getBlockEntity(tile.connectedTo)) instanceof IEnergyUser)){

                tile.connectedTo = Helpers.NULL_POS;
            }

        }
    }

    public void tryTransmitEnergy(AbstractEnergyGeneratorTileEntity generator, int amount, List<BlockPos> visited){
        if (connectedTo != null){
            BlockEntity tile = this.level.getBlockEntity(connectedTo);
            if (tile != null){
                if (tile instanceof AbstractSolarNetworkRepeater && !visited.contains(connectedTo)){
                    visited.add(connectedTo);
                    ((AbstractSolarNetworkRepeater)tile).tryTransmitEnergy(generator,amount,visited);
                }else if(tile instanceof IEnergyUser user){
                    if (user.requriesEnergy()) {
                        if (generator.getEnergy() >= generator.getEnergyFlowAmount()) {
                            int flag = user.giveEnergy(generator.getEnergyFlowAmount());
                            generator.SOLAR_ENERGY-= generator.getEnergyFlowAmount();
                            generator.SOLAR_ENERGY+=flag;
                        } else if (generator.getEnergy() > 0) {
                            int flag = user.giveEnergy((int) generator.getEnergy());
                            generator.SOLAR_ENERGY-=generator.getEnergy();
                            generator.SOLAR_ENERGY+=flag;
                        }
                    }
                }else if (tile instanceof AbstractSolarCore core){
                    if (generator.getEnergy() >= generator.getEnergyFlowAmount()){
                        int flag = core.giveEnergy(generator.getEnergyFlowAmount());
                        generator.SOLAR_ENERGY-=generator.getEnergyFlowAmount();
                        generator.SOLAR_ENERGY+=flag;
                    }else if (generator.getEnergy() > 0){
                        int flag = core.giveEnergy((int)generator.getEnergy());
                        generator.SOLAR_ENERGY-=generator.SOLAR_ENERGY;
                        generator.SOLAR_ENERGY+=flag;
                    }
                }
            }
        }
    }
    public void tryTransmitEnergyCore(AbstractSolarCore generator,int amount,List<BlockPos> visited){
        if (connectedTo != null){
            BlockEntity tile = this.level.getBlockEntity(connectedTo);
            if (tile != null){
                if (tile instanceof AbstractSolarNetworkRepeater && !visited.contains(connectedTo)){
                    visited.add(connectedTo);
                    ((AbstractSolarNetworkRepeater)tile).tryTransmitEnergyCore(generator,amount,visited);
                }else if(tile instanceof IEnergyUser){
                    IEnergyUser user = (IEnergyUser) tile;
                    if (user.requriesEnergy()) {
                        if (generator.getEnergy() >= generator.getMaxEnergyFlowPerSec()) {
                            int flag = user.giveEnergy(generator.getMaxEnergyFlowPerSec());
                            generator.energy-= generator.getMaxEnergyFlowPerSec();
                            generator.giveEnergy(flag);
                        } else if (generator.getEnergy() > 0) {
                            int flag = user.giveEnergy((int) generator.getEnergy());
                            generator.energy-=generator.getEnergy();
                            generator.giveEnergy(flag);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void saveAdditional(CompoundTag p_189515_1_) {

            p_189515_1_.putInt("xpos", connectedTo.getX());
            p_189515_1_.putInt("ypos", connectedTo.getY());
            p_189515_1_.putInt("zpos", connectedTo.getZ());

         super.saveAdditional(p_189515_1_);
    }


    @Override
    public void load( CompoundTag c) {
        connectedTo = new BlockPos(c.getInt("xpos"),c.getInt("ypos"),c.getInt("zpos"));
        super.load( c);
    }


    @Override
    public void bindPos(BlockPos pos) {
        if (!connectedTo.equals(pos)){
            BlockEntity tileAtPos = level.getBlockEntity(pos);

            if (tileAtPos instanceof AbstractSolarNetworkRepeater) {
                if ((((AbstractSolarNetworkRepeater) tileAtPos).connectedTo != this.worldPosition) && Helpers.isReachable(level,worldPosition,pos,getRadius())) {
                    connectedTo = pos;
                }
            }else if (tileAtPos instanceof AbstractEnergyGeneratorTileEntity){
                ((AbstractEnergyGeneratorTileEntity) tileAtPos).bindPos(worldPosition);
            }else if (tileAtPos instanceof AbstractSolarCore){
                if (Helpers.isReachable(level,worldPosition,pos,this.getRadius())) {
                    connectedTo = pos;
                }
            }else if (tileAtPos instanceof IEnergyUser){

                if (Helpers.isReachable(level,worldPosition,pos,this.getRadius())) {
                    connectedTo = pos;
                }
            }
        }
        update(this);
    }
}
