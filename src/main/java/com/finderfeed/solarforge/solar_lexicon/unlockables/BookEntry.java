package com.finderfeed.solarforge.solar_lexicon.unlockables;

import net.minecraft.util.text.TranslationTextComponent;

import java.awt.*;

public enum BookEntry {
    ;

    private final TranslationTextComponent translation;
    private final Point placeInBook;

    BookEntry(TranslationTextComponent translation, Point placeInBook){
        this.placeInBook = placeInBook;
        this.translation = translation;
    }


    public TranslationTextComponent getTranslation() {
        return translation;

    }

    public Point getPlaceInBook() {
        return placeInBook;
    }

    public BookEntry[] getAllEntries(){
        return BookEntry.class.getEnumConstants();
    }

}
