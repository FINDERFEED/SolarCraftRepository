package com.finderfeed.solarforge.misc_things;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;

public class RunicEnergy {

    public static String MAX_ENERGY_TAG = "solarcraft.max_player_runic_energy";
    public static String DEFAULT_ENERGY_TAG = "solarcraft.solar_rune_energy_";

    public static int givePlayerEnergy(PlayerEntity playerEntity,int energyAmount, Type type){
        CompoundNBT nbt = playerEntity.getPersistentData();
        int maxEnergy = nbt.getInt(MAX_ENERGY_TAG);
        if (maxEnergy == 0){
            nbt.putInt(MAX_ENERGY_TAG,100000);
            maxEnergy = nbt.getInt(MAX_ENERGY_TAG);
        }

        int kolvo = nbt.getInt(DEFAULT_ENERGY_TAG+type.id) + energyAmount;
        if (kolvo <= maxEnergy){
            nbt.putInt(DEFAULT_ENERGY_TAG+type.id,kolvo);
            return 0;
        }else{
            int raznitsa = kolvo - maxEnergy;
            nbt.putInt(DEFAULT_ENERGY_TAG+type.id,maxEnergy);
            return raznitsa;
        }


    }


    public static boolean spendEnergy(PlayerEntity playerEntity,int amount,Type type){
        int currentEnergy = getEnergy(playerEntity,type);
        if ((currentEnergy - amount) >= 0){
            setEnergy(playerEntity,type,currentEnergy-amount);
            return true;
        }else{
            return false;
        }
    }


    private static int getEnergy(PlayerEntity playerEntity,Type type){
        return playerEntity.getPersistentData().getInt(DEFAULT_ENERGY_TAG+type.id);
    }

    private static void setEnergy(PlayerEntity playerEntity,Type type,int amount){
        playerEntity.getPersistentData().putInt(DEFAULT_ENERGY_TAG+type.id,amount);
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
