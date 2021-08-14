package com.finderfeed.solarforge.magic_items.items;

import com.finderfeed.solarforge.SolarForge;
import com.finderfeed.solarforge.config.SolarcraftConfig;
import com.finderfeed.solarforge.magic_items.item_tiers.SolarCraftToolTiers;
import com.finderfeed.solarforge.magic_items.items.vein_miner.VeinMiner;
import com.finderfeed.solarforge.registries.items.ItemsRegister;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.tags.BlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import net.minecraft.world.item.Item.Properties;



public class QualadiumPickaxe extends VeinMiner {

    public List<Item> REWARDS_LIST;

    public QualadiumPickaxe(Tier p_i48478_1_, int p_i48478_2_, float p_i48478_3_, Properties p_i48478_4_) {
        super(p_i48478_1_, p_i48478_2_, p_i48478_3_, p_i48478_4_);
    }

    //world.getServer().registryAccess().registry(Registry.ITEM_REGISTRY).get().get()

    @Override
    public boolean mineBlock(ItemStack stack, Level world, BlockState state, BlockPos pos, LivingEntity entity) {
        if (!world.isClientSide){

            if (state.is(BlockTags.BASE_STONE_OVERWORLD) &&(world.random.nextFloat() >= 0.8) ){
                initList(world);
                if (!REWARDS_LIST.isEmpty()) {
                    ItemEntity ent = new ItemEntity(world, pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5,
                            REWARDS_LIST.get(world.random.nextInt(REWARDS_LIST.size())).getDefaultInstance());
                    world.addFreshEntity(ent);
                }
            }
        }


        return super.mineBlock(stack, world, state, pos, entity);
    }



    @Override
    public void appendHoverText(ItemStack p_77624_1_, @Nullable Level p_77624_2_, List<Component> p_77624_3_, TooltipFlag p_77624_4_) {
        p_77624_3_.add(new TranslatableComponent("solarforge.qualadium_pickaxe").withStyle(ChatFormatting.GOLD));
        super.appendHoverText(p_77624_1_, p_77624_2_, p_77624_3_, p_77624_4_);
    }


    private void initList(Level level){
        if (REWARDS_LIST == null){
            REWARDS_LIST = new ArrayList<>();
            List<String> ids = SolarcraftConfig.PICKAXE_TREASURES.get();
            Optional<? extends Registry<Item>> a  = level.registryAccess().registry(Registry.ITEM_REGISTRY);
            if (a.isPresent()){
                Registry<Item> reg = a.get();
                ids.forEach((string)->{
                    Item item = reg.get(new ResourceLocation(string));
                    if (item != null){
                        REWARDS_LIST.add(item);
                    }
                });
            }
        }
    }
}
