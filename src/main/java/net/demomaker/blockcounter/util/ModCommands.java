package net.demomaker.blockcounter.util;

import com.mojang.brigadier.CommandDispatcher;
import net.demomaker.blockcounter.common.CommandCountBlocks;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;

public class ModCommands {

    public static void register(CommandDispatcher<CommandSource> dispatcher) {
        dispatcher.register(
                Commands.literal(BlockCounter.MOD_ID)
                        .then(CommandCountBlocks.register(dispatcher))
        );
    }

}
