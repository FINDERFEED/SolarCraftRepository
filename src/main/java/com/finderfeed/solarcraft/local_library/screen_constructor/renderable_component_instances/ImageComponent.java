package com.finderfeed.solarcraft.local_library.screen_constructor.renderable_component_instances;

import com.finderfeed.solarcraft.helpers.ClientHelpers;
import com.finderfeed.solarcraft.local_library.helpers.RenderingTools;
import com.finderfeed.solarcraft.local_library.screen_constructor.BuildableScreen;
import com.finderfeed.solarcraft.local_library.screen_constructor.RenderableComponent;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;

public class ImageComponent<T extends BuildableScreen> implements RenderableComponent<T> {

    private int x;
    private int y;
    private int width;
    private int height;
    private int texWidth;
    private int texHeight;
    private ResourceLocation texture;

    /**
     * @param x - offset from relX
     * @param y - offset from relY
     */
    public ImageComponent(ResourceLocation texture,int x, int y,int width,int height,int texWidth,int texHeight){
        this.x = x;
        this.y = y;
        this.texture = texture;
        this.width = width;
        this.height = height;
        this.texWidth = texWidth;
        this.texHeight = texHeight;
    }

    @Override
    public void render(T screen, GuiGraphics graphics, int mx, int my, float pticks) {
        PoseStack matrices = graphics.pose();
        matrices.pushPose();
        ClientHelpers.bindText(texture);
        RenderingTools.blitWithBlend(matrices,screen.relX + x,screen.relY + y,0,0,width,height,texWidth,texHeight,0,1f);
        matrices.popPose();
    }
}
