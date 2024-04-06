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
import net.minecraft.network.chat.Component;
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
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
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
        if (world instanceof ServerLevel level){
            UUID uuid = UUID.fromString("2cb35eb4-753d-4585-b01c-4dd404ff55b8");
//            System.out.println(level.getEntity(uuid));
        }
        super.inventoryTick(item, world, player, slot, held);
    }


    public void switchPylons(BlockPos pos,Level world){
        if (world.getBlockEntity(pos) instanceof RuneEnergyPylonTile tile){
            tile.setType(RunicEnergy.Type.getAll()[(tile.getEnergyType().getIndex() + 1) % RunicEnergy.Type.getAll().length]);
            Helpers.updateTile(tile);
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {

        return super.use(world, player, hand);
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
