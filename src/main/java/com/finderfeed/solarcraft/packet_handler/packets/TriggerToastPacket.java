package com.finderfeed.solarcraft.packet_handler.packets;

import com.finderfeed.solarcraft.content.items.solar_lexicon.progressions.Progression;
import com.finderfeed.solarcraft.helpers.ClientHelpers;
import com.finderfeed.solarcraft.client.toasts.ProgressionToast;
import com.finderfeed.solarcraft.content.items.solar_lexicon.progressions.progression_tree.ProgressionTree;
import com.finderfeed.solarcraft.registries.sounds.SolarcraftSounds;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.neoforge.fml.DistExecutor;
import net.neoforged.neoforge.network.NetworkEvent;


import java.util.function.Supplier;

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
    public void handle(Supplier<NetworkEvent.Context> ctx){
        ctx.get().enqueueWork(()->{
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> ()-> {

                ClientHelpers.playSound(SolarcraftSounds.PROGRESSION_GAIN.get(),1,1);
                ProgressionToast.addOrUpdate(Minecraft.getInstance().getToasts(), Progression.getProgressionByName(id));
            });

        });
        ctx.get().setPacketHandled(true);
    }
}
