package com.finderfeed.solarcraft.local_library.screen_constructor;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;

@FunctionalInterface
public interface RenderableComponent<T extends Screen> {

    void render(T screen, GuiGraphics graphics, int mx, int my, float pticks);


    default void hackyRender(BuildableScreen screen,GuiGraphics graphics,int mx,int my, float pticks){
        this.render((T)screen,graphics,mx,my,pticks);
    }

}
