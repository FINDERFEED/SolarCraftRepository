package com.finderfeed.solarforge.packet_handler.packets;

import com.finderfeed.solarforge.SolarAbilities.SolarAbilities;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.fmllegacy.network.NetworkEvent;

import java.util.function.Supplier;

public class CastAbilityPacket {
    private final int index;
    public CastAbilityPacket(FriendlyByteBuf buf){

        index = buf.readInt();

    }
    public CastAbilityPacket(int index){
    this.index = index;
    }
    public void toBytes(FriendlyByteBuf buf){
        buf.writeInt(index);
    }
    public void handle(Supplier<NetworkEvent.Context> ctx){
        ctx.get().enqueueWork(()->{
            ServerPlayer enti = ctx.get().getSender();
            Player entity = (Player)enti;

            SolarAbilities.castAbility(enti.getLevel(),enti,enti.getPersistentData().getString("solar_forge_ability_binded_"+Integer.toString(index)));
        });
        ctx.get().setPacketHandled(true);
    }
}
