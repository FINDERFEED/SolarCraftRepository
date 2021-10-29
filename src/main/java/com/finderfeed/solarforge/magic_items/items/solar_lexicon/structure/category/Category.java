package com.finderfeed.solarforge.magic_items.items.solar_lexicon.structure.category;

import com.finderfeed.solarforge.magic_items.items.solar_lexicon.structure.subcategory.SubCategory;
import com.finderfeed.solarforge.magic_items.items.solar_lexicon.structure.subcategory.SubCategoryBase;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Category {

    private Map<SubCategoryBase,SubCategory> categories = new HashMap<>();
    private final CategoryBase base;

    int xsize;
    int ysize;

    public Category(CategoryBase base){
        this.base = base;
    }

    public void addSubCategory(SubCategory c){
        this.categories.put(c.getBase(),c);
    }

    @Nullable
    public SubCategory getSubCategory(SubCategoryBase base){
        return this.categories.get(base);
    }

    public void initAtPos(int x, int y){

    }

    public void renderAtPos(int x, int y){

    }

    public int[] getSize(){
        return null;
    }
}
