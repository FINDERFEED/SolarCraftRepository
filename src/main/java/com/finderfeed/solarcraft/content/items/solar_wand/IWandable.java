package com.finderfeed.solarcraft.content.items.solar_wand;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;

public interface IWandable {

    void onWandUse(BlockPos usePos,Player user);

}
