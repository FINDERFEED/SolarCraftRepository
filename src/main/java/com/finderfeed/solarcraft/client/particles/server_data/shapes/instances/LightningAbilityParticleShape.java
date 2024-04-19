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
                new FDDefaultOptions(70f,6,0.3f,0.8f,1f,1f,0f,false,true),
                new FDScalingOptions(2,4),
                new AlphaInOutOptions(0,0)
        );
        level.addParticle(spark,true,posX,posY,posZ,xd,yd,zd);

        float angle = 10;
        Random random = new Random();
        for (int i = 0; i <= 360 / angle;i++){
            for (int g = 0; g < 3;g++) {
                double xdir = Math.cos(Math.toRadians((i + random.nextFloat() * 0.5f) * angle)) * 0.15;
                double zdir = Math.sin(Math.toRadians((i + random.nextFloat() * 0.5f) * angle)) * 0.15;
                ExtendedBallParticleOptions particle = new ExtendedBallParticleOptions(
                        new FDDefaultOptions(5f / g, 100, 0.2f + random.nextFloat() * 0.1f, 0.8f + random.nextFloat() * 0.1f, 1f, 1f, 1f, false, true),
                        new FDScalingOptions(0, 100),
                        new AlphaInOutOptions(0, 0)
                );


                level.addParticle(particle, true, posX, posY, posZ, xdir * g, 0, zdir * g);
            }
        }

    }

    @Override
    public ParticleSpawnShapeType getType() {
        return ParticleSpawnShapeType.LIGHTNING_ABILITY_SHAPE;
    }
}
