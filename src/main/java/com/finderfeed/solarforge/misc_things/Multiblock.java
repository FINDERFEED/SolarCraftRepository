package com.finderfeed.solarforge.misc_things;


import com.finderfeed.solarforge.magic.items.solar_lexicon.progressions.Progression;

import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;


import java.util.HashMap;
import java.util.Set;


public class Multiblock {


    public HashMap<Character, StateAndTag> blockMap;
    public String[][] struct;
    public Progression reqProgression;
    public BlockState mainBlock;
    public String name;
    public Multiblock(Multiblock.Constructor cons){
            this.blockMap = cons.blockMap;
            blockMap.put(' ', new StateAndTag(Blocks.AIR.defaultBlockState(),null,false));
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

    public HashMap<Character, StateAndTag> getBlockMap() {
        return blockMap;
    }

    public Set<Character> getKeySet(){
        return blockMap.keySet();
    }

    public BlockState getBlockByCharacter(Character a){
        return blockMap.get(a).getState();
    }
    public StateAndTag getStateAndTag(Character a){
        return blockMap.get(a);
    }
    public Character getCharacterByBlock(BlockState a){
        for (Character f : blockMap.keySet()){
            if (blockMap.get(f).getState() == a){
                return f;
            }
        }
        return null;
    }




    public static class Constructor{
        public HashMap<Character, StateAndTag> blockMap = new HashMap<>();

        public String[][] structure;
        public Progression ach;
        public BlockState mainBlock;
        public String name = "";
        public Multiblock.Constructor addBlock(BlockState a,Character b,boolean ignoreFacing){
            if (blockMap.containsKey(b)){
                throw new RuntimeException("Duplicate character in structure: " + b);
            }
            blockMap.put(b,new StateAndTag(a,null,ignoreFacing));
            return this;
        }

        public Multiblock.Constructor addBlockAndTag(BlockState a, TagKey<Block> tag, Character b, boolean ignoreFacing){
            if (blockMap.containsKey(b)){
                throw new RuntimeException("Duplicate character in structure: " + b);
            }
            blockMap.put(b,new StateAndTag(a,tag,ignoreFacing));
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

        public Multiblock.Constructor extend(Multiblock structure){
            this.mainBlock = structure.mainBlock;
            this.blockMap = new HashMap<>(structure.getBlockMap());
            return this;
        }
    }



}
