package com.finderfeed.solarcraft.packet_handler.packets;

import com.finderfeed.solarcraft.helpers.ClientHelpers;
import com.finderfeed.solarcraft.packet_handler.ClientPacketHandles;
import com.finderfeed.solarcraft.packet_handler.packet_system.FDPacket;
import com.finderfeed.solarcraft.packet_handler.packet_system.Packet;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.item.ItemEntity;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;
import java.util.function.Supplier;

@Packet("uodate_item_tag_in_item_entity_packet")
public class UpdateItemTagInItemEntityPacket extends FDPacket {

    public int entityId;
    public CompoundTag itemStackTag;

    public UpdateItemTagInItemEntityPacket(ItemEntity entity) {
        this.entityId = entity.getId();
        this.itemStackTag = entity.getItem().getTag();
    }


    public UpdateItemTagInItemEntityPacket(FriendlyByteBuf buf) {
        this.entityId = buf.readInt();
        this.itemStackTag = buf.readNbt();
    }

    public void toBytes(FriendlyByteBuf buf){
        buf.writeInt(entityId);
        buf.writeNbt(itemStackTag);
    }

//    public void handle(PlayPayloadContext ctx) {
//
//            ClientPacketHandles.handleUpdateItemEntityPacket(entityId,itemStackTag);
//
//
//    }

    @Override
    public void clientPlayHandle(PlayPayloadContext ctx) {
        ClientPacketHandles.handleUpdateItemEntityPacket(entityId,itemStackTag);
    }

    @Override
    public void write(FriendlyByteBuf friendlyByteBuf) {
        this.toBytes(friendlyByteBuf);
    }
}
