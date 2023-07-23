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
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;


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
    public void handle(Supplier<NetworkEvent.Context> ctx){
        ctx.get().enqueueWork(()->{
            ClientPacketHandles.handleProgressionUpdate(progressionData);
        });
        ctx.get().setPacketHandled(true);
    }

    public static void send(ServerPlayer player){
        SCPacketHandler.INSTANCE.sendTo(new UpdateProgressionsOnClient(player),player.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
    }
}
