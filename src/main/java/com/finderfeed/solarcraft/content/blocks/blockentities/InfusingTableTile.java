package com.finderfeed.solarcraft.content.blocks.blockentities;

import com.finderfeed.solarcraft.content.items.solar_wand.IWandable;
import com.finderfeed.solarcraft.helpers.ClientHelpers;
import com.finderfeed.solarcraft.helpers.Helpers;
import com.finderfeed.solarcraft.local_library.OwnedBlock;
import com.finderfeed.solarcraft.content.items.solar_lexicon.progressions.Progression;
import com.finderfeed.solarcraft.content.items.solar_lexicon.unlockables.AncientFragmentHelper;
import com.finderfeed.solarcraft.client.particles.SCParticleTypes;
import com.finderfeed.solarcraft.misc_things.PhantomInventory;
import com.finderfeed.solarcraft.content.recipe_types.infusing_crafting.InfusingCraftingRecipe;
import com.finderfeed.solarcraft.registries.items.SCItems;
import com.finderfeed.solarcraft.registries.recipe_types.SolarcraftRecipeTypes;
import com.finderfeed.solarcraft.registries.tile_entities.SCTileEntities;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.capabilities.Capabilities;
import net.neoforged.neoforge.items.IItemHandler;
import javax.annotation.Nullable;
import java.util.*;

public class InfusingTableTile extends BlockEntity implements OwnedBlock, IWandable {
    public static final int ANIM_TIME = 100;
    public PhantomInventory phantomInv = new PhantomInventory(10);
    private UUID owner;
    private boolean recipeTrigerred = false;
    private int recipeTime = 0;


    public InfusingTableTile( BlockPos p_155229_, BlockState p_155230_) {
        super(SCTileEntities.INFUSING_CRAFTING_TABLE.get(), p_155229_, p_155230_);
    }


    public static void tick(Level world,BlockPos pos,BlockState state,InfusingTableTile tile){
        if (!world.isClientSide){

            if (tile.isRecipeInProgress()){
                IItemHandler handler = tile.getInventory();
                Optional<RecipeHolder<InfusingCraftingRecipe>> optional = world.getRecipeManager().getRecipeFor(SolarcraftRecipeTypes.INFUSING_CRAFTING.get(), tile.phantomInv.set(handler), world);
                if (optional.isPresent()) {
                    InfusingCraftingRecipe recipe = optional.get().value();
                    int recipeTime = recipe.getTime();
                    int maxOutput = tile.calculateMaximumRecipeOutput(recipe);
                    if (tile.getCurrentTime() < recipeTime*maxOutput){
//                        if (recipeTime - tile.getCurrentTime() <= ANIM_TIME){
//                            updateTime = 1;
//                            tile.remainingRecipeTime = ANIM_TIME - (recipeTime - tile.getCurrentTime());
//                        }
                        tile.recipeTime++;
                    }else{
                        ItemStack stack = recipe.getOutput().copy();
                        if (stack.is(SCItems.SOLAR_FORGE_ITEM.get()) || stack.is(SCItems.INFUSER_ITEM.get())) {
                            if (tile.getOwner() != null) {
                                Player player = world.getPlayerByUUID(tile.getOwner());
                                if (player != null) {
                                    if (stack.is(SCItems.SOLAR_FORGE_ITEM.get())) {
                                        Helpers.fireProgressionEvent(player, Progression.CRAFT_SOLAR_FORGE);
                                    }else if (stack.is(SCItems.INFUSER_ITEM.get())){
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
                        tile.update();
                    }
                }else{
                    tile.recipeTrigerred = false;
                }

            }else{
                tile.recipeTime = 0;
            }


            tile.update();
        }


        if (world.isClientSide){
            tile.clientTick();
        }

    }


    public void clientTick(){
        if (isRecipeInProgress()) {
            IItemHandler handler = this.getInventory();
            Optional<RecipeHolder<InfusingCraftingRecipe>> optional = level.getRecipeManager().getRecipeFor(SolarcraftRecipeTypes.INFUSING_CRAFTING.get(), this.phantomInv.set(handler), level);
            if (optional.isPresent()) {
                InfusingCraftingRecipe recipe = optional.get().value();
                int recipeTime = recipe.getTime();
                int maxOutput = this.calculateMaximumRecipeOutput(recipe);

                int remainingRecipeTime = recipeTime*maxOutput - this.recipeTime;

                if (level.getGameTime() % 2 == 0) {
                    float time = -level.getGameTime() * 4;
                    Vec3 pos = Helpers.getBlockCenter(worldPosition);
                    for (int i = 0; i <= 1; i++) {
                        double a = Math.toRadians(i * 180 + time % 360);
                        double x = 0.5 * Math.sin(a);
                        double z = 0.5 * Math.cos(a);
                        ClientHelpers.ParticleConstructor c = new ClientHelpers.ParticleConstructor(SCParticleTypes.SMALL_SOLAR_STRIKE_PARTICLE.get(), pos.x + x, pos.y + 0.9, pos.z + z, 0, 0, 0);
                    }
                }
                int tm = Math.min(recipeTime*maxOutput,InfusingTableTile.ANIM_TIME);
                if (remainingRecipeTime < tm) {
                    Vec3 vec3 = Helpers.randomVector();
                    Vec3 pos = Helpers.getBlockCenter(worldPosition);
                    ClientHelpers.ParticleConstructor c = new ClientHelpers.ParticleConstructor(SCParticleTypes.SPARK_PARTICLE.get(), pos.x, pos.y + 0.9, pos.z, vec3.x * 0.01, vec3.y * 0.01, vec3.z * 0.01)
                            .setColor(255, 255, Math.round(40 + level.random.nextFloat() * 120));
                }
            }
        }
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
            //level.getRecipeManager().getAllRecipesFor(SolarcraftRecipeTypes.INFUSING_CRAFTING.get()).stream().forEach(recipe -> {
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
                Optional<RecipeHolder<InfusingCraftingRecipe>> recipe = level.getRecipeManager().getRecipeFor(SolarcraftRecipeTypes.INFUSING_CRAFTING.get(), phantomInv.set(handler), level);
                    if (recipe.isPresent()) {
                        try {
                            if (AncientFragmentHelper.doPlayerHasFragment(pl, recipe.get().value().getFragment())) {
                                Helpers.fireProgressionEvent(pl, Progression.INFUSING_CRAFTING_TABLE);
                                this.recipeTrigerred = true;
                                update();
                            } else {
                                pl.sendSystemMessage(Component.literal("Cant start craft, you don't have " + recipe.get().value().getFragment().getId().toUpperCase(Locale.ROOT) +
                                        " fragment unlocked.").withStyle(ChatFormatting.RED));
                            }
                        }catch (Exception e){
                            pl.sendSystemMessage(Component.literal("INCORRECT FRAGMENT IN RECIPE "+ recipe.get().value().getOutput().getDisplayName()+" TELL MOD AUTHOR TO FIX IT").withStyle(ChatFormatting.RED));
                        }
                    }else{
                        pl.sendSystemMessage(Component.literal("Recipe invalid.").withStyle(ChatFormatting.RED));
                    }

            }
        }else{
            pl.sendSystemMessage(Component.literal("You are not the owner!").withStyle(ChatFormatting.RED));
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
        IItemHandler handler = this.getCapability(Capabilities.ITEM_HANDLER).orElse(null);
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

        CompoundTag tag = new CompoundTag();
        saveAdditional(tag);

        return Helpers.createTilePacket(this,tag);
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        this.load(pkt.getTag());
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
//    public CompoundTag saveW(CompoundTag tag) {
//        tag.putInt("remainingRecipeTime", remainingRecipeTime);
//        tag.putBoolean("recipe",recipeTrigerred);
//        tag.putInt("time",recipeTime);
//        super.saveAdditional(tag);
//        return tag;
//    }

//    public void loadW(CompoundTag tag) {
//        this.remainingRecipeTime = tag.getInt("remainingRecipeTime");
//        this.recipeTrigerred = tag.getBoolean("recipe");
//        this.recipeTime = tag.getInt("time");
//        super.load(tag);
//    }


    public void update(){
        BlockState state = level.getBlockState(worldPosition);
        this.setChanged();
        level.sendBlockUpdated(worldPosition,state,state,3);
    }

    @Override
    public void onWandUse(BlockPos usePos, Player user) {
        triggerRecipe(user);
    }
}
