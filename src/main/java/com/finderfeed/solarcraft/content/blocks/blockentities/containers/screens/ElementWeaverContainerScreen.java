package com.finderfeed.solarcraft.content.blocks.blockentities.containers.screens;

import com.finderfeed.solarcraft.SolarCraft;
import com.finderfeed.solarcraft.client.particles.screen.SolarStrikeParticleScreen;
import com.finderfeed.solarcraft.client.screens.components.TooltipBoxComponent;
import com.finderfeed.solarcraft.content.blocks.blockentities.ElementWeaverTileEntity;
import com.finderfeed.solarcraft.content.blocks.blockentities.containers.ElementWeaverContainer;
import com.finderfeed.solarcraft.content.items.runic_energy.RunicEnergyCost;
import com.finderfeed.solarcraft.helpers.ClientHelpers;
import com.finderfeed.solarcraft.local_library.client.particles.ScreenParticlesRenderHandler;
import com.finderfeed.solarcraft.local_library.client.screens.DefaultContainerScreen;
import com.finderfeed.solarcraft.local_library.helpers.RenderingTools;
import com.finderfeed.solarcraft.misc_things.RunicEnergy;
import com.finderfeed.solarcraft.registries.ConfigRegistry;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.Gui;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ElementWeaverContainerScreen extends DefaultContainerScreen<ElementWeaverContainer> {

    public static final ResourceLocation GUI = new ResourceLocation(SolarCraft.MOD_ID,"textures/gui/element_weaver.png");

    private int[][] symbolPositions = new int[8][2];

    public ElementWeaverContainerScreen(ElementWeaverContainer menu, Inventory inv, Component cmp) {
        super(menu, inv, cmp);
        inventoryLabelX = 1000000;

    }

    @Override
    protected void init() {
        super.init();
        ElementWeaverTileEntity tile = menu.getTile();


        TooltipBoxComponent zeta = new TooltipBoxComponent(this,relX + 84,relY + 18,16,16,
                (matrix,x,y)->{
                    renderTooltip(matrix,Component.literal("ZETA: " + tile.getRunicEnergy(RunicEnergy.Type.ZETA)),(int)x,(int)y);
                });

        TooltipBoxComponent ardo = new TooltipBoxComponent(this,relX + 118,relY + 32,16,16,
                (matrix,x,y)->{
                    renderTooltip(matrix,Component.literal("ARDO: " + tile.getRunicEnergy(RunicEnergy.Type.ARDO)),(int)x,(int)y);
                });

        TooltipBoxComponent urba = new TooltipBoxComponent(this,relX + 133,relY + 67,16,16,
                (matrix,x,y)->{
                    renderTooltip(matrix,Component.literal("URBA: " + tile.getRunicEnergy(RunicEnergy.Type.URBA)),(int)x,(int)y);
                });

        TooltipBoxComponent kelda = new TooltipBoxComponent(this,relX + 118,relY + 101,16,16,
                (matrix,x,y)->{
                    renderTooltip(matrix,Component.literal("KELDA: " + tile.getRunicEnergy(RunicEnergy.Type.KELDA)),(int)x,(int)y);
                });

        TooltipBoxComponent fira = new TooltipBoxComponent(this,relX + 84,relY + 116,16,16,
                (matrix,x,y)->{
                    renderTooltip(matrix,Component.literal("FIRA: " + tile.getRunicEnergy(RunicEnergy.Type.FIRA)),(int)x,(int)y);
                });

        TooltipBoxComponent tera = new TooltipBoxComponent(this,relX + 50,relY + 101,16,16,
                (matrix,x,y)->{
                    renderTooltip(matrix,Component.literal("TERA: " + tile.getRunicEnergy(RunicEnergy.Type.TERA)),(int)x,(int)y);
                });

        TooltipBoxComponent giro = new TooltipBoxComponent(this,relX + 35,relY + 67,16,16,
                (matrix,x,y)->{
                    renderTooltip(matrix,Component.literal("GIRO: " + tile.getRunicEnergy(RunicEnergy.Type.GIRO)),(int)x,(int)y);
                });

        TooltipBoxComponent ultima = new TooltipBoxComponent(this,relX + 50,relY + 33,16,16,
                (matrix,x,y)->{
                    renderTooltip(matrix,Component.literal("ULTIMA: " + tile.getRunicEnergy(RunicEnergy.Type.ULTIMA)),(int)x,(int)y);
                });
        symbolPositions[0] = new int[]{zeta.getX(),zeta.getY()};
        symbolPositions[1] = new int[]{ardo.getX(),ardo.getY()};
        symbolPositions[2] = new int[]{urba.getX(),urba.getY()};
        symbolPositions[3] = new int[]{kelda.getX(),kelda.getY()};
        symbolPositions[4] = new int[]{fira.getX(),fira.getY()};
        symbolPositions[5] = new int[]{tera.getX(),tera.getY()};
        symbolPositions[6] = new int[]{giro.getX(),giro.getY()};
        symbolPositions[7] = new int[]{ultima.getX(),ultima.getY()};

        this.addFDComponent("zeta",zeta);
        this.addFDComponent("ardo",ardo);
        this.addFDComponent("urba",urba);
        this.addFDComponent("kelda",kelda);
        this.addFDComponent("fira",fira);
        this.addFDComponent("tera",tera);
        this.addFDComponent("giro",giro);
        this.addFDComponent("ultima",ultima);
    }

    @Override
    protected void renderBg(PoseStack matrix, float partialTicks, int mx, int my) {
        this.renderBackground(matrix);
        ClientHelpers.bindText(GUI);
        Gui.blit(matrix,relX,relY,0,0,getScreenWidth(),getScreenHeight(),256,256);

        matrix.pushPose();
        matrix.translate(0,0,200);
        this.renderTooltip(matrix,mx,my);
        this.renderAllComponents(matrix,mx,my,partialTicks);
        matrix.popPose();
    }


    private Item processingItem;
    private RunicEnergyCost processingCost;
    private int time;

    @Override
    public void containerTick() {
        time++;
        super.containerTick();
        ElementWeaverTileEntity tile = menu.getTile();
        ItemStack item = tile.inputSlot();
        if (!item.isEmpty()){
            this.setProcessingItem(item);
            if (processingItem != null) {
                this.spawnParticles();
            }
        }else{
            processingCost = null;
            processingItem = null;
        }
    }

    private void spawnParticles(){
        float centerX = relX + 84 + 7f;
        float centerY = relY + 67 + 7f;
        if (time % 3 == 0) {
            for (RunicEnergy.Type type : processingCost.getSetTypes()) {
                float posX = symbolPositions[type.getIndex()][0] + 7;
                float posY = symbolPositions[type.getIndex()][1] + 7;
                SolarStrikeParticleScreen particle = new SolarStrikeParticleScreen(
                        40, posX, posY,
                        (centerX - posX) * 0.025,
                        (centerY - posY) * 0.025,
                        0, 0, 255, 216, 0, 255
                );
                particle.setSize(10f);
                ScreenParticlesRenderHandler.addParticle(particle);
            }
        }

        for (int i = 0; i < 4; i++){
            float angle = (float)(Math.PI/2f) * i;
            double t = time / (Math.PI*2) * 0.2;
            float x = 62 * (float)Math.sin(angle + t);
            float y = 62 * (float)Math.cos(angle + t);
            SolarStrikeParticleScreen particle = new SolarStrikeParticleScreen(
                    60,centerX + x,centerY + y, 0, 0,
                    0,0,255,216,0,255
            );
            particle.setSize(10f);
            ScreenParticlesRenderHandler.addParticle(particle);
        }
    }

    private void setProcessingItem(ItemStack item){
        if (processingItem == null){
            RunicEnergyCost cost = ConfigRegistry.ITEM_RE_CONFIG.getItemCost(item.getItem());
            if (cost != null){
                processingItem = item.getItem();
                processingCost = cost;
            }else{
                processingCost = null;
                processingItem = null;
            }
        }
    }

    @Override
    public int getScreenWidth() {
        return 182;
    }

    @Override
    public int getScreenHeight() {
        return 228;
    }

}
