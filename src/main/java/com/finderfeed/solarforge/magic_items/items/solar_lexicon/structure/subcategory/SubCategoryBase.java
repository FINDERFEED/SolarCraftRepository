package com.finderfeed.solarforge.magic_items.items.solar_lexicon.structure.subcategory;

import net.minecraft.network.chat.TranslatableComponent;

public enum SubCategoryBase {
;



    private final TranslatableComponent translation;

    SubCategoryBase(TranslatableComponent comp){
        this.translation = comp;
    }

    public TranslatableComponent getTranslation() {
        return translation;
    }
}
