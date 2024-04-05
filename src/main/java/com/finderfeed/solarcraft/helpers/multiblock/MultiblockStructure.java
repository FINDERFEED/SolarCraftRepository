package com.finderfeed.solarcraft.helpers.multiblock;

import com.finderfeed.solarcraft.client.screens.PositionBlockStateTileEntity;
import com.finderfeed.solarcraft.helpers.Helpers;
import com.finderfeed.solarcraft.local_library.OwnedBlock;
import com.finderfeed.solarcraft.local_library.helpers.FDBlockStateParser;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.arguments.blocks.BlockStateParser;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MultiblockStructure {

    private final List<PositionBlockStateTileEntity> blocks = new ArrayList<>();
    private final BlockState[][][] states;
    private final String id;
    public final String[][] pattern;
    public final Map<Character,BlockState> stateMap;
    private final Map<BlockState, TagKey<Block>> tagMap;
    private BlockPos centerOffset;
    public BlockState mainBlock;


    private MultiblockStructure(String structId,String[][] pattern,char center,Map<Character, BlockState> stateMap,Map<BlockState,TagKey<Block>> tagMap){
        this.tagMap = tagMap;
        this.id = structId;
        this.states = new BlockState[pattern[0][0].length()][pattern.length][pattern[0].length];
        this.stateMap = stateMap;
        this.pattern = pattern;
        this.mainBlock = stateMap.get(center);
        this.prepare(structId,pattern,center,stateMap);
    }



    private void prepare(String structId,String[][] pattern,char center,Map<Character, BlockState> stateMap){

        BlockPos bcenter = null;
        for (int y = 0; y < pattern.length;y++){
            for (int z = 0; z < pattern[y].length;z++){
                String row = pattern[y][z];
                for (int x = 0; x < row.length();x++){
                    if (row.charAt(x) == center){
                        bcenter = new BlockPos(x,y,z);
                        break;
                    }
                }
                if (bcenter != null){
                    break;
                }
            }
            if (bcenter != null){
                break;
            }
        }
        if (bcenter == null){
            throw new IllegalStateException("No center block specified in structure: " + structId);
        }
        this.centerOffset = bcenter;

        for (int y = 0; y < pattern.length;y++){
            for (int z = 0; z < pattern[y].length;z++){
                String row = pattern[y][z];
                for (int x = 0; x < row.length();x++){
                    BlockState state = stateMap.get(row.charAt(x));
                    BlockPos position = new BlockPos(x,y,z).subtract(bcenter);
                    states[x][y][z] = state;
                    blocks.add(new PositionBlockStateTileEntity(Helpers.posToVec(position),state));
                }
            }
        }
    }

    /**
     * RELATIVE TO STRUCTURE 0,0,0
     */
    public BlockState getBlockAt(BlockPos pos){
        return states[pos.getX()][pos.getY()][pos.getZ()];
    }


    public String[][] getPattern() {
        return pattern;
    }

    public BlockState getBlockByCharacter(Character character){
        return stateMap.get(character);
    }

    public String getId() {
        return id;
    }

    public boolean check(Level world, BlockPos center, boolean ignoreOtherBlocks){
        for (PositionBlockStateTileEntity block : this.blocks){
            if (block.state.isAir() && ignoreOtherBlocks) continue;
            BlockPos pos = center.offset(Helpers.vecToPos(block.pos));
            BlockState test = world.getBlockState(pos);
            TagKey<Block> tag;
            if ((tag = tagMap.get(block.state)) == null) {
                if (test != block.state) {
                    return false;
                }
            }else{
                if (!test.is(tag)){
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Returns incorrect blockstates, if tag doesn't match, incorrect state object also contains this tag,
     * else - null.
     */
    public List<IncorrectState> checkWithInfo(Level world, BlockPos center, boolean ignoreOtherBlocks){
        List<IncorrectState> incorrectStates = new ArrayList<>();
        for (PositionBlockStateTileEntity block : this.blocks){
            if (block.state.isAir() && ignoreOtherBlocks) continue;
            BlockPos pos = center.offset(Helpers.vecToPos(block.pos));
            BlockState test = world.getBlockState(pos);
            TagKey<Block> tag;
            if ((tag = tagMap.get(block.state)) == null) {
                if (test != block.state) {
                    incorrectStates.add(new IncorrectState(pos,block.state,test,null));
                }
            }else{
                if (!test.is(tag)){
                    incorrectStates.add(new IncorrectState(pos,block.state,test,tag));
                }
            }
        }
        return incorrectStates;
    }

    public void placeInWorld(Player player,Level world, BlockPos p){
        for (PositionBlockStateTileEntity pos : blocks){
            BlockPos position = p.offset(Helpers.vecToPos(pos.pos)).offset(centerOffset);
            world.setBlockAndUpdate(position,pos.state);
            if (player != null && player.level.getBlockEntity(position) instanceof OwnedBlock bl){
                bl.setOwner(player.getUUID());
            }
        }
    }

    public List<PositionBlockStateTileEntity> getBlocks() {
        return blocks;
    }

    public BlockPos getCenterOffset() {
        return centerOffset;
    }

    public static class Builder{

        private Map<Character,BlockState> stateMap = new HashMap<>();
        private Map<BlockState,TagKey<Block>> tagCheck = new HashMap<>();
        private String id;
        private String[][] pattern;
        private char mainChar;

        public static Builder start(){
            return new Builder();
        }

        public Builder setMainChar(char mainChar) {
            this.mainChar = mainChar;
            return this;
        }

        public Builder setPattern(String[][] pattern) {
            this.pattern = pattern;
            return this;
        }

        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Builder put(char c, BlockState state){
            this.stateMap.put(c,state);
            return this;
        }

        public Builder put(char c, String state) {
            stateMap.put(c,  FDBlockStateParser.parseString(state));
            return this;
        }

        public Builder checkTag(Character c,TagKey<Block> blockTagKey){
            tagCheck.put(stateMap.get(c),blockTagKey);
            return this;
        }

        public MultiblockStructure build(){
            return new MultiblockStructure(id,pattern,mainChar,stateMap,tagCheck);
        }

    }

    public record IncorrectState(BlockPos atPos,BlockState correct,BlockState incorrect,TagKey<Block> tag){}
}
