package com.finderfeed.solarcraft.packet_handler.packets;

import com.finderfeed.solarcraft.helpers.ClientHelpers;
import com.finderfeed.solarcraft.packet_handler.ClientPacketHandles;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.item.ItemEntity;
import net.neoforged.neoforge.network.NetworkEvent;
import java.util.function.Supplier;

public class UpdateItemTagInItemEntityPacket {

    public int entityId;
    public CompoundTag itemStackTag;

    public UpdateItemTagInItemEntityPacket(ItemEntity entity) {
        this.entityId = entity.getId();
        this.itemStackTag = entity.getItem().getTag();
    }


    public UpdateItemTagInItemEntityPacket(FriendlyByteBuf buf){
        this.entityId = buf.readInt();
        this.itemStackTag = buf.readNbt();
    }

    public void toBytes(FriendlyByteBuf buf){
        buf.writeInt(entityId);
        buf.writeNbt(itemStackTag);
    }

    public void handle(NetworkEvent.Context ctx) {
        ctx.enqueueWork(() -> {
            ClientPacketHandles.handleUpdateItemEntityPacket(entityId,itemStackTag);
        });
        ctx.setPacketHandled(true);
    }

}
