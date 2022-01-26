package com.finderfeed.solarforge.misc_things;

import com.finderfeed.solarforge.Helpers;
import com.finderfeed.solarforge.magic.items.solar_lexicon.achievements.Progression;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.player.Player;


public interface IProgressionBlock {




    Block getUnlockedBlock();

    Block getLockedBlock();

    Progression getRequiredProgression();

    default BlockState getHarvestState(Player playerEntity, BlockState state){
        return Helpers.hasPlayerUnlocked(getRequiredProgression(),playerEntity) ? getUnlockedBlock().defaultBlockState():getLockedBlock().defaultBlockState();
    }


}
