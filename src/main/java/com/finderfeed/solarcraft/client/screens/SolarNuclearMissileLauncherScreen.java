package com.finderfeed.solarcraft.client.screens;

import com.finderfeed.solarcraft.client.screens.components.IntegerEditBox;
import com.finderfeed.solarcraft.content.blocks.solar_forge_block.solar_forge_screen.SolarForgeButtonYellow;
import com.finderfeed.solarcraft.local_library.client.screens.DefaultScreen;
import com.finderfeed.solarcraft.packet_handler.SCPacketHandler;
import com.finderfeed.solarcraft.packet_handler.packets.LaunchNuclearMissilePacket;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;

public class SolarNuclearMissileLauncherScreen extends DefaultScreen {

    private BlockPos tilePos;

    public SolarNuclearMissileLauncherScreen(BlockPos tilePos){
        this.tilePos = tilePos;
    }

    @Override
    protected void init() {
        super.init();
        IntegerEditBox xbox = new IntegerEditBox(font,relX,relY,30,20, Component.literal("X"));
        IntegerEditBox zbox = new IntegerEditBox(font,relX,relY + 25,30,20, Component.literal("Z"));
        IntegerEditBox radbox = new IntegerEditBox(font,relX,relY + 40,30,20, Component.literal("Rad"));
        IntegerEditBox depthbox = new IntegerEditBox(font,relX,relY + 25,30,20, Component.literal("Depth"));
        xbox.setValue(tilePos.getX() + "");
        xbox.setValue(tilePos.getZ() + "");
        radbox.setValue("1");
        depthbox.setValue("1");
        this.addRenderableWidget(xbox);
        this.addRenderableWidget(zbox);
        this.addRenderableWidget(radbox);
        this.addRenderableWidget(depthbox);
        SolarForgeButtonYellow launchButton = new SolarForgeButtonYellow(relX + 200,relY,40,20,
                Component.literal("Launch"),(btn)->{

            int x = Integer.parseInt(xbox.getValue());

            SCPacketHandler.INSTANCE.sendToServer(new LaunchNuclearMissilePacket(
                    tilePos,
            ));
        });

    }


    @Override
    public void render(PoseStack matrices, int mx, int my, float pTicks) {

        super.render(matrices, mx, my, pTicks);
    }

    @Override
    public int getScreenWidth() {
        return 300;
    }

    @Override
    public int getScreenHeight() {
        return 300;
    }
}
