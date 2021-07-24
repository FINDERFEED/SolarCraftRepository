package com.finderfeed.solarforge.misc_things;

import com.finderfeed.solarforge.Helpers;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.player.PlayerEvent;

public class RunicEnergy {

    public static String MAX_ENERGY_TAG = "solarcraft.max_player_runic_energy";
    public static String DEFAULT_ENERGY_TAG = "solarcraft.solar_rune_energy_";


    public static void setEnergy(PlayerEntity playerEntity,float amount, Type type){
        CompoundNBT nbt = playerEntity.getPersistentData();
        nbt.putFloat(DEFAULT_ENERGY_TAG+type.id,amount);
    }


    public static float givePlayerEnergy(PlayerEntity playerEntity,float energyAmount, Type type){
        CompoundNBT nbt = playerEntity.getPersistentData();
        float maxEnergy = nbt.getFloat(MAX_ENERGY_TAG);
        if (maxEnergy == 0){
            nbt.putInt(MAX_ENERGY_TAG,10000);
            maxEnergy = nbt.getFloat(MAX_ENERGY_TAG);
        }

        float kolvo = nbt.getFloat(DEFAULT_ENERGY_TAG+type.id) + energyAmount;
        if (kolvo <= maxEnergy){
            nbt.putFloat(DEFAULT_ENERGY_TAG+type.id,kolvo);

            return 0;
        }else{
            float raznitsa = kolvo - maxEnergy;
            nbt.putFloat(DEFAULT_ENERGY_TAG+type.id,maxEnergy);

            return raznitsa;
        }


    }


    public static boolean spendEnergy(PlayerEntity playerEntity,float amount,Type type){
        float currentEnergy = getEnergy(playerEntity,type);
        if ((currentEnergy - amount) >= 0){
            setEnergy(playerEntity,type,currentEnergy-amount);
            return true;
        }else{
            return false;
        }
    }


    public static float getEnergy(PlayerEntity playerEntity,Type type){
        return playerEntity.getPersistentData().getFloat(DEFAULT_ENERGY_TAG+type.id);
    }

    public static void setEnergy(PlayerEntity playerEntity,Type type,float amount){
        playerEntity.getPersistentData().putFloat(DEFAULT_ENERGY_TAG+type.id,amount);
    }


    public static void handleCloneEvent(PlayerEvent.Clone event){
        PlayerEntity newP = event.getPlayer();
        PlayerEntity oldP = event.getOriginal();

        for (Type type : Type.values()){
            setEnergy(newP,getEnergy(oldP,type),type);
        }

    }

    public enum Type{
        ZETA("zeta"),
        ARDO("ardo"),
        URBA("urba"),
        KELDA("kelda"),
        FIRA("fira"),
        TERA("tera");


        public String id;

        Type(String id){
            this.id = id;
        }


        public static Type byId(String id){
            for (Type type : values()){
                if (type.id.equals(id)){
                    return type;
                }
            }
            return null;
        }


    }
}