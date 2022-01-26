package com.finderfeed.solarforge.magic.items.solar_lexicon.unlockables;

import com.finderfeed.solarforge.Helpers;
import com.finderfeed.solarforge.SolarForge;
import com.finderfeed.solarforge.magic.items.solar_lexicon.achievements.Progression;
import com.finderfeed.solarforge.misc_things.RunicEnergy;
import com.finderfeed.solarforge.recipe_types.InfusingRecipe;
import com.finderfeed.solarforge.recipe_types.infusing_crafting.InfusingCraftingRecipe;
import com.finderfeed.solarforge.recipe_types.solar_smelting.SolarSmeltingRecipe;
import com.finderfeed.solarforge.registries.items.ItemsRegister;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;

import java.util.*;


/**
 * Class for all things with fragments
 */
public class ProgressionHelper {




    public static final String COMPOUND_TAG_FRAGMENTS = "fragments";
    public static Map<Item, InfusingRecipe> INFUSING_RECIPE_MAP = new HashMap<>();
    public static Map<Item, InfusingRecipe> UPGRADES_INFUSING_RECIPE_MAP = new HashMap<>();
    public static Map<Item, SolarSmeltingRecipe> SMELTING_RECIPE_MAP = new HashMap<>();
    public static Map<Item, InfusingCraftingRecipe> INFUSING_CRAFTING_RECIPE_MAP = new HashMap<>();
    public static final String UNLOCK_PATTERN = "solar_forge_player_pattern";
    public static final String TAG_ELEMENT = "fragment";
    public static final String FRAG_ID = "fragmentid";

    public static final int[] NULL_ARRAY = {-1,-1,-1,-1,-1,-1};


    //they have weird names didn't they?
    public static final Item[] RUNES = {
            ItemsRegister.SOLAR_RUNE_ARDO.get(),
            ItemsRegister.SOLAR_RUNE_FIRA.get(),
            ItemsRegister.SOLAR_RUNE_KELDA.get(),
            ItemsRegister.SOLAR_RUNE_TERA.get(),
            ItemsRegister.SOLAR_RUNE_URBA.get(),
            ItemsRegister.SOLAR_RUNE_ZETA.get(),
            ItemsRegister.SOLAR_RUNE_GIRO.get(),
            ItemsRegister.SOLAR_RUNE_ULTIMA.get()
    };

    public static Map<RunicEnergy.Type,Item> RUNES_MAP = initRunesMap();


    public static Map<RunicEnergy.Type,Item> initRunesMap(){
        RUNES_MAP = new HashMap<>();
        RUNES_MAP.put(RunicEnergy.Type.ARDO,ItemsRegister.SOLAR_RUNE_ARDO.get());
        RUNES_MAP.put(RunicEnergy.Type.FIRA,ItemsRegister.SOLAR_RUNE_FIRA.get());
        RUNES_MAP.put(RunicEnergy.Type.ZETA,ItemsRegister.SOLAR_RUNE_ZETA.get());
        RUNES_MAP.put(RunicEnergy.Type.KELDA,ItemsRegister.SOLAR_RUNE_KELDA.get());
        RUNES_MAP.put(RunicEnergy.Type.URBA,ItemsRegister.SOLAR_RUNE_URBA.get());
        RUNES_MAP.put(RunicEnergy.Type.TERA,ItemsRegister.SOLAR_RUNE_TERA.get());
        RUNES_MAP.put(RunicEnergy.Type.GIRO,ItemsRegister.SOLAR_RUNE_GIRO.get());
        RUNES_MAP.put(RunicEnergy.Type.ULTIMA,ItemsRegister.SOLAR_RUNE_ULTIMA.get());
        return RUNES_MAP;
    }



    public static void givePlayerFragment(AncientFragment frag,Player pe){
        if (pe.getPersistentData().get(COMPOUND_TAG_FRAGMENTS) == null){
            pe.getPersistentData().put(COMPOUND_TAG_FRAGMENTS,new CompoundTag());
        }

        pe.getPersistentData().getCompound(COMPOUND_TAG_FRAGMENTS).putBoolean(Helpers.FRAGMENT+frag.getId(),true);
    }

    public static void revokePlayerFragment(AncientFragment frag,Player pe){
        if (pe.getPersistentData().get(COMPOUND_TAG_FRAGMENTS) == null){
            pe.getPersistentData().put(COMPOUND_TAG_FRAGMENTS,new CompoundTag());
        }
        pe.getPersistentData().getCompound(COMPOUND_TAG_FRAGMENTS).putBoolean(Helpers.FRAGMENT+frag.getId(),false);
    }

    public static void givePlayerFragmentOld(AncientFragment frag,Player pe){


        pe.getPersistentData().putBoolean(Helpers.FRAGMENT+frag.getId(),true);
    }

    public static void revokePlayerFragmentOld(AncientFragment frag,Player pe){

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
            fraglist.sort((f1,f2)->{
                return f1.getPriority() - f2.getPriority();
            });
            List<AncientFragment> h = getNextFragmentsForRandoming(fraglist);
            if (h != null) {
                return h.get(pe.level.random.nextInt(h.size()));
            }else{
                SolarForge.LOGGER.log(org.apache.logging.log4j.Level.ERROR,"Maybe something went wrong but i am not sure.");
                return null;
            }
        }else{
            return null;
        }
    }

    private static List<AncientFragment> getNextFragmentsForRandoming(List<AncientFragment> a){
        List<AncientFragment> attacksToCopyTo = new ArrayList<>();
        if (a.size() != 0) {
            int intToSeek = a.get(0).getPriority();
            for (AncientFragment h : a) {
                if (h.getPriority() == intToSeek) {
                    attacksToCopyTo.add(h);
                }else{
                    break;
                }
            }
        }else{
            return null;
        }
        return attacksToCopyTo;
    }

    public static List<AncientFragment> getAllUnlockableFragments(Player pe){
        List<AncientFragment> list = new ArrayList<>();
        for (AncientFragment frag : AncientFragment.getAllFragments()){
            if (canBeUnlocked(pe,frag)){
                list.add(frag);
            }
        }
        return list.isEmpty() ? null : list;
    }

    private static boolean canBeUnlocked(Player pe,AncientFragment frag){
        if (!doPlayerHasFragment(pe,frag)){
            if (!(frag.getNeededProgression() == null)) {
                for (Progression a : frag.getNeededProgression()) {
                    if (!Helpers.hasPlayerUnlocked(a, pe)) {
                        return false;
                    }
                }
            }else{
                return true;
            }
        }else{
            return false;
        }
        return true;
    }

    public static void resetPattern(Player pe){
        pe.getPersistentData().putIntArray(UNLOCK_PATTERN,NULL_ARRAY);
    }

    public static void generateRandomPatternForPlayer(Player pe){
        if (Arrays.equals(pe.getPersistentData().getIntArray(UNLOCK_PATTERN), new int[0])  || Arrays.equals(pe.getPersistentData().getIntArray(UNLOCK_PATTERN), NULL_ARRAY) ){
            pe.getPersistentData().putIntArray(UNLOCK_PATTERN,new int[]{
                    pe.level.random.nextInt(8),
                    pe.level.random.nextInt(8),
                    pe.level.random.nextInt(8),
                    pe.level.random.nextInt(8),
                    pe.level.random.nextInt(8),
                    pe.level.random.nextInt(8),
            });
        }
    }


    public static boolean doPlayerAlreadyHasPattern(Player pe){
        if (Arrays.equals(pe.getPersistentData().getIntArray(UNLOCK_PATTERN), new int[0])  || Arrays.equals(pe.getPersistentData().getIntArray(UNLOCK_PATTERN), NULL_ARRAY) ){
            return false;
        }
        return true;
    }

    public static String getFragIdString(AncientFragment frag){
        return Helpers.FRAGMENT+frag.getId();
    }

    public static int[] getPlayerPattern(Player pe){
        return pe.getPersistentData().getIntArray(UNLOCK_PATTERN);
    }


    public static boolean doPlayerHasFragment(Player pe,AncientFragment frag){
        if (pe.getPersistentData().get(COMPOUND_TAG_FRAGMENTS) == null){
            pe.getPersistentData().put(COMPOUND_TAG_FRAGMENTS,new CompoundTag());
        }
        return  pe.getPersistentData().getCompound(COMPOUND_TAG_FRAGMENTS).getBoolean(Helpers.FRAGMENT+frag.getId());
    }

    public static boolean doPlayerHasFragmentOld(Player pe,AncientFragment frag){

        return  pe.getPersistentData().getBoolean(Helpers.FRAGMENT+frag.getId());
    }

    public static InfusingRecipe getInfusingRecipeForItem(Item item){
        return INFUSING_RECIPE_MAP.get(item);
    }
    public static InfusingCraftingRecipe getInfusingCraftingRecipeForItem(Item item){
        return INFUSING_CRAFTING_RECIPE_MAP.get(item);
    }

    public static SolarSmeltingRecipe getSolarSmeltingRecipeForItem(Item item){
        return SMELTING_RECIPE_MAP.get(item);
    }


    public static void initInfRecipesMap(RecipeManager manager){
        List<InfusingRecipe> list = manager.getAllRecipesFor(SolarForge.INFUSING_RECIPE_TYPE);
        list.forEach((recipe)->{
            if (recipe.tag.equals("") && !INFUSING_RECIPE_MAP.containsKey(recipe.output.getItem())) {
                INFUSING_RECIPE_MAP.put(recipe.output.getItem(),recipe);
            }else if (!recipe.tag.equals("") && !UPGRADES_INFUSING_RECIPE_MAP.containsKey(recipe.output.getItem())){
                UPGRADES_INFUSING_RECIPE_MAP.put(recipe.output.getItem(),recipe);
            }
        });
    }

    public static void initSmeltingRecipesMap(RecipeManager manager){
        List<SolarSmeltingRecipe> list = manager.getAllRecipesFor(SolarForge.SOLAR_SMELTING);
        list.forEach((recipe)->{
            if (!SMELTING_RECIPE_MAP.containsKey(recipe.output.getItem())) {
                SMELTING_RECIPE_MAP.put(recipe.output.getItem(),recipe);
            }
        });
    }

    public static void initInfusingCraftingRecipes(RecipeManager manager){
        List<InfusingCraftingRecipe> list = manager.getAllRecipesFor(SolarForge.INFUSING_CRAFTING_RECIPE_TYPE);
        list.forEach((recipe)->{
            if (!INFUSING_CRAFTING_RECIPE_MAP.containsKey(recipe.getOutput().getItem())) {
                INFUSING_CRAFTING_RECIPE_MAP.put(recipe.getOutput().getItem(),recipe);
            }
        });
    }
}
