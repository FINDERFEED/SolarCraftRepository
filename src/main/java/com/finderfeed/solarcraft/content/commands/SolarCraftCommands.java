package com.finderfeed.solarcraft.content.commands;

import com.finderfeed.solarcraft.SolarCraft;
import com.finderfeed.solarcraft.content.abilities.AbilityHelper;
import com.finderfeed.solarcraft.content.abilities.ability_classes.AbstractAbility;
import com.finderfeed.solarcraft.content.items.solar_lexicon.progressions.progression_tree.ProgressionTree;
import com.finderfeed.solarcraft.helpers.Helpers;
import com.finderfeed.solarcraft.helpers.multiblock.Multiblocks;
import com.finderfeed.solarcraft.local_library.bedrock_loader.animations.AnimatedObject;
import com.finderfeed.solarcraft.local_library.bedrock_loader.animations.Animation;
import com.finderfeed.solarcraft.local_library.bedrock_loader.animations.manager.AnimationTicker;
import com.finderfeed.solarcraft.local_library.helpers.FDMathHelper;
import com.finderfeed.solarcraft.content.items.solar_lexicon.SolarLexicon;
import com.finderfeed.solarcraft.content.items.solar_lexicon.progressions.Progression;
import com.finderfeed.solarcraft.misc_things.RunicEnergy;

import com.finderfeed.solarcraft.registries.SCAttachmentTypes;
import com.finderfeed.solarcraft.registries.abilities.AbilitiesRegistry;
import com.finderfeed.solarcraft.registries.animations.AnimationReloadableResourceListener;
import com.finderfeed.solarcraft.registries.items.SCItems;
import com.finderfeed.solarcraft.content.items.solar_lexicon.unlockables.AncientFragment;
import com.finderfeed.solarcraft.content.items.solar_lexicon.unlockables.AncientFragmentHelper;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;


import net.neoforged.neoforge.items.IItemHandler;
import java.util.Locale;

public class SolarCraftCommands {

    public static void register(CommandDispatcher<CommandSourceStack> disp){
        LiteralCommandNode<CommandSourceStack> cmd = disp.register(
                Commands.literal("solarcraft").requires((p)-> p.hasPermission(2))

                        .then(Commands.literal("progressions")
                                .then(Commands.literal("help").executes((e)->progressionsHelp(e.getSource())))
                                .then(Commands.literal("unlock")
                                        .then(Commands.argument("progression",StringArgumentType.string())
                                                .executes((e)->unlockProgression(e.getSource(),e.getArgument("progression",String.class)))))
                                .then(Commands.literal("revoke").then(Commands.argument("progression",StringArgumentType.string())
                                        .executes((e)->revokeProgression(e.getSource(),e.getArgument("progression",String.class))))))


                        .then(RestoreFragments.register())
                        .then(Commands.literal("structure")
                                .then(Commands.literal("construct").then(Commands.argument("structure_code",new SolarcraftStructureArgument())
                                .executes((cmds)-> constructMultiblock(cmds.getSource(),cmds.getArgument("structure_code", String.class))))))
                        .then(Commands.literal("fillLexicon").executes((cmss)-> fillLexicon(cmss.getSource())))
                        .then(Commands.literal("runicEnergy")
                                .then(Commands.literal("set").then(Commands.argument("type",StringArgumentType.string())
                                        .then(Commands.argument("target",EntityArgument.player()).then(Commands.argument("amount", FloatArgumentType.floatArg(0))
                                                .executes((stack)->setREAmount(stack.getSource(),EntityArgument.getPlayer(stack,"target"),
                                                                stack.getArgument("type",String.class),
                                                                stack.getArgument("amount",Float.class)))))))
                        ).then(Commands.literal("animtest")
                                .then(Commands.argument("ticker",StringArgumentType.string())
                                        .then(Commands.argument("animation",StringArgumentType.string())
                                                .executes(stack->testEntityAnimation(stack.getSource(),
                                                        stack.getArgument("animation",String.class),
                                                        stack.getArgument("ticker",String.class))))))
                        .then(Commands.literal("abilities")
                                .then(Commands.literal("unlock")
                                        .then(Commands.argument("ability_id",new SCAbilityArgument())
                                                .executes(css->unlockAbility(css.getSource(),css.getArgument("ability_id",String.class)))))
                                .then(Commands.literal("revoke")
                                        .then(Commands.argument("ability_id",new SCAbilityArgument())
                                                .executes(css->revokeAbility(css.getSource(),css.getArgument("ability_id",String.class))))))

        );
    }

    public static int revokeAbility(CommandSourceStack stack,String abilityId) throws CommandSyntaxException {
        AbstractAbility ability = AbilitiesRegistry.getAbilityByID(abilityId);
        if (ability != null){
            AbilityHelper.setAbilityUsable(stack.getPlayerOrException(), ability,false);
            stack.sendSuccess(()->Component.literal("Successfully revoked ability."),true);
        }else {
            stack.sendFailure(Component.literal("No ability exists with id: " + abilityId));
        }

        return 1;
    }

    public static int unlockAbility(CommandSourceStack stack,String abilityId) throws CommandSyntaxException {
        AbstractAbility ability = AbilitiesRegistry.getAbilityByID(abilityId);
        if (ability != null){
            AbilityHelper.setAbilityUsable(stack.getPlayerOrException(), ability,true);
            stack.sendSuccess(()->Component.literal("Successfully unlocked ability."),true);

        }else {
            stack.sendFailure(Component.literal("No ability exists with id: " + abilityId));
        }

        return 1;
    }

    public static int testEntityAnimation(CommandSourceStack src,String animationName,String tickerName) throws CommandSyntaxException {
        ServerPlayer player = src.getPlayerOrException();

        Vec3 i = player.position().add(0,2,0);
        Vec3 end = i.add(player.getLookAngle().multiply(20,20,20));

        HitResult result = Helpers.getEntityHitResultIgnoreBlocks(player,player.level(),i,end,(e)->true);
        if (result instanceof EntityHitResult entityHitResult && entityHitResult.getEntity() instanceof AnimatedObject object){
            if (!animationName.equals("null")) {
                Animation animation = AnimationReloadableResourceListener.INSTANCE.getAnimation(new ResourceLocation(SolarCraft.MOD_ID,animationName));
                if (animation == null) {
                    src.sendFailure(Component.literal("Animation doesn't exist: " + animationName).withStyle(ChatFormatting.RED));
                    return 0;
                }
                object.getAnimationManager().setAnimation(tickerName, new AnimationTicker.Builder(animation)
                        .replaceable(false)
                        .startFrom(0)
                        .toNullTransitionTime(10)
                        .build());
            }else{
                object.getAnimationManager().stopAnimation(tickerName);
            }
        }else{
            src.sendFailure(Component.literal("Entity not found").withStyle(ChatFormatting.RED));
        }
        return 1;
    }

    public static int revokeProgression(CommandSourceStack src,String code) throws CommandSyntaxException {
        Progression progression = Progression.getProgressionByName(code);
        ServerPlayer pl = src.getPlayerOrException();
        if (code.equals("all")){
            for (Progression a : Progression.allProgressions){
                Helpers.setProgressionCompletionStatus(a,src.getPlayerOrException(),false);
                src.sendSuccess(()->Component.translatable("solarcraft.success_revoke")
                        .append(Component.literal(" "+a.translation.getString()).withStyle(ChatFormatting.GOLD)),false);
            }
            Helpers.updateProgressionsOnClient(src.getPlayerOrException());

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
                    src.sendSuccess(()->Component.translatable("solarcraft.success_revoke")
                            .append(Component.literal(" " + progression.getProgressionCode()).withStyle(ChatFormatting.GOLD)), false);
                    Helpers.updateProgressionsOnClient(src.getPlayerOrException());
                }else{
                    src.sendFailure(Component.translatable("solarcraft.failure_revoke"));

                }
            }else {
                src.sendFailure(Component.translatable("solarcraft.failure_revoke"));
            }
        }else {
            src.sendFailure(Component.translatable("solarcraft.failure_revoke"));
        }
        Helpers.forceChunksReload(src.getPlayerOrException());
        return 0;
    }

    public static int unlockProgression(CommandSourceStack src,String code) throws CommandSyntaxException {
        Progression progression = Progression.getProgressionByName(code);
        ServerPlayer pl = src.getPlayerOrException();
        if (code.equals("all")){
            for (Progression a : Progression.allProgressions){
                Helpers.setProgressionCompletionStatus(a,src.getPlayerOrException(),true);
                src.sendSuccess(()->Component.translatable("solarcraft.success_unlock")
                        .append(Component.literal(" "+a.translation.getString()).withStyle(ChatFormatting.GOLD)),false);
            }
            Helpers.updateProgressionsOnClient(src.getPlayerOrException());

        }else if (progression != null){
            if (Helpers.canPlayerUnlock(progression,pl)){
                Helpers.setProgressionCompletionStatus(progression,pl,true);
                src.sendSuccess(()->Component.translatable("solarcraft.success_unlock")
                        .append(Component.literal(" "+ progression.getProgressionCode()).withStyle(ChatFormatting.GOLD)),false);

                Helpers.updateProgressionsOnClient(src.getPlayerOrException());


            }else {
                src.sendFailure(Component.translatable("solarcraft.failure_unlock"));
            }
        }else {
            src.sendFailure(Component.translatable("solarcraft.failure_unlock"));
        }
        Helpers.forceChunksReload(src.getPlayerOrException());
        return 0;
    }

    public static int progressionsHelp(CommandSourceStack src) throws CommandSyntaxException {
        src.sendSuccess(()->Component.translatable("solarcraft.gethelpcommand").withStyle(ChatFormatting.GOLD),false);
        for (Progression ach : Progression.allProgressions){

            src.sendSuccess(()->Component.literal(ach.translation.getString()).withStyle(ChatFormatting.GOLD)
                    .append(Component.literal(" -> "+ach.getProgressionCode())).withStyle(ChatFormatting.WHITE),false);

        }
        src.sendSuccess(()->Component.literal("all").withStyle(ChatFormatting.GOLD)
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
                stack.sendFailure(Component.literal("Unknown RE type."));
            }
        }else{
            stack.sendFailure(Component.literal("Target is null."));
        }
        return 1;
    }

    public static int fillLexicon(CommandSourceStack src) throws CommandSyntaxException {
        ServerPlayer player = src.getPlayerOrException();
        if (player.getMainHandItem().getItem() instanceof SolarLexicon){

            IItemHandler inv = player.getMainHandItem().getData(SCAttachmentTypes.LEXICON_INVENTORY);
            if (inv != null){

                    try {
                        for (int i = 0; i < AncientFragment.getAllFragments().size(); i++) {

                            AncientFragment fragment = AncientFragment.getAllFragments().get(i);
                            ItemStack stack = SCItems.INFO_FRAGMENT.get().getDefaultInstance();
                            AncientFragmentHelper.applyTagToFragment(stack, fragment);
                            inv.insertItem(i, stack, false);


                        }
                        src.sendSuccess(()->Component.literal("Filled lexicon inventory"),false);
                    }catch (Exception e){
                        e.printStackTrace();
                        src.sendFailure(Component.literal("CAUGHT FATAL ERROR DURING COMMAND, STACK TRACE PRINTED"));
                    }


            }else {
                src.sendFailure(Component.literal("Not found inventory"));
            }
        }else {
            src.sendFailure(Component.literal("Solar lexicon not found in main hand"));
        }
        return 1;
    }

    public static int constructMultiblock(CommandSourceStack src,String id) throws CommandSyntaxException{
        ServerPlayer player = src.getPlayerOrException();
        if (Multiblocks.STRUCTURES.containsKey(id)){
            Multiblocks.STRUCTURES.get(id).placeInWorld(player,player.level(),player.getOnPos().above());
            src.sendSuccess(()->Component.literal("Constructed!"),false);
        }else{
            src.sendFailure(Component.literal("Structure doesnt exist"));
        }
        return 0;
    }
}





class RestoreFragments {
    public static ArgumentBuilder<CommandSourceStack,?> register(){
        return Commands.literal("fragments")
                .requires(cs->cs.hasPermission(0))
//                .then(Commands.literal("retain").executes((cmd)->retainFragments(cmd.getSource())))
                .then(Commands.literal("unlock").then(Commands.argument("player",EntityArgument.player())
                        .then(Commands.argument("fragment_id",StringArgumentType.string())
                                .executes((cmd)->{
                                    CommandSourceStack stack = cmd.getSource();
                                    Player player = EntityArgument.getPlayer(cmd,"player");
                                    String id = cmd.getArgument("fragment_id",String.class);
                                    AncientFragment fragment = AncientFragment.getFragmentByID(id);
                                    if (fragment != null){
                                        AncientFragmentHelper.givePlayerFragment(fragment,player);
                                        stack.sendSuccess(()->Component.literal("Fragment successefuly given."),true);
                                    }else{
                                        stack.sendFailure(Component.literal("This fragments doesn't exist."));
                                    }
                                    return 0;
                                }))))
                .then(Commands.literal("revoke").then(Commands.argument("player",EntityArgument.player())
                        .then(Commands.argument("fragment_id",StringArgumentType.string())
                                .executes((cmd)->{
                                    CommandSourceStack stack = cmd.getSource();
                                    Player player = EntityArgument.getPlayer(cmd,"player");
                                    String id = cmd.getArgument("fragment_id",String.class);
                                    AncientFragment fragment = AncientFragment.getFragmentByID(id);
                                    if (fragment != null){
                                        AncientFragmentHelper.revokePlayerFragment(fragment,player);
                                        stack.sendSuccess(()->Component.literal("Fragment successfully revoked."),true);
                                    }else{
                                        stack.sendFailure(Component.literal("This fragments doesn't exist."));
                                    }
                                    return 0;
                                }))))

                .then(Commands.literal("unlockall").executes((cmd)->unlockAllFragments(cmd.getSource())))
                .then(Commands.literal("refresh").executes((cmd)->refreshFragments(cmd.getSource())));
    }

    public static int unlockAllFragments(CommandSourceStack src) throws CommandSyntaxException {
        ServerPlayer playerEntity  = src.getPlayerOrException();
        for (AncientFragment fragment : AncientFragment.getAllFragments()){
            AncientFragmentHelper.givePlayerFragment(fragment,playerEntity);
        }
        Helpers.updateFragmentsOnClient(playerEntity);
        return 0;
    }
    public static int refreshFragments(CommandSourceStack src) throws CommandSyntaxException {
        ServerPlayer playerEntity  = src.getPlayerOrException();
        for (AncientFragment fragment : AncientFragment.getAllFragments()){
            AncientFragmentHelper.revokePlayerFragment(fragment,playerEntity);
        }
        Helpers.updateFragmentsOnClient(playerEntity);
        return 0;
    }
}





