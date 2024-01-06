package com.finderfeed.solarcraft.client.rendering.radiant_texture;

import com.finderfeed.solarcraft.local_library.helpers.FDMathHelper;
import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.texture.SpriteContents;
import net.minecraft.client.renderer.texture.SpriteTicker;
import net.minecraft.client.resources.metadata.animation.FrameSize;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceMetadata;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.Nullable;

public class RadiantTextureSpriteContets extends SpriteContents {

    public NativeImage original;

    public RadiantTextureSpriteContets(ResourceLocation location, FrameSize size, NativeImage image, ResourceMetadata metadata) {
        super(location, size, image, metadata);
        original = new NativeImage(image.getWidth(),image.getHeight(),true);
        original.copyFrom(image);
    }


    @Nullable
    @Override
    public SpriteTicker createTicker() {
        return new SpriteTicker() {
            @Override
            public void tickAndUpload(int idk1, int idk2) {
                NativeImage[] mainImage = byMipLevel;
                for(int i = 0; i < mainImage.length; ++i) {
                    NativeImage image = mainImage[i];
                    if ((width() >> i <= 0) || (height() >> i <= 0)) break;
                    if (Minecraft.getInstance().level != null){
                        ClientLevel level = Minecraft.getInstance().level;

                        if (original != null) {
                            float timeofday = level.getTimeOfDay(Minecraft.getInstance().getDeltaFrameTime());
                            NativeImage image2 = original;
                            for (int a = 0; a < image.getWidth(); a++) {
                                for (int b = 0; b < image.getHeight(); b++) {
                                    int color = image2.getPixelRGBA(a, b);
                                    int[] rgba = FDMathHelper.intToRgba(color);
                                    int flag = isGray(rgba);
                                    //rb - night
                                    //rg - day
                                    if (flag != -10) {
                                        float day = getDayPercentage(timeofday);
                                        float night = getNightPercentage(timeofday);
                                        if (!((timeofday >= 0.25f ) && (timeofday <= 0.75))){

                                            int d = Math.round(flag * (Mth.clamp(0.3f, day, 1f)));
                                            int dg = d;
                                            int n = 0;
                                            if ((timeofday <= 0.25f)) {
                                                float percentage = 0.05f - day;
                                                if (percentage >= 0) {
                                                    n = Math.round(flag * percentage);
                                                }
                                            }

                                            int finalColor = FDMathHelper.rgbaToInt(new int[]{d + 10, dg + 10-n*5, n*3, 255});
                                            image.setPixelRGBA(a, b, finalColor);
                                        }else{

                                            int n = Math.round(flag * (Mth.clamp(0.2f, night, 1f)));
                                            int d = 0;
                                            if ((timeofday >= 0.5f)) {
                                                float percentage = 0.05f - night;
                                                if (percentage >= 0) {
                                                    d = Math.round(flag * percentage);
                                                }
                                            }

                                            int finalColor = FDMathHelper.rgbaToInt(new int[]{Math.round((float)n/1.8f) + d*3, d*3, n-d*3 , 255});
                                            image.setPixelRGBA(a, b, finalColor);
                                        }
                                    }
                                }
                            }
                        }
                    }

                    image.upload(i, idk1 >> i, idk2 >> i, 0 >> i, 0 >> i, width() >> i, height() >> i, mainImage.length > 1, false);
                }
            }

            @Override
            public void close() {

            }
        };
    }


    private float getDayPercentage(float timeOfDay){
        float toReturn = 0f;
        if (timeOfDay >= 0.75){
            toReturn = (timeOfDay-0.75f)*4;
        }else if ((timeOfDay >= 0) && (timeOfDay <= 0.25f)){
            toReturn = 1-timeOfDay*4;
        }
        return toReturn;
    }

    private float getNightPercentage(float timeOfDay){
        float toReturn = 0f;
        if ((timeOfDay >= 0.25f) && (timeOfDay < 0.5f)){
            toReturn = (timeOfDay-0.25f)*4;
        }else if ((timeOfDay >= 0.5f) && (timeOfDay <= 0.75f)){
            toReturn = 1-(timeOfDay-0.5f)*4;
        }
        return toReturn;
    }
    private int isGray(int[] rgb){
        return ((rgb[0] != 0) && (rgb[0] == rgb[1]) && (rgb[0] == rgb[2])) ? rgb[1] : -10;
    }
}
