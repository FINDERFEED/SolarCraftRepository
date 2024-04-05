package com.finderfeed.solarcraft.client.rendering.rendertypes;

import java.util.Objects;

public class BloomData {
    public float deviation;
    public float size;
    public float xscale;
    public float colorMod;
    public BloomData(float deviation,float size,float xscale,float colorMod){
        this.deviation = deviation;
        this.size = size;
        this.xscale = xscale;
        this.colorMod = colorMod;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BloomData bloomData = (BloomData) o;
        return Float.compare(deviation, bloomData.deviation) == 0 && Float.compare(size, bloomData.size) == 0 && Float.compare(xscale, bloomData.xscale) == 0 && Float.compare(colorMod, bloomData.colorMod) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(deviation, size, xscale, colorMod);
    }
}
