package com.finderfeed.solarforge.misc_things;


import com.finderfeed.solarforge.magic_items.items.solar_lexicon.achievements.Progression;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;


import java.util.HashMap;
import java.util.Set;


public class Multiblock {


    public HashMap<Character, BlockState> blockMap;
    public String[][] struct;
    public Progression reqProgression;
    public BlockState mainBlock;
    public String name;
    public Multiblock(Multiblock.Constructor cons){
            this.blockMap = cons.blockMap;
            blockMap.put(' ', Blocks.AIR.defaultBlockState());
            this.struct = cons.structure;
            this.reqProgression = cons.ach;
            this.mainBlock = cons.mainBlock;
            this.name = cons.name;
    }

    public String getName() {
        return name;
    }

    public BlockState getMainBlock() {
        return mainBlock;
    }

    public Progression getReqAchievement() {
        return reqProgression;
    }

    public String[][] getStruct() {
        return struct;
    }

    public HashMap<Character, BlockState> getBlockMap() {
        return blockMap;
    }

    public Set<Character> getKeySet(){
        return blockMap.keySet();
    }

    public BlockState getBlockByCharacter(Character a){
        return blockMap.get(a);
    }
    public Character getCharacterByBlock(BlockState a){
        for (Character f : blockMap.keySet()){
            if (blockMap.get(f) == a){
                return f;
            }
        }
        return null;
    }


    public static class Constructor{
        public HashMap<Character, BlockState> blockMap = new HashMap<>();
        public String[][] structure;
        public Progression ach;
        public BlockState mainBlock;
        public String name = "";
        public Multiblock.Constructor addBlock(BlockState a,Character b){
            blockMap.put(b,a);
            return this;
        }

        public Multiblock.Constructor addStruct(String[][] a){
            structure = a;
            return this;
        }

        public Multiblock.Constructor addAchievement(Progression ach){
            this.ach = ach;
            return this;
        }

        public Multiblock.Constructor addMainBlock(BlockState ach){
            this.mainBlock = ach;
            return this;
        }
        public Multiblock.Constructor setStructName(String name){
            this.name = name;
            return this;
        }

    }



}
