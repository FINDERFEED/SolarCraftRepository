package com.finderfeed.solarforge.solar_lexicon.unlockables;

import com.finderfeed.solarforge.SolarForge;
import com.finderfeed.solarforge.multiblocks.Multiblocks;
import com.finderfeed.solarforge.registries.items.ItemsRegister;
import com.finderfeed.solarforge.solar_lexicon.achievements.Achievement;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.text.TranslationTextComponent;

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

    BLOCK_BOOMERANG(tx("solar_fragment.block_boomerang"),"block_boomerang",Achievement.CRAFT_SOLAR_INFUSER,BookEntry.BEGINNER_ITEMS,ItemsRegister.BLOCK_BOOMERANG.get().getDefaultInstance(),tx("block_boomerang.lore"), SolarForge.INFUSING_RECIPE_TYPE),
    ILLIDIUM_SWORD(tx("solar_fragment.illidium_sword"),"illidium_sword",Achievement.CRAFT_SOLAR_INFUSER,BookEntry.BEGINNER_ITEMS,ItemsRegister.ILLIDIUM_SWORD.get().getDefaultInstance(),tx("illidium_sword.lore"), SolarForge.INFUSING_RECIPE_TYPE),
    ILLIDIUM_AXE(tx("solar_fragment.illidium_axe"),"illidium_axe",Achievement.CRAFT_SOLAR_INFUSER,BookEntry.BEGINNER_ITEMS,ItemsRegister.ILLIDIUM_AXE.get().getDefaultInstance(),tx("illidium_axe.lore"), SolarForge.INFUSING_RECIPE_TYPE),
    ILLIDIUM_SHOVEL(tx("solar_fragment.illidium_shovel"),"illidium_shovel",Achievement.CRAFT_SOLAR_INFUSER,BookEntry.BEGINNER_ITEMS,ItemsRegister.ILLIDIUM_SHOVEL.get().getDefaultInstance(),tx("illidium_shovel.lore"), SolarForge.INFUSING_RECIPE_TYPE),
    ILLIDIUM_HOE(tx("solar_fragment.illidium_hoe"),"illidium_hoe",Achievement.CRAFT_SOLAR_INFUSER,BookEntry.BEGINNER_ITEMS,ItemsRegister.GROWTH_HOE.get().getDefaultInstance(),tx("illidium_hoe.lore"), SolarForge.INFUSING_RECIPE_TYPE),
    ILLIDIUM_PICKAXE(tx("solar_fragment.illidium_pickaxe"),"illidium_pickaxe",Achievement.CRAFT_SOLAR_INFUSER,BookEntry.BEGINNER_ITEMS,ItemsRegister.VEIN_MINER.get().getDefaultInstance(),tx("illidium_pickaxe.lore"), SolarForge.INFUSING_RECIPE_TYPE),


    QUALADIUM_SWORD(tx("solar_fragment.qualadium_sword"),"qualadium_sword",Achievement.CRAFT_SOLAR_LENS,BookEntry.SKILLED_ITEMS,ItemsRegister.QUALADIUM_SWORD.get().getDefaultInstance(),tx("qualadium_sword.lore"), SolarForge.INFUSING_RECIPE_TYPE),
    QUALADIUM_AXE(tx("solar_fragment.qualadium_axe"),"qualadium_axe",Achievement.CRAFT_SOLAR_LENS,BookEntry.SKILLED_ITEMS,ItemsRegister.QUALADIUM_AXE.get().getDefaultInstance(),tx("qualadium_axe.lore"), SolarForge.INFUSING_RECIPE_TYPE),
    QUALADIUM_SHOVEL(tx("solar_fragment.qualadium_shovel"),"qualadium_shovel",Achievement.CRAFT_SOLAR_LENS,BookEntry.SKILLED_ITEMS,ItemsRegister.QUALADIUM_SHOVEL.get().getDefaultInstance(),tx("qualadium_shovel.lore"), SolarForge.INFUSING_RECIPE_TYPE),
    QUALADIUM_HOE(tx("solar_fragment.qualadium_hoe"),"qualadium_hoe",Achievement.CRAFT_SOLAR_LENS,BookEntry.SKILLED_ITEMS,ItemsRegister.QUALADIUM_HOE.get().getDefaultInstance(),tx("qualadium_hoe.lore"), SolarForge.INFUSING_RECIPE_TYPE),
    QUALADIUM_PICKAXE(tx("solar_fragment.qualadium_pickaxe"),"qualadium_pickaxe",Achievement.CRAFT_SOLAR_LENS,BookEntry.SKILLED_ITEMS,ItemsRegister.QUALADIUM_PICKAXE.get().getDefaultInstance(),tx("qualadium_pickaxe.lore"), SolarForge.INFUSING_RECIPE_TYPE),
    ;


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


    public AncientFragment[] getAllFragments(){
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
