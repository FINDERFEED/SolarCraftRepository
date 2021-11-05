package com.finderfeed.solarforge.magic_items.items;

import com.finderfeed.solarforge.Helpers;
import com.finderfeed.solarforge.for_future_library.helpers.CompoundNBTHelper;
import com.finderfeed.solarforge.magic_items.blocks.primitive.InscriptionStone;
import com.finderfeed.solarforge.magic_items.items.solar_lexicon.achievements.Progression;
import com.finderfeed.solarforge.misc_things.RunicEnergy;
import com.finderfeed.solarforge.registries.blocks.BlocksRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.util.Constants;


public class RuneItem extends Item {

    private static final String TAG_PROGRESS = "progress";
    private static final String TAG_POSITION = "progress";
    private static final String TAG = "progress";
    public final RunicEnergy.Type type;
    public RuneItem(Properties p_41383_,RunicEnergy.Type type) {
        super(p_41383_);
        this.type = type;
    }


    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level world = context.getLevel();
        BlockPos pos = context.getClickedPos();
        Player pl = context.getPlayer();
        ItemStack stack = context.getItemInHand();
        InteractionHand hand=  context.getHand();
        BlockState state = world.getBlockState(pos);

        if (!world.isClientSide && (Helpers.hasPlayerUnlocked(Progression.SOLAR_RUNE,pl))){

            if (state.hasProperty(InscriptionStone.PROP) && (state.getValue(InscriptionStone.PROP) == RunicEnergy.Type.NONE) ) {
                if (hand == InteractionHand.MAIN_HAND) {
                    if (stack.getCount() == 1) {
                        CompoundTag tag = putTag(stack);
                        int progress = tag.getInt(TAG_PROGRESS);
                        BlockPos position = CompoundNBTHelper.getBlockPos(TAG_POSITION,tag);
                        if (Helpers.equalsBlockPos(pos,position)){
                            if (progress <= 300){
                                tag.putInt(TAG_PROGRESS,progress+1);
                                if (progress % 5 == 0){
                                    world.levelEvent(null, 2001, pos, Block.getId(state));
                                    world.playSound(null,pos.getX(),pos.getY(),pos.getZ(), SoundEvents.STONE_BREAK, SoundSource.BLOCKS,1,1);
                                }
                            }else{
                                Helpers.fireProgressionEvent(pl, Progression.PYLON_INSCRIPTION);
                                world.playSound(null,pos.getX(),pos.getY(),pos.getZ(), SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.BLOCKS,1,1);
                                world.setBlock(pos, BlocksRegistry.INSCRIPTION_STONE.get().defaultBlockState().setValue(InscriptionStone.PROP,type), Constants.BlockFlags.DEFAULT);
                                pl.setItemInHand(hand,ItemStack.EMPTY);
                            }
                        }else{
                            CompoundNBTHelper.writeBlockPos(TAG_POSITION,pos,tag);
                            tag.putInt(TAG_PROGRESS,0);
                        }


                    }
                }
            }
        }
        return InteractionResult.SUCCESS;
    }



    private CompoundTag putTag(ItemStack stack){
        return stack.getOrCreateTagElement(TAG);
    }


    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return slotChanged;
    }
}
