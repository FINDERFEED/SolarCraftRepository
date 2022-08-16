package com.finderfeed.solarforge.magic.blocks.blockentities;

import com.finderfeed.solarforge.ClientHelpers;
import com.finderfeed.solarforge.Helpers;
import com.finderfeed.solarforge.SolarForge;
import com.finderfeed.solarforge.local_library.OwnedBlock;
import com.finderfeed.solarforge.magic.items.solar_lexicon.progressions.Progression;
import com.finderfeed.solarforge.magic.items.solar_lexicon.unlockables.ProgressionHelper;
import com.finderfeed.solarforge.client.particles.SolarcraftParticleTypes;
import com.finderfeed.solarforge.misc_things.PhantomInventory;
import com.finderfeed.solarforge.recipe_types.infusing_crafting.InfusingCraftingRecipe;
import com.finderfeed.solarforge.registries.tile_entities.SolarcraftTileEntityTypes;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nullable;
import java.util.*;

public class InfusingTableTile extends BlockEntity implements OwnedBlock {
    public static final int ANIM_TIME = 100;
    private PhantomInventory phantomInv = new PhantomInventory(10);
    private UUID owner;
    private boolean recipeTrigerred = false;
    private int recipeTime = 0;
    private int remainingRecipeTime = -1;

    public InfusingTableTile( BlockPos p_155229_, BlockState p_155230_) {
        super(SolarcraftTileEntityTypes.INFUSING_CRAFTING_TABLE.get(), p_155229_, p_155230_);
    }


    public static void tick(Level world,BlockPos pos,BlockState state,InfusingTableTile tile){
        if (!world.isClientSide){
            int updateTime = 40;
            if (tile.isRecipeInProgress()){
                IItemHandler handler = tile.getInventory();
                Optional<InfusingCraftingRecipe> optional = world.getRecipeManager().getRecipeFor(SolarForge.INFUSING_CRAFTING_RECIPE_TYPE, tile.phantomInv.set(handler), world);
                if (optional.isPresent()) {
                    InfusingCraftingRecipe recipe = optional.get();
                    int recipeTime = recipe.getTime();
                    int maxOutput = tile.calculateMaximumRecipeOutput(recipe);
                    if (tile.getCurrentTime() < recipeTime*maxOutput){
                        if (recipeTime - tile.getCurrentTime() <= ANIM_TIME){
                            updateTime = 1;
                            tile.remainingRecipeTime = ANIM_TIME - (recipeTime - tile.getCurrentTime());
                        }
                        tile.recipeTime++;
                    }else{
                        ItemStack stack = recipe.getOutput().copy();
                        if (stack.is(SolarForge.SOLAR_FORGE_ITEM.get()) || stack.is(SolarForge.INFUSER_ITEM.get())) {
                            if (tile.getOwner() != null) {
                                Player player = world.getPlayerByUUID(tile.getOwner());
                                if (player != null) {
                                    if (stack.is(SolarForge.SOLAR_FORGE_ITEM.get())) {
                                        Helpers.fireProgressionEvent(player, Progression.CRAFT_SOLAR_FORGE);
                                    }else if (stack.is(SolarForge.INFUSER_ITEM.get())){
                                        Helpers.fireProgressionEvent(player,Progression.SOLAR_INFUSER);
                                    }
                                }
                            }
                        }
                        for (int i = 0; i < 9;i++){
                            handler.extractItem(i,maxOutput,false);
                        }
                        stack.setCount(maxOutput*recipe.getOutputCount());
                        handler.insertItem(9,stack,false);
                        tile.recipeTrigerred = false;
                        tile.recipeTime = 0;
                        tile.remainingRecipeTime = -1;
                        tile.update();
                    }
                }else{
                    tile.remainingRecipeTime = -1;
                    tile.recipeTrigerred = false;
                }

            }else{
                tile.remainingRecipeTime = -1;
                tile.recipeTime = 0;
            }


            if (world.getGameTime() % updateTime == 0){
                tile.update();
            }
        }


        if (world.isClientSide){
            tile.clientTick();
        }

    }


    public void clientTick(){
        if (isRecipeInProgress() && (level.getGameTime() % 2 == 0) ){
            float time = -level.getGameTime()*4;
            Vec3 pos = Helpers.getBlockCenter(worldPosition);
            for (int i = 0; i <= 1;i++){
                double a = Math.toRadians(i * 180 + time % 360);
                double x = 0.5*Math.sin(a);
                double z = 0.5*Math.cos(a);
                ClientHelpers.ParticleConstructor c = new ClientHelpers.ParticleConstructor(SolarcraftParticleTypes.SMALL_SOLAR_STRIKE_PARTICLE.get(),pos.x+x,pos.y+0.9,pos.z+z,0,0,0);
            }
        }
        if (remainingRecipeTime != -1){
            Vec3 vec3 = Helpers.randomVector();
            Vec3 pos = Helpers.getBlockCenter(worldPosition);
            ClientHelpers.ParticleConstructor c = new ClientHelpers.ParticleConstructor(SolarcraftParticleTypes.SPARK_PARTICLE.get(),pos.x,pos.y+0.9,pos.z,vec3.x*0.01,vec3.y*0.01,vec3.z*0.01)
                    .setColor(255,255,Math.round(40+level.random.nextFloat()*120));

        }
    }


    public int getRemainingRecipeTime() {
        return remainingRecipeTime;
    }

    public int getCurrentTime(){
        return recipeTime;
    }

    public boolean isRecipeInProgress(){
        return recipeTrigerred;
    }

    public void triggerRecipe(Player pl){

        if (level.getPlayerByUUID(owner) == pl){
            IItemHandler handler = getInventory();
            //level.getRecipeManager().getAllRecipesFor(SolarForge.INFUSING_CRAFTING_RECIPE_TYPE).stream().forEach(recipe -> {
            //    if (recipe.getId().getNamespace().matches(CraftTweakerConstants.MOD_ID)){
            //        CraftTweakerAPI.LOGGER.info(recipe.getOutput().getDisplayName().getString());
            //        CraftTweakerAPI.LOGGER.info(Arrays.toString(recipe.getPattern()));
            //        CraftTweakerAPI.LOGGER.info(recipe.getDefinitions());
            //        CraftTweakerAPI.LOGGER.info(recipe.getTime());
            //        CraftTweakerAPI.LOGGER.info(recipe.getFragmentID());
            //        if (!recipe.getFragmentID().isEmpty()){
            //            //CraftTweakerAPI.LOGGER.info(ProgressionHelper.doPlayerHasFragment(pl, AncientFragment.getFragmentByID(recipe.getFragmentID())));
            //        }
            //    }
            //});
            if (handler.getStackInSlot(9).is(Items.AIR)) {
                Optional<InfusingCraftingRecipe> recipe = level.getRecipeManager().getRecipeFor(SolarForge.INFUSING_CRAFTING_RECIPE_TYPE, phantomInv.set(handler), level);
                    if (recipe.isPresent()) {
                        try {
                            if (ProgressionHelper.doPlayerHasFragment(pl, recipe.get().getFragment())) {
                                Helpers.fireProgressionEvent(pl, Progression.INFUSING_CRAFTING_TABLE);
                                this.recipeTrigerred = true;
                                update();
                            } else {
                                pl.sendMessage(new TextComponent("Cant start craft, you don't have " + recipe.get().getFragment().getId().toUpperCase(Locale.ROOT) +
                                        " fragment unlocked.").withStyle(ChatFormatting.RED), pl.getUUID());
                            }
                        }catch (Exception e){
                            pl.sendMessage(new TextComponent("INCORRECT FRAGMENT IN RECIPE "+ recipe.get().getOutput().getDisplayName()+" TELL MOD AUTHOR TO FIX IT").withStyle(ChatFormatting.RED),
                                    pl.getUUID());
                        }
                    }else{
                        pl.sendMessage(new TextComponent("Recipe invalid.").withStyle(ChatFormatting.RED),
                                pl.getUUID());
                    }

            }
        }else{
            pl.sendMessage(new TextComponent("You are not the owner!").withStyle(ChatFormatting.RED),pl.getUUID());
        }
        }

    public int calculateMaximumRecipeOutput(InfusingCraftingRecipe recipe){
        int maxSize = recipe.getOutput().getMaxStackSize();
        if (maxSize != 1) {
            int outputcount = recipe.getOutputCount();
            int max = Math.floorDiv(maxSize,outputcount);
            IItemHandler g = getInventory();
            for (int i = 0;i < 9;i++){
                ItemStack stack = g.getStackInSlot(i);
                if (!stack.isEmpty()) {
                    int count = stack.getCount();
                    if (count < max) {
                        max = count;
                    }
                }
            }
            return max;
        }else{
            return 1;
        }
    }


    public IItemHandler getInventory(){
        IItemHandler handler = this.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElse(null);
        return handler;
    }
    @Override
    public UUID getOwner() {
        return owner;
    }

    @Override
    public void setOwner(UUID owner) {
        this.owner = owner;
    }


    @Nullable
    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return Helpers.createTilePacket(this,this.saveW(new CompoundTag()));
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        this.loadW(pkt.getTag());
    }

    @Override
    public void saveAdditional(CompoundTag tag) {
        tag.putBoolean("recipe",recipeTrigerred);
        tag.putInt("time",recipeTime);
        tag.putUUID("tileowner", owner);
        super.saveAdditional(tag);

    }

    @Override
    public void load(CompoundTag tag) {
        this.recipeTrigerred = tag.getBoolean("recipe");
        this.recipeTime = tag.getInt("time");
        if (tag.contains("tileowner")){
            this.setOwner(tag.getUUID("tileowner"));
        }

        super.load(tag);
    }
    public CompoundTag saveW(CompoundTag tag) {
        tag.putInt("remainingRecipeTime", remainingRecipeTime);
        tag.putBoolean("recipe",recipeTrigerred);
        tag.putInt("time",recipeTime);
        super.saveAdditional(tag);
        return tag;
    }

    public void loadW(CompoundTag tag) {
        this.remainingRecipeTime = tag.getInt("remainingRecipeTime");
        this.recipeTrigerred = tag.getBoolean("recipe");
        this.recipeTime = tag.getInt("time");
        super.load(tag);
    }


    public void update(){
        BlockState state = level.getBlockState(worldPosition);
        this.setChanged();
        level.sendBlockUpdated(worldPosition,state,state,3);
    }
}
