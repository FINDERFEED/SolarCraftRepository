package com.finderfeed.solarforge.client.particles;


import com.finderfeed.solarforge.for_future_library.helpers.RenderingTools;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
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
        this.lifetime = 60 + (int)(p_i232448_1_.random.nextFloat()*6);
        this.quadSize = 0.5f;
    }

    @Override
    public void render(VertexConsumer vertex, Camera camera, float pticks) {
        PoseStack matrices = new PoseStack();
        matrices.pushPose();
        matrices.translate(this.x,this.y,this.z);
        RenderingTools.applyMovementMatrixRotations(matrices,new Vec3(this.xd,this.yd,this.zd));
        Matrix4f mat = matrices.last().pose();
        RenderingTools.coloredBasicVertexNoOverlay(mat,vertex,-0.5,1,0,0,1,(int)this.rCol,(int)this.gCol, (int) this.bCol,(int)255);
        RenderingTools.coloredBasicVertexNoOverlay(mat,vertex,0.5,1,0,1,1,(int)this.rCol,(int)this.gCol, (int) this.bCol,(int)255);
        RenderingTools.coloredBasicVertexNoOverlay(mat,vertex,0.5,0,0,1,0,(int)this.rCol,(int)this.gCol, (int) this.bCol,(int)255);
        RenderingTools.coloredBasicVertexNoOverlay(mat,vertex,-0.5,0,0,0,0,(int)this.rCol,(int)this.gCol, (int) this.bCol,(int)255);

        RenderingTools.coloredBasicVertexNoOverlay(mat,vertex,-0.5,0,0,0,0,(int)this.rCol,(int)this.gCol, (int) this.bCol,(int)255);
        RenderingTools.coloredBasicVertexNoOverlay(mat,vertex,0.5,0,0,1,0,(int)this.rCol,(int)this.gCol, (int) this.bCol,(int)255);
        RenderingTools.coloredBasicVertexNoOverlay(mat,vertex,0.5,1,0,1,1,(int)this.rCol,(int)this.gCol, (int) this.bCol,(int)255);
        RenderingTools.coloredBasicVertexNoOverlay(mat,vertex,-0.5,1,0,0,1,(int)this.rCol,(int)this.gCol, (int) this.bCol,(int)255);
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
            particle.setColor(1,1,0);

            particle.pickSprite(this.spriteSetl);
            return particle;
        }
    }
}
