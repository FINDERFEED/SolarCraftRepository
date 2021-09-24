package com.finderfeed.solarforge.rendering;

import com.finderfeed.solarforge.for_future_library.helpers.FinderfeedMathHelper;
import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.Mth;

import java.util.ArrayList;
import java.util.List;


public class RadiantBlocksAtlasSprite extends TextureAtlasSprite {

    private List<NativeImage> original;

    protected RadiantBlocksAtlasSprite(TextureAtlas p_118358_, Info p_118359_, int p_118360_, int p_118361_, int p_118362_, int p_118363_, int p_118364_, NativeImage p_118365_) {
        super(p_118358_, p_118359_, p_118360_, p_118361_, p_118362_, p_118363_, p_118364_, p_118365_);

    }

    @Override
    public void uploadFirstFrame() {

        super.uploadFirstFrame();
        if (original == null) {
            original = new ArrayList<>();
            for (int i = 0;i < mainImage.length;i++){
                NativeImage originalImage = mainImage[i];
                NativeImage image = new NativeImage(originalImage.getWidth(),originalImage.getHeight(),true);
                for (int a = 0; a < originalImage.getWidth(); a++) {
                    for (int b = 0; b < originalImage.getHeight(); b++) {
                        image.setPixelRGBA(a,b,originalImage.getPixelRGBA(a,b));
                    }
                }
                original.add(image);
            }
        }
    }

    @Override
    public void upload(int p_118376_, int p_118377_, NativeImage[] images) {
        for(int i = 0; i < this.mainImage.length; ++i) {
            NativeImage image = images[i];
            if ((this.getWidth() >> i <= 0) || (this.getHeight() >> i <= 0)) break;
            if (Minecraft.getInstance().level != null){
                ClientLevel level = Minecraft.getInstance().level;

                if (original != null) {
                    float timeofday = level.getTimeOfDay(Minecraft.getInstance().getDeltaFrameTime());
                    NativeImage image2 = original.get(i);
                    for (int a = 0; a < image.getWidth(); a++) {
                        for (int b = 0; b < image.getHeight(); b++) {
                            int color = image2.getPixelRGBA(a, b);
                            int[] rgba = FinderfeedMathHelper.intToRgba(color);
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

                                    int finalColor = FinderfeedMathHelper.rgbaToInt(new int[]{d + 10, dg + 10-n*5, n*3, 255});
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

                                    int finalColor = FinderfeedMathHelper.rgbaToInt(new int[]{Math.round((float)n/1.8f) + d*3, d*3, n-d*3 , 255});
                                    image.setPixelRGBA(a, b, finalColor);
                                }
                            }
                        }
                    }
                }
            }


            image.upload(i, this.getX() >> i, this.getY() >> i, p_118376_ >> i, p_118377_ >> i, this.getWidth() >> i, this.getHeight() >> i, this.mainImage.length > 1, false);
        }
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
