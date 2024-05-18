package com.finderfeed.solarcraft.content.items;

import com.finderfeed.solarcraft.content.items.primitive.solacraft_item_classes.SolarcraftItem;
import com.finderfeed.solarcraft.content.items.solar_lexicon.unlockables.AncientFragment;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Supplier;

public class EnderRadarItem extends SolarcraftItem {
    public EnderRadarItem(Properties p_41383_, Supplier<AncientFragment> fragment) {
        super(p_41383_, fragment);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (!level.isClientSide){
            ItemStack item = player.getItemInHand(hand);
            setState(item,!isTurnedOn(item));
        }
        return super.use(level, player, hand);
    }


    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> component, TooltipFlag flag) {
        super.appendHoverText(stack, level, component, flag);
        component.add(Component.translatable("solarcraft.item.ender_radar",isTurnedOn(stack)));
    }

    @Override
    public ItemStack getDefaultInstance() {
        ItemStack stack = super.getDefaultInstance();
        setState(stack,true);
        return stack;
    }

    @Override
    public boolean isFoil(ItemStack item) {
        return isTurnedOn(item);
    }

    public static boolean isTurnedOn(ItemStack item){
        return item.getOrCreateTag().getBoolean("turned_on");
    }

    public static void setState(ItemStack item,boolean state){
        item.getOrCreateTag().putBoolean("turned_on",state);
    }
}
