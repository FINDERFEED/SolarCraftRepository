package com.finderfeed.solarcraft.events.my_events;

import com.finderfeed.solarcraft.client.custom_tooltips.CustomTooltip;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.Event;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;


/**
 * Workaround event for custom tooltips, works just like forge Color event.
 */
public class MyColorEvent extends Event {

    //origin:RenderTooltipEvent
    private final ItemStack itemStack;
    private final PoseStack poseStack;
    private int x;
    private int y;
    private Font font;
    private final List<ClientTooltipComponent> components;

    //origin:RenderTooltipEvent.Color
    private final int originalBackground;
    private final int originalBorderStart;
    private final int originalBorderEnd;
    private int backgroundStart;
    private int backgroundEnd;
    private int borderStart;
    private int borderEnd;


    private CustomTooltip tooltip;

    public MyColorEvent(@Nonnull ItemStack stack, PoseStack matrixStack, int x, int y, @Nonnull Font fr, @Nonnull List<ClientTooltipComponent> components, CustomTooltip tooltip) {
//        super(stack, matrixStack, x, y, fr, 0xf0100010, 0xDD5000FF, 0xDD28007f, components);

        this.itemStack = stack;
        this.poseStack = matrixStack;
        this.components = Collections.unmodifiableList(components);
        this.x = x;
        this.y = y;
        this.font = fr;
        this.originalBackground = 0xf0100010;
        this.originalBorderStart = 0xDD5000FF;
        this.originalBorderEnd = 0xDD28007f;

        this.backgroundStart = originalBackground;
        this.backgroundEnd = originalBackground;

        this.borderStart = originalBorderStart;
        this.borderEnd = originalBorderEnd;


        this.tooltip = tooltip;
    }

    public CustomTooltip getTooltip() {
        return tooltip;
    }


    public List<ClientTooltipComponent> getComponents() {
        return components;
    }

    public PoseStack getPoseStack() {
        return poseStack;
    }

    //methods origin: RenderTooltipEvent.Color
    public int getBackgroundStart()
    {
        return backgroundStart;
    }

    public int getBackgroundEnd()
    {
        return backgroundEnd;
    }

    public void setBackground(int background)
    {
        this.backgroundStart = background;
        this.backgroundEnd = background;
    }

    public void setBackgroundStart(int backgroundStart)
    {
        this.backgroundStart = backgroundStart;
    }

    public void setBackgroundEnd(int backgroundEnd)
    {
        this.backgroundEnd = backgroundEnd;
    }

    public int getBorderStart()
    {
        return borderStart;
    }

    public void setBorderStart(int borderStart)
    {
        this.borderStart = borderStart;
    }

    public int getBorderEnd()
    {
        return borderEnd;
    }

    public void setBorderEnd(int borderEnd)
    {
        this.borderEnd = borderEnd;
    }

    public int getOriginalBackgroundStart()
    {
        return originalBackground;
    }

    public int getOriginalBackgroundEnd()
    {
        return originalBackground;
    }

    public int getOriginalBorderStart()
    {
        return originalBorderStart;
    }

    public int getOriginalBorderEnd()
    {
        return originalBorderEnd;
    }
//    @Nonnull
//    @Override
//    public ItemStack getItemStack() {
//        throw new RuntimeException("MUDA MUDA MUDA MUDA MUDA");
//    }
}
