package com.finderfeed.solarcraft.local_library.screen_constructor;

@FunctionalInterface
public interface OnMouseDrag<T extends BuildableScreen> {

    void onMouseDrag(T screen,double xpos, double ypos, int button, double leftright, double updown);


    default void hackyDrag(BuildableScreen screen,double xpos, double ypos, int button, double leftright, double updown){
        this.onMouseDrag((T)screen,xpos,ypos,button,leftright,updown);
    }
}
