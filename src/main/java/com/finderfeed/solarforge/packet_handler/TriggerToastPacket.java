package com.finderfeed.solarforge.packet_handler;

import com.finderfeed.solarforge.other_events.SolarAchievementToast;
import com.finderfeed.solarforge.solar_lexicon.achievement_tree.AchievementTree;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class TriggerToastPacket {

    public final int id;

    public TriggerToastPacket(PacketBuffer buf){
        this.id = buf.readInt();
    }
    public TriggerToastPacket(int id){
        this.id = id;
    }
    public void toBytes(PacketBuffer buf){
        buf.writeInt(id);
    }
    public void handle(Supplier<NetworkEvent.Context> ctx){
        ctx.get().enqueueWork(()->{
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> ()-> {
                AchievementTree tree = AchievementTree.loadTree();

                SolarAchievementToast.addOrUpdate(Minecraft.getInstance().getToasts(), tree.getAchievementById(id));
            });

        });
        ctx.get().setPacketHandled(true);
    }
}
