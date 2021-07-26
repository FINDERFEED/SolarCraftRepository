package com.finderfeed.solarforge.magic_items.blocks.infusing_table_things.infusing_pool;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;


import java.util.function.Supplier;



import net.minecraftforge.fmllegacy.network.NetworkEvent;

public class UpdateStacksOnClientPacketPool {
    public ItemStack stack;
    public BlockPos pos;
    public UpdateStacksOnClientPacketPool(FriendlyByteBuf buf){
        stack = buf.readItem();
        pos = buf.readBlockPos();
    }
    public UpdateStacksOnClientPacketPool(ItemStack stack,BlockPos pos){
        this.stack = stack;
        this.pos = pos;
    }
    public void toBytes(FriendlyByteBuf buf){
        buf.writeItem(stack);
        buf.writeBlockPos(pos);
    }
    public void handle(Supplier<NetworkEvent.Context> ctx){
        ctx.get().enqueueWork(()->{
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> ()->{
                ClientLevel world = Minecraft.getInstance().level;

                InfusingPoolTileEntity tile = (InfusingPoolTileEntity) world.getBlockEntity(pos);
                if (tile != null) {
                    tile.setItem(0, stack);
                }

            });

        });
        ctx.get().setPacketHandled(true);
    }
}
