package com.finderfeed.solarforge.magic.blocks.blockentities.runic_energy;

import com.finderfeed.solarforge.Helpers;
import com.finderfeed.solarforge.local_library.OwnedBlock;
import com.finderfeed.solarforge.local_library.helpers.CompoundNBTHelper;
import com.finderfeed.solarforge.local_library.helpers.FinderfeedMathHelper;
import com.finderfeed.solarforge.magic.blocks.blockentities.RuneEnergyPylonTile;
import com.finderfeed.solarforge.magic.blocks.blockentities.SolarcraftBlockEntity;
import com.finderfeed.solarforge.magic.items.runic_energy.RunicEnergyContainer;
import com.finderfeed.solarforge.magic.items.runic_energy.RunicEnergyCost;
import com.finderfeed.solarforge.magic.runic_network.algorithms.RunicEnergyPath;
import com.finderfeed.solarforge.magic.runic_network.repeater.BaseRepeaterTile;
import com.finderfeed.solarforge.misc_things.RunicEnergy;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;

import javax.annotation.Nullable;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;


public abstract class AbstractRunicEnergyContainer extends SolarcraftBlockEntity implements OwnedBlock {

    private int seekingCooldown = 0;

    private RunicEnergyContainer container = new RunicEnergyContainer();

    private float RUNE_ENERGY_ARDO = 0;
    private float RUNE_ENERGY_FIRA = 0;
    private float RUNE_ENERGY_TERA = 0;
    private float RUNE_ENERGY_URBA = 0;
    private float RUNE_ENERGY_KELDA = 0;
    private float RUNE_ENERGY_ZETA = 0;
    private float RUNE_ENERGY_GIRO = 0;
    private float RUNE_ENERGY_ULTIMA = 0;

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

    public void requestRunicEnergy(Map<RunicEnergy.Type,Double> costs,int multiplier){
        if (seekingCooldown > getSeekingCooldown()){
            tryConstructWays(costs.keySet());
        }else{
            seekingCooldown++;
        }


        for (Map.Entry<RunicEnergy.Type,Double> entry : costs.entrySet()){


            double cost  =entry.getValue();
            RunicEnergy.Type type = entry.getKey();
            if (!PATH_TO_CONTAINERS.containsKey(type)) continue;

            double multiplied = cost * multiplier;
            double runicEnergy = getRunicEnergy(type);
            if (multiplied >= runicEnergy + getMaxEnergyInput()) {
                requestSpecificEnergyNew(type, getMaxEnergyInput());
            } else if ((multiplied > runicEnergy) && (multiplied < runicEnergy + getMaxEnergyInput())) {
                double request = multiplied - getRunicEnergy(type);
                requestSpecificEnergyNew(type, request);
            } else {
                BlockPos firstPos = PATH_TO_CONTAINERS.get(type).get(1);
                if (nullOrGiverPositionForClient.contains(firstPos)) {
                    nullOrGiverPositionForClient.remove(firstPos);
                    BlockState state = level.getBlockState(worldPosition);
                    this.setChanged();
                    this.level.sendBlockUpdated(worldPosition, state, state, 3);
                }
                RunicEnergyPath.resetRepeaterConnections(PATH_TO_CONTAINERS.get(type), level);
                PATH_TO_CONTAINERS.remove(type);
            }
        }


    }

    public void requestRunicEnergy(RunicEnergyCost costs,int multiplier){
        if (seekingCooldown > getSeekingCooldown()){
            tryConstructWays(costs.getSetTypes());
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
                BlockPos firstPos = PATH_TO_CONTAINERS.get(type).get(1);
                if (nullOrGiverPositionForClient.contains(firstPos)) {
                    nullOrGiverPositionForClient.remove(firstPos);
                    BlockState state = level.getBlockState(worldPosition);
                    this.setChanged();
                    this.level.sendBlockUpdated(worldPosition, state, state, 3);
                }
                RunicEnergyPath.resetRepeaterConnections(PATH_TO_CONTAINERS.get(type), level);
                PATH_TO_CONTAINERS.remove(type);
            }
        }


    }



    public void requestSpecificEnergyNew(RunicEnergy.Type type,double amount){
            List<BlockPos> route = PATH_TO_CONTAINERS.get(type);
            RunicEnergyPath.setRepeaterConnections(PATH_TO_CONTAINERS.get(type),level);
            BlockPos firstPos = PATH_TO_CONTAINERS.get(type).get(1);
            BlockEntity first = level.getBlockEntity(firstPos);
            if (first instanceof RunicEnergyGiver container){
                if (FinderfeedMathHelper.canSee(firstPos,worldPosition,getMaxRange(),level)) {
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


    public void requestSpecificEnergy(RunicEnergy.Type type,double amount){
        if (PATH_TO_CONTAINERS.containsKey(type) && PATH_TO_CONTAINERS.get(type) != null){
            List<BlockPos> route = PATH_TO_CONTAINERS.get(type);
            RunicEnergyPath.setRepeaterConnections(PATH_TO_CONTAINERS.get(type),level);
            BlockPos firstPos = PATH_TO_CONTAINERS.get(type).get(1);
            BlockEntity first = level.getBlockEntity(firstPos);
            if (first instanceof RunicEnergyGiver container){
                double flag = container.extractEnergy(type, amount);
                this.giveEnergy(type, flag);
            }else if (first instanceof BaseRepeaterTile repeater){

                if (RunicEnergyPath.isRouteCorrect(PATH_TO_CONTAINERS.get(type),level)){
                    if (level.getBlockEntity(route.get(route.size()-1)) instanceof RunicEnergyGiver container){
                        double flag = container.extractEnergy(type,amount);
                        this.giveEnergy(type,flag);
                    }else {
                        RunicEnergyPath.resetRepeaterConnections(PATH_TO_CONTAINERS.get(type),level);
                        PATH_TO_CONTAINERS.remove(type);
//                        constructWay(type);
                    }
                }else{
                    RunicEnergyPath.resetRepeaterConnections(PATH_TO_CONTAINERS.get(type),level);
                    PATH_TO_CONTAINERS.remove(type);
//                    constructWay(type);
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
//                constructWay(type);
            }

        }
//        else{
//
//            constructWay(type);
//        }
    }

    public void spendEnergy(Map<RunicEnergy.Type,Double> costs,int multiplier){
        costs.forEach((type,cost)->{
            this.giveEnergy(type,-cost*multiplier);
        });
    }

    public abstract boolean shouldFunction();

    public void onRemove(){
        PATH_TO_CONTAINERS.forEach((type,way)->{
            RunicEnergyPath.resetRepeaterConnections(PATH_TO_CONTAINERS.get(type),level);
        });
    }

    public void resetAllRepeaters(){
        PATH_TO_CONTAINERS.forEach((type,way)->{
            RunicEnergyPath.resetRepeaterConnections(PATH_TO_CONTAINERS.get(type),level);
        });
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
//        switch (type){
//            case ARDO -> RUNE_ENERGY_ARDO+=amount;
//            case FIRA -> RUNE_ENERGY_FIRA+=amount;
//            case TERA -> RUNE_ENERGY_TERA+=amount;
//            case KELDA -> RUNE_ENERGY_KELDA+=amount;
//            case URBA -> RUNE_ENERGY_URBA+=amount;
//            case ZETA -> RUNE_ENERGY_ZETA+=amount;
//            case ULTIMA -> RUNE_ENERGY_ULTIMA+=amount;
//            case GIRO -> RUNE_ENERGY_GIRO+=amount;
//        }
        container.set(type,container.get(type)+(float)amount);
    }

    protected boolean isEnough(RunicEnergy.Type type, RunicEnergyCost cost, int multiplier){
//        boolean a = false;
//        switch (type){
//            case ARDO -> a =  RUNE_ENERGY_ARDO >= costs.get(RunicEnergy.Type.ARDO)*multiplier;
//            case FIRA-> a =  RUNE_ENERGY_FIRA >= costs.get(RunicEnergy.Type.FIRA)*multiplier;
//            case TERA-> a =  RUNE_ENERGY_TERA >= costs.get(RunicEnergy.Type.TERA)*multiplier;
//            case URBA-> a =  RUNE_ENERGY_URBA >= costs.get(RunicEnergy.Type.URBA)*multiplier;
//            case ZETA-> a =  RUNE_ENERGY_ZETA >= costs.get(RunicEnergy.Type.ZETA)*multiplier;
//            case KELDA-> a =  RUNE_ENERGY_KELDA >= costs.get(RunicEnergy.Type.KELDA)*multiplier;
//            case ULTIMA-> a =  RUNE_ENERGY_ULTIMA >= costs.get(RunicEnergy.Type.ULTIMA)*multiplier;
//            case GIRO-> a =  RUNE_ENERGY_GIRO >= costs.get(RunicEnergy.Type.GIRO)*multiplier;
//        }
        return  container.get(type) >= cost.get(type)*multiplier;
    }

    public boolean hasEnoughRunicEnergy(RunicEnergyCost cost,int multiplier){
        for (RunicEnergy.Type type : RunicEnergy.Type.getAll()){
            float c = cost.get(type);
            if (c == 0) continue;
            if (c < getRunicEnergy(type)){
                return false;
            }
        }
        return true;
    }

    public boolean hasEnoughRunicEnergy(Map<RunicEnergy.Type,Double> costs, int multiplier){
        AtomicBoolean bool = new AtomicBoolean(true);
        costs.forEach((type,cost)->{
            if (getRunicEnergy(type) < cost*multiplier){
                bool.set(false);
            }
        });
        return bool.get();
    }



    private boolean checkRoute(List<BlockPos> route,RunicEnergy.Type type){
        return route != null && (level.getBlockEntity(route.get(1)) instanceof RunicEnergyGiver giver ?
                FinderfeedMathHelper.canSee(giver.getPos(),worldPosition,getMaxRange(),level) :
                RunicEnergyPath.isRouteCorrect(PATH_TO_CONTAINERS.get(type),level));
    }

    public void tryConstructWays(List<RunicEnergy.Type> types){
        List<BlockEntity> entities = findNearestRepeatersOrPylons(worldPosition, level);
        for (RunicEnergy.Type type : types) {
            List<BlockPos> oldRoute = PATH_TO_CONTAINERS.get(type);
            if (checkRoute(oldRoute,type)) continue;

            PATH_TO_CONTAINERS.remove(type);
            for (BlockEntity entity : entities) {
                if (entity instanceof BaseRepeaterTile tile && tile.getEnergyType() == type) {
                    List<BlockPos> route = new RunicEnergyPath(type, this.worldPosition).build(tile);
                    if (route != null) {
                        PATH_TO_CONTAINERS.put(type, route);
                    }
                } else if (entity instanceof RunicEnergyGiver container && container.getTypes().contains(type)) {

                    PATH_TO_CONTAINERS.put(type, List.of(this.worldPosition, container.getPos()));
                }
            }
        }

    }

    public void tryConstructWays(Set<RunicEnergy.Type> types){
        List<BlockEntity> entities = findNearestRepeatersOrPylons(worldPosition, level);
        for (RunicEnergy.Type type : types) {
            List<BlockPos> oldRoute = PATH_TO_CONTAINERS.get(type);
            if (checkRoute(oldRoute,type)) continue;

            PATH_TO_CONTAINERS.remove(type);
            for (BlockEntity entity : entities) {
                if (entity instanceof BaseRepeaterTile tile && tile.getEnergyType() == type) {
                    List<BlockPos> route = new RunicEnergyPath(type, this.worldPosition).build(tile);
                    if (route != null) {
                        PATH_TO_CONTAINERS.put(type, route);
                    }
                } else if (entity instanceof RunicEnergyGiver container && container.getTypes().contains(type)) {

                    PATH_TO_CONTAINERS.put(type, List.of(this.worldPosition, container.getPos()));
                }
            }
        }

    }

    public void constructWay(RunicEnergy.Type type){
        PATH_TO_CONTAINERS.remove(type);
        BlockEntity entity = findNearestRepeaterOrPylon(worldPosition, level, type);
        if (entity instanceof BaseRepeaterTile tile) {
            List<BlockPos> route = new RunicEnergyPath(type, this.worldPosition).build(tile);
            if (route != null) {
                PATH_TO_CONTAINERS.put(type, route);
            }
        } else if (entity instanceof RunicEnergyGiver container) {
            nullOrGiverPositionForClient.add(container.getPos());
            BlockState state = level.getBlockState(worldPosition);
            this.setChanged();
            this.level.sendBlockUpdated(worldPosition,state,state,3);
            PATH_TO_CONTAINERS.put(type, List.of(this.worldPosition, container.getPos()));
        }

    }



    private BlockEntity findNearestRepeaterOrPylon(BlockPos pos, Level world, RunicEnergy.Type type){
        List<LevelChunk> chunks = Helpers.getSurroundingChunks5Radius(pos,world);
        double minRange = getMaxRange()+1;
        BlockEntity tile = null;

        for (int i = 0; i < chunks.size();i++){
            List<BlockEntity> tiles = chunks.get(i).getBlockEntities().values().stream().toList();
            for (int g = 0; g < tiles.size();g++){
                if (tiles.get(g) instanceof BaseRepeaterTile repeater){

                    if ((repeater.getEnergyType() == type) && !(tile instanceof RuneEnergyPylonTile)) {
                        if (FinderfeedMathHelper.canSee(repeater.getBlockPos(),pos,getMaxRange(),world)) {
                            double range = FinderfeedMathHelper.getDistanceBetween(repeater.getBlockPos(), pos);
                            if (range <= getMaxRange()) {
                                if (range <= minRange) {
                                    minRange = range;
                                    tile = repeater;
                                }
                            }
                        }
                    }
                }
                else if (tiles.get(g) instanceof RunicEnergyGiver pylon){
                    if (FinderfeedMathHelper.canSee(pylon.getPos(),pos,getMaxRange(),world)) {
                        if (pylon.getTypes() != null && pylon.getTypes().contains(type)) {
                            double range = FinderfeedMathHelper.getDistanceBetween(pylon.getPos(), pos);
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
        return tile;
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
                            if (FinderfeedMathHelper.canSee(repeater.getBlockPos(), pos, getMaxRange(), world)) {
                                double range = FinderfeedMathHelper.getDistanceBetween(repeater.getBlockPos(), pos);
                                if (range <= getMaxRange()) {
                                    if (range <= minRange) {
                                        minRange = range;
                                        tile = repeater;
                                    }
                                }
                            }
                        }
                    } else if (entity instanceof RunicEnergyGiver pylon) {
                        if (FinderfeedMathHelper.canSee(pylon.getPos(), pos, getMaxRange(), world)) {
                            if (pylon.getTypes() != null && pylon.getTypes().contains(type)) {
                                double range = FinderfeedMathHelper.getDistanceBetween(pylon.getPos(), pos);
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
//        double toReturn = 0;
//        switch (type){
//            case ZETA -> toReturn = RUNE_ENERGY_ZETA;
//            case URBA -> toReturn = RUNE_ENERGY_URBA;
//            case KELDA -> toReturn = RUNE_ENERGY_KELDA;
//            case FIRA -> toReturn = RUNE_ENERGY_FIRA;
//            case ARDO -> toReturn = RUNE_ENERGY_ARDO;
//            case TERA -> toReturn = RUNE_ENERGY_TERA;
//            case GIRO -> toReturn = RUNE_ENERGY_GIRO;
//            case ULTIMA -> toReturn = RUNE_ENERGY_ULTIMA;
//
//        }

        return container.get(type);
    }

    private void saveRunicEnergy(CompoundTag tag){
//        tag.putFloat("ardo",RUNE_ENERGY_ARDO);
//        tag.putFloat("fira",RUNE_ENERGY_FIRA);
//        tag.putFloat("kelda",RUNE_ENERGY_KELDA);
//        tag.putFloat("urba",RUNE_ENERGY_URBA);
//        tag.putFloat("tera",RUNE_ENERGY_TERA);
//        tag.putFloat("zeta",RUNE_ENERGY_ZETA);
//        tag.putFloat("giro",RUNE_ENERGY_GIRO);
//        tag.putFloat("ultima",RUNE_ENERGY_ULTIMA);
        container.saveToTag(tag);
    }
    private void loadRunicEnergy(CompoundTag tag){
//        RUNE_ENERGY_ARDO  = tag.getFloat("ardo");
//        RUNE_ENERGY_FIRA = tag.getFloat("fira");
//        RUNE_ENERGY_KELDA = tag.getFloat("kelda");
//        RUNE_ENERGY_URBA = tag.getFloat("urba");
//        RUNE_ENERGY_TERA = tag.getFloat("tera");
//        RUNE_ENERGY_ZETA = tag.getFloat("zeta");
//        RUNE_ENERGY_GIRO = tag.getFloat("giro");
//        RUNE_ENERGY_ULTIMA = tag.getFloat("ultima");
        container.loadFromTag(tag);
    }

    public void breakWay(RunicEnergy.Type type){
        List<BlockPos> path = PATH_TO_CONTAINERS.remove(type);
        if (path == null) return;
        RunicEnergyPath.resetRepeaterConnections(path,level);
    }

    public Map<RunicEnergy.Type,List<BlockPos>> getWays(){
        return PATH_TO_CONTAINERS;
    }

}

