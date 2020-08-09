package net.demomaker.blockcounter.common;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.command.arguments.ItemInput;
import net.minecraft.util.math.BlockPos;

import java.util.HashMap;
import java.util.Map;

public class Algorithm {
    public Map<String, Integer> CountBlocks(BlockPos firstPosition, BlockPos secondPosition, ItemInput itemInput)
    {
        String item = "";
            if(itemInput != null)
            {
                item = itemInput.getItem().getRegistryName().toString();
            }
        return GetAmountOfBlocks(firstPosition, secondPosition, item);
    }

    public Map<String, Integer> GetAmountOfBlocks(BlockPos firstPosition,BlockPos secondPosition, String blockName)
    {
        Map<String, Integer> blockCounts = new HashMap<String, Integer>();
        ClientWorld clientWorld = Minecraft.getInstance().world;
        String currentBlockName = "";
        Block currentBlock = null;
        int smallestX = firstPosition.getX();
        int smallestY = firstPosition.getY();
        int smallestZ = firstPosition.getZ();
        int highestX = secondPosition.getX();
        int highestY = secondPosition.getY();
        int highestZ = secondPosition.getZ();
        if(highestX < smallestX) {
            int temp = highestX;
            highestX = smallestX;
            smallestX = temp;
        }
        if(highestY < smallestY) {
            int temp = highestY;
            highestY = smallestY;
            smallestY = temp;
        }
        if(highestZ < smallestZ) {
            int temp = highestZ;
            highestZ = smallestZ;
            smallestZ = temp;
        }

        for(int x = smallestX; x <= highestX; x++) {
            for(int y = smallestY; y <= highestY; y++) {
                for(int z = smallestZ; z <= highestZ; z++) {
                    currentBlock = clientWorld.getBlockState(new BlockPos(x, y, z)).getBlock();
                    currentBlockName = currentBlock.asItem().getItem().getName().getString();
                    if((blockName.equals("") || currentBlockName.equals(blockName)))
                    {
                        if(blockCounts.containsKey(currentBlockName))
                            blockCounts.replace(currentBlockName, blockCounts.get(currentBlockName) + 1);
                        else
                            blockCounts.putIfAbsent(currentBlockName, 1);
                    }
                }
            }
        }
        return blockCounts;
    }

    public String GetStringContainingAllBlockCountsFor(BlockPos firstPosition, BlockPos secondPosition, ItemInput itemInput)
    {
        String item = "";
        if(itemInput != null)
        {
            item = itemInput.getItem().getName().getString();
        }
        Map<String, Integer> localBlockCounts = GetAmountOfBlocks(firstPosition, secondPosition, item);
        String returnString = "";
        for(String key : localBlockCounts.keySet()) {
            returnString += key + " : " + localBlockCounts.get(key).toString() + " \n";
        }
        return returnString;
    }
}
