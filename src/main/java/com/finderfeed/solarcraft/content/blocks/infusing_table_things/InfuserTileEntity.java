package com.finderfeed.solarcraft.content.blocks.infusing_table_things;


import com.finderfeed.solarcraft.config.SolarcraftConfig;
import com.finderfeed.solarcraft.content.blocks.solar_energy.Bindable;
import com.finderfeed.solarcraft.content.blocks.solar_energy.SolarEnergyContainer;
import com.finderfeed.solarcraft.content.items.SunShardItem;
import com.finderfeed.solarcraft.content.items.solar_wand.IWandable;
import com.finderfeed.solarcraft.content.items.solar_wand.wand_actions.structure_check.IStructureOwner;
import com.finderfeed.solarcraft.helpers.ClientHelpers;
import com.finderfeed.solarcraft.helpers.Helpers;
import com.finderfeed.solarcraft.SolarCraft;
import com.finderfeed.solarcraft.client.particles.SCParticleTypes;
import com.finderfeed.solarcraft.helpers.multiblock.MultiblockStructure;
import com.finderfeed.solarcraft.helpers.multiblock.Multiblocks;
import com.finderfeed.solarcraft.local_library.helpers.FDMathHelper;
import com.finderfeed.solarcraft.local_library.other.EaseIn;
import com.finderfeed.solarcraft.content.blocks.blockentities.REItemHandlerBlockEntity;
import com.finderfeed.solarcraft.content.blocks.infusing_table_things.infusing_pool.InfusingStandTileEntity;
import com.finderfeed.solarcraft.content.items.runic_energy.RunicEnergyCost;
import com.finderfeed.solarcraft.content.items.solar_lexicon.unlockables.AncientFragment;
import com.finderfeed.solarcraft.content.items.solar_lexicon.unlockables.AncientFragmentHelper;
import com.finderfeed.solarcraft.misc_things.*;
//import com.finderfeed.solarcraft.multiblocks.Multiblocks;
import com.finderfeed.solarcraft.content.recipe_types.infusing_new.InfusingRecipe;
import com.finderfeed.solarcraft.content.items.solar_lexicon.progressions.Progression;
import com.finderfeed.solarcraft.registries.Tags;
import com.finderfeed.solarcraft.registries.blocks.SCBlocks;
import com.finderfeed.solarcraft.content.world_generation.structures.NotStructures;
import com.finderfeed.solarcraft.registries.items.SCItems;
import com.finderfeed.solarcraft.registries.recipe_types.SolarcraftRecipeTypes;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;

import javax.annotation.Nullable;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class InfuserTileEntity extends REItemHandlerBlockEntity implements SolarEnergyContainer, Bindable, DebugTarget, IWandable, IStructureOwner {


    public static final int MAX_RUNIC_ENERGY = 100000;
    public static final int MAX_SOLAR_ENERGY = 100000;

    private EaseIn rotationValue = new EaseIn(0,1,100);

    private Tier tier = null;

    public int solarEnergy = 0;
    public int infusingTime;
    public int currentTime;
    public boolean isRecipeInProgress = false;
    public boolean requiresEnergy = false;
    private InfusingRecipe currentRecipe;


    public InfuserTileEntity(BlockPos p_155630_, BlockState p_155631_) {
        super(SolarCraft.INFUSING_STAND_BLOCKENTITY.get(), p_155630_, p_155631_);
    }


    public ItemStack getItem(int slot){
        ItemStackHandler handler = getInventory();
        return handler != null ? handler.getStackInSlot(slot) : ItemStack.EMPTY;
    }

    public void setItem(int slot,ItemStack stack){
        ItemStackHandler handler = getInventory();
        if (handler != null){
            handler.setStackInSlot(slot,stack);
        }
    }
    public List<ItemStack> getItems(){
        List<ItemStack> toReturn = new ArrayList<>();
        ItemStackHandler inv = getInventory();
        if (inv != null){
            for (int i = 0; i < inv.getSlots();i++){
                toReturn.add(inv.getStackInSlot(i));
            }
        }
        return toReturn;
    }



    public int getContainerSize() {
        ItemStackHandler h = getInventory();
        return h != null ? h.getSlots() : 0;
    }



    @Override
    public void saveAdditional(CompoundTag cmp){
        super.saveAdditional(cmp);
        cmp.putBoolean("reqenergy",requiresEnergy);
        cmp.putInt("energy", solarEnergy);
        cmp.putInt("infusing_time", infusingTime);
        cmp.putInt("recipe_progress", currentTime);
        cmp.putBoolean("is_recipe_in_progress", isRecipeInProgress);
        if (this.tier != null) {
            cmp.putString("tierid", this.tier.id);
        }else{
            cmp.putString("tierid", "null");
        }
    }



    @Override
    public void load( CompoundTag cmp) {
        super.load(cmp);
        requiresEnergy = cmp.getBoolean("reqenergy");
        tier = Tier.byId(cmp.getString("tierid"));
        solarEnergy = cmp.getInt("energy");
        infusingTime = cmp.getInt("infusing_time");
        currentTime = cmp.getInt("recipe_progress");
        isRecipeInProgress = cmp.getBoolean("is_recipe_in_progress");

    }

    @Override
    public float getREPerTickInput() {
        return 30;
    }

    @Override
    public float getRunicEnergyLimit() {
        return MAX_RUNIC_ENERGY;
    }

    @Override
    public int getSeekCooldown() {
        return 10;
    }

    @Override
    public boolean shouldFunction() {
        return true;
    }

    public Tier getTier() {
        return tier;
    }

    public static void tick(Level world, BlockPos pos, BlockState blockState, InfuserTileEntity tile) {
        if (!world.isClientSide){
            IItemHandler inv = tile.getInventory();
            if (inv == null) return;
            tile.updateStacksInPhantomSlots();
            boolean forceUpdate = false;
            if (tile.isRecipeInProgress) {
                
                Optional<InfusingRecipe> recipe;
                if (tile.currentRecipe == null){
                    recipe = tile.level.getRecipeManager().getRecipeFor(SolarcraftRecipeTypes.INFUSING.get(), new PhantomInventory(inv), world);
                    recipe.ifPresent(infusingRecipe -> tile.currentRecipe = infusingRecipe);
                }else{
                    if (!tile.currentRecipe.matches(new PhantomInventory(inv),world)){
                        recipe = Optional.empty();
                        forceUpdate = true;
                    }else{
                        recipe = Optional.of(tile.currentRecipe);
                    }
                }

                if (recipe.isEmpty()) {
                    tile.isRecipeInProgress = false;
                    tile.currentTime = 0;
                    tile.infusingTime = 0;
                    tile.requiresEnergy = false;
                    tile.currentRecipe = null;
                    tile.onTileRemove();
                }
                if (tile.isRecipeInProgress && tile.catalystsMatch(recipe.get()) && tile.isStructureCorrect()) {
                    forceUpdate = true;
                    InfusingRecipe recipe1 = recipe.get();

                    int count = tile.getMinRecipeCountOutput(recipe1);
                    RunicEnergyCost costs = recipe1.RUNIC_ENERGY_COST;
                    tile.infusingTime = recipe1.infusingTime * count;

                    boolean check = tile.hasEnoughRunicEnergy(costs, count);
                    if ((tile.solarEnergy >= recipe1.requriedSolarEnergy * count) && check) {
                        tile.onTileRemove();

                        tile.requiresEnergy = false;
                        tile.currentTime++;
                        if (tile.currentTime >= tile.infusingTime) {
                            finishRecipe(world, tile, recipe1);
                        }
                    } else {
                        tile.requestRunicEnergy(costs, count);
                        tile.requiresEnergy = !(tile.solarEnergy >= recipe1.requriedSolarEnergy * count);
                    }

                } else {
                    tile.currentRecipe = null;
                    tile.isRecipeInProgress = false;
                    tile.currentTime = 0;
                    tile.infusingTime = 0;
                    tile.requiresEnergy = false;
//                    tile.currentRecipe = null;
                    tile.onTileRemove();

                }

            }
            //if (world.getGameTime() % 40 == 0){
            //    Helpers.updateTile(tile);
            //}else{
            if (forceUpdate) {
                Helpers.updateTile(tile);
            }
            //}

        }else{
            recipeFinalizationParticles(tile,pos,world);
            doParticlesAnimation(world,tile);
        }

    }

    public EaseIn getRotationValue() {
        return rotationValue;
    }

    private static void doParticlesAnimation(Level world, InfuserTileEntity tile){
        if (tile.isRecipeInProgress){
            if (tile.tier == null){
                tile.calculateTier();
            }
            if (tile.tier == Tier.FIRST) {
                firstTierAnimation(tile,world);
            }else if (tile.tier == Tier.RUNIC_ENERGY){
                secondTierAnimation(tile,world);
            }else if (tile.tier == Tier.SOLAR_ENERGY){
                thirdTierAnimation(tile,world);
            }
        }
    }
    public static boolean firstTierAnimation(InfuserTileEntity tile,Level world){
        if (world.getGameTime() % 2 == 0) {
            int r = Math.round(world.random.nextFloat() * 100);
            Vec3 center = Helpers.getBlockCenter(tile.getBlockPos());
            for (int i = 1; i <= 4; i++) {
                double h = Math.toRadians(i * 90 + 30);
                double[] xz = FDMathHelper.rotatePointRadians(7,0,h);
                double x = xz[0];
                double z = xz[1];
                ClientHelpers.Particles.randomline(SCParticleTypes.SPARK_PARTICLE.get(),
                        center.add(x, 4, z), center, 0.5, () -> 255, () -> 255, () -> r, 0.4f,0.05f);
            }
            for (int i = 1; i <= 4; i++) {
                double h = Math.toRadians(i * 90 + 60);
                double[] xz = FDMathHelper.rotatePointRadians(6.5f,0,h);
                double x = xz[0];
                double z = xz[1];
                ClientHelpers.Particles.randomline(SCParticleTypes.SPARK_PARTICLE.get(),
                        center.add(x, 4, z), center, 0.5, () -> 255, () -> 255, () -> r, 0.4f,0.05f);
            }
            ClientHelpers.Particles.verticalCircle(SCParticleTypes.SMALL_SOLAR_STRIKE_PARTICLE.get(),
                    center.add(0, -0.5, 0), 3, 4, new float[]{0, 0, 0}, () -> 255, () -> 255, () -> r, 0.35f);
            return true;
        }else{
            return false;
        }
    }
    public static boolean secondTierAnimation(InfuserTileEntity tile,Level world){

        if (firstTierAnimation(tile,world)) {
            for (BlockPos pos : NotStructures.infusingPoolsPositions(tile.worldPosition)) {
                Vec3 center = Helpers.getBlockCenter(pos);
                ClientHelpers.Particles.verticalCircle(SCParticleTypes.SMALL_SOLAR_STRIKE_PARTICLE.get(),
                        center, 1, 3, new float[]{0, 0, 0}, () -> 255, () -> 255, () -> Math.round(world.random.nextFloat() * 128) + 40, 0.25f);
            }
            return true;
        }else{
            return false;
        }
    }

    public static void thirdTierAnimation(InfuserTileEntity tile,Level world){
        if (secondTierAnimation(tile,world)) {
            Vec3 center = Helpers.getBlockCenter(tile.getBlockPos());
            ClientHelpers.Particles.verticalCircle(SCParticleTypes.SMALL_SOLAR_STRIKE_PARTICLE.get(),
                    center.add(0, 6, 0), 3, 4, new float[]{0, 0, 0}, () -> 255, () -> 255, ()->40, 0.25f);
            ClientHelpers.Particles.verticalCircle(SCParticleTypes.SMALL_SOLAR_STRIKE_PARTICLE.get(),
                    center.add(0, 8, 0), 3, 4, new float[]{0, 0, 0}, () -> 255, () -> 255, ()->40, 0.25f);
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

        ClientboundBlockEntityDataPacket pkt = super.getUpdatePacket();
        CompoundTag tag = saveWithFullMetadata();
        tag.merge(pkt.getTag());

        return Helpers.createTilePacket(this,tag);
    }



    private void resetCatalysts(InfusingRecipe recipe){
        if (recipe.getDeserializedCatalysts() != null){
            int iterator = 0;
            for (BlockPos pos : getCatalystsPositions()){
                Block c = recipe.getDeserializedCatalysts()[iterator];
                if (c != null && c.defaultBlockState().is(Tags.CATALYST)){
                    level.setBlock(pos, SCBlocks.CATALYST_BASE.get().defaultBlockState(), 3);
                }
                iterator++;
            }
        }
    }


    private static void finishRecipe(Level world, InfuserTileEntity tile, InfusingRecipe recipe){
        tile.resetCatalysts(recipe);
        ItemStack result = recipe.getResultItem(world.registryAccess()).copy();
        int count = tile.getMinRecipeCountOutput(recipe);
        for (RunicEnergy.Type type : recipe.RUNIC_ENERGY_COST.getSetTypes()){
            tile.giveEnergy(type,-recipe.RUNIC_ENERGY_COST.get(type)*count);
        }
        if (!recipe.tag.equals("")) {
            if (result.getItem() instanceof IUpgradable result2){
                result2.upgrade(tile.getItem(tile.inputSlot()),result);
            }
        }
        ItemStack prev = tile.getItem(tile.inputSlot());
        if (prev.isEnchanted()){
            Map<Enchantment,Integer> map = EnchantmentHelper.getEnchantments(prev);

            for (Enchantment a : map.keySet()){
                result.enchant(a,map.get(a));
            }

        }

        if ((prev.getItem() instanceof TieredItem) && (result.getItem() instanceof TieredItem)) {
            result.hurt(prev.getDamageValue(), world.random, null);
        }

        result.setCount(count*recipe.count);
        tile.getItem(tile.inputSlot()).grow(-count);
        tile.getInventory().setStackInSlot(tile.outputSlot(), result);
        tile.isRecipeInProgress = false;
        tile.currentRecipe = null;
        tile.infusingTime = 0;
        tile.currentTime = 0;
        tile.deleteStacksInPhantomSlots(count);
        tile.level.playSound(null, tile.worldPosition, SoundEvents.BEACON_DEACTIVATE, SoundSource.AMBIENT, 2, 1);
        tile.solarEnergy -= recipe.requriedSolarEnergy *count;
        tile.resetAllRepeaters();


    }

    public void calculateTier(){
        boolean tier1 = Multiblocks.INFUSER_TIER_ONE.check(level,worldPosition,true);
        boolean tier2 = Multiblocks.INFUSER_TIER_TWO.check(level,worldPosition,true);
        boolean tier3 = Multiblocks.INFUSER_TIER_THREE.check(level,worldPosition,true);
        if (tier3){
            this.tier = Tier.SOLAR_ENERGY;
        }else if (tier2){
            this.tier = Tier.RUNIC_ENERGY;
        }else if (tier1){
            this.tier = Tier.FIRST;
        }else{
            this.tier = null;
        }
    }

    private boolean tierEquals(Tier other){
        if (other == Tier.FIRST){
            return this.tier == Tier.SOLAR_ENERGY || this.tier == Tier.FIRST || this.tier == Tier.RUNIC_ENERGY;
        }else if (other == Tier.RUNIC_ENERGY){
            return this.tier == Tier.SOLAR_ENERGY ||  this.tier == Tier.RUNIC_ENERGY;
        }else{
            return this.tier == Tier.SOLAR_ENERGY;
        }
    }

    public void triggerCrafting(Player playerEntity){
        if (getInventory() == null) {
            playerEntity.sendSystemMessage(Component.literal("Can't access inventory").withStyle(ChatFormatting.RED));
            return;
        }
        Optional<InfusingRecipe> opt = this.level.getRecipeManager().getRecipeFor(SolarcraftRecipeTypes.INFUSING.get(),new PhantomInventory(getInventory()),level);
        calculateTier();
        try {
            if (opt.isPresent() && AncientFragmentHelper.doPlayerHasFragment(playerEntity, AncientFragment.getFragmentByID(opt.get().fragID))) {
                if (!this.getItem(outputSlot()).isEmpty()){
                    playerEntity.sendSystemMessage(Component.literal("Clear the output slot").withStyle(ChatFormatting.RED));
                    return;
                }
                InfusingRecipe recipe = opt.get();
                if (recipe.output.getItem() == SCItems.ENERGY_GENERATOR_BLOCK.get()) {
                    ItemStack input = this.getItem(inputSlot());
                    if (input.getItem() instanceof SunShardItem shard && !shard.isHeated(input)){
                        playerEntity.sendSystemMessage(Component.literal("Unable to start ").withStyle(ChatFormatting.RED)
                                .append(Component.literal("melting").withStyle(ChatFormatting.GOLD)).append(" the item: Sun Shard")
                                .withStyle(ChatFormatting.RED));
                        return;
                    }
                }else if (recipe.output.getItem() == SCItems.ORBITAL_MISSILE_LAUNCHER.get()){
                    if (!SolarcraftConfig.IS_ORBITAL_MISSILE_LAUNCHER_ALLOWED.get()){
                        playerEntity.sendSystemMessage(Component.translatable("solarcraft.message.block_disabled").withStyle(ChatFormatting.RED));
                        return;
                    }
                }

                if (tierEquals(recipe.getTier())) {
                    if (catalystsMatch(recipe)) {
                        if (!isRecipeInProgress) {
                            Helpers.fireProgressionEvent(playerEntity, Progression.SOLAR_INFUSER);
                            this.infusingTime = recipe.infusingTime;
                            this.isRecipeInProgress = true;
                            this.level.playSound(null, this.worldPosition, SoundEvents.BEACON_ACTIVATE, SoundSource.AMBIENT, 2, 1);
                        } else {
                            this.level.playSound(null, this.worldPosition, SoundEvents.VILLAGER_NO, SoundSource.AMBIENT, 2, 1);
                        }
                    }else{
                        playerEntity.sendSystemMessage(Component.literal("Catalysts don't match the recipe.").withStyle(ChatFormatting.RED));
                    }
                }else{
                    playerEntity.sendSystemMessage(Component.literal("Structure invalid.").withStyle(ChatFormatting.RED));
                }

            } else {
                if (opt.isPresent()) {
                    AncientFragment fragment = AncientFragment.getFragmentByID(opt.get().fragID);
                    if (fragment != null){
                        if (!AncientFragmentHelper.doPlayerHasFragment(playerEntity,fragment)){
                            playerEntity.sendSystemMessage(Component.literal("Cant start craft, you dont have "+fragment.getTranslation().getString().toUpperCase()+" fragment unlocked.").withStyle(ChatFormatting.RED));
                        }
                    }
                }else{
                    playerEntity.sendSystemMessage(Component.literal("Recipe invalid").withStyle(ChatFormatting.RED));
                }
                this.level.playSound(null, this.worldPosition, SoundEvents.VILLAGER_NO, SoundSource.AMBIENT, 2, 1);
            }
        }catch (NullPointerException e){
            playerEntity.sendSystemMessage(Component.literal("INCORRECT FRAGMENT IN RECIPE "+ opt.get().output.getDisplayName().getString()+" TELL MOD AUTHOR TO FIX IT").withStyle(ChatFormatting.RED));
        }

    }




    public void updateStacksInPhantomSlots(){
        List<BlockEntity> list = NotStructures.checkInfusingStandStructure(worldPosition,level);
        for (int i = 0;i < list.size();i++){
            int iter = i;
            if (i >= 6){
                iter++;
            }
            if (list.get(i) instanceof InfusingStandTileEntity){
                InfusingStandTileEntity tile = (InfusingStandTileEntity) list.get(i);
                this.setItem(iter,tile.getStackInSlot(0));
            }else{
                this.setItem(iter, ItemStack.EMPTY);
            }
        }
    }
    public void infusingStandsRendering(boolean e){
        List<BlockEntity> list = NotStructures.checkInfusingStandStructure(worldPosition,level);
        for (int i = 0;i < list.size();i++){
            if (list.get(i) instanceof InfusingStandTileEntity stand){
               stand.shouldRenderItem(e);
            }
        }
    }

    private int getMinRecipeCountOutput(InfusingRecipe recipe){
        AtomicInteger count = new AtomicInteger((int)Math.floor(64/(float)recipe.count));

        double maxenergycost = getMaxRunicEnergyCostFromRecipe(recipe);
        if (maxenergycost != 0){
            int maxItems = (int)Math.floor(this.getMaxSolarEnergy()/maxenergycost);
            if (maxItems < count.get()){
                count.set(maxItems);
            }
        }
        int solarEnergyCost = recipe.requriedSolarEnergy;
        if (solarEnergyCost != 0){
            int maxItems = (int)Math.floor(this.getMaxSolarEnergy()/(float)solarEnergyCost);
            if (maxItems < count.get()){
                count.set(maxItems);
            }
        }

        NotStructures.checkInfusingStandStructure(worldPosition,level).forEach((tile)->{
            if (tile instanceof InfusingStandTileEntity pool){
                if ((pool.getStackInSlot(0).getItem() != Items.AIR) && pool.getStackInSlot(0).getCount() < count.get()){
                    count.set(pool.getStackInSlot(0).getCount());
                }
            }
        });
        if (this.getItem(inputSlot()).getCount() < count.get()){
            count.set(getItem(inputSlot()).getCount());
        }
        return count.get();
    }


    private float getMaxRunicEnergyCostFromRecipe(InfusingRecipe recipe){
//        AtomicDouble integer = new AtomicDouble(0);
//        recipe.RUNIC_ENERGY_COST.forEach((type,baseCost)->{
//            if (baseCost > integer.get()){
//                integer.set(baseCost);
//            }
//        });
        float max = 0;
        RunicEnergyCost cost = recipe.RUNIC_ENERGY_COST;
        for (RunicEnergy.Type type : cost.getSetTypes()){
            float energy = cost.get(type);
            if (energy > max){
                max = energy;
            }
        }
        return max;
    }


    public void deleteStacksInPhantomSlots(int amount){
        List<BlockEntity> list = NotStructures.checkInfusingStandStructure(worldPosition,level);
        for (int i = 0;i < list.size();i++){
            if (list.get(i) instanceof InfusingStandTileEntity){
                InfusingStandTileEntity tile = (InfusingStandTileEntity) list.get(i);
                tile.getStackInSlot(0).grow(-amount);
                Helpers.updateTile(tile);
            }
        }
    }



    public Block[] getCurrentCatalystPattern(){
        Block[] block = {
                level.getBlockState(worldPosition.above().west(3).north(6)).getBlock(),
                level.getBlockState(worldPosition.above().north(6)).getBlock(),
                level.getBlockState(worldPosition.above().east(3).north(6)).getBlock(),

                level.getBlockState(worldPosition.above().east(6).north(3)).getBlock(),
                level.getBlockState(worldPosition.above().east(6)).getBlock(),
                level.getBlockState(worldPosition.above().east(6).south(3)).getBlock(),

                level.getBlockState(worldPosition.above().south(6).east(3)).getBlock(),
                level.getBlockState(worldPosition.above().south(6)).getBlock(),
                level.getBlockState(worldPosition.above().south(6).west(3)).getBlock(),

                level.getBlockState(worldPosition.above().west(6).south(3)).getBlock(),
                level.getBlockState(worldPosition.above().west(6)).getBlock(),
                level.getBlockState(worldPosition.above().west(6).north(3)).getBlock()
        };

        return block;
    }

    public BlockPos[] getCatalystsPositions(){
        return new BlockPos[]{
                worldPosition.above().west(3).north(6),
                worldPosition.above().north(6),
                worldPosition.above().east(3).north(6),
                worldPosition.above().east(6).north(3),
                worldPosition.above().east(6),
                worldPosition.above().east(6).south(3),
                worldPosition.above().south(6).east(3),
                worldPosition.above().south(6),
                worldPosition.above().south(6).west(3),
                worldPosition.above().west(6).south(3),
                worldPosition.above().west(6),
                worldPosition.above().west(6).north(3)
        };
    }



    private boolean isStructureCorrect(){
        return tier != null && this.tier.structure.check(level,worldPosition,true);
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
                "SOLAR ENERGY "+ solarEnergy
        );
    }

    public void onTileRemove(){
        this.resetAllRepeaters();
    }


    public boolean catalystsMatch(InfusingRecipe recipe){
        if (recipe.getDeserializedCatalysts() != null) {
            Block[] c = getCurrentCatalystPattern();
            for (int i = 0 ; i < 12;i++){
                Block bl = recipe.getDeserializedCatalysts()[i];
                if (bl != null && bl.defaultBlockState().is(Tags.CATALYST)){
                    if (bl != c[i]){
                        return false;
                    }
                }
            }
        }else{
            return true;
        }
        return true;
    }

    public static boolean doRecipeRequiresRunicEnergy(RunicEnergyCost costs){
        for (double cost : costs.getCosts()){
            if (Math.round(cost) != 0){
                return true;
            }
        }
        return false;
    }


    private static void recipeFinalizationParticles(InfuserTileEntity tile,BlockPos pos,Level world){
        int maxTime = Math.min(tile.infusingTime, 100);
        if (tile.isRecipeInProgress && (tile.infusingTime - tile.currentTime <= maxTime)){
            tile.getRotationValue().setDuration(maxTime);
            tile.getRotationValue().tick();

            BlockPos[] offsets = NotStructures.infusingPoolsPositions(BlockPos.ZERO);

            float rotValue = (float) tile.getRotationValue().getValue();
            for (int i = 0; i < 14; i++) {
                int iter = i;
                //kostyli nashe vse!
                if (i == 6) continue;
                if (i > 6) iter--;
                if (!tile.getItem(i).isEmpty()) {
                    BlockPos p = offsets[iter];
                    Vec3 v = new Vec3(p.getX(), p.getY(), p.getZ()).multiply(1 - rotValue, 1 - rotValue, 1 - rotValue).yRot(-(float) Math.toRadians(rotValue * 360));
                    Vec3 ps = Helpers.getBlockCenter(pos).add(v);
                    ClientHelpers.Particles.createParticle(SCParticleTypes.SMALL_SOLAR_STRIKE_PARTICLE.get(), ps.x, ps.y, ps.z,
                            0, 0, 0, () -> 255, () -> 255, () -> 0, 0.25f);
                    world.addParticle(SCParticleTypes.SMALL_SOLAR_STRIKE_PARTICLE.get(), ps.x, ps.y, ps.z, 0, 0, 0);
                }
            }

            if (world.getGameTime() % 5 == 0) {
                tile.infusingStandsRendering(false);
            }

        }else {
            tile.getRotationValue().reset();
            if (world.getGameTime() % 5 == 0) {
                tile.infusingStandsRendering(true);
            }
        }
    }

    @Override
    public boolean bind(BlockPos pos) {
        return false;
    }

    @Override
    public int getSolarEnergy() {
        return solarEnergy;
    }

    @Override
    public void setSolarEnergy(int energy) {
        boolean flag = energy == getMaxSolarEnergy();
        this.solarEnergy = energy;
        if (!flag) {
            Helpers.updateTile(this);
        }
    }

    @Override
    public int getMaxSolarEnergy() {
        return MAX_SOLAR_ENERGY;
    }

    @Override
    public BlockPos getPos() {
        return worldPosition;
    }

    @Override
    public double getSolarEnergyCollectionRadius() {
        return 14;
    }

    @Override
    public boolean canBeBinded() {
        return true;
    }

    @Override
    public int maxEnergyInput() {
        return 20;
    }

    @Override
    public void onWandUse(BlockPos usePos,Player user) {
        triggerCrafting(user);
    }

    @Override
    public List<MultiblockStructure> getMultiblocks() {
        return List.of(
          Multiblocks.INFUSER_TIER_ONE,
          Multiblocks.INFUSER_TIER_TWO,
          Multiblocks.INFUSER_TIER_THREE
        );
    }


    public enum Tier{
        FIRST("first",Multiblocks.INFUSER_TIER_ONE),
        RUNIC_ENERGY("runic_energy",Multiblocks.INFUSER_TIER_TWO),
        SOLAR_ENERGY("solar_energy",Multiblocks.INFUSER_TIER_THREE)
        ;
        private String id;
        private MultiblockStructure structure;

        Tier(String id,MultiblockStructure struct){
            this.id = id;
            this.structure = struct;


        }

        public MultiblockStructure getStructure() {
            return structure;
        }

        public String getId() {
            return id;
        }

        public static Tier byId(String id){
            for (Tier t : Tier.class.getEnumConstants()){
                if (t.id.equals(id)){
                    return t;
                }
            }
            return null;
        }
    }

    @Override
    public AABB getRenderBoundingBox() {
        return new AABB(-5,-5,-5,5,5,5).move(Helpers.getBlockCenter(getBlockPos()));
    }

    public int inputSlot(){
        return 6;
    }

    public int outputSlot(){
        return 13;
    }
}
