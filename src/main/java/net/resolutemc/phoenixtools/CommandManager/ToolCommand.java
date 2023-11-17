package net.resolutemc.phoenixtools.CommandManager;

import net.resolutemc.phoenixtools.ChatManager.ChatMessage;
import net.resolutemc.phoenixtools.ChatManager.ColorManager;
import net.resolutemc.phoenixtools.GiveManager.ToolFactory;
import net.resolutemc.phoenixtools.PhoenixTools;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ToolCommand implements CommandExecutor {

    public ToolCommand() {
        PhoenixTools.getInstance().getCommand("Tools").setExecutor(this);
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 0) {
            ChatMessage.sendConsoleMessage(sender, "Not-enough-args-placeholder");
            return false;
        }
        if (args[0].equalsIgnoreCase("Reload")) {
            if (!sender.hasPermission("ragnarokTools.Reload")) {
                ChatMessage.sendConsoleMessage(sender, "No-permissions-placeholder");
                return false;
            }
            sender.sendMessage(ColorManager.chatColor("&4Phoenix &6Tools &7> &aPlugin config reloaded in &2" + String.valueOf(System.currentTimeMillis() - 1) + " &ams"));
            PhoenixTools.getInstance().reloadConfig();
            return false;
        }

        if (args[0].equalsIgnoreCase("List")) {
            if (!sender.hasPermission("ragnarokTools.List")) {
                ChatMessage.sendConsoleMessage(sender, "No-permissions-placeholder");
                return false;
            }
            ChatMessage.sendMessageNoPrefix(sender, "Tools-list-placeholder-header");
            ChatMessage.sendMessageNoPrefix(sender, "Tools-list-A");
            ChatMessage.sendMessageNoPrefix(sender, "Tools-list-B");
            ChatMessage.sendMessageNoPrefix(sender, "Tools-list-C");
            ChatMessage.sendMessageNoPrefix(sender, "Tools-list-D");
            ChatMessage.sendMessageNoPrefix(sender, "Tools-list-placeholder-footer");
            return false;
        }
        if (!sender.hasPermission("ragnarokTools.Give")) {
            ChatMessage.sendConsoleMessage(sender, "No-permissions-placeholder");
            return false;
        }
        if (!args[0].equalsIgnoreCase("Give")) {
            ChatMessage.sendConsoleMessage(sender, "Not-give-placeholder");
            return false;
        }
        if (args.length < 2) {
            ChatMessage.sendConsoleMessage(sender, "Not-player-placeholder");
            return false;
        }
        Player target = Bukkit.getPlayer(args[1]);
        if (target == null) {
            ChatMessage.sendConsoleMessage(sender, "Player-not-found-placeholder");
            return false;
        }
        if (args.length < 3) {
            ChatMessage.sendConsoleMessage(sender, "Not-tool-placeholder");
            return false;
        }
        ItemStack item = ToolFactory.getItem(args[2]);
        if (item == null) {
            ChatMessage.sendConsoleMessage(sender, "Tool-not-found-placeholder");
            return false;
        }
        int amount = 1;
        if (args.length >= 4) {
            amount = Integer.parseInt(args[3]);
        }
        for (int i = 0; i < amount; i++) {
            if (target.getInventory().firstEmpty() == -1) {
                ChatMessage.sendPlayerMessage(target, "Player-inventory-full-placeholder");
                target.getLocation().getWorld().dropItem(target.getLocation(), item);
                return false;
            }
            ChatMessage.sendPlayerMessage(target, "Player-give-tool-placeholder");
            target.getInventory().addItem(item);
        }

        return false;
    }
}
