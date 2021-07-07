package com.finderfeed.solarforge.solar_lexicon.unlockables;

import com.finderfeed.solarforge.SolarForge;
import com.finderfeed.solarforge.multiblocks.Multiblocks;
import com.finderfeed.solarforge.registries.items.ItemsRegister;
import com.finderfeed.solarforge.solar_lexicon.achievements.Achievement;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.HashMap;
import java.util.Map;

public enum AncientFragment {
    LEXICON(tx("solar_fragment.lexicon"),"lexicon",null,BookEntry.BEGINNING_INFO, ItemsRegister.SOLAR_LEXICON.get(),tx("lexicon.lore")),
    WAND(tx("solar_fragment.wand"),"wand",null,BookEntry.BEGINNING_INFO, ItemsRegister.SOLAR_WAND.get(),tx("wand.lore")),
    SOLAR_DUST(tx("solar_fragment.solar_dust"),"solar_dust",Achievement.CRAFT_SOLAR_FORGE,BookEntry.SOLAR_FORGE_BASICS, ItemsRegister.SOLAR_DUST.get(),tx("solar_dust.lore")),
    ENERGY_DUST(tx("solar_fragment.energy_dust"),"energy_dust",Achievement.CRAFT_SOLAR_FORGE,BookEntry.SOLAR_FORGE_BASICS, ItemsRegister.ENERGY_DUST.get(),tx("energy_dust.lore")),
    SOLAR_FORGE(tx("solar_fragment.solar_forge"),"solar_forge",Achievement.RUNE_ENERGY_CLAIM,BookEntry.SOLAR_FORGE_BASICS, ItemsRegister.SOLAR_DUST.get(),tx("solar_forge.lore")),
    SOLAR_HELMET(tx("solar_fragment.solar_helmet"),"solar_helmet",Achievement.CRAFT_SOLAR_INFUSER,BookEntry.ARMOR,ItemsRegister.SOLAR_HELMET.get().getDefaultInstance(),tx("solar_helmet.lore"), SolarForge.INFUSING_RECIPE_TYPE),
    SOLAR_CHESTPLATE(tx("solar_fragment.solar_chestplate"),"solar_chestplate",Achievement.CRAFT_SOLAR_INFUSER,BookEntry.ARMOR,ItemsRegister.SOLAR_CHESTPLATE.get().getDefaultInstance(),tx("solar_chestplate.lore"), SolarForge.INFUSING_RECIPE_TYPE),
    SOLAR_LEGGINS(tx("solar_fragment.solar_leggings"),"solar_leggings",Achievement.CRAFT_SOLAR_INFUSER,BookEntry.ARMOR,ItemsRegister.SOLAR_LEGGINS.get().getDefaultInstance(),tx("solar_leggings.lore"), SolarForge.INFUSING_RECIPE_TYPE),
    SOLAR_BOOTS(tx("solar_fragment.solar_boots"),"solar_boots",Achievement.CRAFT_SOLAR_INFUSER,BookEntry.ARMOR,ItemsRegister.SOLAR_HELMET.get().getDefaultInstance(),tx("solar_boots.lore"), SolarForge.INFUSING_RECIPE_TYPE),
    ITEM_MAGNET(tx("solar_fragment.item_magnet"),"item_magnet",Achievement.CRAFT_SOLAR_INFUSER,BookEntry.BEGINNER_ITEMS,ItemsRegister.MAGNET_BLOCK.get().getDefaultInstance(),tx("item_magnet.lore"), SolarForge.INFUSING_RECIPE_TYPE),
    AURA_HEALER(tx("solar_fragment.aura_healer"),"aura_healer",Achievement.CRAFT_SOLAR_INFUSER,BookEntry.BEGINNER_ITEMS,ItemsRegister.AURA_HEALER.get().getDefaultInstance(),tx("aura_healer.lore"), SolarForge.INFUSING_RECIPE_TYPE),
    DISC_LAUNCHER(tx("solar_fragment.disc_launcher"),"disc_launcher",Achievement.USE_SOLAR_INFUSER,BookEntry.BEGINNER_ITEMS,ItemsRegister.SOLAR_DISC_LAUNCHER.get().getDefaultInstance(),tx("disc_launcher.lore"), SolarForge.INFUSING_RECIPE_TYPE),
    SOLAR_TURRET(tx("solar_fragment.solar_turret"),"solar_turret",Achievement.USE_SOLAR_INFUSER,BookEntry.BEGINNER_ITEMS,ItemsRegister.TURRET_BLOCK.get().getDefaultInstance(),tx("solar_turret.lore"), SolarForge.INFUSING_RECIPE_TYPE),
    MANA_REGEN_AMULET(tx("solar_fragment.mana_regen_amulet"),"mana_regen_amulet",Achievement.USE_SOLAR_INFUSER,BookEntry.BEGINNER_ITEMS,ItemsRegister.SOLAR_MANA_AMULET.get().getDefaultInstance(),tx("mana_regen_amulet.lore"), SolarForge.INFUSING_RECIPE_TYPE),

    SMALL_SOLAR_REACTOR(tx("solar_fragment.small_solar_reactor"),"small_solar_reactor",Achievement.CRAFT_SOLAR_INFUSER,BookEntry.BEGINNER_ITEMS,ItemsRegister.SMALL_SOLAR_REACTOR.get().getDefaultInstance(),tx("small_solar_reactor.lore"), SolarForge.INFUSING_RECIPE_TYPE),
    ILLIDIUM_INGOT(tx("solar_fragment.illidium_ingot"),"illidium_ingot",Achievement.CRAFT_SOLAR_INFUSER,BookEntry.BEGINNER_ITEMS,ItemsRegister.ILLIDIUM_INGOT.get().getDefaultInstance(),tx("illidium_ingot.lore"), SolarForge.INFUSING_RECIPE_TYPE),
    ALGADIUM_INGOT(tx("solar_fragment.algadium_ingot"),"algadium_ingot",Achievement.CRAFT_SOLAR_INFUSER,BookEntry.BEGINNER_ITEMS,ItemsRegister.SMALL_SOLAR_REACTOR.get().getDefaultInstance(),tx("algadium_ingot.lore"), SolarForge.INFUSING_RECIPE_TYPE),
    BLOCK_BOOMERANG(tx("solar_fragment.block_boomerang"),"block_boomerang",Achievement.CRAFT_SOLAR_INFUSER,BookEntry.BEGINNER_ITEMS,ItemsRegister.BLOCK_BOOMERANG.get().getDefaultInstance(),tx("block_boomerang.lore"), SolarForge.INFUSING_RECIPE_TYPE),
    ILLIDIUM_SWORD(tx("solar_fragment.illidium_sword"),"illidium_sword",Achievement.CRAFT_SOLAR_INFUSER,BookEntry.BEGINNER_ITEMS,ItemsRegister.ILLIDIUM_SWORD.get().getDefaultInstance(),tx("illidium_sword.lore"), SolarForge.INFUSING_RECIPE_TYPE),
    ILLIDIUM_AXE(tx("solar_fragment.illidium_axe"),"illidium_axe",Achievement.CRAFT_SOLAR_INFUSER,BookEntry.BEGINNER_ITEMS,ItemsRegister.ILLIDIUM_AXE.get().getDefaultInstance(),tx("illidium_axe.lore"), SolarForge.INFUSING_RECIPE_TYPE),
    ILLIDIUM_SHOVEL(tx("solar_fragment.illidium_shovel"),"illidium_shovel",Achievement.CRAFT_SOLAR_INFUSER,BookEntry.BEGINNER_ITEMS,ItemsRegister.ILLIDIUM_SHOVEL.get().getDefaultInstance(),tx("illidium_shovel.lore"), SolarForge.INFUSING_RECIPE_TYPE),
    ILLIDIUM_HOE(tx("solar_fragment.illidium_hoe"),"illidium_hoe",Achievement.CRAFT_SOLAR_INFUSER,BookEntry.BEGINNER_ITEMS,ItemsRegister.GROWTH_HOE.get().getDefaultInstance(),tx("illidium_hoe.lore"), SolarForge.INFUSING_RECIPE_TYPE),
    ILLIDIUM_PICKAXE(tx("solar_fragment.illidium_pickaxe"),"illidium_pickaxe",Achievement.CRAFT_SOLAR_INFUSER,BookEntry.BEGINNER_ITEMS,ItemsRegister.VEIN_MINER.get().getDefaultInstance(),tx("illidium_pickaxe.lore"), SolarForge.INFUSING_RECIPE_TYPE),

    SOLAR_LENS(tx("solar_fragment.solar_lens"),"solar_lens",Achievement.ACQUIRE_COLD_STAR_ACTIVATED,BookEntry.SKILLED_ITEMS,ItemsRegister.SOLAR_LENS.get().getDefaultInstance(),tx("solar_lens.lore"), SolarForge.INFUSING_RECIPE_TYPE),

    QUALADIUM_INGOT(tx("solar_fragment.solar_energy_generator"),"solar_energy_generator",Achievement.CRAFT_SOLAR_LENS,BookEntry.SKILLED_MATERIALS,ItemsRegister.QUALADIUM_INGOT.get().getDefaultInstance(),tx("solar_energy_generator.lore"), SolarForge.SOLAR_SMELTING),
    SOLAR_GOD_SWORD(tx("solar_fragment.solar_god_sword"),"solar_god_sword",Achievement.CRAFT_SOLAR_LENS,BookEntry.SKILLED_ITEMS,ItemsRegister.SOLAR_GOD_SWORD.get().getDefaultInstance(),tx("solar_god_sword.lore"), SolarForge.INFUSING_RECIPE_TYPE),
    SOLAR_GOD_SHIELD(tx("solar_fragment.solar_god_shield"),"solar_god_shield",Achievement.CRAFT_SOLAR_LENS,BookEntry.SKILLED_ITEMS,ItemsRegister.SOLAR_GOD_SHIELD.get().getDefaultInstance(),tx("solar_god_shield.lore"), SolarForge.INFUSING_RECIPE_TYPE),
    QUALADIUM_SWORD(tx("solar_fragment.qualadium_sword"),"qualadium_sword",Achievement.CRAFT_SOLAR_LENS,BookEntry.SKILLED_ITEMS,ItemsRegister.QUALADIUM_SWORD.get().getDefaultInstance(),tx("qualadium_sword.lore"), SolarForge.INFUSING_RECIPE_TYPE),
    QUALADIUM_AXE(tx("solar_fragment.qualadium_axe"),"qualadium_axe",Achievement.CRAFT_SOLAR_LENS,BookEntry.SKILLED_ITEMS,ItemsRegister.QUALADIUM_AXE.get().getDefaultInstance(),tx("qualadium_axe.lore"), SolarForge.INFUSING_RECIPE_TYPE),
    QUALADIUM_SHOVEL(tx("solar_fragment.qualadium_shovel"),"qualadium_shovel",Achievement.CRAFT_SOLAR_LENS,BookEntry.SKILLED_ITEMS,ItemsRegister.QUALADIUM_SHOVEL.get().getDefaultInstance(),tx("qualadium_shovel.lore"), SolarForge.INFUSING_RECIPE_TYPE),
    QUALADIUM_HOE(tx("solar_fragment.qualadium_hoe"),"qualadium_hoe",Achievement.CRAFT_SOLAR_LENS,BookEntry.SKILLED_ITEMS,ItemsRegister.QUALADIUM_HOE.get().getDefaultInstance(),tx("qualadium_hoe.lore"), SolarForge.INFUSING_RECIPE_TYPE),
    QUALADIUM_PICKAXE(tx("solar_fragment.qualadium_pickaxe"),"qualadium_pickaxe",Achievement.CRAFT_SOLAR_LENS,BookEntry.SKILLED_ITEMS,ItemsRegister.QUALADIUM_PICKAXE.get().getDefaultInstance(),tx("qualadium_pickaxe.lore"), SolarForge.INFUSING_RECIPE_TYPE),


    CHARGED_QUALADIUM_SWORD(tx("solar_fragment.charged_qualadium_sword"),"charged_qualadium_sword",Achievement.CRAFT_SOLAR_ENERGY_GENERATOR,BookEntry.MASTER_ITEMS,ItemsRegister.CHARGED_QUALADIUM_SWORD.get().getDefaultInstance(),tx("charged_qualadium_sword.lore"), SolarForge.INFUSING_RECIPE_TYPE),
    CHARGED_QUALADIUM_AXE(tx("solar_fragment.charged_qualadium_axe"),"charged_qualadium_axe",Achievement.CRAFT_SOLAR_ENERGY_GENERATOR,BookEntry.MASTER_ITEMS,ItemsRegister.CHARGED_QUALADIUM_AXE.get().getDefaultInstance(),tx("charged_qualadium_axe.lore"), SolarForge.INFUSING_RECIPE_TYPE),
    CHARGED_QUALADIUM_SHOVEL(tx("solar_fragment.charged_qualadium_shovel"),"charged_qualadium_shovel",Achievement.CRAFT_SOLAR_ENERGY_GENERATOR,BookEntry.MASTER_ITEMS,ItemsRegister.CHARGED_QUALADIUM_SHOVEL.get().getDefaultInstance(),tx("charged_qualadium_shovel.lore"), SolarForge.INFUSING_RECIPE_TYPE),
    CHARGED_QUALADIUM_HOE(tx("solar_fragment.charged_qualadium_hoe"),"charged_qualadium_hoe",Achievement.CRAFT_SOLAR_ENERGY_GENERATOR,BookEntry.MASTER_ITEMS,ItemsRegister.CHARGED_QUALADIUM_HOE.get().getDefaultInstance(),tx("charged_qualadium_hoe.lore"), SolarForge.INFUSING_RECIPE_TYPE),
    CHARGED_QUALADIUM_PICKAXE(tx("solar_fragment.charged_qualadium_pickaxe"),"charged_qualadium_pickaxe",Achievement.CRAFT_SOLAR_ENERGY_GENERATOR,BookEntry.MASTER_ITEMS,ItemsRegister.CHARGED_QUALADIUM_PICKAXE.get().getDefaultInstance(),tx("charged_qualadium_pickaxe.lore"), SolarForge.INFUSING_RECIPE_TYPE),


    SOLAR_ENERGY_GENERATOR(tx("solar_fragment.solar_energy_generator"),"solar_energy_generator",Achievement.CRAFT_SOLAR_LENS,BookEntry.MASTER_ENERGY,ItemsRegister.ENERGY_GENERATOR_BLOCK.get().getDefaultInstance(),tx("solar_energy_generator.lore"), SolarForge.INFUSING_RECIPE_TYPE),
    SOLAR_CORE(tx("solar_fragment.solar_core"),"solar_core",Achievement.CRAFT_SOLAR_ENERGY_GENERATOR,BookEntry.MASTER_ENERGY,ItemsRegister.SOLAR_CORE.get().getDefaultInstance(),tx("solar_core.lore"), SolarForge.INFUSING_RECIPE_TYPE),
    SOLAR_ENERGY_REPEATER(tx("solar_fragment.solar_energy_repeater"),"solar_energy_repeater",Achievement.CRAFT_SOLAR_ENERGY_GENERATOR,BookEntry.MASTER_ENERGY,ItemsRegister.SOLAR_ENERGY_REPEATER.get().getDefaultInstance(),tx("solar_energy_repeater.lore"), SolarForge.INFUSING_RECIPE_TYPE),

    MEDIUM_SOLAR_REACTOR(tx("solar_fragment.medium_solar_reactor"),"medium_solar_reactor",Achievement.CRAFT_SOLAR_ENERGY_GENERATOR,BookEntry.MASTER_MATERIALS,ItemsRegister.MEDIUM_SOLAR_REACTOR.get().getDefaultInstance(),tx("medium_solar_reactor.lore"), SolarForge.INFUSING_RECIPE_TYPE),
    CHARGED_QUALADIUM_INGOT(tx("solar_fragment.charged_qualadium_ingot"),"charged_qualadium_ingot",Achievement.CRAFT_SOLAR_ENERGY_GENERATOR,BookEntry.MASTER_MATERIALS,ItemsRegister.CHARGED_QUALADIUM_INGOT.get().getDefaultInstance(),tx("charged_qualadium_ingot.lore"), SolarForge.INFUSING_RECIPE_TYPE),
    SOLAR_MORTAR(tx("solar_fragment.solar_mortar"),"solar_mortar",Achievement.CRAFT_SOLAR_ENERGY_GENERATOR,BookEntry.MASTER_ITEMS,ItemsRegister.SOLAR_MORTAR.get().getDefaultInstance(),tx("solar_mortar.lore"), SolarForge.INFUSING_RECIPE_TYPE),
    SOLAR_FURNACE(tx("solar_fragment.solar_furnace"),"solar_furnace",Achievement.CRAFT_SOLAR_ENERGY_GENERATOR,BookEntry.MASTER_ITEMS,ItemsRegister.SOLAR_FURNACE_BLOCK.get().getDefaultInstance(),tx("solar_furnace.lore"), SolarForge.INFUSING_RECIPE_TYPE),
    TOTEM_OF_IMMORTALITY(tx("solar_fragment.totem_of_immortality"),"totem_of_immortality",Achievement.CRAFT_SOLAR_ENERGY_GENERATOR,BookEntry.MASTER_ITEMS,ItemsRegister.TOTEM_OF_IMMORTALITY.get().getDefaultInstance(),tx("totem_of_immortality.lore"), SolarForge.INFUSING_RECIPE_TYPE),
    SOLAR_CROSSBOW(tx("solar_fragment.solar_crossbow"),"solar_crossbow",Achievement.CRAFT_SOLAR_ENERGY_GENERATOR,BookEntry.MASTER_ITEMS,ItemsRegister.ULTRA_CROSSBOW.get().getDefaultInstance(),tx("solar_crossbow.lore"), SolarForge.INFUSING_RECIPE_TYPE),

    ;

    public static Map<String,AncientFragment> FRAGMENTS_ID_MAP = new HashMap<>();

    private final TranslationTextComponent translation;
    private final String id;
    private final Achievement neededProgression;
    private final Type type;
    private final Item icon;
    private final BookEntry entry;
    private Multiblocks structure;
    private ItemStack item;
    private TranslationTextComponent lore;
    private TranslationTextComponent itemLore;
    private IRecipeType<?> recipeType;

    AncientFragment(TranslationTextComponent translation, String id, Achievement neededProgression,BookEntry entry, Type type,Item Icon){
        this.translation = translation;
        this.id = id;
        this.neededProgression = neededProgression;
        this.type = type;
        this.icon = Icon;
        this.entry = entry;
    }

    AncientFragment(TranslationTextComponent translation, String id, Achievement neededProgression,BookEntry entry, ItemStack item,TranslationTextComponent itemLore,IRecipeType<?> recipeType){
        this(translation,id,neededProgression,entry,Type.ITEM,item.getItem());
        this.item = item;
        this.itemLore = itemLore;
        this.recipeType = recipeType;
    }

    AncientFragment(TranslationTextComponent translation, String id, Achievement neededProgression,BookEntry entry,  Multiblocks structure){
        this(translation,id,neededProgression,entry,Type.STRUCTURE,structure.getM().mainBlock.asItem());
        this.structure = structure;
    }

    AncientFragment(TranslationTextComponent translation, String id, Achievement neededProgression,BookEntry entry, Item Icon, TranslationTextComponent lore){
        this(translation,id,neededProgression,entry,Type.INFORMATION,Icon);
        this.lore = lore;
    }

    public IRecipeType<?> getRecipeType() {
        return recipeType;
    }

    public TranslationTextComponent getItemDescription(){
        return itemLore;
    }

    public TranslationTextComponent getLore() {
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

    public TranslationTextComponent getTranslation() {
        return translation;
    }

    public Achievement getNeededProgression() {
        return neededProgression;
    }

    public String getId() {
        return id;
    }


    public static AncientFragment[] getAllFragments(){
        return AncientFragment.class.getEnumConstants();
    }
    enum Type{
        STRUCTURE,
        ITEM,
        INFORMATION
    }
    public static TranslationTextComponent tx(String a){
        return new TranslationTextComponent(a);
    }
}
