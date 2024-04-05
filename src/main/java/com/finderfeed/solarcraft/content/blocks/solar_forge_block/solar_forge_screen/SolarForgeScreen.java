package com.finderfeed.solarcraft.content.blocks.solar_forge_block.solar_forge_screen;


import com.finderfeed.solarcraft.helpers.ClientHelpers;
import com.finderfeed.solarcraft.local_library.helpers.RenderingTools;
import com.finderfeed.solarcraft.packet_handler.SCPacketHandler;
import com.finderfeed.solarcraft.packet_handler.packet_system.FDPacketUtil;
import com.finderfeed.solarcraft.packet_handler.packets.TakeEnergyFromForgePacket;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.resources.ResourceLocation;
import java.util.List;
import java.util.Optional;

@OnlyIn(Dist.CLIENT)
public class SolarForgeScreen extends AbstractContainerScreen<SolarForgeContainer> {




    private static final ResourceLocation GUI_TEXT = new ResourceLocation("solarcraft","textures/gui/solar_forge_gui.png");

    public SolarForgeScreen(SolarForgeContainer container, Inventory inv, Component text) {
        super(container, inv, text);
        this.leftPos = 0;
        this.topPos = 0;
        this.height = 170;
        this.width = 256;
        this.imageHeight = 170;
        this.imageWidth = 256;

    }


    @Override
    public void init(){
        super.init();
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;

        addRenderableWidget(new SolarCraftButtonRed(i + 186, j + 32, 65, 15, Component.translatable("forge.take"),(button)->{
            FDPacketUtil.sendToServer(new TakeEnergyFromForgePacket(this.menu.te.getBlockPos()));
//            SCPacketHandler.INSTANCE.sendToServer(new TakeEnergyFromForgePacket(this.menu.te.getBlockPos()));
        },(button,graphics,mousex,mousey)->{
            graphics.renderTooltip(font, List.of(Component.literal("Consume energy. Abilities were moved to separate screen"),
                    Component.literal("look into hotkey settings.")
                    ), Optional.empty(),mousex,mousey);

        }));

    }

    @Override
    public void render(GuiGraphics graphics, int rouseX, int rouseY, float partialTicks){
        this.renderBackground(graphics,rouseX,rouseY,partialTicks);
        super.render(graphics,rouseX,rouseY,partialTicks);
        this.renderTooltip(graphics,rouseX,rouseY);

    }
    @Override
    protected void renderBg(GuiGraphics graphics, float partialTicks, int x, int y) {

        PoseStack matrices = graphics.pose();


        ClientHelpers.bindText(GUI_TEXT);
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        matrices.pushPose();
        RenderingTools.blitWithBlend(matrices, i, j, 0, 0, this.imageWidth, this.imageHeight,256,256,0,1f);




        float percent = (float)this.menu.getCurrentCharge()/30000;

        RenderingTools.blitWithBlend(matrices, i + 14, j + 22, 7, 170, 55, (int) (40 * percent),256,256,0,1f);
        graphics.drawString( minecraft.font, Integer.toString(this.menu.getCurrentCharge())+"/30000",i+65,j+37,0xff002b);
        matrices.popPose();
    }



}

