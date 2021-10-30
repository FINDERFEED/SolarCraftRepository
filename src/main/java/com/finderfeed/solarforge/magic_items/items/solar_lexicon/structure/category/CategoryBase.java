package com.finderfeed.solarforge.magic_items.items.solar_lexicon.structure.category;

import net.minecraft.network.chat.TranslatableComponent;

public enum CategoryBase {
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
}
