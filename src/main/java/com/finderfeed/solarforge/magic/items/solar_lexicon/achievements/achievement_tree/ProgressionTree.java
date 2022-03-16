package com.finderfeed.solarforge.magic.items.solar_lexicon.achievements.achievement_tree;

import com.finderfeed.solarforge.magic.items.solar_lexicon.achievements.Progression;

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
        tree.addAchievementRequirements(Progression.ENTER_END,Progression.ENTER_NETHER);
        tree.addAchievementRequirements(Progression.KILL_WITHER,Progression.ENTER_NETHER);
        tree.addAchievementRequirements(Progression.ACQUIRE_COLD_STAR,Progression.FIND_KEY_LOCK_DUNGEON,Progression.FIND_KEY_SOURCE);
        tree.addAchievementRequirements(Progression.RUNE_ENERGY_CLAIM,Progression.RUNE_ENERGY_PYLON);
        tree.addAchievementRequirements(Progression.SOLAR_RUNE,Progression.RUNE_ENERGY_PYLON);
        tree.addAchievementRequirements(Progression.KILL_DRAGON,Progression.ENTER_END);
        tree.addAchievementRequirements(Progression.IMBUED_COLD_STAR,Progression.KILL_WITHER,Progression.ACQUIRE_COLD_STAR,Progression.FIND_INFUSER_DUNGEON);
        tree.addAchievementRequirements(Progression.PYLON_INSCRIPTION,Progression.SOLAR_RUNE);
        tree.addAchievementRequirements(Progression.ALL_ENERGY_TYPES,Progression.RUNE_ENERGY_PYLON);
        tree.addAchievementRequirements(Progression.CRAFT_SOLAR_FORGE,Progression.RUNE_ENERGY_CLAIM,Progression.KILL_DRAGON);
        tree.addAchievementRequirements(Progression.TRANSMUTE_GEM,Progression.FIND_INCINERATED_FOREST,Progression.IMBUED_COLD_STAR,Progression.TRADE_FOR_BLUE_GEM);
        tree.addAchievementRequirements(Progression.SOLAR_INFUSER,Progression.CRAFT_SOLAR_FORGE,Progression.INFUSING_CRAFTING_TABLE);
        tree.addAchievementRequirements(Progression.DIMENSIONAL_SHARD_DUNGEON,Progression.TRANSMUTE_GEM);
        tree.addAchievementRequirements(Progression.CRAFT_SOLAR_LENS,Progression.IMBUED_COLD_STAR,Progression.SOLAR_INFUSER,Progression.CATALYSTS);
        tree.addAchievementRequirements(Progression.CATALYSTS,Progression.SOLAR_INFUSER);
        tree.addAchievementRequirements(Progression.RUNIC_ENERGY_REPEATER,Progression.SOLAR_INFUSER,Progression.ALL_ENERGY_TYPES,Progression.RUNE_ENERGY_PYLON,Progression.CATALYSTS);
        tree.addAchievementRequirements(Progression.CRAFT_SOLAR_ENERGY_GENERATOR,Progression.CRAFT_SOLAR_LENS,Progression.RUNIC_ENERGY_REPEATER);
        tree.addAchievementRequirements(Progression.RADIANT_LAND,Progression.DIMENSIONAL_SHARD_DUNGEON,Progression.TRADE_FOR_BLUE_GEM,Progression.CRAFT_SOLAR_ENERGY_GENERATOR);
        tree.addAchievementRequirements(Progression.KILL_CRYSTAL_BOSS,Progression.RADIANT_LAND);
        tree.addAchievementRequirements(Progression.KILL_RUNIC_ELEMENTAL,Progression.KILL_CRYSTAL_BOSS);

        return tree;
    }



    public Collection<Progression> getAchievementRequirements(Progression ach){
        if (PROGRESSION_TREE.containsKey(ach)){
            return PROGRESSION_TREE.get(ach).TO_UNLOCK_GENERAL_ACHIEVEMENT;
        } else {
            return null;
        }
    }

    private void addAchievementRequirements(Progression ach, List<Progression> requires) {
        if (PROGRESSION_TREE.containsKey(ach)){
            PROGRESSION_TREE.get(ach).TO_UNLOCK_GENERAL_ACHIEVEMENT.addAll(requires);
            requires.clear();
        }else{
            requires.clear();
            System.out.println(("Cannot add achivement "+requires.toString()+" because "+ach.getAchievementCode().toUpperCase()+" root doesnt exist"));
        }
    }

    private void addAchievementRequirements(Progression ach, Progression... progressions) {
        if (PROGRESSION_TREE.containsKey(ach)){
            for (Progression achi : progressions) {
                PROGRESSION_TREE.get(ach).TO_UNLOCK_GENERAL_ACHIEVEMENT.add(achi);
            }

        }else{
            for (Progression achi : progressions) {
                System.out.println(("Cannot add achivement "+achi.str+" because "+ach.getAchievementCode().toUpperCase()+" root doesnt exist"));
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


}
class TreePart<T>{

    public Collection<T> TO_UNLOCK_GENERAL_ACHIEVEMENT = new ArrayList<>();

    TreePart(){

    }




}
