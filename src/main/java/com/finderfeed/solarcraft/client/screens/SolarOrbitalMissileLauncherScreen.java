package com.finderfeed.solarcraft.client.screens;

import com.finderfeed.solarcraft.SolarCraft;
import com.finderfeed.solarcraft.client.screens.components.IntegerEditBox;
import com.finderfeed.solarcraft.content.blocks.blockentities.SolarOrbitalMissileLauncherTileEntity;
import com.finderfeed.solarcraft.content.blocks.solar_forge_block.solar_forge_screen.SolarCraftButton;
import com.finderfeed.solarcraft.content.items.solar_lexicon.screen.SolarLexiconScreen;
import com.finderfeed.solarcraft.content.items.solar_lexicon.screen.buttons.InfoButton;
import com.finderfeed.solarcraft.helpers.ClientHelpers;
import com.finderfeed.solarcraft.helpers.Helpers;
import com.finderfeed.solarcraft.local_library.client.screens.DefaultScreen;
import com.finderfeed.solarcraft.local_library.helpers.FDMathHelper;
import com.finderfeed.solarcraft.local_library.helpers.RenderingTools;
import com.finderfeed.solarcraft.misc_things.RunicEnergy;
import com.finderfeed.solarcraft.packet_handler.SCPacketHandler;
import com.finderfeed.solarcraft.packet_handler.packets.LaunchOrbitalMissilePacket;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.Optional;

public class SolarOrbitalMissileLauncherScreen extends DefaultScreen {

    public static final ResourceLocation LOCATION = new ResourceLocation(SolarCraft.MOD_ID,"textures/gui/orbital_explosion_launcher.png");
    private BlockPos tilePos;

    private EditBox radBox;
    private EditBox depthBox;
    public SolarOrbitalMissileLauncherScreen(BlockPos tilePos){
        this.tilePos = tilePos;
    }

    @Override
    protected void init() {
        super.init();
        relX-=40;

        IntegerEditBox xbox = new IntegerEditBox(font,relX + 8,relY + 26,209,18, Component.literal("X"));
        IntegerEditBox zbox = new IntegerEditBox(font,relX + 8,relY  + 26 + 18 + 20,209,18, Component.literal("Z"));
        IntegerEditBox radbox = new IntegerEditBox(font,relX + 8,relY  + 26 + 18*2 + 40,209,18, Component.literal("Rad"));
        IntegerEditBox depthbox = new IntegerEditBox(font,relX + 8,relY  + 26 + 18*3 + 60,209,18, Component.literal("Depth"));
        this.radBox = radbox;
        this.depthBox = depthbox;
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
        SolarCraftButton launchButton = new SolarCraftButton(relX + this.getScreenWidth() + 28,relY + 20,45,15,
                Component.translatable("solarcraft.screens.orbital_missile_launch.launch"),(btn)->{
            int x = Integer.parseInt(xbox.getValue());
            int z = Integer.parseInt(zbox.getValue());
            int radius = Integer.parseInt(radbox.getValue());
            int depth = Integer.parseInt(depthbox.getValue());
            SCPacketHandler.INSTANCE.sendToServer(new LaunchOrbitalMissilePacket(
                    tilePos,x,z,radius,depth,false
            ));
        });
        SolarCraftButton cancelButton = new SolarCraftButton(relX + this.getScreenWidth() + 28,relY + 50,45,15,
                Component.translatable("solarcraft.screens.orbital_missile_launch.cancel"),(btn)->{
            SCPacketHandler.INSTANCE.sendToServer(new LaunchOrbitalMissilePacket(
                    tilePos,0,0,0,0,true
            ));
        });
        InfoButton info = new InfoButton(relX - 15,relY +  this.getScreenHeight() / 2 - 6,13,13,(btn,graphics,mx,my)->{
            String s = Component.translatable("solarcraft.screens.orbital_missile_launch.info",
                    ""+Level.MAX_LEVEL_SIZE,""+SolarOrbitalMissileLauncherTileEntity.MAX_RADIUS,
                    ""+SolarOrbitalMissileLauncherTileEntity.MAX_DEPTH,""+SolarOrbitalMissileLauncherTileEntity.RE_PER_ONE_RING).getString();
            graphics.renderTooltip(font,font.split(Component.literal(s),200),mx,my);
        });
        this.addRenderableWidget(info);
        this.addRenderableWidget(cancelButton);
        this.addRenderableWidget(launchButton);
    }


    @Override
    public void render(GuiGraphics graphics, int mx, int my, float pTicks) {

        PoseStack matrices = graphics.pose();

        this.renderBackground(graphics);
        ClientHelpers.bindText(LOCATION);
        RenderingTools.blitWithBlend(matrices,relX,relY,0,0,this.getScreenWidth(),this.getScreenHeight(),256,256,0,1f);
        if (ClientHelpers.getLevel().getBlockEntity(tilePos) instanceof SolarOrbitalMissileLauncherTileEntity tile){
            int tick = tile.getLaunchTicker();
            String s = Helpers.generateMinutesAndSecondsStringFromTicks(tick);
            String s1 = Component.translatable("solarcraft.screens.orbital_missile_launch.launch_time").getString();
//            drawCenteredString(matrices,font,s1 + ": " + s,relX + this.getScreenWidth()/2,relY + 181, SolarLexiconScreen.TEXT_COLOR);
            graphics.drawString(font,s1 + ":",relX +  10,relY + 181,SolarLexiconScreen.TEXT_COLOR);
            graphics.drawString(font,s,relX +  98,relY + 181,SolarLexiconScreen.TEXT_COLOR);

            graphics.drawCenteredString(font,Component.translatable("solarcraft.word.x_coordinate"),relX + this.getScreenWidth()/2,relY + 12, SolarLexiconScreen.TEXT_COLOR);
            graphics.drawCenteredString(font,Component.translatable("solarcraft.word.z_coordinate"),relX + this.getScreenWidth()/2,relY + 32 + 18, SolarLexiconScreen.TEXT_COLOR);
            graphics.drawCenteredString(font,Component.translatable("solarcraft.word.radius"),relX + this.getScreenWidth()/2,relY + 52 + 18*2, SolarLexiconScreen.TEXT_COLOR);
            graphics.drawCenteredString(font,Component.translatable("solarcraft.word.depth"),relX + this.getScreenWidth()/2,relY + 72 + 18*3, SolarLexiconScreen.TEXT_COLOR);


            float needed = (this.getNumberFromEditBox(radBox) + this.getNumberFromEditBox(depthBox))*SolarOrbitalMissileLauncherTileEntity.RE_PER_ONE_RING;
            float pNeeded = (float)FDMathHelper.clamp(0,needed / (float)SolarOrbitalMissileLauncherTileEntity.RE_LIMIT,1);

            float pCurrent = (float) FDMathHelper.clamp(0,tile.getRunicEnergy(RunicEnergy.Type.KELDA) / (float) SolarOrbitalMissileLauncherTileEntity.RE_LIMIT,1);
            RenderingTools.fill(matrices,relX + 39 ,relY + 200,relX + 39 + pNeeded*174,relY + 200 + 16,1,1,0,0.5f);
            RenderingTools.fill(matrices,relX + 39 ,relY + 200,relX + 39 + pCurrent*174,relY + 200 + 16,1,1,0,1f);
            if (RenderingTools.isMouseInBorders(mx,my,relX + 39,relY + 200,relX + 39 + 174,relY + 200 + 16)){
                graphics.renderTooltip(font,List.of(
                        Component.literal(Component.translatable("solarcraft.word.needed").getString() + ": " + needed).withStyle(ChatFormatting.GOLD),
                        Component.literal(Component.translatable("solarcraft.word.current").getString() + ": " + tile.getRunicEnergy(RunicEnergy.Type.KELDA)).withStyle(ChatFormatting.GOLD)
                ), Optional.empty(),mx,my);
            }
        }
        RenderingTools.renderTextField(matrices,relX + this.getScreenWidth() + 20, relY + 13,60,60);
        super.render(graphics, mx, my, pTicks);
    }

    private int getNumberFromEditBox(EditBox box){
        String s = box.getValue();
        if (s.isEmpty() || !s.chars().allMatch(Character::isDigit)){
            return 0;
        }
        return Integer.parseInt(s);
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
