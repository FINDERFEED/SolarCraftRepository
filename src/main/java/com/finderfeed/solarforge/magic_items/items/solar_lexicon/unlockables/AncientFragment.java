package com.finderfeed.solarforge.magic_items.items.solar_lexicon.unlockables;

import com.finderfeed.solarforge.SolarForge;
import com.finderfeed.solarforge.multiblocks.Multiblocks;
import com.finderfeed.solarforge.registries.items.ItemsRegister;
import com.finderfeed.solarforge.magic_items.items.solar_lexicon.achievements.Achievement;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.network.chat.TranslatableComponent;

import java.util.*;

public enum AncientFragment {

    RUNIC_TABLE(tx("solar_fragment.runic_table"),"runic_table",null,BookEntry.BEGINNING_INFO, ItemsRegister.RUNIC_TABLE.get(),tx("runic_table.lore"),true),
    FRAGMENT(tx("solar_fragment.fragment"),"fragment",null,BookEntry.BEGINNING_INFO, ItemsRegister.INFO_FRAGMENT.get(),tx("fragment.lore"),false),
    LEXICON(tx("solar_fragment.lexicon"),"lexicon",null,BookEntry.BEGINNING_INFO, ItemsRegister.SOLAR_LEXICON.get(),tx("lexicon.lore"),false),
    WAND(tx("solar_fragment.wand"),"wand",Achievement.RUNE_ENERGY_DEPOSIT,BookEntry.BEGINNING_INFO, ItemsRegister.SOLAR_WAND.get(),tx("wand.lore"),false),
    SOLAR_DUST(tx("solar_fragment.solar_dust"),"solar_dust",Achievement.CRAFT_SOLAR_FORGE,BookEntry.SOLAR_FORGE_BASICS, ItemsRegister.SOLAR_DUST.get(),tx("solar_dust.lore"),true),
    ENERGY_DUST(tx("solar_fragment.energy_dust"),"energy_dust",null,BookEntry.SOLAR_FORGE_BASICS, ItemsRegister.ENERGY_DUST.get(),tx("energy_dust.lore"),true),
    SOLAR_INFUSER(tx("solar_fragment.solar_infuser"),"solar_infuser",Achievement.FIND_SOLAR_STONE,BookEntry.SOLAR_FORGE_BASICS, SolarForge.INFUSING_STAND_ITEM.get(),tx("solar_infuser.lore"),true),
    SOLAR_FORGE(tx("solar_fragment.solar_forge"),"solar_forge",Achievement.ENTER_NETHER,BookEntry.SOLAR_FORGE_BASICS, SolarForge.SOLAR_FORGE_ITEM.get(),tx("solar_forge.lore"),true),
    SOLAR_HELMET(tx("solar_fragment.solar_helmet"),"solar_helmet",Achievement.CRAFT_SOLAR_INFUSER,BookEntry.ARMOR,ItemsRegister.SOLAR_HELMET.get().getDefaultInstance(),tx("solar_helmet.lore"), SolarForge.INFUSING_RECIPE_TYPE,true),
    SOLAR_CHESTPLATE(tx("solar_fragment.solar_chestplate"),"solar_chestplate",Achievement.CRAFT_SOLAR_INFUSER,BookEntry.ARMOR,ItemsRegister.SOLAR_CHESTPLATE.get().getDefaultInstance(),tx("solar_chestplate.lore"), SolarForge.INFUSING_RECIPE_TYPE,true),
    SOLAR_LEGGINS(tx("solar_fragment.solar_leggings"),"solar_leggings",Achievement.CRAFT_SOLAR_INFUSER,BookEntry.ARMOR,ItemsRegister.SOLAR_LEGGINS.get().getDefaultInstance(),tx("solar_leggings.lore"), SolarForge.INFUSING_RECIPE_TYPE,true),
    SOLAR_BOOTS(tx("solar_fragment.solar_boots"),"solar_boots",Achievement.CRAFT_SOLAR_INFUSER,BookEntry.ARMOR,ItemsRegister.SOLAR_BOOTS.get().getDefaultInstance(),tx("solar_boots.lore"), SolarForge.INFUSING_RECIPE_TYPE,true),
    ITEM_MAGNET(tx("solar_fragment.item_magnet"),"item_magnet",Achievement.CRAFT_SOLAR_INFUSER,BookEntry.BEGINNER_ITEMS,ItemsRegister.MAGNET_BLOCK.get().getDefaultInstance(),tx("item_magnet.lore"), SolarForge.INFUSING_RECIPE_TYPE,true),
    AURA_HEALER(tx("solar_fragment.aura_healer"),"aura_healer",Achievement.USE_SOLAR_INFUSER,BookEntry.BEGINNER_ITEMS,ItemsRegister.AURA_HEALER.get().getDefaultInstance(),tx("aura_healer.lore"), SolarForge.INFUSING_RECIPE_TYPE,true),
    DISC_LAUNCHER(tx("solar_fragment.disc_launcher"),"disc_launcher",Achievement.USE_SOLAR_INFUSER,BookEntry.BEGINNER_ITEMS,ItemsRegister.SOLAR_DISC_LAUNCHER.get().getDefaultInstance(),tx("disc_launcher.lore"), SolarForge.INFUSING_RECIPE_TYPE,true),
    SOLAR_TURRET(tx("solar_fragment.solar_turret"),"solar_turret",Achievement.USE_SOLAR_INFUSER,BookEntry.BEGINNER_ITEMS,ItemsRegister.TURRET_BLOCK.get().getDefaultInstance(),tx("solar_turret.lore"), SolarForge.INFUSING_RECIPE_TYPE,true),
    MANA_REGEN_AMULET(tx("solar_fragment.mana_regen_amulet"),"mana_regen_amulet",Achievement.USE_SOLAR_INFUSER,BookEntry.BEGINNER_ITEMS,ItemsRegister.SOLAR_MANA_AMULET.get().getDefaultInstance(),tx("mana_regen_amulet.lore"), SolarForge.INFUSING_RECIPE_TYPE,true),

    SMALL_SOLAR_REACTOR(tx("solar_fragment.small_solar_reactor"),"small_solar_reactor",Achievement.CRAFT_SOLAR_INFUSER,BookEntry.BEGINNER_ITEMS,ItemsRegister.SMALL_SOLAR_REACTOR.get().getDefaultInstance(),tx("small_solar_reactor.lore"), SolarForge.INFUSING_RECIPE_TYPE,true),
    ILLIDIUM_INGOT(tx("solar_fragment.illidium_ingot"),"illidium_ingot",Achievement.CRAFT_SOLAR_INFUSER,BookEntry.BEGINNER_ITEMS,ItemsRegister.ILLIDIUM_INGOT.get().getDefaultInstance(),tx("illidium_ingot.lore"), SolarForge.INFUSING_RECIPE_TYPE,true),
    ALGADIUM_INGOT(tx("solar_fragment.algadium_ingot"),"algadium_ingot",Achievement.CRAFT_SOLAR_INFUSER,BookEntry.BEGINNER_ITEMS,ItemsRegister.ALGADIUM_INGOT.get().getDefaultInstance(),tx("algadium_ingot.lore"), SolarForge.INFUSING_RECIPE_TYPE,true),
    BLOCK_BOOMERANG(tx("solar_fragment.block_boomerang"),"block_boomerang",Achievement.CRAFT_SOLAR_INFUSER,BookEntry.BEGINNER_ITEMS,ItemsRegister.BLOCK_BOOMERANG.get().getDefaultInstance(),tx("block_boomerang.lore"), SolarForge.INFUSING_RECIPE_TYPE,true),
    ILLIDIUM_SWORD(tx("solar_fragment.illidium_sword"),"illidium_sword",Achievement.CRAFT_SOLAR_INFUSER,BookEntry.BEGINNER_ITEMS,ItemsRegister.ILLIDIUM_SWORD.get().getDefaultInstance(),tx("illidium_sword.lore"), SolarForge.INFUSING_RECIPE_TYPE,true),
    ILLIDIUM_AXE(tx("solar_fragment.illidium_axe"),"illidium_axe",Achievement.CRAFT_SOLAR_INFUSER,BookEntry.BEGINNER_ITEMS,ItemsRegister.ILLIDIUM_AXE.get().getDefaultInstance(),tx("illidium_axe.lore"), SolarForge.INFUSING_RECIPE_TYPE,true),
    ILLIDIUM_SHOVEL(tx("solar_fragment.illidium_shovel"),"illidium_shovel",Achievement.CRAFT_SOLAR_INFUSER,BookEntry.BEGINNER_ITEMS,ItemsRegister.ILLIDIUM_SHOVEL.get().getDefaultInstance(),tx("illidium_shovel.lore"), SolarForge.INFUSING_RECIPE_TYPE,true),
    ILLIDIUM_HOE(tx("solar_fragment.illidium_hoe"),"illidium_hoe",Achievement.CRAFT_SOLAR_INFUSER,BookEntry.BEGINNER_ITEMS,ItemsRegister.GROWTH_HOE.get().getDefaultInstance(),tx("illidium_hoe.lore"), SolarForge.INFUSING_RECIPE_TYPE,true),
    ILLIDIUM_PICKAXE(tx("solar_fragment.illidium_pickaxe"),"illidium_pickaxe",Achievement.CRAFT_SOLAR_INFUSER,BookEntry.BEGINNER_ITEMS,ItemsRegister.VEIN_MINER.get().getDefaultInstance(),tx("illidium_pickaxe.lore"), SolarForge.INFUSING_RECIPE_TYPE,true),

    SOLAR_LENS(tx("solar_fragment.solar_lens"),"solar_lens",Achievement.ACQUIRE_COLD_STAR_ACTIVATED,BookEntry.SKILLED_ITEMS,ItemsRegister.SOLAR_LENS.get().getDefaultInstance(),tx("solar_lens.lore"), SolarForge.INFUSING_RECIPE_TYPE,true),

    QUALADIUM_INGOT(tx("solar_fragment.qualadium_ingot"),"qualadium_ingot",Achievement.CRAFT_SOLAR_LENS,BookEntry.SKILLED_MATERIALS,ItemsRegister.QUALADIUM_INGOT.get().getDefaultInstance(),tx("qualadium_ingot.lore"), SolarForge.SOLAR_SMELTING,true),
    SOLAR_GOD_SWORD(tx("solar_fragment.solar_god_sword"),"solar_god_sword",Achievement.CRAFT_SOLAR_LENS,BookEntry.SKILLED_ITEMS,ItemsRegister.SOLAR_GOD_SWORD.get().getDefaultInstance(),tx("solar_god_sword.lore"), SolarForge.INFUSING_RECIPE_TYPE,true),
    SOLAR_GOD_PICKAXE(tx("solar_fragment.solar_god_pickaxe"),"solar_god_pickaxe",Achievement.CRAFT_SOLAR_LENS,BookEntry.SKILLED_ITEMS,ItemsRegister.SOLAR_GOD_PICKAXE.get().getDefaultInstance(),tx("solar_god_pickaxe.lore"), SolarForge.INFUSING_RECIPE_TYPE,true),
    SOLAR_GOD_SHIELD(tx("solar_fragment.solar_god_shield"),"solar_god_shield",Achievement.CRAFT_SOLAR_LENS,BookEntry.SKILLED_ITEMS,ItemsRegister.SOLAR_GOD_SHIELD.get().getDefaultInstance(),tx("solar_god_shield.lore"), SolarForge.INFUSING_RECIPE_TYPE,true),
    QUALADIUM_SWORD(tx("solar_fragment.qualadium_sword"),"qualadium_sword",Achievement.CRAFT_SOLAR_LENS,BookEntry.SKILLED_ITEMS,ItemsRegister.QUALADIUM_SWORD.get().getDefaultInstance(),tx("qualadium_sword.lore"), SolarForge.INFUSING_RECIPE_TYPE,true),
    QUALADIUM_AXE(tx("solar_fragment.qualadium_axe"),"qualadium_axe",Achievement.CRAFT_SOLAR_LENS,BookEntry.SKILLED_ITEMS,ItemsRegister.QUALADIUM_AXE.get().getDefaultInstance(),tx("qualadium_axe.lore"), SolarForge.INFUSING_RECIPE_TYPE,true),
    QUALADIUM_SHOVEL(tx("solar_fragment.qualadium_shovel"),"qualadium_shovel",Achievement.CRAFT_SOLAR_LENS,BookEntry.SKILLED_ITEMS,ItemsRegister.QUALADIUM_SHOVEL.get().getDefaultInstance(),tx("qualadium_shovel.lore"), SolarForge.INFUSING_RECIPE_TYPE,true),
    QUALADIUM_HOE(tx("solar_fragment.qualadium_hoe"),"qualadium_hoe",Achievement.CRAFT_SOLAR_LENS,BookEntry.SKILLED_ITEMS,ItemsRegister.QUALADIUM_HOE.get().getDefaultInstance(),tx("qualadium_hoe.lore"), SolarForge.INFUSING_RECIPE_TYPE,true),
    QUALADIUM_PICKAXE(tx("solar_fragment.qualadium_pickaxe"),"qualadium_pickaxe",Achievement.CRAFT_SOLAR_LENS,BookEntry.SKILLED_ITEMS,ItemsRegister.QUALADIUM_PICKAXE.get().getDefaultInstance(),tx("qualadium_pickaxe.lore"), SolarForge.INFUSING_RECIPE_TYPE,true),


    CHARGED_QUALADIUM_SWORD(tx("solar_fragment.charged_qualadium_sword"),"charged_qualadium_sword",Achievement.CRAFT_SOLAR_ENERGY_GENERATOR,BookEntry.MASTER_ITEMS,ItemsRegister.CHARGED_QUALADIUM_SWORD.get().getDefaultInstance(),tx("charged_qualadium_sword.lore"), SolarForge.INFUSING_RECIPE_TYPE,true),
    CHARGED_QUALADIUM_AXE(tx("solar_fragment.charged_qualadium_axe"),"charged_qualadium_axe",Achievement.CRAFT_SOLAR_ENERGY_GENERATOR,BookEntry.MASTER_ITEMS,ItemsRegister.CHARGED_QUALADIUM_AXE.get().getDefaultInstance(),tx("charged_qualadium_axe.lore"), SolarForge.INFUSING_RECIPE_TYPE,true),
    CHARGED_QUALADIUM_SHOVEL(tx("solar_fragment.charged_qualadium_shovel"),"charged_qualadium_shovel",Achievement.CRAFT_SOLAR_ENERGY_GENERATOR,BookEntry.MASTER_ITEMS,ItemsRegister.CHARGED_QUALADIUM_SHOVEL.get().getDefaultInstance(),tx("charged_qualadium_shovel.lore"), SolarForge.INFUSING_RECIPE_TYPE,true),
    CHARGED_QUALADIUM_HOE(tx("solar_fragment.charged_qualadium_hoe"),"charged_qualadium_hoe",Achievement.CRAFT_SOLAR_ENERGY_GENERATOR,BookEntry.MASTER_ITEMS,ItemsRegister.CHARGED_QUALADIUM_HOE.get().getDefaultInstance(),tx("charged_qualadium_hoe.lore"), SolarForge.INFUSING_RECIPE_TYPE,true),
    CHARGED_QUALADIUM_PICKAXE(tx("solar_fragment.charged_qualadium_pickaxe"),"charged_qualadium_pickaxe",Achievement.CRAFT_SOLAR_ENERGY_GENERATOR,BookEntry.MASTER_ITEMS,ItemsRegister.CHARGED_QUALADIUM_PICKAXE.get().getDefaultInstance(),tx("charged_qualadium_pickaxe.lore"), SolarForge.INFUSING_RECIPE_TYPE,true),


    SOLAR_ENERGY_GENERATOR(tx("solar_fragment.solar_energy_generator"),"solar_energy_generator",Achievement.CRAFT_SOLAR_LENS,BookEntry.MASTER_ENERGY,ItemsRegister.ENERGY_GENERATOR_BLOCK.get().getDefaultInstance(),tx("solar_energy_generator.lore"), SolarForge.INFUSING_RECIPE_TYPE,true),
    SOLAR_CORE(tx("solar_fragment.solar_core"),"solar_core",Achievement.CRAFT_SOLAR_ENERGY_GENERATOR,BookEntry.MASTER_ENERGY,ItemsRegister.SOLAR_CORE.get().getDefaultInstance(),tx("solar_core.lore"), SolarForge.INFUSING_RECIPE_TYPE,true),
    SOLAR_ENERGY_REPEATER(tx("solar_fragment.solar_energy_repeater"),"solar_energy_repeater",Achievement.CRAFT_SOLAR_ENERGY_GENERATOR,BookEntry.MASTER_ENERGY,ItemsRegister.SOLAR_ENERGY_REPEATER.get().getDefaultInstance(),tx("solar_energy_repeater.lore"), SolarForge.INFUSING_RECIPE_TYPE,true),

    RADIANT_CHESTPLATE(tx("solar_fragment.radiant_cuirass"),"radiant_cuirass",Achievement.CRAFT_SOLAR_ENERGY_GENERATOR,BookEntry.ARMOR,ItemsRegister.RADIANT_CHESTPLATE.get().getDefaultInstance(),tx("radiant_cuirass.lore"), SolarForge.INFUSING_RECIPE_TYPE,true),
    MEDIUM_SOLAR_REACTOR(tx("solar_fragment.medium_solar_reactor"),"medium_solar_reactor",Achievement.CRAFT_SOLAR_ENERGY_GENERATOR,BookEntry.MASTER_MATERIALS,ItemsRegister.MEDIUM_SOLAR_REACTOR.get().getDefaultInstance(),tx("medium_solar_reactor.lore"), SolarForge.INFUSING_RECIPE_TYPE,true),
    CHARGED_QUALADIUM_INGOT(tx("solar_fragment.charged_qualadium_ingot"),"charged_qualadium_ingot",Achievement.CRAFT_SOLAR_ENERGY_GENERATOR,BookEntry.MASTER_MATERIALS,ItemsRegister.CHARGED_QUALADIUM_INGOT.get().getDefaultInstance(),tx("charged_qualadium_ingot.lore"), SolarForge.INFUSING_RECIPE_TYPE,true),
    SOLAR_MORTAR(tx("solar_fragment.solar_mortar"),"solar_mortar",Achievement.CRAFT_SOLAR_ENERGY_GENERATOR,BookEntry.MASTER_ITEMS,ItemsRegister.SOLAR_MORTAR.get().getDefaultInstance(),tx("solar_mortar.lore"), SolarForge.INFUSING_RECIPE_TYPE,true),
    SOLAR_FURNACE(tx("solar_fragment.solar_furnace"),"solar_furnace",Achievement.CRAFT_SOLAR_ENERGY_GENERATOR,BookEntry.MASTER_ITEMS,ItemsRegister.SOLAR_FURNACE_BLOCK.get().getDefaultInstance(),tx("solar_furnace.lore"), SolarForge.INFUSING_RECIPE_TYPE,true),
    TOTEM_OF_IMMORTALITY(tx("solar_fragment.totem_of_immortality"),"totem_of_immortality",Achievement.CRAFT_SOLAR_ENERGY_GENERATOR,BookEntry.MASTER_ITEMS,ItemsRegister.TOTEM_OF_IMMORTALITY.get().getDefaultInstance(),tx("totem_of_immortality.lore"), SolarForge.INFUSING_RECIPE_TYPE,true),
    SOLAR_CROSSBOW(tx("solar_fragment.solar_crossbow"),"solar_crossbow",Achievement.CRAFT_SOLAR_ENERGY_GENERATOR,BookEntry.MASTER_ITEMS,ItemsRegister.ULTRA_CROSSBOW.get().getDefaultInstance(),tx("solar_crossbow.lore"), SolarForge.INFUSING_RECIPE_TYPE,true),


    AURA_HEALER_STRUCTURE(tx("solar_fragment.aura_healer_structure"),"aura_healer_structure",Achievement.USE_SOLAR_INFUSER,BookEntry.STRUCTURES,Multiblocks.AURA_HEALER,true),
    SOLAR_MORTAR_STRUCTURE(tx("solar_fragment.solar_mortar_structure"),"solar_mortar_structure",SOLAR_MORTAR.neededProgression,BookEntry.STRUCTURES,Multiblocks.SOLAR_MORTAR,true),
    SOLAR_INFUSER_STRUCTURE(tx("solar_fragment.solar_infuser_structure"),"solar_infuser_structure",Achievement.CRAFT_SOLAR_INFUSER,BookEntry.STRUCTURES,Multiblocks.INFUSER,true),
    SOLAR_CORE_STRUCTURE(tx("solar_fragment.solar_core_structure"),"solar_core_structure",SOLAR_CORE.neededProgression,BookEntry.STRUCTURES,Multiblocks.SOLAR_CORE,true),
    SOLAR_ENERGY_GENERATOR_STRUCTURE(tx("solar_fragment.solar_energy_generator_structure"),"solar_energy_generator_structure",SOLAR_ENERGY_GENERATOR.neededProgression,BookEntry.STRUCTURES,Multiblocks.SOLAR_ENERGY_GENERATOR,true),

    SOLAR_GOD_PICKAXE_UPGRADE(tx("solar_fragment.solar_god_pickaxe_upgrade"),"solar_god_pickaxe_upgrade",Achievement.CRAFT_SOLAR_ENERGY_GENERATOR,BookEntry.UPGRADES,ItemsRegister.SOLAR_GOD_PICKAXE.get().getDefaultInstance(),tx("solar_pickaxe_upgrade.lore")),
    SOLAR_GOD_SWORD_UPGRADE(tx("solar_fragment.solar_god_sword_upgrade"),"solar_god_sword_upgrade",Achievement.CRAFT_SOLAR_ENERGY_GENERATOR,BookEntry.UPGRADES,ItemsRegister.SOLAR_GOD_SWORD.get().getDefaultInstance(),tx("solar_god_sword_upgrade.lore")),

    SOLAR_GOD_BOW(tx("solar_fragment.solar_god_bow"),"solar_god_bow",Achievement.CRAFT_SOLAR_ENERGY_GENERATOR,BookEntry.MASTER_ITEMS,ItemsRegister.SOLAR_GOD_BOW.get().getDefaultInstance(),tx("solar_god_bow.lore"), SolarForge.INFUSING_RECIPE_TYPE,true),
    SOLAR_GOD_BOW_UPGRADE(tx("solar_fragment.solar_god_bow_upgrade"),"solar_god_bow_upgrade",Achievement.CRAFT_SOLAR_ENERGY_GENERATOR,BookEntry.UPGRADES,ItemsRegister.SOLAR_GOD_BOW.get().getDefaultInstance(),tx("solar_god_bow_upgrade.lore")),
    ;

    public static Map<String,AncientFragment> FRAGMENTS_ID_MAP = new HashMap<>();


    public static AncientFragment[] FRAGMENTS_WITHOUT_TABLE_NEEDED = {
            RUNIC_TABLE,
            FRAGMENT,
            LEXICON,
            WAND
    };

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
    private final BookEntry entry;
    private Multiblocks structure;
    private ItemStack item;
    private TranslatableComponent lore;
    private TranslatableComponent itemLore;
    private RecipeType<?> recipeType;
    private final boolean requiresFragment;


    AncientFragment(TranslatableComponent translation, String id, Achievement neededProgression,BookEntry entry, Type type,Item Icon,boolean requiresFragment){
        this.translation = translation;
        this.id = id;
        this.neededProgression = neededProgression;
        this.type = type;
        this.icon = Icon;
        this.entry = entry;
        this.requiresFragment = requiresFragment;
    }

    AncientFragment(TranslatableComponent translation, String id, Achievement neededProgression,BookEntry entry, ItemStack item,TranslatableComponent itemLore,RecipeType<?> recipeType,boolean requiresFragment){
        this(translation,id,neededProgression,entry,Type.ITEM,item.getItem(),requiresFragment);
        this.item = item;
        this.itemLore = itemLore;
        this.recipeType = recipeType;
    }

    AncientFragment(TranslatableComponent translation, String id, Achievement neededProgression,BookEntry entry,  Multiblocks structure,boolean requiresFragment){
        this(translation,id,neededProgression,entry,Type.STRUCTURE,structure.getM().mainBlock.asItem(),requiresFragment);
        this.structure = structure;
    }

    AncientFragment(TranslatableComponent translation, String id, Achievement neededProgression,BookEntry entry, Item Icon, TranslatableComponent lore,boolean requiresFragment){
        this(translation,id,neededProgression,entry,Type.INFORMATION,Icon,requiresFragment);
        this.lore = lore;
    }

    AncientFragment(TranslatableComponent translation, String id, Achievement neededProgression,BookEntry entry, ItemStack item,TranslatableComponent upgradeLore){
        this(translation,id,neededProgression,entry,Type.UPGRADE,item.getItem(),true);
        this.item = item;
        this.itemLore = upgradeLore;
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

    public static AncientFragment[] getAllFragments(){
        return AncientFragment.class.getEnumConstants();
    }
    public enum Type{
        STRUCTURE,
        ITEM,
        INFORMATION,
        UPGRADE
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
