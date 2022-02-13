package com.finderfeed.solarforge.entities;

import com.finderfeed.solarforge.registries.entities.EntityTypes;
import net.minecraft.CrashReportCategory;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.protocol.game.ClientboundBlockUpdatePacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.DirectionalPlaceContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.function.Predicate;

public class MyFallingBlockEntity extends Entity {
    public BlockState blockState = Blocks.SAND.defaultBlockState();
    public int time;
    public boolean dropItem = true;
    public boolean cancelDrop;
    private boolean hurtEntities;
    private int fallDamageMax = 20;
    private float fallDamagePerDistance;
    public long removeAtMillis;
    @Nullable
    public CompoundTag blockData;
    protected static final EntityDataAccessor<BlockPos> DATA_START_POS = SynchedEntityData.defineId(MyFallingBlockEntity.class, EntityDataSerializers.BLOCK_POS);

    public MyFallingBlockEntity(EntityType<? extends Entity> p_31950_, Level p_31951_) {
        super(p_31950_, p_31951_);
    }

    public MyFallingBlockEntity(Level world, double x, double y, double z, BlockState p_31957_) {
        this(EntityTypes.FALLING_BLOCK.get(), world);
        this.blockState = p_31957_;
        this.blocksBuilding = true;
        this.setPos(x, y + (double)((1.0F - this.getBbHeight()) / 2.0F), z);
        this.setDeltaMovement(Vec3.ZERO);
        this.xo = x;
        this.yo = y;
        this.zo = z;
        this.setStartPos(this.blockPosition());
        time = 1;
    }

    public boolean isAttackable() {
        return false;
    }

    public void setStartPos(BlockPos pos) {
        this.entityData.set(DATA_START_POS, pos);
    }

    public BlockPos getStartPos() {
        return this.entityData.get(DATA_START_POS);
    }

    protected Entity.MovementEmission getMovementEmission() {
        return Entity.MovementEmission.NONE;
    }

    protected void defineSynchedData() {
        this.entityData.define(DATA_START_POS, BlockPos.ZERO);
    }

    public boolean isPickable() {
        return !this.isRemoved();
    }

    public void tick() {
        if (this.blockState.isAir()) {
            this.discard();
        } else if (this.level.isClientSide && this.removeAtMillis > 0L) {
            if (System.currentTimeMillis() >= this.removeAtMillis) {
                super.setRemoved(Entity.RemovalReason.DISCARDED);
            }

        } else {
            Block block = this.blockState.getBlock();
            if (this.time++ == 0) {
                BlockPos blockpos = this.blockPosition();
                if (this.level.getBlockState(blockpos).is(block)) {
                    this.level.removeBlock(blockpos, false);
                } else if (!this.level.isClientSide) {
                    this.discard();
                    return;
                }
            }

            if (!this.isNoGravity()) {
                this.setDeltaMovement(this.getDeltaMovement().add(0.0D, -0.04D, 0.0D));
            }

            this.move(MoverType.SELF, this.getDeltaMovement());
            if (!this.level.isClientSide) {
                BlockPos blockpos1 = this.blockPosition();
                boolean flag = this.blockState.getBlock() instanceof ConcretePowderBlock;
                boolean flag1 = flag && this.level.getFluidState(blockpos1).is(FluidTags.WATER);
                double d0 = this.getDeltaMovement().lengthSqr();
                if (flag && d0 > 1.0D) {
                    BlockHitResult blockhitresult = this.level.clip(new ClipContext(new Vec3(this.xo, this.yo, this.zo), this.position(), ClipContext.Block.COLLIDER, ClipContext.Fluid.SOURCE_ONLY, this));
                    if (blockhitresult.getType() != HitResult.Type.MISS && this.level.getFluidState(blockhitresult.getBlockPos()).is(FluidTags.WATER)) {
                        blockpos1 = blockhitresult.getBlockPos();
                        flag1 = true;
                    }
                }

                if (!this.onGround && !flag1) {
                    if (!this.level.isClientSide && (this.time > 100 && (blockpos1.getY() <= this.level.getMinBuildHeight() || blockpos1.getY() > this.level.getMaxBuildHeight()) || this.time > 600)) {
                        if (this.dropItem && this.level.getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
                            this.spawnAtLocation(block);
                        }

                        this.discard();
                    }
                } else {
                    BlockState blockstate = this.level.getBlockState(blockpos1);
                    this.setDeltaMovement(this.getDeltaMovement().multiply(0.7D, -0.5D, 0.7D));
                    if (!blockstate.is(Blocks.MOVING_PISTON)) {
                        if (!this.cancelDrop) {
                            boolean flag2 = blockstate.canBeReplaced(new DirectionalPlaceContext(this.level, blockpos1, Direction.DOWN, ItemStack.EMPTY, Direction.UP));
                            boolean flag3 = FallingBlock.isFree(this.level.getBlockState(blockpos1.below())) && (!flag || !flag1);
                            boolean flag4 = this.blockState.canSurvive(this.level, blockpos1) && !flag3;
                            if (flag2 && flag4) {
                                if (this.blockState.hasProperty(BlockStateProperties.WATERLOGGED) && this.level.getFluidState(blockpos1).getType() == Fluids.WATER) {
                                    this.blockState = this.blockState.setValue(BlockStateProperties.WATERLOGGED, Boolean.valueOf(true));
                                }

                                if (this.level.setBlock(blockpos1, this.blockState, 3)) {
                                    ((ServerLevel)this.level).getChunkSource().chunkMap.broadcast(this, new ClientboundBlockUpdatePacket(blockpos1, this.level.getBlockState(blockpos1)));
                                    this.discard();
                                    if (this.blockData != null && this.blockState.hasBlockEntity()) {
                                        BlockEntity blockentity = this.level.getBlockEntity(blockpos1);
                                        if (blockentity != null) {
                                            CompoundTag compoundtag = blockentity.saveWithoutMetadata();

                                            for(String s : this.blockData.getAllKeys()) {
                                                compoundtag.put(s, this.blockData.get(s).copy());
                                            }

                                            try {
                                                blockentity.load(compoundtag);
                                            } catch (Exception exception) {
                                                LOGGER.error("Failed to load block entity from falling block (SolarCraft custom falling block)", (Throwable)exception);
                                            }

                                            blockentity.setChanged();
                                        }
                                    }
                                } else if (this.dropItem && this.level.getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
                                    this.discard();
                                    this.callOnBrokenAfterFall(block, blockpos1);
                                    this.spawnAtLocation(block);
                                }
                            } else {
                                this.discard();
                                if (this.dropItem && this.level.getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
                                    this.callOnBrokenAfterFall(block, blockpos1);
                                    this.spawnAtLocation(block);
                                }
                            }
                        } else {
                            this.discard();
                            this.callOnBrokenAfterFall(block, blockpos1);
                        }
                    }
                }
            }

            this.setDeltaMovement(this.getDeltaMovement().scale(0.98D));
        }
    }

    public void setRemoved(Entity.RemovalReason reason) {
        if (this.level.shouldDelayFallingBlockEntityRemoval(reason)) {
            this.removeAtMillis = System.currentTimeMillis() + 50L;
        } else {
            super.setRemoved(reason);
        }
    }

    public void callOnBrokenAfterFall(Block block, BlockPos pos) {

    }

    public boolean causeFallDamage(float damage, float multiplier, DamageSource source) {
        if (!this.hurtEntities) {
            return false;
        } else {
            int i = Mth.ceil(damage - 1.0F);
            if (i < 0) {
                return false;
            } else {
                Predicate<Entity> predicate;
                DamageSource damagesource;
                if (this.blockState.getBlock() instanceof Fallable) {
                    Fallable fallable = (Fallable)this.blockState.getBlock();
                    predicate = fallable.getHurtsEntitySelector();
                    damagesource = fallable.getFallDamageSource();
                } else {
                    predicate = EntitySelector.NO_SPECTATORS;
                    damagesource = DamageSource.FALLING_BLOCK;
                }

                float f = (float)Math.min(Mth.floor((float)i * this.fallDamagePerDistance), this.fallDamageMax);
                this.level.getEntities(this, this.getBoundingBox(), predicate).forEach((p_149649_) -> {
                    p_149649_.hurt(damagesource, f);
                });
                boolean flag = this.blockState.is(BlockTags.ANVIL);
                if (flag && f > 0.0F && this.random.nextFloat() < 0.05F + (float)i * 0.05F) {
                    BlockState blockstate = AnvilBlock.damage(this.blockState);
                    if (blockstate == null) {
                        this.cancelDrop = true;
                    } else {
                        this.blockState = blockstate;
                    }
                }

                return false;
            }
        }
    }

    protected void addAdditionalSaveData(CompoundTag p_31973_) {
        p_31973_.put("BlockState", NbtUtils.writeBlockState(this.blockState));
        p_31973_.putInt("Time", this.time);
        p_31973_.putBoolean("DropItem", this.dropItem);
        p_31973_.putBoolean("HurtEntities", this.hurtEntities);
        p_31973_.putFloat("FallHurtAmount", this.fallDamagePerDistance);
        p_31973_.putInt("FallHurtMax", this.fallDamageMax);
        if (this.blockData != null) {
            p_31973_.put("TileEntityData", this.blockData);
        }

    }

    protected void readAdditionalSaveData(CompoundTag p_31964_) {
        this.blockState = NbtUtils.readBlockState(p_31964_.getCompound("BlockState"));
        this.time = p_31964_.getInt("Time");
        if (p_31964_.contains("HurtEntities", 99)) {
            this.hurtEntities = p_31964_.getBoolean("HurtEntities");
            this.fallDamagePerDistance = p_31964_.getFloat("FallHurtAmount");
            this.fallDamageMax = p_31964_.getInt("FallHurtMax");
        } else if (this.blockState.is(BlockTags.ANVIL)) {
            this.hurtEntities = true;
        }

        if (p_31964_.contains("DropItem", 99)) {
            this.dropItem = p_31964_.getBoolean("DropItem");
        }

        if (p_31964_.contains("TileEntityData", 10)) {
            this.blockData = p_31964_.getCompound("TileEntityData");
        }

        if (this.blockState.isAir()) {
            this.blockState = Blocks.SAND.defaultBlockState();
        }

    }

    public void setHurtsEntities(float damagePerDistance, int max) {
        this.hurtEntities = true;
        this.fallDamagePerDistance = damagePerDistance;
        this.fallDamageMax = max;
    }

    public boolean displayFireAnimation() {
        return false;
    }

    public void fillCrashReportCategory(CrashReportCategory cat) {
        super.fillCrashReportCategory(cat);
        cat.setDetail("Immitating BlockState (SolarCraft custom falling block)", this.blockState.toString());
    }

    public BlockState getBlockState() {
        return this.blockState;
    }

    public boolean onlyOpCanSetNbt() {
        return true;
    }

    public Packet<?> getAddEntityPacket() {
        return new ClientboundAddEntityPacket(this, Block.getId(this.getBlockState()));
    }

    public void recreateFromPacket(ClientboundAddEntityPacket packet) {
        super.recreateFromPacket(packet);
        this.blockState = Block.stateById(packet.getData());
        this.blocksBuilding = true;
        double d0 = packet.getX();
        double d1 = packet.getY();
        double d2 = packet.getZ();
        this.setPos(d0, d1 + (double)((1.0F - this.getBbHeight()) / 2.0F), d2);
        this.setStartPos(this.blockPosition());
    }
    @Override
    public boolean ignoreExplosion() {
        return true;
    }
}
