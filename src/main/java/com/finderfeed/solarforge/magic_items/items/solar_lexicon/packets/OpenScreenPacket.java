package com.finderfeed.solarforge.magic_items.items.solar_lexicon.packets;

import com.finderfeed.solarforge.misc_things.AbstractPacket;
import com.finderfeed.solarforge.magic_items.items.solar_lexicon.screen.InvokeScreenTest;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class OpenScreenPacket extends AbstractPacket {
    public OpenScreenPacket(FriendlyByteBuf buf){

    }
    public OpenScreenPacket(){

    }
    @Override
    public void toBytes(FriendlyByteBuf buf) {

    }

    @Override
    public void handle(Supplier<NetworkEvent.Context> ctx) {

        ctx.get().enqueueWork(InvokeScreenTest::openScreen);
    ctx.get().setPacketHandled(true);
    }
}
