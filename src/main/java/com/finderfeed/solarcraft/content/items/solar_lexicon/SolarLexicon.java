package com.finderfeed.solarcraft.content.items.solar_lexicon;

import com.finderfeed.solarcraft.helpers.Helpers;
import com.finderfeed.solarcraft.content.items.solar_lexicon.unlockables.AncientFragment;
import com.finderfeed.solarcraft.content.items.solar_lexicon.unlockables.AncientFragmentHelper;
import com.finderfeed.solarcraft.packet_handler.SCPacketHandler;
import com.finderfeed.solarcraft.content.items.solar_lexicon.progressions.progression_tree.ProgressionTree;
import com.finderfeed.solarcraft.content.items.solar_lexicon.packets.OpenScreenPacket;
import com.finderfeed.solarcraft.content.items.solar_lexicon.packets.UpdateInventoryPacket;
import com.finderfeed.solarcraft.registries.items.SolarcraftItems;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.level.Level;


import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkHooks;

import java.util.ArrayList;
import java.util.List;

public class SolarLexicon extends Item {

    public Screen currentSavedScreen = null;

    public SolarLexicon(Properties p_i48487_1_) {
        super(p_i48487_1_);
    }


    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player pe, InteractionHand hand) {
        if (!world.isClientSide && hand.equals(InteractionHand.MAIN_HAND)){

            if (!AncientFragmentHelper.doPlayerHasFragment(pe,AncientFragment.LEXICON)) {
                ItemStack frag = SolarcraftItems.INFO_FRAGMENT.get().getDefaultInstance();
                AncientFragmentHelper.applyTagToFragment(frag, AncientFragment.LEXICON);
                ItemEntity entity = new ItemEntity(pe.level, pe.getX(), pe.getY() + 0.3f, pe.getZ(), frag);
                AncientFragmentHelper.givePlayerFragment(AncientFragment.LEXICON, pe);
                pe.level.addFreshEntity(entity);

            }

            if (!AncientFragmentHelper.doPlayerHasFragment(pe,AncientFragment.FRAGMENT)){
                ItemStack frag = SolarcraftItems.INFO_FRAGMENT.get().getDefaultInstance();
                AncientFragmentHelper.applyTagToFragment(frag, AncientFragment.FRAGMENT);
                ItemEntity entity = new ItemEntity(pe.level,pe.getX(),pe.getY()+0.3f,pe.getZ(),frag);
                AncientFragmentHelper.givePlayerFragment(AncientFragment.FRAGMENT,pe);
                pe.level.addFreshEntity(entity);
            }

            if (!AncientFragmentHelper.doPlayerHasFragment(pe,AncientFragment.RUNIC_TABLE)){
                ItemStack frag = SolarcraftItems.INFO_FRAGMENT.get().getDefaultInstance();
                AncientFragmentHelper.applyTagToFragment(frag, AncientFragment.RUNIC_TABLE);
                ItemEntity entity = new ItemEntity(pe.level,pe.getX(),pe.getY()+0.3f,pe.getZ(),frag);
                AncientFragmentHelper.givePlayerFragment(AncientFragment.RUNIC_TABLE,pe);
                pe.level.addFreshEntity(entity);
            }
            Helpers.updateFragmentsOnClient((ServerPlayer) pe);
            updateInventory(pe.getMainHandItem(),pe);
            if (!pe.isCrouching()) {
                ProgressionTree tree = ProgressionTree.INSTANCE;
//                for (Progression a : tree.PROGRESSION_TREE.keySet()) {
//                    SCPacketHandler.INSTANCE.sendTo(new UpdateProgressionsOnClient(a.getProgressionCode(), pe.getPersistentData().getBoolean(Helpers.PROGRESSION + a.getProgressionCode())),
//                            ((ServerPlayer) pe).connection.connection, NetworkDirection.PLAY_TO_CLIENT);
//                }
                Helpers.updateProgressionsOnClient((ServerPlayer) pe);
                SCPacketHandler.INSTANCE.sendTo(new OpenScreenPacket(), ((ServerPlayer) pe).connection.connection, NetworkDirection.PLAY_TO_CLIENT);

            }else{
                NetworkHooks.openScreen((ServerPlayer) pe,new SolarLexiconContainer.Provider(pe.getItemInHand(hand)),(buf)->{
                    buf.writeItem(pe.getItemInHand(hand));
                });
            }

        }



    return super.use(world,pe,hand);
    }



    public void updateInventory(ItemStack stack,Player ent){
        if (stack.getItem() instanceof SolarLexicon){
            IItemHandler handelr = stack.getCapability(ForgeCapabilities.ITEM_HANDLER).orElse(null);
            if (handelr != null){
                List<ItemStack> stacks = new ArrayList<>();
                for (int i = 0;i < handelr.getSlots();i++){
                    stacks.add(handelr.getStackInSlot(i));
                }
                ItemStack[] arr = new ItemStack[stacks.size()];
                SCPacketHandler.INSTANCE.sendTo(new UpdateInventoryPacket(stacks.toArray(arr)), ((ServerPlayer) ent).connection.connection, NetworkDirection.PLAY_TO_CLIENT);
            }
        }
    }
}
