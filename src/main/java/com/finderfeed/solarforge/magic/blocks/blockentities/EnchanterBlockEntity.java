package com.finderfeed.solarforge.magic.blocks.blockentities;


import com.finderfeed.solarforge.config.EnchantmentsConfig;
import com.finderfeed.solarforge.misc_things.RunicEnergy;
import com.finderfeed.solarforge.registries.tile_entities.TileEntitiesRegistry;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.ForgeRegistries;

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
                if (!stack.isEmpty() && stack.canApplyAtEnchantingTable(enchanter.processingEnchantment)){
                    enchanter.setChanged();
                    world.sendBlockUpdated(pos,state,state,3);

                    Map<RunicEnergy.Type,Double> defaultCosts = SERVERSIDE_CONFIG.get(enchanter.processingEnchantment);
                    if (enchanter.hasEnoughRunicEnergy(defaultCosts,enchanter.procesingEnchantmentLevel)){
                        if (enchanter.enchantingTicks++ > MAX_ENCHANTING_TICKS){
                            stack.enchant(enchanter.processingEnchantment,enchanter.procesingEnchantmentLevel);
                            enchanter.spendEnergy(defaultCosts, enchanter.procesingEnchantmentLevel);
                            enchanter.reset();
                        }
                    }else{
                        enchanter.requestRunicEnergy(defaultCosts, enchanter.procesingEnchantmentLevel);
                    }
                }else{
                    enchanter.reset();
                }
            }else{
                enchanter.reset();
            }
        }else{

        }
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



    @Override
    public double getMaxEnergyInput() {
        return 10;
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


    public static class RunicEnergyCostConstructor{

        public Map<RunicEnergy.Type,Double> COSTS=new HashMap<>();

        public RunicEnergyCostConstructor(){}

        public RunicEnergyCostConstructor addRunicEnergy(RunicEnergy.Type type, double amount){
            COSTS.put(type,amount);
            return this;
        }
    }
}
