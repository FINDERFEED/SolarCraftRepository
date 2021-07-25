package com.finderfeed.solarforge.misc_things;

import com.finderfeed.solarforge.Helpers;

import com.finderfeed.solarforge.packet_handler.SolarForgePacketHandler;
import com.finderfeed.solarforge.packet_handler.packets.TileEnergyGeneratorUpdate;
import com.finderfeed.solarforge.registries.tile_entities.TileEntitiesRegistry;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.nbt.CompoundTag;

import net.minecraft.world.level.block.entity.BlockEntity;

import net.minecraft.core.BlockPos;

import net.minecraftforge.fmllegacy.network.PacketDistributor;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractEnergyGeneratorTileEntity extends BlockEntity implements IBindable,ISolarEnergyContainer{

    public int update_tick = 0;
    public int SOLAR_ENERGY = 0;
    public List<BlockPos> poslist = new ArrayList<>();
    public int count = 0;

    public AbstractEnergyGeneratorTileEntity( BlockPos p_155229_, BlockState p_155230_) {
        super(TileEntitiesRegistry.ENERGY_GENERATOR_TILE.get(), p_155229_, p_155230_);
    }



    public static void tick(Level world, BlockPos pos, BlockState blockState, AbstractEnergyGeneratorTileEntity tile) {

        if (!world.isClientSide && tile.getConditionToFunction()){
            if (tile.SOLAR_ENERGY+tile.getEnergyPerSecond() <= tile.getEnergyCap()) {
                tile.SOLAR_ENERGY += tile.getEnergyPerSecond();
            }


        }

        if (!world.isClientSide){


            tile.count = tile.poslist.size();
            List<BlockPos> toRemove = new ArrayList<>();
            for (BlockPos CONNECTED_TO : tile.poslist) {
                boolean a = false;

                if (CONNECTED_TO != Helpers.NULL_POS && (tile.level.getBlockEntity(CONNECTED_TO) instanceof AbstractSolarNetworkRepeater
                        || tile.level.getBlockEntity(CONNECTED_TO) instanceof IEnergyUser
                        || tile.level.getBlockEntity(CONNECTED_TO) instanceof AbstractSolarCore)) {
                    if (tile.level.getBlockEntity(CONNECTED_TO) instanceof IEnergyUser) {
                        IEnergyUser user = (IEnergyUser) tile.level.getBlockEntity(CONNECTED_TO);

                        if (tile.SOLAR_ENERGY >= tile.getEnergyFlowAmount() && user.requriesEnergy()) {
                            user.giveEnergy(tile.getEnergyFlowAmount());
                            tile.SOLAR_ENERGY -= tile.getEnergyFlowAmount();
                        }

                    } else if (tile.level.getBlockEntity(CONNECTED_TO) instanceof AbstractSolarNetworkRepeater) {
                        AbstractSolarNetworkRepeater rep = (AbstractSolarNetworkRepeater) tile.level.getBlockEntity(CONNECTED_TO);
                        List<BlockPos > visited = new ArrayList<>();
                        rep.tryTransmitEnergy(tile, tile.getEnergyFlowAmount(),visited);
                    }else if (tile.level.getBlockEntity(CONNECTED_TO) instanceof AbstractSolarCore) {
                        AbstractSolarCore core = (AbstractSolarCore) tile.level.getBlockEntity(CONNECTED_TO);
                        if (tile.SOLAR_ENERGY >= tile.getEnergyFlowAmount()){
                            core.energy+=tile.getEnergyFlowAmount();
                            tile.SOLAR_ENERGY -= tile.getEnergyFlowAmount();
                        }
                    }
                } else {

                    toRemove.add(CONNECTED_TO);
                    a = true;
                }

                    SolarForgePacketHandler.INSTANCE.send(PacketDistributor.NEAR.with(PacketDistributor.TargetPoint.p(pos.getX(), pos.getY(), pos.getZ(), 40, world.dimension())),
                            new TileEnergyGeneratorUpdate(pos, CONNECTED_TO, tile.poslist.indexOf(CONNECTED_TO), a));


            }
            tile.poslist.removeAll(toRemove);
        }
    }

    public abstract int getEnergyFlowAmount();
    public abstract int getEnergyCap();
    public abstract int getRadius();
    public abstract int getEnergyPerSecond();
    public abstract boolean getConditionToFunction();

    @Override
    public CompoundTag save(CompoundTag p_189515_1_) {
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
    public void load( CompoundTag p_230337_2_) {
        count = p_230337_2_.getInt("sizepos");
        for (int i = 0;i<count;i++) {
            poslist.add(new BlockPos(p_230337_2_.getInt("xpos"+i), p_230337_2_.getInt("ypos"+i), p_230337_2_.getInt("zpos"+i)));
        }
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
