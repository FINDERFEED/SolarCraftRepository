package com.finderfeed.solarcraft.mixins;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

import static com.finderfeed.solarcraft.events.other_events.event_handler.ClientEventsHandler.cameraShakeEffect;

@Mixin(GameRenderer.class)
public class GameRendererMixin {

    @Inject(method = "renderLevel",at = @At(value = "HEAD"))
    public void renderLevel(float p_109090_, long p_109091_, PoseStack matrices, CallbackInfo ci){
        if (cameraShakeEffect != null) {
            Random random = new Random(Minecraft.getInstance().level.getGameTime() * 1233);

            float spread = cameraShakeEffect.getMaxSpread();
            float mod = 1f;
            int time = cameraShakeEffect.getTicker();
            if (time <= cameraShakeEffect.getInTime()) {
                mod = time / (float) cameraShakeEffect.getInTime();
            } else if (time >= cameraShakeEffect.getInTime() + cameraShakeEffect.getStayTime()) {
                mod = 1.0f - (time - (cameraShakeEffect.getInTime() + cameraShakeEffect.getStayTime())) / (float) cameraShakeEffect.getOutTime();
            }


            spread *= mod;

            float rx = random.nextFloat() * spread * 2 - spread;
            float ry = random.nextFloat() * spread * 2 - spread;
            matrices.translate(rx,ry,0);
        }
    }

}
