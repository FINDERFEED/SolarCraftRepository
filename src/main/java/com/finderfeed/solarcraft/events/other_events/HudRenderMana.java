package com.finderfeed.solarcraft.events.other_events;

//,value = Dist.CLIENT
//@Mod.EventBusSubscriber(modid = "solarcraft",bus = Mod.EventBusSubscriber.Bus.FORGE,value = Dist.CLIENT)
public class HudRenderMana {

//    public static ResourceLocation Loc2 = new ResourceLocation("solarcraft","textures/gui/tooltips_solarforge.png");
//    @SubscribeEvent
//    public static void gameOverlay(final RenderGameOverlayEvent event){
//        Minecraft mc = Minecraft.getInstance();
//
//
//
//
//        if (event.getType() == RenderGameOverlayEvent.ElementType.TEXT){
//            List<ResourceLocation> locationsToRender = new ArrayList<>();
//            for (ToggleableAbility ability : AbilitiesRegistry.getToggleableAbilities()){
//                if (ability.isToggled(mc.player)){
//                    locationsToRender.add(new ResourceLocation(SolarForge.MOD_ID,"textures/abilities/" + ability.id + ".png"));
//                }
//            }
//
//            int initYPos = mc.getWindow().getGuiScaledHeight()/2 - 10 - (locationsToRender.size()-1)*20;
//            int initXPos = mc.getWindow().getGuiScaledWidth() - 20;
//            for (int i = 0; i < locationsToRender.size();i++){
//                ResourceLocation location = locationsToRender.get(i);
//                ClientHelpers.bindText(location);
//                Gui.blit(event.getMatrixStack(),initXPos,initYPos + i*20,0,0,20,20,20,20);
//            }
//
//
//
//
//
//            if (mc.player.getMainHandItem().getItem() instanceof SolarWandItem){
//                int height = event.getWindow().getGuiScaledHeight();
//                int width = event.getWindow().getGuiScaledWidth();
//                RenderingTools.renderRuneEnergyOverlay(event.getMatrixStack(),2,height/2-43, RunicEnergy.Type.KELDA);
//                RenderingTools.renderRuneEnergyOverlay(event.getMatrixStack(),14,height/2-43, RunicEnergy.Type.ARDO);
//                RenderingTools.renderRuneEnergyOverlay(event.getMatrixStack(),26,height/2-43, RunicEnergy.Type.ZETA);
//                RenderingTools.renderRuneEnergyOverlay(event.getMatrixStack(),2,height/2+15, RunicEnergy.Type.FIRA);
//                RenderingTools.renderRuneEnergyOverlay(event.getMatrixStack(),14,height/2+15, RunicEnergy.Type.TERA);
//                RenderingTools.renderRuneEnergyOverlay(event.getMatrixStack(),26,height/2+15, RunicEnergy.Type.URBA);
//                RenderingTools.renderRuneEnergyOverlay(event.getMatrixStack(),38,height/2-43, RunicEnergy.Type.GIRO);
//                RenderingTools.renderRuneEnergyOverlay(event.getMatrixStack(),38,height/2+15, RunicEnergy.Type.ULTIMA);
//            }
//
//        }


//    }


}
