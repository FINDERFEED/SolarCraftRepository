package com.finderfeed.solarcraft.content.items.solar_wand.wand_actions.drain_runic_enenrgy_action;

import com.finderfeed.solarcraft.SolarCraft;
import com.finderfeed.solarcraft.SolarcraftClientInit;
import com.finderfeed.solarcraft.helpers.ClientHelpers;
import com.finderfeed.solarcraft.local_library.client.screens.DefaultScreen;
import com.finderfeed.solarcraft.local_library.client.screens.RadialMenu;
import com.finderfeed.solarcraft.local_library.helpers.RenderingTools;
import com.finderfeed.solarcraft.misc_things.RunicEnergy;
import com.finderfeed.solarcraft.packet_handler.SolarCraftPacketHandler;
import com.finderfeed.solarcraft.packet_handler.packets.SetREDrainTypePacket;
import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class RETypeSelectionScreen extends DefaultScreen {

    public static final ResourceLocation ALL_ELEMENTS_ID_ORDERED = new ResourceLocation(SolarCraft.MOD_ID,"textures/misc/all_elements_id_ordered.png");
    private RadialMenu menu;


    @Override
    protected void init() {
        super.init();
        Minecraft minecraft = Minecraft.getInstance();
        RadialMenu.RadialMenuShaderSettings settings =
                new RadialMenu.RadialMenuShaderSettings(0,0.25f,0.1f,
                        new float[]{0.3f,0.3f,0.3f,0.9f},
                        new float[]{0.6f,0.6f,0.6f,0.9f},
                        0.05f
                );
        List<RadialMenu.RadialMenuSection> sections = new ArrayList<>();

         for (int i = 0; i < RunicEnergy.Type.getAll().length;i++){
            RunicEnergy.Type type = RunicEnergy.Type.getAll()[i];

            RadialMenu.RadialMenuSection section = new RadialMenu.RadialMenuSection(
                    ()->{
                        SolarCraftPacketHandler.INSTANCE.sendToServer(new SetREDrainTypePacket(type));
                    },
                    (matrices, x, y) -> {
                        ClientHelpers.bindText(ALL_ELEMENTS_ID_ORDERED);
                        RenderingTools.blitWithBlend(matrices,x-8,y-8,type.getIndex() * 16,
                                0,16,16,128,16,0,1f);
                    },
                    (matrices, x, y) -> {
                        renderTooltip(matrices, Component.literal(type.id.toUpperCase(Locale.ROOT)),(int)x,(int)y);
                    }
            );
            sections.add(section);
        }

        RadialMenu menu = new RadialMenu(settings,relX,relY,100,
                sections
        );
        this.menu = menu;
    }


    @Override
    public void render(PoseStack matrices, int mx, int my, float pTicks) {
        super.render(matrices, mx, my, pTicks);
        menu.render(matrices,mx,my,pTicks,0);
    }

    @Override
    public boolean mouseClicked(double x, double y, int p_94697_) {
        if (menu.getSectionUnderMouse((float)x,(float)y) != -1) {
            menu.mouseClicked((float) x, (float) y);
            Minecraft.getInstance().setScreen(null);
        }

        return super.mouseClicked(x, y, p_94697_);
    }

    @Override
    public boolean keyPressed(int keyCode, int idk, int type) {
        return super.keyPressed(keyCode, idk, type);
    }

    @Override
    public boolean keyReleased(int keyCode, int p_94716_, int p_94717_) {

        return super.keyReleased(keyCode, p_94716_, p_94717_);
    }


    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public int getScreenWidth() {
        return 0;
    }

    @Override
    public int getScreenHeight() {
        return 0;
    }
}
