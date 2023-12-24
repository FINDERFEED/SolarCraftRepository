package com.finderfeed.solarcraft.content.blocks.blockentities.runic_energy;

import com.finderfeed.solarcraft.SolarCraft;
import com.finderfeed.solarcraft.content.items.solar_lexicon.progressions.Progression;
import com.finderfeed.solarcraft.events.other_events.event_handler.ModEventHandler;
import com.finderfeed.solarcraft.helpers.Helpers;
import com.finderfeed.solarcraft.local_library.OwnedBlock;
import com.finderfeed.solarcraft.local_library.helpers.CompoundNBTHelper;
import com.finderfeed.solarcraft.local_library.helpers.FDMathHelper;
import com.finderfeed.solarcraft.content.blocks.blockentities.RuneEnergyPylonTile;
import com.finderfeed.solarcraft.content.blocks.blockentities.SolarcraftBlockEntity;
import com.finderfeed.solarcraft.content.items.runic_energy.RunicEnergyContainer;
import com.finderfeed.solarcraft.content.items.runic_energy.RunicEnergyCost;
import com.finderfeed.solarcraft.content.runic_network.algorithms.RunicEnergyPath;
import com.finderfeed.solarcraft.content.runic_network.repeater.BaseRepeaterTile;
import com.finderfeed.solarcraft.misc_things.RunicEnergy;
import com.finderfeed.solarcraft.packet_handler.SCPacketHandler;
import com.finderfeed.solarcraft.packet_handler.packets.UpdateRunicEnergyInContainerPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.neoforged.neoforge.common.world.chunk.ForcedChunkManager;
import net.neoforged.neoforge.common.world.chunk.TicketController;
import net.neoforged.neoforge.network.PacketDistributor;
import javax.annotation.Nullable;
import java.util.*;


public abstract class AbstractRunicEnergyContainer extends SolarcraftBlockEntity implements OwnedBlock {

    private int seekCooldown = 0;
    private RunicEnergyContainer container = new RunicEnergyContainer();
    private List<BlockPos> nullOrGiverPositionForClient = new ArrayList<>();
    private UUID owner;
    private Map<RunicEnergy.Type,List<BlockPos>> PATH_TO_CONTAINERS = new HashMap<>();

    public AbstractRunicEnergyContainer(BlockEntityType<?> p_155228_, BlockPos p_155229_, BlockState p_155230_) {
        super(p_155228_, p_155229_, p_155230_);
    }

    @Override
    public void saveAdditional(CompoundTag tag) {
        saveRunicEnergy(tag);
        if (getOwner() != null) {
            tag.putUUID("tileowner", getOwner());
        }
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

    public abstract float getREPerTickInput();
    public abstract float getRunicEnergyLimit();
    public abstract int getSeekCooldown();
    public abstract double getMaxRange();

    public void requestRunicEnergy(RunicEnergyCost costs,int multiplier){
        if (seekCooldown > getSeekCooldown()){
            tryConstructWays(costs,multiplier);
            seekCooldown = 0;
        }else{
            seekCooldown++;
        }


        for (RunicEnergy.Type type : costs.getSetTypes()){


            if (!PATH_TO_CONTAINERS.containsKey(type)) continue;
            float cost = costs.get(type);
            double multiplied = cost * multiplier;
            double runicEnergy = getRunicEnergy(type);
            if (multiplied >= runicEnergy + getREPerTickInput()) {
                requestSpecificEnergyNew(type, getREPerTickInput());
            } else if ((multiplied > runicEnergy) && (multiplied < runicEnergy + getREPerTickInput())) {
                double request = multiplied - runicEnergy;
                requestSpecificEnergyNew(type, request);
            } else {
//                List<BlockPos> path = PATH_TO_CONTAINERS.remove(type);
//                BlockPos firstPos = path.get(1);
//                if (nullOrGiverPositionForClient.contains(firstPos)) {
//                    nullOrGiverPositionForClient.remove(firstPos);
//                    BlockState state = level.getBlockState(worldPosition);
//                    this.setChanged();
//                    this.level.sendBlockUpdated(worldPosition, state, state, 3);
//                }
//                if (!level.isClientSide && level.getBlockEntity(path.get(path.size()-1)) instanceof RuneEnergyPylonTile pylon){
//                    ChunkPos pos = new ChunkPos(pylon.getPos());
//                    ForgeChunkManager.forceChunk((ServerLevel)level, SolarCraft.MOD_ID,
//                            pylon.getPos(),pos.x,pos.z,false,true);
//                }
//                RunicEnergyPath.resetRepeaterConnections(path, level);
                this.breakWay(type);
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
                    this.removeFirstPos(firstPos);
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
                this.removeFirstPos(firstPos);
                RunicEnergyPath.resetRepeaterConnections(PATH_TO_CONTAINERS.get(type),level);
                PATH_TO_CONTAINERS.remove(type);
            }


    }

    private void removeFirstPos(BlockPos firstPos){
        if (nullOrGiverPositionForClient.contains(firstPos)) {
            nullOrGiverPositionForClient.remove(firstPos);
            BlockState state = level.getBlockState(worldPosition);
            this.setChanged();
            this.level.sendBlockUpdated(worldPosition, state, state, 3);
        }
    }


    public void spendEnergy(RunicEnergyCost costs,int multiplier){
        for (RunicEnergy.Type type : costs.getSetTypes()){
            this.giveEnergy(type,-costs.get(type)*multiplier);
        }
    }

    public abstract boolean shouldFunction();

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
        if (this.saveAndLoadEverything()){
            this.saveAdditional(cmp);
        }
        return ClientboundBlockEntityDataPacket.create(this,(tile)->{return cmp;});
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        nullOrGiverPositionForClient = CompoundNBTHelper.getBlockPosList("posclient",pkt.getTag());
        if (this.saveAndLoadEverything()){
            this.load(pkt.getTag());
        }
    }

    public void giveEnergy(RunicEnergy.Type type, double amount){
        setEnergy(type,(float)amount + container.get(type));
    }

    public void setEnergy(RunicEnergy.Type type,float amount){
        container.set(type,Math.min((float)getRunicEnergyLimit(), Math.max(0,amount)));
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
        List<BlockEntity> entities = findNearestRepeatersOrPylons(worldPosition, (ServerLevel) level);
        for (RunicEnergy.Type type : cost.getSetTypes()) {
            List<BlockPos> oldRoute = PATH_TO_CONTAINERS.get(type);

            float c = cost.get(type) * multiplier;
            double runicEnergy = getRunicEnergy(type);
            if (runicEnergy >= c){
//                if (oldRoute != null){
//                    BlockPos firstPos = oldRoute.get(1);
//                    this.removeFirstPos(firstPos);
//                    if (!level.isClientSide && level.getBlockEntity(oldRoute.get(oldRoute.size()-1)) instanceof RuneEnergyPylonTile pylon){
//                        ChunkPos pos = new ChunkPos(pylon.getPos());
//                        ForgeChunkManager.forceChunk((ServerLevel)level, SolarCraft.MOD_ID,
//                                pylon.getPos(),pos.x,pos.z,false,true);
//                    }
//                    RunicEnergyPath.resetRepeaterConnections(PATH_TO_CONTAINERS.get(type), level);
//                    PATH_TO_CONTAINERS.remove(type);
//                }
                this.breakWay(type);
                continue;
            }

            if (checkRoute(oldRoute,type)) continue;
            if (oldRoute != null){
//                RunicEnergyPath.resetRepeaterConnections(oldRoute,level);
                this.breakWay(type);
            }
//            PATH_TO_CONTAINERS.remove(type);
            for (BlockEntity entity : entities) {
                this.createPath(type,entity);
            }
        }

    }

    private void createPath(RunicEnergy.Type type,BlockEntity entity){
        if (entity instanceof BaseRepeaterTile tile && tile.getAcceptedEnergyTypes().contains(type)) {
            List<BlockPos> route = new RunicEnergyPath(type, this.worldPosition).build(tile);
            if (route != null) {
                if (level.getBlockEntity(route.get(route.size()-1)) instanceof RuneEnergyPylonTile pylon) {
                    ChunkPos pos = new ChunkPos(pylon.getPos());

                    ModEventHandler.TICKET_CONTROLLER.forceChunk((ServerLevel) level,
                            pylon.getPos(), pos.x, pos.z, true, true);
                }
                PATH_TO_CONTAINERS.put(type, route);
            }
        } else if (entity instanceof RunicEnergyGiver container && container.getTypes().contains(type)) {
            PATH_TO_CONTAINERS.put(type, List.of(this.worldPosition, container.getPos()));
        }
    }



    private boolean checkOwner(MinecraftServer server){
        Player player = server.getPlayerList().getPlayer(this.getOwner());
        if (player != null){
            return Helpers.hasPlayerCompletedProgression(Progression.RUNIC_ENERGY_REPEATER, player);
        }else{
            return false;
        }
    }

    public List<BlockEntity> findNearestRepeatersOrPylons(BlockPos pos, ServerLevel world){
        List<BlockEntity> toReturn = new ArrayList<>();
        if (!checkOwner(world.getServer())){
            return toReturn;
        }
        List<LevelChunk> chunks = Helpers.getSurroundingChunks5Radius(pos,world);
        for (RunicEnergy.Type type : RunicEnergy.Type.getAll()) {
            double minRange = getMaxRange()+1;
            BlockEntity tile = null;
            for (LevelChunk chunk : chunks) {
                for (BlockEntity entity : chunk.getBlockEntities().values()) {
                    if (entity instanceof BaseRepeaterTile repeater) {

                        if ((repeater.getAcceptedEnergyTypes().contains(type)) && !(tile instanceof RuneEnergyPylonTile)) {
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

    public void saveRunicEnergy(CompoundTag tag){
        container.saveToTag(tag);
    }
    public void loadRunicEnergy(CompoundTag tag){
        container.loadFromTag(tag);
    }

    public void updateRunicEnergy(float radiusOfUpdate){
        if (!level.isClientSide){
            SCPacketHandler.INSTANCE.send(PacketDistributor.NEAR.with(
                PacketDistributor.TargetPoint.p(
                        this.getBlockPos().getX(),
                        this.getBlockPos().getY(),
                        this.getBlockPos().getZ(),
                        radiusOfUpdate,
                        this.level.dimension()
                )
            ),new UpdateRunicEnergyInContainerPacket(this));
        }
    }

    public void breakWay(RunicEnergy.Type type){
        List<BlockPos> path = PATH_TO_CONTAINERS.remove(type);
        if (path == null) return;

        BlockPos firstPos = path.get(1);
        this.removeFirstPos(firstPos);
        if (!level.isClientSide && level.getBlockEntity(path.get(path.size()-1)) instanceof RuneEnergyPylonTile pylon){
            ChunkPos pos = new ChunkPos(pylon.getPos());
            ModEventHandler.TICKET_CONTROLLER.forceChunk((ServerLevel)level,
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

    public boolean saveAndLoadEverything(){
        return false;
    }

    public List<BlockPos> getNullOrGiverPositionForClient() {
        return nullOrGiverPositionForClient;
    }

    @Override
    public void onLoad() {
        super.onLoad();
        if (!level.isClientSide) {
            this.container.setMaximumEnergy((float) getRunicEnergyLimit());
            if (this.saveAndLoadEverything()){
                Helpers.updateTile(this);
            }
        }
    }
}

