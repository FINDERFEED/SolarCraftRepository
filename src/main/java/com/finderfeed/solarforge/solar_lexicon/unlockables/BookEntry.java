package com.finderfeed.solarforge.solar_lexicon.unlockables;

import com.finderfeed.solarforge.solar_lexicon.achievements.Achievement;
import net.minecraft.util.text.TranslationTextComponent;

import javax.annotation.Nullable;
import java.awt.*;

public enum BookEntry {
    BEGINNING_INFO(tx("solar_category.beginning"),point(20,40),null,null),
    SOLAR_FORGE_BASICS(tx("solar_category.solar_forge"),point(210,40),Achievement.CRAFT_SOLAR_FORGE,null),
    ARMOR(tx("solar_category.armor"),point(20,40),Achievement.CRAFT_SOLAR_INFUSER,null),
    STRUCTURES(tx("solar_category.structures"),point(180,260),Achievement.CRAFT_SOLAR_INFUSER,null),
    BEGINNER(tx("solar_category.beginner"),point(180,260),Achievement.CRAFT_SOLAR_INFUSER,null),
    BEGINNER_ITEMS(tx("solar_category.beginner_items"),point(180,260),Achievement.CRAFT_SOLAR_INFUSER,BEGINNER),
    SKILLED(tx("solar_category.skilled"),point(180,260),Achievement.CRAFT_SOLAR_INFUSER,null),
    SKILLED_ITEMS(tx("solar_category.skilled_items"),point(180,260),Achievement.CRAFT_SOLAR_INFUSER,SKILLED),
    MAGIC_MATERIALS(tx("solar_category.materials"),point(20,260),Achievement.FIND_SOLAR_STONE,null);




    private final TranslationTextComponent translation;
    private final Point placeInBook;
    private final Achievement toUnlock;
    private final BookEntry child;

    BookEntry(TranslationTextComponent translation, Point placeInBook,@Nullable Achievement toUnlock,@Nullable BookEntry child){
        this.placeInBook = placeInBook;
        this.translation = translation;
        this.toUnlock = toUnlock;
        this.child = child;
    }


    public TranslationTextComponent getTranslation() {
        return translation;

    }

    public BookEntry getParent(){
        return child;
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
