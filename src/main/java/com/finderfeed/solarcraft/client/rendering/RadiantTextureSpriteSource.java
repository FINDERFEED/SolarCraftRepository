package com.finderfeed.solarcraft.client.rendering;

import com.finderfeed.solarcraft.SolarCraft;
import net.minecraft.client.renderer.texture.atlas.SpriteSource;
import net.minecraft.client.renderer.texture.atlas.SpriteSourceType;
import net.minecraft.client.renderer.texture.atlas.SpriteSources;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;

public class RadiantTextureSpriteSource implements SpriteSource {



    @Override
    public void run(ResourceManager manager, Output output) {

    }

    @Override
    public SpriteSourceType type() {
        return SpriteSources.SINGLE_FILE;
    }
}
