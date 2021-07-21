package com.finderfeed.solarforge.magic_items.items.solar_lexicon;

import com.finderfeed.solarforge.Helpers;
import com.finderfeed.solarforge.magic_items.items.solar_lexicon.unlockables.AncientFragment;
import com.finderfeed.solarforge.magic_items.items.solar_lexicon.unlockables.ProgressionHelper;
import com.finderfeed.solarforge.packet_handler.SolarForgePacketHandler;
import com.finderfeed.solarforge.magic_items.items.solar_lexicon.achievements.achievement_tree.AchievementTree;
import com.finderfeed.solarforge.magic_items.items.solar_lexicon.achievements.Achievement;
import com.finderfeed.solarforge.magic_items.items.solar_lexicon.packets.OpenScreenPacket;
import com.finderfeed.solarforge.magic_items.items.solar_lexicon.packets.UpdateInventoryPacket;
import com.finderfeed.solarforge.magic_items.items.solar_lexicon.packets.UpdateProgressionOnClient;
import com.finderfeed.solarforge.registries.items.ItemsRegister;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import java.util.ArrayList;
import java.util.List;

public class SolarLexicon extends Item {

    public Screen currentSavedScreen = null;

    public SolarLexicon(Properties p_i48487_1_) {
        super(p_i48487_1_);
    }


    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity pe, Hand hand) {
        if (!world.isClientSide && hand.equals(Hand.MAIN_HAND)){
            if (!ProgressionHelper.doPlayerHasFragment(pe,AncientFragment.LEXICON)) {
                ItemStack frag = ItemsRegister.INFO_FRAGMENT.get().getDefaultInstance();
                ProgressionHelper.applyTagToFragment(frag, AncientFragment.LEXICON);
                ItemEntity entity = new ItemEntity(pe.level, pe.getX(), pe.getY() + 0.3f, pe.getZ(), frag);
                ProgressionHelper.givePlayerFragment(AncientFragment.LEXICON, pe);
                pe.level.addFreshEntity(entity);
            }
            if (!pe.isCrouching()) {
                AchievementTree tree = AchievementTree.loadTree();
                for (Achievement a : tree.ACHIEVEMENT_TREE.keySet()) {
                    SolarForgePacketHandler.INSTANCE.sendTo(new UpdateProgressionOnClient(a.getAchievementCode(), pe.getPersistentData().getBoolean(Helpers.PROGRESSION + a.getAchievementCode())),
                            ((ServerPlayerEntity) pe).connection.connection, NetworkDirection.PLAY_TO_CLIENT);
                }
                SolarForgePacketHandler.INSTANCE.sendTo(new OpenScreenPacket(), ((ServerPlayerEntity) pe).connection.connection, NetworkDirection.PLAY_TO_CLIENT);
                updateInventory(pe.getMainHandItem(),pe);
            }else{
                NetworkHooks.openGui((ServerPlayerEntity) pe,new SolarLexiconContainer.Provider(pe.getItemInHand(hand)),(buf)->{
                    buf.writeItem(pe.getItemInHand(hand));
                });
            }
        }



    return super.use(world,pe,hand);
    }



    public void updateInventory(ItemStack stack,PlayerEntity ent){
        if (stack.getItem() instanceof SolarLexicon){
            IItemHandler handelr = stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElse(null);
            if (handelr != null){
                List<ItemStack> stacks = new ArrayList<>();
                for (int i = 0;i < handelr.getSlots();i++){
                    stacks.add(handelr.getStackInSlot(i));
                }
                ItemStack[] arr = new ItemStack[stacks.size()];
                SolarForgePacketHandler.INSTANCE.sendTo(new UpdateInventoryPacket(stacks.toArray(arr)), ((ServerPlayerEntity) ent).connection.connection, NetworkDirection.PLAY_TO_CLIENT);
            }
        }
    }
}
