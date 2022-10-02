package com.finderfeed.solarforge.content.commands;

import com.finderfeed.solarforge.content.items.solar_lexicon.progressions.progression_tree.ProgressionTree;
import com.finderfeed.solarforge.helpers.Helpers;
import com.finderfeed.solarforge.helpers.multiblock.Multiblocks;
import com.finderfeed.solarforge.local_library.helpers.FDMathHelper;
import com.finderfeed.solarforge.content.items.solar_lexicon.SolarLexicon;
import com.finderfeed.solarforge.content.items.solar_lexicon.progressions.Progression;
import com.finderfeed.solarforge.misc_things.RunicEnergy;

import com.finderfeed.solarforge.registries.items.SolarcraftItems;
import com.finderfeed.solarforge.content.items.solar_lexicon.unlockables.AncientFragment;
import com.finderfeed.solarforge.content.items.solar_lexicon.unlockables.ProgressionHelper;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import java.util.Locale;

public class SolarCraftCommands {

    public static void register(CommandDispatcher<CommandSourceStack> disp){
        LiteralCommandNode<CommandSourceStack> cmd = disp.register(
                Commands.literal("solarcraft").requires((p)-> p.hasPermission(2))
//                        .then(UnlockProgressionsCommand.register())
//                        .then(refreshAchievements.register())
//                        .then(AchievementsHelp.register())

                        .then(Commands.literal("progressions")
                                .then(Commands.literal("help").executes((e)->progressionsHelp(e.getSource())))
                                .then(Commands.literal("unlock")
                                        .then(Commands.argument("progression",StringArgumentType.string())
                                                .executes((e)->unlockProgression(e.getSource(),e.getArgument("progression",String.class)))))
                                .then(Commands.literal("revoke").then(Commands.argument("progression",StringArgumentType.string())
                                        .executes((e)->revokeProgression(e.getSource(),e.getArgument("progression",String.class))))))


                        .then(RetainFragments.register())
                        .then(Commands.literal("structure").then(Commands.literal("construct").then(Commands.argument("structure_code",StringArgumentType.string())
                                .executes((cmds)-> constructMultiblock(cmds.getSource(),cmds.getArgument("structure_code",String.class))))))
                        .then(Commands.literal("fillLexicon").executes((cmss)-> fillLexicon(cmss.getSource())))
                        .then(Commands.literal("runicEnergy")
                                .then(Commands.literal("set").then(Commands.argument("type",StringArgumentType.string())
                                        .then(Commands.argument("target",EntityArgument.player()).then(Commands.argument("amount", FloatArgumentType.floatArg(0))
                                                .executes((stack)->setREAmount(stack.getSource(),EntityArgument.getPlayer(stack,"target"),
                                                                stack.getArgument("type",String.class),
                                                                stack.getArgument("amount",Float.class)))))))
                        )

        );
    }

    public static int revokeProgression(CommandSourceStack src,String code) throws CommandSyntaxException {
        Progression progression = Progression.getAchievementByName(code);
        ServerPlayer pl = src.getPlayerOrException();
        if (code.equals("all")){
            for (Progression a : Progression.allProgressions){
                Helpers.setProgressionCompletionStatus(a,src.getPlayerOrException(),false);
                src.sendSuccess(new TranslatableComponent("solarcraft.success_revoke")
                        .append(new TextComponent(" "+a.translation.getString()).withStyle(ChatFormatting.GOLD)),false);
            }
            Helpers.updateProgression(src.getPlayerOrException());

        }else if (progression != null){
            if (Helpers.hasPlayerCompletedProgression(progression,pl)){
                boolean flag = true;
                for (Progression p : ProgressionTree.INSTANCE.getProgressionChildren(progression)){
                    if (Helpers.hasPlayerCompletedProgression(p,pl)){
                        flag = false;
                        break;
                    }
                }
                if (flag) {
                    Helpers.setProgressionCompletionStatus(progression, pl, false);
                    src.sendSuccess(new TranslatableComponent("solarcraft.success_revoke")
                            .append(new TextComponent(" " + progression.getProgressionCode()).withStyle(ChatFormatting.GOLD)), false);
                    Helpers.updateProgression(src.getPlayerOrException());
                }else{
                    src.sendFailure(new TranslatableComponent("solarcraft.failure_revoke"));

                }
            }else {
                src.sendFailure(new TranslatableComponent("solarcraft.failure_revoke"));
            }
        }else {
            src.sendFailure(new TranslatableComponent("solarcraft.failure_revoke"));
        }
        Helpers.forceChunksReload(src.getPlayerOrException());
        return 0;
    }

    public static int unlockProgression(CommandSourceStack src,String code) throws CommandSyntaxException {
        Progression progression = Progression.getAchievementByName(code);
        ServerPlayer pl = src.getPlayerOrException();
        if (code.equals("all")){
            for (Progression a : Progression.allProgressions){
                Helpers.setProgressionCompletionStatus(a,src.getPlayerOrException(),true);
                src.sendSuccess(new TranslatableComponent("solarcraft.success_unlock")
                        .append(new TextComponent(" "+a.translation.getString()).withStyle(ChatFormatting.GOLD)),false);
            }
            Helpers.updateProgression(src.getPlayerOrException());

        }else if (progression != null){
            if (Helpers.canPlayerUnlock(progression,pl)){
                Helpers.setProgressionCompletionStatus(progression,pl,true);
                src.sendSuccess(new TranslatableComponent("solarcraft.success_unlock")
                        .append(new TextComponent(" "+ progression.getProgressionCode()).withStyle(ChatFormatting.GOLD)),false);

                Helpers.updateProgression(src.getPlayerOrException());


            }else {
                src.sendFailure(new TranslatableComponent("solarcraft.failure_unlock"));
            }
        }else {
            src.sendFailure(new TranslatableComponent("solarcraft.failure_unlock"));
        }
        Helpers.forceChunksReload(src.getPlayerOrException());
        return 0;
    }

    public static int progressionsHelp(CommandSourceStack src) throws CommandSyntaxException {
        src.sendSuccess(new TranslatableComponent("solarcraft.gethelpcommand").withStyle(ChatFormatting.GOLD),false);
        for (Progression ach : Progression.allProgressions){

            src.sendSuccess(new TextComponent(ach.translation.getString()).withStyle(ChatFormatting.GOLD)
                    .append(new TextComponent(" -> "+ach.getProgressionCode())).withStyle(ChatFormatting.WHITE),false);

        }
        src.sendSuccess(new TextComponent("all").withStyle(ChatFormatting.GOLD)
                .append(" -> unlocks/revokes all").withStyle(ChatFormatting.WHITE),false);
        return 0;
    }

    public static int setREAmount(CommandSourceStack stack, Player target,String type,float amount){
        if (target != null){
            RunicEnergy.Type t = RunicEnergy.Type.byId(type.toLowerCase(Locale.ROOT));
            if (t != null && t != RunicEnergy.Type.NONE){
                RunicEnergy.setEnergy(target, (float)FDMathHelper.clamp(0,amount,10000),t);
                Helpers.updateRunicEnergyOnClient(t,RunicEnergy.getEnergy(target,t),target);
            }else{
                stack.sendFailure(new TextComponent("Unknown RE type."));
            }
        }else{
            stack.sendFailure(new TextComponent("Target is null."));
        }
        return 1;
    }

    public static int fillLexicon(CommandSourceStack src) throws CommandSyntaxException {
        ServerPlayer player = src.getPlayerOrException();
        if (player.getMainHandItem().getItem() instanceof SolarLexicon){
            LazyOptional<IItemHandler> cap = player.getMainHandItem().getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY);
            if (cap.isPresent()){
                cap.ifPresent((inv)->{
                    try {
                        for (int i = 0; i < AncientFragment.getAllFragments().length; i++) {

                            AncientFragment fragment = AncientFragment.getAllFragments()[i];
                            ItemStack stack = SolarcraftItems.INFO_FRAGMENT.get().getDefaultInstance();
                            ProgressionHelper.applyTagToFragment(stack, fragment);
                            inv.insertItem(i, stack, false);


                        }
                        src.sendSuccess(new TextComponent("Filled lexicon inventory"),false);
                    }catch (Exception e){
                        e.printStackTrace();
                        src.sendFailure(new TextComponent("CAUGHT FATAL ERROR DURING COMMAND, STACK TRACE PRINTED"));
                    }

                });
            }else {
                src.sendFailure(new TextComponent("Not found inventory"));
            }
        }else {
            src.sendFailure(new TextComponent("Solar lexicon not found in main hand"));
        }
        return 1;
    }

    public static int constructMultiblock(CommandSourceStack src,String id) throws CommandSyntaxException{
        ServerPlayer player = src.getPlayerOrException();
        if (Multiblocks.STRUCTURES.containsKey(id)){
            Multiblocks.STRUCTURES.get(id).placeInWorld(player,player.level,player.getOnPos().above());
//            Helpers.constructMultiblock(player,Multiblocks.MULTIBLOCKS.get(id));
            src.sendSuccess(new TextComponent("Constructed!"),false);
        }else{
            src.sendFailure(new TextComponent("Structure doesnt exist"));
        }
        return 0;
    }
}





class RetainFragments{
    public static ArgumentBuilder<CommandSourceStack,?> register(){
        return Commands.literal("fragments")
                .requires(cs->cs.hasPermission(0))
//                .then(Commands.literal("retain").executes((cmd)->retainFragments(cmd.getSource())))
                .then(Commands.literal("unlockall").executes((cmd)->unlockAllFragments(cmd.getSource())))
                .then(Commands.literal("transferFromOldDataToNew").executes((cmd)->transferFromOldToNew(cmd.getSource())))
                .then(Commands.literal("refresh").executes((cmd)->refreshFragments(cmd.getSource())));
    }
    
    public static int transferFromOldToNew(CommandSourceStack src) throws CommandSyntaxException{
        ServerPlayer player = src.getPlayerOrException();
        for (AncientFragment fragment : AncientFragment.getAllFragments()){
            if (player.getPersistentData().getBoolean(ProgressionHelper.getFragIdString(fragment))){
                ProgressionHelper.givePlayerFragment(fragment,player);
            }
        }
        src.sendSuccess(new TextComponent("Fragments successfully translated from old data to new"),true);
        return 1;
    }

//    public static int retainFragments(CommandSourceStack src) throws CommandSyntaxException {
//        ServerPlayer playerEntity  = src.getPlayerOrException();
//        for (AncientFragment fragment : AncientFragment.getAllFragments()){
//            if (ProgressionHelper.doPlayerHasFragment(playerEntity,fragment)){
//                ItemStack frag = SolarcraftItems.INFO_FRAGMENT.get().getDefaultInstance();
//                ProgressionHelper.applyTagToFragment(frag,fragment);
//                ItemEntity entity = new ItemEntity(playerEntity.level,playerEntity.getX(),playerEntity.getY()+0.3f,playerEntity.getZ(),frag);
//                playerEntity.getLevel().addFreshEntity(entity);
//            }
//        }
//        return 0;
//    }

    public static int unlockAllFragments(CommandSourceStack src) throws CommandSyntaxException {
        ServerPlayer playerEntity  = src.getPlayerOrException();
        for (AncientFragment fragment : AncientFragment.getAllFragments()){
            ProgressionHelper.givePlayerFragment(fragment,playerEntity);
        }
        Helpers.updateFragmentsOnClient(playerEntity);
        return 0;
    }
    public static int refreshFragments(CommandSourceStack src) throws CommandSyntaxException {
        ServerPlayer playerEntity  = src.getPlayerOrException();
        for (AncientFragment fragment : AncientFragment.getAllFragments()){
            ProgressionHelper.revokePlayerFragment(fragment,playerEntity);
        }
        Helpers.updateFragmentsOnClient(playerEntity);
        return 0;
    }
}


class AchievementsHelp{
    public static ArgumentBuilder<CommandSourceStack,?> register(){
        return Commands.literal("codes")
                .requires(cs->cs.hasPermission(0))
                .executes((cmd)->{

                    return getHelp(cmd.getSource());
                        });
    }

    public static int getHelp(CommandSourceStack src){
        src.sendSuccess(new TranslatableComponent("solarcraft.gethelpcommand").withStyle(ChatFormatting.GOLD),false);
        for (Progression ach : Progression.allProgressions){

            src.sendSuccess(new TextComponent(ach.translation.getString()).withStyle(ChatFormatting.GOLD)
                    .append(new TextComponent(" -> "+ach.getProgressionCode())).withStyle(ChatFormatting.WHITE),false);

        }
        src.sendSuccess(new TextComponent("all").withStyle(ChatFormatting.GOLD)
                .append(" -> unlocks all").withStyle(ChatFormatting.WHITE),false);
        return 0;
    }
}


class UnlockProgressionsCommand {
    public static ArgumentBuilder<CommandSourceStack,?> register(){
        return Commands.literal("unlock")
                .requires(cs->cs.hasPermission(0))
                .then(Commands.argument("progression", StringArgumentType.string())
                        .executes((cmd)->{

                    return unlockAchievement(cmd.getSource(),cmd.getArgument("progression",String.class));
                }));
    }
    public static int unlockAchievement(CommandSourceStack src,String code) throws CommandSyntaxException{
            Progression progression = Progression.getAchievementByName(code);
            ServerPlayer pl = src.getPlayerOrException();
            if (code.equals("all")){
                for (Progression a : Progression.allProgressions){
                    Helpers.setProgressionCompletionStatus(a,src.getPlayerOrException(),true);
                    src.sendSuccess(new TranslatableComponent("solarcraft.success_unlock")
                            .append(new TextComponent(" "+a.translation.getString()).withStyle(ChatFormatting.GOLD)),false);
                }
                Helpers.updateProgression(src.getPlayerOrException());

            }else if (progression != null){
                if (Helpers.canPlayerUnlock(progression,pl)){
                    Helpers.setProgressionCompletionStatus(progression,pl,true);
                    src.sendSuccess(new TranslatableComponent("solarcraft.success_unlock")
                            .append(new TextComponent(" "+ progression.getProgressionCode()).withStyle(ChatFormatting.GOLD)),false);

                    Helpers.updateProgression(src.getPlayerOrException());


                }else {
                    src.sendFailure(new TranslatableComponent("solarcraft.failure_unlock"));
                }
            }else {
                src.sendFailure(new TranslatableComponent("solarcraft.failure_unlock"));
            }
        Helpers.forceChunksReload(src.getPlayerOrException());
        return 0;
    }

}

class refreshAchievements{
    public static ArgumentBuilder<CommandSourceStack,?> register(){
        return Commands.literal("refresh").requires(cs->cs.hasPermission(0)).executes(
                (src)->{
                    return refresh(src.getSource());
                });
    }

    public static int refresh(CommandSourceStack src) throws CommandSyntaxException {
            for (Progression ach : Progression.allProgressions){
                Helpers.setProgressionCompletionStatus(ach,src.getPlayerOrException(),false);
            }
            src.sendSuccess(new TextComponent("Successfully refreshed all progressions"),false);
        Helpers.updateProgression(src.getPlayerOrException());
        Helpers.forceChunksReload(src.getPlayerOrException());
        return 0;
    }
}
