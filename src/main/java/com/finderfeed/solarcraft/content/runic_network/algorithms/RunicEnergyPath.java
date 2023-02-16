package com.finderfeed.solarcraft.content.runic_network.algorithms;

import com.finderfeed.solarcraft.helpers.Helpers;
import com.finderfeed.solarcraft.local_library.helpers.FDMathHelper;
import com.finderfeed.solarcraft.content.blocks.blockentities.runic_energy.RunicEnergyGiver;
import com.finderfeed.solarcraft.content.runic_network.repeater.BaseRepeaterTile;
import com.finderfeed.solarcraft.misc_things.RunicEnergy;
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.chunk.LevelChunk;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RunicEnergyPath {

    private final RunicEnergy.Type type;
    private List<PosPair> FINAL_POSITIONS = new ArrayList<>();
    private final BlockPos startingPos;
    private Object2ObjectMap<BlockEntity,Boolean> usedTiles = new Object2ObjectLinkedOpenHashMap<>();

    public RunicEnergyPath(RunicEnergy.Type type,BlockPos startingpos){
        this.type = type;
        this.startingPos = startingpos;
    }

    @Nullable
    public List<BlockPos> build(BaseRepeaterTile beggining){
        usedTiles.clear();
        Map<BlockPos,List<BlockPos>> graph = buildGraphOpt(beggining,new HashMap<>());
        if (hasEndPoint()){
            sortBestGiver(beggining.getLevel());
            return buildRouteAStar(graph,beggining.getBlockPos(),beggining.getLevel());
        }else{
            return null;
        }
    }



    private Map<BlockPos,List<BlockPos>> buildGraph(BaseRepeaterTile tile,List<BlockPos> visited,Map<BlockPos,List<BlockPos>> toReturn){
        List<BlockPos> positions = findConnectablePylonsAndEnergySources(tile);
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

    private Map<BlockPos,List<BlockPos>> buildGraphOpt(BaseRepeaterTile tile,Map<BlockPos,List<BlockPos>> toReturn){
        List<BlockEntity> positions = findConnectablePylonsAndEnergySourcesOpt(tile);
        toReturn.put(tile.getBlockPos(),positions.stream().map(BlockEntity::getBlockPos).collect(Collectors.toList()));
        positions.forEach((tileEntity)->{
            Boolean bool = usedTiles.get(tileEntity);
            if (bool == null || !bool){
                BaseRepeaterTile rep = (BaseRepeaterTile) tileEntity;
                if (bool == null){
                    usedTiles.put(tileEntity,true);
                }
                if (rep != null) {
                    buildGraphOpt(rep, toReturn);
                }

            }
        });

        return toReturn;
    }




    private static BaseRepeaterTile getTile(BlockPos pos,Level w){
        BlockEntity be = w.getBlockEntity(pos);
        return  be instanceof BaseRepeaterTile ? (BaseRepeaterTile) be : null;
    }

//3319
    private List<BlockPos> findConnectablePylonsAndEnergySources(BaseRepeaterTile start){
//        double startRange = start.getMaxRange();
        Level world = start.getLevel();
        BlockPos mainpos = start.getBlockPos();
        List<LevelChunk> chunks = Helpers.getSurroundingChunks5Radius(mainpos,world);

        List<BlockPos> tiles = new ArrayList<>();
        for (LevelChunk chunk : chunks){
            chunk.getBlockEntities().forEach((position,tileentity)->{
                if ((tileentity instanceof BaseRepeaterTile repeater) && this.testRepeater(start,position,repeater)){
//                    double range = Math.min(startRange,repeater.getMaxRange());
//                    if (FDMathHelper.canSeeTileEntity(start,repeater,range)){
                    tiles.add(tileentity.getBlockPos());
//                    }
                }else if ((tileentity instanceof RunicEnergyGiver giver) && this.testGiver(start,giver)){
//                    double range = Math.min(startRange,giver.getRange());
//                    if ((FDMathHelper.getDistanceBetween(start.getBlockPos(),giver.getPos()) <= range) &&
//                        (FDMathHelper.canSeeTileEntity(start.getBlockPos(),giver.getPos(),range,world))){
//                    tiles.add(giver.getPos());
                    FINAL_POSITIONS.add(new PosPair(start.getBlockPos(), giver.getPos()));
//                    }
                }
            });
        }
        return tiles;
    }


    private List<BlockEntity> findConnectablePylonsAndEnergySourcesOpt(BaseRepeaterTile start){
//        double startRange = start.getMaxRange();
        Level world = start.getLevel();
        BlockPos mainpos = start.getBlockPos();
        List<LevelChunk> chunks = Helpers.getSurroundingChunks5Radius(mainpos,world);

        List<BlockEntity> tiles = new ArrayList<>();
        for (LevelChunk chunk : chunks){
            chunk.getBlockEntities().forEach((position,tileentity)->{
                if ((tileentity instanceof BaseRepeaterTile repeater) && this.testRepeater(start,position,repeater)){
//                    double range = Math.min(startRange,repeater.getMaxRange());
//                    if (FDMathHelper.canSeeTileEntity(start,repeater,range)){
                    tiles.add(tileentity);
//                    }
                }else if ((tileentity instanceof RunicEnergyGiver giver) && this.testGiver(start,giver)){
//                    double range = Math.min(startRange,giver.getRange());
//                    if ((FDMathHelper.getDistanceBetween(start.getBlockPos(),giver.getPos()) <= range) &&
//                        (FDMathHelper.canSeeTileEntity(start.getBlockPos(),giver.getPos(),range,world))){
//                    tiles.add(giver.getPos());
                    FINAL_POSITIONS.add(new PosPair(start.getBlockPos(), giver.getPos()));
//                    }
                }
            });
        }
        return tiles;
    }

    private boolean testRepeater(BaseRepeaterTile start,BlockPos position,BaseRepeaterTile repeater){
        double range = Math.min(start.getMaxRange(),repeater.getMaxRange());
        return !(Helpers.equalsBlockPos(start.getBlockPos(),position))
                && (repeater.getAcceptedEnergyTypes().contains(type)) && FDMathHelper.canSeeTileEntity(start,repeater,range);
    }
    private boolean testGiver(BaseRepeaterTile start,RunicEnergyGiver giver){
        double range = Math.min(start.getMaxRange(),giver.getRange());

        return (giver.getTypes() != null) &&
                (giver.getTypes().contains(type))
                && (FDMathHelper.getDistanceBetween(start.getBlockPos(),giver.getPos()) <= range) &&
                (FDMathHelper.canSeeTileEntity(start.getBlockPos(),giver.getPos(),range,start.getLevel()));
    }



    private boolean hasEndPoint(){
        return !FINAL_POSITIONS.isEmpty();
    }

    private void sortBestGiver(Level w){
        FINAL_POSITIONS.sort((n1,n2)->{
            return (int)Math.round(gv(w,n2.getGiverPos()).getRunicEnergy(type)) - (int)Math.round(gv(w,n1.getGiverPos()).getRunicEnergy(type));
        });
        PosPair pos = FINAL_POSITIONS.get(0);
        FINAL_POSITIONS.clear();
        FINAL_POSITIONS.add(pos);
    }

    private RunicEnergyGiver gv(Level w,BlockPos pos){
        return (RunicEnergyGiver) w.getBlockEntity(pos);
    }


    private Node findLeastFNode(List<Node> nodes){
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

    private List<BlockPos> buildRouteAStar(Map<BlockPos,List<BlockPos>> pylons,BlockPos start,Level world){
        BlockPos finalPos = FINAL_POSITIONS.get(0).getRepeaterPos();

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
                        Node nd = new Node(nodes.get(i), finalPos, currentNode.g + FDMathHelper.getDistanceBetween(nodes.get(i), currentNode.pos));
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
            savedPath.add(FINAL_POSITIONS.get(0).getGiverPos());
            List<BlockPos> returnthis = new ArrayList<>();
            returnthis.add(startingPos);
            returnthis.addAll(savedPath);
            return returnthis;
        }else{
            return List.of(startingPos,finalPos,FINAL_POSITIONS.get(0).getGiverPos());
        }

    }

    public static boolean isRouteCorrect(List<BlockPos> route,Level world){
        for (int i = 0; i < route.size()-1;i++){
            if (i >= 1 ){
                if (!(world.getBlockEntity(route.get(i)) instanceof BaseRepeaterTile)){
                    return false;
                }
            }
            if (!FDMathHelper.canSeeTileEntity(route.get(i),route.get(i+1),10000,world)){
                return false;
            }
        }
        return true;
    }

    public static void resetRepeaterConnections(List<BlockPos> route,Level world){
        for (int i = 0;i < route.size()-1;i++){
            if (world.getBlockEntity(route.get(i)) instanceof BaseRepeaterTile repeater){
                if (i == route.size()-2){
                    if (repeater.getConnections().contains(route.get(route.size()-1))) {
                        repeater.removeConnection(route.get(route.size() - 1));
                        Helpers.updateTile(repeater);
                    }
                }
                if (repeater.getConnections().contains(route.get(i-1))) {
                    repeater.removeConnection(route.get(i - 1));
                    Helpers.updateTile(repeater);
                }
            }
        }
    }

    public static void setRepeaterConnections(List<BlockPos> route,Level world){
        for (int i = 0;i < route.size()-1;i++){
            if (world.getBlockEntity(route.get(i)) instanceof BaseRepeaterTile repeater){
                if (i == route.size()-2){
                    if (!repeater.getConnections().contains(route.get(route.size()-1))) {
                        repeater.addConnection(route.get(route.size() - 1));
                        Helpers.updateTile(repeater);
                    }
                }
                if (!repeater.getConnections().contains(route.get(i-1))) {
                    repeater.addConnection(route.get(i - 1));
                    Helpers.updateTile(repeater);
                }
            }
        }
    }


}

class PosPair{

    private final BlockPos pos1;
    private final BlockPos pos2;

    public PosPair(BlockPos pos1,BlockPos pos2){
        this.pos1 = pos1;
        this.pos2 = pos2;
    }

    public BlockPos getRepeaterPos() {
        return pos1;
    }

    public BlockPos getGiverPos() {
        return pos2;
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
        this.heuristic = FDMathHelper.getDistanceBetween(pos,finalPos);
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