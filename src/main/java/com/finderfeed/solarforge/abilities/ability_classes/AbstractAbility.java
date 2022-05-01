package com.finderfeed.solarforge.abilities.ability_classes;

import com.finderfeed.solarforge.events.my_events.AbilityUseEvent;
import com.finderfeed.solarforge.magic.items.runic_energy.RunicEnergyCost;
import com.finderfeed.solarforge.misc_things.RunicEnergy;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.MinecraftForge;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractAbility {


    public RunicEnergyCost cost;
    public String id;
    public int buyCost;

    public AbstractAbility(String id, RunicEnergyCost cost, int buyCost) {
        this.id = id;
        this.cost = cost;
        this.buyCost = buyCost;
    }

    public abstract void cast(ServerPlayer entity, ServerLevel world);

    public RunicEnergyCost getCost() {
        return cost;
    }

    public String getId() {
        return id;
    }

    public int getBuyCost() {
        return buyCost;
    }
}
//    public boolean shouldBeCasted(ServerPlayer entity, ServerLevel world){
//        AbilityUseEvent event = new AbilityUseEvent(this,entity);
//        MinecraftForge.EVENT_BUS.post(event);
//        if (event.isCanceled()) return false;
////        allowed = canUse(entity);
////        if (!entity.isCreative() && allowed) {
////            RUNIC_ENERGY_COST.forEach((type,cost)->{
////                if (RunicEnergy.getEnergy(entity,type) < cost){
////                    allowed = false;
////                    entity.sendMessage(new TextComponent("Not enough rune energy: "+ type.id.toUpperCase()),entity.getUUID());
////                }
////            });
////            if (allowed){
////                RUNIC_ENERGY_COST.forEach((type,cost)->{
////                    RunicEnergy.spendEnergy(entity,cost,type);
////                });
////            }
////        }
//
//
//
//        return true;
//    }