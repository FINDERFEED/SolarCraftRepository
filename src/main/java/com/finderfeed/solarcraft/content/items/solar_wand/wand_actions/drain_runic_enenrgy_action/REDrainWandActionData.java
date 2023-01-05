package com.finderfeed.solarcraft.content.items.solar_wand.wand_actions.drain_runic_enenrgy_action;

import com.finderfeed.solarcraft.content.items.solar_wand.WandData;
import com.finderfeed.solarcraft.content.items.solar_wand.WandDataSerializer;
import com.finderfeed.solarcraft.misc_things.RunicEnergy;

public class REDrainWandActionData implements WandData<REDrainWandActionData> {

    private RunicEnergy.Type typeToDrain;

    public REDrainWandActionData(RunicEnergy.Type typeToDrain){
        this.typeToDrain = typeToDrain;
    }

    public RunicEnergy.Type getTypeToDrain() {
        return typeToDrain;
    }

    public void setTypeToDrain(RunicEnergy.Type typeToDrain) {
        this.typeToDrain = typeToDrain;
    }

    @Override
    public WandDataSerializer<REDrainWandActionData> getSerializer() {
        return REDrainWandActionDataSerializer.SERIALIZER;
    }
}
