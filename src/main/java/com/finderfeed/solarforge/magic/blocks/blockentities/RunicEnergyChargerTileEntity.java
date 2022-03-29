package com.finderfeed.solarforge.magic.blocks.blockentities;

import com.finderfeed.solarforge.Helpers;
import com.finderfeed.solarforge.abilities.ability_classes.AbstractAbility;
import com.finderfeed.solarforge.magic.items.runic_energy.IRunicEnergyUser;
import com.finderfeed.solarforge.misc_things.RunicEnergy;
import com.finderfeed.solarforge.registries.tile_entities.TileEntitiesRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.ItemStackHandler;

import java.util.HashMap;
import java.util.Map;

public class RunicEnergyChargerTileEntity extends REItemHandlerBlockEntity {



    public RunicEnergyChargerTileEntity( BlockPos pos, BlockState state) {
        super(TileEntitiesRegistry.RUNIC_ENERGY_CHARGER.get(), pos, state);
    }
//TODO:dodelat
    public static void tick(RunicEnergyChargerTileEntity tile, Level world,BlockState state,BlockPos pos){
        if (!world.isClientSide){
            ItemStackHandler inventory = tile.getInventory();
            if (inventory != null){
                ItemStack chargeItem = tile.chargeSlot();
                if (chargeItem.getItem() instanceof IRunicEnergyUser user){
                    Map<RunicEnergy.Type,Double> request = new HashMap<>();
                    for (RunicEnergy.Type type : user.allowedInputs()){
                        request.put(type,tile.getRunicEnergyLimit());
                    }
                    tile.requestRunicEnergy(request,1);
                }else{
                    tile.clearWays();
                }
            }
        }
    }

    public ItemStack chargeSlot(){
        ItemStackHandler inv = this.getInventory();
        if (inv == null) return ItemStack.EMPTY;
        return inv.getStackInSlot(0);
    }

    public ItemStack catalystSlot(){
        ItemStackHandler inv = this.getInventory();
        if (inv == null) return ItemStack.EMPTY;
        return inv.getStackInSlot(1);
    }

    @Override
    public double getMaxEnergyInput() {
        return 2.5;
    }

    @Override
    public double getRunicEnergyLimit() {
        return 100000;
    }

    @Override
    public int getSeekingCooldown() {
        return 40;
    }

    @Override
    public double getMaxRange() {
        return 16;
    }

    @Override
    public boolean shouldFunction() {
        return true;
    }
}
