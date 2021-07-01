package com.finderfeed.solarforge.misc_things;

import com.finderfeed.solarforge.Helpers;

import com.finderfeed.solarforge.packet_handler.SolarForgePacketHandler;
import com.finderfeed.solarforge.packets.TileEnergyGeneratorUpdate;
import com.finderfeed.solarforge.packets.UpdateCoreOnClient;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;

import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.PacketDistributor;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class AbstractSolarCore extends TileEntity implements ITickableTileEntity,IBindable,ISolarEnergyContainer {
    public int energy = 0;
    public int count = 0;
    public int update_tick=0;
    public List<BlockPos> poslist = new ArrayList<>();
    public AbstractSolarCore(TileEntityType<?> p_i48289_1_) {
        super(p_i48289_1_);
    }

    @Override
    public void tick() {
        if (this.level.isClientSide){
            //System.out.println(poslist);
        }
        if (!this.level.isClientSide && getConditionToFunction()){


            count = poslist.size();
            List<BlockPos> toRemove = new ArrayList<>();

            for (BlockPos a :poslist){
                boolean b = false;
                TileEntity tileAtPos = this.level.getBlockEntity(a);
                if (tileAtPos instanceof AbstractSolarNetworkRepeater){
                    List<BlockPos > visited = new ArrayList<>();
                    ((AbstractSolarNetworkRepeater) tileAtPos).tryTransmitEnergyCore(this,getMaxEnergyFlowPerSec(), visited);
                }else if (tileAtPos instanceof IEnergyUser){
                    if (energy >= getMaxEnergyFlowPerSec() && ((IEnergyUser) tileAtPos).requriesEnergy()  ) {
                        ((IEnergyUser) tileAtPos).giveEnergy(getMaxEnergyFlowPerSec());
                        this.energy -= getMaxEnergyFlowPerSec();
                    }
                }else {

                    toRemove.add(a);
                    b = true;
                }
                //setChanged();

                    SolarForgePacketHandler.INSTANCE.send(PacketDistributor.NEAR.with(PacketDistributor.TargetPoint.p(worldPosition.getX(), worldPosition.getY(), worldPosition.getZ(), 40, level.dimension())),
                            new UpdateCoreOnClient(worldPosition, a, poslist.indexOf(a), b));


            }
            poslist.removeAll(toRemove);
        }
    }

    public abstract int getMaxEnergy();
    public abstract int getMaxEnergyFlowPerSec();
    public abstract int getRadius();
    public abstract boolean getConditionToFunction();

    @Override
    public void load(BlockState p_230337_1_, CompoundNBT p_230337_2_) {
        count = p_230337_2_.getInt("sizepos");
        for (int i = 0;i<count;i++) {
            poslist.add(new BlockPos(p_230337_2_.getInt("xpos"+i), p_230337_2_.getInt("ypos"+i), p_230337_2_.getInt("zpos"+i)));
        }
        energy = p_230337_2_.getInt("energy_level");
        super.load(p_230337_1_, p_230337_2_);
    }

    @Override
    public CompoundNBT save(CompoundNBT p_189515_1_) {
        p_189515_1_.putInt("sizepos",count);
        for (int i = 0;i<poslist.size();i++) {
            p_189515_1_.putInt("xpos"+i, poslist.get(i).getX());
            p_189515_1_.putInt("ypos"+i, poslist.get(i).getY());
            p_189515_1_.putInt("zpos"+i, poslist.get(i).getZ());
        }
        p_189515_1_.putInt("energy_level",energy);
        return super.save(p_189515_1_);
    }


    @Override
    public void bindPos(BlockPos pos) {
        if (!poslist.contains(pos) && !(level.getBlockEntity(pos) instanceof AbstractSolarCore) && Helpers.isReachable(level,worldPosition,pos,getRadius()) && !(level.getBlockEntity(pos) instanceof AbstractEnergyGeneratorTileEntity)){
            poslist.add(pos);
        }
        else if ((level.getBlockEntity(pos) instanceof AbstractEnergyGeneratorTileEntity) && Helpers.isReachable(level,worldPosition,pos,getRadius())){
            ((AbstractEnergyGeneratorTileEntity) level.getBlockEntity(pos)).bindPos(worldPosition);
        }

    }

//    @Nullable
//    @Override
//    public SUpdateTileEntityPacket getUpdatePacket() {
//        System.out.println("a");
//        CompoundNBT cmp = new CompoundNBT();
//        for (int i = 0;i < poslist.size();i++){
//            cmp.put("pos"+i,NBTUtil.writeBlockPos(poslist.get(i)));
//        }
//        cmp.putInt("pos_count",count);
//        return new SUpdateTileEntityPacket(worldPosition,1,cmp);
//    }
//
//
//    @Override
//    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
//        System.out.println("b");
//
//        CompoundNBT cmp = pkt.getTag();
//        int cnt = cmp.getInt("pos_count");
//        List<BlockPos> posList = new ArrayList<>();
//        for (int i = 0; i < cnt;i++){
//            posList.add(NBTUtil.readBlockPos(cmp.getCompound("pos"+i)));
//        }
//        this.poslist = posList;
//        super.onDataPacket(net, pkt);
//    }
    @Override
    public double getEnergy() {
        return energy;
    }
}
