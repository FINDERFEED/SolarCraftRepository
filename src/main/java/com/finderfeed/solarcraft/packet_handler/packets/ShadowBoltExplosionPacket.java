package com.finderfeed.solarcraft.packet_handler.packets;

import com.finderfeed.solarcraft.helpers.ClientHelpers;
import com.finderfeed.solarcraft.misc_things.AbstractPacket;
import com.finderfeed.solarcraft.packet_handler.SCPacketHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;
import net.neoforged.neoforge.network.PacketDistributor;
import java.util.function.Supplier;

public class ShadowBoltExplosionPacket extends AbstractPacket {


    public Vec3 pos;

    public ShadowBoltExplosionPacket(Vec3 pos){
        this.pos = pos;
    }

    public ShadowBoltExplosionPacket(FriendlyByteBuf buffer){
        this.pos = new Vec3(buffer.readDouble(),buffer.readDouble(),buffer.readDouble());
    }

    @Override
    public void toBytes(FriendlyByteBuf buf) {
        buf.writeDouble(pos.x);
        buf.writeDouble(pos.y);
        buf.writeDouble(pos.z);

    }

    @Override
    public void handle(PlayPayloadContext ctx) {
        
            ClientHelpers.handleShadowBoltExplosion(pos);

    }

    public static void send(Level level, Vec3 pos){
        SCPacketHandler.INSTANCE.send(PacketDistributor.NEAR.with(
                PacketDistributor.TargetPoint.p(pos.x,pos.y,pos.z,50,level.dimension())),new ShadowBoltExplosionPacket(pos));
    }
}
