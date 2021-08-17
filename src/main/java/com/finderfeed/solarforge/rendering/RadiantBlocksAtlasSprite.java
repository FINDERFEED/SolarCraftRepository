package com.finderfeed.solarforge.rendering;

import com.finderfeed.solarforge.Helpers;
import com.finderfeed.solarforge.for_future_library.FinderfeedMathHelper;
import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;

import java.awt.*;
import java.util.Arrays;


public class RadiantBlocksAtlasSprite extends TextureAtlasSprite {



    protected RadiantBlocksAtlasSprite(TextureAtlas p_118358_, Info p_118359_, int p_118360_, int p_118361_, int p_118362_, int p_118363_, int p_118364_, NativeImage p_118365_) {
        super(p_118358_, p_118359_, p_118360_, p_118361_, p_118362_, p_118363_, p_118364_, p_118365_);

    }


    @Override
    public void upload(int p_118376_, int p_118377_, NativeImage[] images) {
        for(int i = 0; i < this.mainImage.length; ++i) {
            if ((this.getWidth() >> i <= 0) || (this.getHeight() >> i <= 0)) break;
            if (Minecraft.getInstance().level != null){
                ClientLevel level = Minecraft.getInstance().level;
                float timeofday = level.getTimeOfDay(Minecraft.getInstance().getDeltaFrameTime());
                NativeImage image = images[i];
                if (timeofday >= 0.5f ){
                    for (int a = 0;a < image.getWidth();a++){
                        for (int b = 0;b < image.getHeight();b++){
                            int color = image.getPixelRGBA(a,b);

                            //abgr
                            image.setPixelRGBA(a,b,0xffffffff);
                        }
                    }
                }else{
                    for (int a = 0;a < image.getWidth();a++){
                        for (int b = 0;b < image.getHeight();b++){
                            int color = image.getPixelRGBA(a,b);

                            //abgr
                            image.setPixelRGBA(a,b,0xffff00ff);
                        }
                    }
                }
            }
            images[i].upload(i, this.getX() >> i, this.getY() >> i, p_118376_ >> i, p_118377_ >> i, this.getWidth() >> i, this.getHeight() >> i, this.mainImage.length > 1, false);
        }
    }



}
