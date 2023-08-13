package com.finderfeed.solarcraft.content.entities.uldera_crystal;

import com.finderfeed.solarcraft.SolarCraft;
import com.finderfeed.solarcraft.helpers.ClientHelpers;
import com.finderfeed.solarcraft.local_library.entities.bossbar.client.CustomBossBarRenderer;
import com.finderfeed.solarcraft.local_library.helpers.RenderingTools;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.Nullable;

public class UlderaCrystalBossBar extends CustomBossBarRenderer {

    public static final ResourceLocation LOCATION = new ResourceLocation(SolarCraft.MOD_ID,"textures/bossbars/uldera_crystal.png");

    public UlderaCrystalBossBar() {
        super(37);
    }

    @Override
    public void render(PoseStack matrices, int x, int y, Component name, float progress, @Nullable Entity entity) {
        matrices.pushPose();
        ClientHelpers.bindText(LOCATION);
        RenderingTools.blitWithBlend(matrices,x - 172 / 2f,y,0,0,172,31,172,37,0,1f);


        RenderingTools.blitWithBlend(matrices,x - 172 / 2f + 12,y + 15,0,31,148 * progress,6,172,37,0,1f);

        matrices.popPose();
    }
}
