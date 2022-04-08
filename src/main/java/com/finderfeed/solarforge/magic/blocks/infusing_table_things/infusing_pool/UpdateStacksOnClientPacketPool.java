package com.finderfeed.solarforge.magic.blocks.infusing_table_things.infusing_pool;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;


import java.util.function.Supplier;



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

                InfusingStandTileEntity tile = (InfusingStandTileEntity) world.getBlockEntity(pos);
                if (tile != null) {
                    tile.setStackInSlot(0, stack);
                }

            });

        });
        ctx.get().setPacketHandled(true);
    }
}
