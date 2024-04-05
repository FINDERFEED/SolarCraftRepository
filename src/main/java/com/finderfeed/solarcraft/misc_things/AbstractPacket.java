package com.finderfeed.solarcraft.misc_things;

import net.minecraft.network.FriendlyByteBuf;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;
import java.util.function.Supplier;

public abstract class AbstractPacket {
    public AbstractPacket(FriendlyByteBuf buf) {}
    public AbstractPacket(){}
    public abstract void toBytes(FriendlyByteBuf buf);
    public abstract void handle(PlayPayloadContext ctx);
}
