package com.finderfeed.solarforge.client.particles;


import com.finderfeed.solarforge.local_library.helpers.RenderingTools;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

public class SolarExplosionParticle extends TextureSheetParticle {

    public SolarExplosionParticle(ClientLevel p_i232448_1_, double p_i232448_2_, double p_i232448_4_, double p_i232448_6_, double x, double y, double z) {
        super(p_i232448_1_, p_i232448_2_, p_i232448_4_, p_i232448_6_, x,y,z);
        this.rCol = 255;
        this.gCol = 255;
        this.bCol = 255;
        this.xd =x;
        this.yd =y;
        this.zd =z;
        this.x = p_i232448_2_;
        this.y = p_i232448_4_;
        this.z = p_i232448_6_;
        this.lifetime = 10 + (int)(p_i232448_1_.random.nextFloat()*6);
        this.quadSize = 0.5f;
    }

    @Override
    public void render(VertexConsumer vertex, Camera camera, float pticks) {
        PoseStack matrices = new PoseStack();
        matrices.pushPose();
        Vec3 vec3 = camera.getPosition();
        float x = (float)(Mth.lerp((double)pticks, this.xo, this.x) - vec3.x());
        float y = (float)(Mth.lerp((double)pticks, this.yo, this.y) - vec3.y());
        float z = (float)(Mth.lerp((double)pticks, this.zo, this.z) - vec3.z());
        matrices.translate(x,y,z);
        RenderingTools.applyMovementMatrixRotations(matrices,new Vec3(this.xd,this.yd,this.zd));
        Matrix4f mat = matrices.last().pose();

        float u0 = getU0();
        float u1 = getU1();
        float v0 = getV0();
        float v1 = getV1();

        RenderingTools.coloredBasicVertexNoOverlay(mat,vertex,-0.5,1,0,u0,v1,(int)this.rCol,(int)this.gCol, (int) this.bCol,(int)255);
        RenderingTools.coloredBasicVertexNoOverlay(mat,vertex,0.5,1,0,u1,v1,(int)this.rCol,(int)this.gCol, (int) this.bCol,(int)255);
        RenderingTools.coloredBasicVertexNoOverlay(mat,vertex,0.5,0,0,u1,v0,(int)this.rCol,(int)this.gCol, (int) this.bCol,(int)255);
        RenderingTools.coloredBasicVertexNoOverlay(mat,vertex,-0.5,0,0,u0,v0,(int)this.rCol,(int)this.gCol, (int) this.bCol,(int)255);

        RenderingTools.coloredBasicVertexNoOverlay(mat,vertex,-0.5,0,0,u0,v0,(int)this.rCol,(int)this.gCol, (int) this.bCol,(int)255);
        RenderingTools.coloredBasicVertexNoOverlay(mat,vertex,0.5,0,0,u1,v0,(int)this.rCol,(int)this.gCol, (int) this.bCol,(int)255);
        RenderingTools.coloredBasicVertexNoOverlay(mat,vertex,0.5,1,0,u1,v1,(int)this.rCol,(int)this.gCol, (int) this.bCol,(int)255);
        RenderingTools.coloredBasicVertexNoOverlay(mat,vertex,-0.5,1,0,u0,v1,(int)this.rCol,(int)this.gCol, (int) this.bCol,(int)255);
        matrices.popPose();
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    public static class Factory implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSetl;
        public Factory(SpriteSet sprite){
            this.spriteSetl = sprite;
        }
        @Nullable
        @Override
        public Particle createParticle(SimpleParticleType type, ClientLevel world, double x, double y, double z, double xv, double yv, double zv) {
            SolarExplosionParticle particle = new SolarExplosionParticle(world,x,y,z,xv,yv,zv);

            particle.pickSprite(this.spriteSetl);
            return particle;
        }
    }
}
