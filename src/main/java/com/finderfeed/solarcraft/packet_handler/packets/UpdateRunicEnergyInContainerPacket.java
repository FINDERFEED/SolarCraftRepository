package com.finderfeed.solarcraft.packet_handler.packets;

import com.finderfeed.solarcraft.content.blocks.blockentities.runic_energy.AbstractRunicEnergyContainer;
import com.finderfeed.solarcraft.helpers.ClientHelpers;
import com.finderfeed.solarcraft.packet_handler.ClientPacketHandles;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;
import java.util.function.Supplier;

public class UpdateRunicEnergyInContainerPacket {

    private BlockPos containerPos;
    private CompoundTag tag;

    public UpdateRunicEnergyInContainerPacket(AbstractRunicEnergyContainer container){
        CompoundTag tag = new CompoundTag();
        container.saveRunicEnergy(tag);
        this.containerPos = container.getBlockPos();
        this.tag = tag;
    }

    public UpdateRunicEnergyInContainerPacket(FriendlyByteBuf buf){
        this.containerPos = buf.readBlockPos();
        this.tag = buf.readNbt();
    }

    public void toBytes(FriendlyByteBuf buf){
        buf.writeBlockPos(containerPos);
        buf.writeNbt(tag);
    }


    public void handle(PlayPayloadContext ctx) {
        
            ClientPacketHandles.updateContainerRunicEnergy(containerPos,tag);
        });
        
    }

}
