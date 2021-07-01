package com.finderfeed.solarforge.misc_things;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public abstract class AbstractPacket {
    public AbstractPacket(PacketBuffer buf) {}
    public AbstractPacket(){}
    public abstract void toBytes(PacketBuffer buf);
    public abstract void handle(Supplier<NetworkEvent.Context> ctx);
}
