package com.finderfeed.solarcraft.local_library.screen_constructor;

@FunctionalInterface
public interface OnKeyPress<T extends BuildableScreen> {

    void onKeyPress(T screen,int key, int scancode, int modifiers);


    default void hackyPress(BuildableScreen screen,int key, int scancode, int modifiers){
        this.onKeyPress((T)screen,key,scancode,modifiers);
    }
}
