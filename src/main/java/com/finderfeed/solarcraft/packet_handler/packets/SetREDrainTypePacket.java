package com.finderfeed.solarcraft.packet_handler.packets;

import com.finderfeed.solarcraft.content.items.solar_wand.wand_actions.drain_runic_enenrgy_action.REDrainWandActionData;
import com.finderfeed.solarcraft.content.items.solar_wand.wand_actions.drain_runic_enenrgy_action.REDrainWandActionDataSerializer;
import com.finderfeed.solarcraft.misc_things.RunicEnergy;
import com.finderfeed.solarcraft.registries.items.SCItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SetREDrainTypePacket {

    private int id;

    public SetREDrainTypePacket(RunicEnergy.Type type){
        this.id = type.getIndex();
    }

    public SetREDrainTypePacket(FriendlyByteBuf buf){
        this.id = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf){
        buf.writeInt(id);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx){
        ctx.get().enqueueWork(()->{
            ServerPlayer enti = ctx.get().getSender();
            if (enti.getMainHandItem().is(SCItems.SOLAR_WAND.get())){
                ItemStack stack = enti.getMainHandItem();
                REDrainWandActionDataSerializer serializer = REDrainWandActionDataSerializer.SERIALIZER;

                String dataname = serializer.getDataName().toString();

                if (!stack.getOrCreateTag().contains(dataname)){
                    stack.getOrCreateTag().put(dataname,new CompoundTag());
                }

                CompoundTag tag = stack.getOrCreateTag().getCompound(serializer.getDataName().toString());

                REDrainWandActionData data = serializer.deserialize(tag);
                data.setTypeToDrain(RunicEnergy.Type.getAll()[id]);
                serializer.serialize(tag,data);
            }
        });
        ctx.get().setPacketHandled(true);
    }


}
