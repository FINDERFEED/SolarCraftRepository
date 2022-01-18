package com.finderfeed.solarforge.magic_items.blocks.blockentities;

import com.finderfeed.solarforge.Helpers;
import com.finderfeed.solarforge.registries.tile_entities.TileEntitiesRegistry;
import net.minecraft.client.model.HeadedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;

public class ExplosionBlockerBlockEntity extends SolarcraftBlockEntity{

    public static final int DEFENDING_RADIUS = 30;
    private boolean shouldRenderShield = true;

    public ExplosionBlockerBlockEntity( BlockPos p_155229_, BlockState p_155230_) {
        super(TileEntitiesRegistry.EXPLOSTION_BLOCKER.get(), p_155229_, p_155230_);
    }


    public void setShieldRenderingState(boolean a){
        shouldRenderShield = a;
    }

    public boolean shouldRenderShield() {
        return shouldRenderShield;
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        CompoundTag tag = new CompoundTag();
        saveAdditional(tag);
        return Helpers.createTilePacket(this,tag);
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        load(pkt.getTag());
        super.onDataPacket(net, pkt);
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putBoolean("shouldRenderShield",shouldRenderShield);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        shouldRenderShield = tag.getBoolean("shouldRenderShield");
        if (level != null){
            this.setChanged();
            level.sendBlockUpdated(worldPosition,getBlockState(),getBlockState(),3);
        }
    }

    @Override
    public AABB getRenderBoundingBox() {
        return new AABB(-40,-40,-40,40,40,40).move(Helpers.getBlockCenter(worldPosition));
    }


}
