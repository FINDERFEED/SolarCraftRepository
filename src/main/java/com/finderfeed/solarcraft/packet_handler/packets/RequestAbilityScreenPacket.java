package com.finderfeed.solarcraft.packet_handler.packets;

import com.finderfeed.solarcraft.SolarCraftTags;
import com.finderfeed.solarcraft.content.abilities.AbilityHelper;
import com.finderfeed.solarcraft.packet_handler.SCPacketHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.PlayNetworkDirection;
import net.neoforged.neoforge.network.NetworkEvent;
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

    public void handle(NetworkEvent.Context ctx) {
        ctx.enqueueWork(()->{
            ServerPlayer player = ctx.getSender();
            SCPacketHandler.INSTANCE.sendTo(new OpenAbilityScreenPacket(
                            player.getPersistentData().getInt(SolarCraftTags.RAW_SOLAR_ENERGY),
                            dontOpen,
                            AbilityHelper.getBindedAbilityID(player,1),
                            AbilityHelper.getBindedAbilityID(player,2),
                            AbilityHelper.getBindedAbilityID(player,3),
                            AbilityHelper.getBindedAbilityID(player,4)
                    )
                    ,player.connection.connection, PlayNetworkDirection.PLAY_TO_CLIENT);
        });
        ctx.setPacketHandled(true);
    }


}
