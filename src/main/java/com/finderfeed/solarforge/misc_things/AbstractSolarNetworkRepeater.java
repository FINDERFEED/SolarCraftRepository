package com.finderfeed.solarforge.misc_things;

import com.finderfeed.solarforge.Helpers;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.BlockPos;


import java.util.List;

public abstract class AbstractSolarNetworkRepeater extends TileEntity implements IRepeater, ITickableTileEntity,IBindable {

    public BlockPos connectedTo = Helpers.NULL_POS;

    public AbstractSolarNetworkRepeater(TileEntityType<?> p_i48289_1_) {
        super(p_i48289_1_);
    }

    @Override
    public void tick() {
        if (!this.level.isClientSide){

            if (level.getBlockEntity(connectedTo) == null){

                this.connectedTo = Helpers.NULL_POS;
            }else if (!((level.getBlockEntity(connectedTo)) instanceof AbstractSolarNetworkRepeater) &&
                    !((level.getBlockEntity(connectedTo)) instanceof AbstractSolarCore) &&
                    !((level.getBlockEntity(connectedTo)) instanceof IEnergyUser)){

                this.connectedTo = Helpers.NULL_POS;
            }

        }
    }

    public void tryTransmitEnergy(AbstractEnergyGeneratorTileEntity generator, int amount, List<BlockPos> visited){
        if (connectedTo != null){
            TileEntity tile = this.level.getBlockEntity(connectedTo);
            if (tile != null){
                if (tile instanceof AbstractSolarNetworkRepeater && !visited.contains(connectedTo)){
                    visited.add(connectedTo);
                    ((AbstractSolarNetworkRepeater)tile).tryTransmitEnergy(generator,amount,visited);
                }else if(tile instanceof IEnergyUser){
                    IEnergyUser user = (IEnergyUser) tile;
                    if (generator.SOLAR_ENERGY >= amount && user.requriesEnergy()){
                        user.giveEnergy(amount);
                        generator.SOLAR_ENERGY-=amount;
                    }
                }else if (tile instanceof AbstractSolarCore){
                    if (generator.SOLAR_ENERGY >= amount && ((AbstractSolarCore) tile).getConditionToFunction() && (((AbstractSolarCore) tile).energy+ amount <= ((AbstractSolarCore) tile).getMaxEnergy())){
                        ((AbstractSolarCore) tile).energy+=amount;
                        generator.SOLAR_ENERGY-=amount;
                    }
                }
            }
        }
    }
    public void tryTransmitEnergyCore(AbstractSolarCore generator,int amount,List<BlockPos> visited){
        if (connectedTo != null){
            TileEntity tile = this.level.getBlockEntity(connectedTo);
            if (tile != null){
                if (tile instanceof AbstractSolarNetworkRepeater && !visited.contains(connectedTo)){
                    visited.add(connectedTo);
                    ((AbstractSolarNetworkRepeater)tile).tryTransmitEnergyCore(generator,amount,visited);
                }else if(tile instanceof IEnergyUser){
                    IEnergyUser user = (IEnergyUser) tile;
                    if (generator.energy >= amount && user.requriesEnergy()){
                        user.giveEnergy(amount);
                        generator.energy-=amount;
                    }
                }
            }
        }
    }

    @Override
    public CompoundNBT save(CompoundNBT p_189515_1_) {

            p_189515_1_.putInt("xpos", connectedTo.getX());
            p_189515_1_.putInt("ypos", connectedTo.getY());
            p_189515_1_.putInt("zpos", connectedTo.getZ());

        return super.save(p_189515_1_);
    }


    @Override
    public void load(BlockState p_230337_1_, CompoundNBT c) {
        connectedTo = new BlockPos(c.getInt("xpos"),c.getInt("ypos"),c.getInt("zpos"));
        super.load(p_230337_1_, c);
    }


    @Override
    public void bindPos(BlockPos pos) {
        if (!connectedTo.equals(pos)){
            TileEntity tileAtPos = level.getBlockEntity(pos);

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
    }
}
