package com.finderfeed.solarcraft.events.my_events;

import com.finderfeed.solarcraft.content.abilities.ability_classes.AbstractAbility;
import net.minecraft.server.level.ServerPlayer;

import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.ICancellableEvent;



public class AbilityUseEvent extends Event implements ICancellableEvent {

    private ServerPlayer player;
    private AbstractAbility ability;

    public AbilityUseEvent(AbstractAbility ability, ServerPlayer player){
        this.ability = ability;
        this.player = player;
    }


    public ServerPlayer getPlayer() {
        return player;
    }

    public AbstractAbility getAbility() {
        return ability;
    }
}
