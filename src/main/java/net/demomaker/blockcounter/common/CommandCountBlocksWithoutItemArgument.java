package net.demomaker.blockcounter.common;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.demomaker.blockcounter.common.CommandCountBlocks;
import net.demomaker.blockcounter.util.AlgorithmHelper;
import net.minecraft.command.CommandSource;
import net.minecraft.command.arguments.BlockPosArgument;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

public class CommandCountBlocksWithoutItemArgument implements Command<CommandSource> {
    @Override
    public int run(CommandContext<CommandSource> context) throws CommandSyntaxException {
        BlockPos firstPosition = BlockPosArgument.getBlockPos(context, CommandCountBlocks.FIRST_POSITION_ARGUMENT_NAME);
        BlockPos secondPosition = BlockPosArgument.getBlockPos(context, CommandCountBlocks.SECOND_POSITION_ARGUMENT_NAME);
        AlgorithmHelper.SetServerWorld(context.getSource().getServer().getWorld(World.field_234918_g_));
        context.getSource().sendFeedback(new StringTextComponent(CommandCountBlocks.ALGORITHM.GetStringContainingAllBlockCountsFor(firstPosition, secondPosition, null)), false);
        return 0;
    }
}
