package com.finderfeed.solarcraft.content.blocks.blockentities;

import com.finderfeed.solarcraft.content.blocks.blockentities.runic_energy.AbstractRunicEnergyContainer;
import com.finderfeed.solarcraft.content.items.runic_energy.RunicEnergyCost;
import com.finderfeed.solarcraft.content.items.solar_wand.IWandable;
import com.finderfeed.solarcraft.content.items.solar_wand.wand_actions.drain_runic_enenrgy_action.IREWandDrainable;
import com.finderfeed.solarcraft.content.runic_network.algorithms.RunicEnergyPath;
import com.finderfeed.solarcraft.helpers.Helpers;
import com.finderfeed.solarcraft.helpers.multiblock.MultiblockStructure;
import com.finderfeed.solarcraft.helpers.multiblock.Multiblocks;
import com.finderfeed.solarcraft.misc_things.RunicEnergy;
import com.finderfeed.solarcraft.registries.tile_entities.SolarcraftTileEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Arrays;
import java.util.List;

public class RunicEnergyCoreTile extends AbstractRunicEnergyContainer implements IREWandDrainable, IWandable {

    private boolean isDrainingEnergy = true;

    private RunicEnergyCost REQUEST = new RunicEnergyCost()
            .set(RunicEnergy.Type.ARDO, (float) getRunicEnergyLimit())
            .set(RunicEnergy.Type.TERA, (float) getRunicEnergyLimit())
            .set(RunicEnergy.Type.FIRA, (float) getRunicEnergyLimit())
            .set(RunicEnergy.Type.ZETA, (float) getRunicEnergyLimit())
            .set(RunicEnergy.Type.KELDA, (float) getRunicEnergyLimit())
            .set(RunicEnergy.Type.URBA, (float) getRunicEnergyLimit())
            .set(RunicEnergy.Type.GIRO, (float) getRunicEnergyLimit())
            .set(RunicEnergy.Type.ULTIMA, (float) getRunicEnergyLimit());

    public RunicEnergyCoreTile( BlockPos pos, BlockState state) {
        super(SolarcraftTileEntityTypes.RUNIC_ENERGY_CORE.get(), pos, state);
    }


    public static void tick(Level world,RunicEnergyCoreTile tile,BlockPos pos,BlockState state){
        if (!world.isClientSide){
            if (tile.isDrainingEnergy && tile.shouldFunction()) {
                tile.requestRunicEnergy(tile.REQUEST, 1);
            }
        }
    }

    public void setDrainingEnergy(boolean drainingEnergy) {
        if (!drainingEnergy){
            for (RunicEnergy.Type type : REQUEST.getSetTypes()){
                breakWay(type);
            }
        }
        isDrainingEnergy = drainingEnergy;

    }

    public boolean isDrainingEnergy() {
        return isDrainingEnergy;
    }

    //AbstractREContainer
    @Override
    public double getMaxRunicEnergyInput() {
        return 5;
    }

    @Override
    public double getRunicEnergyLimit() {
        return 1000000;
    }

    @Override
    public int getSeekCooldown() {
        return 40;
    }

    @Override
    public double getMaxRange() {
        return 20;
    }

    @Override
    public boolean shouldFunction() {
        return Multiblocks.RUNIC_ENERGY_CORE.check(level,worldPosition,false);
    }


    //IREWandDrainable
    @Override
    public float drainEnergy(RunicEnergy.Type type,Player player, float amount) {
        if (!player.level.isClientSide){
            float current = getRunicEnergy(type);
            float toReturn = Math.min(current,amount);
            this.giveEnergy(type,-toReturn);
            return toReturn;
        }
        return 0;
    }

    @Override
    public float returnEnergy(RunicEnergy.Type type,Player player, float amount) {
        if (!player.level.isClientSide){
            float current = this.getRunicEnergy(type);
            float r = current + amount - (float)getRunicEnergyLimit();
            this.giveEnergy(type,amount);
            this.setEnergy(type,Math.min(current + amount,(float)getRunicEnergyLimit()));
            return r > 0 ? r : 0;
        }
        return 0;
    }

    @Override
    public float getMaxEnergyDrain() {
        return 5;
    }

    private final List<RunicEnergy.Type> TYPES = Arrays.stream(RunicEnergy.Type.getAll()).toList();

    @Override
    public List<RunicEnergy.Type> allowedDrainableTypes() {
        return TYPES;
    }

    @Override
    public boolean shouldAutomaticallySwitchWandType() {
        return false;
    }

    //IWandable
    @Override
    public void onWandUse(BlockPos usePos, Player user) {
        if (!user.level.isClientSide){
            user.sendSystemMessage(Component.literal("Draining Energy: " + !isDrainingEnergy()));
            setDrainingEnergy(!isDrainingEnergy());
        }
    }
}
