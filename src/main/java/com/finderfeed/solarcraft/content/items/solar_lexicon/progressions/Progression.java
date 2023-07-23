package com.finderfeed.solarcraft.content.items.solar_lexicon.progressions;

import com.finderfeed.solarcraft.registries.blocks.SolarcraftBlocks;
import com.finderfeed.solarcraft.registries.items.SolarcraftItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.chat.Component;
public enum Progression {
    ENTER_NETHER("enter_nether",1, Blocks.NETHERRACK.asItem()),
    FIND_KEY_LOCK_DUNGEON("find_key_lock",1, SolarcraftItems.INVINCIBLE_STONE.get()),
    FIND_INFUSER_DUNGEON("find_infuser_dungeon",1, SolarcraftItems.SOLAR_STONE_COLLUMN.get()),
    FIND_KEY_SOURCE("find_key_source",1, SolarcraftItems.SOLAR_STONE_COLLUMN_HORIZONTAL.get()),
    GIANT_VAULT("giant_vault",1, SolarcraftItems.CHISELED_MAGISTONE.get()),
    RUNE_ENERGY_PYLON("rune_energy_deposit",1, SolarcraftItems.RUNE_ENERGY_PYLON.get()),
    TRADE_FOR_BLUE_GEM("blue_gem_trade",1, SolarcraftItems.BLUE_GEM.get()),
    INFUSING_CRAFTING_TABLE("infusing_crafting_table",1, SolarcraftItems.INFUSING_TABLE.get()),


    ENTER_END("enter_end",2, Blocks.END_STONE.asItem()),
    KILL_WITHER("kill_wither",2, Blocks.WITHER_SKELETON_SKULL.asItem()),
    ACQUIRE_COLD_STAR("cold_star_piece",2, SolarcraftItems.COLD_STAR_PIECE.get()),
    RUNE_ENERGY_CLAIM("rune_energy_claim",2, SolarcraftItems.SOLAR_WAND.get()),
    SOLAR_RUNE("solar_rune",2, SolarcraftItems.SOLAR_RUNE_BASE.get()),
    ALL_ENERGY_TYPES("all_energy_types",3, SolarcraftItems.RUNE_ENERGY_PYLON.get()),


    KILL_DRAGON("kill_dragon",3, Blocks.DRAGON_HEAD.asItem()),
    IMBUED_COLD_STAR("cold_star_piece_activated",3, SolarcraftItems.COLD_STAR_PIECE_ACTIVATED.get()),
    PYLON_INSCRIPTION("pylon_inscription",3, SolarcraftBlocks.INSCRIPTION_STONE.get().asItem()),

    CRAFT_SOLAR_FORGE("solar_forge_craft",4, SolarcraftItems.SOLAR_FORGE_ITEM.get()),
    TRANSMUTE_GEM("transmute_gem",4, SolarcraftItems.BLUE_GEM_ENCHANCED.get()),

    SOLAR_INFUSER("solar_infuser_create",5, SolarcraftItems.INFUSER_ITEM.get()),
    DIMENSIONAL_SHARD_DUNGEON("dim_shard_dungeon",5, SolarcraftItems.SOLAR_STONE_CHISELED.get()),

    CATALYSTS("catalysts",6, SolarcraftItems.ARDO_RUNE_BLOCK.get()),


    CRAFT_SOLAR_LENS("solar_lens_craft",7, SolarcraftItems.SOLAR_LENS.get()),
    RUNIC_ENERGY_REPEATER("runic_energy_repeater",7, SolarcraftItems.REPEATER.get()),

    CRAFT_SOLAR_ENERGY_GENERATOR("energy_generator_craft",8, SolarcraftItems.ENERGY_GENERATOR_BLOCK.get()),

    RADIANT_LAND("dimension_core",9, SolarcraftItems.DIMENSION_CORE.get()),

    KILL_CRYSTAL_BOSS("crystal_boss",10, SolarcraftItems.CRYSTALLITE_CORE.get()),

    KILL_RUNIC_ELEMENTAL("runic_elemental",11, SolarcraftItems.CRYSTAL_HEART.get()),

    CLEAR_WORLD("clear_world",12, SolarcraftItems.CLEARING_CRYSTAL_RITUAL.get());


    public static Progression[] allProgressions = Progression.class.getEnumConstants();

    public static Progression getProgressionByName(String name){
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

    public final Component pretext;
    public final Component afterText;
    Progression(String id, int tier, Item icon) {
        this.str = id;
        this.tier = tier;
        this.icon = icon.getDefaultInstance();
        this.translation = Component.translatable("ach." + id);
        this.pretext =  Component.translatable("pre." + id);
        this.afterText = Component.translatable("aft." + id);
    }

    public String getProgressionCode(){
        return this.str;
    }

    public int getAchievementTier(){
        return this.tier;
    }

    public ItemStack getIcon() {
        return icon;
    }

    public Component getPretext() {
        return pretext.copy();
    }


    public Component getTranslation() {
        return translation.copy();
    }

    public Component getAfterText() {
        return afterText.copy();
    }
}
