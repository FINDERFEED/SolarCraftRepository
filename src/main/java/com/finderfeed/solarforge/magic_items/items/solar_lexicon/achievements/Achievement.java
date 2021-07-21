package com.finderfeed.solarforge.magic_items.items.solar_lexicon.achievements;

import com.finderfeed.solarforge.SolarForge;
import com.finderfeed.solarforge.registries.items.ItemsRegister;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TranslationTextComponent;

public enum Achievement {
    RUNE_ENERGY_CLAIM("rune_energy_claim",2, ItemsRegister.SOLAR_WAND.get().getDefaultInstance(),new TranslationTextComponent("ach.rune_energy_claim"),2,new TranslationTextComponent("pre.rune_energy_claim")),
    RUNE_ENERGY_DEPOSIT("rune_energy_deposit",1, ItemsRegister.RUNE_ENERGY_PYLON.get().getDefaultInstance(),new TranslationTextComponent("ach.rune_energy_deposit"),3,new TranslationTextComponent("pre.rune_energy_deposit")),
    SOLAR_RUNE("solar_rune",2, ItemsRegister.SOLAR_RUNE_ZETA.get().getDefaultInstance(),new TranslationTextComponent("ach.solar_rune"),4,new TranslationTextComponent("pre.solar_rune")),
    ENTER_NETHER("enter_nether",1, Blocks.NETHERRACK.asItem().getDefaultInstance(),new TranslationTextComponent("ach.enter_nether"),5,new TranslationTextComponent("pre.enter_nether")),
    CRAFT_SOLAR_FORGE("solar_forge_craft",2, SolarForge.SOLAR_FORGE_ITEM.get().getDefaultInstance(),new TranslationTextComponent("ach.solar_forge_craft"),6,new TranslationTextComponent("pre.solar_forge_craft")),
    FIND_SOLAR_STONE("solar_stone",2, ItemsRegister.SOLAR_STONE.get().getDefaultInstance(),new TranslationTextComponent("ach.solar_stone"),7,new TranslationTextComponent("pre.solar_stone")),
    CRAFT_SOLAR_INFUSER("solar_infuser_create",3,SolarForge.INFUSING_STAND_ITEM.get().getDefaultInstance(),new TranslationTextComponent("ach.solar_infuser_create"),8,new TranslationTextComponent("pre.solar_infuser_create")),
    USE_SOLAR_INFUSER("solar_infuser_use",4,ItemsRegister.SOLAR_WAND.get().getDefaultInstance(),new TranslationTextComponent("ach.solar_infuser_use"),9,new TranslationTextComponent("pre.solar_infuser_use")),
    ACQUIRE_SOLAR_DUST("acquire_solar_dust",3,ItemsRegister.SOLAR_DUST.get().getDefaultInstance(),new TranslationTextComponent("ach.acquire_solar_dust"),10,new TranslationTextComponent("pre.acquire_solar_dust")),
    FIND_KEY_LOCK_DUNGEON("find_key_lock",1,ItemsRegister.INVINCIBLE_STONE.get().getDefaultInstance(), new TranslationTextComponent("ach.key_lock_dungeon"),11,new TranslationTextComponent("pre.key_lock_dungeon")),
    FIND_INFUSER_DUNGEON("find_infuser_dungeon",1,ItemsRegister.SOLAR_STONE_COLLUMN.get().getDefaultInstance(), new TranslationTextComponent("ach.infuser_dungeon"),12,new TranslationTextComponent("pre.infuser_dungeon")),
    FIND_KEY_SOURCE("find_key_source",1,ItemsRegister.SOLAR_STONE_COLLUMN_HORIZONTAL.get().getDefaultInstance(), new TranslationTextComponent("ach.key_source"),13,new TranslationTextComponent("pre.key_source")),
    ACQUIRE_COLD_STAR("cold_star_piece",3,ItemsRegister.COLD_STAR_PIECE.get().getDefaultInstance(), new TranslationTextComponent("ach.cold_star_piece"),14,new TranslationTextComponent("pre.cold_star_piece")),
    ACQUIRE_COLD_STAR_ACTIVATED("cold_star_piece_activated",4,ItemsRegister.COLD_STAR_PIECE_ACTIVATED.get().getDefaultInstance(), new TranslationTextComponent("ach.cold_star_piece_activated"),15,new TranslationTextComponent("pre.cold_star_piece_activated")),
    CRAFT_SOLAR_LENS("solar_lens_craft",5,ItemsRegister.SOLAR_LENS.get().getDefaultInstance(), new TranslationTextComponent("ach.solar_lens_craft"),16,new TranslationTextComponent("pre.solar_lens_craft")),
    CRAFT_SOLAR_ENERGY_GENERATOR("energy_generator_craft",6,ItemsRegister.ENERGY_GENERATOR_BLOCK.get().getDefaultInstance(), new TranslationTextComponent("ach.solar_generator_craft"),17,new TranslationTextComponent("pre.solar_generator_craft")),
    FIND_INCINERATED_FOREST("find_incinerated_forest",1,ItemsRegister.BURNT_LOG.get().getDefaultInstance(), new TranslationTextComponent("ach.find_incinerated_forest"),18,new TranslationTextComponent("pre.find_incinerated_forest")),
    TRADE_FOR_BLUE_GEM("blue_gem_trade",1,ItemsRegister.BLUE_GEM.get().getDefaultInstance(), new TranslationTextComponent("ach.blue_gem_trade"),19,new TranslationTextComponent("pre.blue_gem_trade")),
    TRANSMUTE_GEM("transmute_gem",4,ItemsRegister.BLUE_GEM_ENCHANCED.get().getDefaultInstance(), new TranslationTextComponent("ach.transmute_gem"),20,new TranslationTextComponent("pre.transmute_gem")),
    DIMENSIONAL_SHARD_DUNGEON("dim_shard_dungeon",5,ItemsRegister.SOLAR_STONE_CHISELED.get().getDefaultInstance(), new TranslationTextComponent("ach.dim_shard_dungeon"),21,new TranslationTextComponent("pre.dim_shard_dungeon"));



    public static Achievement[] ALL_ACHIEVEMENTS = Achievement.class.getEnumConstants();

    public static Achievement[] getAllAchievements(){
        return Achievement.class.getEnumConstants();
    }

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
