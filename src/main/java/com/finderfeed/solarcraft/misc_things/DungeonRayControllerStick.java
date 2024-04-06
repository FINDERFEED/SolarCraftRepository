package com.finderfeed.solarcraft.misc_things;

import com.finderfeed.solarcraft.content.entities.DungeonRay;
import com.finderfeed.solarcraft.content.entities.dungeon_ray_controller.DungeonRayController;
import com.finderfeed.solarcraft.content.entities.dungeon_ray_controller.DungeonRayHandler;
import com.finderfeed.solarcraft.content.entities.dungeon_ray_controller.SendHandlersToClient;
import com.finderfeed.solarcraft.packet_handler.packet_system.FDPacketUtil;
import com.finderfeed.solarcraft.registries.entities.SCEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

public class DungeonRayControllerStick extends DebugStick {
    public DungeonRayControllerStick(Properties p_41383_) {
        super(p_41383_);
    }


    @UseAction("setController")
    public void setController(UseContext ctx){
        Player player = ctx.player();
        Level level = ctx.level();
        if (!level.isClientSide) {
            DungeonRayController controller = this.getRayControllerOnSight(level, player);
            if (controller != null) {
                this.setUUID(player.getItemInHand(ctx.hand()), controller.getUUID());
                player.sendSystemMessage(Component.literal("Controller set"));
            }else{
                player.sendSystemMessage(Component.literal("Controller not found"));
            }
        }
    }

    @UseAction("createHandler")
    public void createHandler(UseContext ctx){
        Player player = ctx.player();
        Level level = ctx.level();
        ItemStack item = player.getItemInHand(ctx.hand());
        if (level instanceof ServerLevel serverLevel) {
            var controller = this.getDungeonRayController(serverLevel,item);
            if (controller != null) {
                controller.getHandlers().add(new DungeonRayHandler());
                player.sendSystemMessage(Component.literal("Created handler"));
                controller.cycleCurrentSelectedHandler();
                FDPacketUtil.sendToTrackingEntity(controller,new SendHandlersToClient(controller));
            }
        }
    }

    @UseAction("removeHandler")
    public void removeHandler(UseContext ctx){
        Player player = ctx.player();
        Level level = ctx.level();
        ItemStack item = player.getItemInHand(ctx.hand());
        if (level instanceof ServerLevel serverLevel) {
            var controller = this.getDungeonRayController(serverLevel,item);
            if (controller != null) {
                controller.removeSelectedHandler();
                FDPacketUtil.sendToTrackingEntity(controller,new SendHandlersToClient(controller));
            }
        }
    }

    @UseAction("cycleHandlers")
    public void cycleHandlers(UseContext ctx){
        Player player = ctx.player();
        Level level = ctx.level();
        ItemStack item = player.getItemInHand(ctx.hand());
        if (level instanceof ServerLevel serverLevel) {
            var controller = this.getDungeonRayController(serverLevel,item);
            if (controller != null) {
                controller.cycleCurrentSelectedHandler();
            }
        }
    }

    @UseAction("enableOrDisableRaysMovement")
    public void enableOrDisableRaysMovement(UseContext ctx){
        Player player = ctx.player();
        Level level = ctx.level();
        ItemStack item = player.getItemInHand(ctx.hand());
        if (level instanceof ServerLevel serverLevel) {
            DungeonRayHandler.stopRays = !DungeonRayHandler.stopRays;
        }
    }
    @UseOnAction("addOffset")
    public void addOffset(UseOnContext ctx){
        Level l = ctx.getLevel();
        InteractionHand hand = ctx.getHand();
        Player player = ctx.getPlayer();
        BlockPos clicked = ctx.getClickedPos();
        if (l instanceof ServerLevel level){
            DungeonRayController controller = this.getDungeonRayController(level, player.getItemInHand(hand));
            if (controller == null) return;
            DungeonRayHandler handler = controller.getCurrentSelectedHandler();
            if (handler == null) return;

            BlockPos epos = controller.blockPosition();
            handler.addPos(clicked.subtract(epos));
            player.sendSystemMessage(Component.literal("Added offset:" + clicked.subtract(epos)));
            FDPacketUtil.sendToTrackingEntity(controller,new SendHandlersToClient(controller));

        }
    }
    @UseAction("changeDirection")
    public void changeDirection(UseContext ctx){
        Level l = ctx.level();
        InteractionHand hand = ctx.hand();
        Player player = ctx.player();
        if (l instanceof ServerLevel level){
            DungeonRayController controller = this.getDungeonRayController(level, player.getItemInHand(hand));
            if (controller == null) return;
            DungeonRayHandler handler = controller.getCurrentSelectedHandler();
            if (handler == null) return;

            List<Direction> dir = List.of(
                    Direction.UP,
                    Direction.DOWN,
                    Direction.NORTH,
                    Direction.WEST,
                    Direction.EAST,
                    Direction.SOUTH
            );
            int idx = dir.indexOf(handler.rayDir);
            handler.rayDir = dir.get((idx + 1) % dir.size());
            FDPacketUtil.sendToTrackingEntity(controller,new SendHandlersToClient(controller));

        }
    }
    @UseAction("changeMovespeed")
    public void changeMovespeed(UseContext ctx){
        Level l = ctx.level();
        InteractionHand hand = ctx.hand();
        Player player = ctx.player();
        if (l instanceof ServerLevel level){
            DungeonRayController controller = this.getDungeonRayController(level, player.getItemInHand(hand));
            if (controller == null) return;
            DungeonRayHandler handler = controller.getCurrentSelectedHandler();
            if (handler == null) return;

            if (player.isCrouching()){
                handler.movespeed -= 0.05f;
            }else{
                handler.movespeed += 0.05f;
            }

            FDPacketUtil.sendToTrackingEntity(controller,new SendHandlersToClient(controller));

        }
    }
    @UseOnAction("summonController")
    public void summonController(UseOnContext ctx){
        Level l = ctx.getLevel();
        InteractionHand hand = ctx.getHand();
        Player player = ctx.getPlayer();
        BlockPos clicked = ctx.getClickedPos();
        if (l instanceof ServerLevel level){
            DungeonRayController controller = new DungeonRayController(SCEntityTypes.DUNGEON_RAY_CONTROLLER.get(),level);
            controller.setPos(clicked.getCenter());
            level.addFreshEntity(controller);
        }
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
