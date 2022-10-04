package com.finderfeed.solarforge.content.blocks.blockentities;

import com.finderfeed.solarforge.content.blocks.blockentities.containers.SolarFurnaceContainer;
import com.finderfeed.solarforge.content.blocks.solar_energy.Bindable;
import com.finderfeed.solarforge.content.blocks.solar_energy.SolarEnergyContainer;
import com.finderfeed.solarforge.registries.tile_entities.SolarcraftTileEntityTypes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.core.NonNullList;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Component;

import java.util.Optional;

public class SolarEnergyFurnaceTile extends RandomizableContainerBlockEntity implements Bindable, SolarEnergyContainer {
    public int SOLAR_ENERGY_LEVEL = 0;
    public int RECIPE_PROGRESS = 0;
    public int MAX_RECIPE_TIME = 0;
    public boolean RECIPE_IN_PROGRESS = false;
    private SimpleContainerData arr = new SimpleContainerData(3);
    public NonNullList<ItemStack> items = NonNullList.withSize(2,ItemStack.EMPTY);

    public SolarEnergyFurnaceTile( BlockPos p_155630_, BlockState p_155631_) {
        super(SolarcraftTileEntityTypes.SOLAR_FURNACE_TILE_ENTITY.get(), p_155630_, p_155631_);
    }


    public int getCurrentEnergy(){
        return SOLAR_ENERGY_LEVEL;
    }
    @Override
    protected Component getDefaultName() {
        return Component.translatable("container.solarfurnace");
    }

    @Override
    protected AbstractContainerMenu createMenu(int x, Inventory inv) {
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
    public void saveAdditional(CompoundTag cmp){
        super.saveAdditional(cmp);
        cmp.putInt("solar_energy",SOLAR_ENERGY_LEVEL);
        cmp.putInt("recipe_time",RECIPE_PROGRESS);
        cmp.putInt("max_recipe_time",MAX_RECIPE_TIME);
        cmp.putBoolean("recipe_in_progress",RECIPE_IN_PROGRESS);
        if (!this.trySaveLootTable(cmp)) {
            ContainerHelper.saveAllItems(cmp, this.items);
        }

    }
    @Override
    public void load( CompoundTag cmp) {
        super.load(cmp);
        SOLAR_ENERGY_LEVEL = cmp.getInt("solar_energy");
        RECIPE_IN_PROGRESS = cmp.getBoolean("recipe_in_progress");
        RECIPE_PROGRESS = cmp.getInt("recipe_time");
        MAX_RECIPE_TIME = cmp.getInt("max_recipe_time");
        this.items = NonNullList.withSize(getContainerSize(), ItemStack.EMPTY);
        if (!this.tryLoadLootTable(cmp)) {
            ContainerHelper.loadAllItems(cmp, this.items);
        }
    }








    public int getSmeltingCost(){
        return 10;
    }


    public static void tick(Level world, BlockPos pos, BlockState blockState, SolarEnergyFurnaceTile tile) {
        if (!tile.level.isClientSide){

            tile.arr.set(0,tile.SOLAR_ENERGY_LEVEL);
            tile.arr.set(1,tile.RECIPE_PROGRESS);
            tile.arr.set(2,tile.MAX_RECIPE_TIME);
            Optional<SmeltingRecipe> recipe =tile.level.getRecipeManager().getRecipeFor(RecipeType.SMELTING,tile,tile.level);
                if (recipe.isPresent()){
                    SmeltingRecipe recipe1 = recipe.get();
                    ItemStack item = tile.getItem(1);
                    if (((item.getItem().equals(recipe1.getResultItem().getItem())
                            && (item.getCount() < item.getMaxStackSize()) )
                            || item.getItem().equals(ItemStack.EMPTY.getItem())) ) {
                        tile.RECIPE_IN_PROGRESS = true;
                        tile.MAX_RECIPE_TIME = recipe1.getCookingTime() / 2;

                        if (tile.getCurrentEnergy() >= tile.getSmeltingCost()) {
                            tile.RECIPE_PROGRESS++;
                            tile.SOLAR_ENERGY_LEVEL-=tile.getSmeltingCost();
                        }
                        if (tile.RECIPE_PROGRESS >= tile.MAX_RECIPE_TIME) {
                            tile.RECIPE_PROGRESS = 0;
                            tile.RECIPE_IN_PROGRESS = false;
                            tile.MAX_RECIPE_TIME = 0;
                            if ((item.getItem().equals(recipe1.getResultItem().getItem()) )){
                                item.grow(1);
                            }else{
                                tile.setItem(1,recipe1.getResultItem().copy());
                            }
                            tile.getItem(0).grow(-1);
                        }

                    }else{
                        tile.RECIPE_PROGRESS = 0;
                        tile.RECIPE_IN_PROGRESS = false;
                        tile.MAX_RECIPE_TIME = 0;
                    }
                }else{
                    tile.RECIPE_IN_PROGRESS = false;
                    tile.RECIPE_PROGRESS = 0;
                    tile.MAX_RECIPE_TIME = 0;
                }
        }
    }

    @Override
    public boolean bind(BlockPos pos) {
        return false;
    }

    @Override
    public int getSolarEnergy() {
        return SOLAR_ENERGY_LEVEL;
    }

    @Override
    public void setSolarEnergy(int energy) {
        this.SOLAR_ENERGY_LEVEL = energy;
    }

    @Override
    public int getMaxSolarEnergy() {
        return 10000;
    }

    @Override
    public BlockPos getPos() {
        return worldPosition;
    }

    @Override
    public double getSolarEnergyCollectionRadius() {
        return 10;
    }

    @Override
    public boolean canBeBinded() {
        return false;
    }

    @Override
    public int maxEnergyInput() {
        return 10;
    }
}
