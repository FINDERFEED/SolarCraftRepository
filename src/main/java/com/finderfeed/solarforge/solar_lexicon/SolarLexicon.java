package com.finderfeed.solarforge.solar_lexicon;

import com.finderfeed.solarforge.Helpers;
import com.finderfeed.solarforge.packet_handler.SolarForgePacketHandler;
import com.finderfeed.solarforge.solar_lexicon.achievement_tree.AchievementTree;
import com.finderfeed.solarforge.solar_lexicon.achievements.Achievement;
import com.finderfeed.solarforge.solar_lexicon.packets.OpenScreenPacket;
import com.finderfeed.solarforge.solar_lexicon.packets.UpdateProgressionOnClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkDirection;

public class SolarLexicon extends Item {

    public Screen currentSavedScreen = null;

    public SolarLexicon(Properties p_i48487_1_) {
        super(p_i48487_1_);
    }


    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity pe, Hand hand) {
        if (!world.isClientSide){
            AchievementTree tree = AchievementTree.loadTree();
            for (Achievement a : tree.ACHIEVEMENT_TREE.keySet()) {



                SolarForgePacketHandler.INSTANCE.sendTo(new UpdateProgressionOnClient(a.getAchievementCode(),pe.getPersistentData().getBoolean(Helpers.PROGRESSION+a.getAchievementCode())),
                        ((ServerPlayerEntity) pe).connection.connection, NetworkDirection.PLAY_TO_CLIENT);
            }
            SolarForgePacketHandler.INSTANCE.sendTo(new OpenScreenPacket(),((ServerPlayerEntity)pe).connection.connection, NetworkDirection.PLAY_TO_CLIENT);

        }

//        if (world.isClientSide) {
//            System.out.println("a");
//                   // Minecraft.getInstance().setScreen(new SolarLexiconScreen());
//            InvokeScreenTest.openScreen();
//        }

    return super.use(world,pe,hand);
    }
}
