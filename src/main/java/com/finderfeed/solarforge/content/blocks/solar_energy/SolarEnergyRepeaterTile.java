package com.finderfeed.solarforge.content.blocks.solar_energy;

import com.finderfeed.solarforge.content.blocks.blockentities.SolarcraftBlockEntity;
import com.finderfeed.solarforge.helpers.Helpers;
import com.finderfeed.solarforge.local_library.helpers.CompoundNBTHelper;
import com.finderfeed.solarforge.registries.tile_entities.SolarcraftTileEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class SolarEnergyRepeaterTile extends SolarcraftBlockEntity implements Bindable{

    private BlockPos connection = Helpers.NULL_POS;

    public SolarEnergyRepeaterTile( BlockPos p_155229_, BlockState p_155230_) {
        super(SolarcraftTileEntityTypes.SOLAR_REPEATER.get(), p_155229_, p_155230_);
    }

    public static void tick(Level world, BlockPos pos, BlockState blockState, SolarEnergyRepeaterTile tile) {
        if (!world.isClientSide && tile.connection != null){
            BlockEntity t = world.getBlockEntity(tile.connection);
            if (!(t instanceof SolarEnergyRepeaterTile) && !(t instanceof SolarEnergyContainer)){
                tile.setConnection(null);
            }
        }
    }

    public SolarEnergyContainer findContainer(List<BlockPos> posList){
        if (connection == null) return null;
        BlockEntity e = level.getBlockEntity(connection);
        if (e instanceof SolarEnergyRepeaterTile repeater && !posList.contains(worldPosition)){
            posList.add(worldPosition);
            return repeater.findContainer(posList);
        }else if (e instanceof SolarEnergyContainer container){
            return container;
        }
        return null;
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        if (connection != null){
            CompoundNBTHelper.writeBlockPos("connection",connection,tag);
        }else{
            CompoundNBTHelper.writeBlockPos("connection",Helpers.NULL_POS,tag);
        }
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        this.connection = CompoundNBTHelper.getBlockPos("connection",tag);
        if (this.connection.equals(Helpers.NULL_POS)){
            this.connection = null;
        }
    }

    public void setConnection(BlockPos connection) {
        this.connection = connection;
        Helpers.updateTile(this);
    }

    @Override
    public boolean bind(BlockPos pos) {
        BlockEntity entity = level.getBlockEntity(pos);
        if (entity instanceof SolarEnergyRepeaterTile repeater){
            if (Helpers.isReachable(level,worldPosition,pos,14)){
                this.setConnection(pos);

                return true;
            }
        }else if (entity instanceof SolarEnergyContainer container && container.canBeBinded()){
            if (Helpers.isReachable(level,worldPosition,pos,container.getSolarEnergyCollectionRadius())){
                this.setConnection(pos);

                return true;
            }
        }
        return false;
    }


    public BlockPos getConnection() {
        return connection;
    }

    @Override
    public AABB getRenderBoundingBox() {
        return Helpers.createAABBWithRadius(Helpers.getBlockCenter(worldPosition),16,16);
    }
}
