package com.finderfeed.solarcraft.local_library;

import net.minecraft.network.FriendlyByteBuf;

public interface NetworkSerializable {

    void toNetwork(FriendlyByteBuf buf);

    void fromNetwork(FriendlyByteBuf buf);

}
