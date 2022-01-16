package com.finderfeed.solarforge.magic_items.items.solar_lexicon.structure.subcategory;

import net.minecraft.network.chat.TranslatableComponent;

public enum SubCategoryBase {
    BEGGINING(tx("solar_category.beginning")),
    BASIC_DUSTS(tx("solar_category.basic_dusts")),
    ARMOR(tx("solar_category.armor")),
    STRUCTURES(tx("solar_category.structures")),
    BEGINNER_ITEMS(tx("solar_category.beginner_items")),
    BEGINNER_MATERIALS(tx("solar_category.beginner_materials")),
    RUNIC_ENERGY_EXPLORATION(tx("solar_category.runic_energy_exploration")),
    RUNIC_ENERGY_TRANSMITTING(tx("solar_category.runic_energy_transmitting")),
    SKILLED_ITEMS(tx("solar_category.skilled_items")),
    SKILLED_BLOCKS(tx("solar_category.skilled_blocks")),
    SKILLED_MATERIALS(tx("solar_category.skilled_materials")),
    MASTER_ITEMS(tx("solar_category.master_items")),
    MASTER_ENERGY(tx("solar_category.master_energy")),
    MASTER_MATERIALS(tx("solar_category.master_materials")),
    UPGRADES(tx("solar_category.upgrades")),
    RADIANT_LAND(tx("solar_category.radiant_land"))
    ;



    private final TranslatableComponent translation;

    SubCategoryBase(TranslatableComponent comp){
        this.translation = comp;
    }

    public TranslatableComponent getTranslation() {
        return translation;
    }

    public static TranslatableComponent tx(String a){
        return new TranslatableComponent(a);
    }
}
