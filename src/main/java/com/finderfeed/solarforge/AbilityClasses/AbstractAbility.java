package com.finderfeed.solarforge.AbilityClasses;

import com.finderfeed.solarforge.capabilities.capability_mana.CapabilitySolarMana;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.world.server.ServerWorld;

public abstract class AbstractAbility {


    public int manacost;
    public String id;

    public AbstractAbility(String id,int manacost) {
        this.manacost=manacost;
        this.id = id;
    }

    public void cast(ServerPlayerEntity entity, ServerWorld world){
        if (!entity.isCreative()) {
            double mana = CapabilitySolarMana.getSolarMana(entity).orElseThrow(RuntimeException::new).getMana();
            CapabilitySolarMana.getSolarMana(entity).orElseThrow(RuntimeException::new).setMana(mana - manacost);
        }
    }


}
