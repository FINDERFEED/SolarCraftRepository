package com.finderfeed.solarcraft.local_library.screen_constructor;

public interface OnMouseScrolled<T extends BuildableScreen> {

    void scroll(T screen,double mx, double my, double deltaX,double deltaY);


    default void hackyScroll(BuildableScreen screen,double mx, double my, double deltaX,double deltaY){
        this.scroll((T)screen,mx,my,deltaX,deltaY);
    }

}
