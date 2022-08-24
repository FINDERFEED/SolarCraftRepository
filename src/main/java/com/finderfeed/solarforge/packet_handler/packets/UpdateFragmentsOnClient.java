package com.finderfeed.solarforge.packet_handler.packets;

import com.finderfeed.solarforge.ClientHelpers;
import com.finderfeed.solarforge.content.items.solar_lexicon.unlockables.ProgressionHelper;
import com.finderfeed.solarforge.misc_things.AbstractPacket;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class UpdateFragmentsOnClient extends AbstractPacket {

    private CompoundTag fragmentData;

    public UpdateFragmentsOnClient(Player player){
        this.fragmentData = player.getPersistentData().getCompound(ProgressionHelper.COMPOUND_TAG_FRAGMENTS);

    }

    public UpdateFragmentsOnClient(FriendlyByteBuf buf){
        this.fragmentData = buf.readAnySizeNbt();

    }


    @Override
    public void toBytes(FriendlyByteBuf buf) {
        buf.writeNbt(fragmentData);


    }

    @Override
    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(()->{

            ClientHelpers.updatePlayerFragments(fragmentData);
        });
        ctx.get().setPacketHandled(true);
    }
}
