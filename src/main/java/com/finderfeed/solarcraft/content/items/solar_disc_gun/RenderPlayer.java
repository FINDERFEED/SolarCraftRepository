package com.finderfeed.solarcraft.content.items.solar_disc_gun;



import net.minecraft.client.model.HumanoidModel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "solarcraft",bus = Mod.EventBusSubscriber.Bus.FORGE,value = Dist.CLIENT)
public class RenderPlayer {


    @SubscribeEvent
    public static void renderPlayer(final RenderPlayerEvent.Pre event){
        if (event.getEntity().getMainHandItem().getItem() instanceof  SolarDiscGunItem) {
            event.getRenderer().getModel().rightArmPose = HumanoidModel.ArmPose.BOW_AND_ARROW;
        }
    }
}
