package com.finderfeed.solarforge.abilities.ability_classes;

import com.finderfeed.solarforge.events.my_events.AbilityUseEvent;
import com.finderfeed.solarforge.misc_things.RunicEnergy;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.MinecraftForge;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractAbility {

    public boolean allowed;
    public Map<RunicEnergy.Type,Float> RUNIC_ENERGY_COST;

    public String id;
    public int buyCost;

    public AbstractAbility(String id,RunicEnergyCostConstructor constr,int buyCost) {
        this.id = id;
        this.RUNIC_ENERGY_COST = constr.COSTS;
        this.buyCost = buyCost;
    }

    public void cast(ServerPlayer entity, ServerLevel world){
        AbilityUseEvent event = new AbilityUseEvent(this,entity);
        MinecraftForge.EVENT_BUS.post(event);
        if (event.isCanceled()) return;
        allowed = true;
        if (!canUse(entity)){
            allowed = false;
        }
        if (!entity.isCreative() && allowed) {
            RUNIC_ENERGY_COST.forEach((type,cost)->{
                if (RunicEnergy.getEnergy(entity,type) < cost){
                    allowed = false;
                    entity.sendMessage(new TextComponent("Not enough rune energy: "+ type.id.toUpperCase()),entity.getUUID());
                }
            });
            if (allowed){
                RUNIC_ENERGY_COST.forEach((type,cost)->{
                    RunicEnergy.spendEnergy(entity,cost,type);
                });
            }
        }


    }

    public void refund(Player entity){

        RUNIC_ENERGY_COST.forEach((type,cost)->{
            RunicEnergy.givePlayerEnergy(entity,cost,type);
        });
    }


    public boolean canUse(Player playerEntity){
        return playerEntity.getPersistentData().getBoolean("solar_forge_can_player_use_"+id);
    }

    public static class RunicEnergyCostConstructor{

        public Map<RunicEnergy.Type,Float> COSTS=new HashMap<>();

        public RunicEnergyCostConstructor(){}

        public RunicEnergyCostConstructor addRunicEnergy(RunicEnergy.Type type,float amount){
            COSTS.put(type,amount);
            return this;
        }
    }

}
