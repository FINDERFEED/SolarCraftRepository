package com.finderfeed.solarcraft.local_library.screen_constructor;

public record RenderableComponentInstance<U extends BuildableScreen>(int renderIndex, RenderableComponent<U> component) {
}
