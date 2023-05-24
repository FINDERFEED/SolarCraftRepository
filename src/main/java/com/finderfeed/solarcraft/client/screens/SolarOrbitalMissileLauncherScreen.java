package com.finderfeed.solarcraft.client.screens;

import com.finderfeed.solarcraft.client.screens.components.IntegerEditBox;
import com.finderfeed.solarcraft.content.blocks.blockentities.SolarOrbitalMissileLauncherTileEntity;
import com.finderfeed.solarcraft.content.blocks.solar_forge_block.solar_forge_screen.SolarForgeButtonYellow;
import com.finderfeed.solarcraft.helpers.ClientHelpers;
import com.finderfeed.solarcraft.local_library.client.screens.DefaultScreen;
import com.finderfeed.solarcraft.packet_handler.SCPacketHandler;
import com.finderfeed.solarcraft.packet_handler.packets.LaunchOrbitalMissilePacket;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;

public class SolarOrbitalMissileLauncherScreen extends DefaultScreen {

    private BlockPos tilePos;

    public SolarOrbitalMissileLauncherScreen(BlockPos tilePos){
        this.tilePos = tilePos;
    }

    @Override
    protected void init() {
        super.init();

        int y = 100;
        IntegerEditBox xbox = new IntegerEditBox(font,relX,relY + y + 20,30,20, Component.literal("X"));
        IntegerEditBox zbox = new IntegerEditBox(font,relX,relY + y + 40,30,20, Component.literal("Z"));
        IntegerEditBox radbox = new IntegerEditBox(font,relX,relY + y + 60,30,20, Component.literal("Rad"));
        IntegerEditBox depthbox = new IntegerEditBox(font,relX,relY + y + 80,30,20, Component.literal("Depth"));
        xbox.setValue(tilePos.getX() + "");
        zbox.setValue(tilePos.getZ() + "");
        radbox.setValue("1");
        depthbox.setValue("1");
        this.addRenderableWidget(xbox);
        this.addRenderableWidget(zbox);
        this.addRenderableWidget(radbox);
        this.addRenderableWidget(depthbox);
        SolarForgeButtonYellow launchButton = new SolarForgeButtonYellow(relX + 200,relY + y,40,20,
                Component.literal("Launch"),(btn)->{
            int x = Integer.parseInt(xbox.getValue());
            int z = Integer.parseInt(zbox.getValue());
            int radius = Integer.parseInt(radbox.getValue());
            int depth = Integer.parseInt(depthbox.getValue());
            SCPacketHandler.INSTANCE.sendToServer(new LaunchOrbitalMissilePacket(
                    tilePos,x,z,radius,depth
            ));
        });
        this.addRenderableWidget(launchButton);
    }


    @Override
    public void render(PoseStack matrices, int mx, int my, float pTicks) {
        this.renderBackground(matrices);
        if (ClientHelpers.getLevel().getBlockEntity(tilePos) instanceof SolarOrbitalMissileLauncherTileEntity tile){
            drawCenteredString(matrices,font,"Launch time: " + tile.getLaunchTicker(),relX + 100,relY + 50,0xffffffff);

        }
        super.render(matrices, mx, my, pTicks);
    }

    @Override
    public void tick() {
        super.tick();
        if (!(ClientHelpers.getLevel().getBlockEntity(tilePos) instanceof SolarOrbitalMissileLauncherTileEntity)) {
            Minecraft.getInstance().setScreen(null);
        }
    }

    @Override
    public int getScreenWidth() {
        return 300;
    }

    @Override
    public int getScreenHeight() {
        return 300;
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
