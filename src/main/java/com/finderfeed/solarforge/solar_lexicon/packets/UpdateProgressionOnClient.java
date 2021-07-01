package com.finderfeed.solarforge.solar_lexicon.packets;

import com.finderfeed.solarforge.Helpers;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class UpdateProgressionOnClient {

    public final String id;
    public final boolean state;

    public UpdateProgressionOnClient(PacketBuffer buf){
        this.id = buf.readUtf();
        this.state = buf.readBoolean();
    }
    public UpdateProgressionOnClient(String s,boolean state){
        this.id = s;
        this.state = state;
    }
    public void toBytes(PacketBuffer buf){
        buf.writeUtf(id);
        buf.writeBoolean(state);
    }
    public void handle(Supplier<NetworkEvent.Context> ctx){
        ctx.get().enqueueWork(()->{


            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> ()-> {
                ClientPlayerEntity clEntity = Minecraft.getInstance().player;

                clEntity.getPersistentData().putBoolean(Helpers.PROGRESSION+id,state);

            });

        });
        ctx.get().setPacketHandled(true);
    }
}
