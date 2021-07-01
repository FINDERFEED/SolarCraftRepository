package com.finderfeed.solarforge.magic_items.blocks.blockentities;

import com.finderfeed.solarforge.SolarForge;
import com.finderfeed.solarforge.misc_things.PhantomInventory;
import com.finderfeed.solarforge.recipe_types.solar_smelting.SolarSmeltingRecipe;
import com.finderfeed.solarforge.registries.tile_entities.TileEntitiesRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;

import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.vector.Vector3d;


import java.util.List;
import java.util.Optional;

public class SolarLensTile extends TileEntity implements ITickableTileEntity {

    public PhantomInventory INVENTORY = new PhantomInventory(4);
    public int SMELTING_TIME = 0;
    public int CURRENT_SMELTING_TIME = 0;
    public boolean RECIPE_IN_PROGRESS = false;
    public SolarLensTile() {
        super(TileEntitiesRegistry.SOLAR_LENS_TILE.get());
    }



    @Override
    public void tick() {
        if (!this.level.isClientSide){

            AxisAlignedBB box = new AxisAlignedBB(-1,-2.5,-1,1,0,1);
            if (this.level.canSeeSky(worldPosition.above()) && this.getLevel().getDayTime() % 24000 <= 13000){

                List<ItemEntity> list = level.getEntitiesOfClass(ItemEntity.class,box.move(worldPosition));

                for (int i = 0;i < 4;i++){
                    if (i > list.size()-1) {
                        INVENTORY.setItem(i, ItemStack.EMPTY);
                    } else {

                        INVENTORY.setItem(i, list.get(i).getItem());

                    }


                }

                Optional<SolarSmeltingRecipe> recipe = this.level.getRecipeManager().getRecipeFor(SolarForge.SOLAR_SMELTING,INVENTORY,this.level);
                if (recipe.isPresent() ){

                    RECIPE_IN_PROGRESS = true;
                    SMELTING_TIME = recipe.get().smeltingTime;
                    CURRENT_SMELTING_TIME++;
                    if (CURRENT_SMELTING_TIME == SMELTING_TIME){
                        for (ItemEntity a : list){
                            a.remove();
                        }
                        Vector3d pos = new Vector3d(worldPosition.getX()+0.5d,worldPosition.getY()-1,worldPosition.getZ()+0.5d);
                        ItemEntity entity = new ItemEntity(this.level,pos.x,pos.y,pos.z,recipe.get().output);
                        this.level.addFreshEntity(entity);
                        SMELTING_TIME = 0;
                        CURRENT_SMELTING_TIME = 0;
                        RECIPE_IN_PROGRESS = false;
                    }
                }else{
                    RECIPE_IN_PROGRESS = false;
                    CURRENT_SMELTING_TIME = 0;
                    SMELTING_TIME = 0;
                }
            }
        }
    }

    @Override
    public CompoundNBT save(CompoundNBT cmp) {
            cmp.putInt("smelting_time",SMELTING_TIME);
            cmp.putInt("smelting_time_current",CURRENT_SMELTING_TIME);
            cmp.putBoolean("recipe_in_progress",RECIPE_IN_PROGRESS);
        return super.save(cmp);
    }

    @Override
    public void load(BlockState p_230337_1_, CompoundNBT cmp) {
        SMELTING_TIME = cmp.getInt("smelting_time");
        CURRENT_SMELTING_TIME = cmp.getInt("smelting_time_current");
        RECIPE_IN_PROGRESS = cmp.getBoolean("recipe_in_progress");
        super.load(p_230337_1_, cmp);
    }
}
