package com.finderfeed.solarcraft.client.particles.server_data.shapes.instances;

import com.finderfeed.solarcraft.client.particles.server_data.shapes.ParticleSpawnShape;
import com.finderfeed.solarcraft.client.particles.server_data.shapes.ParticleSpawnShapeSerializer;
import com.finderfeed.solarcraft.client.particles.server_data.shapes.ParticleSpawnShapeType;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class SphereParticleShape implements ParticleSpawnShape {

    public static final ParticleSpawnShapeSerializer<SphereParticleShape> SERIALIZER = new ParticleSpawnShapeSerializer<SphereParticleShape>() {
        @Override
        public void toBytes(SphereParticleShape shape, FriendlyByteBuf buf) {
            buf.writeDouble(shape.startRadius);
            buf.writeDouble(shape.speed);
            buf.writeInt(shape.density);
            buf.writeBoolean(shape.randomSpeed);
        }

        @Override
        public SphereParticleShape fromNetwork(FriendlyByteBuf buf) {
            return new SphereParticleShape(
                    buf.readDouble(),
                    buf.readDouble(),
                    buf.readInt(),
                    buf.readBoolean()
            );
        }
    };

    private double startRadius;
    private double speed;

    private int density;

    private boolean randomSpeed;

    public SphereParticleShape(double startRadius,double speed,int density,boolean randomSpeed){
        this.startRadius = startRadius;
        this.speed = speed;
        this.density = density;
        this.randomSpeed = randomSpeed;
    }

    @Override
    public void placeParticles(Level level, ParticleOptions options, double posX, double posY, double posZ, double xd, double yd, double zd) {
        Vec3 center = new Vec3(
          posX,
          posY,
          posZ
        );
        for (int x = -density; x <= density;x++){
            for (int y = -density; y <= density;y++){
                for (int z = -density; z <= density;z++){
                    Vec3 add = new Vec3(x,y,z).normalize();
                    Vec3 pos = add.multiply(this.startRadius,this.startRadius,this.startRadius).add(center);
                    Vec3 speed = add.multiply(this.speed,this.speed,this.speed);
                    Vec3 rnd;
                    if (randomSpeed){
                        rnd = new Vec3(
                                (level.random.nextFloat() * speed.x * 2 - speed.x),
                                (level.random.nextFloat() * speed.y * 2 - speed.y),
                                (level.random.nextFloat() * speed.z * 2 - speed.z)
                        );
                    }else{
                        rnd = Vec3.ZERO;
                    }
                    speed = speed.add(rnd);
                    level.addParticle(options,true,
                            pos.x,pos.y,pos.z,
                            speed.x,speed.y,speed.z);
                }
            }
        }
    }

    @Override
    public ParticleSpawnShapeType getType() {
        return ParticleSpawnShapeType.SPHERE_SHAPE;
    }
}
