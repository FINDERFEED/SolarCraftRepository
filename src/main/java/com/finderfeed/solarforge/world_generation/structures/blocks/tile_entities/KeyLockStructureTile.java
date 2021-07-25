package com.finderfeed.solarforge.world_generation.structures.blocks.tile_entities;

import com.finderfeed.solarforge.misc_things.AbstractStructureBlockentity;
import com.finderfeed.solarforge.registries.tile_entities.TileEntitiesRegistry;
import com.finderfeed.solarforge.magic_items.items.solar_lexicon.achievements.Achievement;
import net.minecraft.world.phys.AABB;

public class KeyLockStructureTile extends AbstractStructureBlockentity {

    public KeyLockStructureTile() {
        super(TileEntitiesRegistry.KEY_LOCK_TILE.get(), Achievement.FIND_KEY_LOCK_DUNGEON, new AABB(-15,-1,-15,15,1,15));
    }

}
