package com.finderfeed.solarforge.magic_items.items.solar_lexicon.achievements.achievement_tree;

import com.finderfeed.solarforge.magic_items.items.solar_lexicon.achievements.Progression;

import java.util.*;

public class AchievementTree {

    public static final AchievementTree INSTANCE = loadTree();

    public HashMap<Progression,TreePart<Progression>> ACHIEVEMENT_TREE = new HashMap<>();

    public AchievementTree(){

    }


    public static AchievementTree loadTree(){
        AchievementTree tree = new AchievementTree();
//        tree.addGeneralAchievement(Achievements.CRAFT_SOLAR_FORGE);
//        tree.addGeneralAchievement(Achievements.CRAFT_SOLAR_INFUSER);
//        tree.addGeneralAchievement(Achievements.FIND_SOLAR_STONE);
//        tree.addGeneralAchievement(Achievements.USE_SOLAR_INFUSER);
//        tree.addGeneralAchievement(Achievements.ACQUIRE_SOLAR_DUST);

        for (Progression a : Progression.allProgressions){
            tree.addGeneralAchievement(a);
        }


        List<Progression> list = new ArrayList<>(0);
//        list.add(Progression.FIND_SOLAR_STONE);
//        tree.addAchievementRequirements(Progression.CRAFT_SOLAR_INFUSER,list);


        list.add(Progression.ACQUIRE_SOLAR_DUST);
        list.add(Progression.CRAFT_SOLAR_INFUSER);
        tree.addAchievementRequirements(Progression.USE_SOLAR_INFUSER,list);


        list.add(Progression.CRAFT_SOLAR_FORGE);
        tree.addAchievementRequirements(Progression.ACQUIRE_SOLAR_DUST,list);

        list.add(Progression.FIND_KEY_SOURCE);
        list.add(Progression.FIND_INFUSER_DUNGEON);
        list.add(Progression.FIND_KEY_LOCK_DUNGEON);
        tree.addAchievementRequirements(Progression.ACQUIRE_COLD_STAR,list);

        list.add(Progression.ACQUIRE_COLD_STAR);
        list.add(Progression.FIND_INFUSER_DUNGEON);
        tree.addAchievementRequirements(Progression.ACQUIRE_COLD_STAR_ACTIVATED,list);

        list.add(Progression.ACQUIRE_COLD_STAR_ACTIVATED);
        list.add(Progression.USE_SOLAR_INFUSER);
        tree.addAchievementRequirements(Progression.CRAFT_SOLAR_LENS,list);

        list.add(Progression.CRAFT_SOLAR_LENS);
        list.add(Progression.TRANSMUTE_GEM);
        list.add(Progression.RUNIC_ENERGY_REPEATER);
        tree.addAchievementRequirements(Progression.CRAFT_SOLAR_ENERGY_GENERATOR,list);

        list.add(Progression.FIND_INCINERATED_FOREST);
        list.add(Progression.TRADE_FOR_BLUE_GEM);
        tree.addAchievementRequirements(Progression.TRANSMUTE_GEM,list);


        list.add(Progression.TRANSMUTE_GEM);
        tree.addAchievementRequirements(Progression.DIMENSIONAL_SHARD_DUNGEON,list);

        tree.addAchievementRequirements(Progression.CRAFT_SOLAR_FORGE, Progression.ENTER_NETHER);
//        tree.addAchievementRequirements(Progression.FIND_SOLAR_STONE, Progression.ENTER_NETHER);
        tree.addAchievementRequirements(Progression.RUNE_ENERGY_CLAIM, Progression.RUNE_ENERGY_DEPOSIT);
        tree.addAchievementRequirements(Progression.SOLAR_RUNE, Progression.RUNE_ENERGY_DEPOSIT);
        tree.addAchievementRequirements(Progression.RUNIC_ENERGY_REPEATER, Progression.RUNE_ENERGY_DEPOSIT, Progression.USE_SOLAR_INFUSER, Progression.ACQUIRE_COLD_STAR_ACTIVATED);
        tree.addAchievementRequirements(Progression.DIMENSION_CORE, Progression.CRAFT_SOLAR_ENERGY_GENERATOR, Progression.DIMENSIONAL_SHARD_DUNGEON);
        tree.addAchievementRequirements(Progression.KILL_CRYSTAL_BOSS, Progression.DIMENSION_CORE);
        return tree;
    }



    public Collection<Progression> getAchievementRequirements(Progression ach){
        if (ACHIEVEMENT_TREE.containsKey(ach)){
            return ACHIEVEMENT_TREE.get(ach).TO_UNLOCK_GENERAL_ACHIEVEMENT;
        } else {
            return null;
        }
    }

    private void addAchievementRequirements(Progression ach, List<Progression> requires) {
        if (ACHIEVEMENT_TREE.containsKey(ach)){
            ACHIEVEMENT_TREE.get(ach).TO_UNLOCK_GENERAL_ACHIEVEMENT.addAll(requires);
            requires.clear();
        }else{
            requires.clear();
            System.out.println(("Cannot add achivement "+requires.toString()+" because "+ach.getAchievementCode().toUpperCase()+" root doesnt exist"));
        }
    }

    private void addAchievementRequirements(Progression ach, Progression... progressions) {
        if (ACHIEVEMENT_TREE.containsKey(ach)){
            for (Progression achi : progressions) {
                ACHIEVEMENT_TREE.get(ach).TO_UNLOCK_GENERAL_ACHIEVEMENT.add(achi);
            }

        }else{
            for (Progression achi : progressions) {
                System.out.println(("Cannot add achivement "+achi.str+" because "+ach.getAchievementCode().toUpperCase()+" root doesnt exist"));
            }

        }
    }



    private void addGeneralAchievement(Progression progression){
        if (!ACHIEVEMENT_TREE.containsKey(progression)){
            ACHIEVEMENT_TREE.put(progression,new TreePart<>());
        }
    }

    public Progression getAchievementById(int id){
        for (Progression a : ACHIEVEMENT_TREE.keySet()){
            if (a.getId() == id){
                return a;
            }
        }
        return Progression.CRAFT_SOLAR_INFUSER;
    }


}
class TreePart<T>{

    public Collection<T> TO_UNLOCK_GENERAL_ACHIEVEMENT = new ArrayList<>();

    TreePart(){

    }




}
