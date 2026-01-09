package net.demomaker.blockcounter.common;

import net.demomaker.blockcounter.util.AlgorithmHelper;
import net.minecraft.block.Block;
import net.minecraft.command.arguments.ItemInput;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;

import java.util.*;

public class Algorithm {
    public String GetStringContainingAllBlockCountsFor(BlockPos firstPosition, BlockPos secondPosition, ItemInput itemInput)
    {
        String item = "";
        if(itemInput != null)
        {
            item = AlgorithmHelper.GetItemNameFromBlockName(Block.byItem(itemInput.getItem()).getDescriptionId()).getString();
        }
        Map<String, Integer> localBlockCounts = GetAmountOfBlocks(firstPosition, secondPosition, item);
        StringBuilder returnString = new StringBuilder();
        for(String key : localBlockCounts.keySet()) {
            returnString.append(key).append(" : ").append(localBlockCounts.get(key).toString()).append(" \n");
        }

        return "===[BlockCounter]===\n"
                + "Number Of Blocks : " + "\n"
                + returnString
                + "-------------------\n"
                + "Total : " + AlgorithmHelper.GetTotal() + "\n"
                + "===================\n";
    }

    public Map<String, Integer> GetAmountOfBlocks(BlockPos firstPosition, BlockPos secondPosition, String blockName)
    {
        Map<String, Integer> blockCounts = new HashMap<String, Integer>();
        int total = 0;
        List<BlockPos> blockCoordinatesToIgnore = new ArrayList<>();
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
                    BlockPos currentBlockPos = new BlockPos(x, y, z);
                    if(blockCoordinatesToIgnore.contains(currentBlockPos)) {
                        continue;
                    }
                    ItemName currentItemName = getItemNameAt(currentBlockPos);

                    if((blockName.isEmpty() || currentItemName.equals(blockName)))
                    {
                        total++;
                        blockCoordinatesToIgnore.addAll(ignoreCoordinatesOfCoupledBlock(currentBlockPos, currentItemName, blockCoordinatesToIgnore));

                        if(blockCounts.containsKey(currentItemName.getString()))
                            blockCounts.replace(currentItemName.getString(), blockCounts.get(currentItemName.getString()) + 1);
                        else
                            blockCounts.putIfAbsent(currentItemName.getString(), 1);
                    }
                }
            }
        }

        AlgorithmHelper.SetTotal(total);
        return blockCounts;
    }

    private List<BlockPos> ignoreCoordinatesOfCoupledBlock(BlockPos blockPos, ItemName itemName, List<BlockPos> existingIgnoredCoordinates) {
        int x = blockPos.getX();
        int y = blockPos.getY();
        int z = blockPos.getZ();
        List<BlockPos> ignoredCoordinates = new ArrayList<>();
        if(DoubledBlockItemNames.getAsList().contains(itemName.getString())) {
            for(int x2 = x-1; x2 < x+2; x2++) {
                if(x2 == x) { continue; }
                BlockPos blockPos2 = new BlockPos(x2, y, z);
                if(itemName.equals(getItemNameAt(blockPos2))) {
                    if(!existingIgnoredCoordinates.contains(blockPos2)) {
                        ignoredCoordinates.add(blockPos2);
                    }
                    break;
                }
            }

            for(int y2 = y-1; y2 < y+2; y2++) {
                if(y2 == y) { continue; }
                BlockPos blockPos2 = new BlockPos(x, y2, z);
                if(itemName.equals(getItemNameAt(blockPos2))) {
                    if(!existingIgnoredCoordinates.contains(blockPos2)) {
                        ignoredCoordinates.add(blockPos2);
                    }
                    break;
                }
            }

            for(int z2 = z-1; z2 < z+2; z2++) {
                if(z2 == z) { continue; }
                BlockPos blockPos2 = new BlockPos(x, y, z2);
                if(itemName.equals(getItemNameAt(blockPos2))) {
                    if(!existingIgnoredCoordinates.contains(blockPos2)) {
                        ignoredCoordinates.add(blockPos2);
                    }
                    break;
                }
            }
        }

        return ignoredCoordinates;
    }

    private ItemName getItemNameAt(BlockPos blockPos) {
        Block currentBlock = AlgorithmHelper.GetBlockAt(blockPos);
        ItemName itemName = AlgorithmHelper.GetItemNameFromBlock(currentBlock);

        if(AlgorithmHelper.IsAir(itemName)) {
            return AlgorithmHelper.GetItemNameFromAir(blockPos);
        }

        return itemName;
    }

    public Map<String, Integer> CountBlocks(BlockPos firstPosition, BlockPos secondPosition, ItemInput itemInput)
    {
        String item = "";
        if(itemInput != null)
        {
            item = itemInput.getItem().getRegistryName().toString();
        }
        return GetAmountOfBlocks(firstPosition, secondPosition, item);
    }
}
