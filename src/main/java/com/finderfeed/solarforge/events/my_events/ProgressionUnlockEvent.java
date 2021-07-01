package com.finderfeed.solarforge.events.my_events;

import com.finderfeed.solarforge.solar_lexicon.achievements.Achievement;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.eventbus.api.Event;


/**
 * Event fires when a progression unlocks.
 */
public class ProgressionUnlockEvent extends Event {

    private final PlayerEntity player;
    private final Achievement ach;


    public ProgressionUnlockEvent(PlayerEntity player, Achievement ach){
        this.player = player;
        this.ach = ach;
    }

    public Achievement getProgression(){
        return ach;
    }

    public PlayerEntity getPlayer(){
        return player;
    }
}
