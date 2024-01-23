package com.finderfeed.solarcraft.client.rendering.radiant_texture;

import com.finderfeed.solarcraft.local_library.helpers.FDMathHelper;
import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.SpriteContents;
import net.minecraft.client.renderer.texture.SpriteTicker;
import net.minecraft.client.resources.metadata.animation.FrameSize;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceMetadata;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

public class RadiantTextureSpriteContents extends SpriteContents {

    public NativeImage[] imagesToUpload;

    public RadiantTextureSpriteContents(ResourceLocation location, FrameSize size, NativeImage image, ResourceMetadata metadata) {
        super(location, size, image, metadata);
        this.initImages();
    }


    @Override
    public void increaseMipLevel(int mipmaps) {
        super.increaseMipLevel(mipmaps);
        this.initImages();
    }

    private void initImages(){
        if (imagesToUpload != null){
            for (NativeImage image : imagesToUpload){
                image.close();
            }
            imagesToUpload = new NativeImage[byMipLevel.length];
        }else{
            imagesToUpload = new NativeImage[byMipLevel.length];
        }
        for (int i = 0; i < byMipLevel.length;i++){
            NativeImage orig = byMipLevel[i];
            NativeImage n = new NativeImage(orig.getWidth(),orig.getHeight(),false);
            n.copyFrom(orig);
            imagesToUpload[i] = n;
        }
    }



    @Nullable
    @Override
    public SpriteTicker createTicker() {
        return new SpriteTicker() {
            @Override
            public void tickAndUpload(int atlasX, int atlasY) {
                var lvl = Minecraft.getInstance().level;
                if (lvl != null) {
                    this.recolor(byMipLevel,imagesToUpload,lvl);
                }
                this.upload(imagesToUpload,atlasX, atlasY);
            }


            private void recolor(NativeImage[] originalImages, NativeImage[] toUpload, Level level){
                float time = level.getTimeOfDay(Minecraft.getInstance().getDeltaFrameTime());

                int[] abgr = calculateABGR(time);
                for (int i = 0; i < originalImages.length;i++){
                    var orig = originalImages[i];
                    var copy = toUpload[i];
                    for (int x = 0; x < orig.getWidth(); x++){
                        for (int y = 0; y < orig.getHeight(); y++){
                            this.recolorPixel(orig,copy,x,y,abgr);
                        }
                    }
                }
            }


            //format - a b g r
            //night hsv - 255°, 75%, 100%
            //day hsv - 56°, 100%, 100%
            //original time
            /*
            0.0 / 6000 - sun in zenith
            0.25 / 12000 - sun goes over horizon
            0.5 / 18000 - moon in zenith
            0.75 / 0 - moon goes over horizon
             */
            private int[] calculateABGR(float time){
                float percent;
                float bpercent = 1;
                if (time > 0.75){
                    percent = 1 - (time - 0.75f) / 0.25f;
                    bpercent = percent;
                    bpercent = 1 - (float) Math.pow(bpercent,8);
                }else if (time < 0.25){
                    percent = time / 0.25f;
                    bpercent = percent;
                    bpercent = 1 - (float) Math.pow(bpercent,8);
                }else{
                    bpercent = 1 - Math.abs(time - 0.5f) / 0.25f;
                    percent = 1;
                }
                percent = (float) Math.pow(percent,16);
                float h = FDMathHelper.lerpThroughBoundaries(60f,255f,percent,0,360);
                float s = FDMathHelper.lerp(1f,0.75f,percent);
                float b = FDMathHelper.lerp(0.5f,1f,bpercent);
                int[] rgb = FDMathHelper.hsvToRgb(h,s,b);

                return new int[]{255,rgb[2],rgb[1],rgb[0]};
            }

            private void recolorPixel(NativeImage originalImage,NativeImage toUpload,int x,int y,int[] abgr){
                int sd = originalImage.getPixelRGBA(x,y);
                int[] oabgr = unpackABGR(originalImage.getPixelRGBA(x,y));

                if (!isGray(oabgr)) return;
                oabgr[3] = Math.round(((oabgr[3] / 255f) * (abgr[3]/255f)) * 255);
                oabgr[2] = Math.round(((oabgr[2] / 255f) * (abgr[2]/255f)) * 255);
                oabgr[1] = Math.round(((oabgr[1] / 255f) * (abgr[1]/255f)) * 255);
                int fabgr = packABGR(oabgr);
                toUpload.setPixelRGBA(x,y,fabgr);
            }

            private int[] unpackABGR(int abgr){
                int a = (abgr & 0xff000000) >> 24;
                int b = (abgr & 0x00ff0000) >> 16;
                int g = (abgr & 0x0000ff00) >> 8;
                int r = (abgr & 0x000000ff);
                return new int[]{
                       a,b,g,r
                };
            }

            private int packABGR(int[] abgr){
                return (abgr[0] << 24) +
                        (abgr[1] << 16) +
                        (abgr[2] << 8) +
                        abgr[3];
            }


            private void upload(NativeImage[] upload,int atlasX,int atlasY){
                for (int i = 0; i < upload.length;i++){
                    var img = upload[i];
                    img.upload(i,atlasX >> i,atlasY >> i,0,0,
                            RadiantTextureSpriteContents.this.width() >> i,
                            RadiantTextureSpriteContents.this.height() >> i,
                            upload.length > 1,
                            false
                    );
                }
            }

            private boolean isGray(int[] abgr){
                return abgr[1] == abgr[2] && abgr[1] == abgr[3] && abgr[1] != 0;
            }

            @Override
            public void close() {
                for (NativeImage image : RadiantTextureSpriteContents.this.imagesToUpload){
                    image.close();
                }
                RadiantTextureSpriteContents.this.imagesToUpload = null;
            }

        };
    }

}
