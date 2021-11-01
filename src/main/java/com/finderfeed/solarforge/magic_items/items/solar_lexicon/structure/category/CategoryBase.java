package com.finderfeed.solarforge.magic_items.items.solar_lexicon.structure.category;

import net.minecraft.network.chat.TranslatableComponent;

public enum CategoryBase {
    BEGGINING_INFO(tx("solar_category.beginning"),1,1),
    SOLAR_FORGE_BASICS(tx("solar_category.solar_forge"),2,1),
    ARMOR(tx("solar_category.armor"),3,1),
    STRUCTURES(tx("solar_category.structures"),4,1),
    UPGRADES(tx("solar_category.upgrades"),5,1),
    BEGINNER(tx("solar_category.beginner"),1,2),
    SKILLED(tx("solar_category.skilled"),1,3),
    MASTER(tx("solar_category.master"),1,4),
    MIDGAME(tx("solar_category.midgame"),1,5);
    ;

    private final TranslatableComponent translation;
    private final int widthPriority;
    private final int heightPriority;

    CategoryBase(TranslatableComponent comp,int x,int y){
        this.translation = comp;
        this.widthPriority = x;
        this.heightPriority = y;
    }


    public int getHeightPriority() {
        return heightPriority;
    }

    public int getWidthPriority() {
        return widthPriority;
    }

    public TranslatableComponent getTranslation() {
        return translation;
    }
    public static TranslatableComponent tx(String a){
        return new TranslatableComponent(a);
    }
}
