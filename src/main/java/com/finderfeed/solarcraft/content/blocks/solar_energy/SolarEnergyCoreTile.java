package com.finderfeed.solarcraft.content.blocks.solar_energy;

import com.finderfeed.solarcraft.content.blocks.blockentities.SolarcraftBlockEntity;
import com.finderfeed.solarcraft.content.items.solar_wand.wand_actions.structure_check.IStructureOwner;
import com.finderfeed.solarcraft.helpers.Helpers;
import com.finderfeed.solarcraft.helpers.multiblock.MultiblockStructure;
import com.finderfeed.solarcraft.helpers.multiblock.Multiblocks;
import com.finderfeed.solarcraft.local_library.helpers.CompoundNBTHelper;
import com.finderfeed.solarcraft.registries.tile_entities.SCTileEntities;
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

public class SolarEnergyCoreTile extends SolarcraftBlockEntity implements SolarEnergyContainer,Bindable, IStructureOwner {

    public static final int MAX_ENERGY = 1000000;
    private List<BlockPos> bindedTiles = new ArrayList<>();
    private int solarEnergy = 0;

    public SolarEnergyCoreTile( BlockPos p_155229_, BlockState p_155230_) {
        super(SCTileEntities.SOLAR_CORE_TILE.get(), p_155229_, p_155230_);
    }


    public static void tick(Level world, BlockPos pos, BlockState blockState, SolarEnergyCoreTile tile) {
        if (!world.isClientSide){
            if (Multiblocks.SOLAR_CORE.check(world,pos,true)){
            checkTilesAndGiveEnrgy(world,tile);
            }
        }
    }

    public static void checkTilesAndGiveEnrgy(Level world, SolarEnergyCoreTile tile){
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
        return MAX_ENERGY;
    }

    @Override
    public BlockPos getPos() {
        return worldPosition;
    }

    @Override
    public double getSolarEnergyCollectionRadius() {
        return 10;
    }

    @Override
    public boolean canBeBinded() {
        return true;
    }

    @Override
    public int maxEnergyInput() {
        return 100;
    }


    public List<BlockPos> getBindedTiles() {
        return bindedTiles;
    }



    @Override
    public boolean bind(BlockPos pos) {
        BlockEntity entity = level.getBlockEntity(pos);
        if ((entity instanceof SolarEnergyRepeaterTile || entity instanceof SolarEnergyContainer) &&
                Helpers.isReachable(level,worldPosition,pos,getSolarEnergyCollectionRadius())) {

            this.bindedTiles.add(pos);
            Helpers.updateTile(this);
            return true;
        }

        return false;
    }

    @Override
    public List<MultiblockStructure> getMultiblocks() {
        return List.of(Multiblocks.SOLAR_CORE);
    }
}
