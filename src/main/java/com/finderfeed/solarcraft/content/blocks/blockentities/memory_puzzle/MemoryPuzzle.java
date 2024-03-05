package com.finderfeed.solarcraft.content.blocks.blockentities.memory_puzzle;

import net.minecraft.nbt.CompoundTag;

import java.util.Random;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicBoolean;

public class MemoryPuzzle {
    private Stack<Integer> values;
    private int maxValue;
    private int stages;
    private int currentStage;

    private MemoryPuzzle(){

    }

    public MemoryPuzzle(MemoryPuzzle other){
        this.maxValue = other.maxValue;
        this.stages = other.stages;
        this.currentStage = other.currentStage;
        this.values = new Stack<>();
        for (int val : other.values){
            this.values.push(val);
        }
    }
    public MemoryPuzzle(int maxValue,int stages){
        this.maxValue = maxValue;
        this.stages = stages;
        this.currentStage = 0;
        this.values = new Stack<>();
    }


    public void initiatePuzzle(boolean first){
        Random random = new Random();
        values.clear();
        if (first) {
            currentStage = 0;
        }
        for (int i = 0; i <= currentStage;i++){
            values.push(random.nextInt(maxValue));
        }
    }

    public boolean solve(int value, boolean ignoreValue, AtomicBoolean stageCompleted){
        if (values.isEmpty()) return true;
        int p = values.pop();
        if (p == value || ignoreValue){
            if (values.isEmpty()){
                currentStage++;
                this.initiatePuzzle(false);
                stageCompleted.set(true);
            }
            return true;
        }else{
            return false;
        }
    }

    public boolean isCompleted(){
        return currentStage >= stages;
    }

    public void setStages(int stages) {
        this.stages = stages;
    }

    public Stack<Integer> getValues() {
        return values;
    }

    public void serialize(CompoundTag tag, String name){
        CompoundTag puzzle = new CompoundTag();
        int len = values.size();
        puzzle.putInt("length",len);
        for (int i = 0; i < values.size();i++){
            puzzle.putInt("value_" + i,values.get(i));
        }
        puzzle.putInt("maxStages",stages);
        puzzle.putInt("currentStage",currentStage);
        puzzle.putInt("maxValue",maxValue);
        puzzle.put(name,puzzle);
    }

    public static MemoryPuzzle deserialize(CompoundTag tag,String name){
        CompoundTag puzzle;
        if (name != null){
            puzzle = tag.getCompound(name);
        }else{
            puzzle = tag;
        }
        int len = puzzle.getInt("length");
        MemoryPuzzle p = new MemoryPuzzle();
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < len;i++){
            stack.push(puzzle.getInt("value_"+i));
        }
        int stages = puzzle.getInt("maxStages");
        int currentStage = puzzle.getInt("currentStage");
        int maxValue = puzzle.getInt("maxValue");
        p.values = stack;
        p.stages = stages;
        p.currentStage = currentStage;
        p.maxValue = maxValue;
        return p;
    }
}
