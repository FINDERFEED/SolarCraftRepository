package com.finderfeed.solarforge.client.particles;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.TextureSheetParticle;

public abstract class SolarcraftParticle extends TextureSheetParticle {

    protected float maxSize;

    protected SolarcraftParticle(ClientLevel p_108328_, double p_108329_, double p_108330_, double p_108331_, double p_108332_, double p_108333_, double p_108334_,float maxSize) {
        super(p_108328_, p_108329_, p_108330_, p_108331_, p_108332_, p_108333_, p_108334_);
        this.maxSize = maxSize;
        this.quadSize = maxSize;

    }

    public void setQuadSize(float size){
        this.quadSize = size;
    }

    public void setMaxSize(float maxSize) {
        this.maxSize = maxSize;
        this.quadSize = maxSize;
    }

    public float getMaxSize() {
        return maxSize;
    }


}
