package net.demomaker.blockcounter.common;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.demomaker.blockcounter.util.AlgorithmHelper;
import net.demomaker.blockcounter.util.MessageHelper;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TextComponent;

public class CommandSetPosition implements Command<CommandSourceStack> {
    private static BlockPos storedPosition = null;

    private static final String COMMAND_NAME = "setposition";

    public static final Algorithm ALGORITHM = new Algorithm();

    public static ArgumentBuilder<CommandSourceStack, ?> register() {
        return Commands.literal(COMMAND_NAME)
                .requires(cs -> cs.hasPermission(0))
                .executes(new CommandSetPosition());
    }

    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        try {
            if (context.getSource().getEntity() == null) {
                return 0;
            }

            if(storedPosition == null) {
                storedPosition = context.getSource().getEntity().blockPosition();
                context.getSource().sendSuccess(new TextComponent(MessageHelper.wrapWithModDecorator(createPositionSetMessage("first", storedPosition))), false);
                return 0;
            }

            BlockPos firstPosition = new BlockPos(storedPosition);
            storedPosition = null;
            BlockPos secondPosition = context.getSource().getEntity().blockPosition();

            context.getSource().sendSuccess(new TextComponent(MessageHelper.wrapWithModDecorator(createPositionSetMessage("second", secondPosition))), false);

            AlgorithmHelper.SetServerWorld(context.getSource().getServer().overworld());
            context.getSource().sendSuccess(new TextComponent(ALGORITHM.GetStringContainingAllBlockCountsFor(firstPosition, secondPosition, null)), false);
        }
        catch (Exception e) {
            context.getSource().sendFailure(new TextComponent(MessageHelper.wrapWithModDecorator(e.getMessage())));
        }

        return 0;
    }

    private String createPositionSetMessage(String positionPlacement, BlockPos position) {
        return "set " + positionPlacement + " position to\n(x: " + position.getX() + " , y: " + position.getY() + ", z: " + position.getZ() + ")\n";
    }
}
