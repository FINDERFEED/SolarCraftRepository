package com.finderfeed.solarcraft.content.items.solar_lexicon.progressions;

import com.finderfeed.solarcraft.SolarCraft;
import com.finderfeed.solarcraft.registries.blocks.SolarcraftBlocks;
import com.finderfeed.solarcraft.registries.items.SolarcraftItems;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.chat.Component;
public enum Progression {
    ENTER_NETHER("enter_nether",1, Blocks.NETHERRACK.asItem().getDefaultInstance(),Component.translatable("ach.enter_nether"),nextID(),Component.translatable("pre.enter_nether"),Component.translatable("aft.enter_nether")),
//    FIND_INCINERATED_FOREST("find_incinerated_forest",1, SolarcraftItems.BURNT_LOG.get().getDefaultInstance(), Component.translatable("ach.find_incinerated_forest"),nextID(),Component.translatable("pre.find_incinerated_forest"),Component.translatable("aft.find_incinerated_forest")),
    FIND_KEY_LOCK_DUNGEON("find_key_lock",1, SolarcraftItems.INVINCIBLE_STONE.get().getDefaultInstance(), Component.translatable("ach.key_lock_dungeon"),nextID(),Component.translatable("pre.key_lock_dungeon"),Component.translatable("aft.key_lock_dungeon")),
    FIND_INFUSER_DUNGEON("find_infuser_dungeon",1, SolarcraftItems.SOLAR_STONE_COLLUMN.get().getDefaultInstance(), Component.translatable("ach.infuser_dungeon"),nextID(),Component.translatable("pre.infuser_dungeon"),Component.translatable("aft.infuser_dungeon")),
    FIND_KEY_SOURCE("find_key_source",1, SolarcraftItems.SOLAR_STONE_COLLUMN_HORIZONTAL.get().getDefaultInstance(), Component.translatable("ach.key_source"),nextID(),Component.translatable("pre.key_source"),Component.translatable("aft.key_source")),
    RUNE_ENERGY_PYLON("rune_energy_deposit",1, SolarcraftItems.RUNE_ENERGY_PYLON.get().getDefaultInstance(),Component.translatable("ach.rune_energy_deposit"),nextID(),Component.translatable("pre.rune_energy_deposit"),Component.translatable("aft.rune_energy_deposit")),
    TRADE_FOR_BLUE_GEM("blue_gem_trade",1, SolarcraftItems.BLUE_GEM.get().getDefaultInstance(), Component.translatable("ach.blue_gem_trade"),nextID(),Component.translatable("pre.blue_gem_trade"),Component.translatable("aft.blue_gem_trade")),
    INFUSING_CRAFTING_TABLE("infusing_crafting_table",1, SolarcraftItems.INFUSING_TABLE.get().getDefaultInstance(), Component.translatable("ach.infusing_crafting_table"),nextID(),Component.translatable("pre.infusing_crafting_table"),Component.translatable("aft.infusing_crafting_table")),


    ENTER_END("enter_end",2, Blocks.END_STONE.asItem().getDefaultInstance(),Component.translatable("ach.enter_end"),nextID(),Component.translatable("pre.enter_end"),Component.translatable("aft.enter_end")),
    KILL_WITHER("kill_wither",2, Blocks.WITHER_SKELETON_SKULL.asItem().getDefaultInstance(),Component.translatable("ach.kill_wither"),nextID(),Component.translatable("pre.kill_wither"),Component.translatable("aft.kill_wither")),
    ACQUIRE_COLD_STAR("cold_star_piece",2, SolarcraftItems.COLD_STAR_PIECE.get().getDefaultInstance(), Component.translatable("ach.cold_star_piece"),nextID(),Component.translatable("pre.cold_star_piece"),Component.translatable("aft.cold_star_piece")),
    RUNE_ENERGY_CLAIM("rune_energy_claim",2, SolarcraftItems.SOLAR_WAND.get().getDefaultInstance(),Component.translatable("ach.rune_energy_claim"),nextID(),Component.translatable("pre.rune_energy_claim"),Component.translatable("aft.rune_energy_claim")),
    SOLAR_RUNE("solar_rune",2, SolarcraftItems.SOLAR_RUNE_BASE.get().getDefaultInstance(),Component.translatable("ach.solar_rune"),nextID(),Component.translatable("pre.solar_rune"),Component.translatable("aft.solar_rune")),
    ALL_ENERGY_TYPES("all_energy_types",2, SolarcraftItems.RUNE_ENERGY_PYLON.get().getDefaultInstance(),Component.translatable("ach.all_energy_types"),nextID(),Component.translatable("pre.all_energy_types"),Component.translatable("aft.all_energy_types")),


    KILL_DRAGON("kill_dragon",3, Blocks.DRAGON_HEAD.asItem().getDefaultInstance(),Component.translatable("ach.kill_dragon"),nextID(),Component.translatable("pre.kill_dragon"),Component.translatable("aft.kill_dragon")),
    IMBUED_COLD_STAR("cold_star_piece_activated",3, SolarcraftItems.COLD_STAR_PIECE_ACTIVATED.get().getDefaultInstance(), Component.translatable("ach.cold_star_piece_activated"),nextID(),Component.translatable("pre.cold_star_piece_activated"),Component.translatable("aft.cold_star_piece_activated")),
    PYLON_INSCRIPTION("pylon_inscription",3, SolarcraftBlocks.INSCRIPTION_STONE.get().asItem().getDefaultInstance(),Component.translatable("ach.pylon_inscription"),nextID(),Component.translatable("pre.pylon_inscription"),Component.translatable("aft.pylon_inscription")),

    CRAFT_SOLAR_FORGE("solar_forge_craft",4, SolarCraft.SOLAR_FORGE_ITEM.get().getDefaultInstance(),Component.translatable("ach.solar_forge_craft"),nextID(),Component.translatable("pre.solar_forge_craft"),Component.translatable("aft.solar_forge_craft")),
    TRANSMUTE_GEM("transmute_gem",4, SolarcraftItems.BLUE_GEM_ENCHANCED.get().getDefaultInstance(), Component.translatable("ach.transmute_gem"),nextID(),Component.translatable("pre.transmute_gem"),Component.translatable("aft.transmute_gem")),

    SOLAR_INFUSER("solar_infuser_create",5, SolarCraft.INFUSER_ITEM.get().getDefaultInstance(),Component.translatable("ach.solar_infuser_create"),nextID(),Component.translatable("pre.solar_infuser_create"),Component.translatable("aft.solar_infuser_create")),
    DIMENSIONAL_SHARD_DUNGEON("dim_shard_dungeon",5, SolarcraftItems.SOLAR_STONE_CHISELED.get().getDefaultInstance(), Component.translatable("ach.dim_shard_dungeon"),nextID(),Component.translatable("pre.dim_shard_dungeon"),Component.translatable("aft.dim_shard_dungeon")),

    CATALYSTS("catalysts",6, SolarcraftItems.ARDO_RUNE_BLOCK.get().getDefaultInstance(), Component.translatable("ach.catalysts"),nextID(),Component.translatable("pre.catalysts"),Component.translatable("aft.catalysts")),


    CRAFT_SOLAR_LENS("solar_lens_craft",7, SolarcraftItems.SOLAR_LENS.get().getDefaultInstance(), Component.translatable("ach.solar_lens_craft"),nextID(),Component.translatable("pre.solar_lens_craft"),Component.translatable("aft.solar_lens_craft")),
    RUNIC_ENERGY_REPEATER("runic_energy_repeater",7, SolarcraftItems.REPEATER.get().getDefaultInstance(), Component.translatable("ach.runic_energy_repeater"),nextID(),Component.translatable("pre.runic_energy_repeater"),Component.translatable("aft.runic_energy_repeater")),

    CRAFT_SOLAR_ENERGY_GENERATOR("energy_generator_craft",8, SolarcraftItems.ENERGY_GENERATOR_BLOCK.get().getDefaultInstance(), Component.translatable("ach.solar_generator_craft"),nextID(),Component.translatable("pre.solar_generator_craft"),Component.translatable("aft.solar_generator_craft")),

    RADIANT_LAND("dimension_core",9, SolarcraftItems.DIMENSION_CORE.get().getDefaultInstance(), Component.translatable("ach.dimension_core"),nextID(),Component.translatable("pre.dimension_core"),Component.translatable("aft.dimension_core")),

    KILL_CRYSTAL_BOSS("crystal_boss",10, SolarcraftItems.CRYSTALLITE_CORE.get().getDefaultInstance(), Component.translatable("ach.crystal_boss"),nextID(),Component.translatable("pre.crystal_boss"),Component.translatable("aft.crystal_boss")),
    KILL_RUNIC_ELEMENTAL("runic_elemental",11, SolarcraftItems.CRYSTAL_HEART.get().getDefaultInstance(), Component.translatable("ach.runic_elemental"),nextID(),Component.translatable("pre.runic_elemental"),Component.translatable("aft.runic_elemental")),
    CLEAR_WORLD("clear_world",12, SolarcraftItems.CLEARING_CRYSTAL_RITUAL.get().getDefaultInstance(), Component.translatable("ach.clear_world"),nextID(),Component.translatable("pre.clear_world"),Component.translatable("aft.clear_world"));



    //FIND_SOLAR_STONE("solar_stone",2, ItemsRegister.SOLAR_STONE.get().getDefaultInstance(),Component.translatable("ach.solar_stone"),7,Component.translatable("pre.solar_stone"),Component.translatable("aft.solar_stone")),
     //USE_SOLAR_INFUSER("solar_infuser_use",4,ItemsRegister.SOLAR_WAND.get().getDefaultInstance(),Component.translatable("ach.solar_infuser_use"),nextID(),Component.translatable("pre.solar_infuser_use"),Component.translatable("aft.solar_infuser_use")),
    //ACQUIRE_SOLAR_DUST("acquire_solar_dust",3,ItemsRegister.SOLAR_DUST.get().getDefaultInstance(),Component.translatable("ach.acquire_solar_dust"),nextID(),Component.translatable("pre.acquire_solar_dust"),Component.translatable("aft.acquire_solar_dust")),


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
    public final Component translation;
    public final int id;
    public final Component pretext;
    public final Component afterText;
    Progression(String str, int tier, ItemStack icon, Component translation, int id, Component pretext, Component after) {
        this.str = str;
        this.tier = tier;
        this.icon = icon;
        this.translation = translation;
        this.id = id;
        this.pretext = pretext;
        this.afterText = after;
    }

    public Component getPretext() {
        return pretext; }

    public String getProgressionCode(){
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

    public Component getTranslation() {
        return translation;
    }


}
