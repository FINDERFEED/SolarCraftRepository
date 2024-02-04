package com.finderfeed.solarcraft.content.items.render_player_events;


import com.finderfeed.solarcraft.content.items.ShieldOfSolarGod;
import com.finderfeed.solarcraft.local_library.helpers.RenderingTools;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RenderHandEvent;
import net.neoforged.neoforge.client.event.RenderPlayerEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.resources.ResourceLocation;



@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE,modid = "solarcraft",value = Dist.CLIENT)
public class RenderSolarGodShieldPlayer {

    public static ResourceLocation LOC = new ResourceLocation("solarcraft","textures/misc/solar_infuser_ring.png");

    @SubscribeEvent
    public static void RenderPlayerHand(final RenderHandEvent event){
        Player playerEntity = Minecraft.getInstance().player;
        if ((playerEntity.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof ShieldOfSolarGod) || (playerEntity.getItemInHand(InteractionHand.OFF_HAND).getItem() instanceof ShieldOfSolarGod) ) {
            if  ( playerEntity.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof ShieldOfSolarGod &&
                    playerEntity.isUsingItem() ){
                PoseStack stack = event.getPoseStack();
                MultiBufferSource buffer = event.getMultiBufferSource();
                float partialTicks = event.getPartialTick();
                int light = event.getPackedLight();
                stack.pushPose();
                float time = (Minecraft.getInstance().level.getGameTime() + partialTicks);
                stack.translate(0, -0.4, -0.4);
//                stack.mulPose(Vector3f.YP.rotationDegrees(time * 8 % 360));
                stack.mulPose(RenderingTools.rotationDegrees(RenderingTools.YP(),time * 8 % 360));
                stack.scale(4f, 0, 4f);


                drawRing(partialTicks, stack, buffer, 1, 0);
                stack.popPose();
            }else if (playerEntity.getItemInHand(InteractionHand.OFF_HAND).getItem() instanceof ShieldOfSolarGod &&
                    playerEntity.isUsingItem() ) {
                PoseStack stack = event.getPoseStack();
                MultiBufferSource buffer = event.getMultiBufferSource();
                float partialTicks = event.getPartialTick();
                int light = event.getPackedLight();
                stack.pushPose();
                float time = (Minecraft.getInstance().level.getGameTime() + partialTicks);
                stack.translate(0, -0.4, -0.4);
//                stack.mulPose(Vector3f.YP.rotationDegrees(time * 8 % 360));
                stack.mulPose(RenderingTools.rotationDegrees(RenderingTools.YP(),time * 8 % 360));
                stack.scale(4f, 0, 4f);

                drawRing(partialTicks, stack, buffer, 1, 0);
                stack.popPose();
            }
        }

    }

    @SubscribeEvent
    public static void RenderPlayer(final RenderPlayerEvent.Post event){
        Player playerEntity = Minecraft.getInstance().player;
        if ((playerEntity.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof ShieldOfSolarGod) || (playerEntity.getItemInHand(InteractionHand.OFF_HAND).getItem() instanceof ShieldOfSolarGod) ) {
            if  ( playerEntity.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof ShieldOfSolarGod &&
                    playerEntity.isUsingItem()  ){
                PoseStack stack = event.getPoseStack();
                MultiBufferSource buffer = event.getMultiBufferSource();
                float partialTicks = event.getPartialTick();
                stack.pushPose();
                float time = (Minecraft.getInstance().level.getGameTime() + partialTicks);
                stack.translate(0, 1, 0);
//                stack.mulPose(Vector3f.YP.rotationDegrees(time * 8 % 360));
                stack.mulPose(RenderingTools.rotationDegrees(RenderingTools.YP(),time * 8 % 360));
                stack.scale(3f, 0, 3f);


                drawRing(partialTicks, stack, buffer, 1, 0);
                stack.popPose();
            }else if (playerEntity.getItemInHand(InteractionHand.OFF_HAND).getItem() instanceof ShieldOfSolarGod &&
                    playerEntity.isUsingItem() ) {
                PoseStack stack = event.getPoseStack();
                MultiBufferSource buffer = event.getMultiBufferSource();
                float partialTicks = event.getPartialTick();
                int light = event.getPackedLight();
                stack.pushPose();
                float time = (Minecraft.getInstance().level.getGameTime() + partialTicks);
                stack.translate(0, 1,0 );
//                stack.mulPose(Vector3f.YP.rotationDegrees(time * 8 % 360));
                stack.mulPose(RenderingTools.rotationDegrees(RenderingTools.YP(),time * 8 % 360));
                stack.scale(3f, 0, 3f);

                drawRing(partialTicks, stack, buffer, 1, 0);
                stack.popPose();
            }
        }

    }

    public static void drawRing(float partialTicks,PoseStack stack,MultiBufferSource buffer,float scaleFactor,float angle){
        VertexConsumer vertex = buffer.getBuffer(RenderType.text(LOC));

//        stack.mulPose(Vector3f.YP.rotationDegrees(angle));
        stack.mulPose(RenderingTools.rotationDegrees(RenderingTools.YP(),angle));
        PoseStack.Pose entry = stack.last();
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
