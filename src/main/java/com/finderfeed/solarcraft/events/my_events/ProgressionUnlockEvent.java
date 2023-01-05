package com.finderfeed.solarcraft.events.my_events;

import com.finderfeed.solarcraft.content.items.solar_lexicon.progressions.Progression;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.eventbus.api.Event;



/**
 * This event fires when a player completes a progression.
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
