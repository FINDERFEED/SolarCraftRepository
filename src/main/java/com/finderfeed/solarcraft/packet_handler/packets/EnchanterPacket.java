package com.finderfeed.solarcraft.packet_handler.packets;

import com.finderfeed.solarcraft.content.blocks.blockentities.EnchanterBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.enchantment.Enchantment;
import net.neoforged.neoforge.network.NetworkEvent;
import java.util.function.Supplier;

public class EnchanterPacket {

    private ResourceLocation location;
    private int level;
    private BlockPos enchanterPos;

    public EnchanterPacket(FriendlyByteBuf buf){
        this.location = buf.readResourceLocation();
        this.level = buf.readInt();
        this.enchanterPos = buf.readBlockPos();
    }
    public EnchanterPacket(BlockPos pos,Enchantment e, int level){
        this.level = level;
        this.location = BuiltInRegistries.ENCHANTMENT.getKey(e);
        this.enchanterPos = pos;
    }
    public void toBytes(FriendlyByteBuf buf){
        buf.writeResourceLocation(location);
        buf.writeInt(level);
        buf.writeBlockPos(enchanterPos);
    }
    public void handle(Supplier<NetworkEvent.Context> ctx){
        ctx.get().enqueueWork(()->{
            ServerPlayer sender  = ctx.get().getSender();
            if (sender.level().getBlockEntity(enchanterPos) instanceof EnchanterBlockEntity enchanter){
                enchanter.triggerEnchanting(BuiltInRegistries.ENCHANTMENT.get(location),level);
            }

         });
        ctx.get().setPacketHandled(true);
    }
}
