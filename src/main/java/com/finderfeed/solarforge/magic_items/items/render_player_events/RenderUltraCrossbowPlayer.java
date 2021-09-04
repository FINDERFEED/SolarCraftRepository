package com.finderfeed.solarforge.magic_items.items.render_player_events;


import com.finderfeed.solarforge.ClientHelpers;
import com.finderfeed.solarforge.magic_items.items.UltraCrossbowItem;
import com.finderfeed.solarforge.magic_items.items.solar_disc_gun.SolarDiscGunItem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.platform.Window;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import com.mojang.math.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.opengl.GL11;


@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE,modid = "solarforge",value = Dist.CLIENT)
public class RenderUltraCrossbowPlayer {

    public static ResourceLocation LOC = new ResourceLocation("solarforge","textures/misc/ultra_crossbow_load.png");
    public static ResourceLocation PRICEL = new ResourceLocation("solarforge","textures/misc/solar_crossbow_pricel.png");


    @SubscribeEvent
    public static void gameOverlay(final RenderGameOverlayEvent event){
        Minecraft mc = Minecraft.getInstance();
        if (event.getType() == RenderGameOverlayEvent.ElementType.TEXT) {
            if (Minecraft.getInstance().player.getMainHandItem().getItem() instanceof UltraCrossbowItem) {
                PoseStack stack = event.getMatrixStack();
                Window window = event.getWindow();
                ClientHelpers.bindText(PRICEL);
                RenderSystem.enableBlend();

                RenderSystem.setShaderColor(1,1,0.3f,0.5f);
                int width = (int)((window.getWidth())/2/window.getGuiScale() -21);
                int height = (int)((window.getHeight())/2/window.getGuiScale() - 20);

                GuiComponent.blit(stack,width,height,0,0,41,41,41,41);
                RenderSystem.setShaderColor(1,1,1f,1f);
            }else if( Minecraft.getInstance().player.getOffhandItem().getItem() instanceof UltraCrossbowItem){
                PoseStack stack = event.getMatrixStack();
                Window window = event.getWindow();
                ClientHelpers.bindText(PRICEL);
                RenderSystem.enableBlend();
                RenderSystem.setShaderColor(1,1,0.3f,0.5f);

                int width = (int)((window.getWidth())/2/window.getGuiScale() -21);
                int height = (int)((window.getHeight())/2/window.getGuiScale() - 20);

                GuiComponent.blit(stack,width,height,0,0,41,41,41,41);
                RenderSystem.setShaderColor(1,1,1f,1f);
            }
        }

    }

    @SubscribeEvent
    public static void RenderHand(final RenderHandEvent event){
        if (Minecraft.getInstance().player.getMainHandItem().getItem() instanceof UltraCrossbowItem) {

            if (Minecraft.getInstance().player.isUsingItem()) {
                PoseStack stack = event.getMatrixStack();
                MultiBufferSource buffer = event.getBuffers();
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
                PoseStack stack = event.getMatrixStack();
                MultiBufferSource buffer = event.getBuffers();
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
            event.getRenderer().getModel().rightArmPose = HumanoidModel.ArmPose.CROSSBOW_HOLD;
        }
    }
    public static void drawRing(float partialTicks, PoseStack stack, MultiBufferSource buffer, float scaleFactor, float angle){
        VertexConsumer vertex = buffer.getBuffer(RenderType.text(LOC));

        stack.mulPose(Vector3f.YP.rotationDegrees(angle));
        PoseStack.Pose entry = stack.last();
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
