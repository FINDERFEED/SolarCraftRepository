package com.finderfeed.solarcraft.content.items;

import com.finderfeed.solarcraft.config.SolarcraftConfig;
import com.finderfeed.solarcraft.content.items.solar_lexicon.unlockables.AncientFragment;
import com.finderfeed.solarcraft.content.items.vein_miner.IllidiumPickaxe;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.tags.BlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;


public class QualadiumPickaxe extends IllidiumPickaxe {

    public List<Item> REWARDS_LIST;

    public QualadiumPickaxe(Tier p_i48478_1_, int p_i48478_2_, float p_i48478_3_, Properties p_i48478_4_, Supplier<AncientFragment> fragmentSupplier) {
        super(p_i48478_1_, p_i48478_2_, p_i48478_3_, p_i48478_4_,fragmentSupplier);
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
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> components, TooltipFlag p_77624_4_) {
        components.add(Component.translatable("solarcraft.qualadium_pickaxe").withStyle(ChatFormatting.GOLD));
        super.appendHoverText(stack, world, components, p_77624_4_);
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
