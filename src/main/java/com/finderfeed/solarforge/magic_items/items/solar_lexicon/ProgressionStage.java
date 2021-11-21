package com.finderfeed.solarforge.magic_items.items.solar_lexicon;

import com.finderfeed.solarforge.magic_items.items.solar_lexicon.achievements.Progression;
import org.apache.commons.lang3.ArrayUtils;


import javax.annotation.Nullable;


public enum ProgressionStage {
    BEGGINING(null,"beggining",Progression.ENTER_NETHER,Progression.RUNE_ENERGY_PYLON,Progression.FIND_INCINERATED_FOREST),
    BEGGINING_2(BEGGINING,"beggining_2",Progression.ENTER_END,Progression.INFUSING_CRAFTING_TABLE),
    PRE_FORGE(BEGGINING_2,"pre_forge",Progression.KILL_DRAGON,Progression.ALL_ENERGY_TYPES,Progression.RUNE_ENERGY_CLAIM),
    FORGE(PRE_FORGE,"pre_infuser",Progression.CRAFT_SOLAR_FORGE),
    AFTER_INFUSER(FORGE,"after_infuser",Progression.SOLAR_INFUSER),
    AFTER_CATALYSTS(AFTER_INFUSER,"after_catalysts",Progression.CATALYSTS),
    PRE_LENS(AFTER_CATALYSTS,"pre_lens",Progression.IMBUED_COLD_STAR,Progression.KILL_WITHER),
    AFTER_LENS(PRE_LENS,"after_lens",Progression.RUNIC_ENERGY_REPEATER,Progression.CRAFT_SOLAR_LENS,Progression.TRANSMUTE_GEM),
    SOLAR_ENERGY(AFTER_LENS,"solar_energy",Progression.CRAFT_SOLAR_ENERGY_GENERATOR),
    DIMENSION(SOLAR_ENERGY,"dimension",Progression.DIMENSIONAL_SHARD_DUNGEON)
    ;


    public final String ID;
    public final Progression[] SELF_PROGRESSIONS;
    public Progression[] ALL_PROGRESSIONS;

    ProgressionStage(@Nullable ProgressionStage extend,String id,Progression... progressions){
        this.ID = id;
        this.SELF_PROGRESSIONS = progressions;
        if (extend != null) {
            if (extend.ALL_PROGRESSIONS != null) {
                ALL_PROGRESSIONS = ArrayUtils.addAll(extend.ALL_PROGRESSIONS, progressions);
            }
        }else{
            this.ALL_PROGRESSIONS = progressions;
        }
    }


}
