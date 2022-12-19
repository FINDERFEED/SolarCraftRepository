package com.finderfeed.solarcraft.content.entities.runic_elemental;

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

public class RunicElementalBossBar extends CustomBossBarRenderer {

    public static final ResourceLocation LOCATION = new ResourceLocation(SolarCraft.MOD_ID,"textures/bossbars/runic_elemental.png");
    public static final ResourceLocation LOCATION1 = new ResourceLocation(SolarCraft.MOD_ID,"textures/bossbars/re_elemental_text.png");

    public RunicElementalBossBar() {
        super(40);
    }

    @Override
    public void render(PoseStack matrices, int x, int y, Component name, float progress,@Nullable Entity entity) {
        matrices.pushPose();
        ClientHelpers.bindText(LOCATION);
        RenderingTools.blitWithBlend(matrices,x - 249/2f,y,0,0,249,22,249,26,0,1f);
        RenderingTools.blitWithBlend(matrices,x - 249/2f + 8,y + 10,0,22,233*progress,4,249,26,100f,1f);


        matrices.pushPose();
        ClientHelpers.bindText(LOCATION1);
        float w = Minecraft.getInstance().getWindow().getGuiScaledWidth();

        matrices.translate(w/2f - 244/2f + 75,y + 18,0);
        matrices.scale(0.35f,0.35f,0.35f);
        RenderingTools.blitWithBlend(matrices,0,0 ,0,0,300,26,300,26,0,1f);
        matrices.popPose();
        matrices.popPose();

    }
}
