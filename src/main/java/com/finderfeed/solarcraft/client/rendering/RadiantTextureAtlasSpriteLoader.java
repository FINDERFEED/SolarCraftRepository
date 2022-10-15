package com.finderfeed.solarcraft.client.rendering;

import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraftforge.client.textures.ITextureAtlasSpriteLoader;

import javax.annotation.Nonnull;

public class RadiantTextureAtlasSpriteLoader implements ITextureAtlasSpriteLoader {

    public static final ResourceLocation REGISTRY_ID = new ResourceLocation("solarcraft","radiant_loader");

    public RadiantTextureAtlasSpriteLoader(){

    }

    @Nonnull
    @Override
    public TextureAtlasSprite load(TextureAtlas atlas,
                                   ResourceManager resourceManager,
                                   TextureAtlasSprite.Info textureInfo,
                                   Resource resource,
                                   int atlasWidth,
                                   int atlasHeight,
                                   int spriteX,
                                   int spriteY,
                                   int mipmapLevel,
                                   NativeImage image) {


        RadiantBlocksAtlasSprite sprite = new RadiantBlocksAtlasSprite(atlas,textureInfo,mipmapLevel,atlasWidth,atlasHeight,spriteX,spriteY,image);
        return sprite;
    }
}
