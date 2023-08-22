package com.finderfeed.solarcraft.content.items;

import com.finderfeed.solarcraft.helpers.Helpers;
import com.finderfeed.solarcraft.local_library.helpers.CompoundNBTHelper;
import com.finderfeed.solarcraft.content.blocks.primitive.InscriptionStone;
import com.finderfeed.solarcraft.content.items.primitive.solacraft_item_classes.SolarcraftItem;
import com.finderfeed.solarcraft.content.items.solar_lexicon.progressions.Progression;
import com.finderfeed.solarcraft.content.items.solar_lexicon.unlockables.AncientFragment;
import com.finderfeed.solarcraft.misc_things.RunicEnergy;
import com.finderfeed.solarcraft.registries.blocks.SCBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Supplier;


public class RuneItem extends SolarcraftItem {

    private static final String TAG_PROGRESS = "progress";
    private static final String TAG_POSITION = "progress";
    private static final String TAG_OFFLINE_TIME = "offline_time";
    private static final String TAG = "progress";
    public final RunicEnergy.Type type;
    public RuneItem(Properties props,RunicEnergy.Type type, Supplier<AncientFragment> fragmentSupplier) {
        super(props,fragmentSupplier);
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

        if (!world.isClientSide && (Helpers.hasPlayerCompletedProgression(Progression.SOLAR_RUNE,pl))){

            if (state.hasProperty(InscriptionStone.PROP) && (state.getValue(InscriptionStone.PROP) == RunicEnergy.Type.NONE) ) {
                if (hand == InteractionHand.MAIN_HAND) {
//                    if (stack.getCount() == 1) {

                        CompoundTag tag = putTag(stack);
                        tag.putInt(TAG_OFFLINE_TIME,100);
                        int progress = tag.getInt(TAG_PROGRESS);
                        BlockPos position = CompoundNBTHelper.getBlockPos(TAG_POSITION,tag);
                        if (Helpers.equalsBlockPos(pos,position)){
                            int time = 200;
                            if (pl.isCreative()){
                                time = 1;
                            }
                            if (progress <= time){
                                tag.putInt(TAG_PROGRESS,progress+1);
                                if (progress % 5 == 0){
                                    world.levelEvent(null, 2001, pos, Block.getId(state));
                                    world.playSound(null,pos.getX(),pos.getY(),pos.getZ(), SoundEvents.STONE_BREAK, SoundSource.BLOCKS,1,1);
                                }
                            }else{
                                Helpers.fireProgressionEvent(pl, Progression.PYLON_INSCRIPTION);
                                world.playSound(null,pos.getX(),pos.getY(),pos.getZ(), SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.BLOCKS,1,1);
                                world.setBlock(pos, SCBlocks.INSCRIPTION_STONE.get().defaultBlockState().setValue(InscriptionStone.PROP,type), 3);
//                                pl.setItemInHand(hand,ItemStack.EMPTY);

                                ItemStack copy = stack.copy();
                                copy.shrink(1);
                                pl.setItemInHand(hand,copy);
                            }
                        }else{
                            CompoundNBTHelper.writeBlockPos(TAG_POSITION,pos,tag);
                            tag.putInt(TAG_PROGRESS,0);
                        }


//                    }
                }
            }
        }
        return InteractionResult.SUCCESS;
    }



    private CompoundTag putTag(ItemStack stack){
        return stack.getOrCreateTagElement(TAG);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level world, Entity entity, int slot, boolean selected) {
        if (!world.isClientSide){
            CompoundTag element = stack.getTagElement(TAG);
            if (element != null){
                int time = element.getInt(TAG_OFFLINE_TIME);
                if (time <= 0){
                    stack.removeTagKey(TAG);
                }else{
                    element.putInt(TAG_OFFLINE_TIME,time-1);
                }
            }
        }
        super.inventoryTick(stack, world, entity, slot, selected);
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return slotChanged;
    }
}
