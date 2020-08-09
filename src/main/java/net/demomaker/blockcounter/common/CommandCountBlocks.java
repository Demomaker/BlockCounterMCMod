package net.demomaker.blockcounter.common;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.BlockPosArgument;
import net.minecraft.command.arguments.ItemArgument;
import net.minecraft.command.arguments.ItemInput;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;

import static com.mojang.brigadier.arguments.IntegerArgumentType.integer;
import static net.minecraft.command.Commands.argument;

public class CommandCountBlocks implements Command<CommandSource> {

    private static final CommandCountBlocks CMD = new CommandCountBlocks();
    private static final CommandCountBlocksWithoutItemArgument CMD_WITHOUT_ITEM = new CommandCountBlocksWithoutItemArgument();
    private static final String COMMAND_NAME = "countblocks";

    public static final Algorithm ALGORITHM = new Algorithm();
    public static final String FIRST_POSITION_ARGUMENT_NAME = "first_position";
    public static final String SECOND_POSITION_ARGUMENT_NAME = "second_position";
    public static final String BLOCK_ARGUMENT_NAME = "block_name";

    public static ArgumentBuilder<CommandSource, ?> register(CommandDispatcher<CommandSource> dispatcher) {
        return Commands.literal(COMMAND_NAME)
                .then(argument(FIRST_POSITION_ARGUMENT_NAME, BlockPosArgument.blockPos())
                        .then(argument(SECOND_POSITION_ARGUMENT_NAME, BlockPosArgument.blockPos())
                                .requires(cs -> cs.hasPermissionLevel(0))
                                .executes(CMD_WITHOUT_ITEM)
                                .then(argument(BLOCK_ARGUMENT_NAME, ItemArgument.item())
                                        .requires(cs -> cs.hasPermissionLevel(0))
                                        .executes(new CommandCountBlocks())
                                )
                        )
                );


    }

    @Override
    public int run(CommandContext<CommandSource> context) throws CommandSyntaxException {
        BlockPos firstPosition = BlockPosArgument.getBlockPos(context, FIRST_POSITION_ARGUMENT_NAME);
        BlockPos secondPosition = BlockPosArgument.getBlockPos(context, SECOND_POSITION_ARGUMENT_NAME);
        ItemInput item = ItemArgument.getItem(context, BLOCK_ARGUMENT_NAME);
        context.getSource().sendFeedback(new StringTextComponent("Number Of Blocks : \n" + ALGORITHM.GetStringContainingAllBlockCountsFor(firstPosition, secondPosition, item)), false);
        return 0;
    }
}
