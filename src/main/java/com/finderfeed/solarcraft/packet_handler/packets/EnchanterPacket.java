package com.finderfeed.solarcraft.packet_handler.packets;

import com.finderfeed.solarcraft.content.blocks.blockentities.EnchanterBlockEntity;
import com.finderfeed.solarcraft.packet_handler.packet_system.FDPacket;
import com.finderfeed.solarcraft.packet_handler.packet_system.Packet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;
import java.util.function.Supplier;

@Packet("enchanter_packet")
public class EnchanterPacket extends FDPacket {

    private ResourceLocation location;
    private int level;
    private BlockPos enchanterPos;

    public EnchanterPacket(BlockPos pos,Enchantment e, int level){
        this.level = level;
        this.location = BuiltInRegistries.ENCHANTMENT.getKey(e);
        this.enchanterPos = pos;
    }
    @Override
    public void read(FriendlyByteBuf buf) {
        this.location = buf.readResourceLocation();
        this.level = buf.readInt();
        this.enchanterPos = buf.readBlockPos();
    }
    public void toBytes(FriendlyByteBuf buf){
        buf.writeResourceLocation(location);
        buf.writeInt(level);
        buf.writeBlockPos(enchanterPos);
    }
    public static void handle(EnchanterPacket packet,PlayPayloadContext ctx){
        Level level  = ctx.level().get();
        if (level.getBlockEntity(packet.enchanterPos) instanceof EnchanterBlockEntity enchanter){
            enchanter.triggerEnchanting(BuiltInRegistries.ENCHANTMENT.get(packet.location), packet.level);
        }
    }

    @Override
    public void serverPlayHandle(PlayPayloadContext ctx) {
        Level level  = ctx.level().get();
        if (level.getBlockEntity(enchanterPos) instanceof EnchanterBlockEntity enchanter){
            enchanter.triggerEnchanting(BuiltInRegistries.ENCHANTMENT.get(location), this.level);
        }    }

    @Override
    public void write(FriendlyByteBuf friendlyByteBuf) {
        this.toBytes(friendlyByteBuf);
    }
}
