package com.finderfeed.solarforge.content.blocks.blockentities.runic_energy;

import com.finderfeed.solarforge.SolarForge;
import com.finderfeed.solarforge.helpers.Helpers;
import com.finderfeed.solarforge.local_library.OwnedBlock;
import com.finderfeed.solarforge.local_library.helpers.CompoundNBTHelper;
import com.finderfeed.solarforge.local_library.helpers.FDMathHelper;
import com.finderfeed.solarforge.content.blocks.blockentities.RuneEnergyPylonTile;
import com.finderfeed.solarforge.content.blocks.blockentities.SolarcraftBlockEntity;
import com.finderfeed.solarforge.content.items.runic_energy.RunicEnergyContainer;
import com.finderfeed.solarforge.content.items.runic_energy.RunicEnergyCost;
import com.finderfeed.solarforge.content.runic_network.algorithms.RunicEnergyPath;
import com.finderfeed.solarforge.content.runic_network.repeater.BaseRepeaterTile;
import com.finderfeed.solarforge.misc_things.RunicEnergy;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.common.world.ForgeChunkManager;

import javax.annotation.Nullable;
import java.util.*;


public abstract class AbstractRunicEnergyContainer extends SolarcraftBlockEntity implements OwnedBlock {

    private int seekingCooldown = 0;
    private RunicEnergyContainer container = new RunicEnergyContainer();
    public List<BlockPos> nullOrGiverPositionForClient = new ArrayList<>();
    private UUID owner;
    private Map<RunicEnergy.Type,List<BlockPos>> PATH_TO_CONTAINERS = new HashMap<>();

    public AbstractRunicEnergyContainer(BlockEntityType<?> p_155228_, BlockPos p_155229_, BlockState p_155230_) {
        super(p_155228_, p_155229_, p_155230_);
    }

    @Override
    public void saveAdditional(CompoundTag tag) {
        saveRunicEnergy(tag);
        tag.putUUID("tileowner",getOwner());
        super.saveAdditional(tag);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        if (tag.contains("tileowner")){
            this.setOwner(tag.getUUID("tileowner"));
        }
        loadRunicEnergy(tag);
    }

    @Override
    public void setOwner(UUID owner) {
        this.owner = owner;
    }

    @Override
    public UUID getOwner() {
        return this.owner;
    }

    public abstract double getMaxEnergyInput();
    public abstract double getRunicEnergyLimit();
    public abstract int getSeekingCooldown();
    public abstract double getMaxRange();

    public void requestRunicEnergy(RunicEnergyCost costs,int multiplier){
        if (seekingCooldown > getSeekingCooldown()){
            tryConstructWays(costs,multiplier);
        }else{
            seekingCooldown++;
        }


        for (RunicEnergy.Type type : costs.getSetTypes()){


            if (!PATH_TO_CONTAINERS.containsKey(type)) continue;
            float cost = costs.get(type);
            double multiplied = cost * multiplier;
            double runicEnergy = getRunicEnergy(type);
            if (multiplied >= runicEnergy + getMaxEnergyInput()) {
                requestSpecificEnergyNew(type, getMaxEnergyInput());
            } else if ((multiplied > runicEnergy) && (multiplied < runicEnergy + getMaxEnergyInput())) {
                double request = multiplied - getRunicEnergy(type);
                requestSpecificEnergyNew(type, request);
            } else {
                List<BlockPos> path = PATH_TO_CONTAINERS.remove(type);
                BlockPos firstPos = path.get(1);

                if (nullOrGiverPositionForClient.contains(firstPos)) {
                    nullOrGiverPositionForClient.remove(firstPos);
                    BlockState state = level.getBlockState(worldPosition);
                    this.setChanged();
                    this.level.sendBlockUpdated(worldPosition, state, state, 3);
                }
                if (!level.isClientSide && level.getBlockEntity(path.get(path.size()-1)) instanceof RuneEnergyPylonTile pylon){
                    ChunkPos pos = new ChunkPos(pylon.getPos());
                    ForgeChunkManager.forceChunk((ServerLevel)level, SolarForge.MOD_ID,
                            pylon.getPos(),pos.x,pos.z,false,true);
                }
                RunicEnergyPath.resetRepeaterConnections(path, level);
            }
        }


    }



    public void requestSpecificEnergyNew(RunicEnergy.Type type,double amount){
            List<BlockPos> route = PATH_TO_CONTAINERS.get(type);
            RunicEnergyPath.setRepeaterConnections(PATH_TO_CONTAINERS.get(type),level);
            BlockPos firstPos = PATH_TO_CONTAINERS.get(type).get(1);
            BlockEntity first = level.getBlockEntity(firstPos);
            if (first instanceof RunicEnergyGiver container){
                if (FDMathHelper.canSee(firstPos,worldPosition,getMaxRange(),level)) {
                    if (!nullOrGiverPositionForClient.contains(firstPos)){
                        nullOrGiverPositionForClient.add(firstPos);
                        BlockState state = level.getBlockState(worldPosition);
                        this.setChanged();
                        this.level.sendBlockUpdated(worldPosition, state, state, 3);
                    }
                    double flag = container.extractEnergy(type, amount);
                    this.giveEnergy(type, flag);
                }else{
                    if (nullOrGiverPositionForClient.contains(firstPos)) {
                        nullOrGiverPositionForClient.remove(firstPos);
                        BlockState state = level.getBlockState(worldPosition);
                        this.setChanged();
                        this.level.sendBlockUpdated(worldPosition, state, state, 3);
                    }
                    PATH_TO_CONTAINERS.remove(type);
                }
            }else if (first instanceof BaseRepeaterTile repeater){
                if (RunicEnergyPath.isRouteCorrect(PATH_TO_CONTAINERS.get(type),level)){
                    if (level.getBlockEntity(route.get(route.size()-1)) instanceof RunicEnergyGiver container){
                        double flag = container.extractEnergy(type,amount);
                        this.giveEnergy(type,flag);
                    }else {
                        RunicEnergyPath.resetRepeaterConnections(PATH_TO_CONTAINERS.get(type),level);
                        PATH_TO_CONTAINERS.remove(type);
                    }
                }else{
                    RunicEnergyPath.resetRepeaterConnections(PATH_TO_CONTAINERS.get(type),level);
                    PATH_TO_CONTAINERS.remove(type);
                }
            }else{
                if (nullOrGiverPositionForClient.contains(firstPos)) {
                    nullOrGiverPositionForClient.remove(firstPos);
                    BlockState state = level.getBlockState(worldPosition);
                    this.setChanged();
                    this.level.sendBlockUpdated(worldPosition, state, state, 3);
                }
                RunicEnergyPath.resetRepeaterConnections(PATH_TO_CONTAINERS.get(type),level);
                PATH_TO_CONTAINERS.remove(type);
            }


    }


    public void spendEnergy(RunicEnergyCost costs,int multiplier){
        for (RunicEnergy.Type type : costs.getSetTypes()){
            this.giveEnergy(type,-costs.get(type)*multiplier);
        }
    }

    public abstract boolean shouldFunction();

    public void onRemove(){
        PATH_TO_CONTAINERS.forEach((type,way)->{
            RunicEnergyPath.resetRepeaterConnections(PATH_TO_CONTAINERS.get(type),level);
        });
    }

    public void resetAllRepeaters(){
        for (RunicEnergy.Type type : RunicEnergy.Type.getAll()){
            breakWay(type);
        }
    }


    @Nullable
    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        CompoundTag cmp = new CompoundTag();
        CompoundNBTHelper.writeBlockPosList("posclient",nullOrGiverPositionForClient,cmp);

        return ClientboundBlockEntityDataPacket.create(this,(tile)->{return cmp;});
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        nullOrGiverPositionForClient = CompoundNBTHelper.getBlockPosList("posclient",pkt.getTag());

    }

    public void clearWays(){
        PATH_TO_CONTAINERS.clear();
    }

    public void giveEnergy(RunicEnergy.Type type, double amount){
        container.set(type,container.get(type)+(float)amount);
    }

    protected boolean isEnough(RunicEnergy.Type type, RunicEnergyCost cost, int multiplier){
        return  container.get(type) >= cost.get(type)*multiplier;
    }

    public boolean hasEnoughRunicEnergy(RunicEnergyCost cost,double multiplier){
        for (RunicEnergy.Type type : RunicEnergy.Type.getAll()){
            float c = cost.get(type);
            if (c == 0) continue;
            if (c*multiplier > getRunicEnergy(type)){
                return false;
            }
        }
        return true;
    }


    private boolean checkRoute(List<BlockPos> route,RunicEnergy.Type type){
        return route != null && (level.getBlockEntity(route.get(1)) instanceof RunicEnergyGiver giver ?
                FDMathHelper.canSee(giver.getPos(),worldPosition,getMaxRange(),level) :
                RunicEnergyPath.isRouteCorrect(PATH_TO_CONTAINERS.get(type),level));
    }

    public void tryConstructWays(RunicEnergyCost cost,int multiplier){
        List<BlockEntity> entities = findNearestRepeatersOrPylons(worldPosition, level);
        for (RunicEnergy.Type type : cost.getSetTypes()) {
            List<BlockPos> oldRoute = PATH_TO_CONTAINERS.get(type);

            float c = cost.get(type) * multiplier;
            double runicEnergy = getRunicEnergy(type);
            if (runicEnergy >= c){
                if (oldRoute != null){
                    BlockPos firstPos = oldRoute.get(1);
                    if (nullOrGiverPositionForClient.contains(firstPos)) {
                        nullOrGiverPositionForClient.remove(firstPos);
                        BlockState state = level.getBlockState(worldPosition);
                        this.setChanged();
                        this.level.sendBlockUpdated(worldPosition, state, state, 3);
                    }
                    if (!level.isClientSide && level.getBlockEntity(oldRoute.get(oldRoute.size()-1)) instanceof RuneEnergyPylonTile pylon){
                        ChunkPos pos = new ChunkPos(pylon.getPos());
                        ForgeChunkManager.forceChunk((ServerLevel)level, SolarForge.MOD_ID,
                                pylon.getPos(),pos.x,pos.z,false,true);
                    }
                    RunicEnergyPath.resetRepeaterConnections(PATH_TO_CONTAINERS.get(type), level);
                    PATH_TO_CONTAINERS.remove(type);
                }
                continue;
            }

            if (checkRoute(oldRoute,type)) continue;

            PATH_TO_CONTAINERS.remove(type);
            for (BlockEntity entity : entities) {
                if (entity instanceof BaseRepeaterTile tile && tile.getEnergyType() == type) {
                    List<BlockPos> route = new RunicEnergyPath(type, this.worldPosition).build(tile);
                    if (route != null) {
                        if (level.getBlockEntity(route.get(route.size()-1)) instanceof RuneEnergyPylonTile pylon) {
                            ChunkPos pos = new ChunkPos(pylon.getPos());
                            ForgeChunkManager.forceChunk((ServerLevel) level, SolarForge.MOD_ID,
                                    pylon.getPos(), pos.x, pos.z, true, true);
                        }
                        PATH_TO_CONTAINERS.put(type, route);
                    }
                } else if (entity instanceof RunicEnergyGiver container && container.getTypes().contains(type)) {

                    PATH_TO_CONTAINERS.put(type, List.of(this.worldPosition, container.getPos()));
                }
            }
        }

    }



    private List<BlockEntity> findNearestRepeatersOrPylons(BlockPos pos, Level world){
        List<BlockEntity> toReturn = new ArrayList<>();
        List<LevelChunk> chunks = Helpers.getSurroundingChunks5Radius(pos,world);
        for (RunicEnergy.Type type : RunicEnergy.Type.getAll()) {
            double minRange = getMaxRange()+1;
            BlockEntity tile = null;
            for (LevelChunk chunk : chunks) {
                for (BlockEntity entity : chunk.getBlockEntities().values()) {
                    if (entity instanceof BaseRepeaterTile repeater) {

                        if ((repeater.getEnergyType() == type) && !(tile instanceof RuneEnergyPylonTile)) {
                            if (FDMathHelper.canSee(repeater.getBlockPos(), pos, getMaxRange(), world)) {
                                double range = FDMathHelper.getDistanceBetween(repeater.getBlockPos(), pos);
                                if (range <= getMaxRange()) {
                                    if (range <= minRange) {
                                        minRange = range;
                                        tile = repeater;
                                    }
                                }
                            }
                        }
                    } else if (entity instanceof RunicEnergyGiver pylon) {
                        if (FDMathHelper.canSee(pylon.getPos(), pos, getMaxRange(), world)) {
                            if (pylon.getTypes() != null && pylon.getTypes().contains(type)) {
                                double range = FDMathHelper.getDistanceBetween(pylon.getPos(), pos);
                                if (range <= getMaxRange()) {
                                    if (range <= minRange) {
                                        minRange = range;
                                        tile = (BlockEntity) pylon;
                                    }
                                }
                            }
                        }

                    }
                }
            }
            if (tile != null) {
                toReturn.add(tile);
            }
        }
        return toReturn;
    }


    public float getRunicEnergy(RunicEnergy.Type type){
        return container.get(type);
    }

    private void saveRunicEnergy(CompoundTag tag){
        container.saveToTag(tag);
    }
    private void loadRunicEnergy(CompoundTag tag){
        container.loadFromTag(tag);
    }

    public void breakWay(RunicEnergy.Type type){
        List<BlockPos> path = PATH_TO_CONTAINERS.remove(type);
        if (path == null) return;
        BlockPos firstPos = path.get(1);
        if (nullOrGiverPositionForClient.contains(firstPos)) {
            nullOrGiverPositionForClient.remove(firstPos);
            BlockState state = level.getBlockState(worldPosition);
            this.setChanged();
            this.level.sendBlockUpdated(worldPosition, state, state, 3);
        }
        if (!level.isClientSide && level.getBlockEntity(path.get(path.size()-1)) instanceof RuneEnergyPylonTile pylon){
            ChunkPos pos = new ChunkPos(pylon.getPos());
            ForgeChunkManager.forceChunk((ServerLevel)level, SolarForge.MOD_ID,
                    pylon.getPos(),pos.x,pos.z,false,true);
        }
        RunicEnergyPath.resetRepeaterConnections(path, level);
    }

    public Map<RunicEnergy.Type,List<BlockPos>> getWays(){
        return PATH_TO_CONTAINERS;
    }

    public RunicEnergyContainer getRunicEnergyContainer() {
        return container;
    }

    public void setContainer(RunicEnergyContainer container) {
        this.container = container;
    }


    @Override
    public void onLoad() {
        super.onLoad();
        if (!level.isClientSide) {
            this.container.setMaximumEnergy((float) getRunicEnergyLimit());
        }
    }
}

