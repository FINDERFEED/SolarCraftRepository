package com.finderfeed.solarcraft.packet_handler.packets;

import com.finderfeed.solarcraft.content.items.solar_lexicon.unlockables.AncientFragment;
import com.finderfeed.solarcraft.content.items.solar_lexicon.unlockables.AncientFragmentHelper;
import com.finderfeed.solarcraft.packet_handler.packet_system.FDPacket;
import com.finderfeed.solarcraft.packet_handler.packet_system.Packet;
import com.finderfeed.solarcraft.registries.items.SCItems;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;
import java.util.function.Supplier;

@Packet("retain_fragment_packet")
public class RetainFragmentPacket extends FDPacket {

    private String fragID;

    public RetainFragmentPacket(String fragID){
        this.fragID = fragID;
    }


    public RetainFragmentPacket(FriendlyByteBuf buf) {
        this.fragID = buf.readUtf();
    }

    public void toBytes(FriendlyByteBuf buf){
        buf.writeUtf(fragID);
    }



//    public void handle(PlayPayloadContext ctx) {
//
//            ServerPlayer sender = ctx.getSender();
//            AncientFragment fragment = AncientFragment.getFragmentByID(fragID);
//            if (fragment != null){
//                int slot = -1;
//                for (int i = 0; i < sender.getInventory().getContainerSize();i++){
//                    if (sender.getInventory().getItem(i).getItem() == Items.PAPER){
//                        slot = i;
//                        break;
//                    }
//                }
//                if (slot != -1){
//                    sender.getInventory().getItem(slot).shrink(1);
//                    ItemStack frag = SCItems.INFO_FRAGMENT.get().getDefaultInstance();
//                    AncientFragmentHelper.applyTagToFragment(frag,fragment);
//                    if (!sender.addItem(frag)){
//                        ItemEntity entity = new ItemEntity(sender.level(),sender.getX(),sender.getY(),sender.getZ(),frag);
//                        sender.level().addFreshEntity(entity);
//                    }
//                }
//            }
//        }

    @Override
    public void serverPlayHandle(PlayPayloadContext ctx) {
        ServerPlayer sender = (ServerPlayer) ctx.player().get();
        AncientFragment fragment = AncientFragment.getFragmentByID(fragID);
        if (fragment != null){
            int slot = -1;
            for (int i = 0; i < sender.getInventory().getContainerSize();i++){
                if (sender.getInventory().getItem(i).getItem() == Items.PAPER){
                    slot = i;
                    break;
                }
            }
            if (slot != -1){
                sender.getInventory().getItem(slot).shrink(1);
                ItemStack frag = SCItems.INFO_FRAGMENT.get().getDefaultInstance();
                AncientFragmentHelper.applyTagToFragment(frag,fragment);
                if (!sender.addItem(frag)){
                    ItemEntity entity = new ItemEntity(sender.level(),sender.getX(),sender.getY(),sender.getZ(),frag);
                    sender.level().addFreshEntity(entity);
                }
            }
        }
    }

    @Override
    public void write(FriendlyByteBuf friendlyByteBuf) {
        this.toBytes(friendlyByteBuf);

    }




}
