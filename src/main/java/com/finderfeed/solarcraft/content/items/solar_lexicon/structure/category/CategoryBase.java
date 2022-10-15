package com.finderfeed.solarcraft.content.items.solar_lexicon.structure.category;

import net.minecraft.network.chat.Component;

public enum CategoryBase {
    BEGGINING_INFO(tx("solar_category.beginning"),1,1),
    EXPLORATION(tx("solar_category.exploration"),2,1),
    ARMOR(tx("solar_category.armor"),3,1),
    STRUCTURES(tx("solar_category.structures"),1,6),
    UPGRADES(tx("solar_category.upgrades"),2,2),
    RUNIC_ENERGY(tx("solar_category.runic_energy"),2,2),
    BEGINNER(tx("solar_category.beginner"),1,3),
    SKILLED(tx("solar_category.skilled"),1,4),
    MASTER(tx("solar_category.master"),1,5),
    ENDGAME(tx("solar_category.endgame"),1,6);
    ;

    private final Component translation;
    private final int widthPriority;
    private final int heightPriority;

    CategoryBase(Component comp,int x,int y){
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

    public Component getTranslation() {
        return translation;
    }
    public static Component tx(String a){
        return Component.translatable(a);
    }
    public static CategoryBase getByID(String id){
        for (CategoryBase v : CategoryBase.class.getEnumConstants()){
            if (v.name().equals(id)){
                return v;
            }
        }
        return null;
    }
}
