package com.finderfeed.solarforge.content.entities.not_alive;

import com.finderfeed.solarforge.registries.entities.SolarcraftEntityTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;


public class LegendaryItem extends Entity {

    private static final EntityDataAccessor<ItemStack> DATA_ITEM = SynchedEntityData.defineId(LegendaryItem.class, EntityDataSerializers.ITEM_STACK);



    public LegendaryItem(EntityType<? extends Entity> p_31991_, Level p_31992_) {
        super(p_31991_, p_31992_);

    }

    public LegendaryItem(Level world,ItemStack stack){
        super(SolarcraftEntityTypes.LEGENDARY_ITEM.get(),world);
        this.setItem(stack);
    }




    @Override
    public void tick() {
        super.tick();
        if (this.getItem().isEmpty()){
            this.discard();
        }
    }

    @Override
    public boolean isNoGravity() {
        return true;
    }

    public ItemStack getItem() {
        return this.getEntityData().get(DATA_ITEM);
    }

    public void setItem(ItemStack stack){
        this.entityData.set(DATA_ITEM,stack);
    }

    @Override
    protected void defineSynchedData() {
        this.getEntityData().define(DATA_ITEM, ItemStack.EMPTY);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {
        CompoundTag compoundtag = tag.getCompound("Item");
        this.setItem(ItemStack.of(compoundtag));
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {
        if (!this.getItem().isEmpty()) {
            tag.put("Item", this.getItem().save(new CompoundTag()));
        }
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public void playerTouch(Player player) {
        if (!this.level.isClientSide) {
            ItemStack itemstack = this.getItem();
            Item item = itemstack.getItem();
            int i = itemstack.getCount();
            ItemStack copy = itemstack.copy();
            if (player.getInventory().add(itemstack)) {
                copy.setCount(copy.getCount() - getItem().getCount());
                player.take(this, i);
                level.playSound(null,this.position().x,this.position().y,this.position().z, SoundEvents.ITEM_PICKUP, SoundSource.PLAYERS,1,1);
                if (itemstack.isEmpty()) {
                    this.discard();
                    itemstack.setCount(i);
                }

                player.awardStat(Stats.ITEM_PICKED_UP.get(item), i);
            }

        }

    }

    @Override
    public boolean isAttackable() {
        return false;
    }

    @Override
    public void checkDespawn() {
        super.checkDespawn();
    }
}
