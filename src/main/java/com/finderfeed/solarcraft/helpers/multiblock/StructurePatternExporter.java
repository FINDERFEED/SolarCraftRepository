package com.finderfeed.solarcraft.helpers.multiblock;

import com.finderfeed.solarcraft.SolarCraft;
import net.minecraft.commands.arguments.blocks.BlockStateParser;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class StructurePatternExporter {

    private static final String blockAlphabet = "qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM1234567890-=+";

    private static Map<BlockState, Character> statesToChar = new HashMap<>();


    public static void export(Level world, BlockPos initPos,BlockPos endPos){
        if (initPos.getX() > endPos.getX() || initPos.getY() > endPos.getY() || initPos.getZ() > endPos.getZ()){
            SolarCraft.LOGGER.error("Error exporting structure");
            return;
        }

        statesToChar = new HashMap<>();
        int index = 0;


        String[][] pattern = new String[endPos.getY() - initPos.getY()+1][endPos.getZ() - initPos.getZ() + 1];
        int yi = 0;
        for (int y = initPos.getY(); y <= endPos.getY();y++){
            int zi = 0;

            for (int z = initPos.getZ(); z <= endPos.getZ();z++){
                StringBuilder builder = new StringBuilder();
                for (int x = initPos.getX(); x <= endPos.getX();x++){
                    BlockState state = world.getBlockState(new BlockPos(x,y,z));
                    if (state.getBlock() == Blocks.BEDROCK){
                        state = Blocks.AIR.defaultBlockState();
                    }
                    if (!statesToChar.containsKey(state)){
                        statesToChar.put(state,blockAlphabet.charAt(index));
                        index++;
                    }
                    builder.append(statesToChar.get(state));
                }

                pattern[yi][zi] = "\"" + builder + "\"";
                zi++;
            }
            yi++;
        }
        String s = Arrays.deepToString(pattern).replace('[','{').replace(']','}');

        System.out.println("new String[][]"+s);

        for (Map.Entry<BlockState,Character> entry : statesToChar.entrySet()){
            String ser = BlockStateParser.serialize(entry.getKey());
            System.out.println(".put('" + entry.getValue() + "',\"" + ser + "\")");
        }
    }




}
