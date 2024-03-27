package com.finderfeed.solarcraft.misc_things;

import com.finderfeed.solarcraft.content.blocks.blockentities.RuneEnergyPylonTile;
import com.finderfeed.solarcraft.content.entities.DungeonRay;
import com.finderfeed.solarcraft.helpers.Helpers;
import com.finderfeed.solarcraft.local_library.client.particles.particle_emitters.ParticleEmitterData;
import com.finderfeed.solarcraft.local_library.client.particles.particle_emitters.ParticleEmmitterPacket;
import com.finderfeed.solarcraft.local_library.client.particles.particle_emitters.particle_emitter_processors.instances.ebpe_processor.EBPEmitterProcessorData;
import com.finderfeed.solarcraft.local_library.client.particles.particle_emitters.particle_processors.instances.random_speed.RandomSpeedProcessorData;
import com.finderfeed.solarcraft.packet_handler.packet_system.FDPacketUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public class SolarcraftDebugStick extends Item {
    public SolarcraftDebugStick(Properties p_41383_) {
        super(p_41383_);
    }

    private BlockPos first;
    private BlockPos second;

    @Override
    public void inventoryTick(ItemStack item, Level world, Entity player, int slot, boolean held) {
        super.inventoryTick(item, world, player, slot, held);
    }

    @Override
    public InteractionResult useOn(UseOnContext ctx) {
        Level world = ctx.getLevel();
        BlockPos pos = ctx.getClickedPos();
        Player player = ctx.getPlayer();
        return this.dungeonRayHandle(ctx.getHand(),world,player,ctx.getItemInHand(),pos);
    }

    public void switchPylons(BlockPos pos,Level world){
        if (world.getBlockEntity(pos) instanceof RuneEnergyPylonTile tile){
            tile.setType(RunicEnergy.Type.getAll()[(tile.getEnergyType().getIndex() + 1) % RunicEnergy.Type.getAll().length]);
            Helpers.updateTile(tile);
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        if (!world.isClientSide){
            DungeonRay ray = this.getRayOnSight(world,player);
            if (ray != null) {
                this.setUUID(player.getItemInHand(hand), ray.getUUID());
            }else{
                DungeonRay ray2 = this.getDungeonRay((ServerLevel) world,player.getItemInHand(hand));
                if (ray2 != null) {
                    List<Direction> dirs = List.of(
                      Direction.UP,
                      Direction.DOWN,
                      Direction.NORTH,
                      Direction.WEST,
                      Direction.EAST,
                      Direction.SOUTH
                    );
                    Direction direction = ray2.getDirection();
                    int index = (dirs.indexOf(direction) + 1) % dirs.size();
                    Direction newDir = dirs.get(index);
                    ray2.setDirection(newDir);
                }
            }
            return InteractionResultHolder.success(player.getItemInHand(hand));
        }
        return super.use(world, player, hand);
    }


    private InteractionResult dungeonRayHandle(InteractionHand hand,Level world,Player player,ItemStack item,BlockPos clickedPos){
        if (hand == InteractionHand.MAIN_HAND && !world.isClientSide){
            DungeonRay ray = this.getDungeonRay((ServerLevel) world,item);
            if (!player.isCrouching()) {
                if (ray != null) {
                    ray.getMovePositions().add(clickedPos);
                }
            }else{
                DungeonRay.summon(world,clickedPos, Direction.UP);
            }
        }
        return InteractionResult.SUCCESS;
    }

    private UUID getUUID(ItemStack itemStack){
        CompoundTag tag = itemStack.getOrCreateTag();
        if (tag.contains("ray_uuid")){
            return tag.getUUID("ray_uuid");
        }else{
            return null;
        }
    }

    private void setUUID(ItemStack stack,UUID uuid){
        CompoundTag tag = stack.getOrCreateTag();
        if (uuid != null) {
            tag.putUUID("ray_uuid", uuid);
        }else{
            tag.remove("ray_uuid");
        }
    }

    @Nullable
    private DungeonRay getDungeonRay(ServerLevel serverLevel,ItemStack item){
        UUID uuid = this.getUUID(item);
        if (uuid != null){
            Entity entity = serverLevel.getEntity(uuid);
            if (entity instanceof DungeonRay ray){
                return ray;
            }else{
                return null;
            }
        }else{
            return null;
        }
    }


    @Nullable
    private DungeonRay getRayOnSight(Level level,Player player){
        Vec3 begin = player.position().add(0,player.getEyeHeight(),0);
        Vec3 end = player.getLookAngle().multiply(5,5,5).add(begin);
        EntityHitResult res = ProjectileUtil.getEntityHitResult(level,player,begin,end,new AABB(
                begin,end
        ),entity->entity instanceof DungeonRay);
        if (res != null && res.getEntity() instanceof DungeonRay ray){
            return ray;
        }else{
            return null;
        }
    }
}
