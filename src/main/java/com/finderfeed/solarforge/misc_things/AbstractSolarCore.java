package com.finderfeed.solarforge.misc_things;

import com.finderfeed.solarforge.Helpers;

import com.finderfeed.solarforge.for_future_library.helpers.CompoundNBTHelper;
import com.finderfeed.solarforge.packet_handler.SolarForgePacketHandler;
import com.finderfeed.solarforge.packet_handler.packets.UpdateCoreOnClient;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.nbt.CompoundTag;


import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.core.BlockPos;

import net.minecraftforge.fmllegacy.network.PacketDistributor;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractSolarCore extends BlockEntity implements IBindable,ISolarEnergyContainer {
    public int energy = 0;
    public int count = 0;
    public List<BlockPos> poslist = new ArrayList<>();

    public AbstractSolarCore(BlockEntityType<?> p_155228_, BlockPos p_155229_, BlockState p_155230_) {
        super(p_155228_, p_155229_, p_155230_);
    }



    public static void tick(Level world, BlockPos pos, BlockState blockState, AbstractSolarCore tile) {

        if (!world.isClientSide){
            if (world.getGameTime() % 10 == 0){
                tile.setChanged();
                world.sendBlockUpdated(pos,blockState,blockState,3);
            }
        }
        if (!tile.level.isClientSide && tile.getConditionToFunction()){

            tile.count = tile.poslist.size();
            List<BlockPos> toRemove = new ArrayList<>();

            for (BlockPos a :tile.poslist){
                BlockEntity tileAtPos = tile.level.getBlockEntity(a);
                if (tileAtPos instanceof AbstractSolarNetworkRepeater){
                    List<BlockPos > visited = new ArrayList<>();
                    ((AbstractSolarNetworkRepeater) tileAtPos).tryTransmitEnergyCore(tile,tile.getMaxEnergyFlowPerSec(), visited);
                }else if (tileAtPos instanceof IEnergyUser){
                    IEnergyUser user = (IEnergyUser) tileAtPos;
                    if (user.requriesEnergy()) {
                        if (tile.getEnergy() >= tile.getMaxEnergyFlowPerSec()) {
                            int flag = user.giveEnergy(tile.getMaxEnergyFlowPerSec());
                            tile.energy-= tile.getMaxEnergyFlowPerSec();
                            tile.giveEnergy(flag);
                        } else if (tile.getEnergy() > 0) {
                            int flag = user.giveEnergy((int) tile.getEnergy());
                            tile.energy-=tile.getEnergy();
                            tile.giveEnergy(flag);
                        }
                    }
                }else {
                    toRemove.add(a);
                }
//                    SolarForgePacketHandler.INSTANCE.send(PacketDistributor.NEAR.with(PacketDistributor.TargetPoint.p(tile.worldPosition.getX(), tile.worldPosition.getY(), tile.worldPosition.getZ(), 40, tile.level.dimension())),
//                            new UpdateCoreOnClient(tile.worldPosition, a, tile.poslist.indexOf(a), b));
            }
            tile.poslist.removeAll(toRemove);
        }
    }

    public abstract int getMaxEnergy();
    public abstract int getMaxEnergyFlowPerSec();
    public abstract int getRadius();
    public abstract boolean getConditionToFunction();

    @Nullable
    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return new ClientboundBlockEntityDataPacket(this.worldPosition,3,this.save(new CompoundTag()));
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        this.load(pkt.getTag());
    }

    @Override
    public void load( CompoundTag p_230337_2_) {
//        count = p_230337_2_.getInt("sizepos");
//        for (int i = 0;i<count;i++) {
//            poslist.add(new BlockPos(p_230337_2_.getInt("xpos"+i), p_230337_2_.getInt("ypos"+i), p_230337_2_.getInt("zpos"+i)));
//        }
        CompoundNBTHelper.writeBlockPosList("positions",poslist,p_230337_2_);
        energy = p_230337_2_.getInt("energy_level");
        super.load( p_230337_2_);
    }

    @Override
    public CompoundTag save(CompoundTag p_189515_1_) {
//        p_189515_1_.putInt("sizepos",count);
//        for (int i = 0;i<poslist.size();i++) {
//            p_189515_1_.putInt("xpos"+i, poslist.get(i).getX());
//            p_189515_1_.putInt("ypos"+i, poslist.get(i).getY());
//            p_189515_1_.putInt("zpos"+i, poslist.get(i).getZ());
//        }
        this.poslist = CompoundNBTHelper.getBlockPosList("positions",p_189515_1_);
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


    @Override
    public double getEnergy() {
        return energy;
    }

    public int giveEnergy(int amount){
        if (getEnergy() + amount <= getMaxEnergy()){
            this.energy+=amount;
            return 0;
        }else{
            int raznitsa =(int) getEnergy()+amount - getMaxEnergy();
            this.energy = getMaxEnergy();
            return raznitsa;
        }
    }
}
