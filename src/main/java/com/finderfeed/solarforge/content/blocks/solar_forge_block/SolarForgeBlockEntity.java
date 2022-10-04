package com.finderfeed.solarforge.content.blocks.solar_forge_block;

import com.finderfeed.solarforge.SolarForge;
import com.finderfeed.solarforge.registries.items.SolarcraftItems;
import com.finderfeed.solarforge.content.blocks.solar_forge_block.solar_forge_screen.SolarForgeContainer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;

import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.core.NonNullList;
import net.minecraft.world.phys.AABB;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Component;

public class SolarForgeBlockEntity extends RandomizableContainerBlockEntity  {
    public int SOLAR_ENERGY_LEVEL = 0;
    private SimpleContainerData arr = new SimpleContainerData(1);

    public NonNullList<ItemStack> items = NonNullList.withSize(2,ItemStack.EMPTY);

    public SolarForgeBlockEntity(BlockPos p_155630_, BlockState p_155631_) {
        super(SolarForge.SOLAR_FORGE_BLOCKENTITY.get(), p_155630_, p_155631_);
    }


    public int getCurrentEnergy(){
        return SOLAR_ENERGY_LEVEL;
    }
    @Override
    protected Component getDefaultName() {
        return Component.translatable("container.solarforge");
    }

    @Override
    protected AbstractContainerMenu createMenu(int x, Inventory inv) {
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



    @Override
    public int getContainerSize() {
        return items.size();
    }
    @Override
    public void saveAdditional(CompoundTag cmp){
        super.saveAdditional(cmp);
        cmp.putInt("solar_energy",SOLAR_ENERGY_LEVEL);
        if (!this.trySaveLootTable(cmp)) {
            ContainerHelper.saveAllItems(cmp, this.items);
        }

    }


    @Override
    public void load(CompoundTag cmp) {
        super.load(cmp);
        SOLAR_ENERGY_LEVEL = cmp.getInt("solar_energy");
        this.items = NonNullList.withSize(getContainerSize(), ItemStack.EMPTY);
        if (!this.tryLoadLootTable(cmp)) {
            ContainerHelper.loadAllItems(cmp, this.items);
        }
    }



    public static void tick(Level world, BlockPos pos, BlockState blockState, SolarForgeBlockEntity tile) {
        if (!world.isClientSide){

            tile.arr.set(0,tile.SOLAR_ENERGY_LEVEL);

            if ( world.getDayTime() % 24000 <= 13000 && tile.SOLAR_ENERGY_LEVEL < 30000 && world.canSeeSky(pos.above())){
                tile.SOLAR_ENERGY_LEVEL++;

                if ((world.getGameTime() % 20 == 1)){
                    if (world.random.nextDouble() > 0.96) {
                        if (tile.getItem(1).isEmpty()) {
                            tile.setItem(1, new ItemStack(SolarcraftItems.SOLAR_DUST.get(), 1));
                        } else {
                            ItemStack stack = tile.getItem(1).copy();
                            stack.grow(1);
                            tile.setItem(1, stack);
                        }
                    }
                }


            }
            if (tile.getItems().get(0).getItem() == SolarForge.TEST_ITEM.get() && tile.SOLAR_ENERGY_LEVEL+300 <= 30000){
                tile.getItems().get(0).grow(-1);
                if (world.random.nextDouble() > 0.85) {
                    if (tile.getItem(1).isEmpty()) {
                        tile.setItem(1, new ItemStack(SolarcraftItems.SOLAR_DUST.get(), 1));
                    } else {
                        ItemStack stack = tile.getItem(1).copy();
                        stack.grow(1);
                        tile.setItem(1, stack);
                    }
                }
                tile.SOLAR_ENERGY_LEVEL+=300;


            }

        }

    }

    @Override
    public AABB getRenderBoundingBox(){
        return new AABB(getBlockPos().offset(-1,0,-1),getBlockPos().offset(1,100,1));
    }


}

