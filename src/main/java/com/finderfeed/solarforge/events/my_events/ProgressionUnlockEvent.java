package com.finderfeed.solarforge.events.my_events;

import com.finderfeed.solarforge.magic.items.solar_lexicon.progressions.Progression;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.eventbus.api.Event;



/**
 * Event fires when a progression unlocks.
 */
public class ProgressionUnlockEvent extends Event {

    private final Player player;
    private final Progression ach;


    public ProgressionUnlockEvent(Player player, Progression ach){
        this.player = player;
        this.ach = ach;
    }

    public Progression getProgression(){
        return ach;
    }

    public Player getPlayer(){
        return player;
    }
}
