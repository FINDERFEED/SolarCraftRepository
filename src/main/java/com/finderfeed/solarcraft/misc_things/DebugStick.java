package com.finderfeed.solarcraft.misc_things;

import com.finderfeed.solarcraft.SolarCraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.InputEvent;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Consumer;

public abstract class DebugStick extends Item {

    private Map<String, Consumer<UseOnContext>> useOnActions = new LinkedHashMap<>();
    private Map<String,Consumer<UseContext>> useActions = new LinkedHashMap<>();

    private List<String> allActionIds = new ArrayList<>();



    public DebugStick(Properties props) {
        super(props);
        this.getUseActions(useActions);
        this.getUseOnActions(useOnActions);
        this.allActionIds.addAll(useActions.keySet());
        this.allActionIds.addAll(useOnActions.keySet());
    }


    @Override
    public InteractionResult useOn(UseOnContext ctx) {
        if (this.getCurrentActionId(ctx.getItemInHand()) != null) {
            var action = useOnActions.get(this.getCurrentActionId(ctx.getItemInHand()));
            if (action != null){
                action.accept(ctx);
            }
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (this.getCurrentActionId(player.getItemInHand(hand)) != null) {
            var action = useActions.get(this.getCurrentActionId(player.getItemInHand(hand)));
            if (action != null){
                action.accept(new UseContext(level,player,hand));
            }
        }
        return InteractionResultHolder.success(player.getItemInHand(hand));
    }

    @Nullable
    public String getCurrentActionId(ItemStack item){
        int currentAction = this.getCurrentActionIndex(item);
        if (!allActionIds.isEmpty() && currentAction < allActionIds.size()){
            return allActionIds.get(currentAction);
        }else{
            return null;
        }
    }

    public int getCurrentActionIndex(ItemStack item){
        return item.getOrCreateTag().getInt("actionIndex");
    }

    public void setCurrentActionIndex(ItemStack item,int id){
        item.getOrCreateTag().putInt("actionIndex",id);
    }

    public abstract void getUseActions(Map<String,Consumer<UseContext>> useOnActions);
    public abstract void getUseOnActions(Map<String,Consumer<UseOnContext>> useOnActions);

    public void switchMode(ItemStack item,boolean forward){
        int currentAction = this.getCurrentActionIndex(item);
        if (forward){
            this.setCurrentActionIndex(item,(currentAction + 1) % allActionIds.size());
        }else{
            if (currentAction - 1 < 0){
                this.setCurrentActionIndex(item,allActionIds.size() - 1);
            }else{
                this.setCurrentActionIndex(item,currentAction - 1);
            }
        }
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int p_41407_, boolean held) {
        super.inventoryTick(stack, level, entity, p_41407_, held);
        if (entity instanceof Player player && held && this.getCurrentActionId(stack) != null) {
            player.displayClientMessage(Component.literal(this.getCurrentActionId(stack)),true);
        }
    }

    public record UseContext(Level level, Player player, InteractionHand hand){};

}
