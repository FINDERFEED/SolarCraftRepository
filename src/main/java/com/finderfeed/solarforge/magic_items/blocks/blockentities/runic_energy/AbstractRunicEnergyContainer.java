package com.finderfeed.solarforge.magic_items.blocks.blockentities.runic_energy;

import com.finderfeed.solarforge.Helpers;
import com.finderfeed.solarforge.for_future_library.OwnedBlock;
import com.finderfeed.solarforge.for_future_library.helpers.CompoundNBTHelper;
import com.finderfeed.solarforge.for_future_library.helpers.FinderfeedMathHelper;
import com.finderfeed.solarforge.magic_items.blocks.blockentities.RuneEnergyPylonTile;
import com.finderfeed.solarforge.magic_items.blocks.blockentities.SolarcraftBlockEntity;
import com.finderfeed.solarforge.magic_items.runic_network.algorithms.RunicEnergyPath;
import com.finderfeed.solarforge.magic_items.runic_network.repeater.BaseRepeaterTile;
import com.finderfeed.solarforge.magic_items.runic_network.repeater.IRunicEnergyContainer;
import com.finderfeed.solarforge.misc_things.RunicEnergy;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import org.apache.logging.log4j.core.tools.picocli.CommandLine;

import javax.annotation.Nullable;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;


public abstract class AbstractRunicEnergyContainer extends SolarcraftBlockEntity implements OwnedBlock {

    private int seekingCooldown = 0;

    private double RUNE_ENERGY_ARDO = 0;
    private double RUNE_ENERGY_FIRA = 0;
    private double RUNE_ENERGY_TERA = 0;
    private double RUNE_ENERGY_URBA = 0;
    private double RUNE_ENERGY_KELDA = 0;
    private double RUNE_ENERGY_ZETA = 0;
    private double RUNE_ENERGY_GIRO = 0;
    private double RUNE_ENERGY_ULTIMA = 0;

    public List<BlockPos> nullOrGiverPositionForClient = new ArrayList<>();

//    private boolean NEEDS_ARDO = false;
//    private boolean NEEDS_KELDA = false;
//    private boolean NEEDS_TERA = false;
//    private boolean NEEDS_FIRA = false;
//    private boolean NEEDS_URBA = false;
//    private boolean NEEDS_ZETA = false;

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
        if (this.level != null){
            if (!this.level.isClientSide){
                this.setOwner(tag.getUUID("tileowner"));
            }
        }else{
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

    public void requestRunicEnergy(Map<RunicEnergy.Type,Double> costs,int multiplier){
        costs.forEach((type,cost)->{
            double multiplied = cost*multiplier;
            double runicEnergy = getRunicEnergy(type);
            if (multiplied >= runicEnergy + getMaxEnergyInput()){
                requestSpecificEnergy(type,getMaxEnergyInput());
            }else if ((multiplied > runicEnergy) && (multiplied < runicEnergy + getMaxEnergyInput())){
                double request = multiplied - getRunicEnergy(type);
                requestSpecificEnergy(type,request);
            }else {
                if (PATH_TO_CONTAINERS.containsKey(type)) {
                    RunicEnergyPath.resetRepeaterConnections(PATH_TO_CONTAINERS.get(type),level);
                    PATH_TO_CONTAINERS.remove(type);
                }
            }
        });
    }

    public void requestSpecificEnergy(RunicEnergy.Type type,double amount){
        if (PATH_TO_CONTAINERS.containsKey(type) && PATH_TO_CONTAINERS.get(type) != null){
            List<BlockPos> route = PATH_TO_CONTAINERS.get(type);
//            FindingAlgorithms.setRepeatersConnections(PATH_TO_CONTAINERS.get(type),level);
            RunicEnergyPath.setRepeaterConnections(PATH_TO_CONTAINERS.get(type),level);
            BlockEntity first = level.getBlockEntity(PATH_TO_CONTAINERS.get(type).get(1));
            if (first instanceof RunicEnergyGiver container){
                double flag = container.extractEnergy(type,amount);
                this.giveEnergy(type,flag);
            }else if (first instanceof BaseRepeaterTile repeater){

                if (RunicEnergyPath.isRouteCorrect(PATH_TO_CONTAINERS.get(type),level)){
                    if (level.getBlockEntity(route.get(route.size()-1)) instanceof RunicEnergyGiver container){
                        double flag = container.extractEnergy(type,amount);
                        this.giveEnergy(type,flag);
                    }else {
                        RunicEnergyPath.resetRepeaterConnections(PATH_TO_CONTAINERS.get(type),level);
                        constructWay(type);
                    }
                }else{
//                    FindingAlgorithms.resetRepeaters(PATH_TO_CONTAINERS.get(type),level,worldPosition);
                    RunicEnergyPath.resetRepeaterConnections(PATH_TO_CONTAINERS.get(type),level);
                    constructWay(type);
                }
            }else{
                if (first != null) {
                    if (nullOrGiverPositionForClient.contains(first.getBlockPos())) {
                        nullOrGiverPositionForClient.remove(first.getBlockPos());
                        BlockState state = level.getBlockState(worldPosition);
                        this.setChanged();
                        this.level.sendBlockUpdated(worldPosition, state, state, 3);
                    }
                }
                RunicEnergyPath.resetRepeaterConnections(PATH_TO_CONTAINERS.get(type),level);
                constructWay(type);
            }

        }else{

            constructWay(type);
        }
    }

    public abstract boolean shouldFunction();

    public void onRemove(){
        PATH_TO_CONTAINERS.forEach((type,way)->{
//            FindingAlgorithms.resetRepeaters(way,level,worldPosition);
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
        switch (type){
            case ARDO -> RUNE_ENERGY_ARDO+=amount;
            case FIRA -> RUNE_ENERGY_FIRA+=amount;
            case TERA -> RUNE_ENERGY_TERA+=amount;
            case KELDA -> RUNE_ENERGY_KELDA+=amount;
            case URBA -> RUNE_ENERGY_URBA=amount;
            case ZETA -> RUNE_ENERGY_ZETA+=amount;
            case ULTIMA -> RUNE_ENERGY_ULTIMA+=amount;
            case GIRO -> RUNE_ENERGY_GIRO+=amount;
        }
    }

    protected boolean isEnough(RunicEnergy.Type type, Map<RunicEnergy.Type, Double> costs, int multiplier){
        boolean a = false;
        switch (type){
            case ARDO -> a =  RUNE_ENERGY_ARDO >= costs.get(RunicEnergy.Type.ARDO)*multiplier;
            case FIRA-> a =  RUNE_ENERGY_FIRA >= costs.get(RunicEnergy.Type.FIRA)*multiplier;
            case TERA-> a =  RUNE_ENERGY_TERA >= costs.get(RunicEnergy.Type.TERA)*multiplier;
            case URBA-> a =  RUNE_ENERGY_URBA >= costs.get(RunicEnergy.Type.URBA)*multiplier;
            case ZETA-> a =  RUNE_ENERGY_ZETA >= costs.get(RunicEnergy.Type.ZETA)*multiplier;
            case KELDA-> a =  RUNE_ENERGY_KELDA >= costs.get(RunicEnergy.Type.KELDA)*multiplier;
            case ULTIMA-> a =  RUNE_ENERGY_ULTIMA >= costs.get(RunicEnergy.Type.ULTIMA)*multiplier;
            case GIRO-> a =  RUNE_ENERGY_GIRO >= costs.get(RunicEnergy.Type.GIRO)*multiplier;
        }
        return  a;
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



    private void saveRunicEnergy(CompoundTag tag){
        tag.putDouble("ardo",RUNE_ENERGY_ARDO);
        tag.putDouble("fira",RUNE_ENERGY_FIRA);
        tag.putDouble("kelda",RUNE_ENERGY_KELDA);
        tag.putDouble("urba",RUNE_ENERGY_URBA);
        tag.putDouble("tera",RUNE_ENERGY_TERA);
        tag.putDouble("zeta",RUNE_ENERGY_ZETA);
        tag.putDouble("giro",RUNE_ENERGY_GIRO);
        tag.putDouble("ultima",RUNE_ENERGY_ULTIMA);
    }
    private void loadRunicEnergy(CompoundTag tag){
        RUNE_ENERGY_ARDO  = tag.getDouble("ardo");
        RUNE_ENERGY_FIRA = tag.getDouble("fira");
        RUNE_ENERGY_KELDA = tag.getDouble("kelda");
        RUNE_ENERGY_URBA = tag.getDouble("urba");
        RUNE_ENERGY_TERA = tag.getDouble("tera");
        RUNE_ENERGY_ZETA = tag.getDouble("zeta");
        RUNE_ENERGY_GIRO = tag.getDouble("giro");
        RUNE_ENERGY_ULTIMA = tag.getDouble("ultima");
    }

    public void constructWay(RunicEnergy.Type type){
        PATH_TO_CONTAINERS.remove(type);
        if (seekingCooldown > getSeekingCooldown()) {
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
        }else{
            seekingCooldown++;
        }
    }

    public abstract double getMaxRange();

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


    public double getRunicEnergy(RunicEnergy.Type type){
        double toReturn = 0;
        switch (type){
            case ZETA -> toReturn = RUNE_ENERGY_ZETA;
            case URBA -> toReturn = RUNE_ENERGY_URBA;
            case KELDA -> toReturn = RUNE_ENERGY_KELDA;
            case FIRA -> toReturn = RUNE_ENERGY_FIRA;
            case ARDO -> toReturn = RUNE_ENERGY_ARDO;
            case TERA -> toReturn = RUNE_ENERGY_TERA;
            case GIRO -> toReturn = RUNE_ENERGY_GIRO;
            case ULTIMA -> toReturn = RUNE_ENERGY_ULTIMA;

        }
        return toReturn;
    }

    public Map<RunicEnergy.Type,List<BlockPos>> getWays(){
        return PATH_TO_CONTAINERS;
    }

}

