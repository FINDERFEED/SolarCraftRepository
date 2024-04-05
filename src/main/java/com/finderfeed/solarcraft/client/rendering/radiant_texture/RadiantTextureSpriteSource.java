package com.finderfeed.solarcraft.client.rendering.radiant_texture;

import com.finderfeed.solarcraft.SolarCraft;
import com.finderfeed.solarcraft.events.other_events.event_handler.SCClientModEventHandler;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.renderer.texture.atlas.SpriteSource;
import net.minecraft.client.renderer.texture.atlas.SpriteSourceType;
import net.minecraft.client.renderer.texture.atlas.SpriteSources;
import net.minecraft.client.renderer.texture.atlas.sources.LazyLoadedImage;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.ExtraCodecs;
import net.neoforged.neoforge.client.loading.ClientModLoader;
import org.apache.logging.log4j.Level;

import java.util.List;

public class RadiantTextureSpriteSource implements SpriteSource {

    public static final Codec<RadiantTextureSpriteSource> CODEC = RecordCodecBuilder.create(inst->inst.group(
            Codec.list(ResourceLocation.CODEC).fieldOf("textures").forGetter(s->s.textures)
            ).apply(inst,RadiantTextureSpriteSource::new)
    );

    private List<ResourceLocation> textures;

    public RadiantTextureSpriteSource(List<ResourceLocation> texture){
        this.textures = texture;
    }

    @Override
    public void run(ResourceManager manager, Output output) {
        for (ResourceLocation texture : textures) {
            var rl = TEXTURE_ID_CONVERTER.idToFile(texture);
            var opt = manager.getResource(rl);
            if (opt.isPresent()) {
                Resource r = opt.get();
                LazyLoadedImage image = new LazyLoadedImage(rl, r, 1);
                RadiantSpriteSupplier spriteSupplier = new RadiantSpriteSupplier(texture, image);
                output.add(texture, spriteSupplier);
            } else {
                SolarCraft.LOGGER.log(Level.ERROR, "Missing texture: " + texture);
            }
        }
    }

    @Override
    public SpriteSourceType type() {
        return SCClientModEventHandler.RADIANT_TEXTURE_TYPE;
    }
}
