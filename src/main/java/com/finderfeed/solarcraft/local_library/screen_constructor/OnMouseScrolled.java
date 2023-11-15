package com.finderfeed.solarcraft.local_library.screen_constructor;

public interface OnMouseScrolled<T extends BuildableScreen> {

    void scroll(T screen,double mx, double my, double delta);


    default void hackyScroll(BuildableScreen screen,double mx, double my, double delta){
        this.scroll((T)screen,mx,my,delta);
    }

}
