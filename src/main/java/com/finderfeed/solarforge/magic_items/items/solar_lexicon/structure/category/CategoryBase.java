package com.finderfeed.solarforge.magic_items.items.solar_lexicon.structure.category;

import net.minecraft.network.chat.TranslatableComponent;

public enum CategoryBase {
    ;

    private final TranslatableComponent translation;

    CategoryBase(TranslatableComponent comp){
        this.translation = comp;
    }

    public TranslatableComponent getTranslation() {
        return translation;
    }
}
