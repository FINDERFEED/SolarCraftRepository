package com.finderfeed.solarcraft.content.items.solar_wand.wand_actions.drain_runic_enenrgy_action;

import com.finderfeed.solarcraft.SolarCraft;
import com.finderfeed.solarcraft.content.items.solar_wand.WandAction;
import com.finderfeed.solarcraft.content.items.solar_wand.WandActionType;
import com.finderfeed.solarcraft.content.items.solar_wand.WandDataSerializer;
import com.finderfeed.solarcraft.content.items.solar_wand.WandUseContext;
import com.finderfeed.solarcraft.misc_things.RunicEnergy;
import com.finderfeed.solarcraft.registries.items.SolarcraftItems;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class REDrainWandAction implements WandAction<REDrainWandActionData> {

    private static final String[] DRAIN_TYPES = {
            "all",
            RunicEnergy.Type.ARDO.id,
            RunicEnergy.Type.FIRA.id,
            RunicEnergy.Type.TERA.id,
            RunicEnergy.Type.GIRO.id,
            RunicEnergy.Type.URBA.id,
            RunicEnergy.Type.ULTIMA.id,
            RunicEnergy.Type.KELDA.id,
            RunicEnergy.Type.ZETA.id,
    };

    @Override
    public InteractionResult run(WandUseContext context, REDrainWandActionData data) {

        WandActionType actionType = this.getActionType(context.player());
        if (actionType == WandActionType.AIR){

        }else{

        }

        return InteractionResult.SUCCESS;
    }

    @Override
    public WandDataSerializer<REDrainWandActionData> getWandDataSerializer() {
        return REDrainWandActionDataSerializer.SERIALIZER;
    }

    @Override
    public WandActionType getActionType(Player player) {
        return player.isCrouching() ? WandActionType.AIR : WandActionType.ON_USE_TICK;
    }

    @Override
    public Component getActionName() {
        return Component.translatable("solarcraft.wand_action.drain_energy");
    }

    @Override
    public ResourceLocation getRegistryName() {
        return new ResourceLocation(SolarCraft.MOD_ID,"drain_re");
    }

    @Override
    public ItemStack getIcon() {
        return SolarcraftItems.SOLAR_WAND.get().getDefaultInstance();
    }
}
