package com.finderfeed.solarforge.magic_items.blocks.blockentities;

import com.finderfeed.solarforge.magic_items.blocks.blockentities.containers.RunicTableContainer;
import com.finderfeed.solarforge.magic_items.blocks.blockentities.containers.SolarFurnaceContainer;
import com.finderfeed.solarforge.registries.tile_entities.TileEntitiesRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;

import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;

public class RunicTableTileEntity extends RandomizableContainerBlockEntity {


    private SimpleContainerData arr = new SimpleContainerData(6);
    public NonNullList<ItemStack> items = NonNullList.withSize(7,ItemStack.EMPTY);

    public RunicTableTileEntity( BlockPos p_155630_, BlockState p_155631_) {
        super(TileEntitiesRegistry.RUNIC_TABLE_TILE.get(), p_155630_, p_155631_);
    }


    @Override
    protected Component getDefaultName() {
        return new TranslatableComponent("container.runictable");
    }

    @Override
    protected AbstractContainerMenu createMenu(int x, Inventory inv) {
        return new RunicTableContainer(x,inv,this,arr);
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return this.items;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> items) {
        this.items = items;
    }

    @Override
    public int getContainerSize() {
        return items.size();
    }

    @Override
    public void saveAdditional(CompoundTag cmp){
        super.saveAdditional(cmp);

        if (!this.trySaveLootTable(cmp)) {
            ContainerHelper.saveAllItems(cmp, this.items);
        }

    }

    @Override
    public void load( CompoundTag cmp) {
        super.load(cmp);

        this.items = NonNullList.withSize(getContainerSize(), ItemStack.EMPTY);
        if (!this.tryLoadLootTable(cmp)) {
            ContainerHelper.loadAllItems(cmp, this.items);
        }
    }


}
