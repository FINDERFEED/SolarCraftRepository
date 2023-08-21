package com.finderfeed.solarcraft.content.commands;

import com.finderfeed.solarcraft.content.abilities.ability_classes.AbstractAbility;
import com.finderfeed.solarcraft.helpers.multiblock.Multiblocks;
import com.finderfeed.solarcraft.registries.abilities.AbilitiesRegistry;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class SCAbilityArgument implements ArgumentType<String> {

    @Override
    public String parse(StringReader reader) throws CommandSyntaxException {
        String current = reader.readString();
        return current;
    }

    @Override
    public Collection<String> getExamples() {
        return AbilitiesRegistry.getAllAbilities().stream().map(AbstractAbility::getId).collect(Collectors.toList());
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
        List<String> ids = AbilitiesRegistry.getAllAbilities().stream().map(AbstractAbility::getId).toList();
        String input = context.getInput().replace("/solarcraft abilities unlock ","");
        input = input.replace("/solarcraft abilities revoke ","");
        for (String s : ids){
            if (s.contains(input)) {
                builder.suggest(s);
            }
        }
        return CompletableFuture.completedFuture(builder.build());
    }
}