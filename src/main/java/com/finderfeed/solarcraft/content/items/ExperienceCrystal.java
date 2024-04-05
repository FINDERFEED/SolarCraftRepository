package com.finderfeed.solarcraft.content.items;

import com.finderfeed.solarcraft.SolarCraftTags;
import com.finderfeed.solarcraft.content.items.primitive.solacraft_item_classes.SolarcraftItem;
import com.finderfeed.solarcraft.content.items.solar_lexicon.unlockables.AncientFragment;
import com.finderfeed.solarcraft.registries.items.SCItems;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Supplier;


public class ExperienceCrystal extends SolarcraftItem {
    public ExperienceCrystal(Properties p_i48487_1_, Supplier<AncientFragment> fragmentSupplier) {
        super(p_i48487_1_,fragmentSupplier);
    }

    public static boolean consumeExperience(Player player,int value){
        Inventory inv = player.getInventory();
        int slot = findExperienceCrystal(inv);
        if (slot != -1){
            ItemStack stack = inv.getItem(slot);
            CompoundTag tag = stack.getTagElement(SolarCraftTags.EXPERIENCE_CRYSTAL_SUBTAG);
            if (tag != null){
                tag.putInt(SolarCraftTags.EXPERIENCE_CRYSTAL_CONTAINED_XP,tag.getInt(SolarCraftTags.EXPERIENCE_CRYSTAL_CONTAINED_XP)+value);
            }else{
                stack.getOrCreateTagElement(SolarCraftTags.EXPERIENCE_CRYSTAL_SUBTAG).putInt(SolarCraftTags.EXPERIENCE_CRYSTAL_CONTAINED_XP,value);
            }
            return true;
        }
        return false;
    }

    private static int findExperienceCrystal(Inventory inv){

        for (int i = 0; i < inv.getContainerSize();i++){
            if (inv.getItem(i).getItem() == SCItems.EXPERIENCE_CRYSTAL.get()){
                return i;
            }
        }
        return -1;
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level p_41432_, Player p_41433_, InteractionHand p_41434_) {
        releaseXP(p_41432_,p_41433_,p_41434_);
        return super.use(p_41432_, p_41433_, p_41434_);
    }

    private void releaseXP(Level world,Player player,InteractionHand hand){
        if (!world.isClientSide){
            ItemStack stack = player.getItemInHand(hand);
            CompoundTag tag = stack.getTagElement(SolarCraftTags.EXPERIENCE_CRYSTAL_SUBTAG);
            if (tag != null){
                int total = tag.getInt(SolarCraftTags.EXPERIENCE_CRYSTAL_CONTAINED_XP);
                player.giveExperiencePoints(total);
                tag.putInt(SolarCraftTags.EXPERIENCE_CRYSTAL_CONTAINED_XP,0);
                if (total != 0) {
                    ((ServerLevel) world).playSound(null, player, SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.AMBIENT, 0.25f, 1f);
                }

            }
        }
    }

    private void spawnOrb(Level world,Player player,int total){
        ExperienceOrb orb = new ExperienceOrb(world,player.getX(),player.getY()+0.3,player.getZ(),total);
        world.addFreshEntity(orb);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level p_41422_, List<Component> list, TooltipFlag p_41424_) {
        list.add(Component.translatable("solarcraft.experience_crystal_lore").withStyle(ChatFormatting.GOLD));
        CompoundTag tag = stack.getTagElement(SolarCraftTags.EXPERIENCE_CRYSTAL_SUBTAG);
        if (tag != null){
            int total = tag.getInt(SolarCraftTags.EXPERIENCE_CRYSTAL_CONTAINED_XP);
            list.add(Component.translatable("solarcraft.experience_crystal_lore2").withStyle(ChatFormatting.GOLD)
                    .append(Component.literal(" "+total).withStyle(ChatFormatting.GREEN)));
        }
        super.appendHoverText(stack, p_41422_, list, p_41424_);
    }
}


