package net.demomaker.blockcounter.common;

public class ItemName {

    private final String name;

    public ItemName(String name) {
        this.name = toModItemName(name);
    }

    public String getString() {
        return name;
    }

    public boolean equals(String other) {
        return this.name.equals(other);
    }

    public boolean equals(ItemName other) {
        return this.name.equals(other.name);
    }

    private static String toModItemName(String name) {
        if(name.matches("^[a-z0-9_]+:[a-z0-9_]+$")) {
            String newName = name.replaceFirst("^[a-z0-9_]+:", "");
            String[] subParts = newName.split("_");
            StringBuilder stringBuilder = new StringBuilder();
            String lastSubPart = subParts[subParts.length - 1];
            for (String subPart : subParts) {
                if(!subPart.isEmpty()) {
                    String upperCase = subPart.substring(0, 1).toUpperCase();
                    String lowerCase = subPart.substring(1);
                    stringBuilder.append(upperCase).append(lowerCase);
                    if(!subPart.equals(lastSubPart)) {
                        stringBuilder.append(" ");
                    }
                }
                else {
                    stringBuilder.append(subPart);
                }
            }
            return stringBuilder.toString();
        }
        return name;
    }
}
