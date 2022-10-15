package com.finderfeed.solarcraft.content.abilities.ability_classes;

import com.finderfeed.solarcraft.content.abilities.AbilityHelper;
import com.finderfeed.solarcraft.content.items.runic_energy.RunicEnergyCost;
import com.finderfeed.solarcraft.content.entities.projectiles.MagicMissile;
import com.finderfeed.solarcraft.misc_things.RunicEnergy;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;

public class MagicBoltAbility extends AbstractAbility{
    public MagicBoltAbility() {
        super("magic_bolt", new RunicEnergyCost().set(RunicEnergy.Type.ULTIMA,100), 40000);
    }

    @Override
    public void cast(ServerPlayer entity, ServerLevel world) {
        if (AbilityHelper.isAbilityUsable(entity,this,true)){
            Vec3 initPos = entity.position().add(0,entity.getBbHeight()*0.75,0).add(entity.getLookAngle());
            MagicMissile magicMissile = new MagicMissile(world,0,0,0);
            magicMissile.setPos(initPos);
            magicMissile.setSpeedDecrement(0);
            magicMissile.setDamage(5f);
            magicMissile.setDeltaMovement(entity.getLookAngle());
            world.addFreshEntity(magicMissile);
            AbilityHelper.spendAbilityEnergy(entity,this);
        }
    }
}
