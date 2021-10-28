package com.finderfeed.solarforge.magic_items.blocks.infusing_table_things;


import com.finderfeed.solarforge.Helpers;
import com.finderfeed.solarforge.SolarForge;
import com.finderfeed.solarforge.magic_items.blocks.blockentities.runic_energy.AbstractRunicEnergyContainerRCBE;
import com.finderfeed.solarforge.magic_items.blocks.infusing_table_things.infusing_pool.InfusingPoolTileEntity;
import com.finderfeed.solarforge.magic_items.items.solar_lexicon.unlockables.AncientFragment;
import com.finderfeed.solarforge.magic_items.items.solar_lexicon.unlockables.ProgressionHelper;
import com.finderfeed.solarforge.magic_items.runic_network.algorithms.FindingAlgorithms;
import com.finderfeed.solarforge.magic_items.runic_network.repeater.BaseRepeaterTile;
import com.finderfeed.solarforge.magic_items.runic_network.repeater.IRunicEnergyContainer;
import com.finderfeed.solarforge.magic_items.runic_network.repeater.IRunicEnergyReciever;
import com.finderfeed.solarforge.misc_things.*;
import com.finderfeed.solarforge.packet_handler.SolarForgePacketHandler;
import com.finderfeed.solarforge.packet_handler.packets.TriggerToastPacket;
import com.finderfeed.solarforge.recipe_types.InfusingRecipe;
import com.finderfeed.solarforge.magic_items.items.solar_lexicon.achievements.Achievement;
import com.finderfeed.solarforge.world_generation.structures.Structures;
import com.google.common.util.concurrent.AtomicDouble;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.core.NonNullList;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.fmllegacy.network.NetworkDirection;
import net.minecraftforge.fmllegacy.network.PacketDistributor;

import javax.annotation.Nullable;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class InfusingTableTileEntity extends AbstractRunicEnergyContainerRCBE implements  IEnergyUser, IBindable, ISolarEnergyContainer, OneWay,DebugTarget {




    public int energy = 0;
    public int TICKS_TIMER=0;
    public float TICKS_RADIUS_TIMER = 0;
    public int INFUSING_TIME;
    public int CURRENT_PROGRESS;
    public boolean RECIPE_IN_PROGRESS = false;
    public boolean requiresEnergy = false;
    public NonNullList<ItemStack> items = NonNullList.withSize(10,ItemStack.EMPTY);

    public InfusingTableTileEntity( BlockPos p_155630_, BlockState p_155631_) {
        super(SolarForge.INFUSING_STAND_BLOCKENTITY.get(), p_155630_, p_155631_);
    }


    public int getProgress(){
        return INFUSING_TIME;
    }

    @Override
    protected Component getDefaultName() {
        return new TranslatableComponent("container.solarforge.infusing_stand");
    }

    @Override
    protected AbstractContainerMenu createMenu(int x, Inventory inv) {
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



    @Override
    public int getContainerSize() {
        return this.items.size();
    }



    @Override
    public CompoundTag save(CompoundTag cmp){
        super.save(cmp);
        cmp.putInt("energy",energy);
        cmp.putInt("infusing_time",INFUSING_TIME );
        cmp.putInt("recipe_progress",CURRENT_PROGRESS );
        cmp.putBoolean("is_recipe_in_progress",RECIPE_IN_PROGRESS );
        if (!this.trySaveLootTable(cmp)) {
            ContainerHelper.saveAllItems(cmp, this.items);
        }
        return cmp;
    }

    @Override
    public void load( CompoundTag cmp) {
        super.load(cmp);
        energy = cmp.getInt("energy");
        INFUSING_TIME = cmp.getInt("infusing_time");
        CURRENT_PROGRESS = cmp.getInt("recipe_progress");
        RECIPE_IN_PROGRESS = cmp.getBoolean("is_recipe_in_progress");
        this.items = NonNullList.withSize(getContainerSize(), ItemStack.EMPTY);
        if (!this.tryLoadLootTable(cmp)) {
            ContainerHelper.loadAllItems(cmp, this.items);
        }
    }

    @Override
    public double getMaxEnergyInput() {
        return 10;
    }

    @Override
    public double getRunicEnergyLimit() {
        return 100000;
    }



    public static void tick(Level world, BlockPos pos, BlockState blockState, InfusingTableTileEntity tile) {
        if (!world.isClientSide){

            tile.updateStacksInPhantomSlots();
            Optional<InfusingRecipe> recipe = tile.level.getRecipeManager().getRecipeFor(SolarForge.INFUSING_RECIPE_TYPE, tile,world);

                if (!recipe.isPresent()){
                    tile.RECIPE_IN_PROGRESS = false;
                    tile.CURRENT_PROGRESS =0;
                    tile.INFUSING_TIME = 0;
                    tile.requiresEnergy = false;
                    tile.onTileRemove();
                    tile.clearWays();
                }
                if (tile.RECIPE_IN_PROGRESS){
                    tile.setChanged();
                    world.sendBlockUpdated(tile.worldPosition,blockState,blockState,3);
                    InfusingRecipe recipe1 = recipe.get();
                    int count = tile.getMinRecipeCountOutput(recipe1);
                    Map<RunicEnergy.Type,Double> costs = recipe1.RUNIC_ENERGY_COST;
                    tile.INFUSING_TIME = recipe1.infusingTime*count;
                    boolean check = tile.hasEnoughRunicEnergy(costs,count);

                    if ((tile.energy >= recipe1.requriedEnergy*count) && check) {
                        tile.onTileRemove();
                        tile.clearWays();
                        tile.requiresEnergy = false;
                        tile.CURRENT_PROGRESS++;


                        if (tile.CURRENT_PROGRESS >= tile.INFUSING_TIME) {
                            finishRecipe(world,tile,recipe1);
                        }
                    }else{
                        tile.requestRunicEnergy(costs,count);
                        tile.requiresEnergy = !(tile.energy >= recipe1.requriedEnergy*count);
                    }
                }
                if (world.getGameTime() % 5 == 1) {
                    sendUpdatePackets(world, tile);
                }
        }
        doParticlesAnimation(world,tile);
    }



    private static void doParticlesAnimation(Level world,InfusingTableTileEntity tile){
        if (tile.RECIPE_IN_PROGRESS){

            tile.spawnParticles(4.7f-tile.TICKS_RADIUS_TIMER,tile.TICKS_TIMER);
            tile.TICKS_TIMER+=2;

            if ((tile.INFUSING_TIME - tile.CURRENT_PROGRESS) <= 80 ){
                tile.TICKS_RADIUS_TIMER += 0.05875;

            }
        }else{
            tile.TICKS_TIMER = 0;
            tile.TICKS_RADIUS_TIMER = 0;
        }
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        this.load(pkt.getTag());
        super.onDataPacket(net, pkt);
    }

    @Nullable
    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {

        CompoundTag tag = new CompoundTag();
        this.save(tag);

        return new ClientboundBlockEntityDataPacket(worldPosition,3,tag);
    }

    private static void sendUpdatePackets(Level world, InfusingTableTileEntity tile){
        SolarForgePacketHandler.INSTANCE.send(PacketDistributor.NEAR.with(PacketDistributor.TargetPoint.p(tile.worldPosition.getX(),tile.worldPosition.getY(),tile.worldPosition.getZ(),20,tile.level.dimension())),
                new UpdateProgressOnClientPacket(tile.INFUSING_TIME,tile.CURRENT_PROGRESS,tile.worldPosition,tile.requiresEnergy,tile.energy));
        ItemStack[] arr = {tile.getItem(0),tile.getItem(1),tile.getItem(2),tile.getItem(3),tile.getItem(4),tile.getItem(5),tile.getItem(6),tile.getItem(7),tile.getItem(8)};
        SolarForgePacketHandler.INSTANCE.send(PacketDistributor.NEAR.with(PacketDistributor.TargetPoint.p(tile.worldPosition.getX(),tile.worldPosition.getY(),tile.worldPosition.getZ(),20,tile.level.dimension())),
                new UpdateStacksOnClientTable(arr,tile.getItem(9),tile.worldPosition,tile.RECIPE_IN_PROGRESS));
    }



    private static void finishRecipe(Level world,InfusingTableTileEntity tile,InfusingRecipe recipe){
        ItemStack result = new ItemStack(recipe.output.getItem(),recipe.count);
        int count = tile.getMinRecipeCountOutput(recipe);
        recipe.RUNIC_ENERGY_COST.forEach((type,cost)->{
            tile.giveEnergy(type,-cost*count);
        });
        if (!recipe.tag.equals("")) {
            if (result.getItem() instanceof ITagUser result2){
                result2.doThingsWithTag(tile.getItem(0),result,recipe.tag);
            }
        }
        ItemStack prev = tile.getItem(0);
        if (prev.isEnchanted()){
            Map<Enchantment,Integer> map = EnchantmentHelper.getEnchantments(prev);

            for (Enchantment a : map.keySet()){
                result.enchant(a,map.get(a));
            }

        }

        if ((prev.getItem() instanceof DiggerItem) && (result.getItem() instanceof DiggerItem)) {
            result.hurt(prev.getDamageValue(), world.random, null);
        }

        result.setCount(count);
        tile.getItem(0).grow(-count);
        tile.getItems().set(9, result);
        tile.RECIPE_IN_PROGRESS = false;
        tile.INFUSING_TIME = 0;
        tile.CURRENT_PROGRESS = 0;
        tile.deleteStacksInPhantomSlots(count);
        tile.level.playSound(null, tile.worldPosition, SoundEvents.BEACON_DEACTIVATE, SoundSource.AMBIENT, 2, 1);
        tile.energy-= recipe.requriedEnergy*count;
        tile.onRemove();
//        tile.getWays().forEach((type,path)->{
//            FindingAlgorithms.resetRepeaters(path,world,tile.worldPosition);
//        });
        tile.clearWays();

    }



    public void triggerCrafting(Player playerEntity){
        Optional<InfusingRecipe> recipe = this.level.getRecipeManager().getRecipeFor(SolarForge.INFUSING_RECIPE_TYPE,(Container) this,level);
        try {
            if (recipe.isPresent() && ProgressionHelper.doPlayerHasFragment(playerEntity, AncientFragment.getFragmentByID(recipe.get().child))) {

                if (!RECIPE_IN_PROGRESS) {
                    Helpers.fireProgressionEvent(playerEntity,Achievement.USE_SOLAR_INFUSER);
                    this.INFUSING_TIME = recipe.get().infusingTime;
                    this.RECIPE_IN_PROGRESS = true;
                    this.level.playSound(null, this.worldPosition, SoundEvents.BEACON_ACTIVATE, SoundSource.AMBIENT, 2, 1);
                } else {
                    this.level.playSound(null, this.worldPosition, SoundEvents.VILLAGER_NO, SoundSource.AMBIENT, 2, 1);
                }

            } else {
                if (recipe.isPresent()) {
                    AncientFragment fragment = AncientFragment.getFragmentByID(recipe.get().child);
                    if (fragment != null){
                        if (!ProgressionHelper.doPlayerHasFragment(playerEntity,fragment)){
                            playerEntity.sendMessage(new TextComponent("Cant start craft, you dont have "+fragment.getTranslation().getString().toUpperCase()+" fragment unlocked.").withStyle(ChatFormatting.RED),
                                    playerEntity.getUUID());
                        }
                    }
                }else{
                    playerEntity.sendMessage(new TextComponent("Recipe invalid").withStyle(ChatFormatting.RED),
                            playerEntity.getUUID());
                }
                this.level.playSound(null, this.worldPosition, SoundEvents.VILLAGER_NO, SoundSource.AMBIENT, 2, 1);
            }
        }catch (NullPointerException e){
            playerEntity.sendMessage(new TextComponent("INCORRECT FRAGMENT IN RECIPE "+ recipe.get().output.getDisplayName()+" TELL MOD AUTHOR TO FIX IT").withStyle(ChatFormatting.RED),
                    playerEntity.getUUID());
        }

    }

    public void updateStacksInPhantomSlots(){
        List<BlockEntity> list = Structures.checkInfusingStandStructure(worldPosition,level);
        for (int i = 0;i < list.size();i++){
            if (list.get(i) instanceof InfusingPoolTileEntity){
                InfusingPoolTileEntity tile = (InfusingPoolTileEntity) list.get(i);
                this.setItem(i+1,tile.getItem(0));
            }else{
                this.setItem(i+1, ItemStack.EMPTY);
            }
        }
    }


    private int getMinRecipeCountOutput(InfusingRecipe recipe){
        AtomicInteger count = new AtomicInteger((int)Math.floor(64/(float)recipe.count));

        double maxenergycost = getMaxEnergyCostFromRecipe(recipe);
        if (maxenergycost != 0){
            int maxItems = (int)Math.floor(100000/maxenergycost);
            if (maxItems < count.get()){
                count.set(maxItems);
            }
        }
        int solarEnergyCost = recipe.requriedEnergy;
        if (solarEnergyCost != 0){
            int maxItems = (int)Math.floor(100000/(float)solarEnergyCost);
            if (maxItems < count.get()){
                count.set(maxItems);
            }
        }

        Structures.checkInfusingStandStructure(worldPosition,level).forEach((tile)->{
            if (tile instanceof InfusingPoolTileEntity pool){
                if ((pool.getItem(0).getItem() != Items.AIR) && pool.getItem(0).getCount() < count.get()){
                    count.set(pool.getItem(0).getCount());
                }
            }
        });
        if (this.getItem(0).getCount() < count.get()){
            count.set(getItem(0).getCount());
        }
        return count.get();
    }


    private double getMaxEnergyCostFromRecipe(InfusingRecipe recipe){
        AtomicDouble integer = new AtomicDouble(0);
        recipe.RUNIC_ENERGY_COST.forEach((type,cost)->{
            if (cost > integer.get()){
                integer.set(cost);
            }
        });
        return integer.get();
    }


    public void deleteStacksInPhantomSlots(int amount){
        List<BlockEntity> list = Structures.checkInfusingStandStructure(worldPosition,level);
        for (int i = 0;i < list.size();i++){
            if (list.get(i) instanceof InfusingPoolTileEntity){
                InfusingPoolTileEntity tile = (InfusingPoolTileEntity) list.get(i);
                tile.getItem(0).grow(-amount);
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
    public int giveEnergy(int a) {
        if (getEnergy() + a <= getMaxEnergy()) {
            this.energy += a;
            return 0;
        }else {
            int raznitsa =((int)getEnergy() + a) - getMaxEnergy();
            this.energy = getMaxEnergy();
            return raznitsa;
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
        BlockEntity poss = level.getBlockEntity(pos);
        if (poss instanceof IBindable && !(poss instanceof IEnergyUser)) {
            ((IBindable) poss).bindPos(worldPosition);
        }
    }

    @Override
    public double getEnergy() {
        return energy;
    }

    @Override
    public double getMaxRange() {
        return 20;
    }

    @Override
    public List<String> getDebugStrings() {


        return List.of(
                "ARDO ENERGY "+ this.getRunicEnergy(RunicEnergy.Type.ARDO),
                "FIRA ENERGY "+ this.getRunicEnergy(RunicEnergy.Type.FIRA),
                "TERA ENERGY "+ this.getRunicEnergy(RunicEnergy.Type.TERA),
                "KELDA ENERGY "+ this.getRunicEnergy(RunicEnergy.Type.KELDA),
                "URBA ENERGY "+ this.getRunicEnergy(RunicEnergy.Type.URBA),
                "ZETA ENERGY "+ this.getRunicEnergy(RunicEnergy.Type.ZETA),
                "SOLAR ENERGY "+ energy
        );
    }

    public void onTileRemove(){
        this.onRemove();
    }

}
