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

    private Integer xsize;
    private Integer ysize;

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

    public CategoryBase getBase(){
        return this.base;
    }

    public void initAtPos(int x, int y){

    }

    public void renderAtPos(int x, int y){

    }

    public int[] getSize(){
        if (xsize == null){
            int x = 0;
            int y = 0;

            int p = categories.size()*20+10;
            int m = getMaxSubCategoryYSize();
            y = m+17;

            for (SubCategory cat : categories.values()){
                x+=cat.getSize()[0];
            }


            this.xsize = x;
            this.ysize = y;
            return new int[]{x,y};
        }else{
            return new int[]{xsize,ysize};
        }
    }


    private int getMaxSubCategoryYSize(){
        int max = 0;
        for (SubCategory cat : categories.values()){
            if (cat.getSize()[1] > max){
                max = cat.getSize()[1];
            }
        }
        return max;
    }
}
