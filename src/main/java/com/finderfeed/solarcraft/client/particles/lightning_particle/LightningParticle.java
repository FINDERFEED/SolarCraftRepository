package com.finderfeed.solarcraft.client.particles.lightning_particle;

import com.finderfeed.solarcraft.SolarCraft;
import com.finderfeed.solarcraft.client.rendering.rendertypes.SolarCraftRenderTypes;
import com.finderfeed.solarcraft.local_library.helpers.RenderingTools;
import com.mojang.blaze3d.systems.RenderSystem;
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
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class LightningParticle extends Particle {

    private LightningParticleOptions options;

    private float particleRoll;

    private int seed;
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
            if (options.getSeed() == -1){
                seed = level.random.nextInt();
            }
        }else{
            this.particleRoll = 0;
        }
        this.hasPhysics = options.hasPhysics();

    }

    @Override
    public void render(VertexConsumer vertex, Camera camera, float pticks) {
        RenderLightningParticles.particles.add(this);
    }

    private void manualRender(PoseStack matrices,VertexConsumer vertex, Camera camera, float pticks){
        if (Minecraft.getInstance().level == null) return;


        matrices.pushPose();

        Vec3 vec3 = camera.getPosition();
        float f = (float)(Mth.lerp(pticks, this.xo, this.x) - vec3.x());
        float f1 = (float)(Mth.lerp(pticks, this.yo, this.y) - vec3.y());
        float f2 = (float)(Mth.lerp(pticks, this.zo, this.z) - vec3.z());

        matrices.translate(f,f1,f2);

        matrices.mulPose(camera.rotation());

        matrices.mulPose(RenderingTools.rotationDegrees(RenderingTools.ZP(),this.particleRoll));

        int lightningLength = 3;
        matrices.scale(
                options.getQuadSize(),
                options.getQuadSize(),
                options.getQuadSize()
        );
        matrices.translate(-lightningLength*0.5f*0.1f,0,0);

        Random rnd = new Random(
                level.getGameTime()* 42442 + seed
        );

        float a = Mth.clamp(this.age / (float)this.lifetime,0,1f);

        a =  -(float)Math.pow(a,4) + 1;

        render(lightningLength,matrices.last().pose(),vertex,rnd,0,
                options.getR()/255f,
                options.getG()/255f,
                options.getB()/255f
                ,a);

        matrices.popPose();
    }


    private void render(int length,Matrix4f m,VertexConsumer vertex,Random random,float zoffset,float r,float g,float b,float a){
        int count = length;
        float prevY = 0;
        float prevX = 0;
        for (int i = 0; i < count; i++){
            int partsCount = 0;
            if (i > 0){
                partsCount = random.nextInt(3);
            }
            float y = random.nextFloat()*0.2f - 0.1f;
            quad(m,vertex,prevX,prevY,prevX + 0.1f,y,0,r,g,b,a);

            renderLightningPart(random,m,vertex,prevX,prevY,zoffset,r,g,b,a,new AtomicInteger(partsCount));

            prevX += 0.1f;
            prevY = y;
        }
    }

    private void renderLightningPart(Random random,Matrix4f m, VertexConsumer v, float x, float y,float zoffset,float r,float g,float b,float a, AtomicInteger remaining){
        int c = remaining.get();
        if (c <= 0) return;
        float yoffs = random.nextFloat()*0.2f - 0.1f;
        quad(m,v,x,y,x + 0.1f,y + yoffs,zoffset,r,g,b,a);
        int parts = random.nextInt(c) + 1;
        remaining.set(c - parts);
        for (int i = 0; i < parts;i++){
            renderLightningPart(random,m,v,x + 0.1f,y + yoffs,zoffset,r,g,b,a,remaining);
        }
    }


    private void quad(Matrix4f m,VertexConsumer vertex, float x1, float y1, float x2, float y2, float zoffset, float r, float g, float b,float a){
        vertex.vertex(m,x1 ,y1 + 0.01f,zoffset).color(r,g,b,a).endVertex();
        vertex.vertex(m,x2 ,y2 + 0.01f,zoffset).color(r,g,b,a).endVertex();
        vertex.vertex(m,x2 ,y2 - 0.01f,zoffset).color(r,g,b,a).endVertex();
        vertex.vertex(m,x1 ,y1 - 0.01f,zoffset).color(r,g,b,a).endVertex();


        vertex.vertex(m,x1 ,y1 + 0.0025f,zoffset - 0.001f).color(1f,1f,1f,a).endVertex();
        vertex.vertex(m,x2 ,y2 + 0.0025f,zoffset - 0.001f).color(1f,1f,1f,a).endVertex();
        vertex.vertex(m,x2 ,y2 - 0.0025f,zoffset - 0.001f).color(1f,1f,1f,a).endVertex();
        vertex.vertex(m,x1 ,y1 - 0.0025f,zoffset - 0.001f).color(1f,1f,1f,a).endVertex();




    }


    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }


    private static final ParticleRenderType RENDER_TYPE = new ParticleRenderType() {
        @Override
        public void begin(BufferBuilder builder, TextureManager manager) {
            RenderType type = SolarCraftRenderTypes.lightning();
            type.setupRenderState();
            builder.begin(type.mode(),type.format());
        }

        @Override
        public void end(Tesselator end) {
            end.end();
            RenderType type = SolarCraftRenderTypes.lightning();
            type.clearRenderState();

        }
    };


    public static class Provider implements ParticleProvider<LightningParticleOptions>{
        @Nullable
        @Override
        public Particle createParticle(LightningParticleOptions options, ClientLevel level, double x, double y, double z,double xd, double yd, double zd) {
            return new LightningParticle(options,level,x,y,z,xd,yd,zd);
        }
    }

    @Mod.EventBusSubscriber(modid = SolarCraft.MOD_ID,bus = Mod.EventBusSubscriber.Bus.FORGE,value = Dist.CLIENT)
    public static class RenderLightningParticles{


        private static final List<LightningParticle> particles = new ArrayList<>();

        @SubscribeEvent
        public static void renderLightningParticles(RenderLevelStageEvent event){
            if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_TRANSLUCENT_BLOCKS) return;
            if (!particles.isEmpty()) {

                PoseStack stack = event.getPoseStack();


                PoseStack posestack = RenderSystem.getModelViewStack();
                posestack.pushPose();
                posestack.mulPoseMatrix(stack.last().pose());
                RenderSystem.applyModelViewMatrix();


                BufferBuilder bufferBuilder = Tesselator.getInstance().getBuilder();
                RENDER_TYPE.begin(bufferBuilder, Minecraft.getInstance().textureManager);
                Frustum frustum = event.getFrustum();
                for (LightningParticle particle : particles) {
                    if (frustum.isVisible(particle.getBoundingBox())) {
                        particle.manualRender(new PoseStack(), bufferBuilder, event.getCamera(), event.getPartialTick());
                    }
                }
                RENDER_TYPE.end(Tesselator.getInstance());
                particles.clear();

                posestack.popPose();

                RenderSystem.applyModelViewMatrix();
            }
        }

    }

}
