package com.finderfeed.solarforge.magic_items.blocks.infusing_table_things;


import com.finderfeed.solarforge.Helpers;
import com.finderfeed.solarforge.SolarForge;
import com.finderfeed.solarforge.magic_items.blocks.infusing_table_things.infusing_pool.InfusingPoolTileEntity;
import com.finderfeed.solarforge.magic_items.blocks.solar_forge_block.SolarForgeBlockEntity;
import com.finderfeed.solarforge.magic_items.items.solar_lexicon.unlockables.AncientFragment;
import com.finderfeed.solarforge.magic_items.items.solar_lexicon.unlockables.ProgressionHelper;
import com.finderfeed.solarforge.magic_items.runic_network.algorithms.FindingAlgorithms;
import com.finderfeed.solarforge.magic_items.runic_network.repeater.BaseRepeaterTile;
import com.finderfeed.solarforge.magic_items.runic_network.repeater.IRunicEnergyContainer;
import com.finderfeed.solarforge.magic_items.runic_network.repeater.IRunicEnergyReciever;
import com.finderfeed.solarforge.misc_things.*;
import com.finderfeed.solarforge.packet_handler.SolarForgePacketHandler;
import com.finderfeed.solarforge.packet_handler.TriggerToastPacket;
import com.finderfeed.solarforge.recipe_types.InfusingRecipe;
import com.finderfeed.solarforge.magic_items.items.solar_lexicon.achievements.Achievement;
import com.finderfeed.solarforge.world_generation.structures.Structures;
import net.minecraft.client.Minecraft;
import net.minecraft.network.Connection;
import net.minecraft.network.FriendlyByteBuf;
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
import net.minecraft.world.level.block.entity.BlockEntityType;
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

public class InfusingTableTileEntity extends RandomizableContainerBlockEntity implements  IEnergyUser, IBindable, ISolarEnergyContainer, OneWay, IRunicEnergyReciever,DebugTarget {


    public double RUNE_ENERGY_ARDO = 0;
    public double RUNE_ENERGY_FIRA = 0;
    public double RUNE_ENERGY_TERA = 0;
    public double RUNE_ENERGY_URBA = 0;
    public double RUNE_ENERGY_KELDA = 0;
    public double RUNE_ENERGY_ZETA = 0;
    public Map<RunicEnergy.Type,List<BlockPos>> PATH_TO_PYLONS = new HashMap<>();
    private boolean NEEDS_RUNIC_ENERGY_FLAG = false;


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
        cmp.putDouble("ardo",RUNE_ENERGY_ARDO);
        cmp.putDouble("fira",RUNE_ENERGY_FIRA);
        cmp.putDouble("tera",RUNE_ENERGY_TERA);
        cmp.putDouble("zeta",RUNE_ENERGY_ZETA);
        cmp.putDouble("kelda",RUNE_ENERGY_KELDA);
        cmp.putDouble("urba",RUNE_ENERGY_URBA);
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
        RUNE_ENERGY_ARDO  = cmp.getDouble("ardo");
        RUNE_ENERGY_FIRA = cmp.getDouble("fira");
        RUNE_ENERGY_TERA = cmp.getDouble("tera");
        RUNE_ENERGY_ZETA = cmp.getDouble("zeta");
        RUNE_ENERGY_KELDA = cmp.getDouble("kelda");
        RUNE_ENERGY_URBA = cmp.getDouble("urba");
        this.items = NonNullList.withSize(getContainerSize(), ItemStack.EMPTY);
        if (!this.tryLoadLootTable(cmp)) {
            ContainerHelper.loadAllItems(cmp, this.items);
        }
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
                    tile.PATH_TO_PYLONS.clear();
                }
                if (tile.RECIPE_IN_PROGRESS){
                    int count = tile.getMinRecipeCountOutput();
                    InfusingRecipe recipe1 = recipe.get();
                    Map<RunicEnergy.Type,Double> costs = recipe1.RUNIC_ENERGY_COST;
                    tile.INFUSING_TIME = recipe1.infusingTime*count;
                    boolean check = hasEnoughRunicEnergy(world,tile,costs,count);
                    tile.NEEDS_RUNIC_ENERGY_FLAG = check;
                    if ((tile.energy >= recipe1.requriedEnergy*count) && check) {
                        tile.onTileRemove();
                        tile.PATH_TO_PYLONS.clear();
                        tile.requiresEnergy = false;
                        tile.CURRENT_PROGRESS++;

                        tile.setChanged();

                        if (tile.CURRENT_PROGRESS >= tile.INFUSING_TIME) {
                            finishRecipe(world,tile,recipe1);
                        }
                    }else{
                        costs.forEach((type,cost)->{
                            if (cost > 0){
                                tile.requestEnergy(10,type);
                            }
                        });
                        tile.requiresEnergy = true;
                    }
                }
            sendUpdatePackets(world,tile);
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


    private static void sendUpdatePackets(Level world,InfusingTableTileEntity tile){
        SolarForgePacketHandler.INSTANCE.send(PacketDistributor.NEAR.with(PacketDistributor.TargetPoint.p(tile.worldPosition.getX(),tile.worldPosition.getY(),tile.worldPosition.getZ(),20,tile.level.dimension())),
                new UpdateProgressOnClientPacket(tile.INFUSING_TIME,tile.CURRENT_PROGRESS,tile.worldPosition,tile.requiresEnergy,tile.energy));
        ItemStack[] arr = {tile.getItem(0),tile.getItem(1),tile.getItem(2),tile.getItem(3),tile.getItem(4),tile.getItem(5),tile.getItem(6),tile.getItem(7),tile.getItem(8)};
        SolarForgePacketHandler.INSTANCE.send(PacketDistributor.NEAR.with(PacketDistributor.TargetPoint.p(tile.worldPosition.getX(),tile.worldPosition.getY(),tile.worldPosition.getZ(),20,tile.level.dimension())),
                new UpdateStacksOnClientTable(arr,tile.getItem(9),tile.worldPosition,tile.RECIPE_IN_PROGRESS));
    }



    private static void finishRecipe(Level world,InfusingTableTileEntity tile,InfusingRecipe recipe){
        ItemStack result = new ItemStack(recipe.output.getItem(),recipe.count);
        int count = tile.getMinRecipeCountOutput();
        recipe.RUNIC_ENERGY_COST.forEach((type,cost)->{
            tile.giveEnergy(type,-cost*count);
        });
        if (!recipe.tag.equals("")) {
            if (result.getItem() instanceof ITagUser){
                ITagUser result2 = (ITagUser) result.getItem();
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
        tile.NEEDS_RUNIC_ENERGY_FLAG  =false;
        tile.PATH_TO_PYLONS.forEach((type,path)->{
            FindingAlgorithms.resetRepeaters(path,world,tile.worldPosition);
        });

    }



    public void triggerCrafting(Player playerEntity){
        Optional<InfusingRecipe> recipe = this.level.getRecipeManager().getRecipeFor(SolarForge.INFUSING_RECIPE_TYPE,(Container) this,level);
        try {
            if (recipe.isPresent() && ProgressionHelper.doPlayerHasFragment(playerEntity, AncientFragment.getFragmentByID(recipe.get().child))) {

                if (!RECIPE_IN_PROGRESS) {
                    if (!playerEntity.getPersistentData().getBoolean(Helpers.PROGRESSION + Achievement.USE_SOLAR_INFUSER.getAchievementCode())) {
                        playerEntity.getPersistentData().putBoolean(Helpers.PROGRESSION + Achievement.USE_SOLAR_INFUSER.getAchievementCode(), true);
                        SolarForgePacketHandler.INSTANCE.sendTo(new TriggerToastPacket(Achievement.USE_SOLAR_INFUSER.getId()), ((ServerPlayer) playerEntity).connection.connection, NetworkDirection.PLAY_TO_CLIENT);
                    }
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


    private int getMinRecipeCountOutput(){
        AtomicInteger count = new AtomicInteger(99999);
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
    public boolean requiresEnergy() {

        if (RECIPE_IN_PROGRESS && NEEDS_RUNIC_ENERGY_FLAG){
            return true;
        }

        return false;
    }

    @Override
    public void requestEnergy(double amount, RunicEnergy.Type type) {

        if (PATH_TO_PYLONS.containsKey(type) && PATH_TO_PYLONS.get(type) != null){
            FindingAlgorithms.setRepeatersConnections(PATH_TO_PYLONS.get(type),level);
            BlockEntity first = level.getBlockEntity(PATH_TO_PYLONS.get(type).get(0));
            if (first instanceof IRunicEnergyContainer container){
                double flag = container.extractEnergy(type,amount);
                this.giveEnergy(type,flag);
            }else if (first instanceof BaseRepeaterTile repeater){
                repeater.addConsumerConnection(worldPosition);
                double flag = repeater.extractEnergy(amount,type);
                if (flag != BaseRepeaterTile.NULL){
                    this.giveEnergy(type,flag);
                }else{
                    FindingAlgorithms.resetRepeaters(PATH_TO_PYLONS.get(type),level,worldPosition);
                    constructWay(type);
                }
            }

        }else{
            constructWay(type);
        }
    }

    @Override
    public double getMaxRange() {
        return 20;
    }



    public void constructWay(RunicEnergy.Type type){
        PATH_TO_PYLONS.remove(type);
        BlockEntity entity = findNearestRepeaterOrPylon(worldPosition,level,type);
        if (entity instanceof BaseRepeaterTile tile){
            Map<BlockPos,List<BlockPos>> graph = FindingAlgorithms.findAllConnectedPylons(tile,new ArrayList<>(),new HashMap<>());
            if (FindingAlgorithms.hasEndPoint(graph,level)) {
                FindingAlgorithms.sortBestPylon(graph, level);
                PATH_TO_PYLONS.put(type, FindingAlgorithms.findConnectionAStar(graph, tile.getBlockPos(), level));

            }
        }else if (entity instanceof IRunicEnergyContainer container){
            PATH_TO_PYLONS.put(type,List.of(container.getPos()));
        }
    }

    public void giveEnergy(RunicEnergy.Type type, double amount){
        switch (type){
            case ARDO -> RUNE_ENERGY_ARDO+=amount;
            case FIRA -> RUNE_ENERGY_FIRA+=amount;
            case TERA -> RUNE_ENERGY_TERA+=amount;
            case KELDA -> RUNE_ENERGY_KELDA+=amount;
            case URBA -> RUNE_ENERGY_URBA=amount;
            case ZETA -> RUNE_ENERGY_ZETA+=amount;
        }
    }



    private static boolean hasEnoughRunicEnergy(Level world,InfusingTableTileEntity tile,Map<RunicEnergy.Type,Double> costs,int multiplier){
        AtomicBoolean bool = new AtomicBoolean(true);
        Map<RunicEnergy.Type,Double> tileEnergy = Map.of(
                RunicEnergy.Type.ARDO,tile.RUNE_ENERGY_ARDO,
                RunicEnergy.Type.TERA,tile.RUNE_ENERGY_TERA,
                RunicEnergy.Type.KELDA,tile.RUNE_ENERGY_KELDA,
                RunicEnergy.Type.FIRA,tile.RUNE_ENERGY_FIRA,
                RunicEnergy.Type.ZETA,tile.RUNE_ENERGY_ZETA,
                RunicEnergy.Type.URBA,tile.RUNE_ENERGY_URBA
        );
        costs.forEach((type,cost)->{
            if (tileEnergy.get(type) < cost*multiplier){
                bool.set(false);
            }
        });

        return bool.get();
    }

    @Override
    public List<String> getDebugStrings() {


        return List.of(
                "ARDO ENERGY "+ RUNE_ENERGY_ARDO,
                "FIRA ENERGY "+ RUNE_ENERGY_FIRA,
                "TERA ENERGY "+ RUNE_ENERGY_TERA,
                "KELDA ENERGY "+ RUNE_ENERGY_KELDA,
                "URBA ENERGY "+ RUNE_ENERGY_URBA,
                "ZETA ENERGY "+ RUNE_ENERGY_ZETA,
                "SOLAR ENERGY "+ energy
        );
    }

    public void onTileRemove(){
        PATH_TO_PYLONS.forEach((type,way)->{
            FindingAlgorithms.resetRepeaters(way,level,worldPosition);
        });
    }

}
