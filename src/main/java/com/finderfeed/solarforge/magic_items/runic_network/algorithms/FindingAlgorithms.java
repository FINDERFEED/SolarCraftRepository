package com.finderfeed.solarforge.magic_items.runic_network.algorithms;

import com.finderfeed.solarforge.Helpers;
import com.finderfeed.solarforge.for_future_library.FinderfeedMathHelper;
import com.finderfeed.solarforge.magic_items.blocks.blockentities.RuneEnergyPylonTile;
import com.finderfeed.solarforge.magic_items.runic_network.repeater.BaseRepeaterTile;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;
import org.apache.logging.log4j.core.tools.picocli.CommandLine;
import org.lwjgl.system.CallbackI;

import java.util.*;

public class FindingAlgorithms {

    /**
     * Finds nearest and available pylons, also sets pylon position in given tile if pylons were found.
     */
    public static List<BlockPos> findCanConnectPylons(BaseRepeaterTile tile,double range){
        Level world = tile.getLevel();
        BlockPos mainpos = tile.getBlockPos();
        List<LevelChunk> chunks = Helpers.getSurroundingChunks5Radius(mainpos,world);

        List<BlockPos> tiles = new ArrayList<>();
        for (LevelChunk chunk : chunks){
            chunk.getBlockEntities().forEach((position,tileentity)->{
                if ((tileentity instanceof BaseRepeaterTile repeater) && !Helpers.equalsBlockPos(tile.getBlockPos(),position) && (repeater.getEnergyType() == tile.getEnergyType())){
                    if (FinderfeedMathHelper.canSeeTileEntity(repeater,tile,range)){
                        repeater.setRepeaterConnection(null);
                        tiles.add(repeater.getBlockPos());
                    }
                }else if (tileentity instanceof RuneEnergyPylonTile pylon){
                    if ((pylon.getEnergyType() != null) && pylon.getEnergyType().equals(tile.getEnergyType()) && FinderfeedMathHelper.canSeeTileEntity(pylon,tile,range)){
                        if (tile.getFinalPos() == null){
                            tile.setFinalPos(pylon.getBlockPos());
                        }else{
                            if (FinderfeedMathHelper.getDistanceBetween(pylon.getBlockPos(),tile.getBlockPos()) < FinderfeedMathHelper.getDistanceBetween(tile.getBlockPos(),tile.getFinalPos())){
                                tile.setFinalPos(pylon.getBlockPos());
                            }
                        }
                    }
                }
            });
        }
        return tiles;
    }


    public static List<BlockPos> findConnectionAStar(Map<BlockPos,List<BlockPos>> pylons,BlockPos start,Level world){
        BlockPos finalPos = null;

        for (BlockPos pos : pylons.keySet()){
            if (getTile(pos,world).hasConnection()){
                finalPos = pos;
                break;
            }
        }

        List<BlockPos> alreadyVisited = new ArrayList<>();
        alreadyVisited.add(start);
        List<Node> hold = new ArrayList<>();
        List<Node> open = new ArrayList<>();
        Node currentNode = new Node(start,finalPos,0);

        if (!Helpers.equalsBlockPos(start,finalPos)) {

            while (true) {
                List<BlockPos> nodes = pylons.get(currentNode.pos);
                for (int i = 0; i < nodes.size(); i++) {
                    if (!alreadyVisited.contains(nodes.get(i))) {
                        alreadyVisited.add(nodes.get(i));
                        Node nd = new Node(nodes.get(i), finalPos, currentNode.g + FinderfeedMathHelper.getDistanceBetween(nodes.get(i), currentNode.pos));
                        nd.setSavedPath(new ArrayList<>(currentNode.getSavedPath()));
                        nd.addToPath(currentNode.pos);
                        open.add(nd);
                    }
                }
                if (!open.isEmpty()) {
                    Node leastF = findLeastFNode(open);
                    open.forEach((node) -> {
                        if (!node.equals(leastF)) {
                            hold.add(node);
                        }
                    });
                    open.clear();
                    currentNode = leastF;

                    if (Helpers.equalsBlockPos(finalPos, currentNode.pos)) {
                        currentNode.addToPath(finalPos);
                        break;
                    }
                } else {
                    Node leastF = findLeastFNode(hold);
                    hold.remove(leastF);
                    currentNode = leastF;

                    if (Helpers.equalsBlockPos(finalPos, currentNode.pos)) {
                        currentNode.addToPath(finalPos);
                        break;
                    }
                }
            }
            List<BlockPos> savedPath = currentNode.getSavedPath();
            savedPath.add(getTile(finalPos,world).getFinalPos());
            return savedPath;
        }else{
            return List.of(finalPos,getTile(finalPos,world).getFinalPos());
        }

    }

    private static Node findLeastFNode(List<Node> nodes){
        int minindex = -1;
        double minf = 10000000;
        for (int i = 0; i < nodes.size();i++){
            if (nodes.get(i).f < minf){
                minf = nodes.get(i).f;
                minindex = i;
            }
        }
        return nodes.get(minindex);
    }

    public static boolean hasEndPoint(Map<BlockPos,List<BlockPos>> graph,Level w){
        for (BlockPos pos : graph.keySet().stream().toList()){
            if (getTile(pos,w).hasConnection()){
                return true;
            }
        }
        return false;
    }

    public static void sortBestPylon(Map<BlockPos,List<BlockPos>> pylons,Level w){
        Map<Float,BlockPos> allConnectedPylons = new HashMap<>();
        List<BlockPos> positions = pylons.keySet().stream().toList();
        for (int i = 0;i < positions.size(); i++){
            BaseRepeaterTile tile = getTile(positions.get(i),w);
            if (tile.hasConnection()){
                allConnectedPylons.put(((RuneEnergyPylonTile)w.getBlockEntity(tile.getFinalPos())).getCurrentEnergy(),positions.get(i));
            }
        }

        List<Float> floats = allConnectedPylons.keySet().stream().toList();
        float max = getMaxFloat(floats);
        BlockPos best = allConnectedPylons.get(max);
        for (int i = 0;i < positions.size(); i++){
            if (!Helpers.equalsBlockPos(positions.get(i),best)){
                getTile(positions.get(i),w).setFinalPos(null);
            }
        }
    }

    private static BaseRepeaterTile getTile(BlockPos pos,Level w){
        return (BaseRepeaterTile) w.getBlockEntity(pos);
    }

    private static float getMaxFloat(List<Float> list){
        float max = -1;
        for (int i = 0;i < list.size(); i++){
                if (list.get(i) >= max){
                    max =list.get(i);
                }
        }
        return max;
    }


    public static Map<BlockPos,List<BlockPos>> findAllConnectedPylons(BaseRepeaterTile tile,List<BlockPos> visited,Map<BlockPos,List<BlockPos>> toReturn){
        List<BlockPos> positions = findCanConnectPylons(tile,tile.getMaxRange());
        toReturn.put(tile.getBlockPos(),positions);
        visited.add(tile.getBlockPos());
        positions.forEach((pos)->{
            if (!visited.contains(pos)){
                findAllConnectedPylons(getTile(pos,tile.getLevel()),visited,toReturn);
            }
        });

        return toReturn;
    }


    public static void setRepeatersConnections(List<BlockPos> positions ,Level w){
        for (int i = 0; i < positions.size()-1;i++){
            if (w.getBlockEntity(positions.get(i)) instanceof BaseRepeaterTile repeaterTile){
                repeaterTile.setRepeaterConnection(positions.get(i+1));
            }
        }

    }


    public static void resetRepeaters(List<BlockPos> positions,Level w,BlockPos consumer){
        positions.forEach((pos)->{
            if (w.getBlockEntity(pos) instanceof BaseRepeaterTile tile){
                tile.resetRepeater(consumer);
            }
        });
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
