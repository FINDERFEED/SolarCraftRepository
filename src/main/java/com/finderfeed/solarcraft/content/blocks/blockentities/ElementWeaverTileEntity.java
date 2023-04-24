package com.finderfeed.solarcraft.content.blocks.blockentities;

import com.finderfeed.solarcraft.content.items.runic_energy.RunicEnergyCost;
import com.finderfeed.solarcraft.helpers.Helpers;
import com.finderfeed.solarcraft.misc_things.RunicEnergy;
import com.finderfeed.solarcraft.registries.ConfigRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class ElementWeaverTileEntity extends REItemHandlerBlockEntity{

    public final RunicEnergyCost REQUEST = new RunicEnergyCost()
            .set(RunicEnergy.Type.ARDO,getRunicEnergyLimit())
            .set(RunicEnergy.Type.TERA,getRunicEnergyLimit())
            .set(RunicEnergy.Type.FIRA,getRunicEnergyLimit())
            .set(RunicEnergy.Type.KELDA,getRunicEnergyLimit())
            .set(RunicEnergy.Type.URBA,getRunicEnergyLimit())
            .set(RunicEnergy.Type.GIRO,getRunicEnergyLimit())
            .set(RunicEnergy.Type.ULTIMA,getRunicEnergyLimit())
            .set(RunicEnergy.Type.ZETA,getRunicEnergyLimit())
            .immutable();

    public static final int MAX_PROCESSING_TIME = 200;
    public static final float LIMIT = 1000000;
    private Item processingItem;
    private RunicEnergyCost processingItemCost;
    private int processingTime;
    private boolean active;

    public ElementWeaverTileEntity(BlockEntityType<?> p_155228_, BlockPos p_155229_, BlockState p_155230_) {
        super(p_155228_, p_155229_, p_155230_);
    }


    public static void tick(ElementWeaverTileEntity tile){
        Level world = tile.level;
        if (!world.isClientSide){
            energyDrain(tile);
            processItems(tile);
        }
    }

    private static void processItems(ElementWeaverTileEntity tile){
        if (!tile.level.isClientSide) {
            ItemStack input = tile.inputSlot();
            ItemStack output = tile.outputSlot();
            if (!input.isEmpty()){
                tile.processingItem = input.getItem();
                tile.processingItemCost = ConfigRegistry.ITEM_RE_CONFIG.getItemCost(input.getItem());
                if (output.isEmpty() || (output.getItem() == input.getItem() && output.getCount() < output.getMaxStackSize())){
                    createItems(tile,output);
                }else{
                    tile.processingTime = 0;
                }
            }else{
                tile.processingItem = null;
                tile.processingItemCost = null;
                tile.processingTime = 0;
            }
        }

    }

    private static void createItems(ElementWeaverTileEntity tile,ItemStack output){
        if (tile.hasEnoughRunicEnergy(tile.processingItemCost,1)){
            if (tile.processingTime >= MAX_PROCESSING_TIME){
                output.grow(1);
                tile.spendEnergy(tile.processingItemCost,1);
            }
            tile.processingTime += 1;
        }else{
            tile.processingTime = 0;
        }
    }


    private static void energyDrain(ElementWeaverTileEntity tile){
        if (tile.isActive()){
            tile.requestRunicEnergy(tile.REQUEST,1);
        }
    }

    public boolean isActive() {
        return active;
    }

    public void setInputSlot(ItemStack set){
        this.setStackInSlot(0,set);
    }

    public void setOutputSlot(ItemStack set){
        this.setStackInSlot(1,set);
    }

    public ItemStack inputSlot(){
        return this.getStackInSlot(0);
    }

    public ItemStack outputSlot(){
        return this.getStackInSlot(1);
    }

    public void onUse(){
        if (!level.isClientSide) {
            this.active = !active;
            Helpers.updateTile(this);
        }
    }


    public RunicEnergyCost getProcessingItemCost() {
        return processingItemCost;
    }

    public int getProcessingTime() {
        return processingTime;
    }

    public Item getProcessingItem() {
        return processingItem;
    }

    @Override
    public float getMaxRunicEnergyInput() {
        return 2;
    }

    @Override
    public float getRunicEnergyLimit() {
        return LIMIT;
    }

    @Override
    public int getSeekCooldown() {
        return 100;
    }

    @Override
    public double getMaxRange() {
        return 20;
    }

    @Override
    public boolean shouldFunction() {
        return true;
    }


}
