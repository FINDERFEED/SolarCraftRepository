package com.finderfeed.solarcraft.content.items.solar_wand;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;

/**
 * Apply to your block / tile entity if you wish it to be able to be used with solar wand
 */
public interface IWandable {

    void onWandUse(BlockPos usePos,Player user);

}
