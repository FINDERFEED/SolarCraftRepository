package com.finderfeed.solarcraft.content.items.solar_wand;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public record WandUseContext(Level level, Player player, ItemStack item, @Nullable UseOnContext context, @Nullable Integer usageTime) {

    @Override
    public String toString() {
        return "WandUseContext{" +
                "level=" + level +
                ", player=" + player +
                ", item=" + item +
                ", context=" + context +
                ", usageTime=" + usageTime +
                '}';
    }
}
