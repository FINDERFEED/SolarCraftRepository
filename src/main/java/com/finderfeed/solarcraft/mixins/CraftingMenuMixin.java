package com.finderfeed.solarcraft.mixins;


import com.finderfeed.solarcraft.content.items.solar_lexicon.unlockables.AncientFragment;
import com.finderfeed.solarcraft.content.items.solar_lexicon.unlockables.ProgressionHelper;
import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.inventory.CraftingMenu;
import net.minecraft.world.inventory.ResultContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

//experimental
@Mixin(CraftingMenu.class)
public class CraftingMenuMixin {


    @Inject(method = "slotChangedCraftingGrid", at = @At(value = "INVOKE",target = "Lnet/minecraft/server/network/ServerGamePacketListenerImpl;send(Lnet/minecraft/network/protocol/Packet;)V",
            shift = At.Shift.AFTER))
    private static void slotChangedCraftingGrid(AbstractContainerMenu menu,
                                                  Level world,
                                                  Player player,
                                                  CraftingContainer container,
                                                  ResultContainer result,
                                                  CallbackInfo ci){
        if (!world.isClientSide) {
            Optional<CraftingRecipe> optional = world.getServer().getRecipeManager().getRecipeFor(RecipeType.CRAFTING, container,world);
            if (optional.isPresent()){
                CraftingRecipe recipe = optional.get();
                ItemStack its = recipe.getResultItem();
                AncientFragment fragment = AncientFragment.CRAFTING_TYPE_ITEMS.get(its.getItem());
                if (fragment != null){
                    if (!ProgressionHelper.doPlayerHasFragment(player,fragment)){
                        result.setItem(0, ItemStack.EMPTY);
                        menu.setRemoteSlot(0, ItemStack.EMPTY);
                        ((ServerPlayer)player).connection.send(new ClientboundContainerSetSlotPacket(menu.containerId,menu.incrementStateId(),
                                0,ItemStack.EMPTY));
                    }
                }
            }

        }
    }


}
