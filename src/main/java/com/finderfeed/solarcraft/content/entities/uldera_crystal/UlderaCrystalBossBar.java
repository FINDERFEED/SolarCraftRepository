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
    public static final ResourceLocation LOCATION_TEXT = new ResourceLocation(SolarCraft.MOD_ID,"textures/bossbars/uldera_crystal_text.png");

    public UlderaCrystalBossBar() {
        super(37);
    }

    @Override
    public void render(PoseStack matrices, int x, int y, Component name, float progress, @Nullable Entity entity) {
        matrices.pushPose();
        ClientHelpers.bindText(LOCATION);
        float scale = 1.25f;
        float tw = 232 * scale;
        float th = 37 * scale;
        float hpw = 188 * scale;

        float xp = x - 232/2f * scale;
        float yoff = 16 * scale;
        float xoff = 22 * scale;

        RenderingTools.blitWithBlend(matrices,xp,y,0,0,tw,th-2 * scale,tw,th,0,1f);


        RenderingTools.blitWithBlend(matrices,xp + xoff,y + yoff,0,th - 2 * scale,hpw * progress,2 * scale,tw,th,1,1f);


        //265 26
        ClientHelpers.bindText(LOCATION_TEXT);

        float textScale = 0.5f;
        float txs = x - (265/2f - 7)*textScale;
        float txw = 265 * textScale;
        float txh = 26 * textScale;

        RenderingTools.blitWithBlend(matrices,txs,y + 35,0,0,txw,txh,txw,txh,0,1);

        matrices.popPose();
    }
}
