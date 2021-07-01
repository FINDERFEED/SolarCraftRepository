package com.finderfeed.solarforge.structures.blocks.tile_entities;

import com.finderfeed.solarforge.misc_things.AbstractStructureBlockentity;
import com.finderfeed.solarforge.registries.tile_entities.TileEntitiesRegistry;
import com.finderfeed.solarforge.solar_lexicon.achievements.Achievement;
import net.minecraft.util.math.AxisAlignedBB;

public class ColdStarInfuserTile extends AbstractStructureBlockentity {
    public ColdStarInfuserTile() {
        super(TileEntitiesRegistry.COLD_STAR_INFUSER.get(), Achievement.FIND_INFUSER_DUNGEON,new AxisAlignedBB(-5,-1,-5,5,1,5));
    }
}
