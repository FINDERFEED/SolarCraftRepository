package com.finderfeed.solarforge.magic_items.items.solar_lexicon.unlockables;

import com.finderfeed.solarforge.SolarForge;
import com.finderfeed.solarforge.magic_items.items.solar_lexicon.structure.category.CategoryBase;
import com.finderfeed.solarforge.magic_items.items.solar_lexicon.structure.subcategory.SubCategoryBase;
import com.finderfeed.solarforge.multiblocks.Multiblocks;
import com.finderfeed.solarforge.registries.items.ItemsRegister;
import com.finderfeed.solarforge.magic_items.items.solar_lexicon.achievements.Achievement;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.network.chat.TranslatableComponent;

import javax.annotation.Nullable;
import java.util.*;

public enum AncientFragment {

    RUNIC_TABLE(tx("solar_fragment.runic_table"),"runic_table",null,SubCategoryBase.BEGGINING,CategoryBase.BEGGINING_INFO, ItemsRegister.RUNIC_TABLE.get(),tx("runic_table.lore"),true),
    FRAGMENT(tx("solar_fragment.fragment"),"fragment",null,SubCategoryBase.BEGGINING,CategoryBase.BEGGINING_INFO, ItemsRegister.INFO_FRAGMENT.get(),tx("fragment.lore"),false),
    LEXICON(tx("solar_fragment.lexicon"),"lexicon",null,SubCategoryBase.BEGGINING,CategoryBase.BEGGINING_INFO, ItemsRegister.SOLAR_LEXICON.get(),tx("lexicon.lore"),false),
    WAND(tx("solar_fragment.wand"),"wand",Achievement.RUNE_ENERGY_DEPOSIT,SubCategoryBase.BEGGINING,CategoryBase.BEGGINING_INFO, ItemsRegister.SOLAR_WAND.get(),tx("wand.lore"),false),
    SOLAR_DUST(tx("solar_fragment.solar_dust"),"solar_dust",Achievement.CRAFT_SOLAR_FORGE,SubCategoryBase.FORGE,CategoryBase.SOLAR_FORGE_BASICS, ItemsRegister.SOLAR_DUST.get(),tx("solar_dust.lore"),true),
    ENERGY_DUST(tx("solar_fragment.energy_dust"),"energy_dust",Achievement.CRAFT_SOLAR_FORGE,SubCategoryBase.FORGE,CategoryBase.SOLAR_FORGE_BASICS, ItemsRegister.ENERGY_DUST.get(),tx("energy_dust.lore"),true),
    SOLAR_INFUSER(tx("solar_fragment.solar_infuser"),"solar_infuser",Achievement.FIND_SOLAR_STONE,SubCategoryBase.FORGE,CategoryBase.SOLAR_FORGE_BASICS, SolarForge.INFUSING_STAND_ITEM.get(),tx("solar_infuser.lore"),true),
    SOLAR_FORGE(tx("solar_fragment.solar_forge"),"solar_forge",Achievement.ENTER_NETHER,SubCategoryBase.FORGE,CategoryBase.SOLAR_FORGE_BASICS, SolarForge.SOLAR_FORGE_ITEM.get(),tx("solar_forge.lore"),true),
    SOLAR_HELMET(tx("solar_fragment.solar_helmet"),"solar_helmet",Achievement.CRAFT_SOLAR_INFUSER,SubCategoryBase.ARMOR,CategoryBase.ARMOR,ItemsRegister.SOLAR_HELMET.get().getDefaultInstance(),tx("solar_helmet.lore"), SolarForge.INFUSING_RECIPE_TYPE,true),
    SOLAR_CHESTPLATE(tx("solar_fragment.solar_chestplate"),"solar_chestplate",Achievement.CRAFT_SOLAR_INFUSER,SubCategoryBase.ARMOR,CategoryBase.ARMOR,ItemsRegister.SOLAR_CHESTPLATE.get().getDefaultInstance(),tx("solar_chestplate.lore"), SolarForge.INFUSING_RECIPE_TYPE,true),
    SOLAR_LEGGINS(tx("solar_fragment.solar_leggings"),"solar_leggings",Achievement.CRAFT_SOLAR_INFUSER,SubCategoryBase.ARMOR,CategoryBase.ARMOR,ItemsRegister.SOLAR_LEGGINS.get().getDefaultInstance(),tx("solar_leggings.lore"), SolarForge.INFUSING_RECIPE_TYPE,true),
    SOLAR_BOOTS(tx("solar_fragment.solar_boots"),"solar_boots",Achievement.CRAFT_SOLAR_INFUSER,SubCategoryBase.ARMOR,CategoryBase.ARMOR,ItemsRegister.SOLAR_BOOTS.get().getDefaultInstance(),tx("solar_boots.lore"), SolarForge.INFUSING_RECIPE_TYPE,true),
    ITEM_MAGNET(tx("solar_fragment.item_magnet"),"item_magnet",Achievement.CRAFT_SOLAR_INFUSER,SubCategoryBase.BEGINNER_ITEMS,CategoryBase.BEGINNER,ItemsRegister.MAGNET_BLOCK.get().getDefaultInstance(),tx("item_magnet.lore"), SolarForge.INFUSING_RECIPE_TYPE,true),
    AURA_HEALER(tx("solar_fragment.aura_healer"),"aura_healer",Achievement.USE_SOLAR_INFUSER,SubCategoryBase.BEGINNER_ITEMS,CategoryBase.BEGINNER,ItemsRegister.AURA_HEALER.get().getDefaultInstance(),tx("aura_healer.lore"), SolarForge.INFUSING_RECIPE_TYPE,true),
    DISC_LAUNCHER(tx("solar_fragment.disc_launcher"),"disc_launcher",Achievement.USE_SOLAR_INFUSER,SubCategoryBase.BEGINNER_ITEMS,CategoryBase.BEGINNER,ItemsRegister.SOLAR_DISC_LAUNCHER.get().getDefaultInstance(),tx("disc_launcher.lore"), SolarForge.INFUSING_RECIPE_TYPE,true),
    TURRET_RADAR(tx("solar_fragment.turret_radar"),"turret_radar",Achievement.USE_SOLAR_INFUSER,SubCategoryBase.BEGINNER_ITEMS,CategoryBase.BEGINNER,ItemsRegister.TURRET_RADAR.get().getDefaultInstance(),tx("turret_radar.lore"), SolarForge.INFUSING_RECIPE_TYPE,true),
    SOLAR_TURRET(tx("solar_fragment.solar_turret"),"solar_turret",Achievement.USE_SOLAR_INFUSER,SubCategoryBase.BEGINNER_ITEMS,CategoryBase.BEGINNER,ItemsRegister.TURRET_BLOCK.get().getDefaultInstance(),tx("solar_turret.lore"), SolarForge.INFUSING_RECIPE_TYPE,true),
    GLOVES_OF_REACH(tx("solar_fragment.gloves_of_reach"),"gloves_of_reach",Achievement.USE_SOLAR_INFUSER,SubCategoryBase.BEGINNER_ITEMS,CategoryBase.BEGINNER,ItemsRegister.REACH_GLOVES.get().getDefaultInstance(),tx("gloves_of_reach.lore"), SolarForge.INFUSING_RECIPE_TYPE,true),
    MANA_REGEN_AMULET(tx("solar_fragment.mana_regen_amulet"),"mana_regen_amulet",Achievement.USE_SOLAR_INFUSER,SubCategoryBase.BEGINNER_ITEMS,CategoryBase.BEGINNER,ItemsRegister.SOLAR_MANA_AMULET.get().getDefaultInstance(),tx("mana_regen_amulet.lore"), SolarForge.INFUSING_RECIPE_TYPE,true),

    SMALL_SOLAR_REACTOR(tx("solar_fragment.small_solar_reactor"),"small_solar_reactor",Achievement.CRAFT_SOLAR_INFUSER,SubCategoryBase.BEGINNER_ITEMS,CategoryBase.BEGINNER,ItemsRegister.SMALL_SOLAR_REACTOR.get().getDefaultInstance(),tx("small_solar_reactor.lore"), SolarForge.INFUSING_RECIPE_TYPE,true),
    ILLIDIUM_INGOT(tx("solar_fragment.illidium_ingot"),"illidium_ingot",Achievement.CRAFT_SOLAR_INFUSER,SubCategoryBase.BEGINNER_ITEMS,CategoryBase.BEGINNER,ItemsRegister.ILLIDIUM_INGOT.get().getDefaultInstance(),tx("illidium_ingot.lore"), SolarForge.INFUSING_RECIPE_TYPE,true),
    ALGADIUM_INGOT(tx("solar_fragment.algadium_ingot"),"algadium_ingot",Achievement.CRAFT_SOLAR_INFUSER,SubCategoryBase.BEGINNER_ITEMS,CategoryBase.BEGINNER,ItemsRegister.ALGADIUM_INGOT.get().getDefaultInstance(),tx("algadium_ingot.lore"), SolarForge.INFUSING_RECIPE_TYPE,true),
    BLOCK_BOOMERANG(tx("solar_fragment.block_boomerang"),"block_boomerang",Achievement.CRAFT_SOLAR_INFUSER,SubCategoryBase.BEGINNER_ITEMS,CategoryBase.BEGINNER,ItemsRegister.BLOCK_BOOMERANG.get().getDefaultInstance(),tx("block_boomerang.lore"), SolarForge.INFUSING_RECIPE_TYPE,true),
    ILLIDIUM_SWORD(tx("solar_fragment.illidium_sword"),"illidium_sword",Achievement.CRAFT_SOLAR_INFUSER,SubCategoryBase.BEGINNER_ITEMS,CategoryBase.BEGINNER,ItemsRegister.ILLIDIUM_SWORD.get().getDefaultInstance(),tx("illidium_sword.lore"), SolarForge.INFUSING_RECIPE_TYPE,true),
    ILLIDIUM_AXE(tx("solar_fragment.illidium_axe"),"illidium_axe",Achievement.CRAFT_SOLAR_INFUSER,SubCategoryBase.BEGINNER_ITEMS,CategoryBase.BEGINNER,ItemsRegister.ILLIDIUM_AXE.get().getDefaultInstance(),tx("illidium_axe.lore"), SolarForge.INFUSING_RECIPE_TYPE,true),
    ILLIDIUM_SHOVEL(tx("solar_fragment.illidium_shovel"),"illidium_shovel",Achievement.CRAFT_SOLAR_INFUSER,SubCategoryBase.BEGINNER_ITEMS,CategoryBase.BEGINNER,ItemsRegister.ILLIDIUM_SHOVEL.get().getDefaultInstance(),tx("illidium_shovel.lore"), SolarForge.INFUSING_RECIPE_TYPE,true),
    ILLIDIUM_HOE(tx("solar_fragment.illidium_hoe"),"illidium_hoe",Achievement.CRAFT_SOLAR_INFUSER,SubCategoryBase.BEGINNER_ITEMS,CategoryBase.BEGINNER,ItemsRegister.GROWTH_HOE.get().getDefaultInstance(),tx("illidium_hoe.lore"), SolarForge.INFUSING_RECIPE_TYPE,true),
    ILLIDIUM_PICKAXE(tx("solar_fragment.illidium_pickaxe"),"illidium_pickaxe",Achievement.CRAFT_SOLAR_INFUSER,SubCategoryBase.BEGINNER_ITEMS,CategoryBase.BEGINNER,ItemsRegister.VEIN_MINER.get().getDefaultInstance(),tx("illidium_pickaxe.lore"), SolarForge.INFUSING_RECIPE_TYPE,true),

    SOLAR_LENS(tx("solar_fragment.solar_lens"),"solar_lens",Achievement.ACQUIRE_COLD_STAR_ACTIVATED,SubCategoryBase.SKILLED_ITEMS,CategoryBase.SKILLED,ItemsRegister.SOLAR_LENS.get().getDefaultInstance(),tx("solar_lens.lore"), SolarForge.INFUSING_RECIPE_TYPE,true),
    RUNIC_ENERGY_REPEATER(tx("solar_fragment.runic_energy_repeater"),"runic_energy_repeater",Achievement.ACQUIRE_COLD_STAR_ACTIVATED,SubCategoryBase.SKILLED_ITEMS,CategoryBase.SKILLED,ItemsRegister.REPEATER.get().getDefaultInstance(),tx("runic_energy_repeater.lore"), SolarForge.INFUSING_RECIPE_TYPE,true),

    QUALADIUM_INGOT(tx("solar_fragment.qualadium_ingot"),"qualadium_ingot",Achievement.CRAFT_SOLAR_LENS,SubCategoryBase.SKILLED_MATERIALS,CategoryBase.SKILLED,ItemsRegister.QUALADIUM_INGOT.get().getDefaultInstance(),tx("qualadium_ingot.lore"), SolarForge.SOLAR_SMELTING,true),
    GEMINIUM_INGOT(tx("solar_fragment.geminium_ingot"),"geminium_ingot",Achievement.TRANSMUTE_GEM,SubCategoryBase.SKILLED_MATERIALS,CategoryBase.SKILLED,ItemsRegister.GEMINIUM_INGOT.get().getDefaultInstance(),tx("geminium_ingot.lore"), SolarForge.SOLAR_SMELTING,true),
    SOLAR_GOD_SWORD(tx("solar_fragment.solar_god_sword"),"solar_god_sword",Achievement.CRAFT_SOLAR_LENS,SubCategoryBase.SKILLED_ITEMS,CategoryBase.SKILLED,ItemsRegister.SOLAR_GOD_SWORD.get().getDefaultInstance(),tx("solar_god_sword.lore"), SolarForge.INFUSING_RECIPE_TYPE,true),
    SOLAR_GOD_PICKAXE(tx("solar_fragment.solar_god_pickaxe"),"solar_god_pickaxe",Achievement.CRAFT_SOLAR_LENS,SubCategoryBase.SKILLED_ITEMS,CategoryBase.SKILLED,ItemsRegister.SOLAR_GOD_PICKAXE.get().getDefaultInstance(),tx("solar_god_pickaxe.lore"), SolarForge.INFUSING_RECIPE_TYPE,true),
    SOLAR_GOD_SHIELD(tx("solar_fragment.solar_god_shield"),"solar_god_shield",Achievement.CRAFT_SOLAR_LENS,SubCategoryBase.SKILLED_ITEMS,CategoryBase.SKILLED,ItemsRegister.SOLAR_GOD_SHIELD.get().getDefaultInstance(),tx("solar_god_shield.lore"), SolarForge.INFUSING_RECIPE_TYPE,true),
    QUALADIUM_SWORD(tx("solar_fragment.qualadium_sword"),"qualadium_sword",Achievement.CRAFT_SOLAR_LENS,SubCategoryBase.SKILLED_ITEMS,CategoryBase.SKILLED,ItemsRegister.QUALADIUM_SWORD.get().getDefaultInstance(),tx("qualadium_sword.lore"), SolarForge.INFUSING_RECIPE_TYPE,true),
    QUALADIUM_AXE(tx("solar_fragment.qualadium_axe"),"qualadium_axe",Achievement.CRAFT_SOLAR_LENS,SubCategoryBase.SKILLED_ITEMS,CategoryBase.SKILLED,ItemsRegister.QUALADIUM_AXE.get().getDefaultInstance(),tx("qualadium_axe.lore"), SolarForge.INFUSING_RECIPE_TYPE,true),
    QUALADIUM_SHOVEL(tx("solar_fragment.qualadium_shovel"),"qualadium_shovel",Achievement.CRAFT_SOLAR_LENS,SubCategoryBase.SKILLED_ITEMS,CategoryBase.SKILLED,ItemsRegister.QUALADIUM_SHOVEL.get().getDefaultInstance(),tx("qualadium_shovel.lore"), SolarForge.INFUSING_RECIPE_TYPE,true),
    QUALADIUM_HOE(tx("solar_fragment.qualadium_hoe"),"qualadium_hoe",Achievement.CRAFT_SOLAR_LENS,SubCategoryBase.SKILLED_ITEMS,CategoryBase.SKILLED,ItemsRegister.QUALADIUM_HOE.get().getDefaultInstance(),tx("qualadium_hoe.lore"), SolarForge.INFUSING_RECIPE_TYPE,true),
    QUALADIUM_PICKAXE(tx("solar_fragment.qualadium_pickaxe"),"qualadium_pickaxe",Achievement.CRAFT_SOLAR_LENS,SubCategoryBase.SKILLED_ITEMS,CategoryBase.SKILLED,ItemsRegister.QUALADIUM_PICKAXE.get().getDefaultInstance(),tx("qualadium_pickaxe.lore"), SolarForge.INFUSING_RECIPE_TYPE,true),
    EXPERIENCE_CRYSTAL(tx("solar_fragment.experience_crystal"),"experience_crystal",Achievement.CRAFT_SOLAR_LENS,SubCategoryBase.SKILLED_ITEMS,CategoryBase.SKILLED,ItemsRegister.EXPERIENCE_CRYSTAL.get().getDefaultInstance(),tx("experience_crystal.lore"), SolarForge.INFUSING_RECIPE_TYPE,true),
    AMETHYST_CORE(tx("solar_fragment.amethyst_core"),"amethyst_core",Achievement.CRAFT_SOLAR_LENS,SubCategoryBase.SKILLED_MATERIALS,CategoryBase.SKILLED,ItemsRegister.AMETHYST_CORE.get().getDefaultInstance(),tx("amethyst_core.lore"), SolarForge.INFUSING_RECIPE_TYPE,true),
    RUNIC_CORE(tx("solar_fragment.runic_core"),"runic_core",Achievement.CRAFT_SOLAR_LENS,SubCategoryBase.SKILLED_MATERIALS,CategoryBase.SKILLED,ItemsRegister.RUNIC_CORE.get().getDefaultInstance(),tx("runic_core.lore"), SolarForge.INFUSING_RECIPE_TYPE,true),
    MODULES(tx("solar_fragment.modules"),"modules",Achievement.CRAFT_SOLAR_LENS,SubCategoryBase.SKILLED_ITEMS,CategoryBase.SKILLED,List.of(
            ItemsRegister.MODULE_APPLIER.get().getDefaultInstance(),ItemsRegister.PICKAXE_MINER_ABILITY_MODULE.get().getDefaultInstance(),
            ItemsRegister.MAGIC_DAMAGE_MODULE_5.get().getDefaultInstance(),ItemsRegister.SWORD_AUTOHEAL_MODULE.get().getDefaultInstance(),
            ItemsRegister.PHYSICAL_DEFENCE_MODULE_10.get().getDefaultInstance(),ItemsRegister.DISARMING_THORNS_MODULE.get().getDefaultInstance(),
            ItemsRegister.SWORD_AOE_ATTACK.get().getDefaultInstance(),ItemsRegister.PICKAXE_AUTO_SMELT.get().getDefaultInstance(),
            ItemsRegister.BLESSED_MODULE.get().getDefaultInstance(),ItemsRegister.POISONING_BLADE_MODULE.get().getDefaultInstance(),
            ItemsRegister.FURY_SWIPES_MODULE.get().getDefaultInstance()
    ),tx("modules.lore")),

    SOLAR_ENERGY_GENERATOR(tx("solar_fragment.solar_energy_generator"),"solar_energy_generator",Achievement.CRAFT_SOLAR_LENS,SubCategoryBase.MASTER_ENERGY,CategoryBase.MASTER,ItemsRegister.ENERGY_GENERATOR_BLOCK.get().getDefaultInstance(),tx("solar_energy_generator.lore"), SolarForge.INFUSING_RECIPE_TYPE,true),
    SOLAR_CORE(tx("solar_fragment.solar_core"),"solar_core",Achievement.CRAFT_SOLAR_ENERGY_GENERATOR,SubCategoryBase.MASTER_ENERGY,CategoryBase.MASTER,ItemsRegister.SOLAR_CORE.get().getDefaultInstance(),tx("solar_core.lore"), SolarForge.INFUSING_RECIPE_TYPE,true),
    SOLAR_ENERGY_REPEATER(tx("solar_fragment.solar_energy_repeater"),"solar_energy_repeater",Achievement.CRAFT_SOLAR_ENERGY_GENERATOR,SubCategoryBase.MASTER_ENERGY,CategoryBase.MASTER,ItemsRegister.SOLAR_ENERGY_REPEATER.get().getDefaultInstance(),tx("solar_energy_repeater.lore"), SolarForge.INFUSING_RECIPE_TYPE,true),

    RADIANT_CHESTPLATE(tx("solar_fragment.radiant_cuirass"),"radiant_cuirass",Achievement.CRAFT_SOLAR_ENERGY_GENERATOR,SubCategoryBase.ARMOR,CategoryBase.ARMOR,ItemsRegister.RADIANT_CHESTPLATE.get().getDefaultInstance(),tx("radiant_cuirass.lore"), SolarForge.INFUSING_RECIPE_TYPE,true),
    MEDIUM_SOLAR_REACTOR(tx("solar_fragment.medium_solar_reactor"),"medium_solar_reactor",Achievement.CRAFT_SOLAR_ENERGY_GENERATOR,SubCategoryBase.MASTER_MATERIALS,CategoryBase.MASTER,ItemsRegister.MEDIUM_SOLAR_REACTOR.get().getDefaultInstance(),tx("medium_solar_reactor.lore"), SolarForge.INFUSING_RECIPE_TYPE,true),
    ZAP_TURRET(tx("solar_fragment.zap_turret"),"zap_turret",Achievement.CRAFT_SOLAR_ENERGY_GENERATOR,SubCategoryBase.MASTER_ITEMS,CategoryBase.MASTER,ItemsRegister.ZAP_TURRET_BLOCK.get().getDefaultInstance(),tx("zap_turret.lore"), SolarForge.INFUSING_RECIPE_TYPE,true),
    CHARGED_QUALADIUM_INGOT(tx("solar_fragment.charged_qualadium_ingot"),"charged_qualadium_ingot",Achievement.CRAFT_SOLAR_ENERGY_GENERATOR,SubCategoryBase.MASTER_MATERIALS,CategoryBase.MASTER,ItemsRegister.CHARGED_QUALADIUM_INGOT.get().getDefaultInstance(),tx("charged_qualadium_ingot.lore"), SolarForge.INFUSING_RECIPE_TYPE,true),
    SOLAR_MORTAR(tx("solar_fragment.solar_mortar"),"solar_mortar",Achievement.CRAFT_SOLAR_ENERGY_GENERATOR,SubCategoryBase.MASTER_ITEMS,CategoryBase.MASTER,ItemsRegister.SOLAR_MORTAR.get().getDefaultInstance(),tx("solar_mortar.lore"), SolarForge.INFUSING_RECIPE_TYPE,true),
    SOLAR_FURNACE(tx("solar_fragment.solar_furnace"),"solar_furnace",Achievement.CRAFT_SOLAR_ENERGY_GENERATOR,SubCategoryBase.MASTER_ITEMS,CategoryBase.MASTER,ItemsRegister.SOLAR_FURNACE_BLOCK.get().getDefaultInstance(),tx("solar_furnace.lore"), SolarForge.INFUSING_RECIPE_TYPE,true),
    TOTEM_OF_IMMORTALITY(tx("solar_fragment.totem_of_immortality"),"totem_of_immortality",Achievement.CRAFT_SOLAR_ENERGY_GENERATOR,SubCategoryBase.MASTER_ITEMS,CategoryBase.MASTER,ItemsRegister.TOTEM_OF_IMMORTALITY.get().getDefaultInstance(),tx("totem_of_immortality.lore"), SolarForge.INFUSING_RECIPE_TYPE,true),
    SOLAR_CROSSBOW(tx("solar_fragment.solar_crossbow"),"solar_crossbow",Achievement.CRAFT_SOLAR_ENERGY_GENERATOR,SubCategoryBase.MASTER_ITEMS,CategoryBase.MASTER,ItemsRegister.ULTRA_CROSSBOW.get().getDefaultInstance(),tx("solar_crossbow.lore"), SolarForge.INFUSING_RECIPE_TYPE,true),
    DIMENSION_CORE(tx("solar_fragment.dimension_core"),"dimension_core",Achievement.CRAFT_SOLAR_ENERGY_GENERATOR,SubCategoryBase.MASTER_ITEMS,CategoryBase.MASTER,ItemsRegister.DIMENSION_CORE.get().getDefaultInstance(),tx("dimension_core.lore"), SolarForge.INFUSING_RECIPE_TYPE,true),


    AURA_HEALER_STRUCTURE(tx("solar_fragment.aura_healer_structure"),"aura_healer_structure",Achievement.USE_SOLAR_INFUSER,SubCategoryBase.STRUCTURES,CategoryBase.STRUCTURES,Multiblocks.AURA_HEALER,true),
    SOLAR_MORTAR_STRUCTURE(tx("solar_fragment.solar_mortar_structure"),"solar_mortar_structure",SOLAR_MORTAR.neededProgression,SubCategoryBase.STRUCTURES,CategoryBase.STRUCTURES,Multiblocks.SOLAR_MORTAR,true),
    SOLAR_INFUSER_STRUCTURE(tx("solar_fragment.solar_infuser_structure"),"solar_infuser_structure",Achievement.CRAFT_SOLAR_INFUSER,SubCategoryBase.STRUCTURES,CategoryBase.STRUCTURES,Multiblocks.INFUSER,true),
    SOLAR_CORE_STRUCTURE(tx("solar_fragment.solar_core_structure"),"solar_core_structure",SOLAR_CORE.neededProgression,SubCategoryBase.STRUCTURES,CategoryBase.STRUCTURES,Multiblocks.SOLAR_CORE,true),
    SOLAR_ENERGY_GENERATOR_STRUCTURE(tx("solar_fragment.solar_energy_generator_structure"),"solar_energy_generator_structure",SOLAR_ENERGY_GENERATOR.neededProgression,SubCategoryBase.STRUCTURES,CategoryBase.STRUCTURES,Multiblocks.SOLAR_ENERGY_GENERATOR,true),
    DIMENSION_STRUCTURE(tx("solar_fragment.dimension_structure"),"dimension_structure",Achievement.DIMENSION_CORE,SubCategoryBase.STRUCTURES,CategoryBase.STRUCTURES,Multiblocks.RADIANT_LAND_PORTAL,true),
    ZAP_TURRET_STRUCTURE(tx("solar_fragment.zap_turret_structure"),"zap_turret_structure",Achievement.CRAFT_SOLAR_ENERGY_GENERATOR,SubCategoryBase.STRUCTURES,CategoryBase.STRUCTURES,Multiblocks.ZAP_TURRET,true),

    SOLAR_GOD_PICKAXE_UPGRADE(tx("solar_fragment.solar_god_pickaxe_upgrade"),"solar_god_pickaxe_upgrade",Achievement.CRAFT_SOLAR_ENERGY_GENERATOR,SubCategoryBase.UPGRADES,CategoryBase.UPGRADES,ItemsRegister.SOLAR_GOD_PICKAXE.get().getDefaultInstance(),tx("solar_pickaxe_upgrade.lore")),
    SOLAR_GOD_SWORD_UPGRADE(tx("solar_fragment.solar_god_sword_upgrade"),"solar_god_sword_upgrade",Achievement.CRAFT_SOLAR_ENERGY_GENERATOR,SubCategoryBase.UPGRADES,CategoryBase.UPGRADES,ItemsRegister.SOLAR_GOD_SWORD.get().getDefaultInstance(),tx("solar_god_sword_upgrade.lore")),

    SOLAR_GOD_BOW(tx("solar_fragment.solar_god_bow"),"solar_god_bow",Achievement.CRAFT_SOLAR_ENERGY_GENERATOR,SubCategoryBase.MASTER_ITEMS,CategoryBase.MASTER,ItemsRegister.SOLAR_GOD_BOW.get().getDefaultInstance(),tx("solar_god_bow.lore"), SolarForge.INFUSING_RECIPE_TYPE,true),
    SOLAR_GOD_BOW_UPGRADE(tx("solar_fragment.solar_god_bow_upgrade"),"solar_god_bow_upgrade",Achievement.CRAFT_SOLAR_ENERGY_GENERATOR,SubCategoryBase.UPGRADES,CategoryBase.UPGRADES,ItemsRegister.SOLAR_GOD_BOW.get().getDefaultInstance(),tx("solar_god_bow_upgrade.lore")),
    AMULETS(tx("solar_fragment.amulets"),"amulets",Achievement.CRAFT_SOLAR_LENS,SubCategoryBase.SKILLED_ITEMS,CategoryBase.SKILLED,List.of(
            ItemsRegister.REGEN_AMULET.get().getDefaultInstance(),ItemsRegister.JUMP_AMULET.get().getDefaultInstance(),
            ItemsRegister.SPEED_AMULET.get().getDefaultInstance(),ItemsRegister.HASTE_AMULET.get().getDefaultInstance(),
            ItemsRegister.NIGHT_VISION_AMULET.get().getDefaultInstance(),ItemsRegister.STRENGTH_AMULET.get().getDefaultInstance()
    ),tx("amulets.lore"));

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
    private final Achievement neededProgression;
    private final Type type;
    private final Item icon;

    @Deprecated
    private final BookEntry entry;

    private final SubCategoryBase subCategory;
    private final CategoryBase category;

    private List<ItemStack> stacks;
    private Multiblocks structure;
    private ItemStack item;
    private TranslatableComponent lore;
    private TranslatableComponent itemLore;
    private RecipeType<?> recipeType;
    private String screenID;
    private final boolean requiresFragment;


    AncientFragment(TranslatableComponent translation, String id, Achievement neededProgression,SubCategoryBase subBase,CategoryBase catBase, Type type,Item Icon,boolean requiresFragment){
        this.translation = translation;
        this.id = id;
        this.entry = null;
        this.neededProgression = neededProgression;
        this.type = type;
        this.icon = Icon;
        this.category = catBase;
        this.subCategory = subBase;
        this.requiresFragment = requiresFragment;
    }

    AncientFragment(TranslatableComponent translation, String id, Achievement neededProgression,SubCategoryBase subBase,CategoryBase catBase, ItemStack item,TranslatableComponent itemLore,RecipeType<?> recipeType,boolean requiresFragment){
        this(translation,id,neededProgression,subBase,catBase,Type.ITEM,item.getItem(),requiresFragment);
        this.item = item;
        this.itemLore = itemLore;
        this.recipeType = recipeType;
    }

    AncientFragment(TranslatableComponent translation, String id, Achievement neededProgression,SubCategoryBase subBase,CategoryBase catBase,  Multiblocks structure,boolean requiresFragment){
        this(translation,id,neededProgression,subBase,catBase,Type.STRUCTURE,structure.getM().mainBlock.asItem(),requiresFragment);
        this.structure = structure;
    }

    AncientFragment(TranslatableComponent translation, String id, Achievement neededProgression,SubCategoryBase subBase,CategoryBase catBase, Item Icon, TranslatableComponent lore,boolean requiresFragment){
        this(translation,id,neededProgression,subBase,catBase,Type.INFORMATION,Icon,requiresFragment);
        this.lore = lore;
    }

    AncientFragment(TranslatableComponent translation, String id, Achievement neededProgression,SubCategoryBase subBase,CategoryBase catBase, ItemStack item,TranslatableComponent upgradeLore){
        this(translation,id,neededProgression,subBase,catBase,Type.UPGRADE,item.getItem(),true);
        this.item = item;
        this.itemLore = upgradeLore;
    }

    AncientFragment(TranslatableComponent translation, String id, Achievement neededProgression,SubCategoryBase subBase,CategoryBase catBase, List<ItemStack> item,TranslatableComponent upgradeLore){
        this(translation,id,neededProgression,subBase,catBase,Type.ITEMS,item.get(0).getItem(),true);
        this.stacks = item;
        this.itemLore = upgradeLore;
    }
    AncientFragment(TranslatableComponent translation, String id, Achievement neededProgression, SubCategoryBase subBase, CategoryBase catBase, String screenid,ItemStack logo){
        this(translation,id,neededProgression,subBase,catBase,Type.CUSTOM,logo.getItem(),true);
        this.screenID = screenid;
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
        return translation;
    }

    public Achievement getNeededProgression() {
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

    public static AncientFragment[] getAllFragments(){
        return AncientFragment.class.getEnumConstants();
    }
    public enum Type{
        STRUCTURE,
        ITEM,
        INFORMATION,
        UPGRADE,
        ITEMS,
        CUSTOM
    }

    public static AncientFragment getFragmentByID(String id){
        return FRAGMENTS_ID_MAP.containsKey(id) ? FRAGMENTS_ID_MAP.get(id) : null;
    }

    public static TranslatableComponent tx(String a){
        return new TranslatableComponent(a);
    }

    public boolean requiresFragment(){
        return requiresFragment;
    }
}
