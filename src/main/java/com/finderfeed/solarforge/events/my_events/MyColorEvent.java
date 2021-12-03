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
 * Workaround event for custom tooltips, works just like color.
 */
public class MyColorEvent extends RenderTooltipEvent.Color {

    private CustomTooltip tooltip;

    public MyColorEvent(@Nonnull ItemStack stack, PoseStack matrixStack, int x, int y, @Nonnull Font fr, @Nonnull List<ClientTooltipComponent> components, CustomTooltip tooltip) {
        super(stack, matrixStack, x, y, fr, 0xf0100010, 0xDD5000FF, 0xDD28007f, components);
        this.tooltip = tooltip;
    }

    public CustomTooltip getTooltip() {
        return tooltip;
    }

    @Nonnull
    @Override
    public ItemStack getItemStack() {
        throw new RuntimeException("MUDA MUDA MUDA MUDA MUDA");
    }
}
