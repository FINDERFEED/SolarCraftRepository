package com.finderfeed.solarforge.magic_items.items;


import com.finderfeed.solarforge.misc_things.IImbuableItem;
import net.minecraft.world.item.Item;

public class RuneBase extends Item implements IImbuableItem {

    private int energyPerRune = 10;

    public RuneBase(Properties p_i48487_1_) {
        super(p_i48487_1_);
    }

//    @Override
//    public InteractionResult useOn(UseOnContext ctx) {
//        Level world = ctx.getLevel();
//        ItemStack stack = ctx.getItemInHand();
//        BlockPos blockPos = ctx.getClickedPos();
//        InteractionHand hand = ctx.getHand();
//        Player player = ctx.getPlayer();
//        BlockEntity tileEntity = world.getBlockEntity(blockPos);
//        if (ProgressionHelper.RUNES_MAP == null){
//            ProgressionHelper.initRunesMap();
//        }
//        if (!world.isClientSide && (tileEntity instanceof RuneEnergyPylonTile) && (player != null) ){
//
//            RuneEnergyPylonTile tile = (RuneEnergyPylonTile) tileEntity;
//
//            if (tile.getCurrentEnergy() >= energyPerRune){
//                Helpers.fireProgressionEvent(player, Achievement.SOLAR_RUNE);
//                int maximumRunesCount = (int)Math.floor(tile.getCurrentEnergy()/energyPerRune);
//                int count = stack.getCount();
//                if (count <= maximumRunesCount){
//                    player.setItemInHand(hand,new ItemStack(ProgressionHelper.RUNES_MAP.get(tile.getEnergyType()),count));
//                    tile.setCurrentEnergy(tile.getCurrentEnergy() - energyPerRune*count);
//                }else{
//                    Helpers.fireProgressionEvent(player, Achievement.SOLAR_RUNE);
//                    ItemStack stack1 = player.getItemInHand(hand);
//                    player.setItemInHand(hand,new ItemStack(stack1.getItem(),stack1.getCount()-maximumRunesCount));
//                    ItemStack frag = new ItemStack(ProgressionHelper.RUNES_MAP.get(tile.getEnergyType()),maximumRunesCount);
//                    ItemEntity entity = new ItemEntity(player.level,player.getX(),player.getY()+0.3f,player.getZ(),frag);
//                    world.addFreshEntity(entity);
//
//                    tile.setCurrentEnergy(tile.getCurrentEnergy() - maximumRunesCount*energyPerRune);
//                }
//
//
//
//            }
//        }
//        return super.useOn(ctx);
//    }

    @Override
    public double getCost() {
        return 10;
    }

    @Override
    public int getImbueTime() {
        return 300;
    }
}
