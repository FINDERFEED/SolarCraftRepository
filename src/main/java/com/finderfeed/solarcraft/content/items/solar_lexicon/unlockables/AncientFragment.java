package com.finderfeed.solarcraft.content.items.solar_lexicon.unlockables;

import com.finderfeed.solarcraft.SolarCraft;
import com.finderfeed.solarcraft.content.items.solar_lexicon.ProgressionStage;
import com.finderfeed.solarcraft.content.items.solar_lexicon.progressions.Progression;
import com.finderfeed.solarcraft.content.items.solar_lexicon.structure.category.CategoryBase;
import com.finderfeed.solarcraft.content.items.solar_lexicon.structure.subcategory.SubCategoryBase;

import com.finderfeed.solarcraft.helpers.multiblock.MultiblockStructure;
import com.finderfeed.solarcraft.helpers.multiblock.Multiblocks;
import com.finderfeed.solarcraft.registries.SCConfigs;
import com.finderfeed.solarcraft.registries.items.SCItems;
import com.finderfeed.solarcraft.registries.recipe_types.SCRecipeTypes;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.network.chat.Component;

import static com.finderfeed.solarcraft.registries.items.SCItems.*;

import java.util.*;
import java.util.function.Supplier;

public class AncientFragment {

    public static final Map<Item,AncientFragment> CRAFTING_TYPE_ITEMS = new HashMap<>();
    public static final Map<MultiblockStructure,AncientFragment> STRUCTURE_FRAGMENTS = new HashMap<>();


    public static List<AncientFragment> ALL_FRAGMENTS = new ArrayList<>();
    public static List<AncientFragment> CLIENTSIDE_FRAGMENTS_CACHE = new ArrayList<>();

    public static final AncientFragment RUNIC_TABLE = new AncientFragment("runic_table",null,SubCategoryBase.BEGGINING,CategoryBase.BEGGINING_INFO,ls(ItemWithRecipe.of(SCItems.RUNIC_TABLE.get().getDefaultInstance(),"runic_table")),RecipeType.CRAFTING,0).addReferences("fragment","lexicon");

    public static final AncientFragment FRAGMENT = new AncientFragment("fragment",null,SubCategoryBase.BEGGINING,CategoryBase.BEGGINING_INFO, SCItems.INFO_FRAGMENT.get(),0).addReferences("runic_table");
    public static final AncientFragment LEXICON = new AncientFragment("lexicon",null,SubCategoryBase.BEGGINING,CategoryBase.BEGGINING_INFO, SCItems.SOLAR_LEXICON.get(),0).addReferences("fragment");

    public static final AncientFragment RUNES = new AncientFragment("runes",null,SubCategoryBase.BEGGINING,CategoryBase.BEGGINING_INFO,ls(ItemWithRecipe.of(SOLAR_RUNE_ARDO.get().getDefaultInstance(),"runestone")),RecipeType.CRAFTING,0).addReferences("runic_table","fragment");


    public static final AncientFragment DUSTS = new AncientFragment("dusts",null,SubCategoryBase.WORLD,CategoryBase.EXPLORATION, SCItems.ENERGY_DUST.get(),1);

    public static final AncientFragment RUNIC_ENERGY_PYLON = new AncientFragment("runic_energy_pylon",null,SubCategoryBase.WORLD,CategoryBase.EXPLORATION, RUNE_ENERGY_PYLON_ITEM_PLACEHOLDER.get(),1);

    public static final AncientFragment INFUSING_CRAFTING_TABLE = new AncientFragment("infusing_crafting_table",null,SubCategoryBase.BEGINNER_BLOCKS,CategoryBase.BEGINNER,ls(ItemWithRecipe.of(INFUSING_TABLE.get().getDefaultInstance(),"infusing_crafting_table")), RecipeType.CRAFTING,1);


    public static final AncientFragment WAND = new AncientFragment("wand",null,SubCategoryBase.BEGGINING,CategoryBase.BEGGINING_INFO,ls(ItemWithRecipe.of(SOLAR_WAND.get().getDefaultInstance(),"solar_wand")), RecipeType.CRAFTING,1);

    public static final AncientFragment HIDDEN_ORES = new AncientFragment("hidden_ores",ProgressionStage.PRE_BEGGINING.ALL_PROGRESSIONS,SubCategoryBase.WORLD,CategoryBase.EXPLORATION, Items.STONE,1);
    public static final AncientFragment THROWABLE_LIGHT = new AncientFragment("throwable_light",null,SubCategoryBase.BEGINNER_ITEMS,CategoryBase.BEGINNER,ls(ItemWithRecipe.of(THROWN_LIGHT.get().getDefaultInstance(),"thrown_light")), RecipeType.CRAFTING,1);
    public static final AncientFragment TELEPORTATION_STONE = new AncientFragment("teleportation_stone",null,SubCategoryBase.BEGINNER_ITEMS,CategoryBase.BEGINNER,ls(ItemWithRecipe.of(SCItems.TELEPORTATION_STONE.get().getDefaultInstance(),"teleportation_stone")), RecipeType.CRAFTING,1);

    public static final AncientFragment SOLAR_STONES = new AncientFragment("solar_stones", ProgressionStage.PRE_BEGGINING.ALL_PROGRESSIONS,SubCategoryBase.BEGINNER_BLOCKS,CategoryBase.BEGINNER, List.of(
            ItemWithRecipe.of(SCItems.SOLAR_STONE_BRICKS.get().getDefaultInstance(),"solar_stone_bricks"),ItemWithRecipe.of(SCItems.SOLAR_STONE_COLLUMN.get().getDefaultInstance(),"solar_stone_collumn"),
            ItemWithRecipe.of(SCItems.SOLAR_STONE_COLLUMN_HORIZONTAL.get().getDefaultInstance(),"solar_stone_collumn_horizontal"),ItemWithRecipe.of(SCItems.SOLAR_STONE_STAIRS.get().getDefaultInstance(),"solar_stone_stairs"),
            ItemWithRecipe.of(SCItems.SOLAR_STONE_CHISELED.get().getDefaultInstance(),"chiseled_solar_stone"),ItemWithRecipe.of(SCItems.ENERGIZED_STONE.get().getDefaultInstance(),"energized_stone"),
            ItemWithRecipe.of(SOLAR_STONE_SLAB.get().getDefaultInstance(),"solar_stone_slab")
    ), SCRecipeTypes.INFUSING_CRAFTING.get(),1);

    public static final AncientFragment MAGISTONE = new AncientFragment("magistone", ProgressionStage.PRE_BEGGINING.ALL_PROGRESSIONS,SubCategoryBase.BEGINNER_BLOCKS,CategoryBase.BEGINNER, List.of(
            ItemWithRecipe.of(MAGISTONE_BRICKS.get().getDefaultInstance(),"magistone_bricks"),ItemWithRecipe.of(MAGISTONE_COLUMN.get().getDefaultInstance(),"magistone_column"),
            ItemWithRecipe.of(MAGISTONE_RAY.get().getDefaultInstance(),"magistone_ray"),ItemWithRecipe.of(MAGISTONE_STAIRS.get().getDefaultInstance(),"magistone_stairs"),
            ItemWithRecipe.of(CHISELED_MAGISTONE.get().getDefaultInstance(),"chiseled_magistone"), ItemWithRecipe.of(MAGISTONE_SLAB.get().getDefaultInstance(),"magistone_slab")
    ), SCRecipeTypes.INFUSING_CRAFTING.get(),1);



    public static final AncientFragment SPEED_ROAD = new AncientFragment("speed_road",ProgressionStage.PRE_BEGGINING.ALL_PROGRESSIONS,SubCategoryBase.BEGINNER_BLOCKS,CategoryBase.BEGINNER, ls(ItemWithRecipe.of(SCItems.SPEED_ROAD.get().getDefaultInstance(),"infusing_crafting_speed_road")), SCRecipeTypes.INFUSING_CRAFTING.get(),2);
    public static final AncientFragment HEATER = new AncientFragment("heater",ProgressionStage.PRE_BEGGINING.ALL_PROGRESSIONS,SubCategoryBase.BEGINNER_BLOCKS,CategoryBase.BEGINNER, ls(ItemWithRecipe.of(HEATER_BLOCK.get().getDefaultInstance(),"infusing_crafting_heater")), SCRecipeTypes.INFUSING_CRAFTING.get(),2);
    public static final AncientFragment EIGHT_ELEMENTS = new AncientFragment("eight_elements",ProgressionStage.BEGGINING_2.ALL_PROGRESSIONS,SubCategoryBase.WORLD,CategoryBase.EXPLORATION,"eight_elements_lore", SCItems.SOLAR_RUNE_ARDO.get().getDefaultInstance(),1);
    public static final AncientFragment INFUSED_CRYSTALS = new AncientFragment("crystals", ProgressionStage.BEGGINING_2.ALL_PROGRESSIONS,SubCategoryBase.BEGINNER_ITEMS,CategoryBase.BEGINNER, List.of(
            ItemWithRecipe.of(SCItems.ARDO_CRYSTAL.get().getDefaultInstance(),"infusing_crafting_ardo_crystal"),ItemWithRecipe.of(SCItems.FIRA_CRYSTAL.get().getDefaultInstance(),"infusing_crafting_fira_crystal"),
            ItemWithRecipe.of(SCItems.TERA_CRYSTAL.get().getDefaultInstance(),"infusing_crafting_tera_crystal"),ItemWithRecipe.of(SCItems.URBA_CRYSTAL.get().getDefaultInstance(),"infusing_crafting_urba_crystal"),
            ItemWithRecipe.of(SCItems.GIRO_CRYSTAL.get().getDefaultInstance(),"infusing_crafting_giro_crystal"),ItemWithRecipe.of(SCItems.ULTIMA_CRYSTAL.get().getDefaultInstance(),"infusing_crafting_ultima_crystal"),
            ItemWithRecipe.of(SCItems.KELDA_CRYSTAL.get().getDefaultInstance(),"infusing_crafting_kelda_crystal"),ItemWithRecipe.of(SCItems.ZETA_CRYSTAL.get().getDefaultInstance(),"infusing_crafting_zeta_crystal")
    ), SCRecipeTypes.INFUSING_CRAFTING.get(),2);


    public static final AncientFragment SOLAR_INFUSER = new AncientFragment("solar_infuser",ProgressionStage.FORGE.ALL_PROGRESSIONS,SubCategoryBase.BEGINNER_BLOCKS,CategoryBase.BEGINNER, List.of(
            ItemWithRecipe.of(SCItems.INFUSER_ITEM.get().getDefaultInstance(),"solar_infuser"),
            ItemWithRecipe.of(SCItems.SOLAR_INFUSION_POOL.get().getDefaultInstance(),"solar_forge_infusion_pool")
    ), SCRecipeTypes.INFUSING_CRAFTING.get(),1).addReferences("solar_infuser_structure","solar_infuser_structure_2","solar_infuser_structure_3");


    public static final AncientFragment DEATH = new AncientFragment("death",ProgressionStage.PRE_BEGGINING.ALL_PROGRESSIONS,SubCategoryBase.BEGGINING,CategoryBase.BEGGINING_INFO, Items.SKELETON_SKULL,1);
    public static final AncientFragment SOLAR_FORGE = new AncientFragment("solar_forge",ProgressionStage.PRE_FORGE.ALL_PROGRESSIONS,SubCategoryBase.BEGINNER_BLOCKS,CategoryBase.BEGINNER, ls(ItemWithRecipe.of(SCItems.SOLAR_FORGE_ITEM.get().getDefaultInstance(),"infusing_crafting_solar_forge")), SCRecipeTypes.INFUSING_CRAFTING.get(),1);
    public static final AncientFragment ENDER_RADAR = new AncientFragment("ender_radar",ProgressionStage.PRE_FORGE.SELF_PROGRESSIONS,SubCategoryBase.BEGINNER_ITEMS,CategoryBase.BEGINNER, ls(ItemWithRecipe.of(SCItems.ENDER_RADAR.get().getDefaultInstance(),"ender_radar")), SCRecipeTypes.INFUSING_CRAFTING.get(),2);



    public static final AncientFragment SOLAR_ARMOR = new AncientFragment("solar_armor",ProgressionStage.FORGE.ALL_PROGRESSIONS,SubCategoryBase.ARMOR,CategoryBase.ARMOR,List.of(
            ItemWithRecipe.of(SOLAR_BOOTS.get().getDefaultInstance(),"infusing_new_solar_boots"),ItemWithRecipe.of(SOLAR_LEGGINGS.get().getDefaultInstance(),"infusing_new_solar_leggins"),
            ItemWithRecipe.of(SOLAR_CHESTPLATE.get().getDefaultInstance(),"infusing_new_solar_chestplate"),ItemWithRecipe.of(SOLAR_HELMET.get().getDefaultInstance(),"infusing_new_solar_helmet")
    ), SCRecipeTypes.INFUSING.get(),2);
    public static final AncientFragment ITEM_MAGNET = new AncientFragment("item_magnet",ProgressionStage.AFTER_INFUSER.ALL_PROGRESSIONS,SubCategoryBase.BEGINNER_BLOCKS,CategoryBase.BEGINNER,ls(ItemWithRecipe.of(SCItems.MAGNET_BLOCK.get().getDefaultInstance(),"infusing_new_magnet_block")), SCRecipeTypes.INFUSING.get(),2);
    public static final AncientFragment ILLIDIUM_INGOT = new AncientFragment("illidium_ingot",ProgressionStage.AFTER_INFUSER.ALL_PROGRESSIONS,SubCategoryBase.BEGINNER_MATERIALS,CategoryBase.BEGINNER,ls(ItemWithRecipe.of(SCItems.ILLIDIUM_INGOT.get().getDefaultInstance(),"infusing_new_illidium_ingot")), SCRecipeTypes.INFUSING.get(),2);
    public static final AncientFragment ALGADIUM_INGOT = new AncientFragment("algadium_ingot",ProgressionStage.AFTER_INFUSER.ALL_PROGRESSIONS,SubCategoryBase.BEGINNER_MATERIALS,CategoryBase.BEGINNER,ls(ItemWithRecipe.of(SCItems.ALGADIUM_INGOT.get().getDefaultInstance(),"infusing_new_algadium_ingot")), SCRecipeTypes.INFUSING.get(),2);
    public static final AncientFragment BLOCK_BOOMERANG = new AncientFragment("block_boomerang",ProgressionStage.AFTER_INFUSER.ALL_PROGRESSIONS,SubCategoryBase.BEGINNER_ITEMS,CategoryBase.BEGINNER,ls(ItemWithRecipe.of(SCItems.BLOCK_BOOMERANG.get().getDefaultInstance(),"infusing_new_block_boomerang")), SCRecipeTypes.INFUSING.get(),2);
    public static final AncientFragment ILLIDIUM_SWORD = new AncientFragment("illidium_sword",ProgressionStage.AFTER_INFUSER.ALL_PROGRESSIONS,SubCategoryBase.BEGINNER_ITEMS,CategoryBase.BEGINNER,ls(ItemWithRecipe.of(SCItems.ILLIDIUM_SWORD.get().getDefaultInstance(),"infusing_new_illidium_sword")), SCRecipeTypes.INFUSING.get(),2).addReferences("illidium_ingot");
    public static final AncientFragment ILLIDIUM_AXE = new AncientFragment("illidium_axe",ProgressionStage.AFTER_INFUSER.ALL_PROGRESSIONS,SubCategoryBase.BEGINNER_ITEMS,CategoryBase.BEGINNER,ls(ItemWithRecipe.of(SCItems.ILLIDIUM_AXE.get().getDefaultInstance(),"infusing_new_illidium_axe")), SCRecipeTypes.INFUSING.get(),2).addReferences("illidium_ingot");
    public static final AncientFragment ILLIDIUM_SHOVEL = new AncientFragment("illidium_shovel",ProgressionStage.AFTER_INFUSER.ALL_PROGRESSIONS,SubCategoryBase.BEGINNER_ITEMS,CategoryBase.BEGINNER,ls(ItemWithRecipe.of(SCItems.ILLIDIUM_SHOVEL.get().getDefaultInstance(),"infusing_new_illidium_shovel")), SCRecipeTypes.INFUSING.get(),2).addReferences("illidium_ingot");
    public static final AncientFragment ILLIDIUM_HOE = new AncientFragment("illidium_hoe",ProgressionStage.AFTER_INFUSER.ALL_PROGRESSIONS,SubCategoryBase.BEGINNER_ITEMS,CategoryBase.BEGINNER,ls(ItemWithRecipe.of(SCItems.GROWTH_HOE.get().getDefaultInstance(),"infusing_new_illidium_hoe")), SCRecipeTypes.INFUSING.get(),2).addReferences("illidium_ingot");
    public static final AncientFragment ILLIDIUM_PICKAXE = new AncientFragment("illidium_pickaxe",ProgressionStage.AFTER_INFUSER.ALL_PROGRESSIONS,SubCategoryBase.BEGINNER_ITEMS,CategoryBase.BEGINNER,ls(ItemWithRecipe.of(SCItems.VEIN_MINER.get().getDefaultInstance(),"infusing_new_miner_item")), SCRecipeTypes.INFUSING.get(),2).addReferences("illidium_ingot");
    public static final AncientFragment RUNIC_ENERGY_CHARGER = new AncientFragment("re_charger",ProgressionStage.AFTER_INFUSER.ALL_PROGRESSIONS,SubCategoryBase.BEGINNER_BLOCKS,CategoryBase.BEGINNER,ls(ItemWithRecipe.of(SCItems.RUNIC_ENERGY_CHARGER.get().getDefaultInstance(),"infusing_runic_energy_charger")), SCRecipeTypes.INFUSING.get(),1);


    public static final AncientFragment AURA_HEALER = new AncientFragment("aura_healer",ProgressionStage.AFTER_CATALYSTS.ALL_PROGRESSIONS,SubCategoryBase.BEGINNER_BLOCKS,CategoryBase.BEGINNER,ls(ItemWithRecipe.of(SCItems.AURA_HEALER.get().getDefaultInstance(),"infusing_new_aura_healer_block")), SCRecipeTypes.INFUSING.get(),2).addReferences("aura_healer_structure");
    public static final AncientFragment DISC_LAUNCHER = new AncientFragment("disc_launcher",ProgressionStage.AFTER_CATALYSTS.ALL_PROGRESSIONS,SubCategoryBase.BEGINNER_ITEMS,CategoryBase.BEGINNER,ls(ItemWithRecipe.of(SCItems.SOLAR_DISC_LAUNCHER.get().getDefaultInstance(),"infusing_new_solar_disc_launcher")), SCRecipeTypes.INFUSING.get(),2).addReferences("re_charger");
    public static final AncientFragment TURRET_RADAR = new AncientFragment("turret_radar",ProgressionStage.AFTER_CATALYSTS.ALL_PROGRESSIONS,SubCategoryBase.BEGINNER_MATERIALS,CategoryBase.BEGINNER,ls(ItemWithRecipe.of(SCItems.TURRET_RADAR.get().getDefaultInstance(),"infusing_new_turret_radar")), SCRecipeTypes.INFUSING.get(),2);
    public static final AncientFragment SOLAR_TURRET = new AncientFragment("solar_turret",ProgressionStage.AFTER_CATALYSTS.ALL_PROGRESSIONS,SubCategoryBase.BEGINNER_BLOCKS,CategoryBase.BEGINNER,ls(ItemWithRecipe.of(SCItems.TURRET_BLOCK.get().getDefaultInstance(),"infusing_new_turret_block")), SCRecipeTypes.INFUSING.get(),2).addReferences("charged_qualadium_ingot");
    public static final AncientFragment GLOVES_OF_REACH = new AncientFragment("gloves_of_reach",ProgressionStage.AFTER_CATALYSTS.ALL_PROGRESSIONS,SubCategoryBase.BEGINNER_ITEMS,CategoryBase.BEGINNER,ls(ItemWithRecipe.of(SCItems.REACH_GLOVES.get().getDefaultInstance(),"infusing_new_gloves_of_reach")), SCRecipeTypes.INFUSING.get(),2);
//    public static final AncientFragment MANA_REGEN_AMULET = new AncientFragment(tx("solar_fragment.mana_regen_amulet"),"mana_regen_amulet",ProgressionStage.AFTER_INFUSER.ALL_PROGRESSIONS,SubCategoryBase.BEGINNER_ITEMS,CategoryBase.BEGINNER,ItemWithRecipe.of(ItemsRegister.SOLAR_MANA_AMULET.get().getDefaultInstance(),"infusing_new_solar_mana_amulet"),tx("mana_regen_amulet.lore"), SolarcraftRecipeTypes.INFUSING.get(),2);
    public static final AncientFragment CATALYSTS = new AncientFragment("catalysts",ProgressionStage.AFTER_CATALYSTS.ALL_PROGRESSIONS,SubCategoryBase.SKILLED_ITEMS,CategoryBase.SKILLED,List.of(
            ItemWithRecipe.of(CATALYST_BASE.get().getDefaultInstance(),"infusing_crafting_catalyst_base"),ItemWithRecipe.of(ARDO_RUNE_BLOCK.get().getDefaultInstance(),"ardo_rune_block"),
            ItemWithRecipe.of(TERA_RUNE_BLOCK.get().getDefaultInstance(),"tera_rune_block"),ItemWithRecipe.of(URBA_RUNE_BLOCK.get().getDefaultInstance(),"urba_rune_block"),
            ItemWithRecipe.of(KELDA_RUNE_BLOCK.get().getDefaultInstance(),"kelda_rune_block"),ItemWithRecipe.of(ZETA_RUNE_BLOCK.get().getDefaultInstance(),"zeta_rune_block"),
            ItemWithRecipe.of(FIRA_RUNE_BLOCK.get().getDefaultInstance(),"fira_rune_block"),ItemWithRecipe.of(ULTIMA_RUNE_BLOCK.get().getDefaultInstance(),"ultima_rune_block"),
            ItemWithRecipe.of(GIRO_RUNE_BLOCK.get().getDefaultInstance(),"giro_rune_block")
    ), SCRecipeTypes.INFUSING_CRAFTING.get(),1);
    public static final AncientFragment CRYSTAL_CORES = new AncientFragment("crystal_cores",ProgressionStage.AFTER_CATALYSTS.ALL_PROGRESSIONS,SubCategoryBase.SKILLED_MATERIALS,CategoryBase.SKILLED,List.of(
            ItemWithRecipe.of(CRYSTAL_CORE.get().getDefaultInstance(),"infusing_new_crystal_core"),ItemWithRecipe.of(SCItems.ENERGY_CORE.get().getDefaultInstance(),"infusing_new_energy_core"),
            ItemWithRecipe.of(VOID_CORE.get().getDefaultInstance(),"infusing_new_void_core"),ItemWithRecipe.of(SCItems.MATERIALIZATION_CORE.get().getDefaultInstance(),"infusing_new_materialization_core"),
            ItemWithRecipe.of(CRYSTAL_STAR.get().getDefaultInstance(),"infusing_new_crystal_star")
    ), SCRecipeTypes.INFUSING.get(),2);
    public static final AncientFragment SMALL_SOLAR_REACTOR = new AncientFragment("small_solar_reactor",ProgressionStage.AFTER_CATALYSTS.ALL_PROGRESSIONS,SubCategoryBase.SKILLED_MATERIALS,CategoryBase.SKILLED,ls(ItemWithRecipe.of(SCItems.SMALL_SOLAR_REACTOR.get().getDefaultInstance(),"infusing_new_small_solar_reactor")), SCRecipeTypes.INFUSING.get(),2);

    public static final AncientFragment SOLAR_LENS = new AncientFragment("solar_lens",ProgressionStage.PRE_LENS.ALL_PROGRESSIONS,SubCategoryBase.SKILLED_ITEMS,CategoryBase.SKILLED,ls(ItemWithRecipe.of(SCItems.SOLAR_LENS.get().getDefaultInstance(),"infusing_new_solar_lens")), SCRecipeTypes.INFUSING.get(),3);
    public static final AncientFragment RUNIC_ENERGY_REPEATER = new AncientFragment("runic_energy_repeater",ProgressionStage.PRE_LENS.ALL_PROGRESSIONS,SubCategoryBase.RUNIC_ENERGY_TRANSMITTING,CategoryBase.RUNIC_ENERGY,ls(ItemWithRecipe.of(SCItems.REPEATER.get().getDefaultInstance(),"infusing_new_repeater")), SCRecipeTypes.INFUSING.get(),3).addReferences("runic_energy","solar_infuser","multirune_block","catalysts");

    public static final AncientFragment CORRUPTED_SHARD = new AncientFragment("corrupted_shard",ProgressionStage.AFTER_LENS.ALL_PROGRESSIONS,SubCategoryBase.WORLD,CategoryBase.EXPLORATION, SCItems.CORRUPTED_SHARD_ITEM.get(),3);

    public static final AncientFragment RUNIC_ENERGY_LORE = new AncientFragment("runic_energy",ProgressionStage.AFTER_LENS.ALL_PROGRESSIONS,SubCategoryBase.RUNIC_ENERGY_TRANSMITTING,CategoryBase.RUNIC_ENERGY,"runic_energy_lore", RUNE_ENERGY_PYLON_ITEM_PLACEHOLDER.get().getDefaultInstance(),3).addReferences("runic_energy_repeater");
    public static final AncientFragment QUALADIUM_INGOT = new AncientFragment("qualadium_ingot",ProgressionStage.AFTER_LENS.ALL_PROGRESSIONS,SubCategoryBase.SKILLED_MATERIALS,CategoryBase.SKILLED,ls(ItemWithRecipe.of(SCItems.QUALADIUM_INGOT.get().getDefaultInstance(),"solar_smelting_qualadium_ingot")), SCRecipeTypes.SMELTING.get(),4).addReferences("solar_lens");
    public static final AncientFragment GEMINIUM_INGOT = new AncientFragment("geminium_ingot",ProgressionStage.AFTER_LENS.ALL_PROGRESSIONS,SubCategoryBase.SKILLED_MATERIALS,CategoryBase.SKILLED,ls(ItemWithRecipe.of(SCItems.GEMINIUM_INGOT.get().getDefaultInstance(),"smelting_geminium_ingot")), SCRecipeTypes.SMELTING.get(),4).addReferences("solar_lens");
    public static final AncientFragment ENDERIUM_INGOT = new AncientFragment("enderium_ingot",ProgressionStage.AFTER_LENS.ALL_PROGRESSIONS,SubCategoryBase.SKILLED_MATERIALS,CategoryBase.SKILLED,ls(ItemWithRecipe.of(SCItems.ENDERIUM_INGOT.get().getDefaultInstance(),"smelting_enderium_ingot")), SCRecipeTypes.SMELTING.get(),4).addReferences("solar_lens");
    public static final AncientFragment ENERGETIC_INGOT = new AncientFragment("energetic_ingot",ProgressionStage.AFTER_LENS.ALL_PROGRESSIONS,SubCategoryBase.SKILLED_MATERIALS,CategoryBase.SKILLED,ls(ItemWithRecipe.of(SCItems.ENERGETIC_INGOT.get().getDefaultInstance(),"smelting_energetic_ingot")), SCRecipeTypes.SMELTING.get(),4).addReferences("solar_lens");


    public static final AncientFragment MULTIRUNE_BLOCK = new AncientFragment("multirune_block",ProgressionStage.AFTER_LENS.ALL_PROGRESSIONS,SubCategoryBase.RUNIC_ENERGY_TRANSMITTING,CategoryBase.RUNIC_ENERGY,ls(ItemWithRecipe.of(MULTIREPEATER_BLOCK.get().getDefaultInstance(),"infusing_multirune_block")), SCRecipeTypes.INFUSING.get(),5).addReferences("runic_energy_repeater");

    public static final AncientFragment RUNIC_ENERGY_CORE = new AncientFragment("runic_energy_core",ProgressionStage.AFTER_LENS.ALL_PROGRESSIONS,SubCategoryBase.SKILLED_BLOCKS,CategoryBase.SKILLED,ls(ItemWithRecipe.of(SCItems.RUNIC_ENERGY_CORE.get().getDefaultInstance(),"infusing_runic_energy_core")), SCRecipeTypes.INFUSING.get(),5).addReferences("runic_core");

    public static final AncientFragment ELEMENT_WEAVER = new AncientFragment("element_weaver",ProgressionStage.AFTER_LENS.ALL_PROGRESSIONS,SubCategoryBase.SKILLED_BLOCKS,CategoryBase.SKILLED,ls(ItemWithRecipe.of(SCItems.ELEMENT_WEAVER.get().getDefaultInstance(),"infusing_element_weaver")), SCRecipeTypes.INFUSING.get(),5).addReferences("runic_energy_repeater");

    public static final AncientFragment SOLAR_GOD_SWORD = new AncientFragment("solar_god_sword",ProgressionStage.AFTER_LENS.ALL_PROGRESSIONS,SubCategoryBase.SKILLED_ITEMS,CategoryBase.SKILLED,ls(ItemWithRecipe.of(SCItems.SOLAR_GOD_SWORD.get().getDefaultInstance(),"infusing_new_solar_god_sword")), SCRecipeTypes.INFUSING.get(),5).addReferences("solar_god_sword_upgrade");
    public static final AncientFragment SOLAR_GOD_PICKAXE = new AncientFragment("solar_god_pickaxe",ProgressionStage.AFTER_LENS.ALL_PROGRESSIONS,SubCategoryBase.SKILLED_ITEMS,CategoryBase.SKILLED,ls(ItemWithRecipe.of(SCItems.SOLAR_GOD_PICKAXE.get().getDefaultInstance(),"infusing_new_solar_god_pickaxe")), SCRecipeTypes.INFUSING.get(),5).addReferences("solar_god_pickaxe_upgrade");
    public static final AncientFragment SOLAR_GOD_SHIELD = new AncientFragment("solar_god_shield",ProgressionStage.AFTER_LENS.ALL_PROGRESSIONS,SubCategoryBase.SKILLED_ITEMS,CategoryBase.SKILLED,ls(ItemWithRecipe.of(SCItems.SOLAR_GOD_SHIELD.get().getDefaultInstance(),"infusing_new_solar_god_shield")), SCRecipeTypes.INFUSING.get(),5);
    public static final AncientFragment QUALADIUM_SWORD = new AncientFragment("qualadium_sword",ProgressionStage.AFTER_LENS.ALL_PROGRESSIONS,SubCategoryBase.SKILLED_ITEMS,CategoryBase.SKILLED,ls(ItemWithRecipe.of(SCItems.QUALADIUM_SWORD.get().getDefaultInstance(),"infusing_new_qualadium_sword")), SCRecipeTypes.INFUSING.get(),5).addReferences("qualadium_ingot");
    public static final AncientFragment QUALADIUM_AXE = new AncientFragment("qualadium_axe",ProgressionStage.AFTER_LENS.ALL_PROGRESSIONS,SubCategoryBase.SKILLED_ITEMS,CategoryBase.SKILLED,ls(ItemWithRecipe.of(SCItems.QUALADIUM_AXE.get().getDefaultInstance(),"infusing_new_qualadium_axe")), SCRecipeTypes.INFUSING.get(),5).addReferences("qualadium_ingot");
    public static final AncientFragment QUALADIUM_SHOVEL = new AncientFragment("qualadium_shovel",ProgressionStage.AFTER_LENS.ALL_PROGRESSIONS,SubCategoryBase.SKILLED_ITEMS,CategoryBase.SKILLED,ls(ItemWithRecipe.of(SCItems.QUALADIUM_SHOVEL.get().getDefaultInstance(),"infusing_new_qualadium_shovel")), SCRecipeTypes.INFUSING.get(),5).addReferences("qualadium_ingot");
    public static final AncientFragment QUALADIUM_HOE = new AncientFragment("qualadium_hoe",ProgressionStage.AFTER_LENS.ALL_PROGRESSIONS,SubCategoryBase.SKILLED_ITEMS,CategoryBase.SKILLED,ls(ItemWithRecipe.of(SCItems.QUALADIUM_HOE.get().getDefaultInstance(),"infusing_new_qualadium_hoe")), SCRecipeTypes.INFUSING.get(),5).addReferences("qualadium_ingot");
    public static final AncientFragment QUALADIUM_PICKAXE = new AncientFragment("qualadium_pickaxe",ProgressionStage.AFTER_LENS.ALL_PROGRESSIONS,SubCategoryBase.SKILLED_ITEMS,CategoryBase.SKILLED,ls(ItemWithRecipe.of(SCItems.QUALADIUM_PICKAXE.get().getDefaultInstance(),"infusing_new_qualadium_pickaxe")), SCRecipeTypes.INFUSING.get(),5).addReferences("qualadium_ingot");
    public static final AncientFragment BONEMEALER = new AncientFragment("bonemealer",ProgressionStage.AFTER_LENS.ALL_PROGRESSIONS ,SubCategoryBase.SKILLED_BLOCKS,CategoryBase.SKILLED,ls(ItemWithRecipe.of(SCItems.BONEMEALER.get().getDefaultInstance(),"infusing_new_bonemealer")), SCRecipeTypes.INFUSING.get(),5);
    public static final AncientFragment ENCHANTER = new AncientFragment("elemental_enchanter",ProgressionStage.AFTER_LENS.ALL_PROGRESSIONS ,SubCategoryBase.SKILLED_BLOCKS,CategoryBase.SKILLED,ls(ItemWithRecipe.of(SCItems.ENCHANTER.get().getDefaultInstance(),"infusing_new_elemental_enchanter")), SCRecipeTypes.INFUSING.get(),5);
    public static final AncientFragment EXPLOSION_BLOCKER = new AncientFragment("explosion_blocker",ProgressionStage.AFTER_LENS.ALL_PROGRESSIONS ,SubCategoryBase.SKILLED_BLOCKS,CategoryBase.SKILLED,ls(ItemWithRecipe.of(SCItems.EXPLOSION_BLOCKER.get().getDefaultInstance(),"infusing_new_explosion_blocker")), SCRecipeTypes.INFUSING.get(),5).addReferences("explosion_blocker_structure");

    public static final AncientFragment EXPERIENCE_CRYSTAL = new AncientFragment("experience_crystal",ProgressionStage.AFTER_LENS.ALL_PROGRESSIONS,SubCategoryBase.SKILLED_ITEMS,CategoryBase.SKILLED,ls(ItemWithRecipe.of(SCItems.EXPERIENCE_CRYSTAL.get().getDefaultInstance(),"infusing_new_xp_crystal")), SCRecipeTypes.INFUSING.get(),5);
    public static final AncientFragment AMETHYST_CORE = new AncientFragment("amethyst_core",ProgressionStage.AFTER_LENS.ALL_PROGRESSIONS,SubCategoryBase.SKILLED_MATERIALS,CategoryBase.SKILLED,ls(ItemWithRecipe.of(SCItems.AMETHYST_CORE.get().getDefaultInstance(),"infusing_new_amethyst_core")), SCRecipeTypes.INFUSING.get(),4).addReferences("small_solar_reactor");
    public static final AncientFragment RUNIC_CORE = new AncientFragment("runic_core",ProgressionStage.AFTER_LENS.ALL_PROGRESSIONS,SubCategoryBase.SKILLED_MATERIALS,CategoryBase.SKILLED,ls(ItemWithRecipe.of(SCItems.RUNIC_CORE.get().getDefaultInstance(),"infusing_new_runic_core")), SCRecipeTypes.INFUSING.get(),4).addReferences("small_solar_reactor");
    public static final AncientFragment MODULES = new AncientFragment("modules",ProgressionStage.AFTER_LENS.ALL_PROGRESSIONS,SubCategoryBase.SKILLED_ITEMS,CategoryBase.SKILLED,List.of(
            ItemWithRecipe.of(MODULE_APPLIER.get().getDefaultInstance(),"infusing_new_module_table"),ItemWithRecipe.of(PICKAXE_MINER_ABILITY_MODULE.get().getDefaultInstance(),"infusing_new_pickaxe_miner_module"),
            ItemWithRecipe.of(MAGIC_DAMAGE_MODULE_5.get().getDefaultInstance(),"infusing_new_magic_damage_5_module"),ItemWithRecipe.of(SWORD_AUTOHEAL_MODULE.get().getDefaultInstance(),"infusing_new_sword_heal_module"),
            ItemWithRecipe.of(PHYSICAL_DEFENCE_MODULE_10.get().getDefaultInstance(),"infusing_new_module_defence_physical_10"),ItemWithRecipe.of(DISARMING_THORNS_MODULE.get().getDefaultInstance(),"infusing_new_disarming_thorns_module"),
            ItemWithRecipe.of(SWORD_AOE_ATTACK.get().getDefaultInstance(),"infusing_new_sword_aoe_module"),ItemWithRecipe.of(PICKAXE_AUTO_SMELT.get().getDefaultInstance(),"infusing_new_pickaxe_auto_smelt_module"),
            ItemWithRecipe.of(BLESSED_MODULE.get().getDefaultInstance(),"infusing_new_blessed_module"),ItemWithRecipe.of(POISONING_BLADE_MODULE.get().getDefaultInstance(),"infusing_new_poisoning_blade_module"),
            ItemWithRecipe.of(FURY_SWIPES_MODULE.get().getDefaultInstance(),"infusing_new_fury_swipes_module")
    ), SCRecipeTypes.INFUSING.get(),5);

    public static final AncientFragment SOLAR_ENERGY_GENERATOR = new AncientFragment("solar_energy_generator",ProgressionStage.AFTER_LENS.ALL_PROGRESSIONS,SubCategoryBase.MASTER_ENERGY,CategoryBase.MASTER,ls(ItemWithRecipe.of(SCItems.ENERGY_GENERATOR_BLOCK.get().getDefaultInstance(),"infusing_new_solar_energy_generator")), SCRecipeTypes.INFUSING.get(),6).addReferences("solar_energy_repeater","solar_energy_generator_structure");
    public static final AncientFragment SOLAR_CORE = new AncientFragment("solar_core",ProgressionStage.SOLAR_ENERGY.ALL_PROGRESSIONS,SubCategoryBase.MASTER_ENERGY,CategoryBase.MASTER,ls(ItemWithRecipe.of(SCItems.SOLAR_CORE.get().getDefaultInstance(),"infusing_new_solar_core_block")), SCRecipeTypes.INFUSING.get(),6).addReferences("solar_energy_generator","solar_energy_repeater","solar_core_structure");
    public static final AncientFragment SOLAR_ENERGY_REPEATER = new AncientFragment("solar_energy_repeater",ProgressionStage.SOLAR_ENERGY.ALL_PROGRESSIONS,SubCategoryBase.MASTER_ENERGY,CategoryBase.MASTER, ls(ItemWithRecipe.of(SCItems.SOLAR_ENERGY_REPEATER.get().getDefaultInstance(),"infusing_crafting_solar_energy_repeater")), SCRecipeTypes.INFUSING_CRAFTING.get(),6);
//    public static final AncientFragment SOLAR_NETWORK_BINDER = new AncientFragment(tx("solar_fragment.solar_network_binder"),"solar_network_binder",ProgressionStage.SOLAR_ENERGY.ALL_PROGRESSIONS,SubCategoryBase.MASTER_ENERGY,CategoryBase.MASTER, ls(ItemWithRecipe.of(SolarcraftItems.SOLAR_NETWORK_BINDER.get().getDefaultInstance(),"solar_network_binder")),tx("solar_network_binder.lore"),RecipeType.CRAFTING,6).addReferences("solar_energy_repeater");

    public static final AncientFragment RADIANT_CHESTPLATE = new AncientFragment("radiant_cuirass",ProgressionStage.SOLAR_ENERGY.ALL_PROGRESSIONS,SubCategoryBase.ARMOR,CategoryBase.ARMOR,ls(ItemWithRecipe.of(SCItems.RADIANT_CHESTPLATE.get().getDefaultInstance(),"infusing_new_radiant_chestplate")), SCRecipeTypes.INFUSING.get(),8)
            .setTranslatableComponentArguments(()->{
                return List.of("%.1f".formatted(SCConfigs.ITEMS.radiantChestplateEvasionChance * 100));
            });
    public static final AncientFragment MEDIUM_SOLAR_REACTOR = new AncientFragment("medium_solar_reactor",ProgressionStage.SOLAR_ENERGY.ALL_PROGRESSIONS,SubCategoryBase.MASTER_MATERIALS,CategoryBase.MASTER,ls(ItemWithRecipe.of(SCItems.MEDIUM_SOLAR_REACTOR.get().getDefaultInstance(),"infusing_new_medium_solar_reactor")), SCRecipeTypes.INFUSING.get(),7).addReferences("small_solar_reactor");
    public static final AncientFragment ZAP_TURRET = new AncientFragment("zap_turret",ProgressionStage.SOLAR_ENERGY.ALL_PROGRESSIONS,SubCategoryBase.MASTER_ITEMS,CategoryBase.MASTER,ls(ItemWithRecipe.of(SCItems.ZAP_TURRET_BLOCK.get().getDefaultInstance(),"infusing_new_zap_turret")), SCRecipeTypes.INFUSING.get(),8).addReferences("zap_turret_structure");
    public static final AncientFragment CHARGED_QUALADIUM_INGOT = new AncientFragment("charged_qualadium_ingot",ProgressionStage.SOLAR_ENERGY.ALL_PROGRESSIONS,SubCategoryBase.MASTER_MATERIALS,CategoryBase.MASTER,ls(ItemWithRecipe.of(SCItems.CHARGED_QUALADIUM_INGOT.get().getDefaultInstance(),"infusing_new_charged_qualadium_ingot")), SCRecipeTypes.INFUSING.get(),7).addReferences("qualadium_ingot");
    public static final AncientFragment SOLAR_MORTAR = new AncientFragment("solar_mortar",ProgressionStage.SOLAR_ENERGY.ALL_PROGRESSIONS,SubCategoryBase.MASTER_ITEMS,CategoryBase.MASTER,ls(ItemWithRecipe.of(SCItems.SOLAR_MORTAR.get().getDefaultInstance(),"infusing_new_solar_mortar_block")), SCRecipeTypes.INFUSING.get(),8).addReferences("solar_mortar_structure");
    public static final AncientFragment SOLAR_FURNACE = new AncientFragment("solar_furnace",ProgressionStage.SOLAR_ENERGY.ALL_PROGRESSIONS,SubCategoryBase.MASTER_ITEMS,CategoryBase.MASTER,ls(ItemWithRecipe.of(SCItems.SOLAR_FURNACE_BLOCK.get().getDefaultInstance(),"infusing_new_solar_furnace")), SCRecipeTypes.INFUSING.get(),8).addReferences("solar_energy_repeater");
    public static final AncientFragment  TOTEM_OF_IMMORTALITY = new AncientFragment("totem_of_immortality",ProgressionStage.SOLAR_ENERGY.ALL_PROGRESSIONS,SubCategoryBase.MASTER_ITEMS,CategoryBase.MASTER,ls(ItemWithRecipe.of(SCItems.TOTEM_OF_IMMORTALITY.get().getDefaultInstance(),"infusing_new_totem_of_immortality")), SCRecipeTypes.INFUSING.get(),8)
            .setTranslatableComponentArguments(()->{
                return List.of("%d".formatted(SCConfigs.ITEMS.totemOfImmortalityEffectTime / 20));
            })
            ;
    public static final AncientFragment SOLAR_CROSSBOW = new AncientFragment("solar_crossbow",ProgressionStage.SOLAR_ENERGY.ALL_PROGRESSIONS,SubCategoryBase.MASTER_ITEMS,CategoryBase.MASTER,ls(ItemWithRecipe.of(SCItems.ULTRA_CROSSBOW.get().getDefaultInstance(),"infusing_new_solar_crossbow")), SCRecipeTypes.INFUSING.get(),8).addReferences("re_charger")
            .setTranslatableComponentArguments(()->{
                return List.of(
                        "%.1f".formatted(SCConfigs.ITEMS.solarCrossbowDamageGain),
                        "%.1f".formatted(SCConfigs.ITEMS.solarCrossbowMaxDamage)
                );
            });
    public static final AncientFragment LIGHTNING_GUN = new AncientFragment("lightning_gun",ProgressionStage.SOLAR_ENERGY.ALL_PROGRESSIONS,SubCategoryBase.MASTER_ITEMS,CategoryBase.MASTER,ls(ItemWithRecipe.of(SCItems.LIGHTNING_GUN.get().getDefaultInstance(),"infusing_new_lightning_emitter")), SCRecipeTypes.INFUSING.get(),8).addReferences("re_charger")
            .setTranslatableComponentArguments(()->{
                String radius = "%.1f".formatted(SCConfigs.ITEMS.ballLightningExplosionRadius);
                String damage = "%.1f".formatted(SCConfigs.ITEMS.ballLightningDamage);
                return List.of(
                        radius,damage
                );
            });
    public static final AncientFragment DIMENSION_CORE = new AncientFragment("dimension_core",ProgressionStage.SOLAR_ENERGY.ALL_PROGRESSIONS,SubCategoryBase.MASTER_ITEMS,CategoryBase.MASTER,ls(ItemWithRecipe.of(SCItems.DIMENSION_CORE.get().getDefaultInstance(),"infusing_new_dimension_core")), SCRecipeTypes.INFUSING.get(),9).addReferences("dimension_structure");




    public static final AncientFragment SOLAR_GOD_PICKAXE_UPGRADE = new AncientFragment("solar_god_pickaxe_upgrade",ProgressionStage.SOLAR_ENERGY.ALL_PROGRESSIONS,SubCategoryBase.UPGRADES,CategoryBase.UPGRADES,ls(ItemWithRecipe.of(SCItems.SOLAR_GOD_PICKAXE.get().getDefaultInstance(),"infusing_new_solar_god_pickaxe_upgrade")), SCRecipeTypes.INFUSING.get(),8).addReferences("solar_god_pickaxe");
    public static final AncientFragment SOLAR_GOD_SWORD_UPGRADE = new AncientFragment("solar_god_sword_upgrade",ProgressionStage.SOLAR_ENERGY.ALL_PROGRESSIONS,SubCategoryBase.UPGRADES,CategoryBase.UPGRADES,ls(ItemWithRecipe.of(SCItems.SOLAR_GOD_SWORD.get().getDefaultInstance(),"infusing_new_solar_god_sword_upgrade")), SCRecipeTypes.INFUSING.get(),8).addReferences("solar_god_sword");

    public static final AncientFragment SOLAR_GOD_BOW = new AncientFragment("solar_god_bow",ProgressionStage.SOLAR_ENERGY.ALL_PROGRESSIONS,SubCategoryBase.MASTER_ITEMS,CategoryBase.MASTER,ls(ItemWithRecipe.of(SCItems.SOLAR_GOD_BOW.get().getDefaultInstance(),"infusing_new_solar_god_bow")), SCRecipeTypes.INFUSING.get(),8).addReferences("solar_god_bow_upgrade");
    public static final AncientFragment SOLAR_GOD_BOW_UPGRADE = new AncientFragment("solar_god_bow_upgrade",ProgressionStage.SOLAR_ENERGY.ALL_PROGRESSIONS,SubCategoryBase.UPGRADES,CategoryBase.UPGRADES,ls(ItemWithRecipe.of(SCItems.SOLAR_GOD_BOW.get().getDefaultInstance(),"infusing_new_solar_god_bow_upgrade")), SCRecipeTypes.INFUSING.get(),8).addReferences("solar_god_bow");
    public static final AncientFragment AMULETS = new AncientFragment("amulets",ProgressionStage.AFTER_LENS.ALL_PROGRESSIONS,SubCategoryBase.SKILLED_ITEMS,CategoryBase.SKILLED,List.of(
            ItemWithRecipe.of(REGEN_AMULET.get().getDefaultInstance(),"infusing_new_regen_amulet"),ItemWithRecipe.of(JUMP_AMULET.get().getDefaultInstance(),"infusing_new_jump_amulet"),
            ItemWithRecipe.of(SPEED_AMULET.get().getDefaultInstance(),"infusing_new_speed_amulet"),ItemWithRecipe.of(HASTE_AMULET.get().getDefaultInstance(),"infusing_new_haste_amulet"),
            ItemWithRecipe.of(NIGHT_VISION_AMULET.get().getDefaultInstance(),"infusing_new_night_vision_amulet"),ItemWithRecipe.of(STRENGTH_AMULET.get().getDefaultInstance(),"infusing_new_strength_amulet")
    ), SCRecipeTypes.INFUSING.get(),5);

    public static final AncientFragment SHADOW_ZOMBIES = new AncientFragment("shadow_zombie",ProgressionStage.DIMENSION.ALL_PROGRESSIONS,SubCategoryBase.RADIANT_LAND,CategoryBase.ENDGAME,"shadow_zombie_lore", Items.PAPER.getDefaultInstance(),9);
    public static final AncientFragment RADIANT_LAND_LORE = new AncientFragment("radiant_land",ProgressionStage.DIMENSION.ALL_PROGRESSIONS,SubCategoryBase.RADIANT_LAND,CategoryBase.ENDGAME,"radiant_land_lore", SCItems.DIMENSION_CORE.get().getDefaultInstance(),8);
    public static final AncientFragment DEFENCE_CRYSTAL = new AncientFragment("defence_crystal",ProgressionStage.DIMENSION.ALL_PROGRESSIONS,SubCategoryBase.RADIANT_LAND,CategoryBase.ENDGAME,"defence_crystal_lore", SCItems.CRYSTALLITE_CORE.get().getDefaultInstance(),9);
    public static final AncientFragment RUNIC_ELEMENTAL = new AncientFragment("runic_elemental",ProgressionStage.DIMENSION.ALL_PROGRESSIONS,SubCategoryBase.RADIANT_LAND,CategoryBase.ENDGAME,"runic_elemental_lore", CRYSTAL_HEART.get().getDefaultInstance(),11);


    public static final AncientFragment DIVINE_ARMOR = new AncientFragment("divine_armor",ProgressionStage.RUNIC_ELEMENTAL.ALL_PROGRESSIONS,SubCategoryBase.ARMOR,CategoryBase.ARMOR,List.of(
            ItemWithRecipe.of(DIVINE_BOOTS.get().getDefaultInstance(),"infusing_divine_boots"),ItemWithRecipe.of(DIVINE_LEGGINGS.get().getDefaultInstance(),"infusing_divine_leggings"),
            ItemWithRecipe.of(DIVINE_CHESTPLATE.get().getDefaultInstance(),"infusing_divine_chestplate"),ItemWithRecipe.of(DIVINE_HELMET.get().getDefaultInstance(),"infusing_divine_helmet")
    ), SCRecipeTypes.INFUSING.get(),9);

    public static final AncientFragment ORBITAL_MISSILE_LAUNCHER = new AncientFragment(
            "orbital_missile_launcher",ProgressionStage.RUNIC_ELEMENTAL.ALL_PROGRESSIONS,SubCategoryBase.MASTER_ITEMS,CategoryBase.MASTER,
            ls(ItemWithRecipe.of(SCItems.ORBITAL_MISSILE_LAUNCHER.get().getDefaultInstance(),"infusing_orbital_missile_launcher")),
             SCRecipeTypes.INFUSING.get(),9)
            .addReferences("runic_energy_repeater","crystal_cores");


    public static final AncientFragment AURA_HEALER_STRUCTURE = new AncientFragment("aura_healer_structure",AURA_HEALER.neededProgression,SubCategoryBase.STRUCTURES,CategoryBase.STRUCTURES, Multiblocks.AURA_HEALER, AURA_HEALER.priority).addReferences("aura_healer");
    public static final AncientFragment SOLAR_MORTAR_STRUCTURE = new AncientFragment("solar_mortar_structure",SOLAR_MORTAR.neededProgression,SubCategoryBase.STRUCTURES,CategoryBase.STRUCTURES, Multiblocks.SOLAR_MORTAR, SOLAR_MORTAR.priority).addReferences("solar_mortar");
    public static final AncientFragment INFUSER_TIER_BEGINNER = new AncientFragment("solar_infuser_structure",SOLAR_INFUSER.neededProgression,SubCategoryBase.STRUCTURES,CategoryBase.STRUCTURES, Multiblocks.INFUSER_TIER_ONE, SOLAR_INFUSER.priority).addReferences("solar_infuser");
    public static final AncientFragment INFUSER_TIER_RUNIC_ENERGY = new AncientFragment("solar_infuser_structure_2",ProgressionStage.AFTER_LENS.ALL_PROGRESSIONS, SubCategoryBase.STRUCTURES,CategoryBase.STRUCTURES, Multiblocks.INFUSER_TIER_TWO, SOLAR_INFUSER.priority).addReferences("solar_infuser");
    public static final AncientFragment INFUSER_TIER_SOLAR_ENERGY = new AncientFragment("solar_infuser_structure_3",ProgressionStage.SOLAR_ENERGY.ALL_PROGRESSIONS,SubCategoryBase.STRUCTURES,CategoryBase.STRUCTURES, Multiblocks.INFUSER_TIER_THREE, SOLAR_INFUSER.priority).addReferences("solar_infuser");

    public static final AncientFragment SOLAR_CORE_STRUCTURE = new AncientFragment("solar_core_structure",SOLAR_CORE.neededProgression,SubCategoryBase.STRUCTURES,CategoryBase.STRUCTURES, Multiblocks.SOLAR_CORE, SOLAR_CORE.priority).addReferences("solar_core");
    public static final AncientFragment SOLAR_ENERGY_GENERATOR_STRUCTURE = new AncientFragment("solar_energy_generator_structure",SOLAR_ENERGY_GENERATOR.neededProgression,SubCategoryBase.STRUCTURES,CategoryBase.STRUCTURES, Multiblocks.ENERGY_GENERATOR, SOLAR_ENERGY_GENERATOR.priority).addReferences("solar_energy_generator");
    public static final AncientFragment DIMENSION_STRUCTURE = new AncientFragment("dimension_structure",ProgressionStage.SOLAR_ENERGY.ALL_PROGRESSIONS,SubCategoryBase.STRUCTURES,CategoryBase.STRUCTURES, Multiblocks.RADIANT_LAND_PORTAL, DIMENSION_CORE.priority).addReferences("dimension_core");
    public static final AncientFragment ZAP_TURRET_STRUCTURE = new AncientFragment("zap_turret_structure",ProgressionStage.SOLAR_ENERGY.ALL_PROGRESSIONS,SubCategoryBase.STRUCTURES,CategoryBase.STRUCTURES, Multiblocks.ZAP_TURRET, ZAP_TURRET.priority).addReferences("zap_turret");
    public static final AncientFragment EXPLOSION_BLOCKER_STRUCTURE = new AncientFragment("explosion_blocker_structure",EXPLOSION_BLOCKER.neededProgression,SubCategoryBase.STRUCTURES,CategoryBase.STRUCTURES, Multiblocks.EXPLOSION_BLOCKER, EXPLOSION_BLOCKER.priority).addReferences("explosion_blocker");
    public static final AncientFragment RUNIC_ENERGY_CORE_STRUCTURE = new AncientFragment("runic_energy_core_structure",RUNIC_ENERGY_CORE.neededProgression,SubCategoryBase.STRUCTURES,CategoryBase.STRUCTURES, Multiblocks.RUNIC_ENERGY_CORE, RUNIC_ENERGY_CORE.priority).addReferences("runic_energy_core");
    public static final AncientFragment ORBITAL_MISSILE_LAUNCHER_STRUCTURE = new AncientFragment("orbital_missile_launcher_structure",ORBITAL_MISSILE_LAUNCHER.neededProgression,SubCategoryBase.STRUCTURES,CategoryBase.STRUCTURES, Multiblocks.ORBITAL_MISSILE_LAUNCHER, ORBITAL_MISSILE_LAUNCHER.priority).addReferences("orbital_missile_launcher");
    public static final AncientFragment PYLON_STRUCTURE = new AncientFragment("runic_energy_pylon_structure",RUNIC_ENERGY_PYLON.neededProgression,SubCategoryBase.STRUCTURES,CategoryBase.STRUCTURES, Multiblocks.PYLON_BASE, RUNIC_ENERGY_PYLON.priority + 1).addReferences("runic_energy_pylon");

    public static Map<String,AncientFragment> FRAGMENTS_ID_MAP = new HashMap<>();



    public static void initFragmentsMap(){

        for (AncientFragment frag : getAllFragments()){
            if (!FRAGMENTS_ID_MAP.containsKey(frag.getId())  ){
                FRAGMENTS_ID_MAP.put(frag.getId(),frag);
            }
        }
    }

    private final Component translation;
    private final String id;
    private final Progression[] neededProgression;
    private final Type type;
    private final Item icon;



    private final SubCategoryBase subCategory;
    private final CategoryBase category;

    private final int priority;
    private List<ItemWithRecipe> stacks;
    private MultiblockStructure structure;
    private ItemWithRecipe item;
    private Component lore;
    private Component itemLore;
    private RecipeType<?> recipeType;
    private String screenID;
    private String[] sReferences;
    private List<AncientFragment> references;
    private Supplier<List<Object>> translatableComponentArguments = ()->{
        return List.of();
    };



    AncientFragment(String id, Progression[] neededProgression, SubCategoryBase subBase, CategoryBase catBase, Type type, Item Icon, int priority,boolean jsonInit){
        this.translation = tx("ancient_fragment."+id);
        this.id = id;

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


    AncientFragment( String id, Progression[] neededProgression, SubCategoryBase subBase, CategoryBase catBase, MultiblockStructure structure, int priority){
        this(id,neededProgression,subBase,catBase,Type.STRUCTURE,structure.mainBlock.getBlock().asItem(),priority,false);
        this.structure = structure;
        STRUCTURE_FRAGMENTS.putIfAbsent(structure,this);
    }

    AncientFragment( String id, Progression[] neededProgression, SubCategoryBase subBase, CategoryBase catBase, Item Icon, int priority){
        this(id,neededProgression,subBase,catBase,Type.INFORMATION,Icon,priority,false);
        this.lore = Component.translatable(id+".lore");

    }


    AncientFragment( String id, Progression[] neededProgression, SubCategoryBase subBase, CategoryBase catBase, List<ItemWithRecipe> item,RecipeType<?> type, int priority){
        this(id,neededProgression,subBase,catBase,Type.ITEMS,item.get(0).getItem().getItem(),priority,false);
        this.stacks = item;
        this.itemLore = Component.translatable(id+".lore");
        this.recipeType = type;

        if (type == RecipeType.CRAFTING){
            for (ItemWithRecipe i : item){
                CRAFTING_TYPE_ITEMS.put(i.getItem().getItem(),this);
            }
        }
    }
    AncientFragment( String id, Progression[] neededProgression, SubCategoryBase subBase, CategoryBase catBase, String screenid, ItemStack logo, int priority){
        this(id,neededProgression,subBase,catBase,Type.CUSTOM,logo.getItem(),priority,false);
        this.screenID = screenid;

    }


    //json constructors start
    AncientFragment( String id, Progression[] neededProgression, SubCategoryBase subBase, CategoryBase catBase, Item Icon, Component lore, int priority,boolean jsonInit){
        this(id,neededProgression,subBase,catBase,Type.INFORMATION,Icon,priority,jsonInit);
        this.lore = lore;

    }

    AncientFragment( String id, Progression[] neededProgression, SubCategoryBase subBase, CategoryBase catBase, List<ItemWithRecipe> item,RecipeType<?> type, Component upgradeLore, int priority,boolean jsonInit){
        this(id,neededProgression,subBase,catBase,Type.ITEMS,item.get(0).getItem().getItem(),priority,jsonInit);
        this.stacks = item;
        this.itemLore = upgradeLore;
        this.recipeType = type;
    }
    //json constructors end

    private AncientFragment addReferences(String... refs){
        this.sReferences = refs;
        return this;
    }

    private AncientFragment setTranslatableComponentArguments(Supplier<List<Object>> s){
        this.translatableComponentArguments = s;
        return this;
    }

    /**
     * DO NOT USE BEFORE MAP INITIALIZATION!
     */
    public List<AncientFragment> getReferences() {
        if (references == null){
            references = new ArrayList<>();
            ArrayList<String> invalid = new ArrayList<>();
            if (sReferences == null){
                references = new ArrayList<>();
                return references;
            }
            for (String ref : sReferences){
                AncientFragment frag = FRAGMENTS_ID_MAP.get(ref);
                if (frag != null){
                    references.add(frag);
                }else{
                    invalid.add(ref);
                }
            }
            if (!invalid.isEmpty()){
                throw new IllegalStateException("Invalid references: " + invalid.toString() + " in fragment " + this.id);
            }
        }
        return references;
    }

    private void addFragToList(){

        ALL_FRAGMENTS.add(this);
    }

    public static List<AncientFragment> getAllFragments(){
//        AncientFragment[] f = new AncientFragment[ALL_FRAGMENTS.size()];
        return ALL_FRAGMENTS;
    }

    public CategoryBase getCategory() {
        return category;
    }

    public SubCategoryBase getSubCategory() {
        return subCategory;
    }

    public List<ItemWithRecipe> getStacks() {
        return stacks;
    }

    public RecipeType<?> getRecipeType() {
        return recipeType;
    }

    public Component getItemDescription(){
        TranslatableContents contents = (TranslatableContents) itemLore.getContents();
        Object[] args = this.translatableComponentArguments.get().toArray(new Object[0]);
        return (Component) Component.translatable(contents.getKey(),args);
    }

    public Component getLore() {
        TranslatableContents contents = (TranslatableContents) lore.getContents();
        Object[] args = this.translatableComponentArguments.get().toArray(new Object[0]);
        return (Component) Component.translatable(contents.getKey(),args);
    }


    public MultiblockStructure getStructure() {
        return structure;
    }

    public ItemWithRecipe getItem() {
        return item;
    }

    public Item getIcon() {
        return icon;
    }

    public MutableComponent getTranslation() {
        return translation.copy();
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
//        ITEM,
        INFORMATION,
//        UPGRADE,
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

    public static Component tx(String a){
        return Component.translatable(a);
    }

    public static List<ItemWithRecipe> ls(ItemWithRecipe... list){
        return List.of(list);
    }



    private static Progression[] r(Progression... a){
        return a;
    }



    public static List<AncientFragment> deserializeFragments(List<JsonObject> serializedFragments){
        if (serializedFragments == null) return null;

        List<AncientFragment> fragments = new ArrayList<>();
        for (JsonObject jFragment : serializedFragments){
            Type type = Type.valueOf(GsonHelper.getAsString(jFragment,"type").toUpperCase(Locale.ROOT));


//            Component translation = Component.translatable(GsonHelper.getAsString(jFragment,"translation_id"));
            Component lore = Component.translatable(GsonHelper.getAsString(jFragment,"translation_id_lore"));
            String id = GsonHelper.getAsString(jFragment,"unique_id");
            CategoryBase catBase = CategoryBase.valueOf(GsonHelper.getAsString(jFragment,"category_base").toUpperCase(Locale.ROOT));
            SubCategoryBase subBase = SubCategoryBase.valueOf(GsonHelper.getAsString(jFragment,"sub_category_base").toUpperCase(Locale.ROOT));
            ProgressionStage stage = ProgressionStage.valueOf(GsonHelper.getAsString(jFragment,"progression_stage").toUpperCase(Locale.ROOT));
            int priority = GsonHelper.getAsInt(jFragment,"priority");

            if (type == Type.ITEMS){
                RecipeType<?> recipeType = typeById(GsonHelper.getAsString(jFragment,"recipe_type"));
                if (recipeType == null || recipeType == SCRecipeTypes.SMELTING.get()) continue;
                List<ItemWithRecipe> items = getItemsFromJSON(jFragment.getAsJsonArray("items"));
                if (items.isEmpty()) continue;
                AncientFragment fragment = new AncientFragment(id,stage.ALL_PROGRESSIONS,subBase,catBase,items,recipeType,lore,priority,true);
                fragments.add(fragment);
            }else if (type == Type.INFORMATION){
                ItemStack item = GsonHelper.getAsItem(jFragment.getAsJsonObject("icon"),"item").value().getDefaultInstance();
                AncientFragment fragment = new AncientFragment(id,stage.ALL_PROGRESSIONS,subBase,catBase,item.getItem(),lore,priority,true);
                fragments.add(fragment);
            }
//            else if (type == Type.ITEM){
//                RecipeType<?> recipeType = typeById(GsonHelper.getAsString(jFragment,"recipe_type"));
//                ItemStack item = GsonHelper.getAsItem(jFragment.getAsJsonObject("recipe_item"),"item").getDefaultInstance();
//                ResourceLocation location = new ResourceLocation(GsonHelper.getAsString(jFragment.getAsJsonObject("recipe_item"),"recipe_id"));
//                AncientFragment fragment = new AncientFragment(translation,id,stage.ALL_PROGRESSIONS,subBase,catBase,new ItemWithRecipe(item,location),lore,recipeType,priority,true);
//                fragments.add(fragment);
//            }
        }
        return fragments;
    }

    private static RecipeType<?> typeById(String id){
        if (id.equals("infusing")) return SCRecipeTypes.INFUSING.get();
        if (id.equals("infusing_crafting")) return SCRecipeTypes.INFUSING_CRAFTING.get();
        if (id.equals("smelting")) return SCRecipeTypes.SMELTING.get();
        if (id.equals("crafting_table")) return RecipeType.CRAFTING;
        return null;
    }

    private static List<ItemWithRecipe> getItemsFromJSON(JsonArray array){
        List<ItemWithRecipe> items = new ArrayList<>();
        for (JsonElement f : array){
            ItemStack item = GsonHelper.getAsItem(f.getAsJsonObject(),"item").value().getDefaultInstance();
            ResourceLocation location = new ResourceLocation(GsonHelper.getAsString(f.getAsJsonObject(),"recipe_id"));
            items.add(new ItemWithRecipe(item,location));
        }
        return items;
    }


    public static class ItemWithRecipe{

        private final ResourceLocation RECIPE_LOCATION;
        private final ItemStack ITEM;

        private ItemWithRecipe(ItemStack stack,ResourceLocation resourceLocation){
            this.RECIPE_LOCATION = resourceLocation;
            this.ITEM = stack;
        }

        private static ItemWithRecipe of(ItemStack stack,String recipeID){
            return new ItemWithRecipe(stack,new ResourceLocation(SolarCraft.MOD_ID,recipeID));
        }

        private static ItemWithRecipe of(ItemStack stack,ResourceLocation location){
            return new ItemWithRecipe(stack,location);
        }

        public ItemStack getItem() {
            return ITEM;
        }

        public ResourceLocation getRecipeLocation() {
            return RECIPE_LOCATION;
        }
    }
}
