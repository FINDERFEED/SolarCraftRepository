package com.finderfeed.solarforge.magic_items.runic_network.repeater;

import com.finderfeed.solarforge.Helpers;
import com.finderfeed.solarforge.for_future_library.FinderfeedMathHelper;
import com.finderfeed.solarforge.misc_things.ParticlesList;
import com.finderfeed.solarforge.misc_things.RunicEnergy;
import com.finderfeed.solarforge.registries.blocks.BlocksRegistry;
import com.finderfeed.solarforge.registries.tile_entities.TileEntitiesRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//NEVER GONNA GIVE YOU UP
public class BaseRepeaterTile extends BlockEntity {

    public static double NULL = -1000000;

    //rune energy pylon position
    private BlockPos FINAL_POSITION;
    private RunicEnergy.Type ENERGY_TYPE;
    private BlockPos CONNECTED_TO;
    private List<BlockPos> ConnectedEnergyConsumers = new ArrayList<>();


    public BaseRepeaterTile( BlockPos p_155229_, BlockState p_155230_) {
        super(TileEntitiesRegistry.REPEATER.get(), p_155229_, p_155230_);
    }


    public double extractEnergy(double maxAmount,RunicEnergy.Type type){
        if (hasConnection()){
            if (level.getBlockEntity(getFinalPos()) instanceof IRunicEnergyContainer cont){
                if (FinderfeedMathHelper.canSee(cont.getPos(),worldPosition,getMaxRange(),level)) {
                    double flag = cont.extractEnergy(type, maxAmount);
                    return flag;
                }else{
                    return NULL;
                }
            }else{
                return NULL;
            }
        }else {
            if (CONNECTED_TO != null) {
                if (level.getBlockEntity(CONNECTED_TO) instanceof BaseRepeaterTile tile) {
                    if (FinderfeedMathHelper.canSeeTileEntity(this,tile,getMaxRange())) {
                        return tile.extractEnergy(maxAmount, type);
                    }else{
                        return NULL;
                    }
                } else {
                    return NULL;
                }
            } else {
                return NULL;
            }
        }
    }


    public static void tick(Level world,BlockPos pos,BlockState state,BaseRepeaterTile tile){
        Block block = world.getBlockState(pos.below()).getBlock();
        if (block == BlocksRegistry.ZETA_RUNE_BLOCK.get()){
            tile.setEnergyType(RunicEnergy.Type.ZETA);
        }else if (block == BlocksRegistry.URBA_RUNE_BLOCK.get()){
            tile.setEnergyType(RunicEnergy.Type.URBA);
        }else if (block == BlocksRegistry.KELDA_RUNE_BLOCK.get()){
            tile.setEnergyType(RunicEnergy.Type.KELDA);
        }else if (block == BlocksRegistry.FIRA_RUNE_BLOCK.get()){
            tile.setEnergyType(RunicEnergy.Type.FIRA);
        }else if (block == BlocksRegistry.ARDO_RUNE_BLOCK.get()){
            tile.setEnergyType(RunicEnergy.Type.ARDO);
        }else if (block == BlocksRegistry.TERA_RUNE_BLOCK.get()){
            tile.setEnergyType(RunicEnergy.Type.TERA);
        }else{
            tile.setEnergyType(null);
        }

        if (!world.isClientSide && (world.getGameTime() %20 == 1) ) {
            tile.checkConsumersValid();
            tile.setChanged();
            world.sendBlockUpdated(pos, state, state, 3);
        }
        if (world.isClientSide && (world.getGameTime() % 15 == 1) && (tile.getRepeaterConnection() != null)){
            tile.handleParticlesBetween(tile.getRepeaterConnection());

            tile.ConnectedEnergyConsumers.forEach(tile::handleParticlesBetween);
        }
    }

    private void handleParticlesBetween(BlockPos pos){
        Vec3 startPos = FinderfeedMathHelper.TileEntityThings.getTileEntityCenter(this);
        Vec3 endPos = FinderfeedMathHelper.TileEntityThings.getTileEntityCenter(pos);
        Vec3 vector = endPos.subtract(startPos);
        double length = vector.length();

        for (int i = 1; i <= Math.floor(length);i++){

            double rndX = level.random.nextDouble()*0.6-0.3;
            double rndY = level.random.nextDouble()*0.6-0.3;
            double rndZ = level.random.nextDouble()*0.6-0.3;

            Vec3 basePos = startPos.add(vector.normalize().multiply(i,i,i));

            level.addParticle(ParticlesList.SMALL_SOLAR_STRIKE_PARTICLE.get(),
                    basePos.x + rndX,
                    basePos.y + rndY,
                    basePos.z + rndZ,
                    rndX*0.01,rndY*0.01,rndZ*0.01);
        }
    }


    public double getMaxRange(){
        return 25;
    }


    public BlockPos getRepeaterConnection(){
        return CONNECTED_TO;
    }

    public void setRepeaterConnection(BlockPos pos){
        this.CONNECTED_TO = pos;
    }

    public void setFinalPos(BlockPos FINAL_POSITION) {
        this.FINAL_POSITION = FINAL_POSITION;
    }

    public BlockPos getFinalPos() {
        return FINAL_POSITION;
    }

    public void setEnergyType(RunicEnergy.Type type){
        this.ENERGY_TYPE = type;
    }

    public RunicEnergy.Type getEnergyType(){
        return ENERGY_TYPE;
    }
    public boolean hasConnection(){
        return FINAL_POSITION != null;
    }



    public void resetRepeater(BlockPos consumer){
        this.FINAL_POSITION = null;
        if ((consumer != null) && ConnectedEnergyConsumers.contains(consumer)) {
            this.ConnectedEnergyConsumers.remove(consumer);
        }
        this.CONNECTED_TO = null;
    }


    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        CompoundTag tag = pkt.getTag();
        BlockPos connectedTo = NbtUtils.readBlockPos(tag);
        if (!Helpers.equalsBlockPos(connectedTo,Helpers.NULL_POS)) {
            this.setRepeaterConnection(connectedTo);
        }else{
            setRepeaterConnection(null);
        }
        List<BlockPos> list = new ArrayList<>();

        for (int i = 0; i < tag.getInt("lengthofconnections"); i++){
            list.add(new BlockPos(
                    tag.getInt("positionx"+i),
                    tag.getInt("positiony"+i),
                    tag.getInt("positionz"+i)
            ));
        }
        this.ConnectedEnergyConsumers = list;
        super.onDataPacket(net, pkt);
    }

    @Nullable
    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        CompoundTag nbt;
        if (getRepeaterConnection() != null){
            nbt = NbtUtils.writeBlockPos(getRepeaterConnection());
        }else{
            nbt = NbtUtils.writeBlockPos(Helpers.NULL_POS);
        }

        for (int i = 0; i < ConnectedEnergyConsumers.size(); i++){
            nbt.putInt("positionx"+i,ConnectedEnergyConsumers.get(i).getX());
            nbt.putInt("positiony"+i,ConnectedEnergyConsumers.get(i).getY());
            nbt.putInt("positionz"+i,ConnectedEnergyConsumers.get(i).getZ());
        }
        nbt.putInt("lengthofconnections",ConnectedEnergyConsumers.size());
        return new ClientboundBlockEntityDataPacket(worldPosition,3,nbt);
    }


    public void addConsumerConnection(BlockPos pos){
        if (!ConnectedEnergyConsumers.contains(pos)) {
            this.ConnectedEnergyConsumers.add(pos);
        }
    }

    public void removeConsumerConnection(int index){
        this.ConnectedEnergyConsumers.remove(index);
    }

    public void checkConsumersValid(){
        for (int i = 0; i < ConnectedEnergyConsumers.size();i++){
            if (level.getBlockEntity(ConnectedEnergyConsumers.get(i)) instanceof IRunicEnergyReciever reciever){
                if (!reciever.requiresEnergy()){
                    removeConsumerConnection(i);
                }
            }else{
                removeConsumerConnection(i);
            }
        }
    }

    public List<BlockPos> getConnectedEnergyConsumers() {
        return ConnectedEnergyConsumers;
    }

    @Override
    public AABB getRenderBoundingBox() {
        return new AABB(worldPosition.offset(-16,-16,-16),worldPosition.offset(16,16,16));
    }
}
