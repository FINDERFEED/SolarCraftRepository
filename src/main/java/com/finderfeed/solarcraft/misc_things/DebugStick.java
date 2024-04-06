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
import java.lang.annotation.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Consumer;

public abstract class DebugStick extends Item {

    private Map<String, Consumer<UseOnContext>> useOnActions = new LinkedHashMap<>();
    private Map<String,Consumer<UseContext>> useActions = new LinkedHashMap<>();

    private List<String> allActionIds = new ArrayList<>();



    public DebugStick(Properties props) {
        super(props);
        this.collectAllActions();
        this.allActionIds.addAll(useActions.keySet());
        this.allActionIds.addAll(useOnActions.keySet());
    }

    protected void collectAllActions(){
        Method[] methods = this.getClass().getMethods();
        for (Method method : methods){
            Annotation annotation;
            if ((annotation = method.getAnnotation(UseAction.class)) != null){
                this.useActions.put(((UseAction)annotation).value(),(ctx)->{
                    try {
                        method.invoke(this,ctx);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    } catch (InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                });
            }else if ((annotation = method.getAnnotation(UseOnAction.class)) != null){
                this.useOnActions.put(((UseOnAction)annotation).value(),(ctx)->{
                    try {
                        method.invoke(this,ctx);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    } catch (InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        }
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

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public @interface UseAction {

        String value();

    }
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public @interface UseOnAction {
        String value();
    }
}
