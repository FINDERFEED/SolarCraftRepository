package com.finderfeed.solarcraft.content.blocks.blockentities;

import com.finderfeed.solarcraft.helpers.Helpers;
import com.finderfeed.solarcraft.config.SolarcraftConfig;
import com.finderfeed.solarcraft.content.blocks.blockentities.runic_energy.IRunicEnergySaver;
import com.finderfeed.solarcraft.content.items.RuneItem;
import com.finderfeed.solarcraft.content.items.runic_energy.IRunicEnergyUser;
import com.finderfeed.solarcraft.content.items.runic_energy.ItemRunicEnergy;
import com.finderfeed.solarcraft.content.items.runic_energy.RunicEnergyCost;
import com.finderfeed.solarcraft.misc_things.RunicEnergy;
import com.finderfeed.solarcraft.registries.items.SolarcraftItems;
import com.finderfeed.solarcraft.registries.tile_entities.SolarcraftTileEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

public class RunicEnergyChargerTileEntity extends REItemHandlerBlockEntity implements IRunicEnergySaver {

    private int updateTicker = 0;
    private static final float CHARGE_RATE_PER_TICK = 2.5f;

    public RunicEnergyChargerTileEntity( BlockPos pos, BlockState state) {
        super(SolarcraftTileEntityTypes.RUNIC_ENERGY_CHARGER.get(), pos, state);
    }
    public static void tick(RunicEnergyChargerTileEntity tile, Level world,BlockState state,BlockPos pos){
        if (!world.isClientSide){
            ItemStackHandler inventory = tile.getInventory();
            if (inventory != null){
                manageItemCharging(tile);
                chargeWithRunes(tile);
            }
        }
    }

    private static void chargeWithRunes(RunicEnergyChargerTileEntity tile){
        ItemStack rune = tile.runeSlot();
        if (rune.getItem() instanceof RuneItem item && tile.getRunicEnergy(item.type) < tile.getRunicEnergyLimit()){
            rune.shrink(1);
            tile.giveEnergy(item.type,Math.min(SolarcraftConfig.RUNIC_ENERGY_PER_RUNE_CHARGER.get(),tile.getRunicEnergyLimit() - tile.getRunicEnergy(item.type)));
            Helpers.updateTile(tile);
        }
    }

    private static void manageItemCharging(RunicEnergyChargerTileEntity tile){
        ItemStack chargeItem = tile.chargeSlot();
        tile.setChanged();
        if (chargeItem.getItem() instanceof IRunicEnergyUser user && !ItemRunicEnergy.isFullyCharged(chargeItem,user)){
            RunicEnergyCost request = new RunicEnergyCost();
            for (RunicEnergy.Type type : user.allowedInputs()){
                if (!ItemRunicEnergy.hasChargedEnergy(chargeItem,user,type)) {
                    request.set(type, (float) tile.getRunicEnergyLimit());
                    double currentEnergy = tile.getRunicEnergy(type);
                    if (currentEnergy > 0){
                        float toAddToItem = (float)Math.max(0,Math.min(CHARGE_RATE_PER_TICK,currentEnergy));
                        float delta = ItemRunicEnergy.addRunicEnergy(chargeItem,user,type,toAddToItem);
                        tile.giveEnergy(type,delta-toAddToItem);
                    }
                }else{
                    tile.breakWay(type);
                }
            }
            tile.requestRunicEnergy(request,1);
            boolean charged = ItemRunicEnergy.isFullyCharged(chargeItem,user);
            if (charged){
                Helpers.updateTile(tile);
                tile.resetAllRepeaters();
                tile.clearWays();
                tile.updateTicker = 0;
            }
            if (tile.updateTicker++ >= 3) {
                Helpers.updateTile(tile);
                tile.updateTicker = 0;
            }
        }
    }

    @Nullable
    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        ClientboundBlockEntityDataPacket packet = super.getUpdatePacket();
        this.saveAdditional(packet.getTag());
        return packet;
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        this.load(pkt.getTag());

    }

    public ItemStack chargeSlot(){
        ItemStackHandler inv = this.getInventory();
        if (inv == null) return ItemStack.EMPTY;
        return inv.getStackInSlot(1);
    }

    public ItemStack runeSlot(){
        ItemStackHandler inv = this.getInventory();
        if (inv == null) return ItemStack.EMPTY;
        return inv.getStackInSlot(0);
    }

    @Override
    public double getMaxEnergyInput() {
        return 2.5;
    }

    @Override
    public double getRunicEnergyLimit() {
        return 10000;
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

    @Override
    public ItemStack droppedStack() {
        return IRunicEnergySaver.defaultSave(SolarcraftItems.RUNIC_ENERGY_CHARGER.get().getDefaultInstance(),this);
    }
}
