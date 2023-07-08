package com.finderfeed.solarcraft.content.items.solar_lexicon.screen;

import com.finderfeed.solarcraft.SolarCraft;
import com.finderfeed.solarcraft.content.items.solar_lexicon.progressions.Progression;
import com.finderfeed.solarcraft.content.items.solar_lexicon.screen.buttons.ItemStackButton;
import com.finderfeed.solarcraft.content.items.solar_lexicon.screen.buttons.ItemStackTabButton;
import com.finderfeed.solarcraft.content.items.solar_lexicon.unlockables.ProgressionHelper;
import com.finderfeed.solarcraft.content.recipe_types.infusing_crafting.InfusingCraftingRecipe;
import com.finderfeed.solarcraft.content.recipe_types.infusing_new.InfusingRecipe;
import com.finderfeed.solarcraft.content.recipe_types.solar_smelting.SolarSmeltingRecipe;
import com.finderfeed.solarcraft.helpers.ClientHelpers;

import com.finderfeed.solarcraft.local_library.custom_registries.RegistryDelegate;
import com.finderfeed.solarcraft.local_library.helpers.RenderingTools;
import com.finderfeed.solarcraft.content.items.solar_lexicon.unlockables.AncientFragment;
import com.finderfeed.solarcraft.registries.ScreenSuppliers;
import com.finderfeed.solarcraft.registries.SolarCraftClientRegistries;
import com.finderfeed.solarcraft.registries.items.SolarcraftItems;
import com.finderfeed.solarcraft.registries.recipe_types.SolarcraftRecipeTypes;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.MultiBufferSource;
import com.mojang.blaze3d.platform.Lighting;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.resources.ResourceLocation;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.IItemHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;


public class InformationScreen extends Screen {

    public int relX;
    public int relY;
    private final ResourceLocation LOC = new ResourceLocation("solarcraft","textures/gui/solar_lexicon_info_screen_new.png");
//    private InfusingRecipeScreen screen;
//    private InfusingCraftingRecipeScreen screenInfusingCrafting;
//    private CraftingRecipeScreen craftingScreen;

    private Item icon;
    private Screen screen;

    private AncientFragment fragment;
    private int ticker = 0;
    public InformationScreen(AncientFragment fragment,InfusingRecipeScreen screen) {
        super(Component.literal(""));
        this.screen = screen;
        this.icon = SolarcraftItems.INFUSER_ITEM.get();
        this.fragment = fragment;
    }

    public InformationScreen(AncientFragment fragment,InfusingCraftingRecipeScreen screen) {
        super(Component.literal(""));
//        this.screenInfusingCrafting = screen;
        this.screen = screen;
        this.icon = SolarcraftItems.INFUSING_TABLE.get();
        this.fragment = fragment;
    }

    public InformationScreen(AncientFragment fragment,CraftingRecipeScreen screen) {
        super(Component.literal(""));
//        this.craftingScreen = screen;
        this.screen = screen;
        this.icon = Items.CRAFTING_TABLE;
        this.fragment = fragment;
    }

    @Override
    public void tick() {
        super.tick();
        ticker++;
    }

    @Override
    protected void init() {
        ticker = 0;
        int width = minecraft.getWindow().getWidth();
        int height = minecraft.getWindow().getHeight();
        int scale = (int) minecraft.getWindow().getGuiScale();
        this.relX = (width/scale - 183)/2 - 40;
        this.relY = (height - 218*scale)/2/scale;

//        Item i = screen != null ? SolarcraftItems.INFUSER_ITEM.get() : screenInfusingCrafting != null ? SolarcraftItems.INFUSING_TABLE.get() : Items.CRAFTING_TABLE.asItem();


        ItemStackButton button = new ItemStackTabButton(relX+255,relY+25 + 18   ,17,17,(buttons)->{
            if (screen != null) {
                Minecraft.getInstance().setScreen(screen);
            }
        }, icon.getDefaultInstance(),0.7f,(buttons,graphics,b,c)->{
            graphics.renderTooltip(font,Component.translatable("solarcraft.screens.buttons.crafting_recipe"),b,c);

        });
        if (screen != null){
            addRenderableWidget(button);
        }

        int h = 0;

        IItemHandler items = SolarLexiconRecipesScreen.getLexiconInventory();
        if (items != null) {
            List<AncientFragment> refs = new ArrayList<>();
            for (int i = 0; i < items.getSlots();i++){
                ItemStack item = items.getStackInSlot(i);
                AncientFragment iFrag = ProgressionHelper.getFragmentFromItem(item);
                if (fragment.getReferences().contains(iFrag)){
                    refs.add(iFrag);
                }
            }

            for (AncientFragment ref : refs) {
                ItemStackTabButton button1 = new ItemStackTabButton(relX + 255, relY + 25 + 18  + h * 18 + 40, 17, 17, b -> {
                    Minecraft.getInstance().setScreen(getScreenFromFragment(ref));
                }, ref.getIcon().getDefaultInstance(), 0.7f, (buttons, graphics, b, c) -> {
                    graphics.renderTooltip(font, ref.getTranslation(), b, c);
                });
                h++;
                addRenderableWidget(button1);
            }
        }

            addRenderableWidget(new ItemStackTabButton(relX + 255, relY + 28 - 3, 17, 17, (buttons) -> {
                minecraft.setScreen(new SolarLexiconRecipesScreen());
            },
                    Items.CRAFTING_TABLE.getDefaultInstance(), 0.7f, (buttons, graphics, b, c) -> {
                graphics.renderTooltip(font, Component.translatable("solarcraft.screens.buttons.recipes_screen"), b, c);
            }));



        super.init();
    }

    @Override
    public void render(GuiGraphics graphics, int mousex, int mousey, float partialTicks) {
        PoseStack matrices = graphics.pose();
        ClientHelpers.bindText(LOC);
        matrices.pushPose();
        RenderingTools.blitWithBlend(matrices,relX,relY,0,0,256,209,256,256,0,1f);

        graphics.drawString(Minecraft.getInstance().font,fragment.getTranslation(), relX+60,relY+35,0xffffff);
        if (fragment.getType() == AncientFragment.Type.INFORMATION) {
            RenderingTools.drawBoundedTextObfuscated(graphics, relX + 14, relY + 81, 43, fragment.getLore(),SolarLexiconScreen.TEXT_COLOR,ticker*4);
        }else{
            RenderingTools.drawBoundedTextObfuscated(graphics, relX + 14, relY + 81, 43, fragment.getItemDescription(),SolarLexiconScreen.TEXT_COLOR,ticker*4);
        }
        RenderingTools.renderScaledGuiItem(graphics,fragment.getIcon().getDefaultInstance(),relX + 32, relY + 32,1f,0);
        matrices.popPose();
//        renderGuiItem(fragment.getIcon().getDefaultInstance(),relX+32,relY+32,Minecraft.getInstance().getItemRenderer().getModel(fragment.getIcon().getDefaultInstance(),null,null,0),1.5,1.5,1.5);
        super.render(graphics, mousex, mousey, partialTicks);
    }



//    protected void renderGuiItem(ItemStack stack, int tx, int ty, BakedModel model,double x,double y,double z) {
//        Minecraft.getInstance().getTextureManager().getTexture(TextureAtlas.LOCATION_BLOCKS).setFilter(false, false);
//        RenderSystem.setShaderTexture(0, TextureAtlas.LOCATION_BLOCKS);
//        RenderSystem.enableBlend();
//        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
//        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
//        PoseStack posestack = RenderSystem.getModelViewStack();
//        posestack.pushPose();
//        posestack.translate((double)tx, (double)ty, (double)(100.0F + Minecraft.getInstance().getItemRenderer().blitOffset));
//        posestack.translate(8.0D, 8.0D, 0.0D);
//        posestack.scale(1.0F, -1.0F, 1.0F);
//        posestack.scale(16.0F, 16.0F, 16.0F);
//        RenderSystem.applyModelViewMatrix();
//        PoseStack posestack1 = new PoseStack();
//        posestack1.scale((float)x,(float)y,(float)z);
//        MultiBufferSource.BufferSource multibuffersource$buffersource = Minecraft.getInstance().renderBuffers().bufferSource();
//        boolean flag = !model.usesBlockLight();
//        if (flag) {
//            Lighting.setupForFlatItems();
//        }
//
//        Minecraft.getInstance().getItemRenderer().render(stack, ItemTransforms.TransformType.GUI, false, posestack1, multibuffersource$buffersource, 15728880, OverlayTexture.NO_OVERLAY, model);
//        multibuffersource$buffersource.endBatch();
//        RenderSystem.enableDepthTest();
//        if (flag) {
//            Lighting.setupFor3DItems();
//        }
//
//        posestack.popPose();
//        RenderSystem.applyModelViewMatrix();
//    }


    public static Screen getScreenFromFragment(AncientFragment fragment){
        switch (fragment.getType()){
            case ITEMS -> {
                Level world = Minecraft.getInstance().level;
                RecipeType<?> type = fragment.getRecipeType();
                if (type == SolarcraftRecipeTypes.INFUSING.get()){
                    List<InfusingRecipe> recipes = new ArrayList<>();
                    for (AncientFragment.ItemWithRecipe i : fragment.getStacks()){
                        recipes.add((InfusingRecipe) world.getRecipeManager().byKey(i.getRecipeLocation()).orElseThrow());
                    }
                    return new InformationScreen(fragment,new InfusingRecipeScreen(recipes));
                }else if (type == SolarcraftRecipeTypes.INFUSING_CRAFTING.get()){
                    List<InfusingCraftingRecipe> recipes = new ArrayList<>();
                    for (AncientFragment.ItemWithRecipe i : fragment.getStacks()){
                        recipes.add((InfusingCraftingRecipe) world.getRecipeManager().byKey(i.getRecipeLocation()).orElseThrow());
                    }
                    return new InformationScreen(fragment,new InfusingCraftingRecipeScreen(recipes));
                }else if (type == SolarcraftRecipeTypes.SMELTING.get()){
                    SolarSmeltingRecipe r =  (SolarSmeltingRecipe)world.getRecipeManager().byKey(fragment.getStacks().get(0).getRecipeLocation()).orElseThrow();
                    return new SmeltingRecipeScreen(r);
                }else if (type == RecipeType.CRAFTING){
                    List<CraftingRecipe> recipes = new ArrayList<>();
                    for (AncientFragment.ItemWithRecipe i : fragment.getStacks()){
                        recipes.add((CraftingRecipe) world.getRecipeManager().byKey(i.getRecipeLocation()).orElseThrow());
                    }
                    return new InformationScreen(fragment,new CraftingRecipeScreen(recipes));
                }
                return null;
            }
            case INFORMATION -> {
                return new InformationScreen(fragment,(InfusingRecipeScreen) null);
            }
            case CUSTOM -> {
                Supplier<Screen> s = RegistryDelegate.getObject(SolarCraftClientRegistries.SCREENS,new ResourceLocation(SolarCraft.MOD_ID,fragment.getScreenID()));
                return s.get();
            }
            case STRUCTURE -> {
                return new StructureScreen(fragment,fragment.getStructure());
            }
            default -> {
                return null;
            }
        }
    }

}
