package com.finderfeed.solarforge.magic_items.blocks.solar_forge_block.solar_forge_screen;


import com.finderfeed.solarforge.packet_handler.packets.AbilityBuyPacket;
import com.finderfeed.solarforge.packet_handler.SolarForgePacketHandler;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SolarForgeScreen extends ContainerScreen<SolarForgeContainer> {




    private static final ResourceLocation GUI_TEXT = new ResourceLocation("solarforge","textures/gui/solar_forge_gui.png");

    public SolarForgeScreen(SolarForgeContainer container, PlayerInventory inv, ITextComponent text) {
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


        addButton(new SolarForgeButton(i + 186, j + 10, 65, 15, new StringTextComponent("Fireball"), button -> SolarForgePacketHandler.INSTANCE.sendToServer(new AbilityBuyPacket("fireball",this.menu.te.getBlockPos(),15000)),
                (button, matrices, p_onTooltip_3_, p_onTooltip_4_) ->{
            Minecraft.getInstance().textureManager.bind(new ResourceLocation("solarforge","textures/gui/tooltips_solarforge.png"));
            blit(matrices,i,j-38,100,0,0,38,38,256,256);
            drawString(matrices, Minecraft.getInstance().font, "Fireball",i+39 ,j -30,0xffffff);
            drawString(matrices, Minecraft.getInstance().font, "Lets you launch Fireballs",i+39 ,j -21,0xffffff);
            drawString(matrices, Minecraft.getInstance().font, "Cost:15000.Manacost:50",i+39 ,j -12,0xffffff);
        }));
        addButton(new SolarForgeButton(i + 186, j + 26, 65, 15, new StringTextComponent("Lightning"), button -> SolarForgePacketHandler.INSTANCE.sendToServer(new AbilityBuyPacket("lightning",this.menu.te.getBlockPos(),20000)),(button, matrices, p_onTooltip_3_, p_onTooltip_4_) ->
        {
            Minecraft.getInstance().textureManager.bind(new ResourceLocation("solarforge","textures/gui/tooltips_solarforge.png"));
            blit(matrices,i,j-38,100,38,0,38,38,256,256);
            drawString(matrices, Minecraft.getInstance().font, "Lightning",i+39 ,j -30,0xffffff);
            drawString(matrices, Minecraft.getInstance().font, "Lets you cast Lightnings",i+39 ,j -21,0xffffff);
            drawString(matrices, Minecraft.getInstance().font, "Cost:20000.Manacost:50",i+39 ,j -12,0xffffff);
        }));
        addButton(new SolarForgeButton(i + 186, j + 42, 65, 15, new StringTextComponent("Disarm"), button -> SolarForgePacketHandler.INSTANCE.sendToServer(new AbilityBuyPacket("solar_stun",this.menu.te.getBlockPos(),20000)),(button, matrices, p_onTooltip_3_, p_onTooltip_4_) ->
        {
            Minecraft.getInstance().textureManager.bind(new ResourceLocation("solarforge","textures/gui/tooltips_solarforge.png"));
            blit(matrices,i,j-38,100,76,0,38,38,256,256);
            drawString(matrices, Minecraft.getInstance().font, "Disarm",i+39 ,j -30,0xffffff);
            drawString(matrices, Minecraft.getInstance().font, "Affected enemies dont deal damage",i+39 ,j -21,0xffffff);
            drawString(matrices, Minecraft.getInstance().font, "8 block radius.Cost:20000.Manacost:300",i+39 ,j -12,0xffffff);
        }));
        addButton(new SolarForgeButton(i + 186, j + 58, 65, 15, new StringTextComponent("Solar Strike"), button -> SolarForgePacketHandler.INSTANCE.sendToServer(new AbilityBuyPacket("solar_strike",this.menu.te.getBlockPos(),30000)),(button, matrices, p_onTooltip_3_, p_onTooltip_4_) ->
        {
            Minecraft.getInstance().textureManager.bind(new ResourceLocation("solarforge","textures/gui/tooltips_solarforge.png"));
            blit(matrices,i,j-38,100,114,0,38,38,256,256);
            drawString(matrices, Minecraft.getInstance().font, "Solar Strike",i+39 ,j -30,0xffffff);
            drawString(matrices, Minecraft.getInstance().font, "Call down the rage of the sun itself!",i+39 ,j -21,0xffffff);
            drawString(matrices, Minecraft.getInstance().font, "Cost:30000.Manacost:1000",i+39 ,j -12,0xffffff);
        }));
        addButton(new SolarForgeButton(i + 186, j + 74, 65, 15, new StringTextComponent("Meteorite"), button -> SolarForgePacketHandler.INSTANCE.sendToServer(new AbilityBuyPacket("meteorite",this.menu.te.getBlockPos(),27000)),(button, matrices, p_onTooltip_3_, p_onTooltip_4_) ->
        {
            Minecraft.getInstance().textureManager.bind(new ResourceLocation("solarforge","textures/gui/tooltips_solarforge.png"));
            blit(matrices,i,j-38,100,152,0,38,38,256,256);
            drawString(matrices, Minecraft.getInstance().font, "Meteorite",i+39 ,j -30,0xffffff);
            drawString(matrices, Minecraft.getInstance().font, "Lets you call down meteorites",i+39 ,j -21,0xffffff);
            drawString(matrices, Minecraft.getInstance().font, "Cost:27000.Manacost:500",i+39 ,j -12,0xffffff);
        }));
        addButton(new SolarForgeButton(i + 186, j + 90, 65, 15, new StringTextComponent("Heal"), button -> SolarForgePacketHandler.INSTANCE.sendToServer(new AbilityBuyPacket("solar_heal",this.menu.te.getBlockPos(),15000)),(button, matrices, p_onTooltip_3_, p_onTooltip_4_) ->
        {
            Minecraft.getInstance().textureManager.bind(new ResourceLocation("solarforge","textures/gui/tooltips_solarforge.png"));
            blit(matrices,i,j-38,100,190,0,38,38,256,256);
            drawString(matrices, Minecraft.getInstance().font, "Heal",i+39 ,j -30,0xffffff);
            drawString(matrices, Minecraft.getInstance().font, "Heals you for 2 hearts",i+39 ,j -21,0xffffff);
            drawString(matrices, Minecraft.getInstance().font, "Cost:15000.Manacost:250",i+39 ,j -12,0xffffff);
        }));
        addButton(new SolarForgeButton(i + 186, j + 106, 65, 15, new StringTextComponent("Alchemist"), button -> SolarForgePacketHandler.INSTANCE.sendToServer(new AbilityBuyPacket("alchemist",this.menu.te.getBlockPos(),25000)),(button, matrices, p_onTooltip_3_, p_onTooltip_4_) ->
        {
            Minecraft.getInstance().textureManager.bind(new ResourceLocation("solarforge","textures/gui/tooltips_solarforge.png"));
            blit(matrices,i,j-38,100,0,38,38,38,256,256);
            drawString(matrices, Minecraft.getInstance().font, "Alchemist",i+39 ,j -30,0xffffff);
            drawString(matrices, Minecraft.getInstance().font, "When active, transforms blocks into exp",i+39 ,j -21,0xffffff);
            drawString(matrices, Minecraft.getInstance().font, "Cost:25000.Mana/sec:10",i+39 ,j -12,0xffffff);
        }));
        addButton(new SolarForgeButton(i + 186, j + 122, 65, 15, new StringTextComponent("Dispel"), button -> SolarForgePacketHandler.INSTANCE.sendToServer(new AbilityBuyPacket("solar_dispel",this.menu.te.getBlockPos(),20000)),(button, matrices, p_onTooltip_3_, p_onTooltip_4_) ->
        {
            Minecraft.getInstance().textureManager.bind(new ResourceLocation("solarforge","textures/gui/tooltips_solarforge.png"));
            blit(matrices,i,j-38,100,38,38,38,38,256,256);
            drawString(matrices, Minecraft.getInstance().font, "Dispel",i+39 ,j -30,0xffffff);
            drawString(matrices, Minecraft.getInstance().font, "Removes all negative effects",i+39 ,j -21,0xffffff);
            drawString(matrices, Minecraft.getInstance().font, "Cost:20000.Mana:400",i+39 ,j -12,0xffffff);
        }));
//solar push

    }

    @Override
    public void render(MatrixStack stack,int rouseX,int rouseY,float partialTicks){
        this.renderBackground(stack);
        super.render(stack,rouseX,rouseY,partialTicks);
        this.renderTooltip(stack,rouseX,rouseY);

    }
    @Override
    protected void renderBg(MatrixStack matrices, float partialTicks, int x, int y) {

        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bind(GUI_TEXT);
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        matrices.pushPose();
        this.blit(matrices, i, j, 0, 0, this.imageWidth, this.imageHeight);




        float percent = (float)this.menu.getCurrentCharge()/30000;

        this.blit(matrices, i + 14, j + 22, 7, 170, 55, (int) (40 * percent));


        drawString(matrices, minecraft.font, Integer.toString(this.menu.getCurrentCharge())+"/30000",i+65,j+37,0xff002b);
        matrices.popPose();
    }



}

