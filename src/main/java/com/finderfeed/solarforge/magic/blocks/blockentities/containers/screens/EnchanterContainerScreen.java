package com.finderfeed.solarforge.magic.blocks.blockentities.containers.screens;

import com.finderfeed.solarforge.ClientHelpers;
import com.finderfeed.solarforge.SolarForge;
import com.finderfeed.solarforge.client.particles.screen.SolarStrikeParticleScreen;
import com.finderfeed.solarforge.config.enchanter_config.EnchanterConfig;
import com.finderfeed.solarforge.local_library.client.particles.ScreenParticlesRenderHandler;
import com.finderfeed.solarforge.local_library.helpers.FinderfeedMathHelper;
import com.finderfeed.solarforge.magic.blocks.blockentities.EnchanterBlockEntity;
import com.finderfeed.solarforge.magic.blocks.blockentities.containers.EnchanterContainer;
import com.finderfeed.solarforge.magic.blocks.infusing_table_things.InfuserScreen;
import com.finderfeed.solarforge.magic.blocks.solar_forge_block.solar_forge_screen.SolarForgeButton;
import com.finderfeed.solarforge.magic.items.runic_energy.RunicEnergyCost;
import com.finderfeed.solarforge.misc_things.RunicEnergy;
import com.finderfeed.solarforge.packet_handler.SolarForgePacketHandler;
import com.finderfeed.solarforge.packet_handler.packets.EnchanterPacket;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.phys.Vec2;
import org.lwjgl.glfw.GLFW;

import java.util.*;

public class EnchanterContainerScreen extends AbstractScrollableContainerScreen<EnchanterContainer> {
    public static final ResourceLocation MAIN_SCREEN = new ResourceLocation(SolarForge.MOD_ID,"textures/gui/enchanter_gui.png");
    private static final ResourceLocation ENERGY_GUI = new ResourceLocation("solarforge","textures/gui/infuser_energy_gui.png");
    public final ResourceLocation RUNIC_ENERGY_BAR = new ResourceLocation("solarforge","textures/gui/runic_energy_bar.png");
    private EnchanterConfig.ConfigEnchantmentInstance selectedEnchantment = null;
    private int selectedLevel = 0;
    private final List<SolarForgeButton> postRender = new ArrayList<>();
    private int currentMouseScroll = 0;
    private List<Runnable> postRunRender = new ArrayList<>();
    private Random random = new Random();
    private int ticker = 0;

    public EnchanterContainerScreen(EnchanterContainer container, Inventory inventory, Component component) {
        super(container, inventory, component);
    }

    @Override
    public void init() {
        currentMouseScroll = 0;
        super.init();
        this.inventoryLabelX = 10000;
        relX+=60;
        relY+=10;
        EnchanterConfig defaultCosts = menu.config;
        int iter = 0;
        postRender.clear();
        for (EnchanterConfig.ConfigEnchantmentInstance e : defaultCosts.getEnchantments()){
            if (iter == 0){
                if (!menu.tile.enchantingInProgress()) {
                    this.selectedEnchantment = e;
                    this.selectedLevel = 1;
                }else{
                    this.selectedEnchantment = menu.tile.getProcessingEnchantment();
                    this.selectedLevel = menu.tile.getProcesingEnchantmentLevel();
                }
            }
            SolarForgeButton b = new SolarForgeButton(relX + 15 ,relY + 12 + iter*16,new TranslatableComponent(e.enchantment().getDescriptionId()),(button)->{
                if (!menu.tile.enchantingInProgress()) {
                    this.selectedEnchantment = e;
                    this.selectedLevel = 1;
                }
            },(btn,matrices,mousex,mousey)->{
                postRunRender.add(()->{
                    renderTooltip(matrices,new TranslatableComponent(e.enchantment().getDescriptionId()),mousex,mousey);
                });
            });
            postRender.add(b);
            addWidget(b);
            iter++;
        }

        Button buttonPlus = new SolarForgeButton(relX+ 87,relY + 22,15,16,new TextComponent("+"),(btn)->{
            if (!(selectedLevel + 1 > selectedEnchantment.maxLevel()) && !menu.tile.enchantingInProgress()){
                selectedLevel++;
            }
        });
        Button buttonMinus = new SolarForgeButton(relX+ 87,relY + 62,15,16,new TextComponent("-"),(btn)->{
            if (!(selectedLevel - 1 < 1) && !menu.tile.enchantingInProgress()){
                selectedLevel--;
            }
        });

        Button button = new SolarForgeButton(relX+ 110,relY + 80,new TextComponent("Enchant"),(btn)->{
            ItemStack stack = menu.tile.getStackInSlot(0);

            if (selectedEnchantment != null && stack.canApplyAtEnchantingTable(selectedEnchantment.enchantment())
                    && EnchantmentHelper.getItemEnchantmentLevel(selectedEnchantment.enchantment(),stack) < selectedLevel) {
                boolean compatible = true;
                Map<Enchantment,Integer> enchs = new HashMap<>(EnchantmentHelper.getEnchantments(stack));
                for (Enchantment e : enchs.keySet()){
                    if (!e.isCompatibleWith(selectedEnchantment.enchantment())){
                        compatible = false;
                    }
                }
                if (compatible) {
                    if (!menu.tile.enchantingInProgress()) {
                        SolarForgePacketHandler.INSTANCE.sendToServer(new EnchanterPacket(menu.tile.getBlockPos(), selectedEnchantment.enchantment(), selectedLevel));
                    } else {
                        Minecraft.getInstance().player.displayClientMessage(new TextComponent("Enchanting is already in progress!"), false);
                    }
                }else{
                    Minecraft.getInstance().player.displayClientMessage(new TextComponent("Enchantment is incompatible with other enchantments on this item"),false);
                }
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

        super.renderBackground(matrices);
        ClientHelpers.bindText(MAIN_SCREEN);
        int scale = (int) minecraft.getWindow().getGuiScale();
        int a = 1;
        if (scale == 2) {
            a = 0;
        }

        blit(matrices,relX+a+3,relY+4,0,0,176,256,256,256);

        if (menu.tile.enchantingInProgress()){
            int xTex = Math.round(62f*(menu.tile.getEnchantingTicks()/ (float)EnchanterBlockEntity.MAX_ENCHANTING_TICKS));
            blit(matrices,relX+a+110,relY+11,176,0,xTex,62,256,256);
        }


        if (selectedEnchantment != null) {
            int y = 12;
            matrices.pushPose();
            ClientHelpers.bindText(ENERGY_GUI);
            blit(matrices, relX + a - 73 + 4, relY - 8 + y, 0, 0, 73, 177, 73, 177);
            ClientHelpers.bindText(RUNIC_ENERGY_BAR);
            RunicEnergyCost c = selectedEnchantment.getCostForLevel(menu.config.getMode(),selectedLevel);
            RenderSystem.enableBlend();
            renderEnergyBar(matrices, relX + a - 12 - 16 + 1, relY + 61 + y, c.get(RunicEnergy.Type.KELDA), true,mousex,mousey);
            renderEnergyBar(matrices, relX + a - 28 - 16 + 1, relY + 61 + y, c.get(RunicEnergy.Type.TERA), true,mousex,mousey);
            renderEnergyBar(matrices, relX + a - 44 - 16 + 1, relY + 61 + y, c.get(RunicEnergy.Type.ZETA), true,mousex,mousey);
            renderEnergyBar(matrices, relX + a - 12 - 16 + 1, relY + 145 + y, c.get(RunicEnergy.Type.URBA), true,mousex,mousey);
            renderEnergyBar(matrices, relX + a - 28 - 16 + 1, relY + 145 + y, c.get(RunicEnergy.Type.FIRA), true,mousex,mousey);
            renderEnergyBar(matrices, relX + a - 44 - 16 + 1, relY + 145 + y, c.get(RunicEnergy.Type.ARDO), true,mousex,mousey);
            renderEnergyBar(matrices, relX + a - 12 , relY + 61 + y, c.get(RunicEnergy.Type.GIRO), true,mousex,mousey);
            renderEnergyBar(matrices, relX + a - 12 , relY + 145 + y, c.get(RunicEnergy.Type.ULTIMA), true,mousex,mousey);
            RenderSystem.disableBlend();


            renderEnergyBar(matrices, relX + a - 12 - 16 + 1, relY + 61 + y, menu.tile.getRunicEnergy(RunicEnergy.Type.KELDA), false,mousex,mousey);
            renderEnergyBar(matrices, relX + a - 28 - 16 + 1, relY + 61 + y, menu.tile.getRunicEnergy(RunicEnergy.Type.TERA), false,mousex,mousey);
            renderEnergyBar(matrices, relX + a - 44 - 16 + 1, relY + 61 + y, menu.tile.getRunicEnergy(RunicEnergy.Type.ZETA), false,mousex,mousey);
            renderEnergyBar(matrices, relX + a - 12 - 16 + 1, relY + 145 + y,menu.tile.getRunicEnergy(RunicEnergy.Type.URBA), false,mousex,mousey);
            renderEnergyBar(matrices, relX + a - 28 - 16 + 1, relY + 145 + y,menu.tile.getRunicEnergy(RunicEnergy.Type.FIRA), false,mousex,mousey);
            renderEnergyBar(matrices, relX + a - 44 - 16 + 1, relY + 145 + y,menu.tile.getRunicEnergy(RunicEnergy.Type.ARDO), false,mousex,mousey);
            renderEnergyBar(matrices, relX + a - 12 , relY + 61 + y, menu.tile.getRunicEnergy(RunicEnergy.Type.GIRO), false,mousex,mousey);
            renderEnergyBar(matrices, relX + a - 12 , relY + 145 + y, menu.tile.getRunicEnergy(RunicEnergy.Type.ULTIMA), false,mousex,mousey);

            drawCenteredString(matrices,font,""+selectedLevel,relX + 94,relY + 45,0xffffff);
            drawCenteredString(matrices,font,selectedEnchantment.enchantment().getFullname(selectedLevel).getString(),relX + 93,relY + 184,0xff0000);
            matrices.popPose();


        }

        RenderSystem.enableScissor((relX + 12 + a)*scale,(relY + 106 )*scale,69*scale,80*scale);

        for (SolarForgeButton b : postRender){
            b.render(matrices,mousex,mousey,pticks);
        }
        RenderSystem.disableScissor();
        postRunRender.forEach(Runnable::run);
        postRunRender.clear();
        super.renderTooltip(matrices,mousex,mousey);

    }

    @Override
    public void onClose() {
        super.onClose();
        ScreenParticlesRenderHandler.clearAllParticles();
    }

    @Override
    protected void containerTick() {
        super.containerTick();
        //I KNOW THIS LOOKS STRANGE DON'T TELL ME ANYTHING OR I WILL.... WISH YOU A GOOD DAY!
        if (menu.tile.enchantingInProgress()){
            int scale = (int) minecraft.getWindow().getGuiScale();
            int a = 1;
            if (scale == 2) {
                a = 0;
            }
            ticker++;
            int time = 40;
            if (ticker > time) ticker = 0;
            int g = time/4;
            double y = 0;
            double x = 0;
            double squareRadius = 16f;
            if (ticker <= g) y = ticker/(float)g*squareRadius;
            if (ticker > g && ticker <= g*2) {y = squareRadius; x = (ticker - g)/(float)g*squareRadius;}
            if (ticker > g*2 && ticker <= g*3) {y = (1-(ticker-g*2)/(float)g)*squareRadius; x = squareRadius;}
            if (ticker > g*3 && ticker < g*4) {x =  (1-(ticker- g*3)/(float)g)*squareRadius; }
            SolarStrikeParticleScreen s = new SolarStrikeParticleScreen(20,relX + 133 + x + a,relY + 34 + y,0,0,
                    231 + random.nextInt(25),
                    231+ random.nextInt(25),0,255);
            s.setSize(7);
            ScreenParticlesRenderHandler.addParticle(s);
        }
        for (SolarForgeButton b : postRender){
            if (!(b.y > relY   && b.y <  relY + 89) ){
                b.active = false;
                b.visible = false;
            }else{
                b.active = true;
                b.visible = true;
            }
        }
    }


    @Override
    public boolean mouseScrolled(double p_94686_, double p_94687_, double delta) {

        for (SolarForgeButton b : postRender){
            if (currentMouseScroll + delta*getScrollValue() >= -getMaxYDownScrollValue() && currentMouseScroll + delta*getScrollValue() <= 0) {
                b.y = b.y + (int) delta * getScrollValue();

            }
        }
        if (currentMouseScroll + delta*getScrollValue() >= -getMaxYDownScrollValue() && currentMouseScroll + delta*getScrollValue() <= 0) {
            currentMouseScroll += delta * getScrollValue();
        }
        return super.mouseScrolled(p_94686_, p_94687_, delta);
    }

    @Override
    public void performScroll(int keyCode) {
        int delta = getScrollValue()/2;
        if ((keyCode == GLFW.glfwGetKeyScancode(GLFW.GLFW_KEY_UP)
                || keyCode == GLFW.glfwGetKeyScancode(GLFW.GLFW_KEY_W)) ){
            for (SolarForgeButton b : postRender){
                if (currentMouseScroll + delta*getScrollValue() >= -getMaxYDownScrollValue() && currentMouseScroll + delta*getScrollValue() <= 0) {
                    b.y = b.y + (int) delta * getScrollValue();

                }
            }
            if (currentMouseScroll + delta*getScrollValue() >= -getMaxYDownScrollValue() && currentMouseScroll + delta*getScrollValue() <= 0) {
                currentMouseScroll += delta * getScrollValue();
            }
        }else if((keyCode == GLFW.glfwGetKeyScancode(GLFW.GLFW_KEY_DOWN)
                || keyCode == GLFW.glfwGetKeyScancode(GLFW.GLFW_KEY_S)) ){
            for (SolarForgeButton b : postRender){
                if (currentMouseScroll - delta*getScrollValue() >= -getMaxYDownScrollValue() && currentMouseScroll - delta*getScrollValue() <= 0) {
                    b.y = b.y - (int) delta * getScrollValue();

                }
            }
            if (currentMouseScroll - delta*getScrollValue() >= -getMaxYDownScrollValue() && currentMouseScroll - delta*getScrollValue() <= 0) {
                currentMouseScroll -= delta * getScrollValue();
            }
        }
        super.performScroll(keyCode);
    }

    private void renderEnergyBar(PoseStack matrices, int offsetx, int offsety, double energyAmount, boolean simulate,int mousex,int mousey){
        matrices.pushPose();

        int texturex = Math.round((float)energyAmount/(float)menu.tile.getRunicEnergyLimit()*60);
        matrices.translate(offsetx,offsety,0);
        matrices.mulPose(Vector3f.ZN.rotationDegrees(90));
        if (!simulate) {
            blit(matrices, 0, 0, 0, 0, texturex, 6);
            if (mousex > offsetx && mousex < offsetx + 6 && mousey > offsety-60 && mousey < offsety){
                postRunRender.add(()->{
                    renderTooltip(matrices,new TextComponent((float) energyAmount + "/" + menu.tile.getRunicEnergyLimit()),mousex-3,mousey+3);
                });
            }
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
        return (int)Math.floor(postRender.size()/5f) * 80;
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
