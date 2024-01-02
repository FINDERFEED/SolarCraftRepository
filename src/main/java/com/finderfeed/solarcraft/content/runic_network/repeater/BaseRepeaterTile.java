package com.finderfeed.solarcraft.content.runic_network.repeater;

import com.finderfeed.solarcraft.helpers.Helpers;
import com.finderfeed.solarcraft.local_library.helpers.CompoundNBTHelper;
import com.finderfeed.solarcraft.local_library.helpers.FDMathHelper;
import com.finderfeed.solarcraft.client.particles.SCParticleTypes;
import com.finderfeed.solarcraft.misc_things.DebugTarget;
import com.finderfeed.solarcraft.misc_things.RunicEnergy;
import com.finderfeed.solarcraft.registries.tile_entities.SCTileEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.*;
import java.util.stream.Collectors;

//NEVER GONNA GIVE YOU UP
public class BaseRepeaterTile extends BlockEntity implements DebugTarget {


    private Set<RunicEnergy.Type> accepted_types = new HashSet<>();
    private List<BlockPos> connections = new ArrayList<>();


    public BaseRepeaterTile( BlockPos p_155229_, BlockState p_155230_) {
        super(SCTileEntities.RUNIC_ENERGY_REPEATER.get(), p_155229_, p_155230_);
    }





    public static void tick(Level world,BlockPos pos,BlockState state,BaseRepeaterTile tile){
//        if (!world.isClientSide && (world.getGameTime() %20 == 1) ) {
//            tile.setChanged();
//            world.sendBlockUpdated(pos, state, state, 3);
//        }
        if (world.isClientSide && (world.getGameTime() % 15 == 1)){
            tile.connections.forEach(tile::handleParticlesBetween);
        }
    }

    private void handleParticlesBetween(BlockPos pos){
        Vec3 startPos = FDMathHelper.TileEntityThings.getTileEntityCenter(this);
        Vec3 endPos = FDMathHelper.TileEntityThings.getTileEntityCenter(pos);
        Vec3 vector = endPos.subtract(startPos);
        double length = vector.length();

        for (int i = 1; i <= Math.floor(length);i++){

            double rndX = level.random.nextDouble()*0.6-0.3;
            double rndY = level.random.nextDouble()*0.6-0.3;
            double rndZ = level.random.nextDouble()*0.6-0.3;

            Vec3 basePos = startPos.add(vector.normalize().multiply(i,i,i));

            level.addParticle(SCParticleTypes.SMALL_SOLAR_STRIKE_PARTICLE.get(),
                    basePos.x + rndX,
                    basePos.y + rndY,
                    basePos.z + rndZ,
                    rndX*0.01,rndY*0.01,rndZ*0.01);
        }
    }


    public double getMaxRange(){
        return 25;
    }

    public List<BlockPos> getConnections(){
        return connections;
    }

    public void addAcceptedEnergyType(RunicEnergy.Type type){
        this.accepted_types.add(type);
    }

    public Set<RunicEnergy.Type> getAcceptedEnergyTypes(){
        return accepted_types;
    }

    public void setAcceptedRunicEnergyTypes(RunicEnergy.Type... type) {
        if (type != null) {
            this.accepted_types = new HashSet<>(Arrays.stream(type).toList());
        }else{
            this.accepted_types = new HashSet<>();
        }
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = new CompoundTag();
        CompoundNBTHelper.writeBlockPosList("connections", connections,tag);
        return tag;
    }

    @Override
    public void handleUpdateTag(CompoundTag tag) {
        this.connections = CompoundNBTHelper.getBlockPosList("connections",tag);
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        CompoundTag tag = pkt.getTag();
        this.connections = CompoundNBTHelper.getBlockPosList("connections",tag);
        super.onDataPacket(net, pkt);
    }

    @Nullable
    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        CompoundTag tag = new CompoundTag();
        CompoundNBTHelper.writeBlockPosList("connections", connections,tag);
        return Helpers.createTilePacket(this,tag);
    }


    public void addConnection(BlockPos pos){
        connections.add(pos);
    }

    public void removeConnection(BlockPos pos){
        connections.remove(pos);
    }



//    @Override
//    public AABB getRenderBoundingBox() {
//        return new AABB(worldPosition.offset(-16,-16,-16),worldPosition.offset(16,16,16));
//    }

    @Override
    public List<String> getDebugStrings() {
        return connections.stream().map(Vec3i::toString).collect(Collectors.toList());
    }
}
