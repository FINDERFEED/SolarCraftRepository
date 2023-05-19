package com.finderfeed.solarcraft.content.blocks.blockentities;

import com.finderfeed.solarcraft.content.items.SunShardItem;
import com.finderfeed.solarcraft.helpers.Helpers;
import com.finderfeed.solarcraft.client.particles.SolarcraftParticleTypes;
import com.finderfeed.solarcraft.misc_things.PhantomInventory;
import com.finderfeed.solarcraft.content.recipe_types.solar_smelting.SolarSmeltingRecipe;
import com.finderfeed.solarcraft.packet_handler.SCPacketHandler;
import com.finderfeed.solarcraft.packet_handler.packets.UpdateItemTagInItemEntityPacket;
import com.finderfeed.solarcraft.registries.items.SolarcraftItems;
import com.finderfeed.solarcraft.registries.recipe_types.SolarcraftRecipeTypes;
import com.finderfeed.solarcraft.registries.tile_entities.SolarcraftTileEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;

import net.minecraft.world.level.block.entity.BlockEntity;

import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.PacketDistributor;
import org.jetbrains.annotations.Nullable;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SolarLensTile extends BlockEntity  {

    public PhantomInventory INVENTORY = new PhantomInventory(6);
    public int SMELTING_TIME = 0;
    public int CURRENT_SMELTING_TIME = 0;
    public boolean RECIPE_IN_PROGRESS = false;

    public SolarLensTile(BlockPos p_155229_, BlockState p_155230_) {
        super(SolarcraftTileEntityTypes.SOLAR_LENS_TILE.get(), p_155229_, p_155230_);
    }



    public static void tick(Level world, BlockPos post, BlockState blockState, SolarLensTile tile) {
        if (!tile.level.isClientSide){

            AABB box = new AABB(-1,-4,-1,1,0,1);
            if (tile.level.canSeeSky(tile.worldPosition.above()) && tile.getLevel().getDayTime() % 24000 <= 13000) {

                List<ItemEntity> list = tile.level.getEntitiesOfClass(ItemEntity.class, box.move(tile.worldPosition));
                ItemEntity shard = null;
                for (int i = 0; i < 6; i++) {
                    if (i > list.size() - 1) {
                        tile.INVENTORY.setItem(i, ItemStack.EMPTY);
                    } else {
                        ItemEntity entity = list.get(i);
                        ItemStack stack = entity.getItem();
                        if (stack.getItem() == SolarcraftItems.SUN_SHARD.get()){
                            shard = entity;
                            break;
                        }
                        tile.INVENTORY.setItem(i, list.get(i).getItem());

                    }
                }
                if (shard == null) {
                    processRecipe(world, post, blockState, tile, list);
                }else{
                    processShard(tile,shard);
                }
            }
        }else{
            if (tile.RECIPE_IN_PROGRESS){
                if (world.getGameTime() % 3 == 0 && Helpers.isDay(world)) {
                    Vec3 v = Helpers.getBlockCenter(post.offset(0, -2, 0));
                    Vec3 offs = Helpers.randomVector().normalize().multiply(0.5, 0.5, 0.5);
                    world.addParticle(SolarcraftParticleTypes.SMALL_SOLAR_STRIKE_PARTICLE.get(), v.x + offs.x, v.y + offs.y, v.z + offs.z, 0, 0.05, 0);
                }
            }
        }
    }

    public static void processShard(SolarLensTile lens,ItemEntity shard){
        ItemStack stack = shard.getItem();
        SunShardItem item = (SunShardItem)stack.getItem();
        if (!item.isHeated(stack)){
            Vec3 v = Helpers.getBlockCenter(lens.getBlockPos().offset(0, -2, 0));
            if (lens.level instanceof ServerLevel world){
                world.sendParticles(SolarcraftParticleTypes.SMALL_SOLAR_STRIKE_PARTICLE.get(),v.x,v.y,v.z,1,0.05,0.05,0.05,0.05);
            }
            int time = item.getHeatedTime(stack);
            if (time >= SunShardItem.MAX_HEATED_TIME){
                item.setHeated(stack,true);
                SCPacketHandler.INSTANCE.send(PacketDistributor.NEAR.with(()->
                        new PacketDistributor.TargetPoint(
                                null,
                                lens.getBlockPos().getX(),
                                lens.getBlockPos().getY(),
                                lens.getBlockPos().getZ(),
                                50,
                                lens.getLevel().dimension()
                        )),new UpdateItemTagInItemEntityPacket(shard));
            }else{
                item.setHeatedTime(stack,time + 1);
            }
        }
    }


    public static void processRecipe(Level world, BlockPos post, BlockState blockState, SolarLensTile tile,List<ItemEntity> list){
        Optional<SolarSmeltingRecipe> recipe = tile.level.getRecipeManager().getRecipeFor(SolarcraftRecipeTypes.SMELTING.get(),tile.INVENTORY,tile.level);
        if (recipe.isPresent() ){
            SolarSmeltingRecipe actualRecipe = recipe.get();
            tile.RECIPE_IN_PROGRESS = true;
            tile.SMELTING_TIME = recipe.get().smeltingTime;
            tile.CURRENT_SMELTING_TIME++;
            if (tile.CURRENT_SMELTING_TIME % 5 == 0){
                tile.setChanged();
                world.sendBlockUpdated(post,blockState,blockState,3);
            }
            int count = tile.getMinRecipeOutput(actualRecipe);
            if (tile.CURRENT_SMELTING_TIME >= tile.SMELTING_TIME*count){

                for (ItemEntity a : list){
                    for (ItemStack item : actualRecipe.getStacks()){
                        if (a.getItem().getItem() == item.getItem()){
                            a.getItem().shrink(item.getCount() * count);
                        }
                    }

                }
                Vec3 pos = new Vec3(tile.worldPosition.getX()+0.5d,tile.worldPosition.getY()-1,tile.worldPosition.getZ()+0.5d);
                ItemEntity entity = new ItemEntity(tile.level,pos.x,pos.y,pos.z,new ItemStack(recipe.get().output.getItem(),count));
                tile.level.addFreshEntity(entity);
                tile.SMELTING_TIME = 0;
                tile.CURRENT_SMELTING_TIME = 0;
                tile.RECIPE_IN_PROGRESS = false;
                tile.setChanged();
                world.sendBlockUpdated(post,blockState,blockState,3);
            }
        }else{
            tile.RECIPE_IN_PROGRESS = false;
            tile.CURRENT_SMELTING_TIME = 0;
            tile.SMELTING_TIME = 0;
        }
    }

    private int getMinRecipeOutput(SolarSmeltingRecipe recipe){
        List<ItemStack> recipeItems = new ArrayList<>(recipe.getStacks());
        ItemStack output = recipe.getResultItem();
        int outputSize = output.getMaxStackSize();
        if (outputSize == 1){
            return  1;
        }
        int minRecipesAmount = Integer.MAX_VALUE;

        for (ItemStack item : INVENTORY){
            for (ItemStack recipeItem : recipeItems){
                if (recipeItem.getItem() == item.getItem()){
                    int invItemCount = item.getCount();
                    int recipeItemCount = recipeItem.getCount();

                    int minOutput = invItemCount / recipeItemCount;
                    if (minOutput < minRecipesAmount){
                        minRecipesAmount = minOutput;
                    }
                    break;
                }
            }
        }


        return Math.min(outputSize, minRecipesAmount);
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        CompoundTag tag = new CompoundTag();
        saveAdditional(tag);
        return Helpers.createTilePacket(this,tag);
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        this.load(pkt.getTag());
        super.onDataPacket(net, pkt);
    }

    @Override
    public void saveAdditional(CompoundTag cmp) {
            cmp.putInt("smelting_time",SMELTING_TIME);
            cmp.putInt("smelting_time_current",CURRENT_SMELTING_TIME);
            cmp.putBoolean("recipe_in_progress",RECIPE_IN_PROGRESS);
            super.saveAdditional(cmp);
    }

    @Override
    public void load( CompoundTag cmp) {
        SMELTING_TIME = cmp.getInt("smelting_time");
        CURRENT_SMELTING_TIME = cmp.getInt("smelting_time_current");
        RECIPE_IN_PROGRESS = cmp.getBoolean("recipe_in_progress");
        super.load( cmp);
    }
}
