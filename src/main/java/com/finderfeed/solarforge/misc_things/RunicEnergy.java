package com.finderfeed.solarforge.misc_things;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.StringTextComponent;

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
            nbt.putInt(MAX_ENERGY_TAG,100000);
            maxEnergy = nbt.getFloat(MAX_ENERGY_TAG);
        }

        float kolvo = nbt.getFloat(DEFAULT_ENERGY_TAG+type.id) + energyAmount;
        if (kolvo <= maxEnergy){
            nbt.putFloat(DEFAULT_ENERGY_TAG+type.id,kolvo);
            playerEntity.displayClientMessage(new StringTextComponent(type.id+" "+nbt.getInt(DEFAULT_ENERGY_TAG+type.id)),true);
            return 0;
        }else{
            float raznitsa = kolvo - maxEnergy;
            nbt.putFloat(DEFAULT_ENERGY_TAG+type.id,maxEnergy);
            playerEntity.displayClientMessage(new StringTextComponent(type.id+" "+nbt.getInt(DEFAULT_ENERGY_TAG+type.id)),true);
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
