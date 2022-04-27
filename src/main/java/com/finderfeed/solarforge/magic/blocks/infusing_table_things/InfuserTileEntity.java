package com.finderfeed.solarforge.magic.blocks.infusing_table_things;


import com.finderfeed.solarforge.ClientHelpers;
import com.finderfeed.solarforge.Helpers;
import com.finderfeed.solarforge.SolarForge;
import com.finderfeed.solarforge.client.particles.ParticleTypesRegistry;
import com.finderfeed.solarforge.local_library.helpers.FDMathHelper;
import com.finderfeed.solarforge.local_library.other.EaseIn;
import com.finderfeed.solarforge.magic.blocks.blockentities.REItemHandlerBlockEntity;
import com.finderfeed.solarforge.magic.blocks.infusing_table_things.infusing_pool.InfusingStandTileEntity;
import com.finderfeed.solarforge.magic.items.runic_energy.RunicEnergyCost;
import com.finderfeed.solarforge.magic.items.solar_lexicon.unlockables.AncientFragment;
import com.finderfeed.solarforge.magic.items.solar_lexicon.unlockables.ProgressionHelper;
import com.finderfeed.solarforge.misc_things.*;
import com.finderfeed.solarforge.multiblocks.Multiblocks;
import com.finderfeed.solarforge.recipe_types.infusing_new.InfusingRecipe;
import com.finderfeed.solarforge.magic.items.solar_lexicon.progressions.Progression;
import com.finderfeed.solarforge.registries.blocks.BlocksRegistry;
import com.finderfeed.solarforge.world_generation.structures.Structures;
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
import net.minecraft.network.chat.TextComponent;
import net.minecraft.ChatFormatting;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class InfuserTileEntity extends REItemHandlerBlockEntity implements  IEnergyUser, IBindable, ISolarEnergyContainer, OneWay,DebugTarget {


    private EaseIn rotationValue = new EaseIn(0,1,100);

    private Tier tier = null;

    public int energy = 0;
    public int INFUSING_TIME;
    public int CURRENT_PROGRESS;
    public boolean RECIPE_IN_PROGRESS = false;
    public boolean requiresEnergy = false;
    private InfusingRecipe currentRecipe;


    public InfuserTileEntity(BlockPos p_155630_, BlockState p_155631_) {
        super(SolarForge.INFUSING_STAND_BLOCKENTITY.get(), p_155630_, p_155631_);
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
        cmp.putInt("energy",energy);
        cmp.putInt("infusing_time",INFUSING_TIME );
        cmp.putInt("recipe_progress",CURRENT_PROGRESS );
        cmp.putBoolean("is_recipe_in_progress",RECIPE_IN_PROGRESS );
        if (this.tier != null) {
            cmp.putString("tierid", this.tier.id);
        }else{
            cmp.putString("tierid", "null");
        }
//        if (!this.trySaveLootTable(cmp)) {
//            ContainerHelper.saveAllItems(cmp, this.items);
//        }

    }



    @Override
    public void load( CompoundTag cmp) {
        super.load(cmp);
        requiresEnergy = cmp.getBoolean("reqenergy");
        tier = Tier.byId(cmp.getString("tierid"));
        energy = cmp.getInt("energy");
        INFUSING_TIME = cmp.getInt("infusing_time");
        CURRENT_PROGRESS = cmp.getInt("recipe_progress");
        RECIPE_IN_PROGRESS = cmp.getBoolean("is_recipe_in_progress");

    }

    @Override
    public double getMaxEnergyInput() {
        return 30;
    }

    @Override
    public double getRunicEnergyLimit() {
        return 100000;
    }

    @Override
    public int getSeekingCooldown() {
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
            if (tile.RECIPE_IN_PROGRESS) {

                Optional<InfusingRecipe> recipe;
                if (tile.currentRecipe == null){
                    recipe = tile.level.getRecipeManager().getRecipeFor(SolarForge.INFUSING_RECIPE_TYPE, new PhantomInventory(inv), world);
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
                    tile.RECIPE_IN_PROGRESS = false;
                    tile.CURRENT_PROGRESS = 0;
                    tile.INFUSING_TIME = 0;
                    tile.requiresEnergy = false;
                    tile.currentRecipe = null;
                    tile.onTileRemove();
                    tile.clearWays();
                }
                if (tile.RECIPE_IN_PROGRESS && tile.catalystsMatch(recipe.get()) && tile.isStructureCorrect()) {
                    forceUpdate = true;
                    InfusingRecipe recipe1 = recipe.get();

                    int count = tile.getMinRecipeCountOutput(recipe1);
                    RunicEnergyCost costs = recipe1.RUNIC_ENERGY_COST;
                    tile.INFUSING_TIME = recipe1.infusingTime * count;

                    boolean check = tile.hasEnoughRunicEnergy(costs, count);
                    if ((tile.energy >= recipe1.requriedEnergy * count) && check) {
                        tile.onTileRemove();
                        tile.clearWays();
                        tile.requiresEnergy = false;
                        tile.CURRENT_PROGRESS++;
                        if (tile.CURRENT_PROGRESS >= tile.INFUSING_TIME) {
                            finishRecipe(world, tile, recipe1);
                        }
                    } else {
                        tile.requestRunicEnergy(costs, count);
                        tile.requiresEnergy = !(tile.energy >= recipe1.requriedEnergy * count);
                    }

                } else {
                    tile.currentRecipe = null;
                    tile.RECIPE_IN_PROGRESS = false;
                    tile.CURRENT_PROGRESS = 0;
                    tile.INFUSING_TIME = 0;
                    tile.requiresEnergy = false;
                    tile.currentRecipe = null;
                    tile.onTileRemove();
                    tile.clearWays();
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
        if (tile.RECIPE_IN_PROGRESS){
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
                ClientHelpers.ParticleAnimationHelper.randomline(ParticleTypesRegistry.SPARK_PARTICLE.get(),
                        center.add(x, 4, z), center, 0.5, () -> 255, () -> 255, () -> r, 0.4f,0.05f);
            }
            for (int i = 1; i <= 4; i++) {
                double h = Math.toRadians(i * 90 + 60);
                double[] xz = FDMathHelper.rotatePointRadians(6.5f,0,h);
                double x = xz[0];
                double z = xz[1];
                ClientHelpers.ParticleAnimationHelper.randomline(ParticleTypesRegistry.SPARK_PARTICLE.get(),
                        center.add(x, 4, z), center, 0.5, () -> 255, () -> 255, () -> r, 0.4f,0.05f);
            }
            ClientHelpers.ParticleAnimationHelper.verticalCircle(ParticleTypesRegistry.SMALL_SOLAR_STRIKE_PARTICLE.get(),
                    center.add(0, -0.5, 0), 3, 4, new float[]{0, 0, 0}, () -> 255, () -> 255, () -> r, 0.35f);
            return true;
        }else{
            return false;
        }
    }
    public static boolean secondTierAnimation(InfuserTileEntity tile,Level world){

        if (firstTierAnimation(tile,world)) {
            for (BlockPos pos : Structures.infusingPoolsPositions(tile.worldPosition)) {
                Vec3 center = Helpers.getBlockCenter(pos);
                ClientHelpers.ParticleAnimationHelper.verticalCircle(ParticleTypesRegistry.SMALL_SOLAR_STRIKE_PARTICLE.get(),
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
            ClientHelpers.ParticleAnimationHelper.verticalCircle(ParticleTypesRegistry.SMALL_SOLAR_STRIKE_PARTICLE.get(),
                    center.add(0, 6, 0), 3, 4, new float[]{0, 0, 0}, () -> 255, () -> 255, ()->40, 0.25f);
            ClientHelpers.ParticleAnimationHelper.verticalCircle(ParticleTypesRegistry.SMALL_SOLAR_STRIKE_PARTICLE.get(),
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
                if (c != null){
                    level.setBlock(pos, BlocksRegistry.CATALYST_BASE.get().defaultBlockState(), 3);
                }
                iterator++;
            }
        }
    }


    private static void finishRecipe(Level world, InfuserTileEntity tile, InfusingRecipe recipe){
        tile.resetCatalysts(recipe);
        ItemStack result = recipe.getResultItem();
        int count = tile.getMinRecipeCountOutput(recipe);
//        recipe.RUNIC_ENERGY_COST.forEach((type,baseCost)->{
//            tile.giveEnergy(type,-baseCost*count);
//        });
        for (RunicEnergy.Type type : recipe.RUNIC_ENERGY_COST.getSetTypes()){
            tile.giveEnergy(type,-recipe.RUNIC_ENERGY_COST.get(type)*count);
        }
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

        if ((prev.getItem() instanceof TieredItem) && (result.getItem() instanceof TieredItem)) {
            result.hurt(prev.getDamageValue(), world.random, null);
        }

        result.setCount(count*recipe.count);
        tile.getItem(tile.inputSlot()).grow(-count);
        tile.getInventory().setStackInSlot(tile.outputSlot(), result);
        tile.RECIPE_IN_PROGRESS = false;
        tile.currentRecipe = null;
        tile.INFUSING_TIME = 0;
        tile.CURRENT_PROGRESS = 0;
        tile.deleteStacksInPhantomSlots(count);
        tile.level.playSound(null, tile.worldPosition, SoundEvents.BEACON_DEACTIVATE, SoundSource.AMBIENT, 2, 1);
        tile.energy-= recipe.requriedEnergy*count;
        tile.onRemove();
        tile.clearWays();

    }

    public void calculateTier(){
        boolean tier1 = Helpers.checkStructure(level,worldPosition.below(1).north(6).west(6),Multiblocks.INFUSER_TIER_FIRST.getM(), true);
        boolean tier2 = Helpers.checkStructure(level,worldPosition.below(1).north(9).west(9),Multiblocks.INFUSER_TIER_RUNIC_ENERGY.getM(), true);
        boolean tier3 = Helpers.checkStructure(level,worldPosition.below(1).north(9).west(9),Multiblocks.INFUSER_TIER_SOLAR_ENERGY.getM(), true);
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
            playerEntity.sendMessage(new TextComponent("Cant access inventory").withStyle(ChatFormatting.RED),
                    playerEntity.getUUID());
            return;
        }
        Optional<InfusingRecipe> recipe = this.level.getRecipeManager().getRecipeFor(SolarForge.INFUSING_RECIPE_TYPE,new PhantomInventory(getInventory()),level);
        calculateTier();
        try {
            if (recipe.isPresent() && ProgressionHelper.doPlayerHasFragment(playerEntity, AncientFragment.getFragmentByID(recipe.get().fragID))) {
                if (!this.getItem(outputSlot()).isEmpty()){
                    playerEntity.sendMessage(new TextComponent("Clear the output slot").withStyle(ChatFormatting.RED),
                            playerEntity.getUUID());
                    return;
                }
                if (tierEquals(recipe.get().getTier())) {
                    if (catalystsMatch(recipe.get())) {
                        if (!RECIPE_IN_PROGRESS) {
                            Helpers.fireProgressionEvent(playerEntity, Progression.SOLAR_INFUSER);
                            this.INFUSING_TIME = recipe.get().infusingTime;
                            this.RECIPE_IN_PROGRESS = true;
                            this.level.playSound(null, this.worldPosition, SoundEvents.BEACON_ACTIVATE, SoundSource.AMBIENT, 2, 1);
                        } else {
                            this.level.playSound(null, this.worldPosition, SoundEvents.VILLAGER_NO, SoundSource.AMBIENT, 2, 1);
                        }
                    }else{
                        playerEntity.sendMessage(new TextComponent("Catalysts don't match the recipe.").withStyle(ChatFormatting.RED),
                                playerEntity.getUUID());
                    }
                }else{
                    playerEntity.sendMessage(new TextComponent("Structure invalid.").withStyle(ChatFormatting.RED),
                            playerEntity.getUUID());
                }

            } else {
                if (recipe.isPresent()) {
                    AncientFragment fragment = AncientFragment.getFragmentByID(recipe.get().fragID);
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
        List<BlockEntity> list = Structures.checkInfusingStandStructure(worldPosition,level);
        for (int i = 0;i < list.size();i++){
            if (list.get(i) instanceof InfusingStandTileEntity stand){
               stand.shouldRenderItem(e);
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


    private float getMaxEnergyCostFromRecipe(InfusingRecipe recipe){
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
        return energy;
    }


    public void deleteStacksInPhantomSlots(int amount){
        List<BlockEntity> list = Structures.checkInfusingStandStructure(worldPosition,level);
        for (int i = 0;i < list.size();i++){
            if (list.get(i) instanceof InfusingStandTileEntity){
                InfusingStandTileEntity tile = (InfusingStandTileEntity) list.get(i);
                tile.getStackInSlot(0).grow(-amount);
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

    private boolean isStructureCorrect(){
        return tier != null && Helpers.checkStructure(level, worldPosition.below().north(tier.offsetPos).west(tier.offsetPos), this.tier.structure, true);
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
        update(this);
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


    public boolean catalystsMatch(InfusingRecipe recipe){
        if (recipe.getDeserializedCatalysts() != null) {
            Block[] c = getCurrentCatalystPattern();
            for (int i = 0 ; i < 12;i++){
                Block bl = recipe.getDeserializedCatalysts()[i];
                if (bl != null){
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
        int maxTime = Math.min(tile.INFUSING_TIME, 100);
        if (tile.RECIPE_IN_PROGRESS && (tile.INFUSING_TIME - tile.CURRENT_PROGRESS <= maxTime)){
            tile.getRotationValue().setDuration(maxTime);
            tile.getRotationValue().tick();

            BlockPos[] offsets = Structures.infusingPoolsPositions(BlockPos.ZERO);

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
                    ClientHelpers.ParticleAnimationHelper.createParticle(ParticleTypesRegistry.SMALL_SOLAR_STRIKE_PARTICLE.get(), ps.x, ps.y, ps.z,
                            0, 0, 0, () -> 255, () -> 255, () -> 0, 0.25f);
                    world.addParticle(ParticleTypesRegistry.SMALL_SOLAR_STRIKE_PARTICLE.get(), ps.x, ps.y, ps.z, 0, 0, 0);
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


    public enum Tier{
        FIRST("first",Multiblocks.INFUSER_TIER_FIRST.getM(), 6),
        RUNIC_ENERGY("runic_energy",Multiblocks.INFUSER_TIER_RUNIC_ENERGY.getM(), 9),
        SOLAR_ENERGY("solar_energy",Multiblocks.INFUSER_TIER_SOLAR_ENERGY.getM(), 9)
        ;
        private String id;
        private Multiblock structure;
        private int offsetPos;
        Tier(String id,Multiblock struct,int offsetPos){
            this.id = id;
            this.structure = struct;
            this.offsetPos = offsetPos;

        }

        public Multiblock getStructure() {
            return structure;
        }

        public int getOffsetPos() {
            return offsetPos;
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
