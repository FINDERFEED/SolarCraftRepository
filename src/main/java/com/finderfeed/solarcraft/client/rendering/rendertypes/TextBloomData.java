package com.finderfeed.solarcraft.client.rendering.rendertypes;

import net.minecraft.resources.ResourceLocation;

import java.util.Objects;

public class TextBloomData extends BloomData {

    public ResourceLocation tex;
    public TextBloomData(ResourceLocation tex,float deviation, float size, float xscale, float colorMod) {
        super(deviation, size, xscale, colorMod);
        this.tex = tex;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        TextBloomData that = (TextBloomData) o;
        return Objects.equals(tex, that.tex);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), tex);
    }
}
