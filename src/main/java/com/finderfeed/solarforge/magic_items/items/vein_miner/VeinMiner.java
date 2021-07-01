package com.finderfeed.solarforge.magic_items.items.vein_miner;

import com.finderfeed.solarforge.Helpers;
import com.finderfeed.solarforge.SolarForge;

import com.finderfeed.solarforge.magic_items.item_tiers.SolarCraftToolTiers;
import com.finderfeed.solarforge.misc_things.ManaConsumer;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.OreBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.renderer.vertex.VertexFormatElement;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.DrawHighlightEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nullable;
import java.util.*;

public class VeinMiner extends PickaxeItem implements ManaConsumer {



    public VeinMiner(IItemTier p_i48478_1_, int p_i48478_2_, float p_i48478_3_, Properties p_i48478_4_) {
        super(p_i48478_1_, p_i48478_2_, p_i48478_3_, p_i48478_4_);

    }

    @Override
    public boolean mineBlock(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity entity) {

        if (!world.isClientSide && entity.isCrouching() && entity instanceof PlayerEntity) {
            List<BlockPos> posList = new ArrayList<>();
            populateList(pos, world, posList,0);
            for (BlockPos a : posList) {
                if (Helpers.canCast((PlayerEntity) entity,getManacost())) {
                    Helpers.spendMana((PlayerEntity) entity,getManacost());
                    Block.dropResources(world.getBlockState(a), world, a, world.getBlockEntity(a), entity, stack);
                    world.destroyBlock(a, false);
                }

            }
        }else if(!world.isClientSide && !entity.isCrouching() && entity instanceof PlayerEntity) {
            if (Helpers.canCast((PlayerEntity) entity,getManacost())){
                Helpers.spendMana((PlayerEntity) entity,getManacost());
            }
        }
//        stack.setDamageValue(-stack.getMaxDamage());

        return super.mineBlock(stack,world,state,pos,entity);
    }



    public static void populateList(BlockPos pos, World world, List<BlockPos> posList,int depth){
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
    public void appendHoverText(ItemStack p_77624_1_, @Nullable World p_77624_2_, List<ITextComponent> p_77624_3_, ITooltipFlag p_77624_4_) {
        p_77624_3_.add(new TranslationTextComponent("solarforge.veinminer").withStyle(TextFormatting.GOLD));
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
