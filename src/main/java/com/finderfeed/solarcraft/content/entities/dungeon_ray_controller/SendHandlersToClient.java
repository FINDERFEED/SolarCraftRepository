package com.finderfeed.solarcraft.content.entities.dungeon_ray_controller;

import com.finderfeed.solarcraft.packet_handler.ClientPacketHandles;
import com.finderfeed.solarcraft.packet_handler.packet_system.FDPacket;
import com.finderfeed.solarcraft.packet_handler.packet_system.Packet;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

import java.util.List;

@Packet("debug_send_handlers")
public class SendHandlersToClient extends FDPacket {

    private int controllerEntity;
    private List<DungeonRayHandler> handlers;

    public SendHandlersToClient(DungeonRayController controller){
        this.controllerEntity = controller.getId();
        this.handlers = controller.getHandlers();
    }

    public SendHandlersToClient(FriendlyByteBuf buf){
        int id = buf.readInt();
        CompoundTag tag = buf.readNbt();
        List<DungeonRayHandler> handlers = DungeonRayController.handlersFromTag(tag);
        this.handlers = handlers;
        this.controllerEntity = id;
    }

    @Override
    public void write(FriendlyByteBuf buf) {
        CompoundTag tag = new CompoundTag();
        DungeonRayController.handlersToTag(handlers,tag);
        buf.writeInt(controllerEntity);
        buf.writeNbt(tag);
    }

    @Override
    public void clientPlayHandle(PlayPayloadContext ctx) {
        ClientPacketHandles.sendHandlersToClientPacketHandle(controllerEntity,handlers);
    }
}
