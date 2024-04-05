package com.finderfeed.solarcraft.content.world_generation.structures.blocks.tile_entities;

import com.finderfeed.solarcraft.misc_things.AbstractStructureBlockentity;
import com.finderfeed.solarcraft.registries.tile_entities.SCTileEntities;
import com.finderfeed.solarcraft.content.items.solar_lexicon.progressions.Progression;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class KeyLockStructureTile extends AbstractStructureBlockentity {

    public KeyLockStructureTile(BlockPos pos, BlockState state) {
        super(SCTileEntities.KEY_LOCK_TILE.get(),pos,state, Progression.FIND_KEY_LOCK_DUNGEON, new AABB(-15,-1,-15,15,1,15));
    }

}
