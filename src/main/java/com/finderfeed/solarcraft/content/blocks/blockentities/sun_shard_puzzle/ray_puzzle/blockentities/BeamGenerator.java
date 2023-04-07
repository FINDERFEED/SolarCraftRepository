package com.finderfeed.solarcraft.content.blocks.blockentities.sun_shard_puzzle.ray_puzzle.blockentities;

import com.finderfeed.solarcraft.content.blocks.blockentities.SolarcraftBlockEntity;
import com.finderfeed.solarcraft.content.blocks.blockentities.sun_shard_puzzle.ray_puzzle.BeamData;
import com.finderfeed.solarcraft.helpers.Helpers;
import com.finderfeed.solarcraft.registries.tile_entities.SolarcraftTileEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.AABB;

import java.util.ArrayList;
import java.util.List;

public class BeamGenerator extends SolarcraftBlockEntity {

    private BeamData beamData;
    public int targetsNeeded = 1;
    public List<BlockPos> destroyPositions = new ArrayList<>();

    public BeamGenerator(BlockPos p_155229_, BlockState p_155230_) {
        super(SolarcraftTileEntityTypes.BEAM_GENERATOR.get(), p_155229_, p_155230_);
    }


    public static void tick(BeamGenerator tile){
        if (tile.getBeamData() == null){
            tile.recalculateBeams();
        }
    }


    public void onUse(){
        this.recalculateBeams();
    }

    public BeamData getBeamData() {
        return beamData;
    }

    public void recalculateBeams(){
        this.beamData = BeamData.build(this,level,worldPosition,level.getBlockState(this.getBlockPos())
                .getValue(BlockStateProperties.FACING),10);
        int ending = 0;
        for (BeamData.BeamPath path : beamData.getPaths()){
            if (level.getBlockState(path.beamEnd()).is(Blocks.REDSTONE_BLOCK)){
                ending++;//TODO:make your own block
                if (ending == targetsNeeded){
                    for (BlockPos offset : destroyPositions){
                        BlockPos pos = worldPosition.offset(offset);
                        level.destroyBlock(pos,false);
                    }
                    break;
                }
            }
        }
    }

    @Override
    public AABB getRenderBoundingBox() {
        return Helpers.createAABBWithRadius(Helpers.getBlockCenter(getBlockPos()),30,30);
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        tag.putInt("targets",targetsNeeded);
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
        super.saveAdditional(tag);//15
    }

    @Override
    public void load(CompoundTag tag) {
        targetsNeeded = tag.getInt("targets");
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
