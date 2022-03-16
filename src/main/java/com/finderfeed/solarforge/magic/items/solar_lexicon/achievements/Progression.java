package com.finderfeed.solarforge.magic.items.solar_lexicon.achievements;

import com.finderfeed.solarforge.SolarForge;
import com.finderfeed.solarforge.registries.blocks.BlocksRegistry;
import com.finderfeed.solarforge.registries.items.ItemsRegister;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.chat.TranslatableComponent;

public enum Progression {
    ENTER_NETHER("enter_nether",1, Blocks.NETHERRACK.asItem().getDefaultInstance(),new TranslatableComponent("ach.enter_nether"),nextID(),new TranslatableComponent("pre.enter_nether"),new TranslatableComponent("aft.enter_nether")),
    FIND_INCINERATED_FOREST("find_incinerated_forest",1,ItemsRegister.BURNT_LOG.get().getDefaultInstance(), new TranslatableComponent("ach.find_incinerated_forest"),nextID(),new TranslatableComponent("pre.find_incinerated_forest"),new TranslatableComponent("aft.find_incinerated_forest")),
    FIND_KEY_LOCK_DUNGEON("find_key_lock",1,ItemsRegister.INVINCIBLE_STONE.get().getDefaultInstance(), new TranslatableComponent("ach.key_lock_dungeon"),nextID(),new TranslatableComponent("pre.key_lock_dungeon"),new TranslatableComponent("aft.key_lock_dungeon")),
    FIND_INFUSER_DUNGEON("find_infuser_dungeon",1,ItemsRegister.SOLAR_STONE_COLLUMN.get().getDefaultInstance(), new TranslatableComponent("ach.infuser_dungeon"),nextID(),new TranslatableComponent("pre.infuser_dungeon"),new TranslatableComponent("aft.infuser_dungeon")),
    FIND_KEY_SOURCE("find_key_source",1,ItemsRegister.SOLAR_STONE_COLLUMN_HORIZONTAL.get().getDefaultInstance(), new TranslatableComponent("ach.key_source"),nextID(),new TranslatableComponent("pre.key_source"),new TranslatableComponent("aft.key_source")),
    RUNE_ENERGY_PYLON("rune_energy_deposit",1, ItemsRegister.RUNE_ENERGY_PYLON.get().getDefaultInstance(),new TranslatableComponent("ach.rune_energy_deposit"),nextID(),new TranslatableComponent("pre.rune_energy_deposit"),new TranslatableComponent("aft.rune_energy_deposit")),
    TRADE_FOR_BLUE_GEM("blue_gem_trade",1,ItemsRegister.BLUE_GEM.get().getDefaultInstance(), new TranslatableComponent("ach.blue_gem_trade"),nextID(),new TranslatableComponent("pre.blue_gem_trade"),new TranslatableComponent("aft.blue_gem_trade")),
    INFUSING_CRAFTING_TABLE("infusing_crafting_table",1,ItemsRegister.INFUSING_TABLE.get().getDefaultInstance(), new TranslatableComponent("ach.infusing_crafting_table"),nextID(),new TranslatableComponent("pre.infusing_crafting_table"),new TranslatableComponent("aft.infusing_crafting_table")),


    ENTER_END("enter_end",2, Blocks.END_STONE.asItem().getDefaultInstance(),new TranslatableComponent("ach.enter_end"),nextID(),new TranslatableComponent("pre.enter_end"),new TranslatableComponent("aft.enter_end")),
    KILL_WITHER("kill_wither",2, Blocks.WITHER_SKELETON_SKULL.asItem().getDefaultInstance(),new TranslatableComponent("ach.kill_wither"),nextID(),new TranslatableComponent("pre.kill_wither"),new TranslatableComponent("aft.kill_wither")),
    ACQUIRE_COLD_STAR("cold_star_piece",2,ItemsRegister.COLD_STAR_PIECE.get().getDefaultInstance(), new TranslatableComponent("ach.cold_star_piece"),nextID(),new TranslatableComponent("pre.cold_star_piece"),new TranslatableComponent("aft.cold_star_piece")),
    RUNE_ENERGY_CLAIM("rune_energy_claim",2, ItemsRegister.SOLAR_WAND.get().getDefaultInstance(),new TranslatableComponent("ach.rune_energy_claim"),nextID(),new TranslatableComponent("pre.rune_energy_claim"),new TranslatableComponent("aft.rune_energy_claim")),
    SOLAR_RUNE("solar_rune",2, ItemsRegister.SOLAR_RUNE_ZETA.get().getDefaultInstance(),new TranslatableComponent("ach.solar_rune"),nextID(),new TranslatableComponent("pre.solar_rune"),new TranslatableComponent("aft.solar_rune")),
    ALL_ENERGY_TYPES("all_energy_types",2, ItemsRegister.RUNE_ENERGY_PYLON.get().getDefaultInstance(),new TranslatableComponent("ach.all_energy_types"),nextID(),new TranslatableComponent("pre.all_energy_types"),new TranslatableComponent("aft.all_energy_types")),


    KILL_DRAGON("kill_dragon",3, Blocks.DRAGON_HEAD.asItem().getDefaultInstance(),new TranslatableComponent("ach.kill_dragon"),nextID(),new TranslatableComponent("pre.kill_dragon"),new TranslatableComponent("aft.kill_dragon")),
    IMBUED_COLD_STAR("cold_star_piece_activated",3,ItemsRegister.COLD_STAR_PIECE_ACTIVATED.get().getDefaultInstance(), new TranslatableComponent("ach.cold_star_piece_activated"),nextID(),new TranslatableComponent("pre.cold_star_piece_activated"),new TranslatableComponent("aft.cold_star_piece_activated")),
    PYLON_INSCRIPTION("pylon_inscription",3, BlocksRegistry.INSCRIPTION_STONE.get().asItem().getDefaultInstance(),new TranslatableComponent("ach.pylon_inscription"),nextID(),new TranslatableComponent("pre.pylon_inscription"),new TranslatableComponent("aft.pylon_inscription")),

    CRAFT_SOLAR_FORGE("solar_forge_craft",4, SolarForge.SOLAR_FORGE_ITEM.get().getDefaultInstance(),new TranslatableComponent("ach.solar_forge_craft"),nextID(),new TranslatableComponent("pre.solar_forge_craft"),new TranslatableComponent("aft.solar_forge_craft")),
    TRANSMUTE_GEM("transmute_gem",4,ItemsRegister.BLUE_GEM_ENCHANCED.get().getDefaultInstance(), new TranslatableComponent("ach.transmute_gem"),nextID(),new TranslatableComponent("pre.transmute_gem"),new TranslatableComponent("aft.transmute_gem")),

    SOLAR_INFUSER("solar_infuser_create",5,SolarForge.INFUSER_ITEM.get().getDefaultInstance(),new TranslatableComponent("ach.solar_infuser_create"),nextID(),new TranslatableComponent("pre.solar_infuser_create"),new TranslatableComponent("aft.solar_infuser_create")),
    DIMENSIONAL_SHARD_DUNGEON("dim_shard_dungeon",5,ItemsRegister.SOLAR_STONE_CHISELED.get().getDefaultInstance(), new TranslatableComponent("ach.dim_shard_dungeon"),nextID(),new TranslatableComponent("pre.dim_shard_dungeon"),new TranslatableComponent("aft.dim_shard_dungeon")),

    CATALYSTS("catalysts",6,ItemsRegister.ARDO_RUNE_BLOCK.get().getDefaultInstance(), new TranslatableComponent("ach.catalysts"),nextID(),new TranslatableComponent("pre.catalysts"),new TranslatableComponent("aft.catalysts")),


    CRAFT_SOLAR_LENS("solar_lens_craft",7,ItemsRegister.SOLAR_LENS.get().getDefaultInstance(), new TranslatableComponent("ach.solar_lens_craft"),nextID(),new TranslatableComponent("pre.solar_lens_craft"),new TranslatableComponent("aft.solar_lens_craft")),
    RUNIC_ENERGY_REPEATER("runic_energy_repeater",7,ItemsRegister.REPEATER.get().getDefaultInstance(), new TranslatableComponent("ach.runic_energy_repeater"),nextID(),new TranslatableComponent("pre.runic_energy_repeater"),new TranslatableComponent("aft.runic_energy_repeater")),

    CRAFT_SOLAR_ENERGY_GENERATOR("energy_generator_craft",8,ItemsRegister.ENERGY_GENERATOR_BLOCK.get().getDefaultInstance(), new TranslatableComponent("ach.solar_generator_craft"),nextID(),new TranslatableComponent("pre.solar_generator_craft"),new TranslatableComponent("aft.solar_generator_craft")),

    RADIANT_LAND("dimension_core",9,ItemsRegister.DIMENSION_CORE.get().getDefaultInstance(), new TranslatableComponent("ach.dimension_core"),nextID(),new TranslatableComponent("pre.dimension_core"),new TranslatableComponent("aft.dimension_core")),

    KILL_CRYSTAL_BOSS("crystal_boss",10,ItemsRegister.CRYSTALLITE_CORE.get().getDefaultInstance(), new TranslatableComponent("ach.crystal_boss"),nextID(),new TranslatableComponent("pre.crystal_boss"),new TranslatableComponent("aft.crystal_boss")),
    KILL_RUNIC_ELEMENTAL("runic_elemental",11,ItemsRegister.CRYSTAL_HEART.get().getDefaultInstance(), new TranslatableComponent("ach.runic_elemental"),nextID(),new TranslatableComponent("pre.runic_elemental"),new TranslatableComponent("aft.runic_elemental"));


    //FIND_SOLAR_STONE("solar_stone",2, ItemsRegister.SOLAR_STONE.get().getDefaultInstance(),new TranslatableComponent("ach.solar_stone"),7,new TranslatableComponent("pre.solar_stone"),new TranslatableComponent("aft.solar_stone")),
     //USE_SOLAR_INFUSER("solar_infuser_use",4,ItemsRegister.SOLAR_WAND.get().getDefaultInstance(),new TranslatableComponent("ach.solar_infuser_use"),nextID(),new TranslatableComponent("pre.solar_infuser_use"),new TranslatableComponent("aft.solar_infuser_use")),
    //ACQUIRE_SOLAR_DUST("acquire_solar_dust",3,ItemsRegister.SOLAR_DUST.get().getDefaultInstance(),new TranslatableComponent("ach.acquire_solar_dust"),nextID(),new TranslatableComponent("pre.acquire_solar_dust"),new TranslatableComponent("aft.acquire_solar_dust")),


    private static int ID = 0;

    private static int nextID(){
        return ID++;
    }

    public static Progression[] allProgressions = Progression.class.getEnumConstants();

    public static Progression[] getAllAchievements(){
        return Progression.class.getEnumConstants();
    }

    public static Progression getAchievementByName(String name){
        for (Progression a : allProgressions){
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
    Progression(String str, int tier, ItemStack icon, TranslatableComponent translation, int id, TranslatableComponent pretext, TranslatableComponent after) {
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
