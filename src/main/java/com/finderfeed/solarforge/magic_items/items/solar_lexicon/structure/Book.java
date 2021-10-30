package com.finderfeed.solarforge.magic_items.items.solar_lexicon.structure;

import com.finderfeed.solarforge.for_future_library.entities.BossAttackChain;
import com.finderfeed.solarforge.magic_items.items.solar_lexicon.structure.category.Category;
import com.finderfeed.solarforge.magic_items.items.solar_lexicon.structure.category.CategoryBase;
import com.mojang.blaze3d.vertex.PoseStack;
import org.lwjgl.system.CallbackI;

import javax.annotation.Nullable;
import java.util.*;

public class Book {

    public static final int SPACING_BETWEEN_CATEGORIES = 20;

    private Map<CategoryBase, Category> categories = new HashMap<>();
    private List<List<Category>> ROWS_COLLUMNS = new ArrayList<>();
    private int[] yPositions;
    private int[][] xPositions;
    private int maxCategoryInARow = 0;

    public Book(){

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


    }

    public void render(PoseStack matrices){

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
            currentYPos += getMaximumHeightInARow();
        }
    }

    //TODO:getMaximumHeightInARow
    private void getMaximumHeightInARow(){

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
            ROWS_COLLUMNS.add(new ArrayList<>(filtered));
        }
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

}
class IntegeredPair<T>{

    private T item;
    private Integer number = null;

    public IntegeredPair(T item){
        this.item = item;
    }

    public T getItem() {
        return item;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getNumber() {
        return number;
    }
}
