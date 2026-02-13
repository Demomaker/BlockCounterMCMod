package net.demomaker.blockcounter.common;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.demomaker.blockcounter.util.AlgorithmHelper;
import net.demomaker.blockcounter.util.MessageHelper;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.commands.arguments.coordinates.WorldCoordinates;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TextComponent;

public class CommandCountBlocksWithoutItemArgument implements Command<CommandSourceStack> {
    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        try {
            WorldCoordinates firstPositionLocationInput = context.getArgument(CommandCountBlocks.FIRST_POSITION_ARGUMENT_NAME, WorldCoordinates.class);
            WorldCoordinates secondPositionLocationInput = context.getArgument(CommandCountBlocks.SECOND_POSITION_ARGUMENT_NAME, WorldCoordinates.class);
            BlockPos firstPosition = firstPositionLocationInput.getBlockPos(context.getSource());
            BlockPos secondPosition = secondPositionLocationInput.getBlockPos(context.getSource());
            AlgorithmHelper.SetServerWorld(context.getSource().getLevel());
            context.getSource().sendSuccess(new TextComponent(CommandCountBlocks.ALGORITHM.GetStringContainingAllBlockCountsFor(firstPosition, secondPosition, null)), false);
        }
        catch (Exception e) {
            context.getSource().sendFailure(new TextComponent(MessageHelper.wrapWithModDecorator(e.getMessage())));
        }

        return 0;
    }
}
