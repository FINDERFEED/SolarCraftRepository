package com.finderfeed.solarforge.abilities.ability_classes;

import com.finderfeed.solarforge.abilities.AbilityHelper;
import com.finderfeed.solarforge.magic.items.runic_energy.RunicEnergyCost;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public abstract class ToggleableAbility extends AbstractAbility{

    private final String TOGGLE_STRING;

    public ToggleableAbility(String id, RunicEnergyCost constr, int buyCost) {
        super(id, constr, buyCost);
        this.TOGGLE_STRING = "solarcraft_ability_toggle_"+id;
    }


    @Override
    public void cast(ServerPlayer entity, ServerLevel world) {
        if (AbilityHelper.isAbilityBought(entity,this)) {
            this.toggle(entity);
        }
    }



    public void toggle(Player player){
        setToggled(player, !isToggled(player));
    }

    public void setToggled(Player player,boolean a){
        player.getPersistentData().putBoolean(TOGGLE_STRING,a);
        if (player instanceof ServerPlayer pl){
            AbilityHelper.sendTogglePacket(pl,this,a);
        }
    }

    public boolean isToggled(Player player){
        return player.getPersistentData().getBoolean(TOGGLE_STRING);
    }

}
