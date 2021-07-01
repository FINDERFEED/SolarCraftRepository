package com.finderfeed.solarforge.magic_items.items.projectiles;

import com.finderfeed.solarforge.SolarAbilities.SolarStrikeEntity;
import com.finderfeed.solarforge.misc_things.ParticlesList;
import com.finderfeed.solarforge.registries.items.ItemsRegister;
import com.finderfeed.solarforge.registries.projectiles.Projectiles;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.DamagingProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.IParticleData;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.network.NetworkHooks;

import java.util.List;
import java.util.UUID;

public class BlockBoomerangProjectile extends DamagingProjectileEntity {
    public static DataParameter<Integer> BLOCK_ID = EntityDataManager.defineId(BlockBoomerangProjectile.class, DataSerializers.INT);

    public int livingTicks = 0;
    public Block blockToPlace = null;
    public boolean isReturning = false;
    public UUID owner;
    public BlockBoomerangProjectile(EntityType<? extends DamagingProjectileEntity> p_i50173_1_, World p_i50173_2_) {
        super(p_i50173_1_, p_i50173_2_);

    }

    public BlockBoomerangProjectile(double p_i50174_2_, double p_i50174_4_, double p_i50174_6_, double p_i50174_8_, double p_i50174_10_, double p_i50174_12_, World p_i50174_14_) {
        super(Projectiles.BLOCK_BOOMERANG.get(), p_i50174_2_, p_i50174_4_, p_i50174_6_, p_i50174_8_, p_i50174_10_, p_i50174_12_, p_i50174_14_);

    }

    public BlockBoomerangProjectile(LivingEntity p_i50175_2_, World p_i50175_9_) {
        super(Projectiles.BLOCK_BOOMERANG.get(),  p_i50175_9_);

    }

    public void setOwner(UUID owner) {
        this.owner = owner;
    }

    @Override
    protected void onHitEntity(EntityRayTraceResult ctx) {
        Entity ent = ctx.getEntity();
        if (ent instanceof PlayerEntity){
            PlayerEntity player = (PlayerEntity)ent;
            if (player.getUUID().equals(owner)){
                if (player.inventory.getFreeSlot() != -1) {
                    player.inventory.add(ItemsRegister.BLOCK_BOOMERANG.get().getDefaultInstance());
                    if (blockToPlace != null){
                        if (player.inventory.getFreeSlot() != -1) {
                            player.inventory.add(blockToPlace.asItem().getDefaultInstance());
                        }else{
                            ItemEntity item = new ItemEntity(level, this.getX(), this.getY(), this.getZ(), blockToPlace.asItem().getDefaultInstance());
                            level.addFreshEntity(item);
                        }
                    }
                    this.remove();
                }else{
                    killAndDropItem();
                }
            }else {
                killAndDropItem();
            }
        }
        super.onHitEntity(ctx);
    }

    @Override
    protected void onHitBlock(BlockRayTraceResult result) {
        BlockPos pos = this.getOnPos();
        if (this.blockToPlace != null && !isReturning){
            isReturning = true;
            this.noPhysics = true;

            if (level.getBlockState(pos).getBlock() == Blocks.AIR){
                level.setBlock(pos,blockToPlace.defaultBlockState(), Constants.BlockFlags.DEFAULT);
            }else{
                level.setBlock(pos.above(),blockToPlace.defaultBlockState(), Constants.BlockFlags.DEFAULT);
            }


            blockToPlace = null;
        }
    }


    public void setBlockToPlace(Block blockToPlace) {
        this.blockToPlace = blockToPlace;
    }

    @Override
    protected float getInertia() {
        return 1F;
    }


    @Override
    public void tick(){

        super.tick();
        if (!this.level.isClientSide){
            if (blockToPlace != null) {
                this.entityData.set(BLOCK_ID, Block.getId(blockToPlace.defaultBlockState()));
            }else{
                this.entityData.set(BLOCK_ID, -1000);
            }
            livingTicks++;
            if (livingTicks >= 600){
                isReturning = true;
            }
        }
        if (!this.level.isClientSide && this.isReturning){
            PlayerEntity player = level.getPlayerByUUID(owner);
            if (player != null){
                Vector3d currentVelocity = getDeltaMovement();
                this.setDeltaMovement(-currentVelocity.x,-currentVelocity.y,-currentVelocity.z);

                Vector3d playerPos = player.position().add(0,1.5,0);
                Vector3d newVelocity = new Vector3d(-this.getX() + playerPos.x,-this.getY() + playerPos.y,-this.getZ() + playerPos.z).normalize();

                this.setDeltaMovement(newVelocity);
            }else{
                killAndDropItem();
            }
        }

    }

    @Override
    protected boolean shouldBurn() {
        return false;
    }
    @Override
    public boolean hurt(DamageSource p_70097_1_, float p_70097_2_) {
        return false;
    }
    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    protected IParticleData getTrailParticle() {
        return ParticlesList.INVISIBLE_PARTICLE.get();
    }


    @Override
    public void load(CompoundNBT cmp) {
        owner = cmp.getUUID("owner_uuids");
        tickCount = cmp.getInt("get_tick_count");
        isReturning = cmp.getBoolean("returning");
        if (cmp.getInt("block_id") != -1000) {
            blockToPlace = Block.stateById(cmp.getInt("block_id")).getBlock();
        }else {
            blockToPlace = null;
        }
        super.load(cmp);
    }

    @Override
    public boolean shouldRender(double p_145770_1_, double p_145770_3_, double p_145770_5_) {
        return true;
    }

    @Override
    public boolean save(CompoundNBT cmp) {
        cmp.putUUID("owner_uuids",owner);
        cmp.putInt("get_tick_count",tickCount);
        if (blockToPlace != null) {
            cmp.putInt("block_id", Block.getId(blockToPlace.defaultBlockState()));
        }else{
            cmp.putInt("block_id", -1000);
        }
        cmp.putBoolean("returning",isReturning);
        return super.save(cmp);
    }

    public void killAndDropItem(){
        if (blockToPlace != null) {
            ItemEntity item = new ItemEntity(level, this.getX(), this.getY(), this.getZ(), blockToPlace.asItem().getDefaultInstance());
            level.addFreshEntity(item);
        }
        ItemEntity boomerang = new ItemEntity(level,this.getX(),this.getY(),this.getZ(),ItemsRegister.BLOCK_BOOMERANG.get().getDefaultInstance());
        level.addFreshEntity(boomerang);
        this.remove();
    }



    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();

        this.entityData.define(BLOCK_ID,1);

    }
}
