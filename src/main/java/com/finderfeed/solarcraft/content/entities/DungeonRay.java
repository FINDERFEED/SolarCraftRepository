package com.finderfeed.solarcraft.content.entities;

import com.finderfeed.solarcraft.helpers.Helpers;
import com.finderfeed.solarcraft.local_library.helpers.CompoundNBTHelper;
import com.finderfeed.solarcraft.packet_handler.packet_system.FDPacketUtil;
import com.finderfeed.solarcraft.registries.damage_sources.SCDamageSources;
import com.finderfeed.solarcraft.registries.entities.SCEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundMoveEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;

import java.util.ArrayList;
import java.util.List;

public class DungeonRay extends Entity {

    public static boolean stop = false;

    public static EntityDataAccessor<Direction> DIRECTION = SynchedEntityData.defineId(DungeonRay.class, EntityDataSerializers.DIRECTION);
    private List<BlockPos> movePos = new ArrayList<>();
    private int currentMoveTarget = 1;
    private double movespeed = 0.1;
    private float rayLength = 0;

    public static void summon(Level level,BlockPos summonPos,Direction direction){
        DungeonRay ray = new DungeonRay(SCEntityTypes.DUNGEON_RAY.get(),level);
        ray.setPos(Helpers.getBlockCenter(summonPos));
        ray.noPhysics = true;
        ray.movePos.add(summonPos);
        level.addFreshEntity(ray);
        ray.setDirection(direction);
    }

    public DungeonRay(EntityType<?> type, Level level) {
        super(type, level);
    }


    @Override
    public void tick() {
        this.noPhysics = true;
        this.rayLength = this.computeRayLength();
        if (!level.isClientSide){
            this.processMovement();
            this.doDamage();
        }
        super.tick();
    }

    private void processMovement(){
        if (movePos.size() > 1 && !stop){
            BlockPos ptarget = movePos.get(currentMoveTarget);
            Vec3 target = ptarget.getCenter();
            Vec3 c = this.position();
            Vec3 b = target.subtract(c);
            if (b.length() > movespeed){
                Vec3 move = b.normalize().multiply(movespeed,movespeed,movespeed);
                this.move(MoverType.SELF,move);
            }else{
                this.setPos(target.x,target.y,target.z);
                currentMoveTarget = (currentMoveTarget + 1) % movePos.size();
            }
        }
    }

    private void doDamage(){
        List<LivingEntity> entities = level.getEntitiesOfClass(LivingEntity.class,this.getDamageBox());
        for (LivingEntity entity : entities){
            entity.hurt(SCDamageSources.RUNIC_MAGIC,10f);
        }
    }

    private AABB getDamageBox(){
        float rayLen = this.rayLength;
        Vec3 begin = this.blockPosition().getCenter();
        Vec3i nrm = this.getDirection().getNormal();
        Vec3 end = begin.add(nrm.getX() * rayLen,nrm.getY() * rayLen,nrm.getZ() * rayLen);
        Vec3i n = this.nc(this.getDirection().getNormal());
        AABB box = new AABB(
                begin.x,begin.y,begin.z,
                end.x + n.getX(),end.y + n.getY(),end.z + n.getZ()
        );
        return box;
    }

    private Vec3i nc(Vec3i normal){
        return new Vec3i(
                normal.getX() == 0 ? 1 : normal.getX(),
                normal.getY() == 0 ? 1 : normal.getY(),
                normal.getZ() == 0 ? 1 : normal.getZ()
        );
    }

    private float computeRayLength(){
        Vec3i n = this.getDirection().getNormal();
        Vec3 begin = this.position();
        Vec3 end = begin.add(n.getX()*20,n.getY()*20,n.getZ()*20);
        ClipContext ctx = new ClipContext(begin,end, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, CollisionContext.empty());
        BlockHitResult res = level.clip(ctx);
        Vec3 location = res.getLocation();
        Vec3 i = location.subtract(begin);
        return (float) i.length();
    }



    public List<BlockPos> getMovePositions() {
        return movePos;
    }


    @Override
    public boolean isNoGravity() {
        return true;
    }

    public float getRayLength() {
        return rayLength;
    }

    public Direction getDirection(){
        return this.entityData.get(DIRECTION);
    }

    public void setDirection(Direction dir){
        this.entityData.set(DIRECTION,dir);
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(DIRECTION,Direction.DOWN);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {
        if (tag.contains("direction")) {
            this.setDirection(Direction.byName(tag.getString("direction")));
        }
        movePos = CompoundNBTHelper.getBlockPosList("movePos",tag);
        currentMoveTarget = tag.getInt("moveTarget");
        movespeed = tag.getDouble("movespeed");
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {
        Direction d = this.getDirection();
        tag.putString("direction",d.getName());
        tag.putInt("moveTarget",currentMoveTarget);
        CompoundNBTHelper.writeBlockPosList("movePos",movePos,tag);
        tag.putDouble("movespeed",movespeed);
    }

    @Override
    public boolean isPickable() {
        return true;
    }


    @Override
    public boolean shouldRender(double p_20296_, double p_20297_, double p_20298_) {
        return true;
    }

    @Override
    public boolean shouldRenderAtSqrDistance(double p_19883_) {
        return true;
    }

}
