package com.finderfeed.solarcraft.content.blocks.blockentities;

import com.finderfeed.solarcraft.client.particles.SolarcraftParticleTypes;
import com.finderfeed.solarcraft.content.blocks.blockentities.runic_energy.AbstractRunicEnergyContainer;
import com.finderfeed.solarcraft.content.items.runic_energy.RunicEnergyCost;
import com.finderfeed.solarcraft.content.items.solar_wand.IWandable;
import com.finderfeed.solarcraft.content.items.solar_wand.wand_actions.drain_runic_enenrgy_action.IREWandDrainable;
import com.finderfeed.solarcraft.content.items.solar_wand.wand_actions.structure_check.IStructureOwner;
import com.finderfeed.solarcraft.content.runic_network.algorithms.RunicEnergyPath;
import com.finderfeed.solarcraft.helpers.ClientHelpers;
import com.finderfeed.solarcraft.helpers.Helpers;
import com.finderfeed.solarcraft.helpers.multiblock.MultiblockStructure;
import com.finderfeed.solarcraft.helpers.multiblock.Multiblocks;
import com.finderfeed.solarcraft.misc_things.RunicEnergy;
import com.finderfeed.solarcraft.registries.tile_entities.SolarcraftTileEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

public class RunicEnergyCoreTile extends AbstractRunicEnergyContainer implements IREWandDrainable, IWandable, IStructureOwner {

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
        }else{
            if (tile.isDrainingEnergy() && tile.shouldFunction()) {
                Vec3 v = new Vec3(1, 0, 0).yRot((float) (Math.PI * world.random.nextFloat()))
                        .xRot((float) (Math.PI * 2 * world.random.nextFloat()));
                Vec3 l = v.add(Helpers.getBlockCenter(pos));

                ClientHelpers.Particles.createParticle(SolarcraftParticleTypes.SMALL_SOLAR_STRIKE_PARTICLE.get(),
                        l.x, l.y, l.z, v.x * 0.025, v.y * 0.025, v.z * 0.025, 230 + world.random.nextInt(25),
                        230 + world.random.nextInt(25), world.random.nextInt(25), 0.25f);
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
        if (!level.isClientSide) {
            Helpers.updateTile(this);
        }

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

    @Override
    public void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putBoolean("drainingEnergy",isDrainingEnergy());
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        isDrainingEnergy = tag.getBoolean("drainingEnergy");
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        super.onDataPacket(net, pkt);
        isDrainingEnergy = pkt.getTag().getBoolean("drainingEnergy");
    }

    @Nullable
    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {

        ClientboundBlockEntityDataPacket pkt = super.getUpdatePacket();
        pkt.getTag().putBoolean("drainingEnergy",isDrainingEnergy);
        return pkt;
    }

    @Override
    public List<MultiblockStructure> getMultiblocks() {
        return List.of(Multiblocks.RUNIC_ENERGY_CORE);
    }
}
