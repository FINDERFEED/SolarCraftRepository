package com.finderfeed.solarcraft.client.screens;

import com.finderfeed.solarcraft.SolarCraft;
import com.finderfeed.solarcraft.client.screens.components.IntegerEditBox;
import com.finderfeed.solarcraft.content.blocks.blockentities.SolarOrbitalMissileLauncherTileEntity;
import com.finderfeed.solarcraft.content.blocks.solar_forge_block.solar_forge_screen.SolarForgeButtonYellow;
import com.finderfeed.solarcraft.content.items.solar_lexicon.screen.SolarLexiconScreen;
import com.finderfeed.solarcraft.content.items.solar_lexicon.screen.buttons.InfoButton;
import com.finderfeed.solarcraft.helpers.ClientHelpers;
import com.finderfeed.solarcraft.helpers.Helpers;
import com.finderfeed.solarcraft.local_library.client.screens.DefaultScreen;
import com.finderfeed.solarcraft.local_library.helpers.RenderingTools;
import com.finderfeed.solarcraft.packet_handler.SCPacketHandler;
import com.finderfeed.solarcraft.packet_handler.packets.LaunchOrbitalMissilePacket;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.border.WorldBorder;

public class SolarOrbitalMissileLauncherScreen extends DefaultScreen {

    public static final ResourceLocation LOCATION = new ResourceLocation(SolarCraft.MOD_ID,"textures/gui/orbital_explosion_launcher.png");
    private BlockPos tilePos;

    public SolarOrbitalMissileLauncherScreen(BlockPos tilePos){
        this.tilePos = tilePos;
    }

    @Override
    protected void init() {
        super.init();
        relX-=40;

        IntegerEditBox xbox = new IntegerEditBox(font,relX + 8,relY + 51,209,18, Component.literal("X"));
        IntegerEditBox zbox = new IntegerEditBox(font,relX + 8,relY  + 51 + 18 + 20,209,18, Component.literal("Z"));
        IntegerEditBox radbox = new IntegerEditBox(font,relX + 8,relY  + 51 + 18*2 + 40,209,18, Component.literal("Rad"));
        IntegerEditBox depthbox = new IntegerEditBox(font,relX + 8,relY  + 51 + 18*3 + 60,209,18, Component.literal("Depth"));

        xbox.setValue(tilePos.getX() + "");
        zbox.setValue(tilePos.getZ() + "");
        radbox.setValue("1");
        depthbox.setValue("1");
        if (ClientHelpers.getLevel().getBlockEntity(tilePos) instanceof SolarOrbitalMissileLauncherTileEntity tile){
            if (tile.getMissileData() != null) {
                SolarOrbitalMissileLauncherTileEntity.MissileData data = tile.getMissileData();
                xbox.setValue(""+data.xDest());
                zbox.setValue(""+data.zDest());
                radbox.setValue(""+data.radius());
                depthbox.setValue(""+data.depth());
            }
        }
        this.addRenderableWidget(xbox);
        this.addRenderableWidget(zbox);
        this.addRenderableWidget(radbox);
        this.addRenderableWidget(depthbox);
        SolarForgeButtonYellow launchButton = new SolarForgeButtonYellow(relX + this.getScreenWidth() + 28,relY + 20,45,15,
                Component.translatable("solarcraft.screens.orbital_missile_launch.launch"),(btn)->{
            int x = Integer.parseInt(xbox.getValue());
            int z = Integer.parseInt(zbox.getValue());
            int radius = Integer.parseInt(radbox.getValue());
            int depth = Integer.parseInt(depthbox.getValue());
            SCPacketHandler.INSTANCE.sendToServer(new LaunchOrbitalMissilePacket(
                    tilePos,x,z,radius,depth,false
            ));
        });
        SolarForgeButtonYellow cancelButton = new SolarForgeButtonYellow(relX + this.getScreenWidth() + 28,relY + 50,45,15,
                Component.translatable("solarcraft.screens.orbital_missile_launch.cancel"),(btn)->{
            SCPacketHandler.INSTANCE.sendToServer(new LaunchOrbitalMissilePacket(
                    tilePos,0,0,0,0,true
            ));
        });
        InfoButton info = new InfoButton(relX - 15,relY +  this.getScreenHeight() / 2 - 6,13,13,(btn,m,mx,my)->{
            String s = Component.translatable("solarcraft.screens.orbital_missile_launch.info",
                    ""+Level.MAX_LEVEL_SIZE,""+SolarOrbitalMissileLauncherTileEntity.MAX_RADIUS,
                    ""+SolarOrbitalMissileLauncherTileEntity.MAX_DEPTH,""+SolarOrbitalMissileLauncherTileEntity.RE_PER_ONE_RING).getString();
            renderTooltip(m,font.split(Component.literal(s),200),mx,my);
        });
        this.addRenderableWidget(info);
        this.addRenderableWidget(cancelButton);
        this.addRenderableWidget(launchButton);
    }


    @Override
    public void render(PoseStack matrices, int mx, int my, float pTicks) {
        this.renderBackground(matrices);
        ClientHelpers.bindText(LOCATION);
        this.blit(matrices,relX,relY,0,0,this.getScreenWidth(),this.getScreenHeight(),256,256);
        if (ClientHelpers.getLevel().getBlockEntity(tilePos) instanceof SolarOrbitalMissileLauncherTileEntity tile){
            int tick = tile.getLaunchTicker();
            String s = Helpers.generateMinutesAndSecondsStringFromTicks(tick);
            String s1 = Component.translatable("solarcraft.screens.orbital_missile_launch.launch_time").getString();
            drawCenteredString(matrices,font,s1 + ": " + s,relX + this.getScreenWidth()/2,relY + 12, SolarLexiconScreen.TEXT_COLOR);

            drawCenteredString(matrices,font,Component.translatable("solarcraft.word.x_coordinate"),relX + this.getScreenWidth()/2,relY + 37, SolarLexiconScreen.TEXT_COLOR);
            drawCenteredString(matrices,font,Component.translatable("solarcraft.word.z_coordinate"),relX + this.getScreenWidth()/2,relY + 57 + 18, SolarLexiconScreen.TEXT_COLOR);
            drawCenteredString(matrices,font,Component.translatable("solarcraft.word.radius"),relX + this.getScreenWidth()/2,relY + 77 + 18*2, SolarLexiconScreen.TEXT_COLOR);
            drawCenteredString(matrices,font,Component.translatable("solarcraft.word.depth"),relX + this.getScreenWidth()/2,relY + 97 + 18*3, SolarLexiconScreen.TEXT_COLOR);


        }
        RenderingTools.renderTextField(matrices,relX + this.getScreenWidth() + 20, relY + 13,60,60);
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
        return 225;
    }

    @Override
    public int getScreenHeight() {
        return 228;
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
