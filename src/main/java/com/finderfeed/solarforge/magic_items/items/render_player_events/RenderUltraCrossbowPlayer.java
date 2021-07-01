package com.finderfeed.solarforge.magic_items.items.render_player_events;


import com.finderfeed.solarforge.magic_items.items.UltraCrossbowItem;
import com.finderfeed.solarforge.magic_items.items.solar_disc_gun.SolarDiscGunItem;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE,modid = "solarforge",value = Dist.CLIENT)
public class RenderUltraCrossbowPlayer {

    public static ResourceLocation LOC = new ResourceLocation("solarforge","textures/misc/ultra_crossbow_load.png");
    public static ResourceLocation PRICEL = new ResourceLocation("solarforge","textures/misc/solar_crossbow_pricel.png");


    @SubscribeEvent
    public static void gameOverlay(final RenderGameOverlayEvent event){
        Minecraft mc = Minecraft.getInstance();
        if (event.getType() == RenderGameOverlayEvent.ElementType.HOTBAR) {
            if (Minecraft.getInstance().player.getMainHandItem().getItem() instanceof UltraCrossbowItem) {
                MatrixStack stack = event.getMatrixStack();
                MainWindow window = event.getWindow();
                mc.getTextureManager().bind(PRICEL);
                RenderSystem.enableBlend();
                RenderSystem.color4f(1,1,0.3f,0.5f);
                int width = (int)((window.getWidth())/2/window.getGuiScale() -21);
                int height = (int)((window.getHeight())/2/window.getGuiScale() - 20);

                AbstractGui.blit(stack,width,height,0,0,41,41,41,41);

            }else if( Minecraft.getInstance().player.getOffhandItem().getItem() instanceof UltraCrossbowItem){
                MatrixStack stack = event.getMatrixStack();
                MainWindow window = event.getWindow();
                mc.getTextureManager().bind(PRICEL);
                RenderSystem.enableBlend();
                RenderSystem.color4f(1,1,0.3f,0.5f);
                int width = (int)((window.getWidth())/2/window.getGuiScale() -21);
                int height = (int)((window.getHeight())/2/window.getGuiScale() - 20);

                AbstractGui.blit(stack,width,height,0,0,41,41,41,41);
            }
        }

    }

    @SubscribeEvent
    public static void RenderHand(final RenderHandEvent event){
        if (Minecraft.getInstance().player.getMainHandItem().getItem() instanceof UltraCrossbowItem) {

            if (Minecraft.getInstance().player.isUsingItem()) {
                MatrixStack stack = event.getMatrixStack();
                IRenderTypeBuffer buffer = event.getBuffers();
                float partialTicks = event.getPartialTicks();
                int light = event.getLight();
                stack.pushPose();
                stack.translate(-0.02, -0.4, -1.7);
                stack.mulPose(Vector3f.ZP.rotationDegrees((Minecraft.getInstance().level.getGameTime() + partialTicks) * 30 % 360));

                stack.mulPose(Vector3f.XP.rotationDegrees(90));
                stack.scale(0.7f, 0.7f, 0.7f);
                drawRing(partialTicks, stack, buffer, 1, 0);
                stack.popPose();
            }
        }else if ( Minecraft.getInstance().player.getOffhandItem().getItem() instanceof UltraCrossbowItem){
            if (Minecraft.getInstance().player.isUsingItem()) {
                MatrixStack stack = event.getMatrixStack();
                IRenderTypeBuffer buffer = event.getBuffers();
                float partialTicks = event.getPartialTicks();
                int light = event.getLight();
                stack.pushPose();
                stack.translate(-0.02, -0.4, -1.7);
                stack.mulPose(Vector3f.ZP.rotationDegrees((Minecraft.getInstance().level.getGameTime() + partialTicks) * 30 % 360));

                stack.mulPose(Vector3f.XP.rotationDegrees(90));
                stack.scale(0.7f, 0.7f, 0.7f);
                drawRing(partialTicks, stack, buffer, 1, 0);
                stack.popPose();
            }
        }
    }










    @SubscribeEvent
    public static void renderPlayer(final RenderPlayerEvent.Pre event){
        if ((event.getPlayer().getMainHandItem().getItem() instanceof UltraCrossbowItem) || (event.getPlayer().getOffhandItem().getItem() instanceof UltraCrossbowItem)) {
            event.getRenderer().getModel().rightArmPose = BipedModel.ArmPose.CROSSBOW_HOLD;
        }
    }
    public static void drawRing(float partialTicks, MatrixStack stack, IRenderTypeBuffer buffer, float scaleFactor, float angle){
        IVertexBuilder vertex = buffer.getBuffer(RenderType.text(LOC));

        stack.mulPose(Vector3f.YP.rotationDegrees(angle));
        MatrixStack.Entry entry = stack.last();
        vertex.vertex(entry.pose(),-0.5F*scaleFactor,0,-0.5F*scaleFactor).color(255,255,40,200).uv(1,0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        vertex.vertex(entry.pose(),0.5F*scaleFactor,0,-0.5F*scaleFactor).color(255,255,40,200).uv(1,1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        vertex.vertex(entry.pose(),0.5F*scaleFactor,0,0.5F*scaleFactor).color(255,255,40,200).uv(0,1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        vertex.vertex(entry.pose(),-0.5F*scaleFactor,0,0.5F*scaleFactor).color(255,255,40,200).uv(0,0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();

        vertex.vertex(entry.pose(),-0.5F*scaleFactor,0,0.5F*scaleFactor).color(255,255,40,200).uv(1,0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        vertex.vertex(entry.pose(),0.5F*scaleFactor,0,0.5F*scaleFactor).color(255,255,40,200).uv(1,1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        vertex.vertex(entry.pose(),0.5F*scaleFactor,0,-0.5F*scaleFactor).color(255,255,40,200).uv(0,1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        vertex.vertex(entry.pose(),-0.5F*scaleFactor,0,-0.5F*scaleFactor).color(255,255,40,200).uv(0,0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
    }
}
