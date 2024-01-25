package com.finderfeed.solarcraft.content.items.solar_lexicon.packets;

import com.finderfeed.solarcraft.helpers.ClientHelpers;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;
import net.minecraft.network.FriendlyByteBuf;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;




public class UpdateInventoryPacket {

    public final int length;
    public final ItemStack[] stacks;

    public UpdateInventoryPacket(ItemStack[] stacks) {
        this.stacks = stacks;
        this.length = stacks.length;
    }

    public UpdateInventoryPacket(FriendlyByteBuf buf) {
        this.length = buf.readInt();

        List<ItemStack> list = new ArrayList<>();
        for (int i = 0; i < length; i++){
            list.add(buf.readItem());
        }
        ItemStack[] arr = new ItemStack[list.size()];
        this.stacks = list.toArray(arr);
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(length);
        for (int i = 0; i < length; i++){
            buf.writeItem(stacks[i]);
        }
    }

    public void handle(PlayPayloadContext ctx) {
        ctx.enqueueWork(()-> ClientHelpers.updateLexiconInventory(stacks));
        
    }
}