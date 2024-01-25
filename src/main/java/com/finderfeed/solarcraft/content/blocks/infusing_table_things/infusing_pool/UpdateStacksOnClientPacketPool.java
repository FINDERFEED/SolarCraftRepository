package com.finderfeed.solarcraft.content.blocks.infusing_table_things.infusing_pool;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.DistExecutor;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.core.BlockPos;
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
    public void handle(PlayPayloadContext ctx){
        
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> ()->{
                ClientLevel world = Minecraft.getInstance().level;

                InfusingStandTileEntity tile = (InfusingStandTileEntity) world.getBlockEntity(pos);
                if (tile != null) {
                    tile.setStackInSlot(0, stack);
                }

            });

        });
        
    }
}
