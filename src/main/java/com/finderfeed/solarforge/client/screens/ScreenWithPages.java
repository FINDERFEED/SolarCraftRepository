package com.finderfeed.solarforge.client.screens;


import com.finderfeed.solarforge.magic.items.solar_lexicon.screen.StructureScreen;
import com.finderfeed.solarforge.registries.sounds.Sounds;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.sounds.SoundManager;

public abstract class ScreenWithPages extends ScrollableScreen{

    private int currentPage;
    private final int maxPages;

    public ScreenWithPages(int maxPages) {
        this.maxPages = maxPages;
        currentPage = 1;
    }

    @Override
    protected void init() {
        super.init();
        if (getPageButtonsCoords() != null) {
            addRenderableWidget(new ImageButton(relX + getPageButtonsCoords()[0] + 16, relY + getPageButtonsCoords()[1], 16, 16, 0, 0, 0, StructureScreen.BUTTONS, 16, 32, (button) -> {
                nextPage(buttonsShouldResetScroll());
            }) {
                @Override
                public void playDownSound(SoundManager smanager) {
                    smanager.play(SimpleSoundInstance.forUI(Sounds.BUTTON_PRESS2.get(), 1, 1));
                }
            });
            addRenderableWidget(new ImageButton(relX + getPageButtonsCoords()[0] , relY+getPageButtonsCoords()[1], 16, 16, 0, 16, 0, StructureScreen.BUTTONS, 16, 32, (button) -> {
                previousPage(buttonsShouldResetScroll());
            }) {
                @Override
                public void playDownSound(SoundManager smanager) {
                    smanager.play(SimpleSoundInstance.forUI(Sounds.BUTTON_PRESS2.get(), 1, 1));
                }
            });
        }
    }

    public void nextPage(boolean resetScrolls){
        if (currentPage+1 <= maxPages){
            currentPage++;
            if (resetScrolls){
                this.scrollX = 0;
                this.scrollY = 0;
            }
        }
    }

    public void previousPage(boolean resetScrolls){
        if (currentPage-1 >= 1){
            currentPage--;
            if (resetScrolls){
                this.scrollX = 0;
                this.scrollY = 0;
            }
        }
    }


    public int getCurrentPage() {
        return currentPage;
    }


    public int getMaxPages() {
        return maxPages;
    }


    public abstract int[] getPageButtonsCoords();

    public boolean buttonsShouldResetScroll(){
        return false;
    }
}
