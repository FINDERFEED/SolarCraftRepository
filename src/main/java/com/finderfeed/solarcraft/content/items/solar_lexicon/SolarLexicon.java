package com.finderfeed.solarcraft.content.items.solar_lexicon;

import com.finderfeed.solarcraft.helpers.Helpers;
import com.finderfeed.solarcraft.content.items.solar_lexicon.unlockables.AncientFragment;
import com.finderfeed.solarcraft.content.items.solar_lexicon.unlockables.AncientFragmentHelper;
import com.finderfeed.solarcraft.packet_handler.SCPacketHandler;
import com.finderfeed.solarcraft.content.items.solar_lexicon.packets.OpenScreenPacket;
import com.finderfeed.solarcraft.content.items.solar_lexicon.packets.UpdateInventoryPacket;
import com.finderfeed.solarcraft.registries.items.SCItems;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.capabilities.Capabilities;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.network.PlayNetworkDirection;
import net.neoforged.neoforge.network.NetworkHooks;
import java.util.ArrayList;
import java.util.List;

public class SolarLexicon extends Item {

    public SolarLexicon(Properties p_i48487_1_) {
        super(p_i48487_1_);
    }


    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player pe, InteractionHand hand) {
        if (!world.isClientSide && hand.equals(InteractionHand.MAIN_HAND)){
            this.givePlayerFragmentIfNecessary(pe,AncientFragment.LEXICON,AncientFragment.FRAGMENT,AncientFragment.RUNIC_TABLE);

            Helpers.updateFragmentsOnClient((ServerPlayer) pe);
            updateInventory(pe.getMainHandItem(),pe);
            if (!pe.isCrouching()) {
                Helpers.updateProgressionsOnClient((ServerPlayer) pe);
                SCPacketHandler.INSTANCE.sendTo(new OpenScreenPacket(), ((ServerPlayer) pe).connection.connection, PlayNetworkDirection.PLAY_TO_CLIENT);

            }else{
                NetworkHooks.openScreen((ServerPlayer) pe,new SolarLexiconContainer.Provider(pe.getItemInHand(hand)),(buf)->{
                    buf.writeItem(pe.getItemInHand(hand));
                });
            }

        }



        return super.use(world,pe,hand);
    }


    private void givePlayerFragmentIfNecessary(Player pe,AncientFragment... fragments){
        for (AncientFragment fragment : fragments) {
            if (!AncientFragmentHelper.doPlayerHasFragment(pe,fragment)) {
                ItemStack frag = SCItems.INFO_FRAGMENT.get().getDefaultInstance();
                AncientFragmentHelper.applyTagToFragment(frag, fragment);
                ItemEntity entity = new ItemEntity(pe.level, pe.getX(), pe.getY() + 0.3f, pe.getZ(), frag);
                AncientFragmentHelper.givePlayerFragment(fragment, pe);
                pe.level.addFreshEntity(entity);
            }
        }
    }


    public void updateInventory(ItemStack stack,Player ent){
        if (stack.getItem() instanceof SolarLexicon){
            IItemHandler handler = stack.getCapability(Capabilities.ITEM_HANDLER).orElse(null);
            if (handler != null){
                List<ItemStack> stacks = new ArrayList<>();
                for (int i = 0;i < handler.getSlots();i++){
                    stacks.add(handler.getStackInSlot(i));
                }
                ItemStack[] arr = new ItemStack[stacks.size()];
                SCPacketHandler.INSTANCE.sendTo(new UpdateInventoryPacket(stacks.toArray(arr)), ((ServerPlayer) ent).connection.connection, PlayNetworkDirection.PLAY_TO_CLIENT);
            }
        }
    }
}
