package com.finderfeed.solarforge.magic_items.items.solar_lexicon.unlockables;

import com.finderfeed.solarforge.magic_items.items.solar_lexicon.achievements.Progression;

public enum ProgressionStage {
    ;


    public final String ID;
    public final Progression[] PROGRESSIONS;

    ProgressionStage(String id,Progression... progressions){
        this.ID = id;
        this.PROGRESSIONS = progressions;
    }


}
