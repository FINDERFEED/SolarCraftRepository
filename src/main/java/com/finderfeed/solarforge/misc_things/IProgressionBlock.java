package com.finderfeed.solarforge.misc_things;

import com.finderfeed.solarforge.Helpers;
import com.finderfeed.solarforge.solar_lexicon.achievements.Achievement;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.Property;

public interface IProgressionBlock {

    Property<Boolean> UNLOCKED = BooleanProperty.create("unlocked");


    Block getUnlockedBlock();

    Block getLockedBlock();

    Achievement getRequiredProgression();

    default BlockState getHarvestState(PlayerEntity playerEntity, BlockState state){
        return Helpers.hasPlayerUnlocked(getRequiredProgression(),playerEntity) ? getUnlockedBlock().defaultBlockState():getLockedBlock().defaultBlockState();
    }


}
