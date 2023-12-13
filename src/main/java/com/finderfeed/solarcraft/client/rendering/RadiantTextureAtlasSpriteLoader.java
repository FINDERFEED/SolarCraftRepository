package com.finderfeed.solarcraft.client.rendering;

import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.client.renderer.texture.SpriteContents;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.metadata.animation.AnimationMetadataSection;
import net.minecraft.client.resources.metadata.animation.FrameSize;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.neoforged.neoforge.client.textures.ForgeTextureMetadata;
import net.neoforged.neoforge.client.textures.ITextureAtlasSpriteLoader;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

public class RadiantTextureAtlasSpriteLoader implements ITextureAtlasSpriteLoader {

    public static final ResourceLocation REGISTRY_ID = new ResourceLocation("solarcraft","radiant_loader");

    public RadiantTextureAtlasSpriteLoader(){

    }

    @Override
    public SpriteContents loadContents(ResourceLocation name, Resource resource, FrameSize frameSize, NativeImage image, AnimationMetadataSection animationMeta,
                                       ForgeTextureMetadata forgeMeta) {
        return new SpriteContents(name,frameSize,image,animationMeta,forgeMeta);
    }

    @Override
    public @NotNull TextureAtlasSprite makeSprite(ResourceLocation atlasName,
                                                  SpriteContents contents,
                                                  int atlasWidth,
                                                  int atlasHeight,
                                                  int spriteX,
                                                  int spriteY,
                                                  int mipmapLevel) {
        RadiantBlocksAtlasSprite sprite = new RadiantBlocksAtlasSprite(atlasName,contents,atlasWidth,atlasHeight,spriteX,spriteY);
        return sprite;
    }

//    @Nonnull
//    @Override
//    public TextureAtlasSprite load(TextureAtlas atlas,
//                                   ResourceManager resourceManager,
//                                   TextureAtlasSprite.Info textureInfo,
//                                   Resource resource,
//                                   int atlasWidth,
//                                   int atlasHeight,
//                                   int spriteX,
//                                   int spriteY,
//                                   int mipmapLevel,
//                                   NativeImage image) {
//
//
//        RadiantBlocksAtlasSprite sprite = new RadiantBlocksAtlasSprite(atlas,textureInfo,mipmapLevel,atlasWidth,atlasHeight,spriteX,spriteY,image);
//        return sprite;
//    }
}
