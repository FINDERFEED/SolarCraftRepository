package com.finderfeed.solarcraft.content.abilities.ability_classes;

import com.finderfeed.solarcraft.content.items.runic_energy.RunicEnergyCost;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;

public abstract class AbstractAbility {


    public String id;
    public AbstractAbility(String id) {
        this.id = id;
    }

    public abstract void cast(ServerPlayer entity, ServerLevel world);

    public abstract RunicEnergyCost getCastCost();
    public abstract int getBuyCost();


    public String getId() {
        return id;
    }

}
