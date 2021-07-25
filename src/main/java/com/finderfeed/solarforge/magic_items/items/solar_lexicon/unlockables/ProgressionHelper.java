package com.finderfeed.solarforge.magic_items.items.solar_lexicon.unlockables;

import com.finderfeed.solarforge.Helpers;
import com.finderfeed.solarforge.SolarForge;
import com.finderfeed.solarforge.misc_things.RunicEnergy;
import com.finderfeed.solarforge.recipe_types.InfusingRecipe;
import com.finderfeed.solarforge.recipe_types.solar_smelting.SolarSmeltingRecipe;
import com.finderfeed.solarforge.registries.items.ItemsRegister;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.*;


/**
 * Class for all things with fragments
 */
public class ProgressionHelper {

    public static Map<Item, InfusingRecipe> INFUSING_RECIPE_MAP = new HashMap<>();
    public static Map<Item, InfusingRecipe> UPGRADES_INFUSING_RECIPE_MAP = new HashMap<>();
    public static Map<Item, SolarSmeltingRecipe> SMELTING_RECIPE_MAP = new HashMap<>();
    public static final String UNLOCK_PATTERN = "solar_forge_player_pattern";
    public static final String TAG_ELEMENT = "fragment";
    public static final String FRAG_ID = "fragmentid";

    public static final int[] NULL_ARRAY = {-1,-1,-1,-1,-1,-1};

    //they have weird names didnt they?
    public static final Item[] RUNES = {
            ItemsRegister.SOLAR_RUNE_ARDO.get(),
            ItemsRegister.SOLAR_RUNE_FIRA.get(),
            ItemsRegister.SOLAR_RUNE_KELDA.get(),
            ItemsRegister.SOLAR_RUNE_TERA.get(),
            ItemsRegister.SOLAR_RUNE_URBA.get(),
            ItemsRegister.SOLAR_RUNE_ZETA.get(),
    };

    public static Map<RunicEnergy.Type,Item> RUNES_MAP = null;


    public static void initRunesMap(){
        RUNES_MAP = new HashMap<>();
        RUNES_MAP.put(RunicEnergy.Type.ARDO,ItemsRegister.SOLAR_RUNE_ARDO.get());
        RUNES_MAP.put(RunicEnergy.Type.FIRA,ItemsRegister.SOLAR_RUNE_FIRA.get());
        RUNES_MAP.put(RunicEnergy.Type.ZETA,ItemsRegister.SOLAR_RUNE_ZETA.get());
        RUNES_MAP.put(RunicEnergy.Type.KELDA,ItemsRegister.SOLAR_RUNE_KELDA.get());
        RUNES_MAP.put(RunicEnergy.Type.URBA,ItemsRegister.SOLAR_RUNE_URBA.get());
        RUNES_MAP.put(RunicEnergy.Type.TERA,ItemsRegister.SOLAR_RUNE_TERA.get());

    }



    public static void givePlayerFragment(AncientFragment frag,Player pe){
        pe.getPersistentData().putBoolean(Helpers.FRAGMENT+frag.getId(),true);
    }

    public static void revokePlayerFragment(AncientFragment frag,Player pe){
        pe.getPersistentData().putBoolean(Helpers.FRAGMENT+frag.getId(),false);
    }

    public static void applyTagToFragment(ItemStack stack,Player pe){
        if (stack.getTagElement(TAG_ELEMENT) == null){
            AncientFragment rndFragment = getRandomUnlockableFragment(pe);
            if (rndFragment != null){

                stack.getOrCreateTagElement(TAG_ELEMENT).putString(FRAG_ID,rndFragment.getId());

            }
        }
    }

    public static void applyTagToFragment(ItemStack stack,AncientFragment fragment){
                stack.getOrCreateTagElement(TAG_ELEMENT).putString(FRAG_ID,fragment.getId());
    }


    public static AncientFragment getRandomUnlockableFragment(Player pe){
        List<AncientFragment> fraglist = getAllUnlockableFragments(pe);
        if (fraglist != null){
            return fraglist.get(pe.level.random.nextInt(fraglist.size()));
        }else{
            return null;
        }
    }


    public static List<AncientFragment> getAllUnlockableFragments(Player pe){
        List<AncientFragment> list = new ArrayList<>();
        for (AncientFragment frag : AncientFragment.getAllFragments()){
            if (Helpers.hasPlayerUnlocked(frag.getNeededProgression(),pe) && !doPlayerHasFragment(pe,frag)){
                list.add(frag);
            }
        }
        return list.isEmpty() ? null : list;
    }

    public static void resetPattern(Player pe){
        pe.getPersistentData().putIntArray(UNLOCK_PATTERN,NULL_ARRAY);
    }

    public static void generateRandomPatternForPlayer(Player pe){
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


    public static boolean doPlayerAlreadyHasPattern(Player pe){
        if (Arrays.equals(pe.getPersistentData().getIntArray(UNLOCK_PATTERN), new int[0])  || Arrays.equals(pe.getPersistentData().getIntArray(UNLOCK_PATTERN), NULL_ARRAY) ){
            return false;
        }
        return true;
    }


    public static int[] getPlayerPattern(Player pe){
        return pe.getPersistentData().getIntArray(UNLOCK_PATTERN);
    }


    public static boolean doPlayerHasFragment(Player pe,AncientFragment frag){
        return  pe.getPersistentData().getBoolean(Helpers.FRAGMENT+frag.getId());
    }

    public static InfusingRecipe getInfusingRecipeForItem(Item item){
        return INFUSING_RECIPE_MAP.get(item);
    }

    public static SolarSmeltingRecipe getSolarSmeltingRecipeForItem(Item item){
        return SMELTING_RECIPE_MAP.get(item);
    }


    public static void initInfRecipesMap(Level world){
        List<InfusingRecipe> list = world.getRecipeManager().getAllRecipesFor(SolarForge.INFUSING_RECIPE_TYPE);
        list.forEach((recipe)->{
            if (recipe.tag.equals("") && !INFUSING_RECIPE_MAP.containsKey(recipe.output.getItem())) {
                INFUSING_RECIPE_MAP.put(recipe.output.getItem(),recipe);
            }else if (!recipe.tag.equals("") && !UPGRADES_INFUSING_RECIPE_MAP.containsKey(recipe.output.getItem())){
                UPGRADES_INFUSING_RECIPE_MAP.put(recipe.output.getItem(),recipe);
            }
        });
    }

    public static void initSmeltingRecipesMap(Level world){
        List<SolarSmeltingRecipe> list = world.getRecipeManager().getAllRecipesFor(SolarForge.SOLAR_SMELTING);
        list.forEach((recipe)->{
            if (!SMELTING_RECIPE_MAP.containsKey(recipe.output.getItem())) {
                SMELTING_RECIPE_MAP.put(recipe.output.getItem(),recipe);
            }
        });
    }
}
