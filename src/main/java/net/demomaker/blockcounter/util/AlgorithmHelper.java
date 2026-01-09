package net.demomaker.blockcounter.util;

import net.demomaker.blockcounter.common.ItemName;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.server.ServerWorld;

public class AlgorithmHelper {
    private static ServerWorld serverWorld;
    private static int total;
    public static void SetServerWorld(ServerWorld serverWorld) {
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
        String fluidName = new TranslationTextComponent(fluidKey).getString();
        return new ItemName(fluidName);
    }

    private static String getAirName() {
        TranslationTextComponent airText = new TranslationTextComponent(Blocks.AIR.getDescriptionId());
        return airText.getString();
    }

    public static ItemName GetItemNameFromBlockName(String blockName) {
        TranslationTextComponent text = new TranslationTextComponent(blockName);
        return new ItemName(text.getString());
    }
}
