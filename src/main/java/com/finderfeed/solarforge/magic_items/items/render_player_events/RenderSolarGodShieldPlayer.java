package com.finderfeed.solarforge.magic_items.items.render_player_events;


import com.finderfeed.solarforge.magic_items.items.ShieldOfSolarGod;
import com.finderfeed.solarforge.magic_items.items.isters.ShieldOfSolarGodISTER;
import com.finderfeed.solarforge.magic_items.items.item_models.SolarGodShield;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE,modid = "solarforge",value = Dist.CLIENT)
public class RenderSolarGodShieldPlayer {

    public static ResourceLocation LOC = new ResourceLocation("solarforge","textures/misc/solar_infuser_ring.png");

    @SubscribeEvent
    public static void RenderPlayerHand(final RenderHandEvent event){
        PlayerEntity playerEntity = Minecraft.getInstance().player;
        if ((playerEntity.getItemInHand(Hand.MAIN_HAND).getItem() instanceof ShieldOfSolarGod) || (playerEntity.getItemInHand(Hand.OFF_HAND).getItem() instanceof ShieldOfSolarGod) ) {
            if  ( playerEntity.getItemInHand(Hand.MAIN_HAND).getItem() instanceof ShieldOfSolarGod &&
                    ((ShieldOfSolarGodISTER)(playerEntity.getItemInHand(Hand.MAIN_HAND).getItem().getItemStackTileEntityRenderer())).isBeingUsed ){
                MatrixStack stack = event.getMatrixStack();
                IRenderTypeBuffer buffer = event.getBuffers();
                float partialTicks = event.getPartialTicks();
                int light = event.getLight();
                stack.pushPose();
                float time = (Minecraft.getInstance().level.getGameTime() + partialTicks);
                stack.translate(0, -0.4, -0.4);
                stack.mulPose(Vector3f.YP.rotationDegrees(time * 8 % 360));
                stack.scale(4f, 0, 4f);


                drawRing(partialTicks, stack, buffer, 1, 0);
                stack.popPose();
            }else if (playerEntity.getItemInHand(Hand.OFF_HAND).getItem() instanceof ShieldOfSolarGod &&
                    ((ShieldOfSolarGodISTER)(playerEntity.getItemInHand(Hand.OFF_HAND).getItem().getItemStackTileEntityRenderer())).isBeingUsed) {
                MatrixStack stack = event.getMatrixStack();
                IRenderTypeBuffer buffer = event.getBuffers();
                float partialTicks = event.getPartialTicks();
                int light = event.getLight();
                stack.pushPose();
                float time = (Minecraft.getInstance().level.getGameTime() + partialTicks);
                stack.translate(0, -0.4, -0.4);
                stack.mulPose(Vector3f.YP.rotationDegrees(time * 8 % 360));
                stack.scale(4f, 0, 4f);

                drawRing(partialTicks, stack, buffer, 1, 0);
                stack.popPose();
            }
        }

    }

    @SubscribeEvent
    public static void RenderPlayer(final RenderPlayerEvent event){
        PlayerEntity playerEntity = Minecraft.getInstance().player;
        if ((playerEntity.getItemInHand(Hand.MAIN_HAND).getItem() instanceof ShieldOfSolarGod) || (playerEntity.getItemInHand(Hand.OFF_HAND).getItem() instanceof ShieldOfSolarGod) ) {
            if  ( playerEntity.getItemInHand(Hand.MAIN_HAND).getItem() instanceof ShieldOfSolarGod &&
                    ((ShieldOfSolarGodISTER)(playerEntity.getItemInHand(Hand.MAIN_HAND).getItem().getItemStackTileEntityRenderer())).isBeingUsed ){
                MatrixStack stack = event.getMatrixStack();
                IRenderTypeBuffer buffer = event.getBuffers();
                float partialTicks = event.getPartialRenderTick();
                int light = event.getLight();
                stack.pushPose();
                float time = (Minecraft.getInstance().level.getGameTime() + partialTicks);
                stack.translate(0, 1, 0);
                stack.mulPose(Vector3f.YP.rotationDegrees(time * 8 % 360));
                stack.scale(3f, 0, 3f);


                drawRing(partialTicks, stack, buffer, 1, 0);
                stack.popPose();
            }else if (playerEntity.getItemInHand(Hand.OFF_HAND).getItem() instanceof ShieldOfSolarGod &&
                    ((ShieldOfSolarGodISTER)(playerEntity.getItemInHand(Hand.OFF_HAND).getItem().getItemStackTileEntityRenderer())).isBeingUsed) {
                MatrixStack stack = event.getMatrixStack();
                IRenderTypeBuffer buffer = event.getBuffers();
                float partialTicks = event.getPartialRenderTick();
                int light = event.getLight();
                stack.pushPose();
                float time = (Minecraft.getInstance().level.getGameTime() + partialTicks);
                stack.translate(0, 1,0 );
                stack.mulPose(Vector3f.YP.rotationDegrees(time * 8 % 360));
                stack.scale(3f, 0, 3f);

                drawRing(partialTicks, stack, buffer, 1, 0);
                stack.popPose();
            }
        }

    }

    public static void drawRing(float partialTicks,MatrixStack stack,IRenderTypeBuffer buffer,float scaleFactor,float angle){
        IVertexBuilder vertex = buffer.getBuffer(RenderType.text(LOC));

        stack.mulPose(Vector3f.YP.rotationDegrees(angle));
        MatrixStack.Entry entry = stack.last();
        vertex.vertex(entry.pose(),-0.5F*scaleFactor,0,-0.5F*scaleFactor).color(255,255,255,255).uv(1,0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        vertex.vertex(entry.pose(),0.5F*scaleFactor,0,-0.5F*scaleFactor).color(255,255,255,255).uv(1,1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        vertex.vertex(entry.pose(),0.5F*scaleFactor,0,0.5F*scaleFactor).color(255,255,255,255).uv(0,1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        vertex.vertex(entry.pose(),-0.5F*scaleFactor,0,0.5F*scaleFactor).color(255,255,255,255).uv(0,0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();

        vertex.vertex(entry.pose(),-0.5F*scaleFactor,0,0.5F*scaleFactor).color(255,255,255,255).uv(1,0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        vertex.vertex(entry.pose(),0.5F*scaleFactor,0,0.5F*scaleFactor).color(255,255,255,255).uv(1,1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        vertex.vertex(entry.pose(),0.5F*scaleFactor,0,-0.5F*scaleFactor).color(255,255,255,255).uv(0,1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        vertex.vertex(entry.pose(),-0.5F*scaleFactor,0,-0.5F*scaleFactor).color(255,255,255,255).uv(0,0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
    }
}
