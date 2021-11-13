package com.finderfeed.solarforge.magic_items.blocks.blockentities;

import com.finderfeed.solarforge.SolarForge;
import com.finderfeed.solarforge.for_future_library.OwnedBlock;
import com.finderfeed.solarforge.misc_things.PhantomInventory;
import com.finderfeed.solarforge.recipe_types.infusing_crafting.InfusingCraftingRecipe;
import com.finderfeed.solarforge.registries.tile_entities.TileEntitiesRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;
import java.nio.charset.MalformedInputException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class InfusingTableTile extends BlockEntity implements OwnedBlock {

    private PhantomInventory phantomInv = new PhantomInventory(10);
    private UUID owner;
    private boolean recipeTrigerred = false;
    private int recipeTime = 0;
    public InfusingTableTile( BlockPos p_155229_, BlockState p_155230_) {
        super(TileEntitiesRegistry.INFUSING_CRAFTING_TABLE.get(), p_155229_, p_155230_);
    }


    public static void tick(Level world,BlockPos pos,BlockState state,InfusingTableTile tile){
        if (!world.isClientSide){
            if (tile.isRecipeInProgress()){
                IItemHandler handler = tile.getInventory();
                Optional<InfusingCraftingRecipe> optional = world.getRecipeManager().getRecipeFor(SolarForge.INFUSING_CRAFTING_RECIPE_TYPE, tile.phantomInv.set(handler), world);
                if (optional.isPresent()) {
                    InfusingCraftingRecipe recipe = optional.get();
                    int recipeTime = recipe.getTime();

                    if (tile.getCurrentTime() < recipeTime){
                        tile.recipeTime++;
                    }else{
                        ItemStack stack = recipe.getOutput().copy();
                        int maxOutput = tile.calculateMaximumRecipeOutput();
                        for (int i = 0; i < 9;i++){
                            handler.extractItem(i,maxOutput,false);
                        }
                        stack.setCount(maxOutput);
                        handler.insertItem(9,stack,false);
                        tile.recipeTrigerred = false;
                        tile.recipeTime = 0;
                    }
                }else{
                    tile.recipeTrigerred = false;
                }

            }else{
                tile.recipeTime = 0;
            }


            if (world.getGameTime() % 40 == 0){
                tile.update();
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
            if (handler.getStackInSlot(9).is(Items.AIR)) {
                Optional<InfusingCraftingRecipe> recipe = level.getRecipeManager().getRecipeFor(SolarForge.INFUSING_CRAFTING_RECIPE_TYPE, phantomInv.set(handler), level);
                if (recipe.isPresent()) {
                    this.recipeTrigerred = true;
                }
            }
        }else{
            pl.sendMessage(new TextComponent("You are not the owner!").withStyle(ChatFormatting.RED),pl.getUUID());
        }
    }

    private int calculateMaximumRecipeOutput(){
        int max = -1;
        IItemHandler inv = getInventory();
        for (int i = 0; i < 9;i++){
            int c = inv.getStackInSlot(i).getCount();
            if (c > max){
                max = c;
            }
        }
        return max;
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
        return new ClientboundBlockEntityDataPacket(worldPosition,3,this.saveW(new CompoundTag()));
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        this.loadW(pkt.getTag());
    }

    @Override
    public CompoundTag save(CompoundTag tag) {
        tag.putBoolean("recipe",recipeTrigerred);
        tag.putInt("time",recipeTime);
        if (owner != null) {
            tag.putUUID("owner", owner);
        }
        return super.save(tag);
    }

    @Override
    public void load(CompoundTag tag) {
        this.recipeTrigerred = tag.getBoolean("recipe");
        this.recipeTime = tag.getInt("time");
        this.owner = tag.getUUID("owner");
        super.load(tag);
    }
    public CompoundTag saveW(CompoundTag tag) {
        tag.putBoolean("recipe",recipeTrigerred);
        tag.putInt("time",recipeTime);
        return super.save(tag);
    }

    public void loadW(CompoundTag tag) {
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
