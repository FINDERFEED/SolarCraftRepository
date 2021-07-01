package com.finderfeed.solarforge.solar_lexicon.achievements;

import com.finderfeed.solarforge.SolarForge;
import com.finderfeed.solarforge.registries.items.ItemsRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TranslationTextComponent;

public enum Achievement {
    CRAFT_SOLAR_FORGE("solar_forge_craft",1, SolarForge.SOLAR_FORGE_ITEM.get().getDefaultInstance(),new TranslationTextComponent("ach.solar_forge_craft"),1,new TranslationTextComponent("pre.solar_forge_craft")),
    FIND_SOLAR_STONE("solar_stone",1, ItemsRegister.SOLAR_STONE.get().getDefaultInstance(),new TranslationTextComponent("ach.solar_stone"),2,new TranslationTextComponent("pre.solar_stone")),
    CRAFT_SOLAR_INFUSER("solar_infuser_create",2,SolarForge.INFUSING_STAND_ITEM.get().getDefaultInstance(),new TranslationTextComponent("ach.solar_infuser_create"),3,new TranslationTextComponent("pre.solar_infuser_create")),
    USE_SOLAR_INFUSER("solar_infuser_use",3,ItemsRegister.SOLAR_WAND.get().getDefaultInstance(),new TranslationTextComponent("ach.solar_infuser_use"),4,new TranslationTextComponent("pre.solar_infuser_use")),
    ACQUIRE_SOLAR_DUST("acquire_solar_dust",2,ItemsRegister.SOLAR_DUST.get().getDefaultInstance(),new TranslationTextComponent("ach.acquire_solar_dust"),5,new TranslationTextComponent("pre.acquire_solar_dust")),
    FIND_KEY_LOCK_DUNGEON("find_key_lock",1,ItemsRegister.INVINCIBLE_STONE.get().getDefaultInstance(), new TranslationTextComponent("ach.key_lock_dungeon"),6,new TranslationTextComponent("pre.key_lock_dungeon")),
    FIND_INFUSER_DUNGEON("find_infuser_dungeon",1,ItemsRegister.SOLAR_STONE_COLLUMN.get().getDefaultInstance(), new TranslationTextComponent("ach.infuser_dungeon"),7,new TranslationTextComponent("pre.infuser_dungeon")),
    FIND_KEY_SOURCE("find_key_source",1,ItemsRegister.SOLAR_STONE_COLLUMN_HORIZONTAL.get().getDefaultInstance(), new TranslationTextComponent("ach.key_source"),8,new TranslationTextComponent("pre.key_source")),
    ACQUIRE_COLD_STAR("cold_star_piece",2,ItemsRegister.COLD_STAR_PIECE.get().getDefaultInstance(), new TranslationTextComponent("ach.cold_star_piece"),9,new TranslationTextComponent("pre.cold_star_piece")),
    ACQUIRE_COLD_STAR_ACTIVATED("cold_star_piece_activated",3,ItemsRegister.COLD_STAR_PIECE_ACTIVATED.get().getDefaultInstance(), new TranslationTextComponent("ach.cold_star_piece_activated"),10,new TranslationTextComponent("pre.cold_star_piece_activated")),
    CRAFT_SOLAR_LENS("solar_lens_craft",4,ItemsRegister.SOLAR_LENS.get().getDefaultInstance(), new TranslationTextComponent("ach.solar_lens_craft"),11,new TranslationTextComponent("pre.solar_lens_craft")),
    CRAFT_SOLAR_ENERGY_GENERATOR("energy_generator_craft",5,ItemsRegister.ENERGY_GENERATOR_BLOCK.get().getDefaultInstance(), new TranslationTextComponent("ach.solar_generator_craft"),12,new TranslationTextComponent("pre.solar_generator_craft")),
    FIND_INCINERATED_FOREST("find_incinerated_forest",1,ItemsRegister.BURNT_LOG.get().getDefaultInstance(), new TranslationTextComponent("ach.find_incinerated_forest"),13,new TranslationTextComponent("pre.find_incinerated_forest")),
    TRADE_FOR_BLUE_GEM("blue_gem_trade",1,ItemsRegister.BLUE_GEM.get().getDefaultInstance(), new TranslationTextComponent("ach.blue_gem_trade"),14,new TranslationTextComponent("pre.blue_gem_trade")),
    TRANSMUTE_GEM("transmute_gem",3,ItemsRegister.BLUE_GEM_ENCHANCED.get().getDefaultInstance(), new TranslationTextComponent("ach.transmute_gem"),15,new TranslationTextComponent("pre.transmute_gem")),
    DIMENSIONAL_SHARD_DUNGEON("dim_shard_dungeon",4,ItemsRegister.SOLAR_STONE_CHISELED.get().getDefaultInstance(), new TranslationTextComponent("ach.dim_shard_dungeon"),16,new TranslationTextComponent("pre.dim_shard_dungeon"));


    public static Achievement[] ALL_ACHIEVEMENTS = {
            CRAFT_SOLAR_FORGE,
            FIND_SOLAR_STONE,
            CRAFT_SOLAR_INFUSER,
            USE_SOLAR_INFUSER,
            ACQUIRE_SOLAR_DUST,
            FIND_KEY_LOCK_DUNGEON,
            FIND_INFUSER_DUNGEON,
            FIND_KEY_SOURCE,
            ACQUIRE_COLD_STAR,
            ACQUIRE_COLD_STAR_ACTIVATED,
            CRAFT_SOLAR_LENS,
            CRAFT_SOLAR_ENERGY_GENERATOR,
            FIND_INCINERATED_FOREST,
            TRADE_FOR_BLUE_GEM,
            TRANSMUTE_GEM,
            DIMENSIONAL_SHARD_DUNGEON
    };

    public static Achievement getAchievementByName(String name){
        for (Achievement a : ALL_ACHIEVEMENTS){
            if (a.str.equals(name)){
                return a;
            }
        }
        return null;
    }

    public final String str;
    public final int tier;
    public final ItemStack icon;
    public final TranslationTextComponent translation;
    public final int id;
    public final TranslationTextComponent pretext;
    Achievement(String str, int tier, ItemStack icon, TranslationTextComponent translation, int id, TranslationTextComponent pretext) {
        this.str = str;
        this.tier = tier;
        this.icon = icon;
        this.translation = translation;
        this.id = id;
        this.pretext = pretext;
    }

    public TranslationTextComponent getPretext() {
        return pretext; }

    public String getAchievementCode(){
        return this.str;
    }
    public int getAchievementTier(){
        return this.tier;
    }
    public ItemStack getIcon() {
        return icon;
    }

    public int getId() {
        return id; }

    public TranslationTextComponent getTranslation() {
        return translation;
    }


}
