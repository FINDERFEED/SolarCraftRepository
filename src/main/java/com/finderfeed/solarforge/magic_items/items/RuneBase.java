package com.finderfeed.solarforge.magic_items.items;


import com.finderfeed.solarforge.magic_items.blocks.blockentities.RuneEnergyPylonTile;
import com.finderfeed.solarforge.magic_items.items.solar_lexicon.unlockables.AncientFragment;
import com.finderfeed.solarforge.magic_items.items.solar_lexicon.unlockables.ProgressionHelper;
import com.finderfeed.solarforge.registries.items.ItemsRegister;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class RuneBase extends Item {

    private int energyPerRune = 10;

    public RuneBase(Properties p_i48487_1_) {
        super(p_i48487_1_);
    }

    @Override
    public ActionResultType useOn(ItemUseContext ctx) {
        World world = ctx.getLevel();
        ItemStack stack = ctx.getItemInHand();
        BlockPos blockPos = ctx.getClickedPos();
        Hand hand = ctx.getHand();
        PlayerEntity player = ctx.getPlayer();
        TileEntity tileEntity = world.getBlockEntity(blockPos);
        if (ProgressionHelper.RUNES_MAP == null){
            ProgressionHelper.initRunesMap();
        }
        if (!world.isClientSide && (tileEntity instanceof RuneEnergyPylonTile) && (player != null) ){

            RuneEnergyPylonTile tile = (RuneEnergyPylonTile) tileEntity;
            System.out.println(tile.getEnergyPerTick());
            if (tile.getCurrentEnergy() >= energyPerRune){
                int maximumRunesCount = (int)Math.floor(tile.getCurrentEnergy()/energyPerRune);
                int count = stack.getCount();
                if (count <= maximumRunesCount){
                    player.setItemInHand(hand,new ItemStack(ProgressionHelper.RUNES_MAP.get(tile.getEnergyType()),count));
                    tile.setCurrentEnergy(tile.getCurrentEnergy() - energyPerRune*count);
                }else{
                    ItemStack stack1 = player.getItemInHand(hand);
                    player.setItemInHand(hand,new ItemStack(stack1.getItem(),stack1.getCount()-maximumRunesCount));
                    ItemStack frag = new ItemStack(ProgressionHelper.RUNES_MAP.get(tile.getEnergyType()),maximumRunesCount);
                    ItemEntity entity = new ItemEntity(player.level,player.getX(),player.getY()+0.3f,player.getZ(),frag);
                    world.addFreshEntity(entity);

                    tile.setCurrentEnergy(tile.getCurrentEnergy() - maximumRunesCount*energyPerRune);
                }



            }
        }
        return super.useOn(ctx);
    }
}
