package com.finderfeed.solarcraft.packet_handler.packets;

import com.finderfeed.solarcraft.content.items.solar_lexicon.progressions.Progression;
import com.finderfeed.solarcraft.helpers.ClientHelpers;
import com.finderfeed.solarcraft.client.toasts.ProgressionToast;
import com.finderfeed.solarcraft.registries.sounds.SCSounds;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.DistExecutor;
import net.neoforged.neoforge.network.NetworkEvent;

public class TriggerToastPacket {

    public final String id;

    public TriggerToastPacket(FriendlyByteBuf buf){
        this.id = buf.readUtf();
    }
    public TriggerToastPacket(String progressionId){
        this.id = progressionId;
    }
    public void toBytes(FriendlyByteBuf buf){
        buf.writeUtf(id);
    }
    public void handle(NetworkEvent.Context ctx){
        ctx.enqueueWork(()->{
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> ()-> {

                ClientHelpers.playSound(SCSounds.PROGRESSION_GAIN.get(),1,1);
                ProgressionToast.addOrUpdate(Minecraft.getInstance().getToasts(), Progression.getProgressionByName(id));
            });

        });
        ctx.setPacketHandled(true);
    }
}
