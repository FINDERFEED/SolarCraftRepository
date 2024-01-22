package com.finderfeed.solarcraft.client.rendering.radiant_texture;

import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.SpriteContents;
import net.minecraft.client.renderer.texture.SpriteTicker;
import net.minecraft.client.resources.metadata.animation.FrameSize;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceMetadata;
import org.jetbrains.annotations.Nullable;

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
                for (NativeImage image : imagesToUpload){
                    for (int i = 0; i < image.getWidth();i++){
                        for (int g = 0; g < image.getHeight();g++){
                          if (lvl == null) {
                              image.setPixelRGBA(i, g, 0xff0000ff);
                          }else{
                              long time = lvl.getGameTime() % 8;
                              if (time < 4){
                                  image.setPixelRGBA(i, g, 0x00ff00ff);
                              }else{
                                  image.setPixelRGBA(i, g, 0xff0000ff);
                              }
                          }
                        }
                    }
                }

                for (int i = 0; i < imagesToUpload.length;i++){
                    var img = imagesToUpload[i];
                    img.upload(i,atlasX >> i,atlasY >> i,0,0,
                            RadiantTextureSpriteContents.this.width() >> i,
                            RadiantTextureSpriteContents.this.height() >> i,
                            RadiantTextureSpriteContents.this.byMipLevel.length > 1,
                            false
                            );
                }
            }

            @Override
            public void close() {

            }

        };
    }


    private void upload(int w, int h, int x, int y, NativeImage[] images) {
        for(int i = 0; i < this.byMipLevel.length && this.width() >> i > 0 && this.height() >> i > 0; ++i) {
            images[i].upload(i, w >> i, h >> i, x >> i, y >> i,
                    this.width() >> i, this.height() >> i, this.byMipLevel.length > 1, false);
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
