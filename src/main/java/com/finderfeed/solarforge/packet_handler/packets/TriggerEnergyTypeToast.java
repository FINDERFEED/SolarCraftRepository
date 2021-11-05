package com.finderfeed.solarforge.packet_handler.packets;

import com.finderfeed.solarforge.ClientHelpers;
import com.finderfeed.solarforge.client.toasts.SolarAchievementToast;
import com.finderfeed.solarforge.client.toasts.UnlockedEnergyTypeToast;
import com.finderfeed.solarforge.magic_items.items.solar_lexicon.achievements.achievement_tree.AchievementTree;
import com.finderfeed.solarforge.misc_things.RunicEnergy;
import com.finderfeed.solarforge.registries.sounds.Sounds;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fmllegacy.network.NetworkEvent;

import java.util.function.Supplier;

public class TriggerEnergyTypeToast {
    public final String id;

    public TriggerEnergyTypeToast(FriendlyByteBuf buf){
        this.id = buf.readUtf();
    }
    public TriggerEnergyTypeToast(String id){
        this.id = id;
    }
    public void toBytes(FriendlyByteBuf buf){
        buf.writeUtf(id);
    }
    public void handle(Supplier<NetworkEvent.Context> ctx){
        ctx.get().enqueueWork(()->{
            ClientHelpers.addEnergyTypeToast(id);
        });
        ctx.get().setPacketHandled(true);
    }
}
