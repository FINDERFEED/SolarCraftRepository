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
        LevelChunk[] chunks = Helpers.getSurroundingChunks(world,mainpos);
        List<BlockPos> tiles = new ArrayList<>();
        for (LevelChunk chunk : chunks){
            chunk.getBlockEntities().forEach((position,tileentity)->{
                if ((tileentity instanceof BaseRepeaterTile repeater) && !Helpers.equalsBlockPos(tile.getBlockPos(),position) && repeater.getEnergyType().equals(tile.getEnergyType())){
                    if (FinderfeedMathHelper.canSeeTileEntity(repeater,tile,range)){
                        tiles.add(repeater.getBlockPos());
                    }
                }else if (tileentity instanceof RuneEnergyPylonTile pylon){
                    if (pylon.getEnergyType().equals(tile.getEnergyType()) && FinderfeedMathHelper.canSeeTileEntity(pylon,tile,range)){
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

    //TODO:Find connection
    public static List<BlockPos> findConnectionAStar(Map<BlockPos,List<BlockPos>> pylons,BlockPos start,Level world){
        BlockPos finalPos = null;

        for (BlockPos pos : pylons.keySet()){
            if (getTile(pos,world).hasConnection()){
                finalPos = pos;
                break;
            }
        }
        List<BlockPos> nodes12 = pylons.keySet().stream().toList();
        int[] arr = new int[nodes12.size()];
        Arrays.fill(arr, 10000);
        arr[nodes12.indexOf(start)] = 0;
        List<BlockPos> alreadyVisited = new ArrayList<>();
        alreadyVisited.add(start);
        List<Node> hold = new ArrayList<>();
        List<Node> open = new ArrayList<>();
        Node currentNode = new Node(start,finalPos,0);
        currentNode.addToPath(start);

        while (true){
            List<BlockPos> nodes = pylons.get(currentNode.pos);
            for (int i = 0;i < nodes.size();i++){
                if (!alreadyVisited.contains(nodes.get(i))) {
                    alreadyVisited.add(nodes.get(i));
                    Node nd = new Node(nodes.get(i), finalPos, currentNode.g + FinderfeedMathHelper.getDistanceBetween(nodes.get(i), currentNode.pos));
                    nd.setSavedPath(currentNode.getSavedPath());
                    nd.addToPath(currentNode.pos);
                    open.add(new Node(nodes.get(i), finalPos, currentNode.g + FinderfeedMathHelper.getDistanceBetween(nodes.get(i), currentNode.pos)));
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
                    break;
                }
            }else{
                Node leastF = findLeastFNode(hold);
                hold.remove(leastF);
                currentNode = leastF;

                if (Helpers.equalsBlockPos(finalPos, currentNode.pos)) {
                    break;
                }
            }
        }
        return currentNode.getSavedPath();
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
