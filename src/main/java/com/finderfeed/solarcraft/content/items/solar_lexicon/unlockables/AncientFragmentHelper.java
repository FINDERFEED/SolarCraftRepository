package com.finderfeed.solarcraft.content.items.solar_lexicon.unlockables;

import com.finderfeed.solarcraft.helpers.Helpers;
import com.finderfeed.solarcraft.SolarCraft;
import com.finderfeed.solarcraft.content.items.solar_lexicon.progressions.Progression;
import com.finderfeed.solarcraft.misc_things.RunicEnergy;
import com.finderfeed.solarcraft.registries.items.SCItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;
import java.util.*;


/**
 * Class for all things with fragments
 */
public class AncientFragmentHelper {




    public static final String COMPOUND_TAG_FRAGMENTS = "fragments";
    public static final String UNLOCK_PATTERN = "solar_forge_player_pattern";
    public static final String TAG_ELEMENT = "fragment";
    public static final String FRAG_ID = "fragmentid";

    public static final int[] NULL_ARRAY = {-1,-1,-1,-1,-1,-1};


    //they have weird names didn't they?
    public static final Item[] RUNES = {
            SCItems.SOLAR_RUNE_ARDO.get(),
            SCItems.SOLAR_RUNE_FIRA.get(),
            SCItems.SOLAR_RUNE_KELDA.get(),
            SCItems.SOLAR_RUNE_TERA.get(),
            SCItems.SOLAR_RUNE_URBA.get(),
            SCItems.SOLAR_RUNE_ZETA.get(),
            SCItems.SOLAR_RUNE_GIRO.get(),
            SCItems.SOLAR_RUNE_ULTIMA.get()
    };

    public static Map<RunicEnergy.Type,Item> RUNES_MAP = initRunesMap();


    public static Map<RunicEnergy.Type,Item> initRunesMap(){
        RUNES_MAP = new HashMap<>();
        RUNES_MAP.put(RunicEnergy.Type.ARDO, SCItems.SOLAR_RUNE_ARDO.get());
        RUNES_MAP.put(RunicEnergy.Type.FIRA, SCItems.SOLAR_RUNE_FIRA.get());
        RUNES_MAP.put(RunicEnergy.Type.ZETA, SCItems.SOLAR_RUNE_ZETA.get());
        RUNES_MAP.put(RunicEnergy.Type.KELDA, SCItems.SOLAR_RUNE_KELDA.get());
        RUNES_MAP.put(RunicEnergy.Type.URBA, SCItems.SOLAR_RUNE_URBA.get());
        RUNES_MAP.put(RunicEnergy.Type.TERA, SCItems.SOLAR_RUNE_TERA.get());
        RUNES_MAP.put(RunicEnergy.Type.GIRO, SCItems.SOLAR_RUNE_GIRO.get());
        RUNES_MAP.put(RunicEnergy.Type.ULTIMA, SCItems.SOLAR_RUNE_ULTIMA.get());
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

    @Nullable
    public static AncientFragment getFragmentFromItem(ItemStack stack){
        if (stack.getTagElement(TAG_ELEMENT) == null) return null;
        CompoundTag tag = stack.getOrCreateTagElement(TAG_ELEMENT);
        return AncientFragment.getFragmentByID(tag.getString(FRAG_ID));
    }


    public static AncientFragment getRandomUnlockableFragment(Player pe){
        List<AncientFragment> fraglist = getAllUnlockableFragments(pe);
        if (fraglist != null){
            fraglist.sort(Comparator.comparingInt(AncientFragment::getPriority));
            List<AncientFragment> h = getNextFragmentsForRandoming(fraglist);
            if (h != null) {
                return h.get(pe.level.random.nextInt(h.size()));
            }else{
                SolarCraft.LOGGER.log(org.apache.logging.log4j.Level.ERROR,"Maybe something went wrong but i am not sure.");
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
                    if (!Helpers.hasPlayerCompletedProgression(a, pe)) {
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

}
