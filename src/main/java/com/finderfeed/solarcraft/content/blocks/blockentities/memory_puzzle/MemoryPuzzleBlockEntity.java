package com.finderfeed.solarcraft.content.blocks.blockentities.memory_puzzle;

import com.finderfeed.solarcraft.content.blocks.blockentities.SolarcraftBlockEntity;
import com.finderfeed.solarcraft.local_library.helpers.CompoundNBTHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MemoryPuzzleBlockEntity extends SolarcraftBlockEntity {
    private MemoryPuzzle puzzle;
    private List<UUID> used = new ArrayList<>();
    private boolean canOpenScreen = true;
    private int ticker = 0;

    public MemoryPuzzleBlockEntity(BlockEntityType<?> tetype, BlockPos pos, BlockState state) {
        super(tetype, pos, state);
    }


    public static void tick(MemoryPuzzleBlockEntity tile, Level level){
        if (!level.isClientSide){




        }
    }

    private void processCompletedPuzzle(){
        if (puzzle.isCompleted()){

        }else{
            canOpenScreen = true;
            ticker = 0;
        }
    }

    public void pushValue(Player player,int value){

        puzzle.solve(value,false);


        if (puzzle.isCompleted()){
            used.add(player.getUUID());
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        CompoundNBTHelper.saveUUIDList(tag,used,"uuids");
        if (puzzle != null){
            puzzle.serialize(tag,"puzzle");
        }
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        if (tag.contains("puzzle")){
            this.puzzle = MemoryPuzzle.deserialize(tag,"puzzle");
        }
        this.used = CompoundNBTHelper.loadUUIDList(tag,"uuids");
    }
}
