package com.finderfeed.solarcraft.content.blocks.blockentities.containers;

import com.finderfeed.solarcraft.content.blocks.ModuleStation;
import com.finderfeed.solarcraft.content.items.ModuleItem;
import com.finderfeed.solarcraft.registries.containers.SCContainers;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.ItemCombinerMenu;
import net.minecraft.world.inventory.ItemCombinerMenuSlotDefinition;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.state.BlockState;

public class ModuleApplierMenu extends ItemCombinerMenu {

    private boolean valid;

    public ModuleApplierMenu( int p_39774_, Inventory p_39775_, ContainerLevelAccess p_39776_) {
        super(SCContainers.MODULE_APPLIER_CONTAINER.get(), p_39774_, p_39775_, p_39776_);


    }


    public ModuleApplierMenu( int p_39774_, Inventory p_39775_, FriendlyByteBuf buf) {
        super(SCContainers.MODULE_APPLIER_CONTAINER.get(), p_39774_, p_39775_, ContainerLevelAccess.create(p_39775_.player.level,buf.readBlockPos()));
    }

    @Override
    protected boolean mayPickup(Player player, boolean b) {
        return valid;
    }

    @Override
    protected void onTake(Player player, ItemStack itemStack) {
        itemStack.onCraftedBy(player.level, player, itemStack.getCount());
        this.shrinkStackInSlot(0);
        this.shrinkStackInSlot(1);
        this.access.execute((p_40263_, p_40264_) -> {
            p_40263_.levelEvent(1044, p_40264_, 0);
        });
    }
    private void shrinkStackInSlot(int p_40271_) {
        ItemStack var2 = this.inputSlots.getItem(p_40271_);
        var2.shrink(1);
        this.inputSlots.setItem(p_40271_, var2);
    }

    @Override
    protected boolean isValidBlock(BlockState blockState) {
        return blockState.getBlock() instanceof ModuleStation;
    }

    @Override
    public void createResult() {
        valid = isModuleValid();
    }

    @Override
    protected ItemCombinerMenuSlotDefinition createInputSlotDefinitions() {
        return ItemCombinerMenuSlotDefinition.create().withSlot(0, 27, 47, (p_266635_) -> {
            return true;
        }).withSlot(1, 76, 47, (p_266634_) -> {
            return true;
        }).withResultSlot(2, 134, 47).build();
    }

    private boolean isModuleValid(){
        ItemStack module = inputSlots.getItem(1);
        ItemStack item = inputSlots.getItem(0);
        if (module.getItem() instanceof ModuleItem moduleItem){
            if (isCompatibleWithItem(moduleItem,item)) {
                if (isItemCorrectType(moduleItem, moduleItem.getType(), item)) {
                    ItemStack result = item.copy();
                    result.getOrCreateTagElement(moduleItem.getSubTag());
                    this.resultSlots.setItem(0, result);
                    return true;
                } else {
                    this.resultSlots.setItem(0, ItemStack.EMPTY);
                    return false;
                }
            }else{
                return false;
            }
        }else{
            this.resultSlots.setItem(0, ItemStack.EMPTY);
            return false;
        }
    }

    private boolean isCompatibleWithItem(ModuleItem item,ItemStack stack){
        ModuleItem.Tags[] incompatible = item.getIncompatibleWith();
        if (incompatible != null) {
            if (incompatible.length != 0) {
                for (ModuleItem.Tags tag : incompatible) {
                    if (stack.getTagElement(tag.tag) != null) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private boolean isItemCorrectType(ModuleItem module,ModuleItem.Type type,ItemStack item){
        if (item.getTagElement(module.getSubTag()) == null) {
            if (type == ModuleItem.Type.ARMOR) {
                return item.getItem() instanceof ArmorItem;
            } else if (type == ModuleItem.Type.SWORDS) {
                return item.getItem() instanceof SwordItem;
            }else if (type == ModuleItem.Type.PICKAXES){
                return item.getItem() instanceof PickaxeItem;
            } else {
                return false;
            }
        }else{
            return false;
        }
    }



}
