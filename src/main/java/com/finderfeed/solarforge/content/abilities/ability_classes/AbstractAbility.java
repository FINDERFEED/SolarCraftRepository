package com.finderfeed.solarforge.content.abilities.ability_classes;

import com.finderfeed.solarforge.content.items.runic_energy.RunicEnergyCost;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;

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
////                    entity.sendMessage(Component.literal("Not enough rune energy: "+ type.id.toUpperCase()),entity.getUUID());
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