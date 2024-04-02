package com.finderfeed.solarcraft.misc_things;

import com.finderfeed.solarcraft.content.entities.DungeonRay;
import com.finderfeed.solarcraft.content.entities.dungeon_ray_controller.DungeonRayController;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

public class DungeonRayControllerStick extends Item {
    public DungeonRayControllerStick(Properties p_41383_) {
        super(p_41383_);
    }


    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {



        return super.use(level, player, hand);
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
