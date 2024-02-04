package com.finderfeed.solarcraft.packet_handler.packets;

import com.finderfeed.solarcraft.helpers.ClientHelpers;
import com.finderfeed.solarcraft.content.items.solar_lexicon.unlockables.AncientFragmentHelper;
import com.finderfeed.solarcraft.misc_things.AbstractPacket;
import com.finderfeed.solarcraft.packet_handler.packet_system.FDPacket;
import com.finderfeed.solarcraft.packet_handler.packet_system.Packet;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;
import java.util.function.Supplier;

@Packet("update_fragments_on_client")
public class UpdateFragmentsOnClient extends FDPacket {

    private CompoundTag fragmentData;

    public UpdateFragmentsOnClient(Player player){
        this.fragmentData = player.getPersistentData().getCompound(AncientFragmentHelper.COMPOUND_TAG_FRAGMENTS);

    }
    public UpdateFragmentsOnClient(FriendlyByteBuf buf) {
        this.fragmentData = buf.readNbt();
    }


    public void toBytes(FriendlyByteBuf buf) {
        buf.writeNbt(fragmentData);


    }

//    public void handle(PlayPayloadContext ctx) {
//
//
//            ClientHelpers.updatePlayerFragments(fragmentData);
//
//    }

    @Override
    public void clientPlayHandle(PlayPayloadContext ctx) {
        ClientHelpers.updatePlayerFragments(fragmentData);
    }

    @Override
    public void write(FriendlyByteBuf friendlyByteBuf) {
        this.toBytes(friendlyByteBuf);
    }
}
