package com.finderfeed.solarforge.local_library.client.particles;


import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.particle.ParticleRenderType;

public abstract class ScreenParticle {


    private double x;
    private double y;
    private double z;

    private double xSpeed;
    private double ySpeed;
    private double zSpeed;

    private double size = 1;

    private boolean removed = false;

    private int lifetime;
    private int age = 0;

    public ScreenParticle(double x,double y,double z,double xSpeed,double ySpeed,double zSpeed) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        this.zSpeed = zSpeed;
    }

    public void tick(){
        this.x += xSpeed;
        this.y += ySpeed;
        this.z += zSpeed;
        if (age++ > lifetime) this.removed = true;
    }


    public boolean isRemoved() {
        return removed;
    }

    public int getLifetime() {
        return lifetime;
    }

    public void setLifetime(int lifetime) {
        this.lifetime = lifetime;
    }


    public void setSpeed(double x, double y, double z){
        this.xSpeed = x;
        this.ySpeed = y;
        this.zSpeed = z;
    }

    public void setPos(double x,double y,double z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public double getSize() {
        return size;
    }

    public abstract void render(VertexConsumer consumer, float partialTicks);
    public abstract ParticleRenderType getRenderType();
}
