package com.finderfeed.solarforge.magic_items.items.solar_lexicon.unlockables;

import com.finderfeed.solarforge.Helpers;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;

public class RunePattern {


    public static final String PATTERN_SAVE_ID = "player_rune_pattern";
    public static final int OPENED = 1000;


    private int[][] pattern;
    private int[] xyPositions;

    public RunePattern(){
        pattern = new int[6][5];
        xyPositions = new int[]{-1,-1,-1,-1,-1,-1};
    }

    public RunePattern(Player player){
        if (player.getPersistentData().get(PATTERN_SAVE_ID) == null){
            player.getPersistentData().put(PATTERN_SAVE_ID,new CompoundTag());
        }
        CompoundTag tag = player.getPersistentData().getCompound(PATTERN_SAVE_ID);

        int height = tag.getInt("patternHeight");
        if (height == 0){
            pattern = null;
        }
        int[] arr = tag.getIntArray("patternRow0");
        if (arr.length == 0){
            pattern = null;
        }else{
            pattern = new int[height][arr.length];
        }
        if (pattern != null) {
            pattern[0] = arr;

            for (int i = 1; i < height; i++) {
                pattern[i] = tag.getIntArray("patternRow" + i);
            }
            xyPositions = tag.getIntArray("xypos");

        }
    }



    public void generate(){
        int height = pattern.length;
        int width = pattern[0].length;

        for (int i = 0;i < height;i++){
            for (int g = 0;g < width;g++){
                pattern[i][g] = Helpers.RANDOM.nextInt(8);
            }
        }


        for (int i = 0;i < 6;i+=2){
            int rndX;
            int rndY;
            do{
                rndX = Helpers.RANDOM.nextInt(width);
                rndY = Helpers.RANDOM.nextInt(height);
                xyPositions[i] = rndX;
                xyPositions[i+1] = rndY;
            }while (containsXY(rndX,rndY,i));
        }
    }

    private boolean containsXY(int x, int y,int iteration){
        for (int i = 0;i < 6;i+=2){
            if (i != iteration) {
                if (xyPositions[i] == x && xyPositions[i + 1] == y) {
                    return true;
                }
            }
        }
        return false;
    }



    public int getRune(int x, int y){
        return pattern[y][x];
    }

    public void save(Player player){
        if (player.getPersistentData().get(PATTERN_SAVE_ID) == null){
            player.getPersistentData().put(PATTERN_SAVE_ID,new CompoundTag());
        }
        CompoundTag tag = player.getPersistentData().getCompound(PATTERN_SAVE_ID);
        tag.putInt("patternHeight",pattern.length);

        for (int height = 0;height < pattern.length;height++){
            tag.putIntArray("patternRow" + height,pattern[height]);
        }

        tag.putIntArray("xypos",xyPositions);
    }

    public void setOpened(int x, int y){
        pattern[y][x] = OPENED;
    }

    public boolean isPresent(){
        return pattern != null;
    }


    public boolean isCompleted(){
        return    pattern[xyPositions[1]][xyPositions[0]] == OPENED
                &&pattern[xyPositions[3]][xyPositions[2]] == OPENED
                &&pattern[xyPositions[5]][xyPositions[4]] == OPENED;
    }

    public int[][] getPattern() {
        return pattern;
    }

    public int[] getXyWinPositions() {
        return xyPositions;
    }

    public boolean isWinPosition(int x,int y){
        return containsXY(x,y,-1);
    }
}
