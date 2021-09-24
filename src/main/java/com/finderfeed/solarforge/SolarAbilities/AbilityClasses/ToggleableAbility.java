package com.finderfeed.solarforge.SolarAbilities.AbilityClasses;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;

public class ToggleableAbility extends AbstractAbility{

    private final String TOGGLE_STRING;

    public ToggleableAbility(String id, int manacost, RunicEnergyCostConstructor constr, int buyCost) {
        super(id, manacost, constr, buyCost);
        this.TOGGLE_STRING = "solarcraft_ability_toggle_"+id;
    }


    @Override
    public void cast(ServerPlayer entity, ServerLevel world) {
        super.cast(entity, world);
        this.toggle(entity);
    }



    public void toggle(ServerPlayer player){
        if (isToggled(player)){
            setToggled(player,false);
        }else{
            setToggled(player,true);
        }
    }

    public void setToggled(ServerPlayer player,boolean a){
        player.getPersistentData().putBoolean(TOGGLE_STRING,a);
    }

    public boolean isToggled(ServerPlayer player){
        return player.getPersistentData().getBoolean(TOGGLE_STRING);
    }
}
