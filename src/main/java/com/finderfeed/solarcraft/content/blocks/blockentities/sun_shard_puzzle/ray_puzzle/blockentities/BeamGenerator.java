package com.finderfeed.solarcraft.content.blocks.blockentities.sun_shard_puzzle.ray_puzzle.blockentities;

import com.finderfeed.solarcraft.content.blocks.blockentities.SolarcraftBlockEntity;
import com.finderfeed.solarcraft.content.blocks.blockentities.sun_shard_puzzle.ray_puzzle.BeamData;
import com.finderfeed.solarcraft.helpers.Helpers;
import com.finderfeed.solarcraft.registries.tile_entities.SolarcraftTileEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class BeamGenerator extends SolarcraftBlockEntity {

    private BeamData beamData;

    public BeamGenerator(BlockPos p_155229_, BlockState p_155230_) {
        super(SolarcraftTileEntityTypes.BEAM_GENERATOR.get(), p_155229_, p_155230_);
    }


    public static void tick(BeamGenerator tile){
//        if (tile.getBeamData() == null){
            tile.recalculateBeams();
//        }
    }


    public BeamData getBeamData() {
        return beamData;
    }

    public void recalculateBeams(){
        this.beamData = BeamData.build(level,worldPosition,level.getBlockState(this.getBlockPos())
                .getValue(BlockStateProperties.FACING),10);
    }

    @Override
    public AABB getRenderBoundingBox() {
        return Helpers.createAABBWithRadius(Helpers.getBlockCenter(getBlockPos()),30,30);
    }
}
