package com.finderfeed.solarforge.magic_items.items.solar_lexicon.structure.subcategory;

import net.minecraft.network.chat.TranslatableComponent;

public enum SubCategoryBase {
    BEGGINING(tx("solar_category.beginning")),
    FORGE(tx("solar_category.solar_forge")),
    ARMOR(tx("solar_category.armor")),
    STRUCTURES(tx("solar_category.structures")),
    BEGINNER_ITEMS(tx("solar_category.beginner_items")),
    SKILLED_ITEMS(tx("solar_category.skilled_items")),
    SKILLED_MATERIALS(tx("solar_category.skilled_materials")),
    MASTER_ITEMS(tx("solar_category.master_items")),
    MASTER_ENERGY(tx("solar_category.master_energy")),
    MASTER_MATERIALS(tx("solar_category.master_materials")),
    UPGRADES(tx("solar_category.upgrades")),
    RADIANT_LAND(tx("solar_category.radiant_land"))
    ;
//    BEGINNING_INFO(tx("solar_category.beginning"),point(20,40),null,null),
//    SOLAR_FORGE_BASICS(tx("solar_category.solar_forge"),point(210,40),Achievement.ENTER_NETHER,null),
//    ARMOR(tx("solar_category.armor"),point(20,80),Achievement.CRAFT_SOLAR_INFUSER,null),
//    STRUCTURES(tx("solar_category.structures"),point(20,700),Achievement.CRAFT_SOLAR_INFUSER,null),
//
//    BEGINNER(tx("solar_category.beginner"),point(20,220),Achievement.CRAFT_SOLAR_INFUSER,null),
//    BEGINNER_ITEMS(tx("solar_category.beginner_items"),point(180,260),Achievement.CRAFT_SOLAR_INFUSER,BEGINNER),
//
//    SKILLED(tx("solar_category.skilled"),point(20,400),Achievement.ACQUIRE_COLD_STAR_ACTIVATED,null),
//    SKILLED_ITEMS(tx("solar_category.skilled_items"),point(180,260),Achievement.ACQUIRE_COLD_STAR_ACTIVATED,SKILLED),
//    SKILLED_MATERIALS(tx("solar_category.skilled_materials"),point(180,260),Achievement.ACQUIRE_COLD_STAR_ACTIVATED,SKILLED),
//
//    MASTER(tx("solar_category.master"),point(20,560),Achievement.CRAFT_SOLAR_LENS,null),
//    MASTER_ITEMS(tx("solar_category.master_items"),point(180,260),Achievement.CRAFT_SOLAR_ENERGY_GENERATOR,MASTER),
//    MASTER_ENERGY(tx("solar_category.master_energy"),point(180,260),Achievement.CRAFT_SOLAR_ENERGY_GENERATOR,MASTER),
//    MASTER_MATERIALS(tx("solar_category.master_materials"),point(180,260),Achievement.CRAFT_SOLAR_ENERGY_GENERATOR,MASTER),
//
//    UPGRADES(tx("solar_category.upgrades"),point(210,80),Achievement.CRAFT_SOLAR_ENERGY_GENERATOR,null)


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
