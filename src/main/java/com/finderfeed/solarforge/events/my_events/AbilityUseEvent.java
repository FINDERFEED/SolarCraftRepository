package com.finderfeed.solarforge.events.my_events;

import com.finderfeed.solarforge.SolarAbilities.AbilityClasses.AbstractAbility;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;

import javax.annotation.Nullable;


@Cancelable
public class AbilityUseEvent extends Event {

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
