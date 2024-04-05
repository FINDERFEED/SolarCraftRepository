package com.finderfeed.solarcraft.client.particles.server_data.shapes.instances;

import com.finderfeed.solarcraft.client.particles.server_data.shapes.ParticleSpawnShape;
import com.finderfeed.solarcraft.client.particles.server_data.shapes.ParticleSpawnShapeSerializer;
import com.finderfeed.solarcraft.client.particles.server_data.shapes.ParticleSpawnShapeType;
import com.finderfeed.solarcraft.local_library.helpers.FriendlyByteBufHelper;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

public class BurstAttackParticleShape implements ParticleSpawnShape {

    public static ParticleSpawnShapeSerializer<BurstAttackParticleShape> SERIALIZER = new ParticleSpawnShapeSerializer<>() {
        @Override
        public void toBytes(BurstAttackParticleShape shape, FriendlyByteBuf buf) {
            FriendlyByteBufHelper.writeVec3(buf, shape.initPos);
            FriendlyByteBufHelper.writeVec3(buf, shape.endPos);
            buf.writeFloat(shape.spawnDistance);
            buf.writeInt(shape.particleCountPerDistance);
            buf.writeDouble(shape.maxSpeed);
        }

        @Override
        public BurstAttackParticleShape fromNetwork(FriendlyByteBuf buf) {
            return new BurstAttackParticleShape(
                    FriendlyByteBufHelper.readVec3(buf),
                    FriendlyByteBufHelper.readVec3(buf),
                    buf.readFloat(),
                    buf.readInt(),
                    buf.readDouble()
            );
        }
    };

    private Vec3 initPos;
    private Vec3 endPos;
    private float spawnDistance;
    private int particleCountPerDistance;
    private double maxSpeed;

    public BurstAttackParticleShape(Vec3 init,Vec3 end,float spawnDistance,int particleCountPerDistance,double maxSpeed){
        this.initPos = init;
        this.endPos = end;
        this.spawnDistance = spawnDistance;
        this.particleCountPerDistance = particleCountPerDistance;
        this.maxSpeed = maxSpeed;
    }

    @Override
    public void placeParticles(Level level, ParticleOptions options, double posX, double posY, double posZ, double xd, double yd, double zd) {
        Vec3 between = endPos.subtract(initPos);
        double len = between.length();
        for (float i = 0; i <= len; i += spawnDistance){
            double p = i / len;
            Vec3 ppos = initPos.add(between.multiply(p,p,p));
            for (int c = 0; c < this.particleCountPerDistance; c++){
                double spX = level.random.nextDouble() * maxSpeed * 2 - maxSpeed;
                double spY = level.random.nextDouble() * maxSpeed * 2 - maxSpeed;
                double spZ = level.random.nextDouble() * maxSpeed * 2 - maxSpeed;
                level.addParticle(options,ppos.x,ppos.y,ppos.z,spX,spY,spZ);
            }
        }
    }

    @Override
    public ParticleSpawnShapeType getType() {
        return ParticleSpawnShapeType.BURST_ATTACK_SHAPE;
    }

}
