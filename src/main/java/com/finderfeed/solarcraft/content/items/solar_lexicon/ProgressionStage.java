package com.finderfeed.solarcraft.content.items.solar_lexicon;

import com.finderfeed.solarcraft.content.items.solar_lexicon.progressions.Progression;
import org.apache.commons.lang3.ArrayUtils;


import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Iterator;


public enum ProgressionStage implements Iterable<Progression>{
    PRE_BEGGINING(null,"pre_beggining",Progression.ENTER_NETHER),
    BEGGINING(PRE_BEGGINING,"beggining",Progression.RUNE_ENERGY_PYLON),
    BEGGINING_2(BEGGINING,"beggining_2",Progression.ENTER_END,Progression.INFUSING_CRAFTING_TABLE,Progression.ALL_ENERGY_TYPES),
    PRE_FORGE(BEGGINING_2,"pre_forge",Progression.KILL_DRAGON,Progression.RUNE_ENERGY_CLAIM),
    FORGE(PRE_FORGE,"pre_infuser",Progression.CRAFT_SOLAR_FORGE),
    AFTER_INFUSER(FORGE,"after_infuser",Progression.SOLAR_INFUSER),
    AFTER_CATALYSTS(AFTER_INFUSER,"after_catalysts",Progression.CATALYSTS),
    PRE_LENS(AFTER_CATALYSTS,"pre_lens",Progression.IMBUED_COLD_STAR,Progression.KILL_WITHER),
    AFTER_LENS(PRE_LENS,"after_lens",Progression.RUNIC_ENERGY_REPEATER,Progression.CRAFT_SOLAR_LENS,Progression.TRANSMUTE_GEM),
    SOLAR_ENERGY(AFTER_LENS,"solar_energy",Progression.CRAFT_SOLAR_ENERGY_GENERATOR),
    DIMENSION(SOLAR_ENERGY,"dimension",Progression.DIMENSIONAL_SHARD_DUNGEON),
    CRYSTAL_CONSTRUCT(DIMENSION,"crystal_construct",Progression.KILL_CRYSTAL_BOSS),
    RUNIC_ELEMENTAL(CRYSTAL_CONSTRUCT,"dimension",Progression.KILL_RUNIC_ELEMENTAL)
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

    public static ProgressionStage[] STAGES_IN_ORDER = {
      PRE_BEGGINING,BEGGINING,BEGGINING_2,PRE_FORGE,FORGE,AFTER_INFUSER,AFTER_CATALYSTS,PRE_LENS,AFTER_LENS,SOLAR_ENERGY,DIMENSION,CRYSTAL_CONSTRUCT,RUNIC_ELEMENTAL
    };

    @Nonnull
    @Override
    public Iterator<Progression> iterator() {
        return Arrays.stream(SELF_PROGRESSIONS).iterator();
    }
}
