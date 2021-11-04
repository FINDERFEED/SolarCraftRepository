package com.finderfeed.solarforge.events.my_events;

import com.finderfeed.solarforge.client.custom_tooltips.CustomTooltip;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.event.RenderTooltipEvent;

import javax.annotation.Nonnull;
import java.util.List;


/**
 * This event is a workaround event that is triggered in RenderingTools#renderTooltipInternal after the
 * tooltip box was rendered. Should be used to render custom tooltip borders.
 */
public class PostColorEvent extends RenderTooltipEvent.Color {

    private int sizeX;
    private int sizeY;
    private CustomTooltip tooltip;

    public PostColorEvent(PoseStack matrixStack, int x, int y, @Nonnull Font fr, @Nonnull List<ClientTooltipComponent> components, int sizeX, int sizeY, CustomTooltip tooltip) {
        super(ItemStack.EMPTY, matrixStack, x, y, fr, 0, 0, 0, components);
        this.sizeX = sizeX;
        this.tooltip = tooltip;
        this.sizeY = sizeY;
    }

    public CustomTooltip getTooltip() {
        return tooltip;
    }

    public int getSizeX() {
        return sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }

    public int getBackgroundStart()
    {
        throw new RuntimeException("Useless here");
    }

    public int getBackgroundEnd()
    {
        throw new RuntimeException("Useless here");
    }

    public void setBackground(int background)
    {
        throw new RuntimeException("Useless here");
    }

    public void setBackgroundStart(int backgroundStart)
    {
        throw new RuntimeException("Useless here");
    }

    public void setBackgroundEnd(int backgroundEnd)
    {
        throw new RuntimeException("Useless here");
    }

    public int getBorderStart()
    {
        throw new RuntimeException("Useless here");
    }

    public void setBorderStart(int borderStart)
    {
        throw new RuntimeException("Useless here");
    }

    public int getBorderEnd()
    {
        throw new RuntimeException("Useless here");
    }

    public void setBorderEnd(int borderEnd)
    {
        throw new RuntimeException("Useless here");
    }



    public int getOriginalBackgroundStart()
    {
        throw new RuntimeException("Useless here");
    }

    public int getOriginalBackgroundEnd()
    {
        throw new RuntimeException("Useless here");
    }

    public int getOriginalBorderStart()
    {
        throw new RuntimeException("Useless here");
    }

    public int getOriginalBorderEnd()
    {
        throw new RuntimeException("Useless here");
    }

}
