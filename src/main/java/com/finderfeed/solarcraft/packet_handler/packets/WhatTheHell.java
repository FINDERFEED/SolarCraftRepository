package com.finderfeed.solarcraft.packet_handler.packets;

import com.finderfeed.solarcraft.SolarCraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.handling.IPayloadHandler;

public record WhatTheHell(int i) implements CustomPacketPayload {
    public static final ResourceLocation TEST_ID = new ResourceLocation(SolarCraft.MOD_ID,"test");

    public WhatTheHell(FriendlyByteBuf buf){
        this(buf.readInt());
    }

    @Override
    public void write(FriendlyByteBuf friendlyByteBuf) {

    }
    @Override
    public ResourceLocation id() {
        return TEST_ID;
    }
}
