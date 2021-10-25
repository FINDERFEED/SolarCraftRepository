package com.finderfeed.solarforge.SolarAbilities.AbilityClasses;

import com.finderfeed.solarforge.Helpers;
import com.finderfeed.solarforge.capabilities.capability_mana.CapabilitySolarMana;
import com.finderfeed.solarforge.events.my_events.AbilityUseEvent;
import com.finderfeed.solarforge.misc_things.RunicEnergy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.common.MinecraftForge;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractAbility {

    public boolean allowed;
    public Map<RunicEnergy.Type,Float> RUNIC_ENERGY_COST;
    public int manacost;
    public String id;
    public int buyCost;

    public AbstractAbility(String id, int manacost,RunicEnergyCostConstructor constr,int buyCost) {
        this.manacost=manacost;
        this.id = id;
        this.RUNIC_ENERGY_COST = constr.COSTS;
        this.buyCost = buyCost;
    }

    public void cast(ServerPlayer entity, ServerLevel world){
        AbilityUseEvent event = new AbilityUseEvent(this,entity);

        MinecraftForge.EVENT_BUS.post(event);
        if (event.isCanceled()) return;
        if (!entity.isDeadOrDying()) {
            allowed = true;
            if (!canUse(entity)){
                allowed = false;
            }
            if (!entity.isCreative() && allowed) {
                double mana = CapabilitySolarMana.getSolarMana(entity).orElseThrow(RuntimeException::new).getMana();
                RUNIC_ENERGY_COST.forEach((type,cost)->{
                    if (RunicEnergy.getEnergy(entity,type) < cost){
                        allowed = false;
                        entity.sendMessage(new TextComponent("Not enough rune energy: "+ type.id.toUpperCase()),entity.getUUID());
                    }
                });
                if (mana < manacost){
                    allowed = false;
                }
                if (allowed){
                    Helpers.spendMana(entity,manacost);
                    RUNIC_ENERGY_COST.forEach((type,cost)->{
                        RunicEnergy.spendEnergy(entity,cost,type);
                    });
                }
            }
        }

    }

    public void refund(Player entity){
        double mana = CapabilitySolarMana.getSolarMana(entity).orElseThrow(RuntimeException::new).getMana();
        CapabilitySolarMana.getSolarMana(entity).orElseThrow(RuntimeException::new).setMana(mana+manacost);
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
