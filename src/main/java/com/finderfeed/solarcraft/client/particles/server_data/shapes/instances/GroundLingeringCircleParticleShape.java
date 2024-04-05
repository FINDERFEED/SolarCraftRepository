package com.finderfeed.solarcraft.client.particles.server_data.shapes.instances;

import com.finderfeed.solarcraft.client.particles.server_data.shapes.ParticleSpawnShape;
import com.finderfeed.solarcraft.client.particles.server_data.shapes.ParticleSpawnShapeSerializer;
import com.finderfeed.solarcraft.client.particles.server_data.shapes.ParticleSpawnShapeType;
import com.finderfeed.solarcraft.local_library.helpers.FriendlyByteBufHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;

public class GroundLingeringCircleParticleShape implements ParticleSpawnShape {
    public static ParticleSpawnShapeSerializer<GroundLingeringCircleParticleShape> DESERIALIZER = new ParticleSpawnShapeSerializer<GroundLingeringCircleParticleShape>() {
        @Override
        public void toBytes(GroundLingeringCircleParticleShape shape, FriendlyByteBuf buf) {
            buf.writeDouble(shape.radius);
            buf.writeInt(shape.count);
        }

        @Override
        public GroundLingeringCircleParticleShape fromNetwork(FriendlyByteBuf buf) {
            return new GroundLingeringCircleParticleShape(
                    buf.readDouble(),
                    buf.readInt()
            );
        }
    };

    private double radius;
    private int count;

    public GroundLingeringCircleParticleShape(double radius,int count){
        this.radius = radius;
        this.count = count;
    }

    @Override
    public void placeParticles(Level level, ParticleOptions options, double posX, double posY, double posZ, double xd, double yd, double zd) {
        for (int i = 0; i < count; i++){
            float rotation = (float)(level.random.nextFloat() * Math.PI * 2);
            Vec3 c = new Vec3(radius*level.random.nextFloat(),0,0).yRot(rotation);
            double px = posX + c.x;
            double pz = posZ + c.z;
            int h = level.getHeight(Heightmap.Types.WORLD_SURFACE,(int)px,(int)pz) + 1;
            level.addParticle(options,px,h,pz,xd,yd,zd);
        }
    }

    @Override
    public ParticleSpawnShapeType getType() {
        return ParticleSpawnShapeType.GROUND_LINGERING_CIRCLE_PARTICLE_SHAPE;
    }
}
