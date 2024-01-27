package com.finderfeed.solarcraft.packet_handler.packets;

import com.finderfeed.solarcraft.helpers.ClientHelpers;
import com.finderfeed.solarcraft.misc_things.RunicEnergy;
import com.finderfeed.solarcraft.packet_handler.ClientPacketHandles;
import com.finderfeed.solarcraft.packet_handler.packet_system.FDPacket;
import com.finderfeed.solarcraft.packet_handler.packet_system.Packet;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.game.ClientboundLevelChunkPacketData;
import net.minecraft.network.protocol.game.ClientboundLightUpdatePacketData;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.LevelChunk;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;
import java.util.function.Supplier;

@Packet("update_chunk_packet")
public class UpdateChunkPacket extends FDPacket {


    private int x;
    private int z;

    private ClientboundLevelChunkPacketData chunkData;

    public UpdateChunkPacket(LevelChunk chunk){
        ChunkPos chunkpos = chunk.getPos();
        this.x = chunkpos.x;
        this.z = chunkpos.z;
        this.chunkData = new ClientboundLevelChunkPacketData(chunk);
    }

    public UpdateChunkPacket(FriendlyByteBuf buf) {
        this.x = buf.readInt();
        this.z = buf.readInt();
        this.chunkData = new ClientboundLevelChunkPacketData(buf, this.x, this.z);
    }

    public void toBytes(FriendlyByteBuf buf){
        buf.writeInt(x);
        buf.writeInt(z);
        chunkData.write(buf);
    }


//    public void handle(PlayPayloadContext ctx) {
//
//            ClientPacketHandles.handleUpdateChunkPacket(x,z,chunkData);
//
//    }

    @Override
    public void clientPlayHandle(PlayPayloadContext ctx) {
        ClientPacketHandles.handleUpdateChunkPacket(x,z,chunkData);
    }

    @Override
    public void write(FriendlyByteBuf friendlyByteBuf) {
        this.toBytes(friendlyByteBuf);
    }
}
