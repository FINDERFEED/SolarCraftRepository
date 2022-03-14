package com.finderfeed.solarforge.magic.items;

import com.finderfeed.solarforge.ClientHelpers;
import com.finderfeed.solarforge.Helpers;
import com.finderfeed.solarforge.entities.BallLightningProjectile;
import com.finderfeed.solarforge.magic.items.primitive.RareSolarcraftItem;
import com.finderfeed.solarforge.magic.items.solar_lexicon.unlockables.AncientFragment;
import com.finderfeed.solarforge.misc_things.ManaConsumer;
import com.finderfeed.solarforge.registries.entities.EntityTypes;
import com.finderfeed.solarforge.registries.sounds.Sounds;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Supplier;

public class LightningGun extends RareSolarcraftItem implements ManaConsumer {

    private static int MANA_COST = 50;

    public LightningGun(Properties p_41383_, Supplier<AncientFragment> fragmentSupplier) {
        super(p_41383_, fragmentSupplier);
    }


    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        if (!world.isClientSide && hand == InteractionHand.MAIN_HAND){
            if (Helpers.canCast(player,MANA_COST)){
                if (!player.isCreative()){
                    player.getCooldowns().addCooldown(this,60);
                }
                world.playSound(player,player.getX(),player.getY()+0.5f,player.getZ(),
                        Sounds.LIGHTNING_GUN_SHOT.get(),SoundSource.PLAYERS,1,10);
                BallLightningProjectile p = new BallLightningProjectile(EntityTypes.BALL_LIGHTNING.get(),world);
                p.setPos(player.getX(),player.getY() + player.getEyeHeight(player.getPose())* 0.8,player.getZ());
                p.setDeltaMovement(player.getLookAngle().multiply(0.7,0.7,0.7));
                world.addFreshEntity(p);
                Helpers.spendMana(player,MANA_COST);
            }
        }
        if (world.isClientSide){
            ClientHelpers.playsoundInEars(Sounds.LIGHTNING_GUN_SHOT.get(),1,10);
        }
        return super.use(world, player, hand);
    }


    @Override
    public void appendHoverText(ItemStack p_41421_, @Nullable Level p_41422_, List<Component> components, TooltipFlag p_41424_) {

        components.add(new TranslatableComponent("solarcraft.lightning_gun").withStyle(ChatFormatting.GOLD));
        super.appendHoverText(p_41421_, p_41422_, components, p_41424_);
    }

    @Override
    public double getManacost() {
        return MANA_COST;
    }
}
