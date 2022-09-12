package com.finderfeed.solarforge.packet_handler.packets;

import com.finderfeed.solarforge.ClientHelpers;
import com.finderfeed.solarforge.SolarCraftTags;
import com.finderfeed.solarforge.content.abilities.AbilityHelper;
import com.finderfeed.solarforge.packet_handler.SolarForgePacketHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.PacketDistributor;

import java.util.function.Supplier;

public class RequestAbilityScreenPacket {

    private boolean dontOpen;

    public RequestAbilityScreenPacket(boolean dontOpen) {
        this.dontOpen = dontOpen;
    }

    public RequestAbilityScreenPacket(FriendlyByteBuf buf) {
        this.dontOpen = buf.readBoolean();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeBoolean(dontOpen);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(()->{
            ServerPlayer player = ctx.get().getSender();
            SolarForgePacketHandler.INSTANCE.sendTo(new OpenAbilityScreenPacket(
                            player.getPersistentData().getInt(SolarCraftTags.RAW_SOLAR_ENERGY),
                            dontOpen,
                            AbilityHelper.getBindedAbilityID(player,1),
                            AbilityHelper.getBindedAbilityID(player,2),
                            AbilityHelper.getBindedAbilityID(player,3),
                            AbilityHelper.getBindedAbilityID(player,4)
                    )
                    ,player.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
        });
        ctx.get().setPacketHandled(true);
    }


}