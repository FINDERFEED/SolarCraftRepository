package com.finderfeed.solarcraft.misc_things;

import com.finderfeed.solarcraft.content.entities.DungeonRay;
import com.finderfeed.solarcraft.content.entities.dungeon_ray_controller.DungeonRayController;
import com.finderfeed.solarcraft.content.entities.dungeon_ray_controller.DungeonRayHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
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
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

public class DungeonRayControllerStick extends DebugStick {
    public DungeonRayControllerStick(Properties p_41383_) {
        super(p_41383_);
    }


//    @Override
//    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
//
//        if (!level.isClientSide){
//            if (hand == InteractionHand.OFF_HAND) {
//                DungeonRayController controller = this.getRayControllerOnSight(level, player);
//                this.setUUID(player.getItemInHand(InteractionHand.OFF_HAND),controller.getUUID());
//                player.sendSystemMessage(Component.literal("Changed controller"));
//                return InteractionResultHolder.success(player.getItemInHand(InteractionHand.OFF_HAND));
//            }else{
//                DungeonRayController controller = this.getDungeonRayController((ServerLevel) level,player.getItemInHand(hand));
//                if (controller != null){
//                    if (!player.isCrouching()) {
//                        controller.getHandlers().add(new DungeonRayHandler());
//                        player.sendSystemMessage(Component.literal("Created handler"));
//                        controller.cycleCurrentSelectedHandler();
//                    }else{
//                        controller.removeSelectedHandler();
//                        player.sendSystemMessage(Component.literal("Removed handler"));
//                    }
//                }
//                return InteractionResultHolder.success(player.getItemInHand(InteractionHand.MAIN_HAND));
//            }
//        }
//
//        return super.use(level, player, hand);
//    }

    @Override
    public void getUseActions(Map<String, Consumer<UseContext>> useOnActions) {
        useOnActions.put("setController",(ctx)->{
            Player player = ctx.player();
            Level level = ctx.level();
            if (!level.isClientSide) {
                DungeonRayController controller = this.getRayControllerOnSight(level, player);
                this.setUUID(player.getItemInHand(InteractionHand.OFF_HAND), controller.getUUID());
            }
        });
        useOnActions.put("createHandler",ctx->{
            Player player = ctx.player();
            Level level = ctx.level();
            ItemStack item = player.getItemInHand(ctx.hand());
            if (level instanceof ServerLevel serverLevel) {
                var controller = this.getDungeonRayController(serverLevel,item);
                if (controller != null) {
                    controller.getHandlers().add(new DungeonRayHandler());
                    player.sendSystemMessage(Component.literal("Created handler"));
                    controller.cycleCurrentSelectedHandler();
                }
            }
        });
        useOnActions.put("removeHandler",ctx->{
            Player player = ctx.player();
            Level level = ctx.level();
            ItemStack item = player.getItemInHand(ctx.hand());
            if (level instanceof ServerLevel serverLevel) {
                var controller = this.getDungeonRayController(serverLevel,item);
                if (controller != null) {
                    controller.removeSelectedHandler();
                }
            }
        });
        useOnActions.put("cycleHandlers",ctx->{
            Player player = ctx.player();
            Level level = ctx.level();
            ItemStack item = player.getItemInHand(ctx.hand());
            if (level instanceof ServerLevel serverLevel) {
                var controller = this.getDungeonRayController(serverLevel,item);
                if (controller != null) {
                    controller.cycleCurrentSelectedHandler();
                }
            }
        });
    }

    @Override
    public void getUseOnActions(Map<String, Consumer<UseOnContext>> useOnActions) {

    }

    @Override
    public InteractionResult useOn(UseOnContext ctx) {
        Level l = ctx.getLevel();
        InteractionHand hand = ctx.getHand();
        Player player = ctx.getPlayer();
        BlockPos clicked = ctx.getClickedPos();
        if (l instanceof ServerLevel level){
            DungeonRayController controller = this.getDungeonRayController(level, player.getItemInHand(hand));
            if (controller == null){
                player.sendSystemMessage(Component.literal("Controller is null"));
                return InteractionResult.SUCCESS;
            }
            DungeonRayHandler handler = controller.getCurrentSelectedHandler();
            if (hand == InteractionHand.MAIN_HAND) {
                if (handler == null){
                    player.sendSystemMessage(Component.literal("Handler is null"));
                    return InteractionResult.SUCCESS;
                }
                BlockPos epos = controller.blockPosition();
                handler.movePositionOffsets.add(clicked.subtract(epos));
                player.sendSystemMessage(Component.literal("Added offset:" + clicked.subtract(epos)));
            }else{
                controller.cycleCurrentSelectedHandler();
                player.sendSystemMessage(Component.literal("Cycled handler"));
            }
            return InteractionResult.SUCCESS;
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
    private DungeonRayController getDungeonRayController(ServerLevel serverLevel, ItemStack item){
        UUID uuid = this.getUUID(item);
        if (uuid != null){
            Entity entity = serverLevel.getEntity(uuid);
            if (entity instanceof DungeonRayController ray){
                return ray;
            }else{
                return null;
            }
        }else{
            return null;
        }
    }

    @Nullable
    private DungeonRayController getRayControllerOnSight(Level level, Player player){
        Vec3 begin = player.position().add(0,player.getEyeHeight(),0);
        Vec3 end = player.getLookAngle().multiply(5,5,5).add(begin);
        EntityHitResult res = ProjectileUtil.getEntityHitResult(level,player,begin,end,new AABB(
                begin,end
        ),entity->entity instanceof DungeonRayController);
        if (res != null && res.getEntity() instanceof DungeonRayController ray){
            return ray;
        }else{
            return null;
        }
    }

}
