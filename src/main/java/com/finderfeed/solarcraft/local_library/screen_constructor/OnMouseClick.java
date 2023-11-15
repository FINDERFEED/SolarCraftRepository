package com.finderfeed.solarcraft.local_library.screen_constructor;

@FunctionalInterface
public interface OnMouseClick<T extends BuildableScreen> {

    void click(T screen,double mx, double my, int button);

    default void hackyClick(BuildableScreen screen,double mx, double my, int button){
        this.click((T)screen,mx,my,button);
    }

}

