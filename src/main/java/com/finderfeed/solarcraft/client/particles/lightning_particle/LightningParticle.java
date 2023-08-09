package com.finderfeed.solarcraft.client.particles.lightning_particle;

import com.finderfeed.solarcraft.local_library.helpers.RenderingTools;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class LightningParticle extends Particle {

    private LightningParticleOptions options;

    private float particleRoll;
    public LightningParticle(LightningParticleOptions options,ClientLevel level, double x, double y, double z, double xd, double yd, double zd) {
        super(level, x, y, z, xd, yd, zd);
        this.x = x;
        this.y = y;
        this.z = z;
        this.xd = xd;
        this.yd = yd;
        this.zd = zd;
        this.options = options;
        this.lifetime = options.getLifetime();
        if (level != null){
            this.particleRoll = level.random.nextFloat() * 360;
        }else{
            this.particleRoll = 0;
        }
    }

    @Override
    public void render(VertexConsumer vertex, Camera camera, float pticks) {
        if (Minecraft.getInstance().level == null) return;

        float size = options.getQuadSize();


        Vec3 initPos = new Vec3(-size/2,0,0);
        Vec3 endPos = new Vec3(size/2,0,0);

        PoseStack matrices = new PoseStack();
        matrices.pushPose();

        Vec3 vec3 = camera.getPosition();
        float f = (float)(Mth.lerp(pticks, this.xo, this.x) - vec3.x());
        float f1 = (float)(Mth.lerp(pticks, this.yo, this.y) - vec3.y());
        float f2 = (float)(Mth.lerp(pticks, this.zo, this.z) - vec3.z());

        matrices.translate(f,f1,f2);

        matrices.mulPose(camera.rotation());

        matrices.mulPose(RenderingTools.rotationDegrees(RenderingTools.ZP(),this.particleRoll));

        Random rnd = new Random(
                level.getGameTime()*3413 + options.getSeed()
        );


        RenderingTools.Lightning2DRenderer.renderLightning(matrices,vertex,
                options.getBreaksCount(),size/2f,size/8f,
                initPos,endPos,rnd,
                options.getR()/255f,
                options.getG()/255f,
                options.getB()/255f
        );


        matrices.popPose();

    }

    @Override
    public ParticleRenderType getRenderType() {
        return RENDER_TYPE;
    }


    private static final ParticleRenderType RENDER_TYPE = new ParticleRenderType() {
        @Override
        public void begin(BufferBuilder builder, TextureManager manager) {
            RenderType type = RenderType.lightning();
            type.setupRenderState();
            builder.begin(type.mode(),type.format());
        }

        @Override
        public void end(Tesselator end) {
            end.end();
            RenderType.lightning().clearRenderState();
        }
    };


    public static class Provider implements ParticleProvider<LightningParticleOptions>{
        @Nullable
        @Override
        public Particle createParticle(LightningParticleOptions options, ClientLevel level, double x, double y, double z,double xd, double yd, double zd) {
            return new LightningParticle(options,level,x,y,z,xd,yd,zd);
        }
    }

}
