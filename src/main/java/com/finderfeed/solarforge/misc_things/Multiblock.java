package com.finderfeed.solarforge.misc_things;


import com.finderfeed.solarforge.solar_lexicon.achievements.Achievement;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;


import java.util.HashMap;
import java.util.Set;


public class Multiblock {


    public HashMap<Character,Block> blockMap;
    public String[][] struct;
    public Achievement reqAchievement;
    public Block mainBlock;
    public String name;
    public Multiblock(Multiblock.Constructor cons){
            this.blockMap = cons.blockMap;
            blockMap.put(' ', Blocks.AIR);
            this.struct = cons.structure;
            this.reqAchievement = cons.ach;
            this.mainBlock = cons.mainBlock;
            this.name = cons.name;
    }

    public String getName() {
        return name;
    }

    public Block getMainBlock() {
        return mainBlock;
    }

    public Achievement getReqAchievement() {
        return reqAchievement;
    }

    public String[][] getStruct() {
        return struct;
    }

    public HashMap<Character, Block> getBlockMap() {
        return blockMap;
    }

    public Set<Character> getKeySet(){
        return blockMap.keySet();
    }

    public Block getBlockByCharacter(Character a){
        return blockMap.get(a);
    }
    public Character getCharacterByBlock(Block a){
        for (Character f : blockMap.keySet()){
            if (blockMap.get(f) == a){
                return f;
            }
        }
        return null;
    }


    public static class Constructor{
        public HashMap<Character, Block> blockMap = new HashMap<>();
        public String[][] structure;
        public Achievement ach;
        public Block mainBlock;
        public String name = "";
        public Multiblock.Constructor addBlock(Block a,Character b){
            blockMap.put(b,a);
            return this;
        }

        public Multiblock.Constructor addStruct(String[][] a){
            structure = a;
            return this;
        }

        public Multiblock.Constructor addAchievement(Achievement ach){
            this.ach = ach;
            return this;
        }

        public Multiblock.Constructor addMainBlock(Block ach){
            this.mainBlock = ach;
            return this;
        }
        public Multiblock.Constructor setStructName(String name){
            this.name = name;
            return this;
        }

    }



}
