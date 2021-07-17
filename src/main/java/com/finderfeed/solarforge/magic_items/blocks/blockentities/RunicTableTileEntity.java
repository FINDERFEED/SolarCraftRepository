package com.finderfeed.solarforge.magic_items.blocks.blockentities;

import com.finderfeed.solarforge.magic_items.blocks.blockentities.containers.RunicTableContainer;
import com.finderfeed.solarforge.magic_items.blocks.blockentities.containers.SolarFurnaceContainer;
import com.finderfeed.solarforge.registries.tile_entities.TileEntitiesRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.IntArray;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class RunicTableTileEntity extends LockableLootTileEntity {


    private IntArray arr = new IntArray(6);
    public NonNullList<ItemStack> items = NonNullList.withSize(7,ItemStack.EMPTY);

    public RunicTableTileEntity() {
        super(TileEntitiesRegistry.RUNIC_TABLE_TILE.get());
    }

    @Override
    protected ITextComponent getDefaultName() {
        return new TranslationTextComponent("container.runictable");
    }

    @Override
    protected Container createMenu(int x, PlayerInventory inv) {
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


}
