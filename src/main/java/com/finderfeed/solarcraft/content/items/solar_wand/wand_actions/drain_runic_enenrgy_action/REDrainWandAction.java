package com.finderfeed.solarcraft.content.items.solar_wand.wand_actions.drain_runic_enenrgy_action;

import com.finderfeed.solarcraft.SolarCraft;
import com.finderfeed.solarcraft.content.blocks.blockentities.RuneEnergyPylonTile;
import com.finderfeed.solarcraft.content.items.solar_lexicon.progressions.Progression;
import com.finderfeed.solarcraft.content.items.solar_wand.WandAction;
import com.finderfeed.solarcraft.content.items.solar_wand.WandActionType;
import com.finderfeed.solarcraft.content.items.solar_wand.WandDataSerializer;
import com.finderfeed.solarcraft.content.items.solar_wand.WandUseContext;
import com.finderfeed.solarcraft.helpers.ClientHelpers;
import com.finderfeed.solarcraft.helpers.Helpers;
import com.finderfeed.solarcraft.misc_things.RunicEnergy;
import com.finderfeed.solarcraft.registries.items.SolarcraftItems;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

import java.util.Locale;

public class REDrainWandAction implements WandAction<REDrainWandActionData> {



    @Override
    public InteractionResult run(WandUseContext context, REDrainWandActionData data) {
        Player player = context.player();
        Level level = context.level();
        RunicEnergy.Type type = data.getTypeToDrain();
        WandActionType actionType = this.getActionType(context.player());
        if (actionType == WandActionType.AIR){
//            RunicEnergy.Type newtype = RunicEnergy.Type.getAll()[(type.getIndex() + 1) % RunicEnergy.Type.getAll().length];
//            data.setTypeToDrain(newtype);
//            player.displayClientMessage(Component.translatable("solarcraft.wand_action.re_drain.drain_type").withStyle(ChatFormatting.GOLD)
//                            .append(Component.literal(": " + newtype.id.toUpperCase(Locale.ROOT)).withStyle(ChatFormatting.GOLD)), true);
            if (level.isClientSide){
                ClientHelpers.handleClientREDrainWandAction();
            }
        }else{
            Vec3 from = player.position().add(0,player.getStandingEyeHeight(
                    player.getPose(),player.getDimensions(player.getPose())
            ),0);
            Vec3 look = player.getLookAngle().multiply(30,30,30);
            Vec3 to = from.add(look);
            ClipContext ctx = new ClipContext(from,to, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE,null);
            BlockHitResult result = level.clip(ctx);
            if (level.getBlockEntity(result.getBlockPos()) instanceof IREWandDrainable source){
                if (!source.shouldAutomaticallySwitchWandType()){
                    if (source.allowedDrainableTypes().contains(type)){
                        if (!level.isClientSide) {
                            float energy = source.drainEnergy(player, source.getMaxEnergyDrain());
                            float delta = RunicEnergy.givePlayerEnergy(player, energy, type);
                            source.returnEnergy(player, delta);
                            Helpers.updateRunicEnergyOnClient(type, RunicEnergy.getEnergy(player, type), player);
                            player.displayClientMessage(Component.literal(type.id.toUpperCase() + " " + RunicEnergy.getEnergy(player, type))
                                    .withStyle(ChatFormatting.GOLD), true);
                        }else{
                            BlockPos p = result.getBlockPos();
                            Vec3 pos = new Vec3(p.getX()+0.5,p.getY()+0.5,p.getZ()+0.5);
                            Vec3 vel = new Vec3(from.x-pos.x,from.y-pos.y,from.z-pos.z);
                            ClientHelpers.handleSolarWandParticles(pos,vel);
                        }
                    }
                }else if (!source.allowedDrainableTypes().isEmpty()){
                    if (!level.isClientSide){
                        RunicEnergy.Type newtype = source.allowedDrainableTypes().get(0);
                        data.setTypeToDrain(newtype);
                        float energy = source.drainEnergy(player,source.getMaxEnergyDrain());
                        float delta = RunicEnergy.givePlayerEnergy(player,energy,newtype);
                        source.returnEnergy(player,delta);
                        player.displayClientMessage(Component.literal(type.id.toUpperCase() + " " + RunicEnergy.getEnergy(player, newtype))
                                .withStyle(ChatFormatting.GOLD), true);
                        Helpers.updateRunicEnergyOnClient(newtype,RunicEnergy.getEnergy(player,newtype),player);
                    }else{
                        BlockPos p = result.getBlockPos();
                        Vec3 pos = new Vec3(p.getX()+0.5,p.getY()+0.5,p.getZ()+0.5);
                        Vec3 vel = new Vec3(from.x-pos.x,from.y-pos.y,from.z-pos.z);
                        ClientHelpers.handleSolarWandParticles(pos,vel);
                    }
                }
            }
        }


        return InteractionResult.SUCCESS;
    }

//    public void handleEnergyConsumption(Level world, Player player,String strType){
//
//        Vec3 from = player.position().add(0,1.4,0);
//        Vec3 look = player.getLookAngle().multiply(30,30,30);
//        Vec3 to = from.add(look);
//        ClipContext ctx = new ClipContext(from,to, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE,null);
//        BlockHitResult res = world.clip(ctx);
//
//        if (world.getBlockEntity(res.getBlockPos()) instanceof IREWandDrainable tile){
//            if (!world.isClientSide){
//                float maxDrain = tile.getMaxEnergyDrain();
//                float energy = tile.drainEnergy(maxDrain);
//                if (strType.equals(IREWandDrainable.ALL)){
//
//                }else {
//                    RunicEnergy.Type type = RunicEnergy.Type.byId(strType);
//                    RunicEnergy.givePlayerEnergy(player,)
//
//                    player.displayClientMessage(Component.literal(type.id.toUpperCase() + " " + RunicEnergy.getEnergy(player, tile.getEnergyType())).withStyle(ChatFormatting.GOLD), true);
//                    Helpers.updateRunicEnergyOnClient(type, RunicEnergy.getEnergy(player, type), player);
//                    Helpers.fireProgressionEvent(player, Progression.RUNE_ENERGY_CLAIM);
//                    if (!RunicEnergy.hasFoundType(player, type)) {
//                        Helpers.sendEnergyTypeToast((ServerPlayer) player, type);
//                        RunicEnergy.setFound(player, type);
//                    }
//                    if (!Helpers.hasPlayerCompletedProgression(Progression.ALL_ENERGY_TYPES, player)) {
//                        boolean f = true;
//                        for (RunicEnergy.Type t : RunicEnergy.Type.getAll()) {
//                            f = RunicEnergy.hasFoundType(player, t);
//                            if (!f) {
//                                break;
//                            }
//                        }
//                        if (f) {
//                            Helpers.fireProgressionEvent(player, Progression.ALL_ENERGY_TYPES);
//                        }
//                    }
//                }
//            }else{
//                Vec3 pos = new Vec3(res.getBlockPos().getX()+0.5,res.getBlockPos().getY()+0.5,res.getBlockPos().getZ()+0.5);
//                Vec3 vel = new Vec3(from.x-pos.x,from.y-pos.y,from.z-pos.z);
//                ClientHelpers.handleSolarWandParticles(pos,vel);
//
//            }
//        }
//    }

    @Override
    public WandDataSerializer<REDrainWandActionData> getWandDataSerializer() {
        return REDrainWandActionDataSerializer.SERIALIZER;
    }

    @Override
    public WandActionType getActionType(Player player) {
        return player.isCrouching() ? WandActionType.AIR : WandActionType.ON_USE_TICK;
    }

    @Override
    public Component getActionDescription() {
        return Component.translatable("solarcraft.wand_action.drain_energy");
    }

    @Override
    public ResourceLocation getRegistryName() {
        return new ResourceLocation(SolarCraft.MOD_ID,"drain_re");
    }

    @Override
    public ItemStack getIcon() {
        return SolarcraftItems.SOLAR_WAND.get().getDefaultInstance();
    }
}
