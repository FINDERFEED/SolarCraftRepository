package com.finderfeed.solarcraft.content.entities.not_alive;

import com.finderfeed.solarcraft.SolarCraft;
import com.finderfeed.solarcraft.registries.entities.SCEntityTypes;
import net.minecraft.CrashReportCategory;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.DirectionalPlaceContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Explosion;
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
    private int fallDamageMax = 40;
    private float fallDamagePerDistance;

    @Nullable
    public CompoundTag blockData;
    protected static final EntityDataAccessor<BlockPos> DATA_START_POS = SynchedEntityData.defineId(MyFallingBlockEntity.class, EntityDataSerializers.BLOCK_POS);

    public MyFallingBlockEntity(EntityType<? extends Entity> p_31950_, Level p_31951_) {
        super(p_31950_, p_31951_);
    }

    public MyFallingBlockEntity(Level world, double x, double y, double z, BlockState p_31957_) {
        this(SCEntityTypes.FALLING_BLOCK.get(), world);
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
        } else {
            Block block = this.blockState.getBlock();
            ++this.time;
            if (!this.isNoGravity()) {
                this.setDeltaMovement(this.getDeltaMovement().add(0.0D, -0.04D, 0.0D));
            }

            this.move(MoverType.SELF, this.getDeltaMovement());
            if (!this.level.isClientSide) {
                BlockPos blockpos = this.blockPosition();
                boolean flag = this.blockState.getBlock() instanceof ConcretePowderBlock;
                boolean flag1 = flag && this.level.getFluidState(blockpos).is(FluidTags.WATER);
                double d0 = this.getDeltaMovement().lengthSqr();
                if (flag && d0 > 1.0D) {
                    BlockHitResult blockhitresult = this.level.clip(new ClipContext(new Vec3(this.xo, this.yo, this.zo), this.position(), ClipContext.Block.COLLIDER, ClipContext.Fluid.SOURCE_ONLY, this));
                    if (blockhitresult.getType() != HitResult.Type.MISS && this.level.getFluidState(blockhitresult.getBlockPos()).is(FluidTags.WATER)) {
                        blockpos = blockhitresult.getBlockPos();
                        flag1 = true;
                    }
                }

                if (!this.onGround() && !flag1) {
                    if (!this.level.isClientSide && (this.time > 100 && (blockpos.getY() <= this.level.getMinBuildHeight() || blockpos.getY() > this.level.getMaxBuildHeight()) || this.time > 600)) {
                        if (this.dropItem && this.level.getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
                            this.spawnAtLocation(block);
                        }

                        this.discard();
                    }
                } else {
                    BlockState blockstate = this.level.getBlockState(blockpos);
                    this.setDeltaMovement(this.getDeltaMovement().multiply(0.7D, -0.5D, 0.7D));
                    if (!blockstate.is(Blocks.MOVING_PISTON)) {
                        if (!this.cancelDrop) {
                            boolean flag2 = blockstate.canBeReplaced(new DirectionalPlaceContext(this.level, blockpos, Direction.DOWN, ItemStack.EMPTY, Direction.UP));
                            boolean flag3 = FallingBlock.isFree(this.level.getBlockState(blockpos.below())) && (!flag || !flag1);
                            boolean flag4 = this.blockState.canSurvive(this.level, blockpos) && !flag3;
                            if (flag2 && flag4) {
                                if (this.blockState.hasProperty(BlockStateProperties.WATERLOGGED) && this.level.getFluidState(blockpos).getType() == Fluids.WATER) {
                                    this.blockState = this.blockState.setValue(BlockStateProperties.WATERLOGGED, true);
                                }

                                if (this.level.setBlock(blockpos, this.blockState, 3)) {
                                    ((ServerLevel)this.level).getChunkSource().chunkMap.broadcast(this, new ClientboundBlockUpdatePacket(blockpos, this.level.getBlockState(blockpos)));
                                    this.discard();

                                    if (this.blockData != null && this.blockState.hasBlockEntity()) {
                                        BlockEntity blockentity = this.level.getBlockEntity(blockpos);
                                        if (blockentity != null) {
                                            CompoundTag compoundtag = blockentity.saveWithoutMetadata();

                                            for(String s : this.blockData.getAllKeys()) {
                                                compoundtag.put(s, this.blockData.get(s).copy());
                                            }

                                            try {
                                                blockentity.load(compoundtag);
                                            } catch (Exception exception) {
                                                SolarCraft.LOGGER.error("Failed to load block entity from falling block", (Throwable)exception);
                                            }

                                            blockentity.setChanged();
                                        }
                                    }
                                } else if (this.dropItem && this.level.getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
                                    this.discard();
                                    this.callOnBrokenAfterFall(block, blockpos);
                                    this.spawnAtLocation(block);
                                }
                            } else {
                                this.discard();
                                if (this.dropItem && this.level.getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
                                    this.callOnBrokenAfterFall(block, blockpos);
                                    this.spawnAtLocation(block);
                                }
                            }
                        } else {
                            this.discard();
                            this.callOnBrokenAfterFall(block, blockpos);
                        }
                    }
                }
            }

            this.setDeltaMovement(this.getDeltaMovement().scale(0.98D));
        }
    }



    public void callOnBrokenAfterFall(Block block, BlockPos pos) {

    }

    public boolean causeFallDamage(float p_149643_, float p_149644_, DamageSource p_149645_) {
        if (!this.hurtEntities) {
            return false;
        } else {
            int i = Mth.ceil(p_149643_ - 1.0F);
            if (i < 0) {
                return false;
            } else {
                Predicate<Entity> predicate = EntitySelector.NO_CREATIVE_OR_SPECTATOR.and(EntitySelector.LIVING_ENTITY_STILL_ALIVE);
                Block $$8 = this.blockState.getBlock();
                DamageSource damagesource1;
                if ($$8 instanceof Fallable) {
                    Fallable fallable = (Fallable)$$8;
                    damagesource1 = fallable.getFallDamageSource(this);
                } else {
                    damagesource1 = this.damageSources().fallingBlock(this);
                }

                DamageSource damagesource = damagesource1;
                float f = (float)Math.min(Mth.floor((float)i * this.fallDamagePerDistance), this.fallDamageMax);
                this.level().getEntities(this, this.getBoundingBox(), predicate).forEach((p_149649_) -> {
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

    protected void addAdditionalSaveData(CompoundTag tag) {
        tag.put("BlockState", NbtUtils.writeBlockState(this.blockState));
        tag.putInt("Time", this.time);
        tag.putBoolean("DropItem", this.dropItem);
        tag.putBoolean("HurtEntities", this.hurtEntities);
        tag.putFloat("FallHurtAmount", this.fallDamagePerDistance);
        tag.putInt("FallHurtMax", this.fallDamageMax);
        if (this.blockData != null) {
            tag.put("TileEntityData", this.blockData);
        }

    }

    protected void readAdditionalSaveData(CompoundTag tag) {
        this.blockState = NbtUtils.readBlockState(this.level().holderLookup(Registries.BLOCK), tag.getCompound("BlockState"));
        this.time = tag.getInt("Time");
        if (tag.contains("HurtEntities", 99)) {
            this.hurtEntities = tag.getBoolean("HurtEntities");
            this.fallDamagePerDistance = tag.getFloat("FallHurtAmount");
            this.fallDamageMax = tag.getInt("FallHurtMax");
        } else if (this.blockState.is(BlockTags.ANVIL)) {
            this.hurtEntities = true;
        }

        if (tag.contains("DropItem", 99)) {
            this.dropItem = tag.getBoolean("DropItem");
        }

        if (tag.contains("TileEntityData", 10)) {
            this.blockData = tag.getCompound("TileEntityData");
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

    public Packet<ClientGamePacketListener> getAddEntityPacket() {
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
    public boolean ignoreExplosion(Explosion e) {
        return true;
    }
}
