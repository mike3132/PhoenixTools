package net.resolutemc.phoenixtools.ChatManager;

import org.bukkit.ChatColor;

public class ColorManager {

    /**
     *
     * @param chatColor Uses & to implement color codes
     * @return Returns the craftBukkit translateAlternateColorCodes
     */
    public static String chatColor(String chatColor) {
        return ChatColor.translateAlternateColorCodes('&', chatColor);
    }
}
