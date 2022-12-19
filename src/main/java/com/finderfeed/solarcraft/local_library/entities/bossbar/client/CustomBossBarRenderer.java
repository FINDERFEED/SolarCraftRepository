package com.finderfeed.solarcraft.local_library.entities.bossbar.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.Nullable;

public abstract class CustomBossBarRenderer {

    private int height;

    public CustomBossBarRenderer(int height){
        this.height = height;
    }

    public abstract void render(PoseStack matrices, int x, int y, Component name, float progress, @Nullable Entity entity);

    public int getHeight() {
        return height;
    }
}
