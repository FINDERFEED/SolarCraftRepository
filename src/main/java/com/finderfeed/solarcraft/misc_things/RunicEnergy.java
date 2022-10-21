package com.finderfeed.solarcraft.misc_things;

import com.finderfeed.solarcraft.registries.blocks.SolarcraftBlocks;
import net.minecraft.world.entity.player.Player;
import net.minecraft.nbt.CompoundTag;

import net.minecraft.world.level.block.Block;
import net.minecraftforge.event.entity.player.PlayerEvent;

import java.util.Map;

public class RunicEnergy {

    public static String MAX_ENERGY_TAG = "solarcraft.max_player_runic_energy";
    public static String DEFAULT_ENERGY_TAG = "solarcraft.solar_rune_energy_";

    public static Map<Block, Type> BLOCK_TO_RUNE_ENERGY_TYPE = Map.of(
            SolarcraftBlocks.TERA_RUNE_BLOCK.get(), RunicEnergy.Type.TERA,
            SolarcraftBlocks.FIRA_RUNE_BLOCK.get(), RunicEnergy.Type.FIRA,
            SolarcraftBlocks.ZETA_RUNE_BLOCK.get(), RunicEnergy.Type.ZETA,
            SolarcraftBlocks.KELDA_RUNE_BLOCK.get(), RunicEnergy.Type.KELDA,
            SolarcraftBlocks.URBA_RUNE_BLOCK.get(), RunicEnergy.Type.URBA,
            SolarcraftBlocks.ARDO_RUNE_BLOCK.get(), RunicEnergy.Type.ARDO,
            SolarcraftBlocks.ULTIMA_RUNE_BLOCK.get(), RunicEnergy.Type.ULTIMA,
            SolarcraftBlocks.GIRO_RUNE_BLOCK.get(), RunicEnergy.Type.GIRO

    );

    public static Map<Block, Type[]> BLOCK_TO_RUNE_ENERGY_TYPE_ARRAY = Map.of(
            SolarcraftBlocks.TERA_RUNE_BLOCK.get(), new Type[]{RunicEnergy.Type.TERA},
            SolarcraftBlocks.FIRA_RUNE_BLOCK.get(), new Type[]{RunicEnergy.Type.FIRA},
            SolarcraftBlocks.ZETA_RUNE_BLOCK.get(), new Type[]{RunicEnergy.Type.ZETA},
            SolarcraftBlocks.KELDA_RUNE_BLOCK.get(), new Type[]{RunicEnergy.Type.KELDA},
            SolarcraftBlocks.URBA_RUNE_BLOCK.get(), new Type[]{RunicEnergy.Type.URBA},
            SolarcraftBlocks.ARDO_RUNE_BLOCK.get(), new Type[]{RunicEnergy.Type.ARDO},
            SolarcraftBlocks.ULTIMA_RUNE_BLOCK.get(), new Type[]{RunicEnergy.Type.ULTIMA},
            SolarcraftBlocks.GIRO_RUNE_BLOCK.get(), new Type[]{RunicEnergy.Type.GIRO},
            SolarcraftBlocks.MULTIREPEATER_BLOCK.get(),Type.getAll()
    );

    public static void setEnergy(Player playerEntity,float amount, Type type){
        CompoundTag nbt = playerEntity.getPersistentData();
        nbt.putFloat(DEFAULT_ENERGY_TAG+type.id,amount);
    }


    public static float givePlayerEnergy(Player playerEntity,float energyAmount, Type type){
        CompoundTag nbt = playerEntity.getPersistentData();
        float maxEnergy = nbt.getFloat(MAX_ENERGY_TAG);
        if (maxEnergy == 0){
            nbt.putFloat(MAX_ENERGY_TAG,10000);
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


    public static boolean spendEnergy(Player playerEntity,float amount,Type type){
        float currentEnergy = getEnergy(playerEntity,type);
        if ((currentEnergy - amount) >= 0){
            setEnergy(playerEntity,type,currentEnergy-amount);
            return true;
        }else{
            return false;
        }
    }


    public static float getEnergy(Player playerEntity,Type type){
        return playerEntity.getPersistentData().getFloat(DEFAULT_ENERGY_TAG+type.id);
    }

    public static void setEnergy(Player playerEntity,Type type,float amount){
        playerEntity.getPersistentData().putFloat(DEFAULT_ENERGY_TAG+type.id,amount);
    }


    public static void handleCloneEvent(PlayerEvent.Clone event){
        Player newP = event.getEntity();
        Player oldP = event.getOriginal();

        for (Type type : Type.values()){
            setEnergy(newP,getEnergy(oldP,type),type);
        }

    }

    public static boolean hasFoundType(Player player,Type type){
        return player.getPersistentData().getBoolean(type.id+"_is_unlocked");
    }

    public static void setFound(Player pl,Type type){
        pl.getPersistentData().putBoolean(type.id+"_is_unlocked",true);
    }


    public enum Type{
        ZETA("zeta",0),
        ARDO("ardo",1),
        URBA("urba",2),
        KELDA("kelda",3),
        FIRA("fira",4),
        TERA("tera",5),
        GIRO("giro",6),
        ULTIMA("ultima",7),
        NONE("none",Integer.MAX_VALUE)
        ;


        private static final Type[] ALL = new Type[]{ZETA,ARDO,URBA,KELDA,FIRA,TERA,GIRO,ULTIMA};
        public String id;
        private int index;

        Type(String id,int index){
            this.id = id;
            this.index = index;
        }


        public static Type[] getAll(){
            return ALL;
        }

        public static Type byIndex(int id){
            return getAll()[id];
        }

        public static Type byId(String id){
            for (Type type : values()){
                if (type.id.equals(id)){
                    return type;
                }
            }
            return null;
        }

        public int getIndex() {
            return index;
        }

    }
}
