package com.finderfeed.solarforge.magic_items.blocks.infusing_table_things.infusing_pool;

import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;


public class UpdateStacksOnClientPacketPool {
    public ItemStack stack;
    public BlockPos pos;
    public UpdateStacksOnClientPacketPool(PacketBuffer buf){
        stack = buf.readItem();
        pos = buf.readBlockPos();
    }
    public UpdateStacksOnClientPacketPool(ItemStack stack,BlockPos pos){
        this.stack = stack;
        this.pos = pos;
    }
    public void toBytes(PacketBuffer buf){
        buf.writeItem(stack);
        buf.writeBlockPos(pos);
    }
    public void handle(Supplier<NetworkEvent.Context> ctx){
        ctx.get().enqueueWork(()->{
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> ()->{
                ClientWorld world = Minecraft.getInstance().level;

                InfusingPoolTileEntity tile = (InfusingPoolTileEntity) world.getBlockEntity(pos);
                if (tile != null) {
                    tile.setItem(0, stack);
                }

            });

        });
        ctx.get().setPacketHandled(true);
    }
}
