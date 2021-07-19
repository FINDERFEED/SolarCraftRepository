package com.finderfeed.solarforge.magic_items.blocks.solar_forge_block;

import com.finderfeed.solarforge.SolarForge;
import com.finderfeed.solarforge.registries.items.ItemsRegister;
import com.finderfeed.solarforge.magic_items.blocks.solar_forge_block.solar_forge_screen.SolarForgeContainer;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.IntArray;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class SolarForgeBlockEntity extends LockableLootTileEntity implements ITickableTileEntity {
    public int SOLAR_ENERGY_LEVEL = 0;
    private IntArray arr = new IntArray(1);
    public NonNullList<ItemStack> items = NonNullList.withSize(2,ItemStack.EMPTY);
    public SolarForgeBlockEntity(TileEntityType<?> type) {
        super(type);
    }

    public int getCurrentEnergy(){
        return SOLAR_ENERGY_LEVEL;
    }
    @Override
    protected ITextComponent getDefaultName() {
        return new TranslationTextComponent("container.solarforge");
    }

    @Override
    protected Container createMenu(int x, PlayerInventory inv) {
        return new SolarForgeContainer(x,inv,this,arr);
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return this.items;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> items) {
        this.items = items;
    }

    public SolarForgeBlockEntity(){
        this(SolarForge.SOLAR_FORGE_BLOCKENTITY.get());
    }

    @Override
    public int getContainerSize() {
        return items.size();
    }
    @Override
    public CompoundNBT save(CompoundNBT cmp){
        super.save(cmp);
        cmp.putInt("solar_energy",SOLAR_ENERGY_LEVEL);
        if (!this.trySaveLootTable(cmp)) {
            ItemStackHelper.saveAllItems(cmp, this.items);
        }
    return cmp;
    }
    @Override
    public void load(BlockState state, CompoundNBT cmp) {
        super.load(state,cmp);
        SOLAR_ENERGY_LEVEL = cmp.getInt("solar_energy");
        this.items = NonNullList.withSize(getContainerSize(), ItemStack.EMPTY);
        if (!this.tryLoadLootTable(cmp)) {
            ItemStackHelper.loadAllItems(cmp, this.items);
        }
    }

    @Override
    public void tick() {
        if (!this.getLevel().isClientSide){

            arr.set(0,SOLAR_ENERGY_LEVEL);

            if ( this.getLevel().getDayTime() % 24000 <= 13000 && SOLAR_ENERGY_LEVEL < 30000 && this.level.canSeeSky(this.getBlockPos().above())){
                SOLAR_ENERGY_LEVEL++;



            }
            if (this.getItems().get(0).getItem() == SolarForge.TEST_ITEM.get() && SOLAR_ENERGY_LEVEL+300 <= 30000){
                this.getItems().get(0).grow(-1);
                if (this.level.random.nextDouble() > 0.85) {
                    if (this.getItem(1).isEmpty()) {
                        this.setItem(1, new ItemStack(ItemsRegister.SOLAR_DUST.get(), 1));
                    } else {
                        ItemStack stack = this.getItem(1).copy();
                        stack.grow(1);
                        this.setItem(1, stack);
                    }
                }
                SOLAR_ENERGY_LEVEL+=300;


            }
        }

    }

    @Override
    public AxisAlignedBB getRenderBoundingBox(){
        return new AxisAlignedBB(getBlockPos().offset(-1,0,-1),getBlockPos().offset(1,100,1));
    }


}

