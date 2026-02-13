package net.demomaker.blockcounter.util;

import com.mojang.brigadier.CommandDispatcher;
import net.demomaker.blockcounter.common.CommandCountBlocks;
import net.demomaker.blockcounter.common.CommandSetPosition;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

public class ModCommands {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                Commands.literal(BlockCounter.MOD_ID)
                        .then(CommandCountBlocks.register())
                        .then(CommandSetPosition.register())
        );
    }

}
