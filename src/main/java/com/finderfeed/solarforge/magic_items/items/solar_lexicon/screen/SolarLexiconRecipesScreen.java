package com.finderfeed.solarforge.magic_items.items.solar_lexicon.screen;

import com.finderfeed.solarforge.ClientHelpers;
import com.finderfeed.solarforge.Helpers;
import com.finderfeed.solarforge.for_future_library.RenderingTools;
import com.finderfeed.solarforge.SolarForge;
import com.finderfeed.solarforge.misc_things.IScrollable;
import com.finderfeed.solarforge.misc_things.Multiblock;
import com.finderfeed.solarforge.recipe_types.InfusingRecipe;
import com.finderfeed.solarforge.recipe_types.solar_smelting.SolarSmeltingRecipe;
import com.finderfeed.solarforge.registries.items.ItemsRegister;
import com.finderfeed.solarforge.magic_items.items.solar_lexicon.unlockables.AncientFragment;
import com.finderfeed.solarforge.magic_items.items.solar_lexicon.unlockables.BookEntry;
import com.finderfeed.solarforge.magic_items.items.solar_lexicon.unlockables.ProgressionHelper;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.*;
import java.util.List;

public class SolarLexiconRecipesScreen extends Screen implements IScrollable {
    public final ResourceLocation MAIN_SCREEN = new ResourceLocation("solarforge","textures/gui/solar_lexicon_recipes_page.png");
    public final ResourceLocation FRAME = new ResourceLocation("solarforge","textures/misc/frame.png");
    public final ResourceLocation MAIN_SCREEN_SCROLLABLE = new ResourceLocation("solarforge","textures/gui/solar_lexicon_main_page_scrollablet.png");

    public Map<BookEntry,List<AncientFragment>> map = new HashMap<>();

    private Map<BookEntry,Integer[]> TO_DRAW = new HashMap<>();

    private boolean showNoFragmentsMessage = true;

    public IItemHandler handler;
    public final ItemStackButton goBack = new ItemStackButton(0,10,12,12,(button)->{minecraft.setScreen(new SolarLexiconScreen());}, SolarForge.SOLAR_FORGE_ITEM.get().getDefaultInstance(),0.7f,false);
    public final ItemStackButton nothing = new ItemStackButton(0,10,12,12,(button)->{}, Items.CRAFTING_TABLE.getDefaultInstance(),0.7f,false);



    public Point structures;

    public int scrollX;
    public int scrollY;
    public int prevscrollX;
    public int prevscrollY;
    public int relX;
    public int relY;

    @Override
    public void performScroll(int keyCode) {
        if (keyCode == GLFW.glfwGetKeyScancode(GLFW.GLFW_KEY_LEFT) && !(scrollX -4 < -700)){
            scrollX-=4;
        } else if (keyCode == GLFW.glfwGetKeyScancode(GLFW.GLFW_KEY_UP) && !(scrollY -4 < -700)){
            scrollY-=4;
        }else if(keyCode == GLFW.glfwGetKeyScancode(GLFW.GLFW_KEY_DOWN) && !(scrollY +4 > 0)){
            scrollY+=4;
        }else if (keyCode == GLFW.glfwGetKeyScancode(GLFW.GLFW_KEY_RIGHT)&& !(scrollX +4 > 0)){
            scrollX+=4;
        }

        if (this.prevscrollX != scrollX){
            List<AbstractWidget> list = ClientHelpers.getScreenButtons(this);
            list.remove(goBack);
            list.remove(nothing);
            for (AbstractWidget a : list) {
                if (prevscrollX < scrollX) {
                    a.x += 4;
                } else {
                    a.x -= 4;
                }

            }
            this.prevscrollX = scrollX;
        }
        if (this.prevscrollY != scrollY){
            List<AbstractWidget> list = ClientHelpers.getScreenButtons(this);
            list.remove(goBack);
            list.remove(nothing);
            for (AbstractWidget a : list) {
                if (prevscrollY < scrollY) {

                    a.y += 4;
                } else {

                    a.y -= 4;
                }


            }
            this.prevscrollY = scrollY;
        }
    }

    protected SolarLexiconRecipesScreen() {
        super(new TextComponent(""));
    }


    @Override
    protected void init() {
        super.init();

        handler = getLexiconInventory();
        map = new HashMap<>();
        populateMap();
        int width = minecraft.getWindow().getWidth();
        int height = minecraft.getWindow().getHeight();
        int scale = (int) minecraft.getWindow().getGuiScale();
        this.relX = (width/scale - 183)/2;
        this.relY = (height - 218*scale)/2/scale;
        scrollX = 0;
        scrollY = 0;
        prevscrollX = 0;
        prevscrollY = 0;







        initButtons();
        //doOldThings();
        addRenderableWidget(goBack);
        addRenderableWidget(nothing);
        nothing.x = relX +207;
        nothing.y = relY + 184;
        goBack.x = relX +207;
        goBack.y = relY + 164;
    }



    public void initButtons(){
        map.forEach((entry,list)->{
            for (int i = 0;i < list.size();i++){
                AncientFragment fragment = list.get(i);
                if (Helpers.hasPlayerUnlocked(fragment.getNeededProgression(),Minecraft.getInstance().player)) {
                    if (fragment.getType() == AncientFragment.Type.INFORMATION) {
                        BookEntry parent = entry.getParent();

                        if (parent == null) {

                            addInformationButton(fragment.getIcon().getDefaultInstance(),
                                    relX + entry.getPlaceInBook().x  + (i % 6) * 25,
                                    relY + entry.getPlaceInBook().y  + (int) Math.floor((float) i / 6) * 25,
                                    fragment);
                        } else {
                            addInformationButton(fragment.getIcon().getDefaultInstance(),
                                    relX + parent.getPlaceInBook().x +10 + (i % 6) * 25 + BookEntry.ENTRY_TREE.get(parent).indexOf(entry) * 200,
                                    relY + parent.getPlaceInBook().y + 10 + (int) Math.floor((float) i / 6) * 25,
                                    fragment);
                        }
                    } else if (fragment.getType() == AncientFragment.Type.ITEM) {
                        BookEntry parent = entry.getParent();

                        if (parent == null) {
                            if (fragment.getRecipeType() == SolarForge.INFUSING_RECIPE_TYPE) {
                                addInfusingRecipeButton(fragment,ProgressionHelper.getInfusingRecipeForItem(fragment.getItem().getItem()),
                                        relX + entry.getPlaceInBook().x  + (i % 6) * 25,
                                        relY + entry.getPlaceInBook().y  + (int) Math.floor((float) i / 6) * 25);
                            } else if (fragment.getRecipeType() == SolarForge.SOLAR_SMELTING) {
                                addSmeltingRecipeButton(ProgressionHelper.getSolarSmeltingRecipeForItem(fragment.getItem().getItem()),
                                        relX + entry.getPlaceInBook().x + 10 + (i % 6) * 25,
                                        relY + entry.getPlaceInBook().y + 10 + (int) Math.floor((float) i / 6) * 25);
                            }
                        } else {
                            if (fragment.getRecipeType() == SolarForge.INFUSING_RECIPE_TYPE) {
                                addInfusingRecipeButton(fragment,ProgressionHelper.getInfusingRecipeForItem(fragment.getItem().getItem()),
                                        relX + parent.getPlaceInBook().x + 10 + (i % 6) * 25 + BookEntry.ENTRY_TREE.get(parent).indexOf(entry) * 200,
                                        relY + parent.getPlaceInBook().y + 10 + (int) Math.floor((float) i / 6) * 25);
                            } else if (fragment.getRecipeType() == SolarForge.SOLAR_SMELTING) {
                                addSmeltingRecipeButton(ProgressionHelper.getSolarSmeltingRecipeForItem(fragment.getItem().getItem()),
                                        relX + parent.getPlaceInBook().x + 10 + (i % 6) * 25 + BookEntry.ENTRY_TREE.get(parent).indexOf(entry) * 200,
                                        relY + parent.getPlaceInBook().y + 10 + (int) Math.floor((float) i / 6) * 25);
                            }
                        }
                    } else if (fragment.getType() == AncientFragment.Type.STRUCTURE) {
                        BookEntry parent = entry.getParent();

                        if (parent == null) {

                            addStructureButton(fragment.getStructure().getM(),
                                    relX + entry.getPlaceInBook().x + (i % 6) * 25,
                                    relY + entry.getPlaceInBook().y + (int) Math.floor((float) i / 6),fragment);
                        } else {
                            addStructureButton(fragment.getStructure().getM(),
                                    relX + parent.getPlaceInBook().x + 10 + (i % 6) * 25 + BookEntry.ENTRY_TREE.get(parent).indexOf(entry) * 200,
                                    relY + parent.getPlaceInBook().y + 10 + (int) Math.floor((float) i / 6),fragment);
                        }
                    }else if (fragment.getType() == AncientFragment.Type.UPGRADE) {
                        BookEntry parent = entry.getParent();

                        if (parent == null) {

                                addInfusingRecipeButton(fragment,ProgressionHelper.UPGRADES_INFUSING_RECIPE_MAP.get(fragment.getItem().getItem()),
                                        relX + entry.getPlaceInBook().x  + (i % 6) * 25,
                                        relY + entry.getPlaceInBook().y  + (int) Math.floor((float) i / 6) * 25);

                        } else {

                                addInfusingRecipeButton(fragment,ProgressionHelper.UPGRADES_INFUSING_RECIPE_MAP.get(fragment.getItem().getItem()),
                                        relX + parent.getPlaceInBook().x + 10 + (i % 6) * 25 + BookEntry.ENTRY_TREE.get(parent).indexOf(entry) * 200,
                                        relY + parent.getPlaceInBook().y + 10 + (int) Math.floor((float) i / 6) * 25);

                        }
                    }else if (fragment.getType() == AncientFragment.Type.ITEMS) {
                        BookEntry parent = entry.getParent();

                        if (parent == null) {

                            addInfusingRecipeButton(fragment,
                                    getRecipesForItemList(fragment.getStacks())
                                    ,
                                    relX + entry.getPlaceInBook().x  + (i % 6) * 25,
                                    relY + entry.getPlaceInBook().y  + (int) Math.floor((float) i / 6) * 25);

                        } else {

                            addInfusingRecipeButton(fragment,
                                    getRecipesForItemList(fragment.getStacks())
                                    ,
                                    relX + parent.getPlaceInBook().x + 10 + (i % 6) * 25 + BookEntry.ENTRY_TREE.get(parent).indexOf(entry) * 200,
                                    relY + parent.getPlaceInBook().y + 10 + (int) Math.floor((float) i / 6) * 25);

                        }
                    }
                }

            }

        });
    }

    public void addInfusingRecipeButton(AncientFragment fragment,List<InfusingRecipe> recipe,int x , int y){
        addRenderableWidget(new ItemStackButton(x,y,24,24,(button)->{
            minecraft.setScreen(new InformationScreen(fragment,new InfusingRecipeScreen(recipe)));
        },fragment.getIcon().getDefaultInstance(),1.5f,false,(button,matrices,mx,my)->{
            renderTooltip(matrices,fragment.getTranslation(),mx,my);
        }));
    }


    public void addInfusingRecipeButton(AncientFragment fragment,InfusingRecipe recipe,int x , int y){
        addRenderableWidget(new ItemStackButton(x,y,24,24,(button)->{
            minecraft.setScreen(new InformationScreen(fragment,new InfusingRecipeScreen(recipe)));
        },fragment.getIcon().getDefaultInstance(),1.5f,false,(button,matrices,mx,my)->{
            renderTooltip(matrices,fragment.getTranslation(),mx,my);
        }));
    }


    public void addSmeltingRecipeButton(SolarSmeltingRecipe recipe,int x , int y){
        addRenderableWidget(new ItemStackButton(x,y,24,24,(button)->{
            minecraft.setScreen(new SmeltingRecipeScreen(recipe));
        },recipe.output,1.5f,false,(button,matrices,mx,my)->{
            renderTooltip(matrices,recipe.output.getHoverName(),mx,my);
        }));
    }


    public void addInformationButton(ItemStack logo,int x , int y,AncientFragment fragment){
        addRenderableWidget(new ItemStackButton(x,y,24,24,(button)->{
            minecraft.setScreen(new InformationScreen(fragment,null));
        },logo,1.5f,false, (button,matrices,mx,my)->{
            renderTooltip(matrices,fragment.getTranslation(),mx,my);
        }));
    }

    public void addStructureButton(Multiblock structure,int x , int y,AncientFragment fragment){
        addRenderableWidget(new ItemStackButton(x,y,24,24,(button)->{
            minecraft.setScreen(new StructureScreen(structure));
        },structure.getMainBlock().asItem().getDefaultInstance(),1.5f,false, (button,matrices,mx,my)->{
            renderTooltip(matrices,fragment.getTranslation(),mx,my);
        }));
    }



    private boolean isButtonPressable(int x,int y){
        if (((x + 24 > relX+7) && (x  < relX+7+190)) && ((y + 24 > relY+7) && (y  < relY+7+193))){
            return true;
        }
        return false;
    }

    @Override
    public void render(PoseStack matrices, int mousex, int mousey, float partialTicks) {
        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        int width = minecraft.getWindow().getWidth();
        int height = minecraft.getWindow().getHeight();
        int scale = (int)minecraft.getWindow().getGuiScale();
        GL11.glScissor(width/2-(83*scale),height/2-(89*scale),(188*scale),190*scale);

        ClientHelpers.bindText(MAIN_SCREEN_SCROLLABLE);
        blit(matrices,relX,relY,0,0,256,256);
        //super.render(matrices,mousex,mousey,partialTicks);

        ClientHelpers.bindText(FRAME);

        TO_DRAW.clear();

        map.keySet().forEach((entry)->{

            if (entry.getParent() == null){
                if (Helpers.hasPlayerUnlocked(entry.toUnlock(),ClientHelpers.getClientPlayer())) {

                    drawRectangle(matrices, calculateLength(entry), calculateHeight(entry), new Point(entry.getPlaceInBook().x+relX,entry.getPlaceInBook().y+relY));
                    drawString(matrices, minecraft.font, entry.getTranslation(), relX+entry.getPlaceInBook().x + scrollX, relY+entry.getPlaceInBook().y - 8 + scrollY, 0xffffff);
                }
            }else{
                int cord = BookEntry.ENTRY_TREE.get(entry.getParent()).indexOf(entry);

                BookEntry child = entry;
                //for (BookEntry child : BookEntry.ENTRY_TREE.get(entry.getParent())){
                    if (map.keySet().contains(child)) {
                        if (Helpers.hasPlayerUnlocked(child.toUnlock(), ClientHelpers.getClientPlayer())) {
                            int heightz = calculateHeight(entry);

                            drawRectangle(matrices, calculateLength(child), heightz, new Point(
                                    relX + entry.getParent().getPlaceInBook().x + 9 + cord * 200,
                                    relY + entry.getParent().getPlaceInBook().y + 9
                            ));
                            drawString(matrices, minecraft.font, entry.getTranslation(), relX + entry.getParent().getPlaceInBook().x + 8 + cord * 200 + scrollX, relY + entry.getParent().getPlaceInBook().y + 1 + scrollY, 0xffffff);
//                            cord++;

                        }
                    }
                //}
                if (!TO_DRAW.containsKey(entry.getParent())){
                    int maxEntries = 0;
                    int maxHeight = 0;
                    for (BookEntry children : BookEntry.ENTRY_TREE.get(entry.getParent())) {
                        if (map.keySet().contains(children)) {
                            int entries = BookEntry.ENTRY_TREE.get(entry.getParent()).indexOf(children) + 1;
                            if (entries > maxEntries){
                                maxEntries = entries;
                            }
                            int heights = calculateHeight(children);
                            if (maxHeight < heights){
                                maxHeight = heights;
                            }
                        }
                    }
                    TO_DRAW.put(entry.getParent(), new Integer[]{maxEntries, maxHeight});
                }

            }
        });

        TO_DRAW.forEach(((bookEntry, integers) -> {
            if (Helpers.hasPlayerUnlocked(bookEntry.toUnlock(),ClientHelpers.getClientPlayer())) {
                drawRectangle(matrices, integers[0] * 200, integers[1] + 20, new Point(bookEntry.getPlaceInBook().x + relX, bookEntry.getPlaceInBook().y + relY));
                drawString(matrices, minecraft.font, bookEntry.getTranslation(), relX + bookEntry.getPlaceInBook().x + scrollX, relY + bookEntry.getPlaceInBook().y - 8 + scrollY, 0xffffff);
            }
        }));
        super.render(matrices,mousex,mousey,partialTicks);
        if (showNoFragmentsMessage){
            drawString(matrices,font,"No fragments present :(",relX+20+scrollX,relY+40+scrollY,0xffffff);
        }
        GL11.glDisable(GL11.GL_SCISSOR_TEST);

        ClientHelpers.bindText(MAIN_SCREEN);
        blit(matrices,relX,relY,0,0,256,256);

        goBack.render(matrices,mousex,mousey,partialTicks);
        nothing.render(matrices,mousex,mousey,partialTicks);

        this.renderables.forEach((widget)->{
            if (widget instanceof AbstractWidget button){
                button.active = isButtonPressable(button.x, button.y);
            }
        });
//        List<Widget> list = this.buttons;
//        list.remove(goBack);
//        list.remove(nothing);



    }



    public int calculateLength(BookEntry entry){

        if (map.containsKey(entry) && map.get(entry).size() >= 6){
            return 6*25;
        }

        return map.containsKey(entry) ? map.get(entry).size()*25 : 0;
    }

    public int calculateHeight(BookEntry entry){
        return map.containsKey(entry) ? 25+(int)Math.floor((float)map.get(entry).size()/6)*25 : 25;
    }


    public void drawRectangle(PoseStack matrices,int x,int y,Point p){
        RenderingTools.drawLine(matrices,p.x+scrollX,p.y+scrollY,p.x+x+scrollX,p.y+scrollY,255,255,255);
        RenderingTools.drawLine(matrices,p.x+x+scrollX,p.y+scrollY,p.x+x+scrollX,p.y+y+scrollY,255,255,255);
        RenderingTools.drawLine(matrices,p.x+scrollX,p.y+y+scrollY,p.x+x+scrollX,p.y+y+scrollY,255,255,255);
        RenderingTools.drawLine(matrices,p.x+scrollX,p.y+scrollY,p.x+scrollX,p.y+y+scrollY,255,255,255);
    }


    public void drawCategoryString(PoseStack matrices,int count,TranslatableComponent translationTextComponent,Point point){
        int x;
        if (count <= 6) {
            x = (count) * 25;
        }else{
            x = 25*6;
        }
        int y = (int)((Math.floor((float)count/6)+1))*25;
        drawRectangle(matrices,x,y,point);
        drawString(matrices,minecraft.font,translationTextComponent,point.x+scrollX,point.y-8+scrollY,0xffffff);
    }


    public IItemHandler getLexiconInventory(){
        return Minecraft.getInstance().player.getMainHandItem().getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElse(null);
    }

    private void populateMap(){
        for (int i = 0;i < handler.getSlots();i++){
            ItemStack stack = handler.getStackInSlot(i);
            if (stack.getItem() == ItemsRegister.INFO_FRAGMENT.get()){
                if (stack.getTagElement(ProgressionHelper.TAG_ELEMENT) != null) {
                    showNoFragmentsMessage = false;
                    AncientFragment frag = AncientFragment.getFragmentByID(stack.getTagElement(ProgressionHelper.TAG_ELEMENT).getString(ProgressionHelper.FRAG_ID));
                    if (frag != null){
                        if (map.containsKey(frag.getEntry())){
                            map.get(frag.getEntry()).add(frag);
                        }else{
                            List<AncientFragment> fraglist = new ArrayList<>();
                            fraglist.add(frag);
                            map.put(frag.getEntry(),fraglist);
                        }
                    }
                }
            }
        }
    }


    private List<InfusingRecipe> getRecipesForItemList(List<ItemStack> stacks){
        List<InfusingRecipe> recipes = new ArrayList<>();
        stacks.forEach((stack)->{
            recipes.add(ProgressionHelper.INFUSING_RECIPE_MAP.get(stack.getItem()));
        });
        return recipes;
    }







//        drawCategoryString(matrices,armorRecipeCount,new TranslationTextComponent("solar_category.armor"),armorCategory);
//        drawCategoryString(matrices,magicRecipesCount,new TranslationTextComponent("solar_category.magic_items"),magicItemsCategory);
//        drawCategoryString(matrices,magicMaterialsCount,new TranslationTextComponent("solar_category.materials"),magicMaterialsCategory);
//        drawCategoryString(matrices,magicToolsCount,new TranslationTextComponent("solar_category.tools"),magicToolsCategory);
//        drawCategoryString(matrices,smeltingRecipeCount,new TranslationTextComponent("solar_category.smelting"),smeltingCategory);
//        drawCategoryString(matrices,structuresCount,new TranslationTextComponent("solar_category.structures"),structures);
//        drawCategoryString(matrices,upgradingRecipesCount,new TranslationTextComponent("solar_category.upgrade"),upgradingRecipesCategory);







//    private void doOldThings(){
//        armorCategory = new Point(relX+20,relY+40);
//        armorRecipeCount = 0;
//
//        magicItemsCategory = new Point(relX+20,relY+150);
//        magicRecipesCount = 0;
//
//        magicMaterialsCategory = new Point(relX+20,relY+260);
//        magicMaterialsCount = 0;
//
//        magicToolsCategory = new Point(relX+180,relY+40);
//        magicToolsCount = 0;
//
//        smeltingCategory = new Point(relX+180,relY+150);
//        smeltingRecipeCount = 0;
//
//        structures = new Point(relX+180,relY+260);
//        structuresCount = 0;
//
//        upgradingRecipesCategory = new Point(relX+360,relY+40);
//        upgradingRecipesCount = 0;
//
//        List<InfusingRecipe> recipe = minecraft.level.getRecipeManager().getAllRecipesFor(SolarForge.INFUSING_RECIPE_TYPE);
//        List<SolarSmeltingRecipe> recipeSmelt = minecraft.level.getRecipeManager().getAllRecipesFor(SolarForge.SOLAR_SMELTING);
//        for (InfusingRecipe a :recipe){
//            if (a.category.equals("solar_category.armor")){
//                if (minecraft.player.getPersistentData().getBoolean(a.child)){
//                    addButton(new ItemStackButton(armorCategory.x+armorRecipeCount*25,armorCategory.y + (int)Math.floor(((double)armorRecipeCount/6))*25,24,24,(button)->{
//                        minecraft.setScreen(new InfusingRecipeScreen(a));
//                    },a.output,1.5f,false));
//                    armorRecipeCount++;
//
//                }
//            }else if (a.category.equals("solar_category.magic_items")){
//                if (minecraft.player.getPersistentData().getBoolean(a.child)){
//                    addButton(new ItemStackButton(magicItemsCategory.x+magicRecipesCount*25 -(int)Math.floor(((double)magicRecipesCount/6))*25*6,magicItemsCategory.y + (int)Math.floor(((double)magicRecipesCount/6))*25,24,24,(button)->{
//                        minecraft.setScreen(new InfusingRecipeScreen(a));
//                    },a.output,1.5f,false));
//                    magicRecipesCount++;
//
//                }
//            }else if (a.category.equals("solar_category.materials")){
//                if (minecraft.player.getPersistentData().getBoolean(a.child)){
//                    addButton(new ItemStackButton(magicMaterialsCategory.x+magicMaterialsCount*25 -(int)Math.floor(((double)magicMaterialsCount/6))*25*6 ,magicMaterialsCategory.y + (int)Math.floor(((double)magicMaterialsCount/6))*25,24,24,(button)->{
//                        minecraft.setScreen(new InfusingRecipeScreen(a));
//                    },a.output,1.5f,false));
//                    magicMaterialsCount++;
//
//                }
//            }else if (a.category.equals("solar_category.tools")){
//                if (minecraft.player.getPersistentData().getBoolean(a.child)){
//                    addButton(new ItemStackButton(magicToolsCategory.x+magicToolsCount*25 -(int)Math.floor(((double)magicToolsCount/6))*25*6,magicToolsCategory.y + (int)Math.floor(((double)magicToolsCount/6))*25,24,24,(button)->{
//                        minecraft.setScreen(new InfusingRecipeScreen(a));
//                    },a.output,1.5f,false));
//                    magicToolsCount++;
//
//                }
//            }else if (a.category.equals("solar_category.upgrade")){
//                if (minecraft.player.getPersistentData().getBoolean(a.child)){
//                    addButton(new ItemStackButton(upgradingRecipesCategory.x+upgradingRecipesCount*25 -(int)Math.floor(((double)upgradingRecipesCount/6))*25*6,upgradingRecipesCategory.y + (int)Math.floor(((double)upgradingRecipesCount/6))*25,24,24,(button)->{
//                        minecraft.setScreen(new InfusingRecipeScreen(a));
//                    },a.output,1.5f,false));
//                    upgradingRecipesCount++;
//
//                }
//            }
//
//
//
//        }
//        for (SolarSmeltingRecipe a :recipeSmelt){
//            if (Helpers.hasPlayerUnlocked(Achievement.CRAFT_SOLAR_LENS, minecraft.player)){
//                addButton(new ItemStackButton(smeltingCategory.x+smeltingRecipeCount*25 -(int)Math.floor(((double)smeltingRecipeCount/6))*25*6,smeltingCategory.y + (int)Math.floor(((double)smeltingRecipeCount/6))*25,24,24,(button)->{
//                    minecraft.setScreen(new SmeltingRecipeScreen(a));
//                },a.output,1.5f,false));
//                smeltingRecipeCount++;
//            }
//        }
//
//        for (Multiblocks a : Multiblocks.ALL_STRUCTURES){
//            Multiblock b = a.getM();
//            if (Helpers.hasPlayerUnlocked(b.reqAchievement, minecraft.player)){
//                addButton(new ItemStackButton(structures.x+structuresCount*25 - (int)Math.floor(((double)structuresCount/6))*25*6,structures.y + (int)Math.floor(((double)structuresCount/6))*25,24,24,(button)->{
//                    minecraft.setScreen(new StructureScreen(b));
//                },b.getMainBlock().asItem().getDefaultInstance(),1.5f,false));
//                structuresCount++;
//            }
//        }
//    }
}
