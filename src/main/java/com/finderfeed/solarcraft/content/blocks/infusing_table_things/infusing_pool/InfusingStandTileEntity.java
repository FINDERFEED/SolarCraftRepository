package com.finderfeed.solarcraft.content.blocks.infusing_table_things.infusing_pool;

import com.finderfeed.solarcraft.helpers.Helpers;
import com.finderfeed.solarcraft.content.blocks.blockentities.ItemStackHandlerTile;
import com.finderfeed.solarcraft.registries.SCAttachmentTypes;
import com.finderfeed.solarcraft.registries.tile_entities.SCTileEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.state.BlockState;

import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import javax.annotation.Nullable;
import java.util.function.Supplier;


public class InfusingStandTileEntity extends ItemStackHandlerTile {


    private boolean shouldRenderItem = true;


    public InfusingStandTileEntity(BlockPos p_155630_, BlockState p_155631_) {
        super(SCTileEntities.INFUSING_POOL_BLOCKENTITY.get(), p_155630_, p_155631_);
    }



    public void shouldRenderItem(boolean e){
        this.shouldRenderItem = e;
    }

    public boolean isRenderingItem() {
        return shouldRenderItem;
    }



//    public static void tick(Level world, BlockPos pos, BlockState state, InfusingStandTileEntity tile) {
//        if (!tile.level.isClientSide) {
//            tile.setChanged();
//            world.sendBlockUpdated(pos,state,state,3);
//        }
//    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        super.onDataPacket(net, pkt);
    }

    @Nullable
    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        CompoundTag tag = saveWithFullMetadata();
        return Helpers.createTilePacket(this,tag);
    }

    @Override
    public Supplier<AttachmentType<ItemStackHandler>> getAttachmentType() {
        return SCAttachmentTypes.INVENTORY_1;
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
    }
}
