package com.finderfeed.solarcraft.content.items.solar_wand.wand_actions.drain_runic_enenrgy_action;

import com.finderfeed.solarcraft.content.items.solar_wand.WandData;
import com.finderfeed.solarcraft.content.items.solar_wand.WandDataSerializer;
import com.finderfeed.solarcraft.misc_things.RunicEnergy;

public class REDrainWandActionData implements WandData<REDrainWandActionData> {

    private String typeToDrain;

    public REDrainWandActionData(String typeToDrain){
        this.typeToDrain = typeToDrain;
    }

    public String getTypeToDrain() {
        return typeToDrain;
    }

    public void setTypeToDrain(String typeToDrain) {
        this.typeToDrain = typeToDrain;
    }

    @Override
    public WandDataSerializer<REDrainWandActionData> getSerializer() {
        return REDrainWandActionDataSerializer.SERIALIZER;
    }
}
