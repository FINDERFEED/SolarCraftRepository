package com.finderfeed.solarforge.magic_items.items.solar_lexicon.structure.category;

import com.finderfeed.solarforge.magic_items.items.solar_lexicon.structure.subcategory.SubCategory;
import com.finderfeed.solarforge.magic_items.items.solar_lexicon.structure.subcategory.SubCategoryBase;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

import javax.annotation.Nullable;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Category {

    public static final int SPACING_BETWEEN_CATEGORIES = 20;

    private Map<SubCategoryBase,SubCategory> categories = new HashMap<>();
    private final CategoryBase base;

    private Integer xsize;
    private Integer ysize;

    public Category(CategoryBase base){
        this.base = base;
    }

    public void addSubCategory(SubCategoryBase c){
        this.categories.put(c,new SubCategory(c));
    }

    @Nullable
    public SubCategory getSubCategory(SubCategoryBase base){
        return this.categories.get(base);
    }

    public CategoryBase getBase(){
        return this.base;
    }

    public void initAtPos(int x, int y){
        int index = 0;
        for (SubCategory cat : categories.values()){
            int xPos = 5+index*(cat.getSize()[0] + SPACING_BETWEEN_CATEGORIES) ;
            cat.initAtPos(xPos,y);
            index++;
        }
    }

    public void renderAtPos(PoseStack matrices,int x, int y){
        for (SubCategory cat : categories.values()) {
            cat.renderAtPos(matrices,x+5,y+7);
        }
        SubCategory.drawRectangle(matrices,getSize()[0],getSize()[1],new Point(x,y));
        Gui.drawString(matrices, Minecraft.getInstance().font,base.getTranslation(),x,y-7,0xffffff);
    }

    public int[] getSize(){
        if (xsize == null){
            int x = 0;
            int y;

            int p = categories.size()*SPACING_BETWEEN_CATEGORIES+10;
            int m = getMaxSubCategoryYSize();
            y = m+7*3;

            for (SubCategory cat : categories.values()){
                x+=cat.getSize()[0];
            }
            x+=p;


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
