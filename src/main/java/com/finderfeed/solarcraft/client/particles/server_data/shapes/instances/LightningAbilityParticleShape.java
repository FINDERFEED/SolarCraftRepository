package com.finderfeed.solarcraft.client.particles.server_data.shapes.instances;

import com.finderfeed.solarcraft.client.particles.ball_particle.BallParticleOptions;
import com.finderfeed.solarcraft.client.particles.fd_particle.AlphaInOutOptions;
import com.finderfeed.solarcraft.client.particles.fd_particle.FDDefaultOptions;
import com.finderfeed.solarcraft.client.particles.fd_particle.FDScalingOptions;
import com.finderfeed.solarcraft.client.particles.fd_particle.instances.ExtendedBallParticleOptions;
import com.finderfeed.solarcraft.client.particles.server_data.shapes.ParticleSpawnShape;
import com.finderfeed.solarcraft.client.particles.server_data.shapes.ParticleSpawnShapeSerializer;
import com.finderfeed.solarcraft.client.particles.server_data.shapes.ParticleSpawnShapeType;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;

import java.util.Random;

public class LightningAbilityParticleShape implements ParticleSpawnShape {

    public static final ParticleSpawnShapeSerializer<LightningAbilityParticleShape> DESERIALIZER = new ParticleSpawnShapeSerializer<LightningAbilityParticleShape>() {
        @Override
        public void toBytes(LightningAbilityParticleShape shape, FriendlyByteBuf buf) {

        }

        @Override
        public LightningAbilityParticleShape fromNetwork(FriendlyByteBuf buf) {
            return new LightningAbilityParticleShape();
        }
    };



    @Override
    public void placeParticles(Level level, ParticleOptions options, double posX, double posY, double posZ, double xd, double yd, double zd) {
        ExtendedBallParticleOptions spark = new ExtendedBallParticleOptions(
                new FDDefaultOptions(100f,4,0.3f,0.8f,1f,1f,0f,false,true),
                new FDScalingOptions(0,0),
                new AlphaInOutOptions(2,2)
        );
        level.addParticle(spark,true,posX,posY,posZ,xd,yd,zd);
        
        Random random = new Random();
        for (int g = 0; g < 4;g++) {
            int particles = 20 * (g + 1);
            float angle = 360f / particles;
            for (float i = 0; i <= 360f; i+=angle){
                double xdir = Math.cos(Math.toRadians((i + angle * ((random.nextFloat() - 0.5f) * 2f)))) * 0.15;
                double zdir = Math.sin(Math.toRadians((i + angle * ((random.nextFloat() - 0.5f) * 2f)))) * 0.15;
                float r = 0.2f + random.nextFloat() * 0.1f;
                float gr = 0.8f + random.nextFloat() * 0.1f;
                float b = 1f;
                float friction = 0.8f + (g+1)*0.01f;
                ExtendedBallParticleOptions particle = new ExtendedBallParticleOptions(
                        new FDDefaultOptions(5f / g, 40, r,gr,b, 1f, friction, false, true),
                        new FDScalingOptions(0, 40),
                        new AlphaInOutOptions(0, 20)
                );
                float ySpeed = g * 1f * (random.nextFloat()+0.5f);
                double xv = xdir * (g + 1) * (3 + random.nextFloat() * 3);
                double zv = zdir * (g + 1) * (3 + random.nextFloat() * 3);
                level.addParticle(particle, true, posX, posY, posZ, xv, ySpeed, zv);
            }
        }

    }

    @Override
    public ParticleSpawnShapeType getType() {
        return ParticleSpawnShapeType.LIGHTNING_ABILITY_SHAPE;
    }
}
