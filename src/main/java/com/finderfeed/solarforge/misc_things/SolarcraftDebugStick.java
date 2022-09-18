package com.finderfeed.solarforge.misc_things;

import com.finderfeed.solarforge.helpers.multiblock.MultiblockStructure;
import com.finderfeed.solarforge.helpers.multiblock.StructurePatternExporter;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

public class SolarcraftDebugStick extends Item {
    public SolarcraftDebugStick(Properties p_41383_) {
        super(p_41383_);
    }

    private BlockPos first;
    private BlockPos second;

    @Override
    public void inventoryTick(ItemStack item, Level world, Entity player, int slot, boolean held) {
        super.inventoryTick(item, world, player, slot, held);
    }

    @Override
    public InteractionResult useOn(UseOnContext ctx) {
        Level world = ctx.getLevel();
        BlockPos pos = ctx.getClickedPos();
        if (world.isClientSide){
            if (first == null){
                first = pos;
            }else if (second == null){
                second = pos;
            }else{
//                MultiblockStructure structure = new MultiblockStructure.Builder()
//                        .setId("aboba")
//                        .setMainChar('u')
//                        .setPattern(new String[][]{{"qqqqqw", "qqqqqw", "qqeqqw", "qqqqqw", "qqqqqw"}, {"rrtrrr", "rrrrrr", "yrurir", "rrrrrr", "rrorrp"}})
//                        .put('q',"solarforge:solar_stone_bricks")
//                        .put('t',"solarforge:solar_stone_stairs[facing=north,half=bottom,shape=straight,waterlogged=false]")
//                        .put('i',"solarforge:solar_stone_stairs[facing=east,half=bottom,shape=straight,waterlogged=false]")
//                        .put('e',"solarforge:chiseled_solar_stone")
//                        .put('w',"solarforge:solar_stone_collumn")
//                        .put('r',"minecraft:air")
//                        .put('y',"solarforge:solar_stone_stairs[facing=west,half=bottom,shape=straight,waterlogged=false]")
//                        .put('o',"solarforge:solar_stone_stairs[facing=south,half=bottom,shape=straight,waterlogged=false]")
//                        .put('u',"solarforge:illidium_block")
//                        .put('p',"solarforge:solar_core_block")
//                        .build();
//                System.out.println(structure.check(world,first.offset(2,1,2),true));
                StructurePatternExporter.export(world,first,second);
                first = null;
                second = null;
            }
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
//        if (!world.isClientSide){
//            ItemStack stack = player.getItemInHand(hand);
//            boolean mode = stack.getOrCreateTagElement("pylon_mode").getBoolean("isCyclingPylons");
//            if (mode){
//                stack.getOrCreateTagElement("pylon_mode").putBoolean("isCyclingPylons",false);
//            }else{
//                stack.getOrCreateTagElement("pylon_mode").putBoolean("isCyclingPylons",true);
//            }
//            SummoningProjectile bolt = new SummoningProjectile(world,SolarcraftEntityTypes.SHADOW_ZOMBIE.get(),
//                    43,0,87);
//            bolt.setPos(player.position().add(0,2,0));
//            bolt.setDeltaMovement(player.getLookAngle());
//            world.addFreshEntity(bolt);
//        }
        return super.use(world, player, hand);
    }
}
