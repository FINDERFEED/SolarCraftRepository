package com.finderfeed.solarcraft.local_library.screen_constructor;

public record RenderableComponentPair<T extends BuildableScreen>(RenderableComponent<T> before,RenderableComponent<T> after) {


}
