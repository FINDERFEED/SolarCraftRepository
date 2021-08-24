package com.finderfeed.solarforge.magic_items.items.solar_lexicon.achievements;

import com.finderfeed.solarforge.SolarForge;
import com.finderfeed.solarforge.registries.items.ItemsRegister;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.chat.TranslatableComponent;

public enum Achievement {
    RUNE_ENERGY_CLAIM("rune_energy_claim",2, ItemsRegister.SOLAR_WAND.get().getDefaultInstance(),new TranslatableComponent("ach.rune_energy_claim"),2,new TranslatableComponent("pre.rune_energy_claim"),new TranslatableComponent("aft.rune_energy_claim")),
    RUNE_ENERGY_DEPOSIT("rune_energy_deposit",1, ItemsRegister.RUNE_ENERGY_PYLON.get().getDefaultInstance(),new TranslatableComponent("ach.rune_energy_deposit"),3,new TranslatableComponent("pre.rune_energy_deposit"),new TranslatableComponent("aft.rune_energy_deposit")),
    SOLAR_RUNE("solar_rune",2, ItemsRegister.SOLAR_RUNE_ZETA.get().getDefaultInstance(),new TranslatableComponent("ach.solar_rune"),4,new TranslatableComponent("pre.solar_rune"),new TranslatableComponent("aft.solar_rune")),
    ENTER_NETHER("enter_nether",1, Blocks.NETHERRACK.asItem().getDefaultInstance(),new TranslatableComponent("ach.enter_nether"),5,new TranslatableComponent("pre.enter_nether"),new TranslatableComponent("aft.enter_nether")),
    CRAFT_SOLAR_FORGE("solar_forge_craft",2, SolarForge.SOLAR_FORGE_ITEM.get().getDefaultInstance(),new TranslatableComponent("ach.solar_forge_craft"),6,new TranslatableComponent("pre.solar_forge_craft"),new TranslatableComponent("aft.solar_forge_craft")),
    FIND_SOLAR_STONE("solar_stone",2, ItemsRegister.SOLAR_STONE.get().getDefaultInstance(),new TranslatableComponent("ach.solar_stone"),7,new TranslatableComponent("pre.solar_stone"),new TranslatableComponent("aft.solar_stone")),
    CRAFT_SOLAR_INFUSER("solar_infuser_create",3,SolarForge.INFUSING_STAND_ITEM.get().getDefaultInstance(),new TranslatableComponent("ach.solar_infuser_create"),8,new TranslatableComponent("pre.solar_infuser_create"),new TranslatableComponent("aft.solar_infuser_create")),
    USE_SOLAR_INFUSER("solar_infuser_use",4,ItemsRegister.SOLAR_WAND.get().getDefaultInstance(),new TranslatableComponent("ach.solar_infuser_use"),9,new TranslatableComponent("pre.solar_infuser_use"),new TranslatableComponent("aft.solar_infuser_use")),
    ACQUIRE_SOLAR_DUST("acquire_solar_dust",3,ItemsRegister.SOLAR_DUST.get().getDefaultInstance(),new TranslatableComponent("ach.acquire_solar_dust"),10,new TranslatableComponent("pre.acquire_solar_dust"),new TranslatableComponent("aft.acquire_solar_dust")),
    FIND_KEY_LOCK_DUNGEON("find_key_lock",1,ItemsRegister.INVINCIBLE_STONE.get().getDefaultInstance(), new TranslatableComponent("ach.key_lock_dungeon"),11,new TranslatableComponent("pre.key_lock_dungeon"),new TranslatableComponent("aft.key_lock_dungeon")),
    FIND_INFUSER_DUNGEON("find_infuser_dungeon",1,ItemsRegister.SOLAR_STONE_COLLUMN.get().getDefaultInstance(), new TranslatableComponent("ach.infuser_dungeon"),12,new TranslatableComponent("pre.infuser_dungeon"),new TranslatableComponent("aft.infuser_dungeon")),
    FIND_KEY_SOURCE("find_key_source",1,ItemsRegister.SOLAR_STONE_COLLUMN_HORIZONTAL.get().getDefaultInstance(), new TranslatableComponent("ach.key_source"),13,new TranslatableComponent("pre.key_source"),new TranslatableComponent("aft.key_source")),
    ACQUIRE_COLD_STAR("cold_star_piece",3,ItemsRegister.COLD_STAR_PIECE.get().getDefaultInstance(), new TranslatableComponent("ach.cold_star_piece"),14,new TranslatableComponent("pre.cold_star_piece"),new TranslatableComponent("aft.cold_star_piece")),
    ACQUIRE_COLD_STAR_ACTIVATED("cold_star_piece_activated",4,ItemsRegister.COLD_STAR_PIECE_ACTIVATED.get().getDefaultInstance(), new TranslatableComponent("ach.cold_star_piece_activated"),15,new TranslatableComponent("pre.cold_star_piece_activated"),new TranslatableComponent("aft.cold_star_piece_activated")),
    CRAFT_SOLAR_LENS("solar_lens_craft",5,ItemsRegister.SOLAR_LENS.get().getDefaultInstance(), new TranslatableComponent("ach.solar_lens_craft"),16,new TranslatableComponent("pre.solar_lens_craft"),new TranslatableComponent("aft.solar_lens_craft")),
    CRAFT_SOLAR_ENERGY_GENERATOR("energy_generator_craft",6,ItemsRegister.ENERGY_GENERATOR_BLOCK.get().getDefaultInstance(), new TranslatableComponent("ach.solar_generator_craft"),17,new TranslatableComponent("pre.solar_generator_craft"),new TranslatableComponent("aft.solar_generator_craft")),
    FIND_INCINERATED_FOREST("find_incinerated_forest",1,ItemsRegister.BURNT_LOG.get().getDefaultInstance(), new TranslatableComponent("ach.find_incinerated_forest"),18,new TranslatableComponent("pre.find_incinerated_forest"),new TranslatableComponent("aft.find_incinerated_forest")),
    TRADE_FOR_BLUE_GEM("blue_gem_trade",1,ItemsRegister.BLUE_GEM.get().getDefaultInstance(), new TranslatableComponent("ach.blue_gem_trade"),19,new TranslatableComponent("pre.blue_gem_trade"),new TranslatableComponent("aft.blue_gem_trade")),
    TRANSMUTE_GEM("transmute_gem",4,ItemsRegister.BLUE_GEM_ENCHANCED.get().getDefaultInstance(), new TranslatableComponent("ach.transmute_gem"),20,new TranslatableComponent("pre.transmute_gem"),new TranslatableComponent("aft.transmute_gem")),
    DIMENSIONAL_SHARD_DUNGEON("dim_shard_dungeon",5,ItemsRegister.SOLAR_STONE_CHISELED.get().getDefaultInstance(), new TranslatableComponent("ach.dim_shard_dungeon"),21,new TranslatableComponent("pre.dim_shard_dungeon"),new TranslatableComponent("aft.dim_shard_dungeon")),
    RUNIC_ENERGY_REPEATER("runic_energy_repeater",5,ItemsRegister.REPEATER.get().getDefaultInstance(), new TranslatableComponent("ach.runic_energy_repeater"),22,new TranslatableComponent("pre.runic_energy_repeater"),new TranslatableComponent("aft.runic_energy_repeater")),
    DIMENSION_CORE("dimension_core",7,ItemsRegister.DIMENSION_CORE.get().getDefaultInstance(), new TranslatableComponent("ach.dimension_core"),23,new TranslatableComponent("pre.dimension_core"),new TranslatableComponent("aft.dimension_core"));



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
    public final TranslatableComponent translation;
    public final int id;
    public final TranslatableComponent pretext;
    public final TranslatableComponent afterText;
    Achievement(String str, int tier, ItemStack icon, TranslatableComponent translation, int id, TranslatableComponent pretext,TranslatableComponent after) {
        this.str = str;
        this.tier = tier;
        this.icon = icon;
        this.translation = translation;
        this.id = id;
        this.pretext = pretext;
        this.afterText = after;
    }

    public TranslatableComponent getPretext() {
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

    public TranslatableComponent getTranslation() {
        return translation;
    }


}
