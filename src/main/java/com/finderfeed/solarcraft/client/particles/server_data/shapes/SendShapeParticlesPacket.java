package com.finderfeed.solarcraft.client.particles.server_data.shapes;

import com.finderfeed.solarcraft.packet_handler.ClientPacketHandles;
import com.finderfeed.solarcraft.packet_handler.packet_system.FDPacket;
import com.finderfeed.solarcraft.packet_handler.packet_system.Packet;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;
import java.util.function.Supplier;

@Packet("send_shape_particles_packet")
public class SendShapeParticlesPacket extends FDPacket {


    private ParticleSpawnShape shape;
    private ParticleOptions options;


    private double x;
    private double y;
    private double z;
    private double xd;
    private double yd;
    private double zd;
    public SendShapeParticlesPacket(ParticleSpawnShape shape, ParticleOptions options, double x,double y,double z,double xd, double yd, double zd){
        this.shape = shape;
        this.options = options;
        this.x = x;
        this.y = y;
        this.z = z;
        this.xd = xd;
        this.yd = yd;
        this.zd = zd;
    }
    public SendShapeParticlesPacket(ParticleSpawnShape shape, ParticleOptions options){
        this(shape,options,0,0,0,0,0,0);
    }

    public SendShapeParticlesPacket(FriendlyByteBuf buf) {
        String shapeId = buf.readUtf();
        ParticleSpawnShapeType type = ParticleSpawnShapeType.valueOf(shapeId);
        this.shape = type.getSerializer().fromNetwork(buf);

        ParticleType pType = buf.readById(BuiltInRegistries.PARTICLE_TYPE);
        this.options = pType.getDeserializer().fromNetwork(pType,buf);

        this.x = buf.readDouble();
        this.y = buf.readDouble();
        this.z = buf.readDouble();
        this.xd = buf.readDouble();
        this.yd = buf.readDouble();
        this.zd = buf.readDouble();
    }


    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUtf(this.shape.getType().name());
        ParticleSpawnShapeSerializer serializer = shape.getType().getSerializer();
        serializer.toBytes(shape, buf);

        buf.writeId(BuiltInRegistries.PARTICLE_TYPE,options.getType());
        options.writeToNetwork(buf);

        buf.writeDouble(x);
        buf.writeDouble(y);
        buf.writeDouble(z);
        buf.writeDouble(xd);
        buf.writeDouble(yd);
        buf.writeDouble(zd);
    }

//    public void handle(PlayPayloadContext ctx){
//
//            ClientPacketHandles.handleSpawnShapeParticlesPacket(shape,options,x,y,z,xd,yd,zd);
//
//    }

    @Override
    public void clientPlayHandle(PlayPayloadContext ctx) {
        ClientPacketHandles.handleSpawnShapeParticlesPacket(shape,options,x,y,z,xd,yd,zd);
    }

    @Override
    public void write(FriendlyByteBuf friendlyByteBuf) {
        this.toBytes(friendlyByteBuf);
    }
}
