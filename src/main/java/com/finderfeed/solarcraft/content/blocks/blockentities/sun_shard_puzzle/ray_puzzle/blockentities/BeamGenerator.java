package com.finderfeed.solarcraft.content.blocks.blockentities.sun_shard_puzzle.ray_puzzle.blockentities;

import com.finderfeed.solarcraft.content.blocks.blockentities.PuzzleBlockEntity;
import com.finderfeed.solarcraft.content.blocks.blockentities.sun_shard_puzzle.ray_puzzle.BeamData;
import com.finderfeed.solarcraft.helpers.Helpers;
import com.finderfeed.solarcraft.registries.blocks.SCBlocks;
import com.finderfeed.solarcraft.registries.tile_entities.SCTileEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.AABB;

public class BeamGenerator extends PuzzleBlockEntity {

    private BeamData beamData;
    public int targetsNeeded = 1;

    public BeamGenerator(BlockPos p_155229_, BlockState p_155230_) {
        super(SCTileEntities.BEAM_GENERATOR.get(), p_155229_, p_155230_);
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
            if (level.getBlockState(path.beamEnd()).is(SCBlocks.BEAM_INPUT.get())){
                ending++;
                if (ending == targetsNeeded){
                    this.solve(false);
                    break;
                }
            }
        }
    }

//    @Override
//    public AABB getRenderBoundingBox() {
//        return Helpers.createAABBWithRadius(Helpers.getBlockCenter(getBlockPos()),30,30);
//    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        tag.putInt("targets",targetsNeeded);
        super.saveAdditional(tag);
    }

    @Override
    public void load(CompoundTag tag) {
        targetsNeeded = tag.getInt("targets");
        super.load(tag);
    }
}
