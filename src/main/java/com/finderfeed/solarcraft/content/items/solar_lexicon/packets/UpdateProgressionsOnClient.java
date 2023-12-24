package com.finderfeed.solarcraft.content.items.solar_lexicon.packets;

import com.finderfeed.solarcraft.content.items.solar_lexicon.progressions.Progression;
import com.finderfeed.solarcraft.helpers.Helpers;
import com.finderfeed.solarcraft.packet_handler.ClientPacketHandles;
import com.finderfeed.solarcraft.packet_handler.SCPacketHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.PlayNetworkDirection;
import net.neoforged.neoforge.network.NetworkEvent;
import java.util.function.Supplier;

public class UpdateProgressionsOnClient {

    private CompoundTag progressionData;



    public UpdateProgressionsOnClient(Player player){
        progressionData = new CompoundTag();
        for (Progression progression : Progression.allProgressions){
            progressionData.putBoolean(progression.getProgressionCode(),Helpers.hasPlayerCompletedProgression(progression,player));
        }
    }


    public UpdateProgressionsOnClient(FriendlyByteBuf buf){
        this.progressionData = buf.readAnySizeNbt();
    }

    public void toBytes(FriendlyByteBuf buf){
        buf.writeNbt(progressionData);
    }
    public void handle(NetworkEvent.Context ctx){
        ctx.enqueueWork(()->{
            ClientPacketHandles.handleProgressionUpdate(progressionData);
        });
        ctx.setPacketHandled(true);
    }

    public static void send(ServerPlayer player){
        SCPacketHandler.INSTANCE.sendTo(new UpdateProgressionsOnClient(player),player.connection.connection, PlayNetworkDirection.PLAY_TO_CLIENT);
    }
}
