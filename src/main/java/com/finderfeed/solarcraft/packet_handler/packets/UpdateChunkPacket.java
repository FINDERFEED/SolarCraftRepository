package com.finderfeed.solarcraft.packet_handler.packets;

import com.finderfeed.solarcraft.helpers.ClientHelpers;
import com.finderfeed.solarcraft.misc_things.RunicEnergy;
import com.finderfeed.solarcraft.packet_handler.ClientPacketHandles;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.game.ClientboundLevelChunkPacketData;
import net.minecraft.network.protocol.game.ClientboundLightUpdatePacketData;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.LevelChunk;
import net.neoforged.neoforge.network.NetworkEvent;
import java.util.function.Supplier;

public class UpdateChunkPacket {


    private final int x;
    private final int z;

    private final ClientboundLevelChunkPacketData chunkData;

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


    public void handle(NetworkEvent.Context ctx) {
        ctx.enqueueWork(()->{
            ClientPacketHandles.handleUpdateChunkPacket(x,z,chunkData);
        });
        ctx.setPacketHandled(true);
    }
}
