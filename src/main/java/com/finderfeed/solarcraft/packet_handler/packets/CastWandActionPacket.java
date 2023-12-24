package com.finderfeed.solarcraft.packet_handler.packets;

import com.finderfeed.solarcraft.content.items.solar_wand.SolarWandItem;
import com.finderfeed.solarcraft.registries.items.SCItems;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.NetworkEvent;
import java.util.function.Supplier;

public class CastWandActionPacket {

    private ResourceLocation actionId;

    public CastWandActionPacket(ResourceLocation actionId){
        this.actionId = actionId;
    }

    public CastWandActionPacket(FriendlyByteBuf buf){
        this.actionId = buf.readResourceLocation();
    }

    public void toBytes(FriendlyByteBuf buf){
        buf.writeResourceLocation(actionId);
    }

    public void handle(NetworkEvent.Context ctx){
        ctx.enqueueWork(()->{
            ServerPlayer player = ctx.getSender();
            ItemStack stack = player.getMainHandItem();
            if (stack.is(SCItems.SOLAR_WAND.get())){
                SolarWandItem.setWandAction(stack,actionId);
            }
        });
        ctx.setPacketHandled(true);
    }
}
