package net.demomaker.blockcounter.common;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.demomaker.blockcounter.common.CommandCountBlocks;
import net.demomaker.blockcounter.util.AlgorithmHelper;
import net.minecraft.command.CommandSource;
import net.minecraft.command.arguments.BlockPosArgument;
import net.minecraft.command.arguments.LocationInput;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

public class CommandCountBlocksWithoutItemArgument implements Command<CommandSource> {
    @Override
    public int run(CommandContext<CommandSource> context) throws CommandSyntaxException {
        LocationInput firstPositionLocationInput = context.getArgument(CommandCountBlocks.FIRST_POSITION_ARGUMENT_NAME, LocationInput.class);
        LocationInput secondPositionLocationInput = context.getArgument(CommandCountBlocks.SECOND_POSITION_ARGUMENT_NAME, LocationInput.class);
        BlockPos firstPosition = firstPositionLocationInput.getBlockPos(context.getSource());
        BlockPos secondPosition = secondPositionLocationInput.getBlockPos(context.getSource());
        AlgorithmHelper.SetServerWorld(context.getSource().getServer().overworld());
        context.getSource().sendSuccess(new StringTextComponent(CommandCountBlocks.ALGORITHM.GetStringContainingAllBlockCountsFor(firstPosition, secondPosition, null)), false);
        return 0;
    }
}
