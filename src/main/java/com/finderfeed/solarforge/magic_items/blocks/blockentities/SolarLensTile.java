package com.finderfeed.solarforge.magic_items.blocks.blockentities;

import com.finderfeed.solarforge.SolarForge;
import com.finderfeed.solarforge.misc_things.PhantomInventory;
import com.finderfeed.solarforge.recipe_types.solar_smelting.SolarSmeltingRecipe;
import com.finderfeed.solarforge.registries.tile_entities.TileEntitiesRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;

import net.minecraft.world.level.block.entity.BlockEntity;

import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;


import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public class SolarLensTile extends BlockEntity  {

    public PhantomInventory INVENTORY = new PhantomInventory(4);
    public int SMELTING_TIME = 0;
    public int CURRENT_SMELTING_TIME = 0;
    public boolean RECIPE_IN_PROGRESS = false;

    public SolarLensTile(BlockPos p_155229_, BlockState p_155230_) {
        super(TileEntitiesRegistry.SOLAR_LENS_TILE.get(), p_155229_, p_155230_);
    }



    public static void tick(Level world, BlockPos post, BlockState blockState, SolarLensTile tile) {
        if (!tile.level.isClientSide){

            AABB box = new AABB(-1,-2.5,-1,1,0,1);
            if (tile.level.canSeeSky(tile.worldPosition.above()) && tile.getLevel().getDayTime() % 24000 <= 13000){

                List<ItemEntity> list = tile.level.getEntitiesOfClass(ItemEntity.class,box.move(tile.worldPosition));

                for (int i = 0;i < 4;i++){
                    if (i > list.size()-1) {
                        tile.INVENTORY.setItem(i, ItemStack.EMPTY);
                    } else {

                        tile.INVENTORY.setItem(i, list.get(i).getItem());

                    }


                }

                Optional<SolarSmeltingRecipe> recipe = tile.level.getRecipeManager().getRecipeFor(SolarForge.SOLAR_SMELTING,tile.INVENTORY,tile.level);
                if (recipe.isPresent() ){

                    tile.RECIPE_IN_PROGRESS = true;
                    tile.SMELTING_TIME = recipe.get().smeltingTime;
                    tile.CURRENT_SMELTING_TIME++;
                    int count = tile.findLeastItemResultAmount();
                    if (tile.CURRENT_SMELTING_TIME >= tile.SMELTING_TIME*count){
                        for (ItemEntity a : list){
                            a.getItem().grow(-count);
                        }
                        Vec3 pos = new Vec3(tile.worldPosition.getX()+0.5d,tile.worldPosition.getY()-1,tile.worldPosition.getZ()+0.5d);
                        ItemEntity entity = new ItemEntity(tile.level,pos.x,pos.y,pos.z,new ItemStack(recipe.get().output.getItem(),count));
                        tile.level.addFreshEntity(entity);
                        tile.SMELTING_TIME = 0;
                        tile.CURRENT_SMELTING_TIME = 0;
                        tile.RECIPE_IN_PROGRESS = false;
                    }
                }else{
                    tile.RECIPE_IN_PROGRESS = false;
                    tile.CURRENT_SMELTING_TIME = 0;
                    tile.SMELTING_TIME = 0;
                }
            }
        }
    }

    private int findLeastItemResultAmount(){
        AtomicInteger mod = new AtomicInteger(99999);
        INVENTORY.INVENTORY.forEach((ingr)->{
            if (!ingr.isEmpty()){
                int itemcount = ingr.getCount();
                if (itemcount < mod.get()){
                    mod.set(itemcount);
                }
            }
        });
        return mod.get();
    }


    @Override
    public void saveAdditional(CompoundTag cmp) {
            cmp.putInt("smelting_time",SMELTING_TIME);
            cmp.putInt("smelting_time_current",CURRENT_SMELTING_TIME);
            cmp.putBoolean("recipe_in_progress",RECIPE_IN_PROGRESS);
            super.saveAdditional(cmp);
    }

    @Override
    public void load( CompoundTag cmp) {
        SMELTING_TIME = cmp.getInt("smelting_time");
        CURRENT_SMELTING_TIME = cmp.getInt("smelting_time_current");
        RECIPE_IN_PROGRESS = cmp.getBoolean("recipe_in_progress");
        super.load( cmp);
    }
}
