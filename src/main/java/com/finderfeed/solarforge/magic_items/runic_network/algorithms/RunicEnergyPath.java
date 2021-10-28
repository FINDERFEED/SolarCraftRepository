package com.finderfeed.solarforge.magic_items.runic_network.algorithms;

import com.finderfeed.solarforge.Helpers;
import com.finderfeed.solarforge.for_future_library.helpers.FinderfeedMathHelper;
import com.finderfeed.solarforge.magic_items.blocks.blockentities.RuneEnergyPylonTile;
import com.finderfeed.solarforge.magic_items.blocks.blockentities.runic_energy.RunicEnergyGiver;
import com.finderfeed.solarforge.magic_items.runic_network.repeater.BaseRepeaterTile;
import com.finderfeed.solarforge.misc_things.RunicEnergy;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.chunk.LevelChunk;
import org.lwjgl.system.CallbackI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RunicEnergyPath {

    private final RunicEnergy.Type type;
    private List<BlockPos> FINAL_POSITIONS = new ArrayList<>();


    public RunicEnergyPath(RunicEnergy.Type type){
        this.type = type;
    }



    private Map<BlockPos,List<BlockPos>> buildGraph(BaseRepeaterTile tile,List<BlockPos> visited,Map<BlockPos,List<BlockPos>> toReturn){
        List<BlockPos> positions = findConnectablePylonsAndEnergySources(tile,tile.getMaxRange());
        toReturn.put(tile.getBlockPos(),positions);
        visited.add(tile.getBlockPos());
        positions.forEach((pos)->{
            if (!visited.contains(pos) ){
                BaseRepeaterTile rep = getTile(pos,tile.getLevel());
                if (rep != null) {
                    buildGraph(rep, visited, toReturn);
                }
            }
        });

        return toReturn;
    }




    private BaseRepeaterTile getTile(BlockPos pos,Level w){
        BlockEntity be = w.getBlockEntity(pos);
        return  be instanceof BaseRepeaterTile ? (BaseRepeaterTile) be : null;
    }


    public List<BlockPos> findConnectablePylonsAndEnergySources(BaseRepeaterTile start, double range){
        Level world = start.getLevel();
        BlockPos mainpos = start.getBlockPos();
        List<LevelChunk> chunks = Helpers.getSurroundingChunks5Radius(mainpos,world);

        List<BlockPos> tiles = new ArrayList<>();
        for (LevelChunk chunk : chunks){
            chunk.getBlockEntities().forEach((position,tileentity)->{
                if ((tileentity instanceof BaseRepeaterTile repeater)
                        && !(Helpers.equalsBlockPos(start.getBlockPos(),position))
                        && (repeater.getEnergyType() == start.getEnergyType())){
                    if (FinderfeedMathHelper.canSeeTileEntity(start,repeater,range)){
                        tiles.add(tileentity.getBlockPos());
                    }
                }else if ((tileentity instanceof RunicEnergyGiver giver) &&
                        (giver.getTypes() != null) &&
                        (giver.getTypes().contains(start.getEnergyType())) &&
                        (FinderfeedMathHelper.getDistanceBetween(start.getBlockPos(),giver.getPos()) <= range)){
//                    tiles.add(giver.getPos());
                    FINAL_POSITIONS.add(giver.getPos());
                }
            });
        }
        return tiles;
    }

    private boolean hasEndPoint(){
        return !FINAL_POSITIONS.isEmpty();
    }

    public void sortBestPylon(Map<BlockPos,List<BlockPos>> graph,Level w){
        FINAL_POSITIONS.sort((n1,n2)->{
            return (int)Math.round(gv(w,n1).getRunicEnergy(type)) - (int)Math.round(gv(w,n2).getRunicEnergy(type));
        });

    }

    private RunicEnergyGiver gv(Level w,BlockPos pos){
        return (RunicEnergyGiver) w.getBlockEntity(pos);
    }

}

class Node{

    public BlockPos pos;
    public double f;
    public double g;
    public double heuristic;
    private List<BlockPos> savedPath = new ArrayList<>();

    public Node(BlockPos pos,BlockPos finalPos,double g){
        this.pos = pos;
        this.heuristic = FinderfeedMathHelper.getDistanceBetween(pos,finalPos);
        this.g = g;
        this.f = heuristic+g;
    }

    public void setSavedPath(List<BlockPos> savedPath) {
        this.savedPath = savedPath;
    }

    public List<BlockPos> getSavedPath() {
        return savedPath;
    }

    public void addToPath(BlockPos pos){
        savedPath.add(pos);
    }
}
//                if ((tileentity instanceof BaseRepeaterTile repeater) && !Helpers.equalsBlockPos(tile.getBlockPos(),position) && (repeater.getEnergyType() == tile.getEnergyType())){
//                    if (FinderfeedMathHelper.canSeeTileEntity(repeater,tile,range)){
//                        tiles.add(repeater.getBlockPos());
//                    }
//                }else if (tileentity instanceof RunicEnergyGiver pylon){
//                    if ((pylon.getTypes() != null) && pylon.getTypes().contains(tile.getEnergyType()) && FinderfeedMathHelper.canSeeTileEntity(pylon.getPos(),tile.getBlockPos(),range,world)){
//                        if (tile.getFinalPos() == null){
//                            tile.setFinalPos(pylon.getBlockPos());
//                        }else{
//                            if (FinderfeedMathHelper.getDistanceBetween(pylon.getPos(),tile.getBlockPos()) < FinderfeedMathHelper.getDistanceBetween(tile.getBlockPos(),tile.getFinalPos())){
//                                tile.setFinalPos(pylon.getBlockPos());
//                            }
//                        }
//                    }
//                }