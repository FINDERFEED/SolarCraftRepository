package com.finderfeed.solarcraft.content.items;

import com.finderfeed.solarcraft.client.particles.ball_particle.BallParticleOptions;
import com.finderfeed.solarcraft.client.particles.lightning_particle.LightningParticleOptions;
import com.finderfeed.solarcraft.client.particles.server_data.shapes.SendShapeParticlesPacket;
import com.finderfeed.solarcraft.client.particles.server_data.shapes.instances.SphereParticleShape;
import com.finderfeed.solarcraft.content.blocks.blockentities.RuneEnergyPylonTile;
import com.finderfeed.solarcraft.content.entities.uldera_crystal.UlderaCrystalBoss;
import com.finderfeed.solarcraft.content.entities.uldera_crystal.UlderaLightningEntity;
import com.finderfeed.solarcraft.content.items.primitive.solacraft_item_classes.SolarcraftItem;
import com.finderfeed.solarcraft.content.items.solar_lexicon.unlockables.AncientFragment;
import com.finderfeed.solarcraft.helpers.Helpers;
import com.finderfeed.solarcraft.misc_things.RunicEnergy;
import com.finderfeed.solarcraft.packet_handler.packet_system.FDPacketUtil;
import com.finderfeed.solarcraft.packet_handler.packets.CameraShakePacket;
import com.finderfeed.solarcraft.registries.entities.SCEntityTypes;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CorruptedShardItem extends SolarcraftItem {

    private static final AABB THROW_AWAY_BOX = new AABB(-10,-10,-10,10,10,10);
    public CorruptedShardItem(Properties props) {
        super(props,() -> AncientFragment.CORRUPTED_SHARD);
    }

    @Override
    public InteractionResult useOn(UseOnContext ctx) {
        BlockPos usePos = ctx.getClickedPos();
        Level level = ctx.getLevel();
        ItemStack item = ctx.getItemInHand();
        if (!level.isClientSide){
            if (removePylonAndSpawnBoss(level,usePos)){
                item.shrink(1);
                return InteractionResult.CONSUME;
            }
        }
        return super.useOn(ctx);
    }


    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> cmps, TooltipFlag flag) {
        super.appendHoverText(stack, level, cmps, flag);
        cmps.add(Component.translatable("solarcraft.item.corrupted_shard").withStyle(ChatFormatting.LIGHT_PURPLE));
    }

    //height - 9
    private boolean removePylonAndSpawnBoss(Level level, BlockPos pos){
        if (level.getBlockEntity(pos) instanceof RuneEnergyPylonTile tile){
            RunicEnergy.Type pylonType = tile.getEnergyType();
            level.setBlock(pos, Blocks.AIR.defaultBlockState(),3);
            UlderaCrystalBoss boss = new UlderaCrystalBoss(SCEntityTypes.ULDERA_CRYSTAL_BOSS.get(),level);
            Vec3 p = new Vec3(pos.getX() + 0.5,pos.getY() - 8,pos.getZ() + 0.5);
            boss.setPos(p);
            boss.setREType(pylonType);
            this.throwAwayAllEntities(level,pos);
            this.spawnEffects(level,pos);
            level.addFreshEntity(boss);
            return true;
        }
        return false;
    }

    private void throwAwayAllEntities(Level level,BlockPos from){
        Vec3 c = Helpers.getBlockCenter(from);
        for (LivingEntity entity : level.getEntitiesOfClass(LivingEntity.class,THROW_AWAY_BOX.move(c))){
            Vec3 v = entity.position().multiply(1,0,1)
                    .subtract(c.multiply(1,0,1))
                    .normalize()
                    .multiply(5,0,5)
                    .add(0,0.5,0);
            if (entity instanceof ServerPlayer player){
                Helpers.setServerPlayerSpeed(player,v);
            }else{
                entity.setDeltaMovement(v);
            }
        }
    }

    private void spawnEffects(Level level,BlockPos spawnPos){
        Vec3 c = Helpers.getBlockCenter(spawnPos);
        ((ServerLevel)level).playSound(null,c.x,c.y,c.z, SoundEvents.GENERIC_EXPLODE, SoundSource.HOSTILE,10,0.5f);
        Helpers.spawnUlderaLightning(level,c,10,0,100);
        FDPacketUtil.sendToPlayersCloseToSpot(level,c,40,new SendShapeParticlesPacket(
                new SphereParticleShape(0.5,0.6f,5,true,true,1f),
                new BallParticleOptions.Builder().setRGB(90,0,186).setPhysics(false)
                        .setShouldShrink(true).setSize(0.5f).build(),
                c.x,c.y,c.z,0,0,0
        ));
        FDPacketUtil.sendToPlayersCloseToSpot(level,c,40,new SendShapeParticlesPacket(
                new SphereParticleShape(0.5,0.6f,3,true,true,1f),
                new LightningParticleOptions(2f,90,0,186,-1,60,false),
                c.x,c.y,c.z,0,0,0
        ));
        FDPacketUtil.sendToPlayersCloseToSpot(level,c,10,new CameraShakePacket(0,20,20,0.5f));
    }


}
