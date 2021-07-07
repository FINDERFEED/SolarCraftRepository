package com.finderfeed.solarforge.solar_lexicon.unlockables;

import com.finderfeed.solarforge.Helpers;
import com.finderfeed.solarforge.registries.items.ItemsRegister;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.apache.logging.log4j.core.tools.picocli.CommandLine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class ProgressionHelper {

    public static final String UNLOCK_PATTERN = "solar_forge_player_pattern";
    public static final String TAG_ELEMENT = "fragment";
    public static final String FRAG_ID = "fragmentid";

    public static final int[] NULL_ARRAY = {-1,-1,-1,-1,-1,-1};

    public static final Item RUNES[] = {
            ItemsRegister.SOLAR_RUNE_ARDO.get(),
            ItemsRegister.SOLAR_RUNE_FIRA.get(),
            ItemsRegister.SOLAR_RUNE_KELDA.get(),
            ItemsRegister.SOLAR_RUNE_TERA.get(),
            ItemsRegister.SOLAR_RUNE_URBA.get(),
            ItemsRegister.SOLAR_RUNE_ZETA.get(),
    };


    public static void givePlayerFragment(AncientFragment frag,PlayerEntity pe){
        pe.getPersistentData().putBoolean(Helpers.FRAGMENT+frag.getId(),true);
    }

    public static void applyTagToFragment(ItemStack stack,PlayerEntity pe){
        if (stack.getTagElement(TAG_ELEMENT) == null){
            AncientFragment rndFragment = getRandomUnlockableFragment(pe);
            if (rndFragment != null){

                stack.getOrCreateTagElement(TAG_ELEMENT).putString(FRAG_ID,rndFragment.getId());
            }
        }
    }

    public static AncientFragment getRandomUnlockableFragment(PlayerEntity pe){
        List<AncientFragment> fraglist = getAllUnlockableFragments(pe);
        if (fraglist != null){
            return fraglist.get(pe.level.random.nextInt(fraglist.size()));
        }else{
            return null;
        }
    }


    public static List<AncientFragment> getAllUnlockableFragments(PlayerEntity pe){
        List<AncientFragment> list = new ArrayList<>();
        for (AncientFragment frag : AncientFragment.getAllFragments()){
            if (Helpers.hasPlayerUnlocked(frag.getNeededProgression(),pe) && !doPlayerHasFragment(pe,frag)){
                list.add(frag);
            }
        }
        return list.isEmpty() ? null : list;
    }

    public static void resetPattern(PlayerEntity pe){
        pe.getPersistentData().putIntArray(UNLOCK_PATTERN,NULL_ARRAY);
    }

    public static void generateRandomPatternForPlayer(PlayerEntity pe){
        if (Arrays.equals(pe.getPersistentData().getIntArray(UNLOCK_PATTERN), new int[0])  || Arrays.equals(pe.getPersistentData().getIntArray(UNLOCK_PATTERN), NULL_ARRAY) ){
            pe.getPersistentData().putIntArray(UNLOCK_PATTERN,new int[]{
                    pe.level.random.nextInt(6),
                    pe.level.random.nextInt(6),
                    pe.level.random.nextInt(6),
                    pe.level.random.nextInt(6),
                    pe.level.random.nextInt(6),
                    pe.level.random.nextInt(6),
            });
        }
    }

    public static boolean doPlayerHasFragment(PlayerEntity pe,AncientFragment frag){
        return  pe.getPersistentData().getBoolean(Helpers.FRAGMENT+frag.getId());
    }
}
