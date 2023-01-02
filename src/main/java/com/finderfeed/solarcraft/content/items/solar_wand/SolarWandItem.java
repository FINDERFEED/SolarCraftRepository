package com.finderfeed.solarcraft.content.items.solar_wand;

import com.finderfeed.solarcraft.content.blocks.infusing_table_things.InfuserTileEntity;
import com.finderfeed.solarcraft.helpers.ClientHelpers;
import com.finderfeed.solarcraft.helpers.Helpers;
import com.finderfeed.solarcraft.content.blocks.blockentities.InfusingTableTile;
import com.finderfeed.solarcraft.content.blocks.blockentities.RuneEnergyPylonTile;
import com.finderfeed.solarcraft.content.items.runic_energy.IRunicEnergyUser;
import com.finderfeed.solarcraft.content.items.runic_energy.RunicEnergyCost;
import com.finderfeed.solarcraft.content.items.solar_lexicon.progressions.Progression;
import com.finderfeed.solarcraft.misc_things.*;
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
import org.jetbrains.annotations.Nullable;


import java.util.HashMap;
import java.util.List;

public class SolarWandItem extends Item implements IRunicEnergyUser {


    private static final HashMap<String, WandAction<?>> WAND_ACTIONS = new HashMap<>();

    public SolarWandItem(Properties p_i48487_1_) {
        super(p_i48487_1_);
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
    public WandAction<?> getCurrentAction(ItemStack stack){
        return getWandAction(new ResourceLocation(stack.getOrCreateTag().getString("solarcraft_wand_action")));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack item = player.getItemInHand(hand);
        var action = getCurrentAction(item);
        if (action != null) {
            WandActionType actionType = action.getActionType();
            if (actionType == WandActionType.AIR){
                WandUseContext context = new WandUseContext(player.level,player,item,null,null);
                WandDataSerializer<? extends WandData<?>> serializer = action.getWandDataSerializer();
                CompoundTag tag = item.getOrCreateTag().getCompound(serializer.getDataName().toString());
                WandData data = serializer.deserialize(tag);

                action.run(context,data);

                serializer.serialize(tag,data);
            }else if (actionType == WandActionType.ON_USE_TICK){
                player.startUsingItem(hand);
            }
        }


        player.startUsingItem(hand);

        return super.use(world, player, hand);
    }

    @Override
    public void onUsingTick(ItemStack stack, LivingEntity player, int time) {
        if (player instanceof  Player) {
            handleEnergyConsumption(player.level, (Player) player);
        }

        var action = getCurrentAction(stack);
        if (action != null && action.getActionType() == WandActionType.ON_USE_TICK && player instanceof Player entity){
            WandUseContext context = new WandUseContext(player.level,entity,stack,null,time);
            WandDataSerializer<? extends WandData<?>> serializer = action.getWandDataSerializer();
            CompoundTag tag = stack.getOrCreateTag().getCompound(serializer.getDataName().toString());
            WandData data = serializer.deserialize(tag);

            action.run(context,data);

            serializer.serialize(tag,data);
        }

        super.onUsingTick(stack, player, time);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack p_77661_1_) {
        return UseAnim.BOW;
    }

    @Override
    public InteractionResult useOn(UseOnContext ctx) {
        Player player = ctx.getPlayer();
        InteractionHand hand = ctx.getHand();
        ItemStack item = player.getItemInHand(hand);

        item.getOrCreateTag().putString("solarcraft_wand_action","solarcraft:on_block_use");

        var action = getCurrentAction(item);
        if (action != null && action.getActionType() == WandActionType.BLOCK){
            WandUseContext context = new WandUseContext(player.level,player,item,ctx,null);
            WandDataSerializer<? extends WandData<?>> serializer = action.getWandDataSerializer();
            CompoundTag tag = item.getOrCreateTag().getCompound(serializer.getDataName().toString());
            WandData data = serializer.deserialize(tag);

            InteractionResult result = action.run(context,data);

            serializer.serialize(tag,data);
            return result;
        }


//        BlockPos pos = ctx.getClickedPos();
//        Level world = ctx.getLevel();
//        if (!world.isClientSide && world.getBlockEntity(pos) != null ) {
//            BlockEntity entity = world.getBlockEntity(pos);
//            if (entity instanceof InfuserTileEntity infuserTileEntity){
//                infuserTileEntity.triggerCrafting(ctx.getPlayer());
//                return InteractionResult.SUCCESS;
//            }else if (entity instanceof InfusingTableTile craftingTable){
//                craftingTable.triggerRecipe(ctx.getPlayer());
//                return InteractionResult.SUCCESS;
//            }
//        }


        return InteractionResult.FAIL;
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 72000;
    }

    @Override
    public void appendHoverText(ItemStack p_77624_1_, @Nullable Level p_77624_2_, List<Component> p_77624_3_, TooltipFlag p_77624_4_) {
        p_77624_3_.add(Component.translatable("solarcraft.solar_wand").withStyle(ChatFormatting.GOLD));
        super.appendHoverText(p_77624_1_, p_77624_2_, p_77624_3_, p_77624_4_);
    }


    public void handleEnergyConsumption(Level world, Player player){

        Vec3 from = player.position().add(0,1.4,0);
        Vec3 look = player.getLookAngle().multiply(30,30,30);
        Vec3 to = from.add(look);
        ClipContext ctx = new ClipContext(from,to, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE,null);
        BlockHitResult res = world.clip(ctx);

        if (world.getBlockEntity(res.getBlockPos()) instanceof RuneEnergyPylonTile tile){
            if (!world.isClientSide){
                tile.givePlayerEnergy(player,5);
                RunicEnergy.Type type = tile.getEnergyType();
                player.displayClientMessage(Component.literal(type.id.toUpperCase()+" "+RunicEnergy.getEnergy(player,tile.getEnergyType())).withStyle(ChatFormatting.GOLD),true);
                Helpers.updateRunicEnergyOnClient(type,RunicEnergy.getEnergy(player,type),player);
                Helpers.fireProgressionEvent(player, Progression.RUNE_ENERGY_CLAIM);
                if (!RunicEnergy.hasFoundType(player,type)) {
                    Helpers.sendEnergyTypeToast((ServerPlayer) player, type);
                    RunicEnergy.setFound(player,type);
                }
                if (!Helpers.hasPlayerCompletedProgression(Progression.ALL_ENERGY_TYPES,player)){
                    boolean f = true;
                    for (RunicEnergy.Type t : RunicEnergy.Type.getAll()){
                        f = RunicEnergy.hasFoundType(player,t);
                        if (!f){
                            break;
                        }
                    }
                    if (f){
                        Helpers.fireProgressionEvent(player,Progression.ALL_ENERGY_TYPES);
                    }
                }
            }else{
                Vec3 pos = new Vec3(res.getBlockPos().getX()+0.5,res.getBlockPos().getY()+0.5,res.getBlockPos().getZ()+0.5);
                Vec3 vel = new Vec3(from.x-pos.x,from.y-pos.y,from.z-pos.z);
                ClientHelpers.handleSolarWandParticles(pos,vel);

            }
        }
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
}

