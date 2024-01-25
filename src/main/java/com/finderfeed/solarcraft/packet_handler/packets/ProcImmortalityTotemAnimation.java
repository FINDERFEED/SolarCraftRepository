package com.finderfeed.solarcraft.packet_handler.packets;

import com.finderfeed.solarcraft.helpers.ClientHelpers;
import com.finderfeed.solarcraft.misc_things.AbstractPacket;
import com.finderfeed.solarcraft.packet_handler.packet_system.FDPacket;
import com.finderfeed.solarcraft.packet_handler.packet_system.Packet;
import net.minecraft.network.FriendlyByteBuf;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.DistExecutor;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;
import java.util.function.Supplier;

@Packet("proc_immortality_totem_animation_packet")
public class ProcImmortalityTotemAnimation extends FDPacket {




    @Override
    public void read(FriendlyByteBuf buf) {

    }

    public ProcImmortalityTotemAnimation(){}
    public void toBytes(FriendlyByteBuf buf) {

    }

//    @Override
//    public void handle(PlayPayloadContext ctx) {
//
//            DistExecutor.safeRunWhenOn(Dist.CLIENT,()-> ClientHelpers::playTotemAnimation);
//        }

    @Override
    public void clientPlayHandle(PlayPayloadContext ctx) {
        ClientHelpers.playTotemAnimation();
    }

    @Override
    public void write(FriendlyByteBuf friendlyByteBuf) {


    }
}
