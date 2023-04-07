package com.finderfeed.solarcraft.content.blocks.blockentities;

import com.finderfeed.solarcraft.content.blocks.blockentities.sun_shard_puzzle.ray_puzzle.BeamData;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;

public abstract class PuzzleBlockEntity extends SolarcraftBlockEntity{

    public List<BlockPos> destroyPositions = new ArrayList<>();
    private boolean solved = false;

    public PuzzleBlockEntity(BlockEntityType<?> p_155228_, BlockPos p_155229_, BlockState p_155230_) {
        super(p_155228_, p_155229_, p_155230_);
    }


    public void solve(boolean ignoreSolved){
        if (!solved || ignoreSolved) {
            this.solved = true;
            for (BlockPos offset : destroyPositions) {
                BlockPos pos = worldPosition.offset(offset);
                level.destroyBlock(pos, false);
            }
        }
    }


    public boolean isSolved() {
        return solved;
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        tag.putBoolean("solved",solved);
        CompoundTag positions = new CompoundTag();
        for (int i = 0; i < destroyPositions.size();i++){
            BlockPos bp = destroyPositions.get(i);
            CompoundTag pos = new CompoundTag();
            pos.putInt("x",bp.getX());
            pos.putInt("y",bp.getY());
            pos.putInt("z",bp.getZ());
            positions.put("pos" + i,pos);
        }
        tag.put("positions",positions);
        tag.putInt("destroyPositionsSize",destroyPositions.size());
        super.saveAdditional(tag);

    }


    @Override
    public void load(CompoundTag tag) {
        this.solved = tag.getBoolean("solved");
        int posCount = tag.getInt("destroyPositionsSize");
        List<BlockPos> destroyPositions = new ArrayList<>();
        CompoundTag positions = tag.getCompound("positions");
        for (int i = 0; i < posCount;i++){
            CompoundTag pos = positions.getCompound("pos" + i);
            destroyPositions.add(
                    new BlockPos(
                            pos.getInt("x"),
                            pos.getInt("y"),
                            pos.getInt("z")
                    )
            );
        }
        this.destroyPositions = destroyPositions;
        super.load(tag);
    }
}
