package com.finderfeed.solarcraft.packet_handler.packets;

import com.finderfeed.solarcraft.helpers.ClientHelpers;
import com.finderfeed.solarcraft.client.toasts.ProgressionToast;
import com.finderfeed.solarcraft.content.items.solar_lexicon.progressions.progression_tree.ProgressionTree;
import com.finderfeed.solarcraft.registries.sounds.SolarcraftSounds;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;


import java.util.function.Supplier;

public class TriggerToastPacket {

    public final int id;

    public TriggerToastPacket(FriendlyByteBuf buf){
        this.id = buf.readInt();
    }
    public TriggerToastPacket(int id){
        this.id = id;
    }
    public void toBytes(FriendlyByteBuf buf){
        buf.writeInt(id);
    }
    public void handle(Supplier<NetworkEvent.Context> ctx){
        ctx.get().enqueueWork(()->{
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> ()-> {
                ProgressionTree tree = ProgressionTree.INSTANCE;
                ClientHelpers.playSound(SolarcraftSounds.PROGRESSION_GAIN.get(),1,1);
                ProgressionToast.addOrUpdate(Minecraft.getInstance().getToasts(), tree.getAchievementById(id));
            });

        });
        ctx.get().setPacketHandled(true);
    }
}
