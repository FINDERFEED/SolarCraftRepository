package com.finderfeed.solarcraft.packet_handler.packets;

import com.finderfeed.solarcraft.content.items.solar_lexicon.unlockables.AncientFragment;
import com.finderfeed.solarcraft.content.items.solar_lexicon.unlockables.AncientFragmentHelper;
import com.finderfeed.solarcraft.helpers.multiblock.MultiblockStructure;
import com.finderfeed.solarcraft.packet_handler.ClientPacketHandles;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class WandStructureActionPacket {

    private CompoundTag data;
    private BlockPos checkPos;
    private List<String> structIds;

    public WandStructureActionPacket(Player player, List<MultiblockStructure> structures, BlockPos checkPos){
        CompoundTag tag = new CompoundTag();
        for (AncientFragment fragment : AncientFragment.STRUCTURE_FRAGMENTS.values()){
            tag.putBoolean(fragment.getId(), AncientFragmentHelper.doPlayerHasFragment(player,fragment));
        }
        this.data = tag;
        this.structIds = structures.stream().map(MultiblockStructure::getId).collect(Collectors.toList());
        this.checkPos = checkPos;

    }


    public WandStructureActionPacket(FriendlyByteBuf buf){
        this.data = buf.readAnySizeNbt();
        int size = buf.readInt();
        structIds = new ArrayList<>();
        for (int i = 0; i < size;i++){
            structIds.add(buf.readUtf());
        }
        this.checkPos = buf.readBlockPos();
    }

    public void toBytes(FriendlyByteBuf buf){
        buf.writeNbt(data);
        buf.writeInt(structIds.size());
        for (String str : structIds){
            buf.writeUtf(str);
        }
        buf.writeBlockPos(checkPos);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(()->{
            ClientPacketHandles.handleWandStructureActionPacket(checkPos,structIds,data);
        });
        ctx.get().setPacketHandled(true);
    }
}
