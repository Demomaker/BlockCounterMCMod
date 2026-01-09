package net.demomaker.blockcounter.common;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DoubledBlockItemNames {
    private static final String[] doubledBlockItemNames = {
            "Birch Door",
            "Oak Door",
            "Spruce Door",
            "Jungle Door",
            "Acacia Door",
            "Dark Oak Door",
            "Crimson Door",
            "Warped Door",
            "Mangrove Door",
            "Bamboo Door",
            "Cherry Door",
            "Sunflower",
            "Lilac",
            "Rose Bush",
            "Peony",
            "Tall Grass",
            "Large Fern",
            "White Bed",
            "Orange Bed",
            "Magenta Bed",
            "Light Blue Bed",
            "Yellow Bed",
            "Lime Bed",
            "Pink Bed",
            "Gray Bed",
            "Light Gray Bed",
            "Cyan Bed",
            "Purple Bed",
            "Blue Bed",
            "Brown Bed",
            "Green Bed",
            "Red Bed",
            "Black Bed",
            "End Portal Frame",
            "Big Dripleaf"
    };

    public static List<String> getAsList() {
        return new ArrayList<>(Arrays.asList(doubledBlockItemNames));
    }
}

