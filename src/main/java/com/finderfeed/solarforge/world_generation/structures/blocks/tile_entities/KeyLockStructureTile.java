package com.finderfeed.solarforge.world_generation.structures.blocks.tile_entities;

import com.finderfeed.solarforge.misc_things.AbstractStructureBlockentity;
import com.finderfeed.solarforge.registries.tile_entities.TileEntitiesRegistry;
import com.finderfeed.solarforge.magic.items.solar_lexicon.achievements.Progression;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class KeyLockStructureTile extends AbstractStructureBlockentity {

    public KeyLockStructureTile(BlockPos pos, BlockState state) {
        super(TileEntitiesRegistry.KEY_LOCK_TILE.get(),pos,state, Progression.FIND_KEY_LOCK_DUNGEON, new AABB(-15,-1,-15,15,1,15));
    }

}
