package com.finderfeed.solarcraft.content.abilities.ability_classes;

import com.finderfeed.solarcraft.SolarCraft;
import com.finderfeed.solarcraft.content.abilities.AbilityHelper;
import com.finderfeed.solarcraft.content.abilities.solar_strike.SolarStrikeEntity;
import com.finderfeed.solarcraft.content.items.runic_energy.RunicEnergyCost;
import com.finderfeed.solarcraft.misc_things.RunicEnergy;
import com.finderfeed.solarcraft.registries.entities.SCEntityTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.server.level.ServerLevel;

public class SolarStrikeAbility extends AbstractAbility{
    public SolarStrikeAbility() {
        super("solar_strike",new RunicEnergyCost()
        .set(RunicEnergy.Type.FIRA,5000)
        .set(RunicEnergy.Type.ARDO,2000)
        .set(RunicEnergy.Type.KELDA,1000),100000);
    }

    @Override
    public void cast(ServerPlayer entity, ServerLevel world) {
        if (AbilityHelper.isAbilityUsable(entity,this,true)) {

            Vec3 vec = entity.getLookAngle().multiply(200, 200, 200);
            ClipContext ctx = new ClipContext(entity.position().add(0, 1.5, 0), entity.position().add(0, 1.5, 0).add(vec), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, entity);
            BlockHitResult result = world.clip(ctx);

            if (result.getType() == HitResult.Type.BLOCK) {

                BlockPos pos = result.getBlockPos();
                if (world.canSeeSky(pos.above())) {
                    SolarStrikeEntity entityBolt = new SolarStrikeEntity(SCEntityTypes.SOLAR_STRIKE_ENTITY_REG.get(), world);
                    entityBolt.setPos(pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5);
                    world.addFreshEntity(entityBolt);
                    AbilityHelper.spendAbilityEnergy(entity,this);
                    entityBolt.setOwner(entity);
                }/*else{*/
//                    if (!entity.isCreative()) {
//                        AbilityHelper.refundEnergy(entity,this);
//                    }
//                }
            }

        }
    }
}
