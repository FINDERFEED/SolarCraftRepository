package com.finderfeed.solarcraft.content.entities;

import com.finderfeed.solarcraft.SolarCraft;
import com.finderfeed.solarcraft.helpers.ClientHelpers;
import com.finderfeed.solarcraft.local_library.entities.bossbar.client.CustomBossBarRenderer;
import com.finderfeed.solarcraft.local_library.helpers.RenderingTools;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.Nullable;

public class CrystalBossBar extends CustomBossBarRenderer {

    public static final ResourceLocation LOCATION = new ResourceLocation(SolarCraft.MOD_ID,"textures/bossbars/defense_crystal.png");
    public static final ResourceLocation LOCATION1 = new ResourceLocation(SolarCraft.MOD_ID,"textures/bossbars/defense_crystal_text.png");

    public CrystalBossBar() {
        super(40);
    }

    @Override
    public void render(PoseStack matrices, int x, int y, Component name, float progress, @Nullable Entity entity) {
        matrices.pushPose();
        ClientHelpers.bindText(LOCATION);
        RenderingTools.blitWithBlend(matrices,x - 307/2f,y,0,0,307,32,307,36,0,1f);
        RenderingTools.blitWithBlend(matrices,x - 307/2f + 5,y + 14,0,32,297*progress,4,307,36,100f,1f);


        matrices.pushPose();
        ClientHelpers.bindText(LOCATION1);
        float w = Minecraft.getInstance().getWindow().getGuiScaledWidth();

        matrices.translate(w/2f - 307/2f + 99,y + 23,0);
        matrices.scale(0.35f,0.35f,0.35f);
        RenderingTools.blitWithBlend(matrices,0,0 ,0,0,322,26,322,26,0,1f);
        matrices.popPose();
        matrices.popPose();

    }
}
