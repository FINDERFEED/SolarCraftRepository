package com.finderfeed.solarforge.content.blocks.blockentities;


import com.finderfeed.solarforge.ClientHelpers;
import com.finderfeed.solarforge.Helpers;
import com.finderfeed.solarforge.client.particles.SolarcraftParticleTypes;
import com.finderfeed.solarforge.config.enchanter_config.EnchanterConfig;
import com.finderfeed.solarforge.config.enchanter_config.EnchanterConfigInit;
import com.finderfeed.solarforge.local_library.helpers.FDMathHelper;
import com.finderfeed.solarforge.content.items.runic_energy.RunicEnergyCost;
import com.finderfeed.solarforge.misc_things.RunicEnergy;
import com.finderfeed.solarforge.registries.tile_entities.SolarcraftTileEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class EnchanterBlockEntity extends REItemHandlerBlockEntity {

    public static EnchanterConfig SERVERSIDE_CONFIG = null;
    public static int RUNIC_ENERGY_LIMIT = 300000;
    public static final int MAX_ENCHANTING_TICKS = 500;
    private int enchantingTicks = 0;
    private boolean enchantingInProgress = false;
    private EnchanterConfig.ConfigEnchantmentInstance processingEnchantment = null;
    private int procesingEnchantmentLevel = 0;

    public EnchanterBlockEntity(BlockPos pos, BlockState state) {
        super(SolarcraftTileEntityTypes.ENCHANTER.get(), pos, state);
    }


    public static void tick(Level world,BlockState state,BlockPos pos,EnchanterBlockEntity enchanter){
        if (!world.isClientSide){
            if (enchanter.enchantingInProgress()){
                ItemStack stack = enchanter.getStackInSlot(0);
                int enchLevelCurrent = EnchantmentHelper.getItemEnchantmentLevel(enchanter.processingEnchantment.enchantment(),stack);
                if (!stack.isEmpty() && stack.canApplyAtEnchantingTable(enchanter.processingEnchantment.enchantment())
                        && enchLevelCurrent < enchanter.procesingEnchantmentLevel){
                    enchanter.setChanged();
                    world.sendBlockUpdated(pos,state,state,3);
                    enchanter.loadConfigIfNecessary();
//                        SERVERSIDE_CONFIG = parseJson(EnchanterConfigInit.SERVERSIDE_JSON);
                    RunicEnergyCost defaultCosts = enchanter.processingEnchantment.getCostForLevel(SERVERSIDE_CONFIG.getMode(), enchanter.procesingEnchantmentLevel);

                    if (enchanter.hasEnoughRunicEnergy(defaultCosts,1)){
                        if (enchanter.enchantingTicks++ > MAX_ENCHANTING_TICKS){
                            Map<Enchantment,Integer> enchs = new HashMap<>(EnchantmentHelper.getEnchantments(stack));
                            if (enchs.containsKey(enchanter.processingEnchantment.enchantment())){
                                enchs.remove(enchanter.processingEnchantment.enchantment());
                                enchs.put(enchanter.processingEnchantment.enchantment(),enchanter.procesingEnchantmentLevel);
                                EnchantmentHelper.setEnchantments(enchs,stack);
                            }else{
                                stack.enchant(enchanter.processingEnchantment.enchantment(),enchanter.procesingEnchantmentLevel);
                            }
                            enchanter.spendEnergy(defaultCosts, 1);
                            enchanter.reset();
                            enchanter.level.playSound(null,enchanter.worldPosition.getX(),enchanter.worldPosition.getY(),enchanter.worldPosition.getZ(),
                                    SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.BLOCKS,
                                    1,1);
                        }
                        enchanter.nullOrGiverPositionForClient.clear();
                        enchanter.onRemove();
                        enchanter.clearWays();
                    }else{
                        enchanter.requestRunicEnergy(defaultCosts, 1);
                    }
                }else{
                    enchanter.reset();
                    enchanter.setChanged();
                    world.sendBlockUpdated(pos,state,state,3);
                }
            }else{
                enchanter.reset();
            }
        }else{
            if (enchanter.enchantingInProgress() ) {
                Vec3 center = Helpers.getBlockCenter(pos);
                ClientHelpers.Particles.verticalCircle(
                        SolarcraftParticleTypes.SMALL_SOLAR_STRIKE_PARTICLE.get(), center.add(0,-0.25,0), 0.75, 3, new float[]{0, 0, 0},
                        () -> 255, () -> 255, () -> 0, 0.25f
                );

                for (int i = 0; i < 3; i++) {
                    double[] xz = FDMathHelper.rotatePointDegrees(0.5, 0, i * 120 + 120 * Math.sin(world.getGameTime() / 20f));
                    ClientHelpers.Particles.createParticle(SolarcraftParticleTypes.SMALL_SOLAR_STRIKE_PARTICLE.get(),
                            center.x + xz[0], center.y - 0.2, center.z + xz[1], 0, 0.07, 0, () -> 255, () -> 255, () -> 0, 0.25f);
                }
            }

        }
    }

    public EnchanterConfig.ConfigEnchantmentInstance getProcessingEnchantment() {
        return processingEnchantment;
    }

    public int getProcesingEnchantmentLevel() {
        return procesingEnchantmentLevel;
    }

    public int getEnchantingTicks() {
        return enchantingTicks;
    }

    private void reset(){
        this.procesingEnchantmentLevel = -1;
        this.enchantingInProgress = false;
        this.processingEnchantment = null;
        this.enchantingTicks = -1;
        this.onRemove();
        this.clearWays();
        nullOrGiverPositionForClient.clear();
    }



    public void triggerEnchanting(Enchantment enchantment, int level){
        if (SERVERSIDE_CONFIG.getConfigEntryByEnchantment(enchantment) == null) return;
        Map<Enchantment,Integer> enchs = new HashMap<>(EnchantmentHelper.getEnchantments(getStackInSlot(0)));
        for (Enchantment e : enchs.keySet()){
            if (!e.isCompatibleWith(enchantment)){
                return;
            }
        }

        this.level.playSound(null,worldPosition.getX(),worldPosition.getY(),worldPosition.getZ(), SoundEvents.BEACON_ACTIVATE, SoundSource.BLOCKS,
                1,1);
        this.processingEnchantment = SERVERSIDE_CONFIG.getConfigEntryByEnchantment(enchantment);
        this.procesingEnchantmentLevel = level;
        this.enchantingInProgress = true;
    }
    public boolean enchantingInProgress() {
        return enchantingInProgress;
    }

    @Override
    public void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putInt("ench_level",procesingEnchantmentLevel);
        tag.putInt("enchanting_ticks",enchantingTicks);
        tag.putBoolean("in_progress",enchantingInProgress);
        if (processingEnchantment != null) {
            tag.putString("enchantment",processingEnchantment.enchantment().getRegistryName().toString());
        }else{
            tag.putString("enchantment","null");
        }
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        procesingEnchantmentLevel = tag.getInt("ench_level");
        enchantingTicks = tag.getInt("enchanting_ticks");
        enchantingInProgress = tag.getBoolean("in_progress");
        String enchantment = tag.getString("enchantment");
        if (!enchantment.equals("null")){
            this.processingEnchantment = SERVERSIDE_CONFIG.getConfigEntryByEnchantment(ForgeRegistries.ENCHANTMENTS.getValue(new ResourceLocation(enchantment)));
        }else{
            processingEnchantment = null;
        }
        if (level != null && !level.isClientSide && SERVERSIDE_CONFIG == null){
//            SERVERSIDE_CONFIG = parseJson(EnchanterConfigInit.SERVERSIDE_JSON);
            SERVERSIDE_CONFIG = new EnchanterConfig(EnchanterConfigInit.SERVERSIDE_JSON);
        }
    }

    @Nullable
    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        CompoundTag tag1 = saveWithFullMetadata();
        CompoundTag tag = super.getUpdatePacket().getTag();
        tag1.merge(tag);
        return Helpers.createTilePacket(this,tag1);
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        super.onDataPacket(net, pkt);
        this.load(pkt.getTag());
    }

    @Override
    public double getMaxEnergyInput() {
        return 10;
    }

    @Override
    public double getRunicEnergyLimit() {
        this.loadConfigIfNecessary();
        return SERVERSIDE_CONFIG.getMaxEnchanterRunicEnergyCapacity();
    }

    @Override
    public int getSeekingCooldown() {
        return 20;
    }

    @Override
    public boolean shouldFunction() {
        return true;
    }

    @Override
    public double getMaxRange() {
        return 16;
    }




    public void loadConfigIfNecessary(){
        if (level.isClientSide) return;
        if (SERVERSIDE_CONFIG != null) return;
        SERVERSIDE_CONFIG = new EnchanterConfig(EnchanterConfigInit.SERVERSIDE_JSON);
    }

    @Override
    public AABB getRenderBoundingBox() {
        return new AABB(-getMaxRange(),-getMaxRange(),-getMaxRange(),getMaxRange(),getMaxRange(),getMaxRange()).move(worldPosition);
    }

    public static class RunicEnergyCostConstructor{

        public Map<RunicEnergy.Type,Double> COSTS=new HashMap<>();

        public RunicEnergyCostConstructor(){}

        public RunicEnergyCostConstructor addRunicEnergy(RunicEnergy.Type type, double amount){
            COSTS.put(type,amount);
            return this;
        }
    }
}
