package com.finderfeed.solarcraft.events.my_events;

import com.finderfeed.solarcraft.client.custom_tooltips.CustomTooltip;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.Event;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;


/**
 * This event is a workaround event that is triggered in RenderingTools#renderTooltipInternal after the
 * tooltip box was rendered. Should be used to render custom tooltip borders.
 */
public class PostColorEvent extends Event {

    private final ItemStack itemStack;
    private final PoseStack poseStack;
    private int x;
    private int y;
    private Font font;
    private final List<ClientTooltipComponent> components;





    private int sizeX;
    private int sizeY;
    private CustomTooltip tooltip;

    public PostColorEvent(PoseStack matrixStack, int x, int y, @Nonnull Font fr, @Nonnull List<ClientTooltipComponent> components, int sizeX, int sizeY, CustomTooltip tooltip) {
        this.itemStack = ItemStack.EMPTY;
        this.poseStack = matrixStack;
        this.components = Collections.unmodifiableList(components);
        this.x = x;
        this.y = y;
        this.font = fr;


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


    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public PoseStack getPoseStack() {
        return poseStack;
    }
}
