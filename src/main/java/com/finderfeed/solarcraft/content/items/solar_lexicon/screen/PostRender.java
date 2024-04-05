package com.finderfeed.solarcraft.content.items.solar_lexicon.screen;

import net.minecraft.client.gui.GuiGraphics;

@FunctionalInterface
interface PostRender{

    void doRender(GuiGraphics graphics, int mousex, int mousey, float partialTicks);

}
