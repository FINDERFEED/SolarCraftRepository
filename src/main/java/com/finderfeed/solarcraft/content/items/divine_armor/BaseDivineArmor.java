package com.finderfeed.solarcraft.content.items.divine_armor;

import com.finderfeed.solarcraft.SolarCraftTags;
import com.finderfeed.solarcraft.content.items.runic_energy.IRunicEnergyUser;
import com.finderfeed.solarcraft.content.items.runic_energy.ItemRunicEnergy;
import com.finderfeed.solarcraft.content.items.primitive.solacraft_item_classes.SolarcraftArmorItem;
import com.finderfeed.solarcraft.content.items.runic_energy.RunicEnergyCost;
import com.finderfeed.solarcraft.content.items.solar_lexicon.unlockables.AncientFragment;
import com.finderfeed.solarcraft.content.items.solar_lexicon.unlockables.ProgressionHelper;
import com.finderfeed.solarcraft.misc_things.RunicEnergy;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public abstract class BaseDivineArmor extends SolarcraftArmorItem implements IRunicEnergyUser {

    public static final RunicEnergyCost COST = new RunicEnergyCost().set(RunicEnergy.Type.ARDO,2);

    public BaseDivineArmor(ArmorMaterial p_40386_, ArmorItem.Type type, Properties p_40388_, Supplier<AncientFragment> fragmentSupplier) {
        super(p_40386_, type, p_40388_, fragmentSupplier);
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> props) {
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
        return "solarcraft:textures/models/armor/divine_armor.png";
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level p_41422_, List<Component> cmps, TooltipFlag p_41424_) {
        cmps.add(Component.translatable("solarcraft.divine_armor").withStyle(ChatFormatting.GOLD));
        if (stack.getItem() instanceof IRunicEnergyUser item){
            ItemRunicEnergy.addRunicEnergyTextComponents(stack,item,cmps);
        }
        super.appendHoverText(stack, p_41422_, cmps, p_41424_);
    }

    @Override
    public boolean canEquip(ItemStack stack, EquipmentSlot armorType, Entity entity) {

        if (entity instanceof Player player){
            return super.canEquip(stack,armorType,entity) && ProgressionHelper.doPlayerHasFragment(player,AncientFragment.DIVINE_ARMOR);
        }
        return super.canEquip(stack, armorType, entity);
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
