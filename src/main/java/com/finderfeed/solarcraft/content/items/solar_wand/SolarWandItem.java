package com.finderfeed.solarcraft.content.items.solar_wand;

import com.finderfeed.solarcraft.content.blocks.infusing_table_things.InfuserTileEntity;
import com.finderfeed.solarcraft.content.items.solar_wand.client.SolarWandRenderProperties;
import com.finderfeed.solarcraft.content.items.solar_wand.wand_actions.drain_runic_enenrgy_action.REDrainWandActionData;
import com.finderfeed.solarcraft.content.items.solar_wand.wand_actions.drain_runic_enenrgy_action.REDrainWandActionDataSerializer;
import com.finderfeed.solarcraft.content.items.solar_wand.wand_actions.solar_network.SolarNetworkBinderWAData;
import com.finderfeed.solarcraft.content.items.solar_wand.wand_actions.solar_network.SolarNetworkBinderWandAction;
import com.finderfeed.solarcraft.helpers.ClientHelpers;
import com.finderfeed.solarcraft.helpers.Helpers;
import com.finderfeed.solarcraft.content.blocks.blockentities.InfusingTableTile;
import com.finderfeed.solarcraft.content.blocks.blockentities.RuneEnergyPylonTile;
import com.finderfeed.solarcraft.content.items.runic_energy.IRunicEnergyUser;
import com.finderfeed.solarcraft.content.items.runic_energy.RunicEnergyCost;
import com.finderfeed.solarcraft.content.items.solar_lexicon.progressions.Progression;
import com.finderfeed.solarcraft.misc_things.*;
import com.finderfeed.solarcraft.registries.wand_actions.SolarCraftWandActionRegistry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.TooltipFlag;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.Vec3;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.Nullable;


import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;

public class SolarWandItem extends Item implements IRunicEnergyUser {


    private static final HashMap<String, WandAction<?>> WAND_ACTIONS = new HashMap<>();

    public SolarWandItem(Properties p_i48487_1_) {
        super(p_i48487_1_);
    }


    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack item = player.getItemInHand(hand);
        var action = getCurrentAction(item);
        if (action != null) {
            WandActionType actionType = action.getActionType(player);
            if (actionType == WandActionType.AIR){
                WandUseContext context = new WandUseContext(player.level,player,item,null,null);
                WandDataSerializer<? extends WandData<?>> serializer = action.getWandDataSerializer();
                String dataname = serializer.getDataName().toString();
                if (!item.getOrCreateTag().contains(dataname)){
                    item.getOrCreateTag().put(dataname,new CompoundTag());
                }

                CompoundTag tag = item.getOrCreateTag().getCompound(dataname);
                WandData<?> data = serializer.deserialize(tag);

                InteractionResult result = action.hackyRun(context,data);

                serializer.hackySerialize(tag,data);
                switch (result){
                    case SUCCESS -> {
                        return InteractionResultHolder.success(item);
                    }
                    case FAIL -> {
                        return InteractionResultHolder.fail(item);
                    }
                    case CONSUME -> {
                        return InteractionResultHolder.consume(item);
                    }
                    default -> {
                        return InteractionResultHolder.pass(item);
                    }
                }
            }else if (actionType == WandActionType.ON_USE_TICK){
                player.startUsingItem(hand);
            }
        }

        return super.use(world, player, hand);
    }

    @Override
    public void onUseTick(Level level, LivingEntity player, ItemStack item, int time) {
        var action = getCurrentAction(item);
        if (action != null && player instanceof Player entity && action.getActionType(entity) == WandActionType.ON_USE_TICK ){
            WandUseContext context = new WandUseContext(player.level,entity,item,null,time);
            WandDataSerializer<? extends WandData<?>> serializer = action.getWandDataSerializer();

            String dataname = serializer.getDataName().toString();
            if (!item.getOrCreateTag().contains(dataname)){
                item.getOrCreateTag().put(dataname,new CompoundTag());
            }

            CompoundTag tag = item.getOrCreateTag().getCompound(dataname);
            WandData<?> data = serializer.deserialize(tag);

            action.hackyRun(context,data);

            serializer.hackySerialize(tag,data);
        }
        super.onUseTick(level, player, item, time);
    }

//    @Override
//    public void onUsingTick(ItemStack item, LivingEntity player, int time) {
////        if (player instanceof  Player) {
////            handleEnergyConsumption(player.level, (Player) player);
////        }
//
//        var action = getCurrentAction(item);
//        if (action != null && player instanceof Player entity && action.getActionType(entity) == WandActionType.ON_USE_TICK ){
//            WandUseContext context = new WandUseContext(player.level,entity,item,null,time);
//            WandDataSerializer<? extends WandData<?>> serializer = action.getWandDataSerializer();
//
//            String dataname = serializer.getDataName().toString();
//            if (!item.getOrCreateTag().contains(dataname)){
//                item.getOrCreateTag().put(dataname,new CompoundTag());
//            }
//
//            CompoundTag tag = item.getOrCreateTag().getCompound(dataname);
//            WandData<?> data = serializer.deserialize(tag);
//
//            action.hackyRun(context,data);
//
//            serializer.hackySerialize(tag,data);
//        }
//
//        super.onUsingTick(item, player, time);
//    }

    @Override
    public UseAnim getUseAnimation(ItemStack p_77661_1_) {
        return UseAnim.BOW;
    }

    @Override
    public InteractionResult useOn(UseOnContext ctx) {
        Player player = ctx.getPlayer();
        InteractionHand hand = ctx.getHand();
        ItemStack item = player.getItemInHand(hand);

        var action = getCurrentAction(item);
        if (action != null && action.getActionType(player) == WandActionType.BLOCK){
            WandUseContext context = new WandUseContext(player.level,player,item,ctx,null);
            WandDataSerializer<? extends WandData<?>> serializer = action.getWandDataSerializer();
            String dataname = serializer.getDataName().toString();
            if (!item.getOrCreateTag().contains(dataname)){
                item.getOrCreateTag().put(dataname,new CompoundTag());
            }

            CompoundTag tag = item.getOrCreateTag().getCompound(dataname);

            WandData data = serializer.deserialize(tag);

            InteractionResult result = action.hackyRun(context,data);

            serializer.hackySerialize(tag,data);
            return result;
        }


        return InteractionResult.FAIL;
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 72000;
    }

    @Override
    public void appendHoverText(ItemStack item, @Nullable Level world, List<Component> components, TooltipFlag p_77624_4_) {
        components.add(Component.translatable("solarcraft.item.solar_wand")
                .withStyle(ChatFormatting.GOLD));
        WandAction<?> action = getCurrentAction(item);
        if (action == SolarCraftWandActionRegistry.RUNIC_ENERGY_DRAIN){
            if (item.getOrCreateTag().contains(action.getWandDataSerializer().getDataName().toString())) {
                CompoundTag tag = item.getOrCreateTag().getCompound(action.getWandDataSerializer().getDataName().toString());
                REDrainWandActionData data = REDrainWandActionDataSerializer.SERIALIZER.deserialize(tag);
                components.add(Component.translatable("solarcraft.wand_action.re_drain.drain_type").withStyle(ChatFormatting.GOLD)
                        .append(Component.literal(": " + data.getTypeToDrain().id.toUpperCase(Locale.ROOT)).withStyle(ChatFormatting.GOLD)));
            }
        }else if (action == SolarCraftWandActionRegistry.SOLAR_NETWORK_BINDER_WAND_ACTION){
            CompoundTag tag = action.getWandDataSerializer().getTag(item);
            SolarNetworkBinderWAData data = (SolarNetworkBinderWAData) action.getWandDataSerializer().deserialize(tag);
            components.add(Component.translatable("solarcraft.wand_action.solar_network_binder_pos").withStyle(ChatFormatting.GOLD)
                    .append(Component.literal("" + data.firstPos)));
        }
        super.appendHoverText(item, world, components, p_77624_4_);
    }

    public static void registerWandAction(ResourceLocation location,WandAction action){
        String loc = location.toString();
        if (WAND_ACTIONS.containsKey(loc)) throw new IllegalStateException("Duplicate action: " + loc);
        WAND_ACTIONS.put(loc,action);
    }

    public static WandAction<?> getWandAction(ResourceLocation location){
        String loc = location.toString();
        return WAND_ACTIONS.get(loc);
    }

    @Nullable
    public static WandAction<?> getCurrentAction(ItemStack stack){
        return getWandAction(new ResourceLocation(stack.getOrCreateTag().getString("solarcraft_wand_action")));
    }

    public static void setWandAction(ItemStack itemStack,ResourceLocation location){
        itemStack.getOrCreateTag().putString("solarcraft_wand_action",location.toString());
    }

    public static Collection<WandAction<?>> getAllActions(){
        return WAND_ACTIONS.values();
    }



    @Override
    public float getMaxRunicEnergyCapacity() {
        return 0;
    }

    @Override
    public List<RunicEnergy.Type> allowedInputs() {
        return List.of();
    }

    @Override
    public RunicEnergyCost getCost() {
        return RunicEnergyCost.EMPTY;
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        super.initializeClient(consumer);
        consumer.accept(SolarWandRenderProperties.INSTANCE);
    }
}

