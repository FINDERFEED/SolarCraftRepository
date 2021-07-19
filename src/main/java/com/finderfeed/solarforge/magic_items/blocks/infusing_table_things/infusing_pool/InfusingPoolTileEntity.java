package com.finderfeed.solarforge.magic_items.blocks.infusing_table_things.infusing_pool;

import com.finderfeed.solarforge.packet_handler.SolarForgePacketHandler;
import com.finderfeed.solarforge.registries.tile_entities.TileEntitiesRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.network.PacketDistributor;

public class InfusingPoolTileEntity extends LockableLootTileEntity implements ITickableTileEntity {

    public NonNullList<ItemStack> items = NonNullList.withSize(1,ItemStack.EMPTY);

    public InfusingPoolTileEntity(TileEntityType<?> p_i48289_1_) {
        super(p_i48289_1_);
    }

    @Override
    protected ITextComponent getDefaultName() {
        return new TranslationTextComponent("container.solarforge.infusing_pool");
    }

    @Override
    protected Container createMenu(int x, PlayerInventory inv) {
        return new InfusingPoolContainer(x,inv,this);
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return this.items;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> items) {
        this.items = items;
    }

    public InfusingPoolTileEntity(){
        this(TileEntitiesRegistry.INFUSING_POOL_BLOCKENTITY.get());
    }

    @Override
    public int getContainerSize() {
        return this.items.size();
    }



    @Override
    public CompoundNBT save(CompoundNBT cmp){
        super.save(cmp);

        if (!this.trySaveLootTable(cmp)) {
            ItemStackHelper.saveAllItems(cmp, this.items);
        }
        return cmp;
    }

    @Override
    public void load(BlockState state, CompoundNBT cmp) {
        super.load(state,cmp);

        this.items = NonNullList.withSize(getContainerSize(), ItemStack.EMPTY);
        if (!this.tryLoadLootTable(cmp)) {
            ItemStackHelper.loadAllItems(cmp, this.items);
        }
    }

    @Override
    public void tick() {
        if (!this.level.isClientSide) {
            SolarForgePacketHandler.INSTANCE.send(PacketDistributor.NEAR.with(PacketDistributor.TargetPoint.p(worldPosition.getX(),worldPosition.getY(),worldPosition.getZ(),20,level.dimension())),
                    new UpdateStacksOnClientPacketPool(this.getItem(0),this.worldPosition));

        }
    }


}
