package com.finderfeed.solarcraft.content.capabilities.solar_lexicon;

import com.finderfeed.solarcraft.content.items.solar_lexicon.unlockables.AncientFragment;
import net.minecraft.nbt.CompoundTag;
import net.neoforged.neoforge.items.ItemStackHandler;

public class LexiconInventory extends ItemStackHandler {


    public LexiconInventory(){
        super(AncientFragment.getAllFragments().size());
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = super.serializeNBT();
        tag.putInt("Size", AncientFragment.getAllFragments().size());
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        nbt.putInt("Size",AncientFragment.getAllFragments().size());
        super.deserializeNBT(nbt);
    }
}
