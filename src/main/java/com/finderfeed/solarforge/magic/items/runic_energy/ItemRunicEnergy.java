package com.finderfeed.solarforge.magic.items.runic_energy;

import com.finderfeed.solarforge.misc_things.RunicEnergy;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.Locale;

public class ItemRunicEnergy {

    public static final String RUNIC_ENERGY_TAG = "solarcraft_runic_energy";


    public static float getRunicEnergyFromItem(ItemStack item, RunicEnergy.Type type){
        return item.getOrCreateTagElement(RUNIC_ENERGY_TAG).getFloat(type.id);
    }

    public static void setRunicEnergyOnItem(ItemStack item,RunicEnergy.Type type, float amount){
        item.getOrCreateTagElement(RUNIC_ENERGY_TAG).putFloat(type.id,amount);
    }



    public static float addRunicEnergy(ItemStack stack, IRunicEnergyUser runicEnergyUser, RunicEnergy.Type type, float amount){
        if (!runicEnergyUser.allowedInputs().contains(type)) throw new IllegalStateException("Item " + stack.getItem() + " doesn't allow " + type.id + " runic energy type!");
        if (amount < 0) throw new IllegalStateException("Should not be less than zero!");
        float maxEnergy = runicEnergyUser.getMaxRunicEnergyCapacity();
        float currentEnergy = getRunicEnergyFromItem(stack,type);
        float delta = currentEnergy + amount - maxEnergy;
        setRunicEnergyOnItem(stack,type,Math.min(maxEnergy,currentEnergy+amount));
        return Math.max(0,delta);
    }

    public static float removeRunicEnergy(ItemStack stack, IRunicEnergyUser runicEnergyUser, RunicEnergy.Type type, float amount){
        if (!runicEnergyUser.allowedInputs().contains(type)) throw new IllegalStateException("Item " + stack.getItem() + " doesn't allow " + type.id + " runic energy type!");
        if (amount < 0) throw new IllegalStateException("Should not be below zero!");
        float currentEnergy = getRunicEnergyFromItem(stack,type);
        float delta = currentEnergy - amount;
        setRunicEnergyOnItem(stack,type,Math.max(0,currentEnergy - amount));
        return Math.abs(Math.min(0,delta));
    }

    public static boolean isFullyCharged(ItemStack stack,IRunicEnergyUser user){
        for (RunicEnergy.Type type : user.allowedInputs()){
            float m = getRunicEnergyFromItem(stack,type);
            if (m != user.getMaxRunicEnergyCapacity()){
                return false;
            }
        }
        return true;
    }

    public static boolean hasChargedEnergy(ItemStack stack,IRunicEnergyUser user,RunicEnergy.Type type){
        return getRunicEnergyFromItem(stack,type) == user.getMaxRunicEnergyCapacity();
    }

    public static boolean isEnough(RunicEnergyCost cost,ItemStack stack,IRunicEnergyUser user,Player player){
        if (player.isCreative()) return true;
        for (RunicEnergy.Type type : user.allowedInputs()){
            float energy = getRunicEnergyFromItem(stack,type);
            float c = cost.get(type);
            if (energy < c){
                return false;
            }
        }
        return true;
    }

    public static boolean spendEnergy(RunicEnergyCost cost, ItemStack stack, IRunicEnergyUser user, Player player){
        if (player.isCreative()) return true;
        if (!isEnough(cost,stack,user,player)){
            return false;
        }
        for (RunicEnergy.Type type : user.allowedInputs()){
            removeRunicEnergy(stack,user,type,cost.get(type));
        }
        return true;
    }

    public static void addRunicEnergyTextComponents(ItemStack stack, IRunicEnergyUser item, List<Component> components){
        for (RunicEnergy.Type type : item.allowedInputs()){
            components.add(new TextComponent(type.id.toUpperCase(Locale.ROOT) + ": " + getRunicEnergyFromItem(stack,type)).withStyle(ChatFormatting.GOLD));
        }
    }


}
