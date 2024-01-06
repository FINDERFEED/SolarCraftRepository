package com.finderfeed.solarcraft.client.rendering.radiant_texture;

import com.finderfeed.solarcraft.SolarCraft;
import com.finderfeed.solarcraft.events.other_events.event_handler.SCClientModEventHandler;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.renderer.texture.atlas.SpriteSource;
import net.minecraft.client.renderer.texture.atlas.SpriteSourceType;
import net.minecraft.client.renderer.texture.atlas.SpriteSources;
import net.minecraft.client.renderer.texture.atlas.sources.LazyLoadedImage;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.neoforged.neoforge.client.loading.ClientModLoader;
import org.apache.logging.log4j.Level;

public class RadiantTextureSpriteSource implements SpriteSource {

    public static final Codec<RadiantTextureSpriteSource> CODEC = RecordCodecBuilder.create(inst->inst.group(
            ResourceLocation.CODEC.fieldOf("texture").forGetter(s->s.texture)
            ).apply(inst,RadiantTextureSpriteSource::new)
    );

    private ResourceLocation texture;

    public RadiantTextureSpriteSource(ResourceLocation texture){
        this.texture = texture;
    }

    @Override
    public void run(ResourceManager manager, Output output) {
        var rl = TEXTURE_ID_CONVERTER.idToFile(texture);
        var opt = manager.getResource(rl);
        if (opt.isPresent()){
            Resource r = opt.get();
            LazyLoadedImage image = new LazyLoadedImage(rl,r,1);
            
        }else{
            SolarCraft.LOGGER.log(Level.ERROR,"Missing texture: " + texture);
        }
    }

    @Override
    public SpriteSourceType type() {
        return SCClientModEventHandler.RADIANT_TEXTURE_TYPE;
    }
}
