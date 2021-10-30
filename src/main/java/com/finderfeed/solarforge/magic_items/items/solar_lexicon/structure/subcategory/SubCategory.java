package com.finderfeed.solarforge.magic_items.items.solar_lexicon.structure.subcategory;

import com.finderfeed.solarforge.magic_items.items.solar_lexicon.unlockables.AncientFragment;

import java.util.ArrayList;
import java.util.List;

public class SubCategory {

    public static final int BUTTONS_SIZE = 22;

    private List<AncientFragment> fragments = new ArrayList<>();

    private final SubCategoryBase base;

    private Integer sizeX;
    private Integer sizeY;

    public SubCategory(SubCategoryBase base){
        this.base = base;
    }

    public SubCategoryBase getBase() {
        return base;
    }



    public void initAtPos(int x, int y){

    }

    public void renderAtPos(int x, int y){

    }

    public void putAncientFragment(AncientFragment frag){
        fragments.add(frag);
    }


    public int[] getSize(){
        if (sizeX == null) {
            int x;
            int y;

            if (fragments.size() >= 6) {
                x = 6 * BUTTONS_SIZE;
            } else {
                x = fragments.size() * BUTTONS_SIZE;
            }

            if (fragments.size() >= 6) {
                y = (fragments.size() % 6) * BUTTONS_SIZE;
            } else {
                y = BUTTONS_SIZE;
            }
            this.sizeX = x;
            this.sizeY = y;
            return new int[]{x,y};
        }else{
            return new int[]{sizeX,sizeY};
        }
    }

}
