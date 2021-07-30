package com.finderfeed.solarforge.magic_items.runic_network.algorithms;

import com.finderfeed.solarforge.Helpers;
import com.finderfeed.solarforge.for_future_library.FinderfeedMathHelper;
import com.finderfeed.solarforge.magic_items.blocks.blockentities.RuneEnergyPylonTile;
import com.finderfeed.solarforge.magic_items.runic_network.repeater.BaseRepeaterTile;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;
import org.apache.logging.log4j.core.tools.picocli.CommandLine;

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
    public static List<BlockPos> findConnection(Map<BlockPos,List<BlockPos>> pylons,BlockPos start,Level world){
        List<BlockPos> usedPositions = new ArrayList<>();
        Queue<BlockPos> queue = new ArrayDeque<>(pylons.size()*2);
        List<List<BlockPos>> paths = new ArrayList<>();
        queue.offer(start);
        usedPositions.add(start);
        while (!queue.isEmpty()){
            BlockPos pos = queue.peek();

        }
        return null;
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
