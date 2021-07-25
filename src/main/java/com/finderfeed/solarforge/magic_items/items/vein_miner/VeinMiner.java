package com.finderfeed.solarforge.magic_items.items.vein_miner;

import com.finderfeed.solarforge.Helpers;
import com.finderfeed.solarforge.SolarForge;

import com.finderfeed.solarforge.magic_items.item_tiers.SolarCraftToolTiers;
import com.finderfeed.solarforge.misc_things.ManaConsumer;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.OreBlock;

import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;


import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.Level;


import javax.annotation.Nullable;
import java.util.*;

import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tier;

public class VeinMiner extends PickaxeItem implements ManaConsumer {



    public VeinMiner(Tier p_i48478_1_, int p_i48478_2_, float p_i48478_3_, Properties p_i48478_4_) {
        super(p_i48478_1_, p_i48478_2_, p_i48478_3_, p_i48478_4_);

    }

    @Override
    public boolean mineBlock(ItemStack stack, Level world, BlockState state, BlockPos pos, LivingEntity entity) {

        if (!world.isClientSide && entity.isCrouching() && entity instanceof Player) {
            List<BlockPos> posList = new ArrayList<>();
            populateList(pos, world, posList,0);
            for (BlockPos a : posList) {
                if (Helpers.canCast((Player) entity,getManacost())) {
                    Helpers.spendMana((Player) entity,getManacost());
                    Block.dropResources(world.getBlockState(a), world, a, world.getBlockEntity(a), entity, stack);
                    world.destroyBlock(a, false);
                }

            }
        }else if(!world.isClientSide && !entity.isCrouching() && entity instanceof Player) {
            if (Helpers.canCast((Player) entity,getManacost())){
                Helpers.spendMana((Player) entity,getManacost());
            }
        }
//        stack.setDamageValue(-stack.getMaxDamage());

        return super.mineBlock(stack,world,state,pos,entity);
    }



    public static void populateList(BlockPos pos, Level world, List<BlockPos> posList,int depth){
        if (depth != 20) {
            List<BlockPos> poslis = new ArrayList<>();
            poslis.add(pos.above());
            poslis.add(pos.below());
            poslis.add(pos.south());
            poslis.add(pos.east());
            poslis.add(pos.north());
            poslis.add(pos.west());
            posList.add(pos);
            poslis.removeIf(posList::contains);
            for (BlockPos a : poslis) {
                if (world.getBlockState(a).getBlock() instanceof OreBlock) {
                    posList.add(a);
                    populateList(a, world, posList, depth + 1);
                }
            }
        }
    }


    @Override
    public double getManacost() {
        return 3.5f;
    }

    @Override
    public void appendHoverText(ItemStack p_77624_1_, @Nullable Level p_77624_2_, List<Component> p_77624_3_, TooltipFlag p_77624_4_) {
        p_77624_3_.add(new TranslatableComponent("solarforge.veinminer").withStyle(ChatFormatting.GOLD));
        super.appendHoverText(p_77624_1_, p_77624_2_, p_77624_3_, p_77624_4_);
    }

    @Override
    public boolean isEnchantable(ItemStack p_77616_1_) {
        return true;
    }
}
//@Mod.EventBusSubscriber(modid = "solarforge",bus = Mod.EventBusSubscriber.Bus.FORGE,value = Dist.CLIENT)
//class RenderHighlight{
//
//    @SubscribeEvent
//    public static void renderHighlight(final DrawHighlightEvent.HighlightBlock event){
//        BlockPos pos = event.getTarget().getBlockPos();
//        ClientPlayerEntity entity = Minecraft.getInstance().player;
//        if (entity.getMainHandItem().getItem() instanceof  VeinMiner){
//            if (entity.level.getBlockState(pos).getBlock() instanceof OreBlock){
//                List<BlockPos> posList = new ArrayList<>();
//                VeinMiner.populateList(pos,entity.level,posList,15);
//                GL11.glPushMatrix();
//                GL11.glPushAttrib(GL11.GL_ALL_ATTRIB_BITS);
//                GL11.glTranslated(pos.getX(),pos.getY(),pos.getZ());
//                GL11.glLineWidth(2);
//
//
//                GL11.glBegin(GL11.GL_LINES);
//                GL11.glColor4f(1,1,1,1);
//                GL11.glVertex3d(0,0,0);
//                GL11.glVertex3d(1,0,0);
//                GL11.glVertex3d(1,1,0);
//                GL11.glVertex3d(0,1,0);
//
//                GL11.glEnd();
//                GL11.glPopAttrib();
//                GL11.glPopMatrix();
//                for (BlockPos a : posList){
//
//                }
//            }
//        }
//    }
//}
