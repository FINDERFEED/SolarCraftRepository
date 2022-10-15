package com.finderfeed.solarcraft.misc_things;

import com.finderfeed.solarcraft.helpers.Helpers;
import com.finderfeed.solarcraft.content.items.solar_lexicon.progressions.Progression;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.player.Player;


public interface IProgressionBlock {




    Block getUnlockedBlock();

    Block getLockedBlock();

    Progression getRequiredProgression();

    default BlockState getHarvestState(Player playerEntity, BlockState state){
        return Helpers.hasPlayerCompletedProgression(getRequiredProgression(),playerEntity) ? getUnlockedBlock().defaultBlockState():getLockedBlock().defaultBlockState();
    }


}
