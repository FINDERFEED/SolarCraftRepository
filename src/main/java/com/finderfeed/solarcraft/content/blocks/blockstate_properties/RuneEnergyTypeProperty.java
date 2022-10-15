package com.finderfeed.solarcraft.content.blocks.blockstate_properties;

import com.finderfeed.solarcraft.misc_things.RunicEnergy;
import net.minecraft.world.level.block.state.properties.Property;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class RuneEnergyTypeProperty extends Property<RunicEnergy.Type> {
    public RuneEnergyTypeProperty(String p_61692_) {
        super(p_61692_, RunicEnergy.Type.class);
    }

    @Override
    public Collection<RunicEnergy.Type> getPossibleValues() {

        return List.of(RunicEnergy.Type.NONE,RunicEnergy.Type.ARDO, RunicEnergy.Type.FIRA, RunicEnergy.Type.TERA, RunicEnergy.Type.ZETA, RunicEnergy.Type.KELDA, RunicEnergy.Type.URBA
        , RunicEnergy.Type.ULTIMA, RunicEnergy.Type.GIRO);
    }

    @Override
    public String getName(RunicEnergy.Type type) {

        return type.id;
    }

    @Override
    public Optional<RunicEnergy.Type> getValue(String id) {

        return Optional.ofNullable(RunicEnergy.Type.byId(id));
    }
}
