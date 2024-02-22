package com.finderfeed.solarcraft.packet_handler.packets;

import com.finderfeed.solarcraft.packet_handler.ClientPacketHandles;
import com.finderfeed.solarcraft.packet_handler.packet_system.FDPacket;
import com.finderfeed.solarcraft.packet_handler.packet_system.Packet;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

@Packet("send_delta_movement_packet")
public class SendDeltaMovementPacket extends FDPacket {

    private int eId;
    private Vec3 deltaMovement;
    public SendDeltaMovementPacket(FriendlyByteBuf buf){
        this.eId = buf.readInt();
        this.deltaMovement = new Vec3(
                buf.readDouble(),
                buf.readDouble(),
                buf.readDouble()
        );
    }

    public SendDeltaMovementPacket(int id, Vec3 deltaMovement){
        this.eId = id;
        this.deltaMovement = deltaMovement;
    }

    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeInt(eId);
        buf.writeDouble(deltaMovement.x);
        buf.writeDouble(deltaMovement.y);
        buf.writeDouble(deltaMovement.z);
    }

    @Override
    public void clientPlayHandle(PlayPayloadContext ctx) {
        super.clientPlayHandle(ctx);
        ClientPacketHandles.deltaMovementPacketHandle(eId,deltaMovement);
    }
}
