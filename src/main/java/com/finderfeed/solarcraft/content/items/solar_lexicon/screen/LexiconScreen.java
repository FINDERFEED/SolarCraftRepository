package com.finderfeed.solarcraft.content.items.solar_lexicon.screen;

import com.finderfeed.solarcraft.local_library.client.screens.DefaultScreen;
import com.finderfeed.solarcraft.local_library.client.screens.DefaultScreenWithPages;

public abstract class LexiconScreen extends DefaultScreenWithPages {

    public LexiconScreen(){

    }


    @Override
    public int getPagesCount() {
        return 0;
    }

    @Override
    public void onPageChanged(int newPage) {

    }
}
