package com.finderfeed.solarforge.solar_lexicon.unlockables;

import com.finderfeed.solarforge.solar_lexicon.achievements.Achievement;
import net.minecraft.util.text.TranslationTextComponent;

import java.awt.*;

public enum BookEntry {
    ARMOR(tx("solar_category.armor"),point(20,40),Achievement.CRAFT_SOLAR_INFUSER),
    STRUCTURES(tx("solar_category.structures"),point(180,260),Achievement.CRAFT_SOLAR_INFUSER),
    MAGIC_MATERIALS(tx("solar_category.materials"),point(20,260),Achievement.FIND_SOLAR_STONE);




    private final TranslationTextComponent translation;
    private final Point placeInBook;
    private final Achievement toUnlock;

    BookEntry(TranslationTextComponent translation, Point placeInBook,Achievement toUnlock){
        this.placeInBook = placeInBook;
        this.translation = translation;
        this.toUnlock = toUnlock;
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

    public Achievement toUnlock() {
        return toUnlock;
    }

    public static Point point(int a,int b){
        return new Point(a,b);
    }

    public static TranslationTextComponent tx(String a){
        return new TranslationTextComponent(a);
    }
}
