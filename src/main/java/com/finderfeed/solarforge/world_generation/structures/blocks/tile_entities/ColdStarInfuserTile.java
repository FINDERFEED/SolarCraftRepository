package com.finderfeed.solarforge.world_generation.structures.blocks.tile_entities;

import com.finderfeed.solarforge.misc_things.AbstractStructureBlockentity;
import com.finderfeed.solarforge.registries.tile_entities.TileEntitiesRegistry;
import com.finderfeed.solarforge.magic.items.solar_lexicon.achievements.Progression;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class ColdStarInfuserTile extends AbstractStructureBlockentity {
    public ColdStarInfuserTile(BlockPos pos, BlockState state) {
        super(TileEntitiesRegistry.COLD_STAR_INFUSER.get(),pos,state, Progression.FIND_INFUSER_DUNGEON,new AABB(-5,-1,-5,5,1,5));
    }
}
