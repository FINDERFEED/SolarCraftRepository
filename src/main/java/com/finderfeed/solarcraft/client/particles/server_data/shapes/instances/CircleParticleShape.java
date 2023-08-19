package com.finderfeed.solarcraft.client.particles.server_data.shapes.instances;

import com.finderfeed.solarcraft.client.particles.server_data.shapes.ParticleSpawnShape;
import com.finderfeed.solarcraft.client.particles.server_data.shapes.ParticleSpawnShapeSerializer;
import com.finderfeed.solarcraft.client.particles.server_data.shapes.ParticleSpawnShapeType;
import com.finderfeed.solarcraft.local_library.helpers.FriendlyByteBufHelper;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class CircleParticleShape implements ParticleSpawnShape {

    public static ParticleSpawnShapeSerializer<CircleParticleShape> DESERIALIZER = new ParticleSpawnShapeSerializer<CircleParticleShape>() {
        @Override
        public void toBytes(CircleParticleShape shape, FriendlyByteBuf buf) {
            FriendlyByteBufHelper.writeVec3(buf,shape.direction);
            buf.writeDouble(shape.radius);
            buf.writeInt(shape.count);
        }

        @Override
        public CircleParticleShape fromNetwork(FriendlyByteBuf buf) {
            return new CircleParticleShape(
                    FriendlyByteBufHelper.readVec3(buf),
                    buf.readDouble(),
                    buf.readInt()
            );
        }
    };

    private Vec3 direction;
    private double radius;
    private int count;

    public CircleParticleShape(Vec3 direction,double radius,int count){
        this.direction = direction;
        this.radius = radius;
        this.count = count;
    }

    @Override
    public void placeParticles(Level level, ParticleOptions options, double posX, double posY, double posZ, double xd, double yd, double zd) {
        for (int i = 0; i < count; i++){
            float rotation = (float)(level.random.nextFloat() * Math.PI * 2);
            Vec3 c = new Vec3(radius,0,0).yRot(rotation);
            c = transformCoordinates(c);
            level.addParticle(options,posX + c.x,posY + c.y,posZ + c.z,xd,yd,zd);
        }
    }

    private Vec3 transformCoordinates(Vec3 cord){
        double angleY = Math.toDegrees(Math.atan2(direction.x,direction.z));
        double angleX = Math.toDegrees(Math.atan2(Math.sqrt(direction.x*direction.x + direction.z*direction.z),direction.y));
        return cord.yRot((float)angleY).xRot((float)angleX);
    }

    @Override
    public ParticleSpawnShapeType getType() {
        return ParticleSpawnShapeType.CIRCLE_SHAPE;
    }
}
