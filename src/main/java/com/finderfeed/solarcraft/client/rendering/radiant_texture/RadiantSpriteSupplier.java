package com.finderfeed.solarcraft.client.rendering.radiant_texture;

import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.client.renderer.texture.SpriteContents;
import net.minecraft.client.renderer.texture.atlas.SpriteResourceLoader;
import net.minecraft.client.renderer.texture.atlas.SpriteSource;
import net.minecraft.client.renderer.texture.atlas.sources.LazyLoadedImage;
import net.minecraft.client.resources.metadata.animation.FrameSize;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceMetadata;

import java.io.IOException;

public class RadiantSpriteSupplier implements SpriteSource.SpriteSupplier {

    private LazyLoadedImage image;
    private ResourceLocation loc;

    public RadiantSpriteSupplier(ResourceLocation loc,LazyLoadedImage image){
        this.image = image;
        this.loc = loc;
    }

    @Override
    public SpriteContents apply(SpriteResourceLoader spriteResourceLoader) {
        try {
            NativeImage n = image.get();
            RadiantTextureSpriteContents contents = new RadiantTextureSpriteContents(loc,
                    new FrameSize(n.getWidth(),n.getHeight()),
                    n, ResourceMetadata.EMPTY);

            return contents;
        }catch (IOException e){
            throw new RuntimeException("Failed to load image: " + loc);
        }
    }
}
