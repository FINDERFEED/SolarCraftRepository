package com.finderfeed.solarforge.local_library.client.particles;


import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.particle.ParticleRenderType;

public abstract class ScreenParticle {


    protected double x;
    protected double y;
    protected double xOld;
    protected double yOld;
    protected double xSpeed;
    protected double ySpeed;
    protected double xAcceleration;
    protected double yAcceleration;
    protected double size = 1;
    protected boolean removed = false;
    protected int lifetime;
    protected int age = 0;

    public ScreenParticle(int lifetime,double x,double y,double xSpeed,double ySpeed,double xAcceleration,double yAcceleration) {
        this.lifetime = lifetime;

        this.x = x;
        this.y = y;

        this.xOld = x;
        this.yOld = y;

        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        this.xAcceleration = xAcceleration;
        this.yAcceleration = yAcceleration;
    }

    public void tick(){
        this.xOld = this.x;
        this.yOld = this.y;

        this.x += xSpeed;
        this.y += ySpeed;

        this.xSpeed += this.xAcceleration;
        this.ySpeed += this.yAcceleration;

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

    }

    public void setPos(double x,double y,double z){
        this.x = x;
        this.y = y;

    }

    public void setSize(double size) {
        this.size = size;
    }

    public double getSize() {
        return size;
    }

    public abstract void render(VertexConsumer vertex, float partialTicks);
    public abstract ParticleRenderType getRenderType();
}
