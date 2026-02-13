package net.demomaker.blockcounter.common;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.demomaker.blockcounter.util.AlgorithmHelper;
import net.demomaker.blockcounter.util.MessageHelper;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.commands.arguments.coordinates.WorldCoordinates;
import net.minecraft.commands.arguments.item.ItemArgument;
import net.minecraft.commands.arguments.item.ItemInput;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TextComponent;

import static net.minecraft.commands.Commands.argument;

public class CommandCountBlocks implements Command<CommandSourceStack> {

    private static final CommandCountBlocks CMD = new CommandCountBlocks();
    private static final CommandCountBlocksWithoutItemArgument CMD_WITHOUT_ITEM = new CommandCountBlocksWithoutItemArgument();
    private static final String COMMAND_NAME = "countblocks";

    public static final Algorithm ALGORITHM = new Algorithm();
    public static final String FIRST_POSITION_ARGUMENT_NAME = "first_position";
    public static final String SECOND_POSITION_ARGUMENT_NAME = "second_position";
    public static final String BLOCK_ARGUMENT_NAME = "block_name";

    public static ArgumentBuilder<CommandSourceStack, ?> register() {
        return Commands.literal(COMMAND_NAME)
                .then(argument(FIRST_POSITION_ARGUMENT_NAME, BlockPosArgument.blockPos())
                        .then(argument(SECOND_POSITION_ARGUMENT_NAME, BlockPosArgument.blockPos())
                                .requires(cs -> cs.hasPermission(0))
                                .executes(CMD_WITHOUT_ITEM)
                                .then(argument(BLOCK_ARGUMENT_NAME, ItemArgument.item())
                                        .requires(cs -> cs.hasPermission(0))
                                        .executes(new CommandCountBlocks())
                                )
                        )
                );


    }

    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        try {
            WorldCoordinates firstPositionLocationInput = context.getArgument(CommandCountBlocks.FIRST_POSITION_ARGUMENT_NAME, WorldCoordinates.class);
            WorldCoordinates secondPositionLocationInput = context.getArgument(CommandCountBlocks.SECOND_POSITION_ARGUMENT_NAME, WorldCoordinates.class);
            BlockPos firstPosition = firstPositionLocationInput.getBlockPos(context.getSource());
            BlockPos secondPosition = secondPositionLocationInput.getBlockPos(context.getSource());
            ItemInput item = context.getArgument(BLOCK_ARGUMENT_NAME, ItemInput.class);
            AlgorithmHelper.SetServerWorld(context.getSource().getLevel());
            context.getSource().sendSuccess(new TextComponent(ALGORITHM.GetStringContainingAllBlockCountsFor(firstPosition, secondPosition, item)), false);
        }
        catch (Exception e) {
            context.getSource().sendFailure(new TextComponent(MessageHelper.wrapWithModDecorator(e.getMessage())));
        }

        return 0;
    }
}
