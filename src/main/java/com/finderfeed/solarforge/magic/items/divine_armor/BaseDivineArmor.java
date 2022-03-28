package com.finderfeed.solarforge.magic.items.divine_armor;

import com.finderfeed.solarforge.SolarCraftTags;
import com.finderfeed.solarforge.magic.items.runic_energy.IRunicEnergyUser;
import com.finderfeed.solarforge.magic.items.runic_energy.ItemRunicEnergy;
import com.finderfeed.solarforge.magic.items.primitive.solacraft_item_classes.SolarcraftArmorItem;
import com.finderfeed.solarforge.magic.items.runic_energy.RunicEnergyCost;
import com.finderfeed.solarforge.magic.items.solar_lexicon.unlockables.AncientFragment;
import com.finderfeed.solarforge.misc_things.RunicEnergy;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.IItemRenderProperties;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public abstract class BaseDivineArmor extends SolarcraftArmorItem implements IRunicEnergyUser {

    public static final RunicEnergyCost COST = new RunicEnergyCost().set(RunicEnergy.Type.ARDO,2);

    public BaseDivineArmor(ArmorMaterial p_40386_, EquipmentSlot p_40387_, Properties p_40388_, Supplier<AncientFragment> fragmentSupplier) {
        super(p_40386_, p_40387_, p_40388_, fragmentSupplier);
    }

    @Override
    public void initializeClient(Consumer<IItemRenderProperties> props) {
        super.initializeClient(props);
        props.accept(DivineArmorRenderProperties.INSTANCE);
    }

    public int getTick(ItemStack stack){
        return stack.getOrCreateTagElement(SolarCraftTags.DIVINE_ARMOR_TAG).getInt("tick");
    }

    public void tick(ItemStack stack){
        int tick = getTick(stack);
        if (tick < 10){
            stack.getOrCreateTagElement(SolarCraftTags.DIVINE_ARMOR_TAG).putBoolean("direction",true);
            stack.getOrCreateTagElement(SolarCraftTags.DIVINE_ARMOR_TAG).putInt("tick",tick+1);
        }
    }

    public void tickBackwards(ItemStack stack){
        int tick = getTick(stack);
        if (tick > 0){
            stack.getOrCreateTagElement(SolarCraftTags.DIVINE_ARMOR_TAG).putBoolean("direction",false);
            stack.getOrCreateTagElement(SolarCraftTags.DIVINE_ARMOR_TAG).putInt("tick",tick-1);
        }
    }

    @Nullable
    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        return "solarforge:textures/models/armor/divine_armor.png";
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level p_41422_, List<Component> cmps, TooltipFlag p_41424_) {
        if (stack.getItem() instanceof IRunicEnergyUser item){
            ItemRunicEnergy.addRunicEnergyTextComponents(stack,item,cmps);
        }
        super.appendHoverText(stack, p_41422_, cmps, p_41424_);
    }

    @Override
    public RunicEnergyCost getCost() {
        return COST;
    }

    @Override
    public List<RunicEnergy.Type> allowedInputs() {
        return List.of(RunicEnergy.Type.ARDO);
    }

    @Override
    public float getMaxRunicEnergyCapacity() {
        return 100000;
    }
}
