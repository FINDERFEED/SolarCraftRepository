package com.finderfeed.solarcraft.packet_handler.packets;

import com.finderfeed.solarcraft.content.items.solar_lexicon.progressions.Progression;
import com.finderfeed.solarcraft.helpers.ClientHelpers;
import com.finderfeed.solarcraft.client.toasts.ProgressionToast;
import com.finderfeed.solarcraft.packet_handler.ClientPacketHandles;
import com.finderfeed.solarcraft.packet_handler.packet_system.FDPacket;
import com.finderfeed.solarcraft.packet_handler.packet_system.Packet;
import com.finderfeed.solarcraft.registries.sounds.SCSounds;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.DistExecutor;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

@Packet("trigger_toast_packet")
public class TriggerToastPacket extends FDPacket {

    public String id;

    public TriggerToastPacket(FriendlyByteBuf buf) {
        this.id = buf.readUtf();

    }

    public TriggerToastPacket(String progressionId){
        this.id = progressionId;
    }
    public void toBytes(FriendlyByteBuf buf){
        buf.writeUtf(id);
    }
//    public void handle(PlayPayloadContext ctx){
//
//            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> ()-> {
//
//                ClientHelpers.playSound(SCSounds.PROGRESSION_GAIN.get(),1,1);
//                ProgressionToast.addOrUpdate(Minecraft.getInstance().getToasts(), Progression.getProgressionByName(id));
//            });
//
//    }


    @Override
    public void clientPlayHandle(PlayPayloadContext ctx) {
        ClientPacketHandles.handleTriggerToastPacket(id);
    }

    @Override
    public void write(FriendlyByteBuf friendlyByteBuf) {
        this.toBytes(friendlyByteBuf);

    }
}
