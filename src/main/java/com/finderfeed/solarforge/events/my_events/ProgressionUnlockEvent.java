package com.finderfeed.solarforge.events.my_events;

import com.finderfeed.solarforge.magic_items.items.solar_lexicon.achievements.Achievement;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.eventbus.api.Event;


import Player;

/**
 * Event fires when a progression unlocks.
 */
public class ProgressionUnlockEvent extends Event {

    private final Player player;
    private final Achievement ach;


    public ProgressionUnlockEvent(Player player, Achievement ach){
        this.player = player;
        this.ach = ach;
    }

    public Achievement getProgression(){
        return ach;
    }

    public Player getPlayer(){
        return player;
    }
}
