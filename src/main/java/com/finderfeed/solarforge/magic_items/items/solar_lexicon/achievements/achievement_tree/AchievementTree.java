package com.finderfeed.solarforge.magic_items.items.solar_lexicon.achievements.achievement_tree;

import com.finderfeed.solarforge.magic_items.items.solar_lexicon.achievements.Achievement;

import java.util.*;

public class AchievementTree {

    public HashMap<Achievement,TreePart<Achievement>> ACHIEVEMENT_TREE = new HashMap<>();

    public AchievementTree(){

    }


    public static AchievementTree loadTree(){
        AchievementTree tree = new AchievementTree();
//        tree.addGeneralAchievement(Achievements.CRAFT_SOLAR_FORGE);
//        tree.addGeneralAchievement(Achievements.CRAFT_SOLAR_INFUSER);
//        tree.addGeneralAchievement(Achievements.FIND_SOLAR_STONE);
//        tree.addGeneralAchievement(Achievements.USE_SOLAR_INFUSER);
//        tree.addGeneralAchievement(Achievements.ACQUIRE_SOLAR_DUST);

        for (Achievement a : Achievement.ALL_ACHIEVEMENTS){
            tree.addGeneralAchievement(a);
        }


        List<Achievement> list = new ArrayList<>(0);
        list.add(Achievement.FIND_SOLAR_STONE);
        tree.addAchievementRequirements(Achievement.CRAFT_SOLAR_INFUSER,list);


        list.add(Achievement.ACQUIRE_SOLAR_DUST);
        list.add(Achievement.CRAFT_SOLAR_INFUSER);
        tree.addAchievementRequirements(Achievement.USE_SOLAR_INFUSER,list);


        list.add(Achievement.CRAFT_SOLAR_FORGE);
        tree.addAchievementRequirements(Achievement.ACQUIRE_SOLAR_DUST,list);

        list.add(Achievement.FIND_KEY_SOURCE);
        list.add(Achievement.FIND_INFUSER_DUNGEON);
        list.add(Achievement.FIND_KEY_LOCK_DUNGEON);
        tree.addAchievementRequirements(Achievement.ACQUIRE_COLD_STAR,list);

        list.add(Achievement.ACQUIRE_COLD_STAR);
        list.add(Achievement.FIND_INFUSER_DUNGEON);
        tree.addAchievementRequirements(Achievement.ACQUIRE_COLD_STAR_ACTIVATED,list);

        list.add(Achievement.ACQUIRE_COLD_STAR_ACTIVATED);
        list.add(Achievement.USE_SOLAR_INFUSER);
        tree.addAchievementRequirements(Achievement.CRAFT_SOLAR_LENS,list);

        list.add(Achievement.CRAFT_SOLAR_LENS);
        list.add(Achievement.TRANSMUTE_GEM);
        tree.addAchievementRequirements(Achievement.CRAFT_SOLAR_ENERGY_GENERATOR,list);

        list.add(Achievement.FIND_INCINERATED_FOREST);
        list.add(Achievement.TRADE_FOR_BLUE_GEM);
        tree.addAchievementRequirements(Achievement.TRANSMUTE_GEM,list);


        list.add(Achievement.TRANSMUTE_GEM);
        tree.addAchievementRequirements(Achievement.DIMENSIONAL_SHARD_DUNGEON,list);
        return tree;
    }



    public Collection<Achievement> getAchievementRequirements(Achievement ach){
        if (ACHIEVEMENT_TREE.containsKey(ach)){
            return ACHIEVEMENT_TREE.get(ach).TO_UNLOCK_GENERAL_ACHIEVEMENT;
        } else {
            return null;
        }
    }

    private void addAchievementRequirements(Achievement ach, List<Achievement> requires) {
        if (ACHIEVEMENT_TREE.containsKey(ach)){
            ACHIEVEMENT_TREE.get(ach).TO_UNLOCK_GENERAL_ACHIEVEMENT.addAll(requires);
            requires.clear();
        }else{
            requires.clear();
            System.out.println(("Cannot add achivement "+requires.toString()+" because "+ach.getAchievementCode().toUpperCase()+" root doesnt exist"));
        }
    }

    private void addAchievementRequirements(Achievement ach, Achievement... achievements) {
        if (ACHIEVEMENT_TREE.containsKey(ach)){
            for (Achievement achi : achievements) {
                ACHIEVEMENT_TREE.get(ach).TO_UNLOCK_GENERAL_ACHIEVEMENT.add(achi);
            }

        }else{
            for (Achievement achi : achievements) {
                System.out.println(("Cannot add achivement "+achi.str+" because "+ach.getAchievementCode().toUpperCase()+" root doesnt exist"));
            }

        }
    }



    private void addGeneralAchievement(Achievement achievement){
        if (!ACHIEVEMENT_TREE.containsKey(achievement)){
            ACHIEVEMENT_TREE.put(achievement,new TreePart<>());
        }
    }

    public Achievement getAchievementById(int id){
        for (Achievement a : ACHIEVEMENT_TREE.keySet()){
            if (a.getId() == id){
                return a;
            }
        }
        return Achievement.CRAFT_SOLAR_INFUSER;
    }


}
class TreePart<T>{

    public Collection<T> TO_UNLOCK_GENERAL_ACHIEVEMENT = new ArrayList<>();

    TreePart(){

    }




}
