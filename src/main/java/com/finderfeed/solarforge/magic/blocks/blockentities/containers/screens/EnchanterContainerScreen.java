package com.finderfeed.solarforge.magic.blocks.blockentities.containers.screens;

import com.finderfeed.solarforge.ClientHelpers;
import com.finderfeed.solarforge.SolarForge;
import com.finderfeed.solarforge.magic.blocks.blockentities.EnchanterBlockEntity;
import com.finderfeed.solarforge.magic.blocks.blockentities.containers.EnchanterContainer;
import com.finderfeed.solarforge.magic.blocks.infusing_table_things.InfuserScreen;
import com.finderfeed.solarforge.magic.blocks.solar_forge_block.solar_forge_screen.SolarForgeButton;
import com.finderfeed.solarforge.misc_things.RunicEnergy;
import com.finderfeed.solarforge.packet_handler.SolarForgePacketHandler;
import com.finderfeed.solarforge.packet_handler.packets.EnchanterPacket;
import com.finderfeed.solarforge.recipe_types.infusing_new.InfusingRecipe;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.enchantment.Enchantment;

import java.util.Map;

public class EnchanterContainerScreen extends AbstractScrollableContainerScreen<EnchanterContainer> {
    public static final ResourceLocation MAIN_SCREEN = new ResourceLocation(SolarForge.MOD_ID,"textures/gui/enchanter_gui.png");
    private static final ResourceLocation ENERGY_GUI = new ResourceLocation("solarforge","textures/gui/infuser_energy_gui.png");
    public final ResourceLocation RUNIC_ENERGY_BAR = new ResourceLocation("solarforge","textures/gui/runic_energy_bar.png");
    private Enchantment selectedEnchantment = null;
    private int selectedLevel = 0;


    public EnchanterContainerScreen(EnchanterContainer container, Inventory inventory, Component component) {
        super(container, inventory, component);
    }

    @Override
    public void init() {
        super.init();
        this.inventoryLabelX = 10000;
        relX+=50;
        relY+=22;
        Map<Enchantment,Map<RunicEnergy.Type,Double>> defaultCosts = menu.costsAndAvailableEnchantments;
        int iter = 0;

        for (Enchantment e : defaultCosts.keySet()){
            if (iter == 0){
                this.selectedEnchantment = e;
                this.selectedLevel = 1;
            }
            addRenderableWidget(new SolarForgeButton(relX + 12 ,relY + 12 + iter*16,new TranslatableComponent(e.getDescriptionId()),(button)->{
                this.selectedEnchantment = e;
                this.selectedLevel = 1;
            }));
            iter++;
        }

        Button buttonPlus = new SolarForgeButton(relX+ 87,relY + 17,16,16,new TextComponent("+"),(btn)->{
            if (!(selectedLevel + 1 > selectedEnchantment.getMaxLevel())){
                selectedLevel++;
            }
        });
        Button buttonMinus = new SolarForgeButton(relX+ 87,relY + 51,16,16,new TextComponent("-"),(btn)->{
            if (!(selectedLevel - 1 < 0)){
                selectedLevel++;
            }
        });

        Button button = new SolarForgeButton(relX+ 112,relY + 71,new TextComponent("Enchant"),(btn)->{
            if (selectedEnchantment != null && menu.tile.getStackInSlot(0).canApplyAtEnchantingTable(selectedEnchantment)) {
                SolarForgePacketHandler.INSTANCE.sendToServer(new EnchanterPacket(menu.tile.getBlockPos(),selectedEnchantment,selectedLevel));
            }else{
                Minecraft.getInstance().player.displayClientMessage(new TextComponent("Enchantment cannot be applied to this item"),false);
            }
        });
        addRenderableWidget(button);
        addRenderableWidget(buttonMinus);
        addRenderableWidget(buttonPlus);
        setAsStaticWidget(button);
        setAsStaticWidget(buttonPlus);
        setAsStaticWidget(buttonMinus);
    }

    @Override
    protected void renderBg(PoseStack matrices, float pticks, int mousex, int mousey) {
        ClientHelpers.bindText(MAIN_SCREEN);
        int scale = (int) minecraft.getWindow().getGuiScale();
        int a = 1;
        if (scale == 2) {
            a = 0;
        }
        //{109;5}
        //+62
        blit(matrices,relX+a+3,relY+4,0,0,176,256,256,256);

        if (menu.tile.enchantingInProgress()){
            int xTex = Math.round(62f*(menu.tile.getEnchantingTicks()/ (float)EnchanterBlockEntity.MAX_ENCHANTING_TICKS));
            blit(matrices,relX+a+109,relY+5,176,0,xTex,62,256,256);
        }


        if (selectedEnchantment != null) {
            matrices.pushPose();
            ClientHelpers.bindText(ENERGY_GUI);
            blit(matrices, relX + a - 73 + 4, relY - 8, 0, 0, 73, 177, 73, 177);
            ClientHelpers.bindText(RUNIC_ENERGY_BAR);

            RenderSystem.enableBlend();
            renderEnergyBar(matrices, relX + a - 12 - 16 + 1, relY + 61, menu.costsAndAvailableEnchantments.get(selectedEnchantment).get(RunicEnergy.Type.KELDA), true);

            renderEnergyBar(matrices, relX + a - 28 - 16 + 1, relY + 61, menu.costsAndAvailableEnchantments.get(selectedEnchantment).get(RunicEnergy.Type.TERA), true);

            renderEnergyBar(matrices, relX + a - 44 - 16 + 1, relY + 61, menu.costsAndAvailableEnchantments.get(selectedEnchantment).get(RunicEnergy.Type.ZETA), true);


            renderEnergyBar(matrices, relX + a - 12 - 16 + 1, relY + 145, menu.costsAndAvailableEnchantments.get(selectedEnchantment).get(RunicEnergy.Type.URBA), true);

            renderEnergyBar(matrices, relX + a - 28 - 16 + 1, relY + 145, menu.costsAndAvailableEnchantments.get(selectedEnchantment).get(RunicEnergy.Type.FIRA), true);

            renderEnergyBar(matrices, relX + a - 44 - 16 + 1, relY + 145, menu.costsAndAvailableEnchantments.get(selectedEnchantment).get(RunicEnergy.Type.ARDO), true);

            renderEnergyBar(matrices, relX + a - 12 + 1, relY + 61, menu.costsAndAvailableEnchantments.get(selectedEnchantment).get(RunicEnergy.Type.GIRO), true);

            renderEnergyBar(matrices, relX + a - 12 + 1, relY + 145, menu.costsAndAvailableEnchantments.get(selectedEnchantment).get(RunicEnergy.Type.ULTIMA), true);
            RenderSystem.disableBlend();


            renderEnergyBar(matrices, relX + a - 12 - 16 + 1, relY + 61, menu.tile.getRunicEnergy(RunicEnergy.Type.KELDA), false);
            renderEnergyBar(matrices, relX + a - 28 - 16 + 1, relY + 61, menu.tile.getRunicEnergy(RunicEnergy.Type.TERA), false);
            renderEnergyBar(matrices, relX + a - 44 - 16 + 1, relY + 61, menu.tile.getRunicEnergy(RunicEnergy.Type.ZETA), false);
            renderEnergyBar(matrices, relX + a - 12 - 16 + 1, relY + 145,menu.tile.getRunicEnergy(RunicEnergy.Type.URBA), false);
            renderEnergyBar(matrices, relX + a - 28 - 16 + 1, relY + 145,menu.tile.getRunicEnergy(RunicEnergy.Type.FIRA), false);
            renderEnergyBar(matrices, relX + a - 44 - 16 + 1, relY + 145,menu.tile.getRunicEnergy(RunicEnergy.Type.ARDO), false);
            renderEnergyBar(matrices, relX + a - 12 + 1, relY + 61, menu.tile.getRunicEnergy(RunicEnergy.Type.GIRO), false);
            renderEnergyBar(matrices, relX + a - 12 + 1, relY + 145, menu.tile.getRunicEnergy(RunicEnergy.Type.ULTIMA), false);
        }
    }

    private void renderEnergyBar(PoseStack matrices, int offsetx, int offsety, double energyAmount,boolean simulate){
        matrices.pushPose();

        int texturex = Math.round((float)energyAmount/100000*60);
        matrices.translate(offsetx,offsety,0);
        matrices.mulPose(Vector3f.ZN.rotationDegrees(90));
        if (!simulate) {
            blit(matrices, 0, 0, 0, 0, texturex, 6);
        }else{
            InfuserScreen.blitm(matrices, 0, 0, 0, 0, texturex, 6,60,6);
        }

        matrices.popPose();
    }
    @Override
    protected int getScrollValue() {
        return 4;
    }

    @Override
    protected int getMaxYDownScrollValue() {
        return 0;
    }

    @Override
    protected int getMaxXRightScrollValue() {
        return 0;
    }

    @Override
    protected int getMaxYUpScrollValue() {
        return 0;
    }

    @Override
    protected int getMaxXLeftScrollValue() {
        return 0;
    }
}
