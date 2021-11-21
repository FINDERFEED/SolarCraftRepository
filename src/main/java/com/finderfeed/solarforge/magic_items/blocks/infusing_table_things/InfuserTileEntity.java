package com.finderfeed.solarforge.magic_items.blocks.infusing_table_things;


import com.finderfeed.solarforge.ClientHelpers;
import com.finderfeed.solarforge.Helpers;
import com.finderfeed.solarforge.SolarForge;
import com.finderfeed.solarforge.for_future_library.helpers.FinderfeedMathHelper;
import com.finderfeed.solarforge.for_future_library.helpers.RenderingTools;
import com.finderfeed.solarforge.magic_items.blocks.blockentities.runic_energy.AbstractRunicEnergyContainerRCBE;
import com.finderfeed.solarforge.magic_items.blocks.infusing_table_things.infusing_pool.InfusingPoolTileEntity;
import com.finderfeed.solarforge.magic_items.items.solar_lexicon.unlockables.AncientFragment;
import com.finderfeed.solarforge.magic_items.items.solar_lexicon.unlockables.ProgressionHelper;
import com.finderfeed.solarforge.misc_things.*;
import com.finderfeed.solarforge.multiblocks.Multiblocks;
import com.finderfeed.solarforge.packet_handler.SolarForgePacketHandler;
import com.finderfeed.solarforge.recipe_types.InfusingRecipe;
import com.finderfeed.solarforge.magic_items.items.solar_lexicon.achievements.Progression;
import com.finderfeed.solarforge.registries.blocks.BlocksRegistry;
import com.finderfeed.solarforge.world_generation.structures.Structures;
import com.google.common.util.concurrent.AtomicDouble;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.core.NonNullList;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fmllegacy.network.PacketDistributor;
import org.apache.logging.log4j.core.tools.picocli.CommandLine;

import javax.annotation.Nullable;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class InfuserTileEntity extends AbstractRunicEnergyContainerRCBE implements  IEnergyUser, IBindable, ISolarEnergyContainer, OneWay,DebugTarget {



    private Tier tier = null;

    public int energy = 0;
    public int INFUSING_TIME;
    public int CURRENT_PROGRESS;
    public boolean RECIPE_IN_PROGRESS = false;
    public boolean requiresEnergy = false;
    public NonNullList<ItemStack> items = NonNullList.withSize(10,ItemStack.EMPTY);

    public InfuserTileEntity(BlockPos p_155630_, BlockState p_155631_) {
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
        return new InfuserContainer(x,inv,this);
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
        if (!this.trySaveLootTable(cmp)) {
            ContainerHelper.saveAllItems(cmp, this.items);
        }
        return cmp;
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

    @Override
    public int getSeekingCooldown() {
        return -1;
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
                if (tile.RECIPE_IN_PROGRESS && tile.catalystsMatch(recipe.get()) && tile.isStructureCorrect()){

                    tile.setChanged();
                    world.sendBlockUpdated(tile.worldPosition,blockState,blockState,3);
                    InfusingRecipe recipe1 = recipe.get();

                    int count = tile.getMinRecipeCountOutput(recipe1);
                    Map<RunicEnergy.Type,Double> costs = recipe1.RUNIC_ENERGY_COST;
                    tile.INFUSING_TIME = recipe1.infusingTime*count;

                    boolean check = tile.hasEnoughRunicEnergy(costs,count);
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

                }else{
                    tile.RECIPE_IN_PROGRESS = false;
                    tile.CURRENT_PROGRESS =0;
                    tile.INFUSING_TIME = 0;
                    tile.requiresEnergy = false;
                    tile.onTileRemove();
                    tile.clearWays();
                }

                if (world.getGameTime() % 5 == 1) {
                    sendUpdatePackets(world, tile);
                }
        }
        doParticlesAnimation(world,tile);
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
            int r = Math.round(world.random.nextFloat() * 40);
            Vec3 center = Helpers.getBlockCenter(tile.getBlockPos());
            for (int i = 1; i <= 4; i++) {
                double h = Math.toRadians(i * 90 + 30);
                double[] xz = FinderfeedMathHelper.rotatePointRadians(7,0,h);
                double x = xz[0];
                double z = xz[1];
                ClientHelpers.ParticleAnimationHelper.timedLine(ParticlesList.SMALL_SOLAR_STRIKE_PARTICLE.get(),
                        center.add(x, 4, z), center, 100, () -> 255, () -> 255, () -> r, 0.25f);
            }
            for (int i = 1; i <= 4; i++) {
                double h = Math.toRadians(i * 90 + 60);
                double[] xz = FinderfeedMathHelper.rotatePointRadians(6.5f,0,h);
                double x = xz[0];
                double z = xz[1];
                ClientHelpers.ParticleAnimationHelper.timedLine(ParticlesList.SMALL_SOLAR_STRIKE_PARTICLE.get(),
                        center.add(x, 4, z), center, 100, () -> 255, () -> 255, () -> r, 0.25f);
            }
            ClientHelpers.ParticleAnimationHelper.verticalCircle(ParticlesList.SMALL_SOLAR_STRIKE_PARTICLE.get(),
                    center.add(0, -0.5, 0), 3, 4, new float[]{0, 0, 0}, () -> 255, () -> 255, () -> r, 0.25f);
            return true;
        }else{
            return false;
        }
    }
    public static boolean secondTierAnimation(InfuserTileEntity tile,Level world){

        if (firstTierAnimation(tile,world)) {
            for (BlockPos pos : Structures.infusingPoolsPositions(tile.worldPosition)) {
                Vec3 center = Helpers.getBlockCenter(pos);
                ClientHelpers.ParticleAnimationHelper.verticalCircle(ParticlesList.SMALL_SOLAR_STRIKE_PARTICLE.get(),
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
            ClientHelpers.ParticleAnimationHelper.verticalCircle(ParticlesList.SMALL_SOLAR_STRIKE_PARTICLE.get(),
                    center.add(0, 6, 0), 3, 4, new float[]{0, 0, 0}, () -> 255, () -> 255, ()->40, 0.25f);
            ClientHelpers.ParticleAnimationHelper.verticalCircle(ParticlesList.SMALL_SOLAR_STRIKE_PARTICLE.get(),
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
        CompoundTag tag = pkt.getTag();
        this.save(tag);

        return new ClientboundBlockEntityDataPacket(worldPosition,3,tag);
    }

    private static void sendUpdatePackets(Level world, InfuserTileEntity tile){
//        SolarForgePacketHandler.INSTANCE.send(PacketDistributor.NEAR.with(PacketDistributor.TargetPoint.p(tile.worldPosition.getX(),tile.worldPosition.getY(),tile.worldPosition.getZ(),20,tile.level.dimension())),
//                new UpdateProgressOnClientPacket(tile.INFUSING_TIME,tile.CURRENT_PROGRESS,tile.worldPosition,tile.requiresEnergy,tile.energy));
        ItemStack[] arr = {tile.getItem(0),tile.getItem(1),tile.getItem(2),tile.getItem(3),tile.getItem(4),tile.getItem(5),tile.getItem(6),tile.getItem(7),tile.getItem(8)};
        SolarForgePacketHandler.INSTANCE.send(PacketDistributor.NEAR.with(PacketDistributor.TargetPoint.p(tile.worldPosition.getX(),tile.worldPosition.getY(),tile.worldPosition.getZ(),20,tile.level.dimension())),
                new UpdateStacksOnClientTable(arr,tile.getItem(9),tile.worldPosition,tile.RECIPE_IN_PROGRESS));
    }


    private void resetCatalysts(InfusingRecipe recipe){
        if (recipe.getDeserializedCatalysts() != null){
            int iterator = 0;
            for (BlockPos pos : getCatalystsPositions()){
                Block c = recipe.getDeserializedCatalysts()[iterator];
                if (c != null){
                    level.setBlock(pos, BlocksRegistry.CATALYST_BASE.get().defaultBlockState(), Constants.BlockFlags.DEFAULT);
                }
                iterator++;
            }
        }
    }


    private static void finishRecipe(Level world, InfuserTileEntity tile, InfusingRecipe recipe){
        tile.resetCatalysts(recipe);
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
        Optional<InfusingRecipe> recipe = this.level.getRecipeManager().getRecipeFor(SolarForge.INFUSING_RECIPE_TYPE,(Container) this,level);
        calculateTier();
        try {
            if (recipe.isPresent() && ProgressionHelper.doPlayerHasFragment(playerEntity, AncientFragment.getFragmentByID(recipe.get().child))) {

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



    public Block[] getCurrentCatalystPattern(){
        Block[] block = {
                level.getBlockState(worldPosition.above().west(6).north(3)).getBlock(),
                level.getBlockState(worldPosition.above().west(4).north(4)).getBlock(),
                level.getBlockState(worldPosition.above().west(3).north(6)).getBlock(),

                level.getBlockState(worldPosition.above().east(3).north(6)).getBlock(),
                level.getBlockState(worldPosition.above().east(4).north(4)).getBlock(),
                level.getBlockState(worldPosition.above().east(6).north(3)).getBlock(),

                level.getBlockState(worldPosition.above().east(6).south(3)).getBlock(),
                level.getBlockState(worldPosition.above().east(4).south(4)).getBlock(),
                level.getBlockState(worldPosition.above().east(3).south(6)).getBlock(),

                level.getBlockState(worldPosition.above().west(3).south(6)).getBlock(),
                level.getBlockState(worldPosition.above().west(4).south(4)).getBlock(),
                level.getBlockState(worldPosition.above().west(6).south(3)).getBlock()
        };

        return block;
    }

    public BlockPos[] getCatalystsPositions(){
        return new BlockPos[]{
                worldPosition.above().west(6).north(3),
                worldPosition.above().west(4).north(4),
                worldPosition.above().west(3).north(6),
                worldPosition.above().east(3).north(6),
                worldPosition.above().east(4).north(4),
                worldPosition.above().east(6).north(3),
                worldPosition.above().east(6).south(3),
                worldPosition.above().east(4).south(4),
                worldPosition.above().east(3).south(6),
                worldPosition.above().west(3).south(6),
                worldPosition.above().west(4).south(4),
                worldPosition.above().west(6).south(3)
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

    public static boolean doRecipeRequiresRunicEnergy(Map<RunicEnergy.Type,Double> costs){
        for (double cost : costs.values()){
            if (Math.round(cost) != 0){
                return true;
            }
        }
        return false;
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


}
