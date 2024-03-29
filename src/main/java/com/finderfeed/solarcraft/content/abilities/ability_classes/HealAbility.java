package com.finderfeed.solarcraft.content.abilities.ability_classes;

import com.finderfeed.solarcraft.content.abilities.AbilityHelper;
import com.finderfeed.solarcraft.content.items.runic_energy.RunicEnergyCost;
import com.finderfeed.solarcraft.misc_things.RunicEnergy;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;

public class HealAbility extends AbstractAbility{
    public HealAbility() {
        super("solar_heal",new RunicEnergyCost()
        .set(RunicEnergy.Type.ZETA,200)
        .set(RunicEnergy.Type.URBA,350)
        .set(RunicEnergy.Type.ARDO,400),15000);
    }

    @Override
    public void cast(ServerPlayer entity, ServerLevel world) {
        if (AbilityHelper.isAbilityUsable(entity,this,true)) {
            entity.heal(4);
            AbilityHelper.spendAbilityEnergy(entity,this);
        }

    }
}
