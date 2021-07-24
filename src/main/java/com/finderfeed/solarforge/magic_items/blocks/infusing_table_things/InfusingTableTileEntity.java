package com.finderfeed.solarforge.magic_items.blocks.infusing_table_things;


import com.finderfeed.solarforge.Helpers;
import com.finderfeed.solarforge.SolarForge;
import com.finderfeed.solarforge.magic_items.blocks.infusing_table_things.infusing_pool.InfusingPoolTileEntity;
import com.finderfeed.solarforge.magic_items.items.solar_lexicon.unlockables.AncientFragment;
import com.finderfeed.solarforge.magic_items.items.solar_lexicon.unlockables.ProgressionHelper;
import com.finderfeed.solarforge.misc_things.*;
import com.finderfeed.solarforge.packet_handler.SolarForgePacketHandler;
import com.finderfeed.solarforge.packet_handler.TriggerToastPacket;
import com.finderfeed.solarforge.recipe_types.InfusingRecipe;
import com.finderfeed.solarforge.magic_items.items.solar_lexicon.achievements.Achievement;
import com.finderfeed.solarforge.world_generation.structures.Structures;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class InfusingTableTileEntity extends LockableLootTileEntity implements ITickableTileEntity, IEnergyUser, IBindable, ISolarEnergyContainer, OneWay {

    public int energy = 0;
    public int TICKS_TIMER=0;
    public float TICKS_RADIUS_TIMER = 0;
    public int INFUSING_TIME;
    public int CURRENT_PROGRESS;
    public boolean RECIPE_IN_PROGRESS = false;
    public boolean requiresEnergy = false;
    public NonNullList<ItemStack> items = NonNullList.withSize(10,ItemStack.EMPTY);
    public InfusingTableTileEntity(TileEntityType<?> type) {
        super(type);
    }

    public int getProgress(){
        return INFUSING_TIME;
    }

    @Override
    protected ITextComponent getDefaultName() {
        return new TranslationTextComponent("container.solarforge.infusing_stand");
    }

    @Override
    protected Container createMenu(int x, PlayerInventory inv) {
        return new InfusingTableContainer(x,inv,this);
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return this.items;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> items) {
        this.items = items;
    }

    public InfusingTableTileEntity(){
        this(SolarForge.INFUSING_STAND_BLOCKENTITY.get());
    }

    @Override
    public int getContainerSize() {
        return this.items.size();
    }



    @Override
    public CompoundNBT save(CompoundNBT cmp){
        super.save(cmp);
        cmp.putInt("energy",energy);
        cmp.putInt("infusing_time",INFUSING_TIME );
        cmp.putInt("recipe_progress",CURRENT_PROGRESS );
        cmp.putBoolean("is_recipe_in_progress",RECIPE_IN_PROGRESS );
        if (!this.trySaveLootTable(cmp)) {
            ItemStackHelper.saveAllItems(cmp, this.items);
        }
        return cmp;
    }

    @Override
    public void load(BlockState state, CompoundNBT cmp) {
        super.load(state,cmp);
        energy = cmp.getInt("energy");
        INFUSING_TIME = cmp.getInt("infusing_time");
        CURRENT_PROGRESS = cmp.getInt("recipe_progress");
        RECIPE_IN_PROGRESS = cmp.getBoolean("is_recipe_in_progress");
        this.items = NonNullList.withSize(getContainerSize(), ItemStack.EMPTY);
        if (!this.tryLoadLootTable(cmp)) {
            ItemStackHelper.loadAllItems(cmp, this.items);
        }
    }

    @Override
    public void tick() {
        if (!this.level.isClientSide){

            updateStacksInPhantomSlots();
            Optional<InfusingRecipe> recipe = this.level.getRecipeManager().getRecipeFor(SolarForge.INFUSING_RECIPE_TYPE, this,level);

                if (!recipe.isPresent()){
                    this.RECIPE_IN_PROGRESS = false;
                    CURRENT_PROGRESS =0;
                    INFUSING_TIME = 0;
                    requiresEnergy = false;

                }


                if (RECIPE_IN_PROGRESS){
                    if (energy >= recipe.get().requriedEnergy) {
                        requiresEnergy = false;
                        CURRENT_PROGRESS++;

                        this.setChanged();
                        if (CURRENT_PROGRESS == INFUSING_TIME) {
                            ItemStack result = new ItemStack(recipe.get().output.getItem(),recipe.get().count);
                            if (!recipe.get().tag.equals("")) {
                                if (result.getItem() instanceof ITagUser){
                                    ITagUser result2 = (ITagUser) result.getItem();
                                    result2.doThingsWithTag(getItem(0),result,recipe.get().tag);
                                }
                            }
                            ItemStack prev = this.getItem(0);
                            if (prev.isEnchanted()){
                                Map<Enchantment,Integer> map = EnchantmentHelper.getEnchantments(prev);

                                for (Enchantment a : map.keySet()){
                                    result.enchant(a,map.get(a));
                                }

                            }
                            if ((prev.getItem() instanceof ToolItem) && (result.getItem() instanceof ToolItem)) {
                                result.hurt(prev.getDamageValue(), level.random, null);
                            }
                            this.getItems().clear();
                            this.getItems().set(9, result);
                            RECIPE_IN_PROGRESS = false;
                            INFUSING_TIME = 0;
                            CURRENT_PROGRESS = 0;
                            deleteStacksInPhantomSlots();
                            this.level.playSound(null, this.worldPosition, SoundEvents.BEACON_DEACTIVATE, SoundCategory.AMBIENT, 2, 1);
                            this.energy-= recipe.get().requriedEnergy;
                        }
                    }else{
                        requiresEnergy = true;
                    }
                }

            SolarForgePacketHandler.INSTANCE.send(PacketDistributor.NEAR.with(PacketDistributor.TargetPoint.p(worldPosition.getX(),worldPosition.getY(),worldPosition.getZ(),20,level.dimension())),
                    new UpdateProgressOnClientPacket(INFUSING_TIME,CURRENT_PROGRESS,this.worldPosition,requiresEnergy,energy));
                ItemStack[] arr = {this.getItem(0),this.getItem(1),this.getItem(2),this.getItem(3),this.getItem(4),this.getItem(5),this.getItem(6),this.getItem(7),this.getItem(8)};
            SolarForgePacketHandler.INSTANCE.send(PacketDistributor.NEAR.with(PacketDistributor.TargetPoint.p(worldPosition.getX(),worldPosition.getY(),worldPosition.getZ(),20,level.dimension())),
                    new UpdateStacksOnClientTable(arr,this.getItem(9),this.worldPosition,RECIPE_IN_PROGRESS));

        }

        if (RECIPE_IN_PROGRESS){

            spawnParticles(4.7f-TICKS_RADIUS_TIMER,TICKS_TIMER);
            TICKS_TIMER+=2;

            if ((INFUSING_TIME - CURRENT_PROGRESS) <= 80 ){
                TICKS_RADIUS_TIMER += 0.05875;

            }
        }else{
            TICKS_TIMER = 0;
            TICKS_RADIUS_TIMER = 0;
        }

    }


    public void triggerCrafting(PlayerEntity playerEntity){
        Optional<InfusingRecipe> recipe = this.level.getRecipeManager().getRecipeFor(SolarForge.INFUSING_RECIPE_TYPE,(IInventory) this,level);
        try {
            if (recipe.isPresent() && ProgressionHelper.doPlayerHasFragment(playerEntity, AncientFragment.getFragmentByID(recipe.get().child))) {

                if (!RECIPE_IN_PROGRESS) {
                    if (!playerEntity.getPersistentData().getBoolean(Helpers.PROGRESSION + Achievement.USE_SOLAR_INFUSER.getAchievementCode())) {
                        playerEntity.getPersistentData().putBoolean(Helpers.PROGRESSION + Achievement.USE_SOLAR_INFUSER.getAchievementCode(), true);
                        SolarForgePacketHandler.INSTANCE.sendTo(new TriggerToastPacket(Achievement.USE_SOLAR_INFUSER.getId()), ((ServerPlayerEntity) playerEntity).connection.connection, NetworkDirection.PLAY_TO_CLIENT);
                    }
                    this.INFUSING_TIME = recipe.get().infusingTime;
                    this.RECIPE_IN_PROGRESS = true;
                    this.level.playSound(null, this.worldPosition, SoundEvents.BEACON_ACTIVATE, SoundCategory.AMBIENT, 2, 1);
                } else {
                    this.level.playSound(null, this.worldPosition, SoundEvents.VILLAGER_NO, SoundCategory.AMBIENT, 2, 1);
                }

            } else {
                if (recipe.isPresent()) {
                    AncientFragment fragment = AncientFragment.getFragmentByID(recipe.get().child);
                    if (fragment != null){
                        if (!ProgressionHelper.doPlayerHasFragment(playerEntity,fragment)){
                            playerEntity.sendMessage(new StringTextComponent("Cant start craft, you dont have "+fragment.getTranslation().getString().toUpperCase()+" fragment unlocked.").withStyle(TextFormatting.RED),
                                    playerEntity.getUUID());
                        }
                    }
                }else{
                    playerEntity.sendMessage(new StringTextComponent("Recipe invalid").withStyle(TextFormatting.RED),
                            playerEntity.getUUID());
                }
                this.level.playSound(null, this.worldPosition, SoundEvents.VILLAGER_NO, SoundCategory.AMBIENT, 2, 1);
            }
        }catch (NullPointerException e){
            playerEntity.sendMessage(new StringTextComponent("INCORRECT FRAGMENT IN RECIPE "+ recipe.get().output.getDescriptionId()+" TELL MOD AUTHOR TO FIX IT").withStyle(TextFormatting.RED),
                    playerEntity.getUUID());
        }

    }

    public void updateStacksInPhantomSlots(){
        List<TileEntity> list = Structures.checkInfusingStandStructure(worldPosition,level);
        for (int i = 0;i < list.size();i++){
            if (list.get(i) instanceof InfusingPoolTileEntity){
                InfusingPoolTileEntity tile = (InfusingPoolTileEntity) list.get(i);
                this.setItem(i+1,tile.getItem(0));
            }else{
                this.setItem(i+1, ItemStack.EMPTY);
            }
        }
    }
    public void deleteStacksInPhantomSlots(){
        List<TileEntity> list = Structures.checkInfusingStandStructure(worldPosition,level);
        for (int i = 0;i < list.size();i++){
            if (list.get(i) instanceof InfusingPoolTileEntity){
                InfusingPoolTileEntity tile = (InfusingPoolTileEntity) list.get(i);
                tile.setItem(0,ItemStack.EMPTY);
            }
        }
    }


    public void spawnParticles(float radius,float angle){

        for (int i = 0;i<4;i++){
            double offsetx = radius*Math.cos(Math.toRadians(i*90+angle));
            double offsetz = radius*Math.sin(Math.toRadians(i*90+angle));
            level.addParticle(ParticleTypes.FLAME,worldPosition.getX()+offsetx+0.5f,worldPosition.getY()+0.5,worldPosition.getZ()+offsetz+0.5f,0,0,0);

        }
    }


    @Override
    public void giveEnergy(int a) {
        if (this.energy+a <= getMaxEnergy()){
            this.energy+=a;
        }
    }

    @Override
    public int getMaxEnergy() {
        return 100000;
    }

    @Override
    public boolean requriesEnergy() {
        return requiresEnergy;
    }

    @Override
    public int getRadius() {
        return 16;
    }

    @Override
    public void bindPos(BlockPos pos) {
        TileEntity poss = level.getBlockEntity(pos);
        if (poss instanceof IBindable && !(poss instanceof IEnergyUser)) {
            ((IBindable) poss).bindPos(worldPosition);
        }
    }

    @Override
    public double getEnergy() {
        return energy;
    }
}
