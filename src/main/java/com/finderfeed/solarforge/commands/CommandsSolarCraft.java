package com.finderfeed.solarforge.commands;

import com.finderfeed.solarforge.Helpers;
import com.finderfeed.solarforge.registries.items.ItemsRegister;
import com.finderfeed.solarforge.solar_lexicon.achievements.Achievement;
import com.finderfeed.solarforge.solar_lexicon.unlockables.AncientFragment;
import com.finderfeed.solarforge.solar_lexicon.unlockables.ProgressionHelper;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

public class CommandsSolarCraft {

    public static void register(CommandDispatcher<CommandSource> disp){
        LiteralCommandNode<CommandSource> cmd = disp.register(
                Commands.literal("solarcraft")
                        .then(UnlockAchievementsCommand.register())
                        .then(refreshAchievements.register())
                        .then(AchievementsHelp.register())
                        .then(RetainFragments.register())
        );
    }

}

class RetainFragments{
    public static ArgumentBuilder<CommandSource,?> register(){
        return Commands.literal("fragments")
                .requires(cs->cs.hasPermission(0))
                .executes((cmd)->{

                    return retainFragments(cmd.getSource());
                });
    }

    public static int retainFragments(CommandSource src) throws CommandSyntaxException {
        ServerPlayerEntity playerEntity  = src.getPlayerOrException();
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
}


class AchievementsHelp{
    public static ArgumentBuilder<CommandSource,?> register(){
        return Commands.literal("codes")
                .requires(cs->cs.hasPermission(0))
                .executes((cmd)->{

                    return getHelp(cmd.getSource());
                        });
    }

    public static int getHelp(CommandSource src){
        src.sendSuccess(new TranslationTextComponent("solarcraft.gethelpcommand").withStyle(TextFormatting.GOLD),false);
        for (Achievement ach : Achievement.ALL_ACHIEVEMENTS){

            src.sendSuccess(new StringTextComponent(ach.translation.getString()).withStyle(TextFormatting.GOLD)
                    .append(new StringTextComponent(" -> "+ach.getAchievementCode())).withStyle(TextFormatting.WHITE),false);

        }
        src.sendSuccess(new StringTextComponent("all").withStyle(TextFormatting.GOLD)
                .append(" -> unlocks all").withStyle(TextFormatting.WHITE),false);
        return 0;
    }
}


class UnlockAchievementsCommand {
    public static ArgumentBuilder<CommandSource,?> register(){
        return Commands.literal("unlock")
                .requires(cs->cs.hasPermission(0))
                .then(Commands.argument("achievement", StringArgumentType.string())
                        .executes((cmd)->{

                    return unlockAchievement(cmd.getSource(),cmd.getArgument("achievement",String.class));
                }));
    }
    public static int unlockAchievement(CommandSource src,String code) throws CommandSyntaxException{
            Achievement achievement = Achievement.getAchievementByName(code);
            ServerPlayerEntity pl = src.getPlayerOrException();
            if (code.equals("all")){
                for (Achievement a : Achievement.ALL_ACHIEVEMENTS){
                    Helpers.setAchievementStatus(a,src.getPlayerOrException(),true);
                    src.sendSuccess(new TranslationTextComponent("solarcraft.success_unlock")
                            .append(new StringTextComponent(" "+a.translation.getString()).withStyle(TextFormatting.GOLD)),false);
                }
                Helpers.updateProgression(src.getPlayerOrException());

            }else if (achievement != null){
                if (Helpers.canPlayerUnlock(achievement,pl)){
                    Helpers.setAchievementStatus(achievement,pl,true);
                    src.sendSuccess(new TranslationTextComponent("solarcraft.success_unlock")
                            .append(new StringTextComponent(" "+achievement.getAchievementCode()).withStyle(TextFormatting.GOLD)),false);

                    Helpers.updateProgression(src.getPlayerOrException());


                }else {
                    src.sendFailure(new TranslationTextComponent("solarcraft.failure_unlock"));
                }
            }else {
                src.sendFailure(new TranslationTextComponent("solarcraft.failure_unlock"));
            }
        Helpers.forceChunksReload(src.getPlayerOrException());
        return 0;
    }

}

class refreshAchievements{
    public static ArgumentBuilder<CommandSource,?> register(){
        return Commands.literal("refresh").requires(cs->cs.hasPermission(0)).executes(
                (src)->{
                    return refresh(src.getSource());
                });
    }

    public static int refresh(CommandSource src) throws CommandSyntaxException {
            for (Achievement ach : Achievement.ALL_ACHIEVEMENTS){
                Helpers.setAchievementStatus(ach,src.getPlayerOrException(),false);
            }
            src.sendSuccess(new StringTextComponent("Successfully refreshed all achievements"),false);
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
