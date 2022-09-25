package com.finderfeed.solarforge.registries;

import com.mojang.brigadier.arguments.BoolArgumentType;
import net.minecraft.world.level.GameRules;

import static net.minecraft.world.level.GameRules.register;

public class SolarcraftGamerules {

    public static final GameRules.Key<GameRules.BooleanValue> SPELL_GRIEFING =
            register("solarcraftSpellGriefing",
            GameRules.Category.PLAYER,
            GameRules.BooleanValue.create(true));



    public static void init(){}
}
