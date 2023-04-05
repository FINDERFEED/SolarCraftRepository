package com.finderfeed.solarcraft.content.blocks.blockentities.sun_shard_puzzle.ray_puzzle;

import com.finderfeed.solarcraft.content.blocks.blockentities.sun_shard_puzzle.ray_puzzle.blockentities.BeamReflectorTile;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BeamData {

    private List<BeamPath> paths;

    private BeamData(){}

    public static BeamData build(Level world, BlockPos initPos, Direction direction,int maxSearchLength){
        BeamData data = new BeamData();
        for (int i = 1; i <= maxSearchLength;i++){
            BlockPos newPos = initPos.offset(direction.getNormal().multiply(i));
            if (!world.getBlockState(newPos).isAir()) {
                if (world.getBlockEntity(newPos) instanceof BeamReflectorTile tile) {
                    List<BeamPath> paths = new ArrayList<>();
                    for (Direction d : tile.getDirections()) {
                        paths.addAll(searchBeamPaths(world, newPos, d, maxSearchLength, new HashSet<>()));
                    }
                    paths.add(new BeamPath(initPos, newPos));
                    data.paths = paths;
                    return data;
                }else{
                    BeamPath path = new BeamPath(initPos,newPos);
                    data.paths = new ArrayList<>(List.of(path));
                    return data;
                }
            }
        }
        BeamPath path = new BeamPath(initPos,initPos.offset(direction.getNormal().multiply(maxSearchLength)));
        data.paths = new ArrayList<>(List.of(path));
        return data;
    }

    public static List<BeamPath> searchBeamPaths(Level world, BlockPos initPos, Direction direction, int maxSearchLength, Set<BlockPos> usedPositions){
        List<BeamPath> beams = new ArrayList<>();
        for (int i = 1; i <= maxSearchLength;i++){
            BlockPos newPos = initPos.offset(direction.getNormal().multiply(i));
            if (!world.getBlockState(newPos).isAir()) {
                if (world.getBlockEntity(newPos) instanceof BeamReflectorTile tile) {
                    if (usedPositions.contains(newPos)) {
                        return beams;
                    }
                    usedPositions.add(newPos);
                    for (Direction d : tile.getDirections()) {
                        List<BeamPath> path = searchBeamPaths(world, newPos, d, maxSearchLength, usedPositions);
                        beams.addAll(path);
                    }
                }
                BeamPath beamPath = new BeamPath(initPos,newPos);
                beams.add(beamPath);
                return beams;
            }
        }
        BeamPath path = new BeamPath(initPos,initPos.offset(direction.getNormal().multiply(maxSearchLength)));
        beams.add(path);

        return beams;
    }



    public List<BeamPath> getPaths() {
        return paths;
    }


    public record BeamPath(BlockPos beamStart, BlockPos beamEnd){};
}
