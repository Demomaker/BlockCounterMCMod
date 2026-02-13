package net.demomaker.blockcounter.util;

import net.demomaker.blockcounter.common.ItemName;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;

public class AlgorithmHelper {
    private static ServerLevel serverWorld;
    private static int total;
    public static void SetServerWorld(ServerLevel serverWorld) {
        AlgorithmHelper.serverWorld = serverWorld;
    }

    public static void SetTotal(int total) {
        AlgorithmHelper.total = total;
    }

    public static int GetTotal() {
        return total;
    }

    public static Block GetBlockAt(BlockPos blockPos) {
        return serverWorld.getBlockState(blockPos).getBlock();
    };

    public static ItemName GetItemNameFromBlock(Block block) {
        return GetItemNameFromBlockName(block.getDescriptionId());
    }

    public static ItemName GetItemNameFromAir(BlockPos blockPos) {
        ItemName itemName = GetItemNameFromBlock(GetBlockAt(blockPos));
        String airName = getAirName();
        if(itemName.getString().equals(airName)) {
            Fluid fluid = serverWorld.getBlockState(blockPos).getFluidState().getType();
            itemName = getFluidName(fluid);
        }

        if(itemName.getString().equals(getFluidName(Fluids.EMPTY).getString())) {
            itemName = new ItemName(airName);
        }
        return itemName;
    }

    public static boolean IsAir(ItemName itemName) {
        return itemName.getString().equals(getAirName());
    }

    public static ItemName getFluidName(Fluid fluid) {
        ResourceLocation fluidOriginalKey = Registry.FLUID.getKey(fluid);
        String fluidKey = "fluid." + fluidOriginalKey.getNamespace() + "." + fluidOriginalKey.getPath();
        String fluidName = new TranslatableComponent(fluidKey).getString();
        return new ItemName(fluidName);
    }

    private static String getAirName() {
        TranslatableComponent airText = new TranslatableComponent(Blocks.AIR.getDescriptionId());
        return airText.getString();
    }

    public static ItemName GetItemNameFromBlockName(String blockName) {
        TranslatableComponent text = new TranslatableComponent(blockName);
        return new ItemName(text.getString());
    }
}
