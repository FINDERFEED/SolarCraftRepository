package com.finderfeed.solarcraft.content.blocks.blockentities;

import com.finderfeed.solarcraft.registries.tile_entities.SolarcraftTileEntityTypes;
import net.minecraft.core.BlockPos;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.Container;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

import net.minecraft.world.level.block.entity.BlockEntity;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class MagnetBlockTile extends BlockEntity  {

    public AABB box;

    public MagnetBlockTile( BlockPos p_155229_, BlockState p_155230_) {
        super(SolarcraftTileEntityTypes.MAGNET_BLOCK_TILE.get(), p_155229_, p_155230_);
    }



    public static void tick(Level world, BlockPos pos, BlockState blockState, MagnetBlockTile tile) {
        if (!tile.level.isClientSide && tile.level.hasNeighborSignal(tile.worldPosition)){
            List<ItemEntity> list = tile.level.getEntitiesOfClass(ItemEntity.class,tile.getBox());

            List<ItemEntity> listToSuckIn = tile.level.getEntitiesOfClass(ItemEntity.class,new AABB(-1,0,-1,1,2,1).move(tile.worldPosition));
            for (ItemEntity a : list){

                Vec3 movement = new Vec3(-a.position().x + tile.worldPosition.getX()+0.5d,-a.position().y + tile.worldPosition.getY()+1.5d,-a.position().z + tile.worldPosition.getZ()+0.5d);
                a.setDeltaMovement(movement.normalize().multiply(0.1,0.1,0.1));
            }

            for (ItemEntity b : listToSuckIn){
                if (tile.level.getBlockEntity(tile.worldPosition.below()) instanceof Container){
                    ItemStack stack = b.getItem();
                    Container inventory = (Container) tile.level.getBlockEntity(tile.worldPosition.below());
                    int slot = canPlaceItem(inventory,stack.getItem());
                    if (slot != -1){
//                        if (inventory.getItem(slot).getItem() == Items.AIR ){
//                            inventory.setItem(slot,stack);
//                        }else{
//                            //int r = insertItem(inventory,slot,stack,stack.getCount());
//                            insertItem(inventory,slot,stack,stack.getCount());
//                            //System.out.println(r);
////                            ItemEntity ent = new ItemEntity(this.level,worldPosition.getX(),worldPosition.getY()+1.5d,worldPosition.getZ(),new ItemStack(stack.getItem(),r));
////                            this.level.addFreshEntity(ent);
//                        }
//                        b.remove();
                        if (stack.getCount() + inventory.getItem(slot).getCount() <= inventory.getItem(slot).getMaxStackSize()){
                            ItemStack stack1 = stack.copy();
                            stack1.grow(inventory.getItem(slot).getCount());
                            inventory.setItem(slot,stack1);
                            b.remove(Entity.RemovalReason.KILLED);
                        }else{
                            ItemStack stack1 = stack.copy();
                            stack1.grow(-(inventory.getItem(slot).getMaxStackSize()-inventory.getItem(slot).getCount()));
                            inventory.setItem(slot,new ItemStack(inventory.getItem(slot).getItem(),inventory.getItem(slot).getMaxStackSize()));
                            b.remove(Entity.RemovalReason.KILLED);
                            ItemEntity ent = new ItemEntity(tile.level,tile.worldPosition.getX(),tile.worldPosition.getY()+1.5d,tile.worldPosition.getZ(),stack1);
                            tile.level.addFreshEntity(ent);
                        }

                    }

                }
            }
        }
    }

    public AABB getBox() {
        box = new AABB(-20,-10,-20,20,10,20).move(this.worldPosition);
        return box;
    }


    public static int canPlaceItem(Container inventory,Item it){
        for (int i = 0;i < inventory.getContainerSize();i++){
            if (inventory.getItem(i).getItem() == Items.AIR || ((inventory.getItem(i)).getItem() == it && inventory.getItem(i).getCount() != inventory.getItem(i).getMaxStackSize())){
                return i;
            }
        }
        return -1;
    }

//    public synchronized int insertItem(IInventory inventory,int slot,ItemStack stackToInsert,int remainingStacks){
//        if (inventory.getItem(slot).getCount()+remainingStacks <= inventory.getItem(slot).getMaxStackSize()){
//            ItemStack stack = inventory.getItem(slot);
//            stack.grow(remainingStacks);
//            inventory.setItem(slot,stack);
//            inventory.setChanged();
//        }else{
//            stackToInsert.grow( -(inventory.getItem(slot).getMaxStackSize() - inventory.getItem(slot).getCount()));
//            int r = stackToInsert.getCount() - (inventory.getItem(slot).getMaxStackSize() - inventory.getItem(slot).getCount());
//
//            inventory.setItem(slot,new ItemStack(inventory.getItem(slot).getItem(),inventory.getItem(slot).getMaxStackSize()));
//            inventory.setChanged();
//            int nextSlot = canPlaceItem(inventory,stackToInsert.getItem());
//            if (nextSlot != -1){
//                insertItem(inventory,nextSlot,stackToInsert,stackToInsert.getCount());
//            }else{
//                ItemEntity ent = new ItemEntity(this.level,worldPosition.getX(),worldPosition.getY()+1.5d,worldPosition.getZ(),stackToInsert);
//                this.level.addFreshEntity(ent);
//                return stackToInsert.getCount();
//            }
//        }
//        return 0;
//    }


}
