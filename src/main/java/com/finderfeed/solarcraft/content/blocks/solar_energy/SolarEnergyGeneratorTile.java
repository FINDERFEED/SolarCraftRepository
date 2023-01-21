package com.finderfeed.solarcraft.content.blocks.solar_energy;

import com.finderfeed.solarcraft.content.blocks.blockentities.SolarcraftBlockEntity;
import com.finderfeed.solarcraft.helpers.Helpers;
import com.finderfeed.solarcraft.helpers.multiblock.Multiblocks;
import com.finderfeed.solarcraft.local_library.helpers.CompoundNBTHelper;
import com.finderfeed.solarcraft.registries.tile_entities.SolarcraftTileEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SolarEnergyGeneratorTile extends SolarcraftBlockEntity implements SolarEnergyContainer,Bindable{

    private List<BlockPos> bindedTiles = new ArrayList<>();
    private int solarEnergy = 0;

    public SolarEnergyGeneratorTile( BlockPos p_155229_, BlockState p_155230_) {
        super(SolarcraftTileEntityTypes.ENERGY_GENERATOR_TILE.get(), p_155229_, p_155230_);
    }

    public static void tick(Level world, BlockPos pos, BlockState blockState, SolarEnergyGeneratorTile tile) {
        if (!world.isClientSide){
            if (Multiblocks.ENERGY_GENERATOR.check(world,tile.worldPosition,true)) {
                if (Helpers.isDay(world) && world.canSeeSky(pos.above())) {
                    tile.addSolarEnergy(tile.getEnergyPerTick());
                }
                checkTilesAndGiveEnergy(world, tile);
            }
        }
    }



    public static void checkTilesAndGiveEnergy(Level world, SolarEnergyGeneratorTile tile){
        if (!tile.bindedTiles.isEmpty()){
            Iterator<BlockPos> i = tile.bindedTiles.iterator();
            boolean flag = false;
            while (i.hasNext()){
                BlockPos posi = i.next();
                BlockEntity t = world.getBlockEntity(posi);
                if (t instanceof SolarEnergyContainer container){
                    int energyToAdd = Mth.clamp(tile.getSolarEnergy(),0,container.maxEnergyInput());
                    int delta = container.addSolarEnergy(energyToAdd);
                    tile.takeSolarEnergy(energyToAdd);
                    tile.addSolarEnergy(delta);
                }else if (t instanceof SolarEnergyRepeaterTile repeater){
                    SolarEnergyContainer container = repeater.findContainer(new ArrayList<>());
                    if (container != null){
                        int energyToAdd = Mth.clamp(tile.getSolarEnergy(),0,container.maxEnergyInput());
                        int delta = container.addSolarEnergy(energyToAdd);
                        tile.takeSolarEnergy(energyToAdd);
                        tile.addSolarEnergy(delta);
                    }
                }else{
                    flag = true;
                    i.remove();
                }
            }
            if (flag){
                Helpers.updateTile(tile);
            }
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putInt("energy",getSolarEnergy());
        CompoundNBTHelper.writeBlockPosList("binds",bindedTiles,tag);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        this.setSolarEnergy(tag.getInt("energy"));
        bindedTiles = CompoundNBTHelper.getBlockPosList("binds",tag);
    }

    @Override
    public int getSolarEnergy() {
        return solarEnergy;
    }

    @Override
    public void setSolarEnergy(int energy) {
        this.solarEnergy = energy;
    }

    @Override
    public int getMaxSolarEnergy() {
        return 50000;
    }

    @Override
    public BlockPos getPos() {
        return worldPosition;
    }

    @Override
    public double getSolarEnergyCollectionRadius() {
        return 14;
    }

    @Override
    public boolean canBeBinded() {
        return false;
    }

    public int getEnergyPerTick(){
        return 5;
    }

    @Override
    public int maxEnergyInput() {
        return 0;
    }

    @Override
    public boolean bind(BlockPos pos) {
        BlockEntity entity = level.getBlockEntity(pos);
        if ((entity instanceof SolarEnergyRepeaterTile || entity instanceof SolarEnergyContainer) &&
                Helpers.isReachable(level,worldPosition,pos,getSolarEnergyCollectionRadius()) &&
                !bindedTiles.contains(pos)) {

            this.bindedTiles.add(pos);
            Helpers.updateTile(this);
            return true;
        }

        return false;
    }

    public List<BlockPos> getBindedTiles() {
        return bindedTiles;
    }

    @Override
    public AABB getRenderBoundingBox(){
        return Helpers.createAABBWithRadius(Helpers.getBlockCenter(this.worldPosition),16,16);
    }
}
