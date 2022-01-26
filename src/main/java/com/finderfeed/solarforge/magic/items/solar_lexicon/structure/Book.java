package com.finderfeed.solarforge.magic.items.solar_lexicon.structure;

import com.finderfeed.solarforge.magic.items.solar_lexicon.structure.category.Category;
import com.finderfeed.solarforge.magic.items.solar_lexicon.structure.category.CategoryBase;
import com.finderfeed.solarforge.magic.items.solar_lexicon.structure.subcategory.SubCategory;
import com.finderfeed.solarforge.magic.items.solar_lexicon.structure.subcategory.SubCategoryBase;
import com.finderfeed.solarforge.magic.items.solar_lexicon.unlockables.AncientFragment;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.Button;

import javax.annotation.Nullable;
import java.util.*;

public class Book {

    public static final int SPACING_BETWEEN_CATEGORIES = 20;

    private Map<CategoryBase, Category> categories = new HashMap<>();
    private List<List<Category>> ROWS_COLLUMNS = new ArrayList<>();
    private int[] yPositions;
    private int[][] xPositions;
    private int maxCategoryInARow = 0;
    private final int relX;
    private final int relY;


    public Book(int relX,int relY){
        this.relX = relX;
        this.relY = relY;
    }

    public void addCategory(CategoryBase base){
        this.categories.put(base,new Category(base));
    }

    @Nullable
    public Category getCategory(CategoryBase base){
        return this.categories.get(base);
    }

    public void init(){
        initRowsAndCollumns();
        calculateInitPositionsForEachCollumnAndRow();
        for (int i = 0; i < ROWS_COLLUMNS.size();i ++){
            List<Category> cies;
            for (int g = 0;g < (cies = ROWS_COLLUMNS.get(i)).size();g++){
                cies.get(g).initAtPos(relX+xPositions[i][g],relY+yPositions[i]);
            }
        }
    }

    public void tick(){
        categories.forEach((s,cat)->{
            cat.tick();
        });
    }

    public void render(PoseStack matrices){
        for (int i = 0; i < ROWS_COLLUMNS.size();i ++){
            List<Category> cies;
            for (int g = 0;g < (cies = ROWS_COLLUMNS.get(i)).size();g++){
                cies.get(g).renderAtPos(matrices,relX+xPositions[i][g],relY+yPositions[i]);
            }
        }
    }

    private void calculateInitPositionsForEachCollumnAndRow(){
        this.yPositions = new int[ROWS_COLLUMNS.size()];
        this.xPositions = new int[ROWS_COLLUMNS.size()][maxCategoryInARow];
        int currentYPos = 0;
        for (int i = 0;i < ROWS_COLLUMNS.size();i++){
            int currentXPos = 0;
            List<Category> cies;
            for (int g = 0;g < (cies = ROWS_COLLUMNS.get(i)).size();g++){
                xPositions[i][g] = currentXPos;
                currentXPos += cies.get(g).getSize()[0] + SPACING_BETWEEN_CATEGORIES;
            }
            yPositions[i] = currentYPos;
            currentYPos += getMaximumHeightInARow(i) + SPACING_BETWEEN_CATEGORIES;
        }
    }


    private int getMaximumHeightInARow(int rowID){
        int maximum = 0;
        for (Category cat : ROWS_COLLUMNS.get(rowID)){
            if (cat.getSize()[1] > maximum){
                maximum = cat.getSize()[1];
            }
        }
        return maximum;
    }


    private void initRowsAndCollumns(){
        List<Category> cies = new ArrayList<>(categories.values());
        cies.sort((c1,c2)->{
            return c1.getBase().getHeightPriority() - c2.getBase().getHeightPriority();
        });
        List<Category> filtered;
        while ((filtered = getNextRow(cies)) != null){
            int a;
            if ((a = filtered.size()) > maxCategoryInARow){
                maxCategoryInARow = a;
            }
            filtered.sort((c1,c2)->{
                return c1.getBase().getWidthPriority() - c2.getBase().getWidthPriority();
            });
            ROWS_COLLUMNS.add(filtered);
        }
    }

    public List<Button> getButtons(){
        List<Button> BUTTONS = new ArrayList<>();
        ROWS_COLLUMNS.forEach((list)->{
            list.forEach((cat)->{
                cat.getAllCategories().forEach((subcat)->{
                    BUTTONS.addAll(subcat.getButtonsToAdd());
                });
            });
        });
        return BUTTONS;
    }

    private List<Category> getNextRow(List<Category> a){
        List<Category> attacksToCopyTo = new ArrayList<>();
        if (a.size() != 0) {
            int intToSeek = a.get(0).getBase().getHeightPriority();
            for (Category h : a) {
                if (h.getBase().getHeightPriority() == intToSeek) {
                    attacksToCopyTo.add(h);
                }else{
                    break;
                }
            }
        }else{
            return null;
        }
        a.removeAll(attacksToCopyTo);
        return attacksToCopyTo;
    }


    public static void initializeBook(Book book,List<AncientFragment> fragments) {
        for (AncientFragment frag : fragments) {
            CategoryBase catBase = frag.getCategory();
            SubCategoryBase subBase = frag.getSubCategory();
            Category cat;
            SubCategory subCat;
            if ((cat = book.getCategory(catBase)) != null) {

                if ((subCat = cat.getSubCategory(subBase)) != null) {
                    subCat.putAncientFragment(frag);
                } else {
                    cat.addSubCategory(subBase);
                    subCat = cat.getSubCategory(subBase);
                    subCat.putAncientFragment(frag);

                }
            } else {
                book.addCategory(catBase);
                cat = book.getCategory(catBase);
                if ((subCat = cat.getSubCategory(subBase)) != null) {
                    subCat.putAncientFragment(frag);
                } else {
                    cat.addSubCategory(subBase);
                    subCat = cat.getSubCategory(subBase);
                    subCat.putAncientFragment(frag);

                }
            }
        }
    }

}

