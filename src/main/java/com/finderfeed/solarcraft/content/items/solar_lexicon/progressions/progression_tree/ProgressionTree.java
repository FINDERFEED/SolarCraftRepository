package com.finderfeed.solarcraft.content.items.solar_lexicon.progressions.progression_tree;

import com.finderfeed.solarcraft.content.items.solar_lexicon.progressions.Progression;

import java.util.*;

public class ProgressionTree {

    public static final ProgressionTree INSTANCE = loadTree();

    public HashMap<Progression,TreePart<Progression>> PROGRESSION_TREE = new HashMap<>();
    public List<Progression> PROGRESSIONS = new ArrayList<>();

    public ProgressionTree(){

    }


    public static ProgressionTree loadTree(){
        ProgressionTree tree = new ProgressionTree();

        for (Progression a : Progression.allProgressions){
            tree.addGeneralAchievement(a);
        }
        tree.addProgressionRequirements(Progression.ENTER_END,Progression.ENTER_NETHER);
        tree.addProgressionRequirements(Progression.KILL_WITHER,Progression.ENTER_NETHER);
        tree.addProgressionRequirements(Progression.ACQUIRE_COLD_STAR,Progression.FIND_KEY_LOCK_DUNGEON,Progression.FIND_KEY_SOURCE);
        tree.addProgressionRequirements(Progression.RUNE_ENERGY_CLAIM,Progression.RUNE_ENERGY_PYLON);
        tree.addProgressionRequirements(Progression.SOLAR_RUNE,Progression.RUNE_ENERGY_PYLON);
        tree.addProgressionRequirements(Progression.KILL_DRAGON,Progression.ENTER_END);
        tree.addProgressionRequirements(Progression.IMBUED_COLD_STAR,Progression.KILL_WITHER,Progression.ACQUIRE_COLD_STAR,Progression.FIND_INFUSER_DUNGEON);
        tree.addProgressionRequirements(Progression.PYLON_INSCRIPTION,Progression.SOLAR_RUNE);
        tree.addProgressionRequirements(Progression.ALL_ENERGY_TYPES,Progression.RUNE_ENERGY_CLAIM);
        tree.addProgressionRequirements(Progression.CRAFT_SOLAR_FORGE,Progression.RUNE_ENERGY_CLAIM,Progression.KILL_DRAGON);
        tree.addProgressionRequirements(Progression.TRANSMUTE_GEM,Progression.IMBUED_COLD_STAR,Progression.TRADE_FOR_BLUE_GEM);
        tree.addProgressionRequirements(Progression.SOLAR_INFUSER,Progression.CRAFT_SOLAR_FORGE,Progression.INFUSING_CRAFTING_TABLE);
        tree.addProgressionRequirements(Progression.DIMENSIONAL_SHARD_DUNGEON,Progression.TRANSMUTE_GEM);
        tree.addProgressionRequirements(Progression.CRAFT_SOLAR_LENS,Progression.IMBUED_COLD_STAR,Progression.SOLAR_INFUSER,Progression.CATALYSTS);
        tree.addProgressionRequirements(Progression.CATALYSTS,Progression.SOLAR_INFUSER);
        tree.addProgressionRequirements(Progression.RUNIC_ENERGY_REPEATER,Progression.SOLAR_INFUSER,Progression.ALL_ENERGY_TYPES,Progression.RUNE_ENERGY_PYLON,Progression.CATALYSTS);
        tree.addProgressionRequirements(Progression.CRAFT_SOLAR_ENERGY_GENERATOR,Progression.CRAFT_SOLAR_LENS,Progression.RUNIC_ENERGY_REPEATER,Progression.GIANT_VAULT);
        tree.addProgressionRequirements(Progression.RADIANT_LAND,Progression.DIMENSIONAL_SHARD_DUNGEON,Progression.TRADE_FOR_BLUE_GEM,Progression.CRAFT_SOLAR_ENERGY_GENERATOR);
        tree.addProgressionRequirements(Progression.KILL_CRYSTAL_BOSS,Progression.RADIANT_LAND);
        tree.addProgressionRequirements(Progression.KILL_RUNIC_ELEMENTAL,Progression.KILL_CRYSTAL_BOSS);
        tree.addProgressionRequirements(Progression.CLEAR_WORLD,Progression.KILL_RUNIC_ELEMENTAL);

        return tree;
    }



    public Collection<Progression> getProgressionRequirements(Progression ach){
        if (PROGRESSION_TREE.containsKey(ach)){
            return PROGRESSION_TREE.get(ach).TO_UNLOCK_GENERAL_PROGRESSION;
        } else {
            return null;
        }
    }

    public Collection<Progression> getProgressionChildren(Progression ach){
        if (PROGRESSION_TREE.containsKey(ach)){
            return PROGRESSION_TREE.get(ach).FORWARD_PROGRESSIONS;
        } else {
            return null;
        }
    }

    private void addProgressionRequirements(Progression ach, List<Progression> requires) {
        if (PROGRESSION_TREE.containsKey(ach)){
            PROGRESSION_TREE.get(ach).TO_UNLOCK_GENERAL_PROGRESSION.addAll(requires);
            requires.clear();
        }else{
            requires.clear();
            System.out.println(("Cannot add progression "+requires.toString()+" because "+ach.getProgressionCode().toUpperCase()+" root doesnt exist"));
        }
    }

    private void addProgressionRequirements(Progression ach, Progression... progressions) {
        if (PROGRESSION_TREE.containsKey(ach)){
            for (Progression achi : progressions) {
                PROGRESSION_TREE.get(achi).FORWARD_PROGRESSIONS.add(ach);
                PROGRESSION_TREE.get(ach).TO_UNLOCK_GENERAL_PROGRESSION.add(achi);
            }

        }else{
            for (Progression achi : progressions) {
                System.out.println(("Cannot add progression "+achi.str+" because "+ach.getProgressionCode().toUpperCase()+" root doesnt exist"));
            }

        }
    }



    private void addGeneralAchievement(Progression progression){
        if (!PROGRESSION_TREE.containsKey(progression)){
            PROGRESSION_TREE.put(progression,new TreePart<>());
        }
    }

    public Progression getAchievementById(int id){
        for (Progression a : PROGRESSION_TREE.keySet()){
            if (a.getId() == id){
                return a;
            }
        }
        return Progression.SOLAR_INFUSER;
    }


    public static class TreePart<T>{

        public Collection<T> TO_UNLOCK_GENERAL_PROGRESSION = new ArrayList<>();
        public Set<Progression> FORWARD_PROGRESSIONS = new HashSet<>();

        TreePart(){

        }




    }
}

