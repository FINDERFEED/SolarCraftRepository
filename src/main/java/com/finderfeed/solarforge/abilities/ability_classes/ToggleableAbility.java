package com.finderfeed.solarforge.abilities.ability_classes;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public class ToggleableAbility extends AbstractAbility{

    private final String TOGGLE_STRING;

    public ToggleableAbility(String id, RunicEnergyCostConstructor constr, int buyCost) {
        super(id, constr, buyCost);
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

    public boolean isToggled(Player player){
        return player.getPersistentData().getBoolean(TOGGLE_STRING);
    }


}
