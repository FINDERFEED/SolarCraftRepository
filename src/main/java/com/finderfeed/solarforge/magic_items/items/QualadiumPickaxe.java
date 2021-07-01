package com.finderfeed.solarforge.magic_items.items;

import com.finderfeed.solarforge.SolarForge;
import com.finderfeed.solarforge.magic_items.item_tiers.SolarCraftToolTiers;
import com.finderfeed.solarforge.magic_items.items.vein_miner.VeinMiner;
import com.finderfeed.solarforge.registries.items.ItemsRegister;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class QualadiumPickaxe extends VeinMiner {

    public final Item[] REWARDS_LIST = {Items.GOLD_NUGGET,Items.IRON_NUGGET,Items.DIAMOND, Items.BONE,Items.BONE_MEAL};

    public QualadiumPickaxe(IItemTier p_i48478_1_, int p_i48478_2_, float p_i48478_3_, Properties p_i48478_4_) {
        super(p_i48478_1_, p_i48478_2_, p_i48478_3_, p_i48478_4_);
    }

    @Override
    public boolean mineBlock(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity entity) {
        if (!world.isClientSide){
            if (state.getBlock().is(BlockTags.BASE_STONE_OVERWORLD) &&(world.random.nextFloat() >= 0.8) ){
                ItemEntity ent = new ItemEntity(world,pos.getX()+0.5,pos.getY(),pos.getZ()+0.5,
                        REWARDS_LIST[world.random.nextInt(5)].getDefaultInstance());
                world.addFreshEntity(ent);
            }
        }


        return super.mineBlock(stack, world, state, pos, entity);
    }



    @Override
    public void appendHoverText(ItemStack p_77624_1_, @Nullable World p_77624_2_, List<ITextComponent> p_77624_3_, ITooltipFlag p_77624_4_) {
        p_77624_3_.add(new TranslationTextComponent("solarforge.qualadium_pickaxe").withStyle(TextFormatting.GOLD));
        super.appendHoverText(p_77624_1_, p_77624_2_, p_77624_3_, p_77624_4_);
    }


}
