package com.finderfeed.solarcraft.content.commands;

import com.finderfeed.solarcraft.helpers.multiblock.MultiblockStructure;
import com.finderfeed.solarcraft.helpers.multiblock.Multiblocks;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.context.ParsedCommandNode;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestion;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import com.mojang.brigadier.tree.CommandNode;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class SolarcraftStructureArgument implements ArgumentType<String> {

    @Override
    public String parse(StringReader reader) throws CommandSyntaxException {
        String current = reader.readString();
        return current;
    }

    @Override
    public Collection<String> getExamples() {
        return Multiblocks.STRUCTURES.keySet();
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
        String input = context.getInput().replace("/solarcraft structure construct ","");
        for (String s : Multiblocks.STRUCTURES.keySet()){
            if (s.contains(input)) {
                builder.suggest(s);
            }
        }
        return CompletableFuture.completedFuture(builder.build());
    }
}
