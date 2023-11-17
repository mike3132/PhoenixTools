package net.resolutemc.phoenixtools.ChatManager;

import net.resolutemc.phoenixtools.PhoenixTools;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;

public class ChatMessage {

    /**
     *
     * @param player The player who is sent the chat message
     * @param key The key that is sent from the messages.yml file
     */
    public static void sendPlayerMessage(Player player, String key) {
        File messagesConfig = new File(PhoenixTools.getInstance().getDataFolder(), "messages.yml");
        YamlConfiguration configMessages = YamlConfiguration.loadConfiguration(messagesConfig);
        String message = configMessages.getString("Messages.Prefix") + configMessages.getString("Messages." + key);
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }

    /**
     *
     * @param sender The command sender that is sent the chat message
     * @param key The key that is sent from the messages.yml file
     */
    public static void sendMessageNoPrefix(CommandSender sender, String key) {
        File messagesConfig = new File(PhoenixTools.getInstance().getDataFolder(), "messages.yml");
        YamlConfiguration configMessages = YamlConfiguration.loadConfiguration(messagesConfig);
        String message = configMessages.getString("Messages." + key, "message");
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }

    /**
     *
     * @param sender The command sender that is sent the chat message
     * @param key The key that is sent from the messages.yml file
     */
    public static void sendConsoleMessage(CommandSender sender, String key) {
        File messagesConfig = new File(PhoenixTools.getInstance().getDataFolder(), "messages.yml");
        YamlConfiguration configMessages = YamlConfiguration.loadConfiguration(messagesConfig);
        String message = configMessages.getString("Messages.Prefix") + configMessages.getString("Messages." + key);
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }
}
