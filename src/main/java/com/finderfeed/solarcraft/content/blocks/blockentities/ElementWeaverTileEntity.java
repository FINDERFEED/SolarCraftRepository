package com.finderfeed.solarcraft.content.blocks.blockentities;

import com.finderfeed.solarcraft.client.particles.SolarcraftParticleTypes;
import com.finderfeed.solarcraft.content.items.runic_energy.RunicEnergyCost;
import com.finderfeed.solarcraft.helpers.ClientHelpers;
import com.finderfeed.solarcraft.helpers.Helpers;
import com.finderfeed.solarcraft.misc_things.RunicEnergy;
import com.finderfeed.solarcraft.registries.ConfigRegistry;
import com.finderfeed.solarcraft.registries.tile_entities.SolarcraftTileEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

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

    public static final int MAX_PROCESSING_TIME = 100;
    public static final float LIMIT = 1000000;
    private Item processingItem;
    private RunicEnergyCost processingItemCost;
    private int processingTime;
    private boolean active;

    public ElementWeaverTileEntity(BlockPos p_155229_, BlockState p_155230_) {
        super(SolarcraftTileEntityTypes.ELEMENT_WEAVER.get(), p_155229_, p_155230_);
    }


    public static void tick(ElementWeaverTileEntity tile){
        Level world = tile.level;
        if (!world.isClientSide){
            energyDrain(tile);
            processItems(tile);
        }else{
            if (tile.level.getGameTime() % 20 == 0) {
                float k = 0.2f;
                for (int i = 0; i < 2; i++) {
                    for (int g = 0; g < 2; g++) {
                        if (tile.isActive()) {
                            Vec3 pos = Helpers.posToVec(tile.getBlockPos()).add(i * (1 - k*2) + k, 0.2, g * (1 - k*2) + k);
                            ClientHelpers.Particles.createParticle(
                                    SolarcraftParticleTypes.SMALL_SOLAR_STRIKE_PARTICLE.get(),
                                    pos.x, pos.y, pos.z, 0, 0, 0, 255, 255, 40, 0.3f
                            );
                            pos = Helpers.posToVec(tile.getBlockPos()).add(i * (1 - k*2) + k, 0.8, g * (1 - k*2) + k);
                            ClientHelpers.Particles.createParticle(
                                    SolarcraftParticleTypes.SMALL_SOLAR_STRIKE_PARTICLE.get(),
                                    pos.x, pos.y, pos.z, 0, 0, 0, 255, 255, 40, 0.3f
                            );
                        }
                    }
                }
            }
        }
    }

    private static void processItems(ElementWeaverTileEntity tile){
        if (!tile.level.isClientSide) {
            ItemStack input = tile.inputSlot();
            ItemStack output = tile.outputSlot();
            if (!input.isEmpty()){
                tile.processingItem = input.getItem();
                tile.processingItemCost = ConfigRegistry.ITEM_RE_CONFIG.getItemCost(input.getItem());
                if (output.isEmpty() || (output.getItem() == input.getItem() &&
                        output.getCount() < output.getMaxStackSize())){
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
                if (!output.isEmpty()) {
                    output.grow(1);
                }else{
                    tile.setOutputSlot(tile.processingItem.getDefaultInstance());
                }
                tile.spendEnergy(tile.processingItemCost,1);
                tile.processingTime = 0;
                tile.updateRunicEnergy(10);
            }
            tile.processingTime += 1;
        }else{
            tile.processingTime = 0;
        }
    }


    private static void energyDrain(ElementWeaverTileEntity tile){
        if (tile.isActive()){
            tile.requestRunicEnergy(tile.REQUEST,1);
            tile.updateRunicEnergy(10);
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
            if (active){
                this.resetAllRepeaters();
            }
            this.active = !active;
            Helpers.updateTile(this);
        }
    }

    @Override
    public void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putBoolean("active",active);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        this.active = tag.getBoolean("active");
    }


    @Override
    public boolean saveAndLoadEverything() {
        return true;
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
    public float getREPerTickInput() {
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

    @Override
    public AABB getRenderBoundingBox() {
        return Helpers.createAABBWithRadius(Helpers.posToVec(getBlockPos()),10,10);
    }
}
