package com.finderfeed.solarcraft.packet_handler.packets;

import com.finderfeed.solarcraft.helpers.ClientHelpers;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraftforge.network.NetworkEvent;

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
        this.itemStackTag = buf.readAnySizeNbt();
    }

    public void toBytes(FriendlyByteBuf buf){
        buf.writeInt(entityId);
        buf.writeNbt(itemStackTag);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ClientHelpers.handleUpdateItemEntityPacket(entityId,itemStackTag);
        });
        ctx.get().setPacketHandled(true);
    }

}
