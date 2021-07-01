package com.finderfeed.solarforge.magic_items.blocks.blockentities;

import com.finderfeed.solarforge.registries.tile_entities.TileEntitiesRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.vector.Vector3d;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MagnetBlockTile extends TileEntity implements ITickableTileEntity {

    public AxisAlignedBB box;

    public MagnetBlockTile() {
        super(TileEntitiesRegistry.MAGNET_BLOCK_TILE.get());
    }

    @Override
    public void tick() {
        if (!this.level.isClientSide && this.level.hasNeighborSignal(this.worldPosition)){
            List<ItemEntity> list = this.level.getEntitiesOfClass(ItemEntity.class,getBox());

            List<ItemEntity> listToSuckIn = this.level.getEntitiesOfClass(ItemEntity.class,new AxisAlignedBB(-1,0,-1,1,2,1).move(this.worldPosition));
            for (ItemEntity a : list){

                Vector3d movement = new Vector3d(-a.position().x + worldPosition.getX()+0.5d,-a.position().y + worldPosition.getY()+1.5d,-a.position().z + worldPosition.getZ()+0.5d);
                a.setDeltaMovement(movement.normalize().multiply(0.1,0.1,0.1));
            }

            for (ItemEntity b : listToSuckIn){
                if (this.level.getBlockEntity(worldPosition.below()) instanceof IInventory){
                    ItemStack stack = b.getItem();
                    IInventory inventory = (IInventory) this.level.getBlockEntity(worldPosition.below());
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
                            b.remove();
                        }else{
                            ItemStack stack1 = stack.copy();
                            stack1.grow(-(inventory.getItem(slot).getMaxStackSize()-inventory.getItem(slot).getCount()));
                            inventory.setItem(slot,new ItemStack(inventory.getItem(slot).getItem(),inventory.getItem(slot).getMaxStackSize()));
                            b.remove();
                            ItemEntity ent = new ItemEntity(this.level,worldPosition.getX(),worldPosition.getY()+1.5d,worldPosition.getZ(),stack1);
                            this.level.addFreshEntity(ent);
                        }

                    }

                }
            }
        }
    }

    public AxisAlignedBB getBox() {
        box = new AxisAlignedBB(-20,-10,-20,20,10,20).move(this.worldPosition);
        return box;
    }


    public static int canPlaceItem(IInventory inventory,Item it){
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
