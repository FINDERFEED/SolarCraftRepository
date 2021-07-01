package com.finderfeed.solarforge.solar_lexicon.unlockables;

import com.finderfeed.solarforge.multiblocks.Multiblocks;
import com.finderfeed.solarforge.solar_lexicon.achievements.Achievement;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TranslationTextComponent;

public enum AncientFragment {
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

    AncientFragment(TranslationTextComponent translation, String id, Achievement neededProgression,BookEntry entry, Type type,Item Icon){
        this.translation = translation;
        this.id = id;
        this.neededProgression = neededProgression;
        this.type = type;
        this.icon = Icon;
        this.entry = entry;
    }

    AncientFragment(TranslationTextComponent translation, String id, Achievement neededProgression,BookEntry entry, ItemStack item,TranslationTextComponent itemLore){
        this(translation,id,neededProgression,entry,Type.ITEM,item.getItem());
        this.item = item;
        this.itemLore = itemLore;
    }

    AncientFragment(TranslationTextComponent translation, String id, Achievement neededProgression,BookEntry entry,  Multiblocks structure){
        this(translation,id,neededProgression,entry,Type.STRUCTURE,structure.getM().mainBlock.asItem());
        this.structure = structure;
    }

    AncientFragment(TranslationTextComponent translation, String id, Achievement neededProgression,BookEntry entry, Item Icon, TranslationTextComponent lore){
        this(translation,id,neededProgression,entry,Type.INFORMATION,Icon);
        this.lore = lore;
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
}
