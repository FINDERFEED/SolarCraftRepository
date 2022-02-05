package com.finderfeed.solarforge.magic.items.solar_lexicon.unlockables;

import com.finderfeed.solarforge.SolarForge;
import com.finderfeed.solarforge.config.JsonFragmentsHelper;
import com.finderfeed.solarforge.magic.items.solar_lexicon.ProgressionStage;
import com.finderfeed.solarforge.magic.items.solar_lexicon.achievements.Progression;
import com.finderfeed.solarforge.magic.items.solar_lexicon.structure.category.CategoryBase;
import com.finderfeed.solarforge.magic.items.solar_lexicon.structure.subcategory.SubCategoryBase;
import com.finderfeed.solarforge.multiblocks.Multiblocks;
import com.finderfeed.solarforge.registries.items.ItemsRegister;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.*;

public class AncientFragment {
    public static List<AncientFragment> ALL_FRAGMENTS = new ArrayList<>();
    public static List<AncientFragment> CLIENTSIDE_FRAGMENTS_CACHE = new ArrayList<>();

    public static final AncientFragment RUNIC_TABLE = new AncientFragment(tx("solar_fragment.runic_table"),"runic_table",null,SubCategoryBase.BEGGINING,CategoryBase.BEGGINING_INFO, ItemsRegister.RUNIC_TABLE.get(),tx("runic_table.lore"),1);
    public static final AncientFragment FRAGMENT = new AncientFragment(tx("solar_fragment.fragment"),"fragment",null,SubCategoryBase.BEGGINING,CategoryBase.BEGGINING_INFO, ItemsRegister.INFO_FRAGMENT.get(),tx("fragment.lore"),1);
    public static final AncientFragment LEXICON = new AncientFragment(tx("solar_fragment.lexicon"),"lexicon",null,SubCategoryBase.BEGGINING,CategoryBase.BEGGINING_INFO, ItemsRegister.SOLAR_LEXICON.get(),tx("lexicon.lore"),1);
    public static final AncientFragment ENERGY_DUST = new AncientFragment(tx("solar_fragment.energy_dust"),"energy_dust",null,SubCategoryBase.BASIC_DUSTS,CategoryBase.EXPLORATION, ItemsRegister.ENERGY_DUST.get(),tx("energy_dust.lore"),1);
    public static final AncientFragment VOID_DUST = new AncientFragment(tx("solar_fragment.void_dust"),"void_dust",null,SubCategoryBase.BASIC_DUSTS,CategoryBase.EXPLORATION, ItemsRegister.VOID_DUST.get(),tx("void_dust.lore"),1);
    public static final AncientFragment INFUSING_CRAFTING_TABLE = new AncientFragment(tx("solar_fragment.infusing_crafting_table"),"infusing_crafting_table",null,SubCategoryBase.BEGINNER_BLOCKS,CategoryBase.BEGINNER, ItemsRegister.INFUSING_TABLE.get(),tx("infusing_crafting_table.lore"),1);
    public static final AncientFragment WAND = new AncientFragment(tx("solar_fragment.wand"),"wand",null,SubCategoryBase.BEGGINING,CategoryBase.BEGGINING_INFO, ItemsRegister.SOLAR_WAND.get(),tx("wand.lore"),1);
    public static final AncientFragment SOLAR_STONES = new AncientFragment(tx("solar_fragment.solar_stones"),"solar_stones", ProgressionStage.PRE_BEGGINING.ALL_PROGRESSIONS,SubCategoryBase.BEGINNER_BLOCKS,CategoryBase.BEGINNER, List.of(
            ItemsRegister.SOLAR_STONE_BRICKS.get().getDefaultInstance(),ItemsRegister.SOLAR_STONE_COLLUMN.get().getDefaultInstance(),
            ItemsRegister.SOLAR_STONE_COLLUMN_HORIZONTAL.get().getDefaultInstance(),ItemsRegister.SOLAR_STONE_STAIRS.get().getDefaultInstance(),
            ItemsRegister.SOLAR_STONE_CHISELED.get().getDefaultInstance(),ItemsRegister.ENERGIZED_STONE.get().getDefaultInstance()
    ),SolarForge.INFUSING_CRAFTING_RECIPE_TYPE,tx("solar_stones.lore"),1);
    public static final AncientFragment SPEED_ROAD = new AncientFragment(tx("solar_fragment.speed_road"),"speed_road",ProgressionStage.PRE_BEGGINING.ALL_PROGRESSIONS,SubCategoryBase.BEGINNER_BLOCKS,CategoryBase.BEGINNER, ItemsRegister.SPEED_ROAD.get().getDefaultInstance(),tx("speed_road.lore"),SolarForge.INFUSING_CRAFTING_RECIPE_TYPE,2);
    public static final AncientFragment EIGHT_ELEMENTS = new AncientFragment(tx("solar_fragment.eight_elements"),"eight_elements",ProgressionStage.BEGGINING_2.ALL_PROGRESSIONS,SubCategoryBase.WORLD,CategoryBase.EXPLORATION,"eight_elements_lore",ItemsRegister.SOLAR_RUNE_ARDO.get().getDefaultInstance(),1);
    public static final AncientFragment INFUSED_CRYSTALS = new AncientFragment(tx("solar_fragment.crystals"),"crystals", ProgressionStage.BEGGINING_2.ALL_PROGRESSIONS,SubCategoryBase.BEGINNER_ITEMS,CategoryBase.BEGINNER, List.of(
            ItemsRegister.ARDO_CRYSTAL.get().getDefaultInstance(),ItemsRegister.FIRA_CRYSTAL.get().getDefaultInstance(),
            ItemsRegister.TERA_CRYSTAL.get().getDefaultInstance(),ItemsRegister.URBA_CRYSTAL.get().getDefaultInstance(),
            ItemsRegister.GIRO_CRYSTAL.get().getDefaultInstance(),ItemsRegister.ULTIMA_CRYSTAL.get().getDefaultInstance(),
            ItemsRegister.KELDA_CRYSTAL.get().getDefaultInstance(),ItemsRegister.ZETA_CRYSTAL.get().getDefaultInstance()
    ),SolarForge.INFUSING_CRAFTING_RECIPE_TYPE,tx("crystals.lore"),2);


    public static final AncientFragment SOLAR_INFUSER = new AncientFragment(tx("solar_fragment.solar_infuser"),"solar_infuser",ProgressionStage.FORGE.ALL_PROGRESSIONS,SubCategoryBase.BEGINNER_BLOCKS,CategoryBase.BEGINNER, List.of(
            SolarForge.INFUSER_ITEM.get().getDefaultInstance(),
            ItemsRegister.SOLAR_INFUSION_POOL.get().getDefaultInstance()
    ),SolarForge.INFUSING_CRAFTING_RECIPE_TYPE,tx("solar_infuser.lore"),1);


    public static final AncientFragment SOLAR_DUST = new AncientFragment(tx("solar_fragment.solar_dust"),"solar_dust",ProgressionStage.PRE_FORGE.ALL_PROGRESSIONS,SubCategoryBase.BASIC_DUSTS,CategoryBase.EXPLORATION, ItemsRegister.SOLAR_DUST.get(),tx("solar_dust.lore"),1);
    public static final AncientFragment SOLAR_FORGE = new AncientFragment(tx("solar_fragment.solar_forge"),"solar_forge",ProgressionStage.PRE_FORGE.ALL_PROGRESSIONS,SubCategoryBase.BEGINNER_BLOCKS,CategoryBase.BEGINNER, SolarForge.SOLAR_FORGE_ITEM.get().getDefaultInstance(),tx("solar_forge.lore"),SolarForge.INFUSING_CRAFTING_RECIPE_TYPE,1);



    public static final AncientFragment ENDER_RADAR = new AncientFragment(tx("solar_fragment.ender_radar"),"ender_radar",ProgressionStage.PRE_FORGE.SELF_PROGRESSIONS,SubCategoryBase.BEGINNER_ITEMS,CategoryBase.BEGINNER, ItemsRegister.ENDER_RADAR.get().getDefaultInstance(),tx("ender_radar.lore"),SolarForge.INFUSING_CRAFTING_RECIPE_TYPE,2);


    public static final AncientFragment SOLAR_HELMET = new AncientFragment(tx("solar_fragment.solar_helmet"),"solar_helmet",ProgressionStage.FORGE.ALL_PROGRESSIONS,SubCategoryBase.ARMOR,CategoryBase.ARMOR,ItemsRegister.SOLAR_HELMET.get().getDefaultInstance(),tx("solar_helmet.lore"), SolarForge.INFUSING_RECIPE_TYPE,2);
    public static final AncientFragment SOLAR_CHESTPLATE = new AncientFragment(tx("solar_fragment.solar_chestplate"),"solar_chestplate",ProgressionStage.FORGE.ALL_PROGRESSIONS,SubCategoryBase.ARMOR,CategoryBase.ARMOR,ItemsRegister.SOLAR_CHESTPLATE.get().getDefaultInstance(),tx("solar_chestplate.lore"), SolarForge.INFUSING_RECIPE_TYPE,2);
    public static final AncientFragment SOLAR_LEGGINS = new AncientFragment(tx("solar_fragment.solar_leggings"),"solar_leggings",ProgressionStage.FORGE.ALL_PROGRESSIONS,SubCategoryBase.ARMOR,CategoryBase.ARMOR,ItemsRegister.SOLAR_LEGGINS.get().getDefaultInstance(),tx("solar_leggings.lore"), SolarForge.INFUSING_RECIPE_TYPE,2);
    public static final AncientFragment SOLAR_BOOTS = new AncientFragment(tx("solar_fragment.solar_boots"),"solar_boots",ProgressionStage.FORGE.ALL_PROGRESSIONS,SubCategoryBase.ARMOR,CategoryBase.ARMOR,ItemsRegister.SOLAR_BOOTS.get().getDefaultInstance(),tx("solar_boots.lore"), SolarForge.INFUSING_RECIPE_TYPE,2);
    public static final AncientFragment ITEM_MAGNET = new AncientFragment(tx("solar_fragment.item_magnet"),"item_magnet",ProgressionStage.AFTER_INFUSER.ALL_PROGRESSIONS,SubCategoryBase.BEGINNER_BLOCKS,CategoryBase.BEGINNER,ItemsRegister.MAGNET_BLOCK.get().getDefaultInstance(),tx("item_magnet.lore"), SolarForge.INFUSING_RECIPE_TYPE,2);
    public static final AncientFragment ILLIDIUM_INGOT = new AncientFragment(tx("solar_fragment.illidium_ingot"),"illidium_ingot",ProgressionStage.AFTER_INFUSER.ALL_PROGRESSIONS,SubCategoryBase.BEGINNER_MATERIALS,CategoryBase.BEGINNER,ItemsRegister.ILLIDIUM_INGOT.get().getDefaultInstance(),tx("illidium_ingot.lore"), SolarForge.INFUSING_RECIPE_TYPE,2);
    public static final AncientFragment ALGADIUM_INGOT = new AncientFragment(tx("solar_fragment.algadium_ingot"),"algadium_ingot",ProgressionStage.AFTER_INFUSER.ALL_PROGRESSIONS,SubCategoryBase.BEGINNER_MATERIALS,CategoryBase.BEGINNER,ItemsRegister.ALGADIUM_INGOT.get().getDefaultInstance(),tx("algadium_ingot.lore"), SolarForge.INFUSING_RECIPE_TYPE,2);
    public static final AncientFragment BLOCK_BOOMERANG = new AncientFragment(tx("solar_fragment.block_boomerang"),"block_boomerang",ProgressionStage.AFTER_INFUSER.ALL_PROGRESSIONS,SubCategoryBase.BEGINNER_ITEMS,CategoryBase.BEGINNER,ItemsRegister.BLOCK_BOOMERANG.get().getDefaultInstance(),tx("block_boomerang.lore"), SolarForge.INFUSING_RECIPE_TYPE,2);
    public static final AncientFragment ILLIDIUM_SWORD = new AncientFragment(tx("solar_fragment.illidium_sword"),"illidium_sword",ProgressionStage.AFTER_INFUSER.ALL_PROGRESSIONS,SubCategoryBase.BEGINNER_ITEMS,CategoryBase.BEGINNER,ItemsRegister.ILLIDIUM_SWORD.get().getDefaultInstance(),tx("illidium_sword.lore"), SolarForge.INFUSING_RECIPE_TYPE,2);
    public static final AncientFragment ILLIDIUM_AXE = new AncientFragment(tx("solar_fragment.illidium_axe"),"illidium_axe",ProgressionStage.AFTER_INFUSER.ALL_PROGRESSIONS,SubCategoryBase.BEGINNER_ITEMS,CategoryBase.BEGINNER,ItemsRegister.ILLIDIUM_AXE.get().getDefaultInstance(),tx("illidium_axe.lore"), SolarForge.INFUSING_RECIPE_TYPE,2);
    public static final AncientFragment ILLIDIUM_SHOVEL = new AncientFragment(tx("solar_fragment.illidium_shovel"),"illidium_shovel",ProgressionStage.AFTER_INFUSER.ALL_PROGRESSIONS,SubCategoryBase.BEGINNER_ITEMS,CategoryBase.BEGINNER,ItemsRegister.ILLIDIUM_SHOVEL.get().getDefaultInstance(),tx("illidium_shovel.lore"), SolarForge.INFUSING_RECIPE_TYPE,2);
    public static final AncientFragment ILLIDIUM_HOE = new AncientFragment(tx("solar_fragment.illidium_hoe"),"illidium_hoe",ProgressionStage.AFTER_INFUSER.ALL_PROGRESSIONS,SubCategoryBase.BEGINNER_ITEMS,CategoryBase.BEGINNER,ItemsRegister.GROWTH_HOE.get().getDefaultInstance(),tx("illidium_hoe.lore"), SolarForge.INFUSING_RECIPE_TYPE,2);
    public static final AncientFragment ILLIDIUM_PICKAXE = new AncientFragment(tx("solar_fragment.illidium_pickaxe"),"illidium_pickaxe",ProgressionStage.AFTER_INFUSER.ALL_PROGRESSIONS,SubCategoryBase.BEGINNER_ITEMS,CategoryBase.BEGINNER,ItemsRegister.VEIN_MINER.get().getDefaultInstance(),tx("illidium_pickaxe.lore"), SolarForge.INFUSING_RECIPE_TYPE,2);

    public static final AncientFragment AURA_HEALER = new AncientFragment(tx("solar_fragment.aura_healer"),"aura_healer",ProgressionStage.AFTER_CATALYSTS.ALL_PROGRESSIONS,SubCategoryBase.BEGINNER_BLOCKS,CategoryBase.BEGINNER,ItemsRegister.AURA_HEALER.get().getDefaultInstance(),tx("aura_healer.lore"), SolarForge.INFUSING_RECIPE_TYPE,2);
    public static final AncientFragment DISC_LAUNCHER = new AncientFragment(tx("solar_fragment.disc_launcher"),"disc_launcher",ProgressionStage.AFTER_CATALYSTS.ALL_PROGRESSIONS,SubCategoryBase.BEGINNER_ITEMS,CategoryBase.BEGINNER,ItemsRegister.SOLAR_DISC_LAUNCHER.get().getDefaultInstance(),tx("disc_launcher.lore"), SolarForge.INFUSING_RECIPE_TYPE,2);
    public static final AncientFragment TURRET_RADAR = new AncientFragment(tx("solar_fragment.turret_radar"),"turret_radar",ProgressionStage.AFTER_CATALYSTS.ALL_PROGRESSIONS,SubCategoryBase.BEGINNER_MATERIALS,CategoryBase.BEGINNER,ItemsRegister.TURRET_RADAR.get().getDefaultInstance(),tx("turret_radar.lore"), SolarForge.INFUSING_RECIPE_TYPE,2);
    public static final AncientFragment SOLAR_TURRET = new AncientFragment(tx("solar_fragment.solar_turret"),"solar_turret",ProgressionStage.AFTER_CATALYSTS.ALL_PROGRESSIONS,SubCategoryBase.BEGINNER_BLOCKS,CategoryBase.BEGINNER,ItemsRegister.TURRET_BLOCK.get().getDefaultInstance(),tx("solar_turret.lore"), SolarForge.INFUSING_RECIPE_TYPE,2);
    public static final AncientFragment GLOVES_OF_REACH = new AncientFragment(tx("solar_fragment.gloves_of_reach"),"gloves_of_reach",ProgressionStage.AFTER_CATALYSTS.ALL_PROGRESSIONS,SubCategoryBase.BEGINNER_ITEMS,CategoryBase.BEGINNER,ItemsRegister.REACH_GLOVES.get().getDefaultInstance(),tx("gloves_of_reach.lore"), SolarForge.INFUSING_RECIPE_TYPE,2);
    public static final AncientFragment MANA_REGEN_AMULET = new AncientFragment(tx("solar_fragment.mana_regen_amulet"),"mana_regen_amulet",ProgressionStage.AFTER_INFUSER.ALL_PROGRESSIONS,SubCategoryBase.BEGINNER_ITEMS,CategoryBase.BEGINNER,ItemsRegister.SOLAR_MANA_AMULET.get().getDefaultInstance(),tx("mana_regen_amulet.lore"), SolarForge.INFUSING_RECIPE_TYPE,2);
    public static final AncientFragment CATALYSTS = new AncientFragment(tx("solar_fragment.catalysts"),"catalysts",ProgressionStage.AFTER_CATALYSTS.ALL_PROGRESSIONS,SubCategoryBase.SKILLED_ITEMS,CategoryBase.SKILLED,List.of(
            ItemsRegister.CATALYST_BASE.get().getDefaultInstance(),ItemsRegister.ARDO_RUNE_BLOCK.get().getDefaultInstance(),
            ItemsRegister.TERA_RUNE_BLOCK.get().getDefaultInstance(),ItemsRegister.URBA_RUNE_BLOCK.get().getDefaultInstance(),
            ItemsRegister.KELDA_RUNE_BLOCK.get().getDefaultInstance(),ItemsRegister.ZETA_RUNE_BLOCK.get().getDefaultInstance(),
            ItemsRegister.FIRA_RUNE_BLOCK.get().getDefaultInstance(),ItemsRegister.ULTIMA_RUNE_BLOCK.get().getDefaultInstance(),
            ItemsRegister.GIRO_RUNE_BLOCK.get().getDefaultInstance()
    ),SolarForge.INFUSING_CRAFTING_RECIPE_TYPE,tx("catalysts.lore"),1);
    public static final AncientFragment CRYSTAL_CORES = new AncientFragment(tx("solar_fragment.crystal_cores"),"crystal_cores",ProgressionStage.AFTER_CATALYSTS.ALL_PROGRESSIONS,SubCategoryBase.SKILLED_MATERIALS,CategoryBase.SKILLED,List.of(
            ItemsRegister.CRYSTAL_CORE.get().getDefaultInstance(),ItemsRegister.ENERGY_CORE.get().getDefaultInstance(),
            ItemsRegister.VOID_CORE.get().getDefaultInstance(),ItemsRegister.MATERIALIZATION_CORE.get().getDefaultInstance(),
            ItemsRegister.CRYSTAL_STAR.get().getDefaultInstance()
    ),SolarForge.INFUSING_RECIPE_TYPE,tx("crystal_cores.lore"),2);
    public static final AncientFragment SMALL_SOLAR_REACTOR = new AncientFragment(tx("solar_fragment.small_solar_reactor"),"small_solar_reactor",ProgressionStage.AFTER_CATALYSTS.ALL_PROGRESSIONS,SubCategoryBase.SKILLED_MATERIALS,CategoryBase.SKILLED,ItemsRegister.SMALL_SOLAR_REACTOR.get().getDefaultInstance(),tx("small_solar_reactor.lore"), SolarForge.INFUSING_RECIPE_TYPE,2);

    public static final AncientFragment SOLAR_LENS = new AncientFragment(tx("solar_fragment.solar_lens"),"solar_lens",ProgressionStage.PRE_LENS.ALL_PROGRESSIONS,SubCategoryBase.SKILLED_ITEMS,CategoryBase.SKILLED,ItemsRegister.SOLAR_LENS.get().getDefaultInstance(),tx("solar_lens.lore"), SolarForge.INFUSING_RECIPE_TYPE,3);
    public static final AncientFragment RUNIC_ENERGY_REPEATER = new AncientFragment(tx("solar_fragment.runic_energy_repeater"),"runic_energy_repeater",ProgressionStage.PRE_LENS.ALL_PROGRESSIONS,SubCategoryBase.RUNIC_ENERGY_TRANSMITTING,CategoryBase.RUNIC_ENERGY,ItemsRegister.REPEATER.get().getDefaultInstance(),tx("runic_energy_repeater.lore"), SolarForge.INFUSING_RECIPE_TYPE,3);

    public static final AncientFragment RUNIC_ENERGY_LORE = new AncientFragment(tx("solar_fragment.runic_energy"),"runic_energy",ProgressionStage.AFTER_LENS.ALL_PROGRESSIONS,SubCategoryBase.RUNIC_ENERGY_TRANSMITTING,CategoryBase.RUNIC_ENERGY,"runic_energy_lore",ItemsRegister.RUNE_ENERGY_PYLON.get().getDefaultInstance(),3);
    public static final AncientFragment QUALADIUM_INGOT = new AncientFragment(tx("solar_fragment.qualadium_ingot"),"qualadium_ingot",ProgressionStage.AFTER_LENS.ALL_PROGRESSIONS,SubCategoryBase.SKILLED_MATERIALS,CategoryBase.SKILLED,ItemsRegister.QUALADIUM_INGOT.get().getDefaultInstance(),tx("qualadium_ingot.lore"), SolarForge.SOLAR_SMELTING,4);
    public static final AncientFragment GEMINIUM_INGOT = new AncientFragment(tx("solar_fragment.geminium_ingot"),"geminium_ingot",ProgressionStage.AFTER_LENS.ALL_PROGRESSIONS,SubCategoryBase.SKILLED_MATERIALS,CategoryBase.SKILLED,ItemsRegister.GEMINIUM_INGOT.get().getDefaultInstance(),tx("geminium_ingot.lore"), SolarForge.SOLAR_SMELTING,4);
    public static final AncientFragment ENDERIUM_INGOT = new AncientFragment(tx("solar_fragment.enderium_ingot"),"enderium_ingot",ProgressionStage.AFTER_LENS.ALL_PROGRESSIONS,SubCategoryBase.SKILLED_MATERIALS,CategoryBase.SKILLED,ItemsRegister.ENDERIUM_INGOT.get().getDefaultInstance(),tx("enderium_ingot.lore"), SolarForge.SOLAR_SMELTING,4);
    public static final AncientFragment ENERGETIC_INGOT = new AncientFragment(tx("solar_fragment.energetic_ingot"),"energetic_ingot",ProgressionStage.AFTER_LENS.ALL_PROGRESSIONS,SubCategoryBase.SKILLED_MATERIALS,CategoryBase.SKILLED,ItemsRegister.ENERGETIC_INGOT.get().getDefaultInstance(),tx("energetic_ingot.lore"), SolarForge.SOLAR_SMELTING,4);

    public static final AncientFragment SOLAR_GOD_SWORD = new AncientFragment(tx("solar_fragment.solar_god_sword"),"solar_god_sword",ProgressionStage.AFTER_LENS.ALL_PROGRESSIONS,SubCategoryBase.SKILLED_ITEMS,CategoryBase.SKILLED,ItemsRegister.SOLAR_GOD_SWORD.get().getDefaultInstance(),tx("solar_god_sword.lore"), SolarForge.INFUSING_RECIPE_TYPE,5);
    public static final AncientFragment SOLAR_GOD_PICKAXE = new AncientFragment(tx("solar_fragment.solar_god_pickaxe"),"solar_god_pickaxe",ProgressionStage.AFTER_LENS.ALL_PROGRESSIONS,SubCategoryBase.SKILLED_ITEMS,CategoryBase.SKILLED,ItemsRegister.SOLAR_GOD_PICKAXE.get().getDefaultInstance(),tx("solar_god_pickaxe.lore"), SolarForge.INFUSING_RECIPE_TYPE,5);
    public static final AncientFragment SOLAR_GOD_SHIELD = new AncientFragment(tx("solar_fragment.solar_god_shield"),"solar_god_shield",ProgressionStage.AFTER_LENS.ALL_PROGRESSIONS,SubCategoryBase.SKILLED_ITEMS,CategoryBase.SKILLED,ItemsRegister.SOLAR_GOD_SHIELD.get().getDefaultInstance(),tx("solar_god_shield.lore"), SolarForge.INFUSING_RECIPE_TYPE,5);
    public static final AncientFragment QUALADIUM_SWORD = new AncientFragment(tx("solar_fragment.qualadium_sword"),"qualadium_sword",ProgressionStage.AFTER_LENS.ALL_PROGRESSIONS,SubCategoryBase.SKILLED_ITEMS,CategoryBase.SKILLED,ItemsRegister.QUALADIUM_SWORD.get().getDefaultInstance(),tx("qualadium_sword.lore"), SolarForge.INFUSING_RECIPE_TYPE,5);
    public static final AncientFragment QUALADIUM_AXE = new AncientFragment(tx("solar_fragment.qualadium_axe"),"qualadium_axe",ProgressionStage.AFTER_LENS.ALL_PROGRESSIONS,SubCategoryBase.SKILLED_ITEMS,CategoryBase.SKILLED,ItemsRegister.QUALADIUM_AXE.get().getDefaultInstance(),tx("qualadium_axe.lore"), SolarForge.INFUSING_RECIPE_TYPE,5);
    public static final AncientFragment QUALADIUM_SHOVEL = new AncientFragment(tx("solar_fragment.qualadium_shovel"),"qualadium_shovel",ProgressionStage.AFTER_LENS.ALL_PROGRESSIONS,SubCategoryBase.SKILLED_ITEMS,CategoryBase.SKILLED,ItemsRegister.QUALADIUM_SHOVEL.get().getDefaultInstance(),tx("qualadium_shovel.lore"), SolarForge.INFUSING_RECIPE_TYPE,5);
    public static final AncientFragment QUALADIUM_HOE = new AncientFragment(tx("solar_fragment.qualadium_hoe"),"qualadium_hoe",ProgressionStage.AFTER_LENS.ALL_PROGRESSIONS,SubCategoryBase.SKILLED_ITEMS,CategoryBase.SKILLED,ItemsRegister.QUALADIUM_HOE.get().getDefaultInstance(),tx("qualadium_hoe.lore"), SolarForge.INFUSING_RECIPE_TYPE,5);
    public static final AncientFragment QUALADIUM_PICKAXE = new AncientFragment(tx("solar_fragment.qualadium_pickaxe"),"qualadium_pickaxe",ProgressionStage.AFTER_LENS.ALL_PROGRESSIONS,SubCategoryBase.SKILLED_ITEMS,CategoryBase.SKILLED,ItemsRegister.QUALADIUM_PICKAXE.get().getDefaultInstance(),tx("qualadium_pickaxe.lore"), SolarForge.INFUSING_RECIPE_TYPE,5);
    public static final AncientFragment BONEMEALER = new AncientFragment(tx("solar_fragment.bonemealer"),"bonemealer",ProgressionStage.AFTER_LENS.ALL_PROGRESSIONS ,SubCategoryBase.SKILLED_BLOCKS,CategoryBase.SKILLED,ItemsRegister.BONEMEALER.get().getDefaultInstance(),tx("bonemealer.lore"), SolarForge.INFUSING_RECIPE_TYPE,5);
    public static final AncientFragment EXPLOSION_BLOCKER = new AncientFragment(tx("solar_fragment.explosion_blocker"),"explosion_blocker",ProgressionStage.AFTER_LENS.ALL_PROGRESSIONS ,SubCategoryBase.SKILLED_BLOCKS,CategoryBase.SKILLED,ItemsRegister.EXPLOSION_BLOCKER.get().getDefaultInstance(),tx("explosion_blocker.lore"), SolarForge.INFUSING_RECIPE_TYPE,5);

    public static final AncientFragment EXPERIENCE_CRYSTAL = new AncientFragment(tx("solar_fragment.experience_crystal"),"experience_crystal",ProgressionStage.AFTER_LENS.ALL_PROGRESSIONS,SubCategoryBase.SKILLED_ITEMS,CategoryBase.SKILLED,ItemsRegister.EXPERIENCE_CRYSTAL.get().getDefaultInstance(),tx("experience_crystal.lore"), SolarForge.INFUSING_RECIPE_TYPE,5);
    public static final AncientFragment AMETHYST_CORE = new AncientFragment(tx("solar_fragment.amethyst_core"),"amethyst_core",ProgressionStage.AFTER_LENS.ALL_PROGRESSIONS,SubCategoryBase.SKILLED_MATERIALS,CategoryBase.SKILLED,ItemsRegister.AMETHYST_CORE.get().getDefaultInstance(),tx("amethyst_core.lore"), SolarForge.INFUSING_RECIPE_TYPE,4);
    public static final AncientFragment RUNIC_CORE = new AncientFragment(tx("solar_fragment.runic_core"),"runic_core",ProgressionStage.AFTER_LENS.ALL_PROGRESSIONS,SubCategoryBase.SKILLED_MATERIALS,CategoryBase.SKILLED,ItemsRegister.RUNIC_CORE.get().getDefaultInstance(),tx("runic_core.lore"), SolarForge.INFUSING_RECIPE_TYPE,4);
    public static final AncientFragment MODULES = new AncientFragment(tx("solar_fragment.modules"),"modules",ProgressionStage.AFTER_LENS.ALL_PROGRESSIONS,SubCategoryBase.SKILLED_ITEMS,CategoryBase.SKILLED,List.of(
            ItemsRegister.MODULE_APPLIER.get().getDefaultInstance(),ItemsRegister.PICKAXE_MINER_ABILITY_MODULE.get().getDefaultInstance(),
            ItemsRegister.MAGIC_DAMAGE_MODULE_5.get().getDefaultInstance(),ItemsRegister.SWORD_AUTOHEAL_MODULE.get().getDefaultInstance(),
            ItemsRegister.PHYSICAL_DEFENCE_MODULE_10.get().getDefaultInstance(),ItemsRegister.DISARMING_THORNS_MODULE.get().getDefaultInstance(),
            ItemsRegister.SWORD_AOE_ATTACK.get().getDefaultInstance(),ItemsRegister.PICKAXE_AUTO_SMELT.get().getDefaultInstance(),
            ItemsRegister.BLESSED_MODULE.get().getDefaultInstance(),ItemsRegister.POISONING_BLADE_MODULE.get().getDefaultInstance(),
            ItemsRegister.FURY_SWIPES_MODULE.get().getDefaultInstance()
    ),SolarForge.INFUSING_RECIPE_TYPE,tx("modules.lore"),5);

    public static final AncientFragment SOLAR_ENERGY_GENERATOR = new AncientFragment(tx("solar_fragment.solar_energy_generator"),"solar_energy_generator",ProgressionStage.AFTER_LENS.ALL_PROGRESSIONS,SubCategoryBase.MASTER_ENERGY,CategoryBase.MASTER,ItemsRegister.ENERGY_GENERATOR_BLOCK.get().getDefaultInstance(),tx("solar_energy_generator.lore"), SolarForge.INFUSING_RECIPE_TYPE,6);
    public static final AncientFragment SOLAR_CORE = new AncientFragment(tx("solar_fragment.solar_core"),"solar_core",ProgressionStage.SOLAR_ENERGY.ALL_PROGRESSIONS,SubCategoryBase.MASTER_ENERGY,CategoryBase.MASTER,ItemsRegister.SOLAR_CORE.get().getDefaultInstance(),tx("solar_core.lore"), SolarForge.INFUSING_RECIPE_TYPE,6);
    public static final AncientFragment SOLAR_ENERGY_REPEATER = new AncientFragment(tx("solar_fragment.solar_energy_repeater"),"solar_energy_repeater",ProgressionStage.SOLAR_ENERGY.ALL_PROGRESSIONS,SubCategoryBase.MASTER_ENERGY,CategoryBase.MASTER,ItemsRegister.SOLAR_ENERGY_REPEATER.get().getDefaultInstance(),tx("solar_energy_repeater.lore"), SolarForge.INFUSING_RECIPE_TYPE,6);

    public static final AncientFragment RADIANT_CHESTPLATE = new AncientFragment(tx("solar_fragment.radiant_cuirass"),"radiant_cuirass",ProgressionStage.SOLAR_ENERGY.ALL_PROGRESSIONS,SubCategoryBase.ARMOR,CategoryBase.ARMOR,ItemsRegister.RADIANT_CHESTPLATE.get().getDefaultInstance(),tx("radiant_cuirass.lore"), SolarForge.INFUSING_RECIPE_TYPE,8);
    public static final AncientFragment MEDIUM_SOLAR_REACTOR = new AncientFragment(tx("solar_fragment.medium_solar_reactor"),"medium_solar_reactor",ProgressionStage.SOLAR_ENERGY.ALL_PROGRESSIONS,SubCategoryBase.MASTER_MATERIALS,CategoryBase.MASTER,ItemsRegister.MEDIUM_SOLAR_REACTOR.get().getDefaultInstance(),tx("medium_solar_reactor.lore"), SolarForge.INFUSING_RECIPE_TYPE,7);
    public static final AncientFragment ZAP_TURRET = new AncientFragment(tx("solar_fragment.zap_turret"),"zap_turret",ProgressionStage.SOLAR_ENERGY.ALL_PROGRESSIONS,SubCategoryBase.MASTER_ITEMS,CategoryBase.MASTER,ItemsRegister.ZAP_TURRET_BLOCK.get().getDefaultInstance(),tx("zap_turret.lore"), SolarForge.INFUSING_RECIPE_TYPE,8);
    public static final AncientFragment CHARGED_QUALADIUM_INGOT = new AncientFragment(tx("solar_fragment.charged_qualadium_ingot"),"charged_qualadium_ingot",ProgressionStage.SOLAR_ENERGY.ALL_PROGRESSIONS,SubCategoryBase.MASTER_MATERIALS,CategoryBase.MASTER,ItemsRegister.CHARGED_QUALADIUM_INGOT.get().getDefaultInstance(),tx("charged_qualadium_ingot.lore"), SolarForge.INFUSING_RECIPE_TYPE,7);
    public static final AncientFragment SOLAR_MORTAR = new AncientFragment(tx("solar_fragment.solar_mortar"),"solar_mortar",ProgressionStage.SOLAR_ENERGY.ALL_PROGRESSIONS,SubCategoryBase.MASTER_ITEMS,CategoryBase.MASTER,ItemsRegister.SOLAR_MORTAR.get().getDefaultInstance(),tx("solar_mortar.lore"), SolarForge.INFUSING_RECIPE_TYPE,8);
    public static final AncientFragment SOLAR_FURNACE = new AncientFragment(tx("solar_fragment.solar_furnace"),"solar_furnace",ProgressionStage.SOLAR_ENERGY.ALL_PROGRESSIONS,SubCategoryBase.MASTER_ITEMS,CategoryBase.MASTER,ItemsRegister.SOLAR_FURNACE_BLOCK.get().getDefaultInstance(),tx("solar_furnace.lore"), SolarForge.INFUSING_RECIPE_TYPE,8);
   public static final AncientFragment  TOTEM_OF_IMMORTALITY = new AncientFragment(tx("solar_fragment.totem_of_immortality"),"totem_of_immortality",ProgressionStage.SOLAR_ENERGY.ALL_PROGRESSIONS,SubCategoryBase.MASTER_ITEMS,CategoryBase.MASTER,ItemsRegister.TOTEM_OF_IMMORTALITY.get().getDefaultInstance(),tx("totem_of_immortality.lore"), SolarForge.INFUSING_RECIPE_TYPE,8);
    public static final AncientFragment SOLAR_CROSSBOW = new AncientFragment(tx("solar_fragment.solar_crossbow"),"solar_crossbow",ProgressionStage.SOLAR_ENERGY.ALL_PROGRESSIONS,SubCategoryBase.MASTER_ITEMS,CategoryBase.MASTER,ItemsRegister.ULTRA_CROSSBOW.get().getDefaultInstance(),tx("solar_crossbow.lore"), SolarForge.INFUSING_RECIPE_TYPE,8);
    public static final AncientFragment LIGHTNING_GUN = new AncientFragment(tx("solar_fragment.lightning_gun"),"lightning_gun",ProgressionStage.SOLAR_ENERGY.ALL_PROGRESSIONS,SubCategoryBase.MASTER_ITEMS,CategoryBase.MASTER,ItemsRegister.LIGHTNING_GUN.get().getDefaultInstance(),tx("lightning_gun.lore"), SolarForge.INFUSING_RECIPE_TYPE,8);
    public static final AncientFragment DIMENSION_CORE = new AncientFragment(tx("solar_fragment.dimension_core"),"dimension_core",ProgressionStage.SOLAR_ENERGY.ALL_PROGRESSIONS,SubCategoryBase.MASTER_ITEMS,CategoryBase.MASTER,ItemsRegister.DIMENSION_CORE.get().getDefaultInstance(),tx("dimension_core.lore"), SolarForge.INFUSING_RECIPE_TYPE,9);


    public static final AncientFragment AURA_HEALER_STRUCTURE = new AncientFragment(tx("solar_fragment.aura_healer_structure"),"aura_healer_structure",AURA_HEALER.neededProgression,SubCategoryBase.STRUCTURES,CategoryBase.STRUCTURES,Multiblocks.AURA_HEALER, AURA_HEALER.priority);
    public static final AncientFragment SOLAR_MORTAR_STRUCTURE = new AncientFragment(tx("solar_fragment.solar_mortar_structure"),"solar_mortar_structure",SOLAR_MORTAR.neededProgression,SubCategoryBase.STRUCTURES,CategoryBase.STRUCTURES,Multiblocks.SOLAR_MORTAR, SOLAR_MORTAR.priority);
    public static final AncientFragment INFUSER_TIER_BEGINNER = new AncientFragment(tx("solar_fragment.solar_infuser_structure"),"solar_infuser_structure",SOLAR_INFUSER.neededProgression,SubCategoryBase.STRUCTURES,CategoryBase.STRUCTURES,Multiblocks.INFUSER_TIER_FIRST, SOLAR_INFUSER.priority);
    public static final AncientFragment INFUSER_TIER_RUNIC_ENERGY = new AncientFragment(tx("solar_fragment.solar_infuser_structure_2"),"solar_infuser_structure_2",ProgressionStage.AFTER_LENS.ALL_PROGRESSIONS, SubCategoryBase.STRUCTURES,CategoryBase.STRUCTURES,Multiblocks.INFUSER_TIER_RUNIC_ENERGY, SOLAR_INFUSER.priority);
    public static final AncientFragment INFUSER_TIER_SOLAR_ENERGY = new AncientFragment(tx("solar_fragment.solar_infuser_structure_3"),"solar_infuser_structure_3",ProgressionStage.SOLAR_ENERGY.ALL_PROGRESSIONS,SubCategoryBase.STRUCTURES,CategoryBase.STRUCTURES,Multiblocks.INFUSER_TIER_SOLAR_ENERGY, SOLAR_INFUSER.priority);


    public static final AncientFragment SOLAR_CORE_STRUCTURE = new AncientFragment(tx("solar_fragment.solar_core_structure"),"solar_core_structure",SOLAR_CORE.neededProgression,SubCategoryBase.STRUCTURES,CategoryBase.STRUCTURES,Multiblocks.SOLAR_CORE, SOLAR_CORE.priority);
    public static final AncientFragment SOLAR_ENERGY_GENERATOR_STRUCTURE = new AncientFragment(tx("solar_fragment.solar_energy_generator_structure"),"solar_energy_generator_structure",SOLAR_ENERGY_GENERATOR.neededProgression,SubCategoryBase.STRUCTURES,CategoryBase.STRUCTURES,Multiblocks.SOLAR_ENERGY_GENERATOR, SOLAR_ENERGY_GENERATOR.priority);
    public static final AncientFragment DIMENSION_STRUCTURE = new AncientFragment(tx("solar_fragment.dimension_structure"),"dimension_structure",ProgressionStage.SOLAR_ENERGY.ALL_PROGRESSIONS,SubCategoryBase.STRUCTURES,CategoryBase.STRUCTURES,Multiblocks.RADIANT_LAND_PORTAL, DIMENSION_CORE.priority);
    public static final AncientFragment ZAP_TURRET_STRUCTURE = new AncientFragment(tx("solar_fragment.zap_turret_structure"),"zap_turret_structure",ProgressionStage.SOLAR_ENERGY.ALL_PROGRESSIONS,SubCategoryBase.STRUCTURES,CategoryBase.STRUCTURES,Multiblocks.ZAP_TURRET, ZAP_TURRET.priority);
    public static final AncientFragment EXPLOSION_BLOCKER_STRUCTURE = new AncientFragment(tx("solar_fragment.explosion_blocker_structure"),"explosion_blocker_structure",EXPLOSION_BLOCKER.neededProgression,SubCategoryBase.STRUCTURES,CategoryBase.STRUCTURES,Multiblocks.EXPLOSION_BLOCKER, EXPLOSION_BLOCKER.priority);

    public static final AncientFragment SOLAR_GOD_PICKAXE_UPGRADE = new AncientFragment(tx("solar_fragment.solar_god_pickaxe_upgrade"),"solar_god_pickaxe_upgrade",ProgressionStage.SOLAR_ENERGY.ALL_PROGRESSIONS,SubCategoryBase.UPGRADES,CategoryBase.UPGRADES,ItemsRegister.SOLAR_GOD_PICKAXE.get().getDefaultInstance(),tx("solar_pickaxe_upgrade.lore"),8);
    public static final AncientFragment SOLAR_GOD_SWORD_UPGRADE = new AncientFragment(tx("solar_fragment.solar_god_sword_upgrade"),"solar_god_sword_upgrade",ProgressionStage.SOLAR_ENERGY.ALL_PROGRESSIONS,SubCategoryBase.UPGRADES,CategoryBase.UPGRADES,ItemsRegister.SOLAR_GOD_SWORD.get().getDefaultInstance(),tx("solar_god_sword_upgrade.lore"),8);

    public static final AncientFragment SOLAR_GOD_BOW = new AncientFragment(tx("solar_fragment.solar_god_bow"),"solar_god_bow",ProgressionStage.SOLAR_ENERGY.ALL_PROGRESSIONS,SubCategoryBase.MASTER_ITEMS,CategoryBase.MASTER,ItemsRegister.SOLAR_GOD_BOW.get().getDefaultInstance(),tx("solar_god_bow.lore"), SolarForge.INFUSING_RECIPE_TYPE,8);
    public static final AncientFragment SOLAR_GOD_BOW_UPGRADE = new AncientFragment(tx("solar_fragment.solar_god_bow_upgrade"),"solar_god_bow_upgrade",ProgressionStage.SOLAR_ENERGY.ALL_PROGRESSIONS,SubCategoryBase.UPGRADES,CategoryBase.UPGRADES,ItemsRegister.SOLAR_GOD_BOW.get().getDefaultInstance(),tx("solar_god_bow_upgrade.lore"),8);
    public static final AncientFragment AMULETS = new AncientFragment(tx("solar_fragment.amulets"),"amulets",ProgressionStage.AFTER_LENS.ALL_PROGRESSIONS,SubCategoryBase.SKILLED_ITEMS,CategoryBase.SKILLED,List.of(
            ItemsRegister.REGEN_AMULET.get().getDefaultInstance(),ItemsRegister.JUMP_AMULET.get().getDefaultInstance(),
            ItemsRegister.SPEED_AMULET.get().getDefaultInstance(),ItemsRegister.HASTE_AMULET.get().getDefaultInstance(),
            ItemsRegister.NIGHT_VISION_AMULET.get().getDefaultInstance(),ItemsRegister.STRENGTH_AMULET.get().getDefaultInstance()
    ),SolarForge.INFUSING_RECIPE_TYPE,tx("amulets.lore"),5);
    public static final AncientFragment RADIANT_LAND_LORE = new AncientFragment(tx("solar_fragment.radiant_land"),"radiant_land",ProgressionStage.DIMENSION.ALL_PROGRESSIONS,SubCategoryBase.RADIANT_LAND,CategoryBase.MIDGAME,"radiant_land_lore",ItemsRegister.DIMENSION_CORE.get().getDefaultInstance(),9);
    public static final AncientFragment DEFENCE_CRYSTAL = new AncientFragment(tx("solar_fragment.defence_crystal"),"defence_crystal",ProgressionStage.DIMENSION.ALL_PROGRESSIONS,SubCategoryBase.RADIANT_LAND,CategoryBase.MIDGAME,"defence_crystal_lore",ItemsRegister.CRYSTALLITE_CORE.get().getDefaultInstance(),9);

    ;

    public static Map<String,AncientFragment> FRAGMENTS_ID_MAP = new HashMap<>();




    public static void initFragmentsMap(){

        for (AncientFragment frag : getAllFragments()){
            if (!FRAGMENTS_ID_MAP.containsKey(frag.getId())  ){
                FRAGMENTS_ID_MAP.put(frag.getId(),frag);
            }
        }
    }

    private final TranslatableComponent translation;
    private final String id;
    private final Progression[] neededProgression;
    private final Type type;
    private final Item icon;

    @Deprecated
    private final BookEntry entry;

    private final SubCategoryBase subCategory;
    private final CategoryBase category;

    private final int priority;
    private List<ItemStack> stacks;
    private Multiblocks structure;
    private ItemStack item;
    private TranslatableComponent lore;
    private TranslatableComponent itemLore;
    private RecipeType<?> recipeType;
    private String screenID;



    AncientFragment(TranslatableComponent translation, String id, Progression[] neededProgression, SubCategoryBase subBase, CategoryBase catBase, Type type, Item Icon, int priority,boolean jsonInit){
        this.translation = translation;
        this.id = id;
        this.entry = null;
        this.neededProgression = neededProgression;
        this.type = type;
        this.icon = Icon;
        this.category = catBase;
        this.subCategory = subBase;
        this.priority = priority;
        if (!jsonInit) {
            addFragToList();
        }
    }

    AncientFragment(TranslatableComponent translation, String id, Progression[] neededProgression, SubCategoryBase subBase, CategoryBase catBase, ItemStack item, TranslatableComponent itemLore, RecipeType<?> recipeType, int priority){
        this(translation,id,neededProgression,subBase,catBase,Type.ITEM,item.getItem(),priority,false);
        this.item = item;
        this.itemLore = itemLore;
        this.recipeType = recipeType;

    }

    AncientFragment(TranslatableComponent translation, String id, Progression[] neededProgression, SubCategoryBase subBase, CategoryBase catBase, Multiblocks structure, int priority){
        this(translation,id,neededProgression,subBase,catBase,Type.STRUCTURE,structure.getM().mainBlock.getBlock().asItem(),priority,false);
        this.structure = structure;

    }

    AncientFragment(TranslatableComponent translation, String id, Progression[] neededProgression, SubCategoryBase subBase, CategoryBase catBase, Item Icon, TranslatableComponent lore, int priority){
        this(translation,id,neededProgression,subBase,catBase,Type.INFORMATION,Icon,priority,false);
        this.lore = lore;

    }

    AncientFragment(TranslatableComponent translation, String id, Progression[] neededProgression, SubCategoryBase subBase, CategoryBase catBase, ItemStack item, TranslatableComponent upgradeLore, int priority){
        this(translation,id,neededProgression,subBase,catBase,Type.UPGRADE,item.getItem(),priority,false);
        this.item = item;
        this.itemLore = upgradeLore;

    }

    AncientFragment(TranslatableComponent translation, String id, Progression[] neededProgression, SubCategoryBase subBase, CategoryBase catBase, List<ItemStack> item,RecipeType<?> type, TranslatableComponent upgradeLore, int priority){
        this(translation,id,neededProgression,subBase,catBase,Type.ITEMS,item.get(0).getItem(),priority,false);
        this.stacks = item;
        this.itemLore = upgradeLore;
        this.recipeType = type;


    }
    AncientFragment(TranslatableComponent translation, String id, Progression[] neededProgression, SubCategoryBase subBase, CategoryBase catBase, String screenid, ItemStack logo, int priority){
        this(translation,id,neededProgression,subBase,catBase,Type.CUSTOM,logo.getItem(),priority,false);
        this.screenID = screenid;

    }


    //json constructors start
    AncientFragment(TranslatableComponent translation, String id, Progression[] neededProgression, SubCategoryBase subBase, CategoryBase catBase, Item Icon, TranslatableComponent lore, int priority,boolean jsonInit){
        this(translation,id,neededProgression,subBase,catBase,Type.INFORMATION,Icon,priority,jsonInit);
        this.lore = lore;

    }
    AncientFragment(TranslatableComponent translation, String id, Progression[] neededProgression, SubCategoryBase subBase, CategoryBase catBase, ItemStack item, TranslatableComponent itemLore, RecipeType<?> recipeType, int priority,boolean jsonInit){
        this(translation,id,neededProgression,subBase,catBase,Type.ITEM,item.getItem(),priority,jsonInit);
        this.item = item;
        this.itemLore = itemLore;
        this.recipeType = recipeType;

    }

    AncientFragment(TranslatableComponent translation, String id, Progression[] neededProgression, SubCategoryBase subBase, CategoryBase catBase, List<ItemStack> item,RecipeType<?> type, TranslatableComponent upgradeLore, int priority,boolean jsonInit){
        this(translation,id,neededProgression,subBase,catBase,Type.ITEMS,item.get(0).getItem(),priority,jsonInit);
        this.stacks = item;
        this.itemLore = upgradeLore;
        this.recipeType = type;
    }
    //json constructors end

    private void addFragToList(){

        ALL_FRAGMENTS.add(this);
    }

    public static AncientFragment[] getAllFragments(){
        AncientFragment[] f = new AncientFragment[ALL_FRAGMENTS.size()];
        return ALL_FRAGMENTS.toArray(f);
    }

    public CategoryBase getCategory() {
        return category;
    }

    public SubCategoryBase getSubCategory() {
        return subCategory;
    }

    public List<ItemStack> getStacks() {
        return stacks;
    }

    public RecipeType<?> getRecipeType() {
        return recipeType;
    }

    public TranslatableComponent getItemDescription(){
        return itemLore;
    }

    public TranslatableComponent getLore() {
        return lore;
    }

    @Deprecated
    public BookEntry getEntry() {
        return entry;
    }

    public Multiblocks getStructure() {
        return structure;
    }

    public ItemStack getItem() {
        return item;
    }

    public Item getIcon() {
        return icon;
    }

    public TranslatableComponent getTranslation() {
        return translation.plainCopy();
    }

    public Progression[] getNeededProgression() {
        return neededProgression;
    }

    public String getId() {
        return id;
    }

    public Type getType() {
        return type;
    }

    public String getScreenID() {
        return screenID;
    }


    public enum Type{
        STRUCTURE,
        ITEM,
        INFORMATION,
        UPGRADE,
        ITEMS,
        CUSTOM;

        public static Type byID(String id){
            for (Type t : Type.class.getEnumConstants()){
                if (t.name().equals(id)){
                    return t;
                }
            }
            return null;
        }
    }

    public int getPriority() {
        return priority;
    }

    public static AncientFragment getFragmentByID(String id){
        return FRAGMENTS_ID_MAP.containsKey(id) ? FRAGMENTS_ID_MAP.get(id) : null;
    }

    public static TranslatableComponent tx(String a){
        return new TranslatableComponent(a);
    }



    private static Progression[] r(Progression... a){
        return a;
    }



    public static List<AncientFragment> deserializeFragments(List<JsonObject> serializedFragments){
        if (serializedFragments == null) return null;

        List<AncientFragment> fragments = new ArrayList<>();
        for (JsonObject jFragment : serializedFragments){
            Type type = Type.valueOf(GsonHelper.getAsString(jFragment,"type").toUpperCase(Locale.ROOT));


            TranslatableComponent translation = new TranslatableComponent(GsonHelper.getAsString(jFragment,"translation_id"));
            TranslatableComponent lore = new TranslatableComponent(GsonHelper.getAsString(jFragment,"translation_id_lore"));
            String id = GsonHelper.getAsString(jFragment,"unique_id");
            CategoryBase catBase = CategoryBase.valueOf(GsonHelper.getAsString(jFragment,"category_base").toUpperCase(Locale.ROOT));
            SubCategoryBase subBase = SubCategoryBase.valueOf(GsonHelper.getAsString(jFragment,"sub_category_base").toUpperCase(Locale.ROOT));
            ProgressionStage stage = ProgressionStage.valueOf(GsonHelper.getAsString(jFragment,"progression_stage").toUpperCase(Locale.ROOT));
            int priority = GsonHelper.getAsInt(jFragment,"priority");

            if (type == Type.ITEMS){
                RecipeType<?> recipeType = typeById(GsonHelper.getAsString(jFragment,"recipe_type"));
                if (recipeType == null || recipeType == SolarForge.SOLAR_SMELTING) continue;
                List<ItemStack> items = getItemsFromJSON(jFragment.getAsJsonArray("items"));
                if (items.isEmpty()) continue;
                AncientFragment fragment = new AncientFragment(translation,id,stage.ALL_PROGRESSIONS,subBase,catBase,items,recipeType,lore,priority,true);
                fragments.add(fragment);
            }else if (type == Type.INFORMATION){
                ItemStack item = GsonHelper.getAsItem(jFragment.getAsJsonObject("icon"),"item").getDefaultInstance();
                AncientFragment fragment = new AncientFragment(translation,id,stage.ALL_PROGRESSIONS,subBase,catBase,item.getItem(),lore,priority,true);
                fragments.add(fragment);
            }else if (type == Type.ITEM){
                RecipeType<?> recipeType = typeById(GsonHelper.getAsString(jFragment,"recipe_type"));
                ItemStack item = GsonHelper.getAsItem(jFragment.getAsJsonObject("recipe_item"),"item").getDefaultInstance();
                AncientFragment fragment = new AncientFragment(translation,id,stage.ALL_PROGRESSIONS,subBase,catBase,item,lore,recipeType,priority,true);
                fragments.add(fragment);
            }
        }
        return fragments;
    }

    private static RecipeType<?> typeById(String id){
        if (id.equals("infusing")) return SolarForge.INFUSING_RECIPE_TYPE;
        if (id.equals("infusing_crafting")) return SolarForge.INFUSING_CRAFTING_RECIPE_TYPE;
        if (id.equals("smelting")) return SolarForge.SOLAR_SMELTING;
        return null;
    }

    private static List<ItemStack> getItemsFromJSON(JsonArray array){
        List<ItemStack> items = new ArrayList<>();
        for (JsonElement f : array){
            ItemStack item = GsonHelper.getAsItem(f.getAsJsonObject(),"item").getDefaultInstance();
            items.add(item);
        }
        return items;
    }

}
