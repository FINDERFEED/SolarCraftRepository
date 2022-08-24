package com.finderfeed.solarforge.content.blocks.infusing_table_things;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;


import java.util.function.Supplier;



public class UpdateStacksOnClientTable {
    public boolean isActive;
    public ItemStack output;
    public ItemStack stack;
    public ItemStack stack1;
    public ItemStack stack2;
    public ItemStack stack3;
    public ItemStack stack4;
    public ItemStack stack5;
    public ItemStack stack6;
    public ItemStack stack7;
    public ItemStack stack8;
    public BlockPos pos;
    public UpdateStacksOnClientTable(FriendlyByteBuf buf){
        stack = buf.readItem();
        stack1 = buf.readItem();
        stack2 = buf.readItem();
        stack3 = buf.readItem();
        stack4 = buf.readItem();
        stack5 = buf.readItem();
        stack6 = buf.readItem();
        stack7 = buf.readItem();
        stack8 = buf.readItem();
        output = buf.readItem();
        pos = buf.readBlockPos();
        isActive = buf.readBoolean();
    }
    public UpdateStacksOnClientTable(ItemStack[] stack,ItemStack output,BlockPos pos,boolean isActive){
        this.stack = stack[0];
        this.stack1 = stack[1];
        this.stack2 = stack[2];
        this.stack3 = stack[3];
        this.stack4 = stack[4];
        this.stack5 = stack[5];
        this.stack6 = stack[6];
        this.stack7 = stack[7];
        this.stack8 = stack[8];
        this.output = output;
        this.pos = pos;
        this.isActive = isActive;
    }
    public void toBytes(FriendlyByteBuf buf){
        buf.writeItem(stack);
        buf.writeItem(stack1);
        buf.writeItem(stack2);
        buf.writeItem(stack3);
        buf.writeItem(stack4);
        buf.writeItem(stack5);
        buf.writeItem(stack6);
        buf.writeItem(stack7);
        buf.writeItem(stack8);
        buf.writeItem(output);
        buf.writeBlockPos(pos);
        buf.writeBoolean(isActive);
    }
    public void handle(Supplier<NetworkEvent.Context> ctx){
        ctx.get().enqueueWork(()->{
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> ()-> {
                ClientLevel world = Minecraft.getInstance().level;

                InfuserTileEntity tile = (InfuserTileEntity) world.getBlockEntity(pos);
                if (tile != null) {
                    tile.setItem(0, stack);
                    tile.setItem(1, stack1);
                    tile.setItem(2, stack2);
                    tile.setItem(3, stack3);
                    tile.setItem(4, stack4);
                    tile.setItem(5, stack5);
                    tile.setItem(6, stack6);
                    tile.setItem(7, stack7);
                    tile.setItem(8, stack8);
                    tile.setItem(9, output);
                    tile.RECIPE_IN_PROGRESS = isActive;
                }
            });
        });
        ctx.get().setPacketHandled(true);
    }
}
