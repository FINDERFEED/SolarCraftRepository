package com.finderfeed.solarforge.commands;

import com.finderfeed.solarforge.Helpers;
import com.finderfeed.solarforge.registries.items.ItemsRegister;
import com.finderfeed.solarforge.magic_items.items.solar_lexicon.achievements.Achievement;
import com.finderfeed.solarforge.magic_items.items.solar_lexicon.unlockables.AncientFragment;
import com.finderfeed.solarforge.magic_items.items.solar_lexicon.unlockables.ProgressionHelper;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.TranslatableComponent;

public class CommandsSolarCraft {

    public static void register(CommandDispatcher<CommandSourceStack> disp){
        LiteralCommandNode<CommandSourceStack> cmd = disp.register(
                Commands.literal("solarcraft")
                        .then(UnlockAchievementsCommand.register())
                        .then(refreshAchievements.register())
                        .then(AchievementsHelp.register())
                        .then(RetainFragments.register())
        );
    }

}

class RetainFragments{
    public static ArgumentBuilder<CommandSourceStack,?> register(){
        return Commands.literal("fragments")
                .requires(cs->cs.hasPermission(0))
                .then(Commands.literal("retain").executes((cmd)->retainFragments(cmd.getSource())))
                .then(Commands.literal("unlockall").executes((cmd)->unlockAllFragments(cmd.getSource())))
                .then(Commands.literal("refresh").executes((cmd)->refreshFragments(cmd.getSource())));
    }

    public static int retainFragments(CommandSourceStack src) throws CommandSyntaxException {
        ServerPlayer playerEntity  = src.getPlayerOrException();
        for (AncientFragment fragment : AncientFragment.getAllFragments()){
            if (ProgressionHelper.doPlayerHasFragment(playerEntity,fragment)){
                ItemStack frag = ItemsRegister.INFO_FRAGMENT.get().getDefaultInstance();
                ProgressionHelper.applyTagToFragment(frag,fragment);
                ItemEntity entity = new ItemEntity(playerEntity.level,playerEntity.getX(),playerEntity.getY()+0.3f,playerEntity.getZ(),frag);
                playerEntity.getLevel().addFreshEntity(entity);
            }
        }
        return 0;
    }

    public static int unlockAllFragments(CommandSourceStack src) throws CommandSyntaxException {
        ServerPlayer playerEntity  = src.getPlayerOrException();
        for (AncientFragment fragment : AncientFragment.getAllFragments()){
            ProgressionHelper.givePlayerFragment(fragment,playerEntity);
        }
        return 0;
    }
    public static int refreshFragments(CommandSourceStack src) throws CommandSyntaxException {
        ServerPlayer playerEntity  = src.getPlayerOrException();
        for (AncientFragment fragment : AncientFragment.getAllFragments()){
            ProgressionHelper.revokePlayerFragment(fragment,playerEntity);
        }
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
        for (Achievement ach : Achievement.ALL_ACHIEVEMENTS){

            src.sendSuccess(new TextComponent(ach.translation.getString()).withStyle(ChatFormatting.GOLD)
                    .append(new TextComponent(" -> "+ach.getAchievementCode())).withStyle(ChatFormatting.WHITE),false);

        }
        src.sendSuccess(new TextComponent("all").withStyle(ChatFormatting.GOLD)
                .append(" -> unlocks all").withStyle(ChatFormatting.WHITE),false);
        return 0;
    }
}


class UnlockAchievementsCommand {
    public static ArgumentBuilder<CommandSourceStack,?> register(){
        return Commands.literal("unlock")
                .requires(cs->cs.hasPermission(0))
                .then(Commands.argument("achievement", StringArgumentType.string())
                        .executes((cmd)->{

                    return unlockAchievement(cmd.getSource(),cmd.getArgument("achievement",String.class));
                }));
    }
    public static int unlockAchievement(CommandSourceStack src,String code) throws CommandSyntaxException{
            Achievement achievement = Achievement.getAchievementByName(code);
            ServerPlayer pl = src.getPlayerOrException();
            if (code.equals("all")){
                for (Achievement a : Achievement.ALL_ACHIEVEMENTS){
                    Helpers.setAchievementStatus(a,src.getPlayerOrException(),true);
                    src.sendSuccess(new TranslatableComponent("solarcraft.success_unlock")
                            .append(new TextComponent(" "+a.translation.getString()).withStyle(ChatFormatting.GOLD)),false);
                }
                Helpers.updateProgression(src.getPlayerOrException());

            }else if (achievement != null){
                if (Helpers.canPlayerUnlock(achievement,pl)){
                    Helpers.setAchievementStatus(achievement,pl,true);
                    src.sendSuccess(new TranslatableComponent("solarcraft.success_unlock")
                            .append(new TextComponent(" "+achievement.getAchievementCode()).withStyle(ChatFormatting.GOLD)),false);

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
            for (Achievement ach : Achievement.ALL_ACHIEVEMENTS){
                Helpers.setAchievementStatus(ach,src.getPlayerOrException(),false);
            }
            src.sendSuccess(new TextComponent("Successfully refreshed all achievements"),false);
        Helpers.updateProgression(src.getPlayerOrException());
        Helpers.forceChunksReload(src.getPlayerOrException());
        return 0;
    }
}


class AchievementArgument implements ArgumentType<String>{

    public static AchievementArgument arg(){
        return new AchievementArgument();
    }
    @Override
    public String parse(StringReader reader) throws CommandSyntaxException {
        return reader.readString();
    }

    
}
