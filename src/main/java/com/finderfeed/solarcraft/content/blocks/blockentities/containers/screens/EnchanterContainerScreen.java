package com.finderfeed.solarcraft.content.blocks.blockentities.containers.screens;

import com.finderfeed.solarcraft.helpers.ClientHelpers;
import com.finderfeed.solarcraft.SolarCraft;
import com.finderfeed.solarcraft.client.particles.screen.SolarStrikeParticleScreen;
import com.finderfeed.solarcraft.config.enchanter_config.EnchanterConfig;
import com.finderfeed.solarcraft.local_library.client.particles.ScreenParticlesRenderHandler;
import com.finderfeed.solarcraft.local_library.helpers.RenderingTools;
import com.finderfeed.solarcraft.content.blocks.blockentities.EnchanterBlockEntity;
import com.finderfeed.solarcraft.content.blocks.blockentities.containers.EnchanterContainer;
import com.finderfeed.solarcraft.content.blocks.infusing_table_things.InfuserScreen;
import com.finderfeed.solarcraft.content.blocks.solar_forge_block.solar_forge_screen.SolarCraftButton;
import com.finderfeed.solarcraft.content.items.runic_energy.RunicEnergyCost;
import com.finderfeed.solarcraft.content.items.solar_lexicon.screen.SolarLexiconScreen;
import com.finderfeed.solarcraft.misc_things.RunicEnergy;
import com.finderfeed.solarcraft.packet_handler.SCPacketHandler;
import com.finderfeed.solarcraft.packet_handler.packets.EnchanterPacket;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.phys.Vec2;
import org.lwjgl.glfw.GLFW;

import java.util.*;

public class EnchanterContainerScreen extends AbstractScrollableContainerScreen<EnchanterContainer> {
    public static final ResourceLocation MAIN_SCREEN = new ResourceLocation(SolarCraft.MOD_ID,"textures/gui/enchanter_gui.png");
    private static final ResourceLocation ENERGY_GUI = new ResourceLocation("solarcraft","textures/gui/infuser_energy_gui.png");
    public final ResourceLocation RUNIC_ENERGY_BAR = new ResourceLocation("solarcraft","textures/gui/runic_energy_bar.png");
    private EnchanterConfig.ConfigEnchantmentInstance selectedEnchantment = null;
    private int selectedLevel = 0;
    private final List<SolarCraftButton> postRender = new ArrayList<>();
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
        relX+=65;
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
            SolarCraftButton b = new SolarCraftButton(relX + 111 ,relY + 60 + iter*16,Component.translatable(e.enchantment().getDescriptionId()),(button)->{
                if (!menu.tile.enchantingInProgress()) {
                    this.selectedEnchantment = e;
                    this.selectedLevel = 1;
                }
            },(btn,graphics,mousex,mousey)->{
                postRunRender.add(()->{
                    graphics.renderTooltip(font,Component.translatable(e.enchantment().getDescriptionId()),mousex,mousey);
                });
            });
            postRender.add(b);
            addWidget(b);
            iter++;
        }

        Button buttonPlus = new SolarCraftButton(relX+ 161,relY + 32,15,16,Component.literal("+"),(btn)->{
            if (!(selectedLevel + 1 > selectedEnchantment.maxLevel()) && !menu.tile.enchantingInProgress()){
                selectedLevel++;
            }
        });
        Button buttonMinus = new SolarCraftButton(relX+ 111,relY + 32,15,16,Component.literal("-"),(btn)->{
            if (!(selectedLevel - 1 < 1) && !menu.tile.enchantingInProgress()){
                selectedLevel--;
            }
        });

        Button button = new SolarCraftButton(relX+ 111,relY + 12,Component.literal("Enchant"),(btn)->{
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
                        SCPacketHandler.INSTANCE.sendToServer(new EnchanterPacket(menu.tile.getBlockPos(), selectedEnchantment.enchantment(), selectedLevel));
                    } else {
                        Minecraft.getInstance().player.displayClientMessage(Component.literal("Enchanting is already in progress!"), false);
                    }
                }else{
                    Minecraft.getInstance().player.displayClientMessage(Component.literal("Enchantment is incompatible with other enchantments on this item"),false);
                }
            }else{

                Minecraft.getInstance().player.displayClientMessage(Component.literal("Enchantment cannot be applied to this item"),false);
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
    protected void renderBg(GuiGraphics graphics, float pticks, int mousex, int mousey) {

        PoseStack matrices = graphics.pose();

        super.renderBackground(graphics,mousex,mousey,pticks);
        ClientHelpers.bindText(MAIN_SCREEN);
        int scale = (int) minecraft.getWindow().getGuiScale();
        int a = 1;
        if (scale == 2) {
            a = 0;
        }

        RenderingTools.blitWithBlend(matrices,relX+a-70,relY+4,0,0,256,180,256,256,0,1f);

        if (menu.tile.enchantingInProgress()){
            double xt = menu.tile.getEnchantingTicks()/(double)EnchanterBlockEntity.MAX_ENCHANTING_TICKS * 150;
            RenderingTools.fill(matrices,relX - 57 + a,relY +13 ,relX + xt - 57,relY + 11+13 + a,1,1,0,0.25f);
        }


        if (selectedEnchantment != null) {

            matrices.pushPose();

            RunicEnergyCost c = selectedEnchantment.getCostForLevel(menu.config.getMode(),selectedLevel);

            for (RunicEnergy.Type type : c.getSetTypes()){
                int x = type.getIndex() * 20;
                int ytexture = (int)(16*(c.get(type) / menu.config.getMaxEnchanterRunicEnergyCapacity()));
                RenderingTools.blitWithBlend(matrices,relX + x - 58 + a,relY + 78 + (16-ytexture),0,196 - ytexture,12,16,256,256,0,0.5f);

            }

            matrices.popPose();
        }
        Runnable rf = null;
        RunicEnergyCost cost = null;
        if (selectedEnchantment != null){
            cost = selectedEnchantment.getCostForLevel(menu.config.getMode(),selectedLevel);
        }
        for (RunicEnergy.Type type : RunicEnergy.Type.getAll()){
            int x = type.getIndex() * 20;
            int ytexture = (int)(16*(menu.tile.getRunicEnergy(type) / menu.config.getMaxEnchanterRunicEnergyCapacity()));

            RenderingTools.blitWithBlend(matrices,relX + x - 58 + a,relY + 78 + (16-ytexture),0,196 - ytexture,12,16,256,256,0,1f);
            if (RenderingTools.isMouseInBorders(mousex,mousey,relX + x - 58,relY + 78,relX + x - 58 + 12,relY + 78 + 16)){
                RunicEnergyCost finalCost = cost;
                rf = ()->
                {
                    List<Component> components = new ArrayList<>();
                    components.add(Component.literal(type.id.toUpperCase(Locale.ROOT) + ": ").withStyle(ChatFormatting.GOLD)
                            .append(Component.literal(menu.tile.getRunicEnergy(type) + "/" + menu.config.getMaxEnchanterRunicEnergyCapacity()).withStyle(ChatFormatting.WHITE)));
                    if (finalCost != null){
                        components.add(Component.translatable("solarcraft.not_enought_runic_energy_needed").withStyle(ChatFormatting.GOLD)
                                .append(Component.literal(": " + finalCost.get(type)).withStyle(ChatFormatting.WHITE)));
                    }
                    graphics.renderTooltip(font,components,Optional.empty(), mousex, mousey);
                };
            }
        }

        if (rf != null) rf.run();
        graphics.drawCenteredString(font,""+selectedLevel,relX + 142,relY + 35,SolarLexiconScreen.TEXT_COLOR);
        graphics.drawCenteredString(font,selectedEnchantment.enchantment().getFullname(selectedLevel).getString(),relX + 20,relY + 15, SolarLexiconScreen.TEXT_COLOR);
        RenderSystem.enableScissor((relX + 108  + a)*scale,(relY + 22 )*scale,69*scale,117*scale);

        for (SolarCraftButton b : postRender){
            b.render(graphics,mousex,mousey,pticks);
        }
        RenderSystem.disableScissor();
        postRunRender.forEach(Runnable::run);
        postRunRender.clear();
        super.renderTooltip(graphics,mousex,mousey);

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

        if (menu.tile.enchantingInProgress() && menu.tile.getProcessingEnchantment() != null){
            if (ticker++ % 10 == 0) {
                ticker = 1;
                for (RunicEnergy.Type type : menu.tile.getProcessingEnchantment().baseCost().getSetTypes()) {
                    int x = relX + type.getIndex() * 20 - 58 + 6;
                    int y = relY + 86;
                    int targetX = relX + 19;
                    int targetY = relY + 38;
                    Vec2 vec2 = new Vec2(targetX - x, targetY - y);
                    float l = vec2.length();
                    vec2 = vec2.normalized();
                    double vel = (l / 40);
                    SolarStrikeParticleScreen strikeParticleScreen = new SolarStrikeParticleScreen(40, x, y, vec2.x * vel, vec2.y * vel, 255, 255, 0, 255);
                    strikeParticleScreen.setSize(20);
                    ScreenParticlesRenderHandler.addParticle(strikeParticleScreen);

                }
            }

        }
        for (SolarCraftButton b : postRender){
            if (!(b.y > relY + 45 && b.y <  relY +175) ){
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

        for (SolarCraftButton b : postRender){
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
        if ((keyCode == GLFW.GLFW_KEY_UP
                || keyCode == GLFW.GLFW_KEY_W) ){
            for (SolarCraftButton b : postRender){
                if (currentMouseScroll + delta*getScrollValue() >= -getMaxYDownScrollValue() && currentMouseScroll + delta*getScrollValue() <= 0) {
                    b.y = b.y + (int) delta * getScrollValue();

                }
            }
            if (currentMouseScroll + delta*getScrollValue() >= -getMaxYDownScrollValue() && currentMouseScroll + delta*getScrollValue() <= 0) {
                currentMouseScroll += delta * getScrollValue();
            }
        }else if((keyCode == GLFW.GLFW_KEY_DOWN
                || keyCode == GLFW.GLFW_KEY_S) ){
            for (SolarCraftButton b : postRender){
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

//    private void renderEnergyBar(PoseStack matrices, int offsetx, int offsety, double energyAmount, boolean simulate,int mousex,int mousey){
//        matrices.pushPose();
//
//        int texturex = Math.round((float)energyAmount/(float)menu.tile.getRunicEnergyLimit()*60);
//        matrices.translate(offsetx,offsety,0);
//        matrices.mulPose(Vector3f.ZN.rotationDegrees(90));
//        if (!simulate) {
//            blit(matrices, 0, 0, 0, 0, texturex, 6);
//            if (mousex > offsetx && mousex < offsetx + 6 && mousey > offsety-60 && mousey < offsety){
//                postRunRender.add(()->{
//                    renderTooltip(matrices,Component.literal((float) energyAmount + "/" + menu.tile.getRunicEnergyLimit()),mousex-3,mousey+3);
//                });
//            }
//        }else{
//
//            InfuserScreen.blitm(matrices, 0, 0, 0, 0, texturex, 6,60,6);
//        }
//
//
//
//        matrices.popPose();
//    }



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
