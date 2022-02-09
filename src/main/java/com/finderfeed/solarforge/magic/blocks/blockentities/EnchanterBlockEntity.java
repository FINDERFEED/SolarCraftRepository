package com.finderfeed.solarforge.magic.blocks.blockentities;


import com.finderfeed.solarforge.ClientHelpers;
import com.finderfeed.solarforge.Helpers;
import com.finderfeed.solarforge.client.particles.ParticleTypesRegistry;
import com.finderfeed.solarforge.config.EnchantmentsConfig;
import com.finderfeed.solarforge.local_library.helpers.FinderfeedMathHelper;
import com.finderfeed.solarforge.misc_things.RunicEnergy;
import com.finderfeed.solarforge.registries.tile_entities.TileEntitiesRegistry;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
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

    public static Map<Enchantment, Map<RunicEnergy.Type,Double>> SERVERSIDE_CONFIG = null;
    public static int RUNIC_ENERGY_LIMIT = 300000;
    public static final int MAX_ENCHANTING_TICKS = 500;
    private int enchantingTicks = 0;
    private boolean enchantingInProgress = false;
    private Enchantment processingEnchantment = null;
    private int procesingEnchantmentLevel = 0;

    public EnchanterBlockEntity(BlockPos pos, BlockState state) {
        super(TileEntitiesRegistry.ENCHANTER.get(), pos, state);
    }


    public static void tick(Level world,BlockState state,BlockPos pos,EnchanterBlockEntity enchanter){
        if (!world.isClientSide){
            if (enchanter.enchantingInProgress()){
                ItemStack stack = enchanter.getStackInSlot(0);
                int enchLevelCurrent = EnchantmentHelper.getItemEnchantmentLevel(enchanter.processingEnchantment,stack);
                if (!stack.isEmpty() && stack.canApplyAtEnchantingTable(enchanter.processingEnchantment)
                        && enchLevelCurrent < enchanter.procesingEnchantmentLevel){
                    enchanter.setChanged();
                    world.sendBlockUpdated(pos,state,state,3);
                    if (SERVERSIDE_CONFIG == null) SERVERSIDE_CONFIG = parseJson(EnchantmentsConfig.SERVERSIDE_JSON);
                    Map<RunicEnergy.Type,Double> defaultCosts = SERVERSIDE_CONFIG.get(enchanter.processingEnchantment);
                    if (enchanter.hasEnoughRunicEnergy(defaultCosts,enchanter.procesingEnchantmentLevel)){
                        if (enchanter.enchantingTicks++ > MAX_ENCHANTING_TICKS){
                            Map<Enchantment,Integer> enchs = new HashMap<>(EnchantmentHelper.getEnchantments(stack));
                            if (enchs.containsKey(enchanter.processingEnchantment)){
                                enchs.remove(enchanter.processingEnchantment);
                                enchs.put(enchanter.processingEnchantment,enchanter.procesingEnchantmentLevel);
                                EnchantmentHelper.setEnchantments(enchs,stack);
                            }else{
                                stack.enchant(enchanter.processingEnchantment,enchanter.procesingEnchantmentLevel);
                            }
                            enchanter.spendEnergy(defaultCosts, enchanter.procesingEnchantmentLevel);
                            enchanter.reset();
                        }
                        enchanter.nullOrGiverPositionForClient.clear();
                        enchanter.onRemove();
                        enchanter.clearWays();
                    }else{
                        enchanter.requestRunicEnergy(defaultCosts, enchanter.procesingEnchantmentLevel);
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
                ClientHelpers.ParticleAnimationHelper.verticalCircle(
                        ParticleTypesRegistry.SMALL_SOLAR_STRIKE_PARTICLE.get(), center.add(0,-0.25,0), 0.75, 3, new float[]{0, 0, 0},
                        () -> 255, () -> 255, () -> 0, 0.25f
                );

                for (int i = 0; i < 3; i++) {
                    double[] xz = FinderfeedMathHelper.rotatePointDegrees(0.5, 0, i * 120 + 120 * Math.sin(world.getGameTime() / 20f));
                    ClientHelpers.ParticleAnimationHelper.createParticle(ParticleTypesRegistry.SMALL_SOLAR_STRIKE_PARTICLE.get(),
                            center.x + xz[0], center.y - 0.2, center.z + xz[1], 0, 0.07, 0, () -> 255, () -> 255, () -> 0, 0.25f);
                }
            }

        }
    }

    public Enchantment getProcessingEnchantment() {
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
        this.processingEnchantment = enchantment;
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
            tag.putString("enchantment",processingEnchantment.getRegistryName().toString());
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
            this.processingEnchantment = ForgeRegistries.ENCHANTMENTS.getValue(new ResourceLocation(enchantment));
        }else{
            processingEnchantment = null;
        }
        if (level != null && !level.isClientSide && SERVERSIDE_CONFIG == null){
            SERVERSIDE_CONFIG = parseJson(EnchantmentsConfig.SERVERSIDE_JSON);
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
        return 25;
    }

    @Override
    public double getRunicEnergyLimit() {
        return RUNIC_ENERGY_LIMIT;
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


    public static Map<Enchantment, Map<RunicEnergy.Type,Double>> parseJson(JsonObject object){
        Map<Enchantment, Map<RunicEnergy.Type,Double>> costs = new HashMap<>();
        JsonArray array = object.getAsJsonArray("enchantments");
        for (JsonElement enchantmentElement : array){
            JsonObject enchantmentJSON = enchantmentElement.getAsJsonObject();
            Enchantment enchantment = ForgeRegistries.ENCHANTMENTS.getValue(new ResourceLocation(GsonHelper.getAsString(enchantmentJSON,"enchantment_id")));
            float ardo    = GsonHelper.getAsFloat(enchantmentJSON,"ardo",0 );
            float zeta = GsonHelper.getAsFloat(enchantmentJSON,"zeta",0 );
            float tera = GsonHelper.getAsFloat(enchantmentJSON,"tera",0 );
            float kelda = GsonHelper.getAsFloat(enchantmentJSON,"kelda",0);
            float urba = GsonHelper.getAsFloat(enchantmentJSON,"urba",0);
            float fira = GsonHelper.getAsFloat(enchantmentJSON,"fira",0);
            float giro = GsonHelper.getAsFloat(enchantmentJSON,"giro",0);
            float ultima = GsonHelper.getAsFloat(enchantmentJSON,"ultima",0);

            Map<RunicEnergy.Type,Double> runicCosts = new RunicEnergyCostConstructor()
                    .addRunicEnergy(RunicEnergy.Type.ARDO,  ardo)
                    .addRunicEnergy(RunicEnergy.Type.ZETA,  zeta)
                    .addRunicEnergy(RunicEnergy.Type.TERA,  tera)
                    .addRunicEnergy(RunicEnergy.Type.KELDA, kelda)
                    .addRunicEnergy(RunicEnergy.Type.URBA,  urba)
                    .addRunicEnergy(RunicEnergy.Type.FIRA,  fira)
                    .addRunicEnergy(RunicEnergy.Type.GIRO,  giro)
                    .addRunicEnergy(RunicEnergy.Type.ULTIMA,ultima)
                    .COSTS;
            costs.put(enchantment,runicCosts);
        }
        return costs;
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
