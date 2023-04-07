package com.finderfeed.solarcraft.content.blocks.blockentities.sun_shard_puzzle.ray_puzzle.blockentities;

import com.finderfeed.solarcraft.content.blocks.blockentities.SolarcraftBlockEntity;
import com.finderfeed.solarcraft.helpers.Helpers;
import com.finderfeed.solarcraft.registries.tile_entities.SolarcraftTileEntityTypes;
import com.mojang.math.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.nio.FloatBuffer;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BeamReflectorTile extends SolarcraftBlockEntity {

    private Set<Direction> directions;
    private BeamGenerator tile;

    public BeamReflectorTile(BlockPos p_155229_, BlockState p_155230_) {
        super(SolarcraftTileEntityTypes.BEAM_REFLECTOR.get(), p_155229_, p_155230_);
        directions = new HashSet<>(List.of(Direction.NORTH));
    }




    public void onUse(Direction face){
        Vec3i faceNormal = face.getNormal();
        HashSet<Direction> newDirs = new HashSet<>();
        for (Direction dir : this.directions){
            Vec3i normal = dir.getNormal();

            int a;
            Matrix3f m = new Matrix3f();
            if (Math.abs(a = faceNormal.getX()) == 1){
                m.load(FloatBuffer.wrap(new float[]{
                        1,0,0,
                        0,(float)(Math.cos(Math.PI/2*a)),- (float)(Math.sin(Math.PI/2*a)),
                        0,(float)(Math.sin(Math.PI/2*a)),(float)(Math.cos(Math.PI/2*a))})
                );
            }else if (Math.abs(a = faceNormal.getY()) == 1){
                m.load(FloatBuffer.wrap(new float[]{
                        (float)(Math.cos(Math.PI/2*a)),0,(float)(Math.sin(Math.PI/2*a)),
                        0,1,0,
                        - (float)(Math.sin(Math.PI/2*a)),0,(float)(Math.cos(Math.PI/2*a))})
                );
            }else if (Math.abs(a = faceNormal.getZ()) == 1){
                m.load(FloatBuffer.wrap(new float[]{
                        (float)(Math.cos(Math.PI/2*a)),- (float)(Math.sin(Math.PI/2*a)),0,
                        (float)(Math.sin(Math.PI/2*a)),(float)(Math.cos(Math.PI/2*a)),0,
                        0,0,1})
                );
            }
            Vector3f v = new Vector3f(normal.getX(),normal.getY(),normal.getZ());
            v.transform(m);
            newDirs.add(Direction.fromNormal((int)v.x(),(int)v.y(),(int)v.z()));
        }
        this.directions = newDirs;
        if (this.getGenerator() != null){
            this.getGenerator().recalculateBeams();
        }
        Helpers.updateTile(this);
    }

    public Set<Direction> getDirections() {
        return directions;
    }

    public void setGenerator(BeamGenerator tile) {
        this.tile = tile;
    }

    public BeamGenerator getGenerator() {
        return tile;
    }

    @Override
    public void handleUpdateTag(CompoundTag tag) {
        super.handleUpdateTag(tag);
        if (this.getGenerator() != null){
            this.getGenerator().recalculateBeams();
        }
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        super.onDataPacket(net, pkt);
        if (this.getGenerator() != null){
            this.getGenerator().recalculateBeams();
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        CompoundTag savedDirections = new CompoundTag();
        for (Direction dir : directions){
            savedDirections.putString(dir.getName(),"1");
        }
        tag.put("directions",savedDirections);
        super.saveAdditional(tag);
    }

    @Override
    public void load(CompoundTag tag) {
        CompoundTag dirTag = tag.getCompound("directions");
        HashSet<Direction> dirs = new HashSet<>();
        for (String s : dirTag.getAllKeys()){
            dirs.add(Direction.byName(s));
        }
        this.directions = dirs;
        super.load(tag);
    }
}
