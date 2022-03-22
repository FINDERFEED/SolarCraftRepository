package com.finderfeed.solarforge.magic.items.divine_armor;

import com.finderfeed.solarforge.SolarCraftTags;
import com.finderfeed.solarforge.magic.items.primitive.solacraft_item_classes.SolarcraftArmorItem;
import com.finderfeed.solarforge.magic.items.solar_lexicon.unlockables.AncientFragment;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.IItemRenderProperties;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class BaseDivineArmor extends SolarcraftArmorItem {


    public BaseDivineArmor(ArmorMaterial p_40386_, EquipmentSlot p_40387_, Properties p_40388_, Supplier<AncientFragment> fragmentSupplier) {
        super(p_40386_, p_40387_, p_40388_, fragmentSupplier);
    }


    @Override
    public void inventoryTick(ItemStack stack, Level p_41405_, Entity e, int p_41407_, boolean p_41408_) {
        super.inventoryTick(stack, p_41405_, e, p_41407_, p_41408_);
        if (stack.getItem() instanceof SolarcraftArmorItem armor) if (armor.getSlot() != EquipmentSlot.CHEST) return;
        if (e instanceof Player player){
            if (player.getAbilities().flying){
                tick(stack);
            }else {
                tickBackwards(stack);
            }
        }
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
}
