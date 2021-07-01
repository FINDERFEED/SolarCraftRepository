package com.finderfeed.solarforge.magic_items.blocks.blockentities;

import com.finderfeed.solarforge.magic_items.blocks.blockentities.containers.SolarFurnaceContainer;
import com.finderfeed.solarforge.misc_things.IBindable;
import com.finderfeed.solarforge.misc_things.IEnergyUser;
import com.finderfeed.solarforge.misc_things.OneWay;
import com.finderfeed.solarforge.registries.tile_entities.TileEntitiesRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.util.IntArray;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import org.lwjgl.system.CallbackI;

import java.util.Optional;

public class SolarEnergyFurnaceTile extends LockableLootTileEntity implements IEnergyUser, IBindable, ITickableTileEntity, OneWay {
    public int SOLAR_ENERGY_LEVEL = 0;
    public int RECIPE_PROGRESS = 0;
    public int MAX_RECIPE_TIME = 0;
    public boolean RECIPE_IN_PROGRESS = false;
    private IntArray arr = new IntArray(3);
    public NonNullList<ItemStack> items = NonNullList.withSize(2,ItemStack.EMPTY);


    public SolarEnergyFurnaceTile(){
        super(TileEntitiesRegistry.SOLAR_FURNACE_TILE_ENTITY.get());
    }

    public int getCurrentEnergy(){
        return SOLAR_ENERGY_LEVEL;
    }
    @Override
    protected ITextComponent getDefaultName() {
        return new TranslationTextComponent("container.solarfurnace");
    }

    @Override
    protected Container createMenu(int x, PlayerInventory inv) {
        return new SolarFurnaceContainer(x,inv,this,arr);
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
        cmp.putInt("solar_energy",SOLAR_ENERGY_LEVEL);
        cmp.putInt("recipe_time",RECIPE_PROGRESS);
        cmp.putInt("max_recipe_time",MAX_RECIPE_TIME);
        cmp.putBoolean("recipe_in_progress",RECIPE_IN_PROGRESS);
        if (!this.trySaveLootTable(cmp)) {
            ItemStackHelper.saveAllItems(cmp, this.items);
        }
        return cmp;
    }
    @Override
    public void load(BlockState state, CompoundNBT cmp) {
        super.load(state,cmp);
        SOLAR_ENERGY_LEVEL = cmp.getInt("solar_energy");
        RECIPE_IN_PROGRESS = cmp.getBoolean("recipe_in_progress");
        RECIPE_PROGRESS = cmp.getInt("recipe_time");
        MAX_RECIPE_TIME = cmp.getInt("max_recipe_time");
        this.items = NonNullList.withSize(getContainerSize(), ItemStack.EMPTY);
        if (!this.tryLoadLootTable(cmp)) {
            ItemStackHelper.loadAllItems(cmp, this.items);
        }
    }

    @Override
    public void bindPos(BlockPos pos) {
        if ((level.getBlockEntity(pos) instanceof IBindable) && !(level.getBlockEntity(pos) instanceof IEnergyUser)){
            ((IBindable) level.getBlockEntity(pos)).bindPos(pos);
        }
    }

    @Override
    public void giveEnergy(int a) {

            this.SOLAR_ENERGY_LEVEL += a;

    }

    @Override
    public int getMaxEnergy() {
        return 10000;
    }

    @Override
    public boolean requriesEnergy() {

        return SOLAR_ENERGY_LEVEL < getMaxEnergy();
    }

    @Override
    public int getRadius() {
        return 16;
    }

    public int getSmeltingCost(){
        return 10;
    }

    @Override
    public void tick() {
        if (!level.isClientSide){

            arr.set(0,SOLAR_ENERGY_LEVEL);
            arr.set(1,RECIPE_PROGRESS);
            arr.set(2,MAX_RECIPE_TIME);
            Optional<FurnaceRecipe> recipe =level.getRecipeManager().getRecipeFor(IRecipeType.SMELTING,this,level);
                if (recipe.isPresent()){
                    FurnaceRecipe recipe1 = recipe.get();
                    if ( ( (getItem(1).getItem().equals(recipe1.getResultItem().getItem()) && (getItem(1).getCount() < getItem(1).getMaxStackSize()) )
                            || getItem(1).getItem().equals(ItemStack.EMPTY.getItem())) ) {
                        RECIPE_IN_PROGRESS = true;
                        MAX_RECIPE_TIME = recipe1.getCookingTime() / 2;

                        if (getCurrentEnergy() >= getSmeltingCost()) {
                            RECIPE_PROGRESS++;
                            SOLAR_ENERGY_LEVEL-=getSmeltingCost();
                        }
                        if (RECIPE_PROGRESS >= MAX_RECIPE_TIME) {
                            RECIPE_PROGRESS = 0;
                            RECIPE_IN_PROGRESS = false;
                            MAX_RECIPE_TIME = 0;
                            if ((getItem(1).getItem().equals(recipe1.getResultItem().getItem()) )){
                                getItem(1).grow(1);
                            }else{
                                setItem(1,recipe1.getResultItem().copy());
                            }
                            getItem(0).grow(-1);
                        }

                    }else{
                        RECIPE_PROGRESS = 0;
                        RECIPE_IN_PROGRESS = false;
                        MAX_RECIPE_TIME = 0;
                    }
                }else{
                    RECIPE_IN_PROGRESS = false;
                    RECIPE_PROGRESS = 0;
                    MAX_RECIPE_TIME = 0;
                }
        }
    }
}
