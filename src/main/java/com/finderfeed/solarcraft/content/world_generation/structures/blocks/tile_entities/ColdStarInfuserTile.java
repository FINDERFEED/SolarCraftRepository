package com.finderfeed.solarcraft.content.world_generation.structures.blocks.tile_entities;

import com.finderfeed.solarcraft.misc_things.AbstractStructureBlockentity;
import com.finderfeed.solarcraft.registries.tile_entities.SolarcraftTileEntityTypes;
import com.finderfeed.solarcraft.content.items.solar_lexicon.progressions.Progression;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class ColdStarInfuserTile extends AbstractStructureBlockentity {
    public ColdStarInfuserTile(BlockPos pos, BlockState state) {
        super(SolarcraftTileEntityTypes.COLD_STAR_INFUSER.get(),pos,state, Progression.FIND_INFUSER_DUNGEON,new AABB(-5,-1,-5,5,1,5));
    }
}
