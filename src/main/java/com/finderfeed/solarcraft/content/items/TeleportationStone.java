package com.finderfeed.solarcraft.content.items;

import com.finderfeed.solarcraft.content.items.primitive.solacraft_item_classes.SolarcraftItem;
import com.finderfeed.solarcraft.content.items.solar_lexicon.unlockables.AncientFragment;
import com.finderfeed.solarcraft.helpers.Helpers;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Supplier;

public class TeleportationStone extends SolarcraftItem {
    public TeleportationStone(Properties props, Supplier<AncientFragment> fragmentSupplier) {
        super(props,fragmentSupplier);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (!level.isClientSide){
            ItemStack item = player.getItemInHand(hand);
            if (player.isCrouching()){
                this.assignPos(item,player);
                level.playSound(null,player.getX(),player.getY(),player.getZ(), SoundEvents.RESPAWN_ANCHOR_CHARGE, SoundSource.PLAYERS,1f,1f);

            }else{
                Pos pos = getPos(item);
                ServerPlayer p = (ServerPlayer) player;
                if (pos.teleport(p)){
                    level.playSound(null,p.getX(),p.getY(),p.getZ(), SoundEvents.RESPAWN_ANCHOR_DEPLETE.value(), SoundSource.PLAYERS,1f,1f);
                    item.shrink(1);
                    ((ServerLevel)level).sendParticles(ParticleTypes.PORTAL,pos.position.x,pos.position.y+p.getBbHeight()/2,pos.position.z,100,0.5,p.getBbHeight()/2,0.5,0.1);
                    return InteractionResultHolder.consume(item);
                }
            }
        }
        return super.use(level, player, hand);
    }

    public void assignPos(ItemStack itemStack, Player player){
        CompoundTag tag = itemStack.getOrCreateTag();
        CompoundTag posTag = Helpers.getTag(tag,"position");
        Pos pos = new Pos(player.level.dimension(),player.position(),player.yBodyRot,0);
        pos.serialize(posTag);
    }

    public Pos getPos(ItemStack item){
        CompoundTag tag = item.getOrCreateTag();
        if (tag.contains("position")){
            return Pos.deserialize(tag.getCompound("position"));
        }else{
            return null;
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> cmps, TooltipFlag p_41424_) {
        cmps.add(Component.translatable("solarcraft.teleportation_stone").withStyle(ChatFormatting.GOLD));
        Pos pos = getPos(stack);
        if (pos != null){
            Vec3 tpPos = pos.position;
            cmps.add(Component.translatable("solarcraft.teleportation_stone_2",
                    pos.dimension.location().toString()
                    ,"%.2f %.2f %.2f".formatted(tpPos.x,tpPos.y,tpPos.z)).withStyle(ChatFormatting.GREEN));
        }
    }

    @Override
    public boolean isFoil(ItemStack item) {
        return item.getOrCreateTag().contains("position");
    }

    public static class Pos {

        public ResourceKey<Level> dimension;
        public Vec3 position;
        public float yaw;
        public float pitch;

        public Pos(ResourceKey<Level> dimension,Vec3 pos,float yaw,float pitch){
            this.dimension = dimension;
            this.position = pos;
            this.yaw = yaw;
            this.pitch = pitch;
        }

        public void serialize(CompoundTag tag){
            tag.putString("dimension",dimension.location().toString());
            tag.putDouble("x",position.x);
            tag.putDouble("y",position.y);
            tag.putDouble("z",position.z);
            tag.putFloat("yaw",yaw);
            tag.putFloat("ptich",pitch);
        }

        public static Pos deserialize(CompoundTag tag){
            ResourceLocation dim = new ResourceLocation(tag.getString("dimension"));
            ResourceKey<Level> dimension = ResourceKey.create(Registries.DIMENSION,dim);
            Vec3 pos = new Vec3(
              tag.getDouble("x"),
              tag.getDouble("y"),
              tag.getDouble("z")
            );
            float yaw = tag.getFloat("yaw");
            float pitch = tag.getFloat("pitch");
            return new Pos(dimension,pos,yaw,pitch);
        }

        public boolean teleport(ServerPlayer player){
            ServerLevel level = player.server.getLevel(this.dimension);
            if (level != null) {
                player.fallDistance = 0;
                player.teleportTo(level,this.position.x,this.position.y,this.position.z,this.yaw,this.pitch);
                return true;
            } else {
                player.sendSystemMessage(Component.literal("Could not find level " + this.dimension.location() + ", try again."));
            }
            return false;
        }
    }
}
