package com.finderfeed.solarcraft.packet_handler.packets;

import com.finderfeed.solarcraft.helpers.ClientHelpers;
import com.finderfeed.solarcraft.content.items.solar_lexicon.unlockables.AncientFragmentHelper;
import com.finderfeed.solarcraft.misc_things.AbstractPacket;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;
import java.util.function.Supplier;

public class UpdateFragmentsOnClient extends AbstractPacket {

    private CompoundTag fragmentData;

    public UpdateFragmentsOnClient(Player player){
        this.fragmentData = player.getPersistentData().getCompound(AncientFragmentHelper.COMPOUND_TAG_FRAGMENTS);

    }

    public UpdateFragmentsOnClient(FriendlyByteBuf buf){
        this.fragmentData = buf.readNbt();

    }


    @Override
    public void toBytes(FriendlyByteBuf buf) {
        buf.writeNbt(fragmentData);


    }

    @Override
    public void handle(PlayPayloadContext ctx) {
        

            ClientHelpers.updatePlayerFragments(fragmentData);
        });
        
    }
}
