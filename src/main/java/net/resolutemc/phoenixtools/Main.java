package net.resolutemc.phoenixtools;

import net.resolutemc.phoenixtools.CommandManager.TabComplete;
import net.resolutemc.phoenixtools.CommandManager.ToolCommand;
import net.resolutemc.phoenixtools.ConigManager.ConfigCreator;
import net.resolutemc.phoenixtools.EventManager.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    public static Main plugin;

    /**
     *
     * @param chatColor Uses & to implement color codes
     * @return Returns the craftBukkit translateAlternateColorCodes
     */
    public static String chatColor(String chatColor) {
        return ChatColor.translateAlternateColorCodes('&', chatColor);
    }

    @Override
    public void onEnable() {
        plugin = this;
        // Plugin startup logic
        getServer().getConsoleSender().sendMessage(chatColor("&bPhoenix &6Tools &7> &2Enabled"));

        // Event loader
        Bukkit.getPluginManager().registerEvents(new MultiBlockPickaxeEvent(), this);
        Bukkit.getPluginManager().registerEvents(new MoltenPickaxeEvent(), this);
        Bukkit.getPluginManager().registerEvents(new SpawnerPickaxeEvent(), this);
        Bukkit.getPluginManager().registerEvents(new CobblePickaxeEvent(), this);
        Bukkit.getPluginManager().registerEvents(new HarvesterHoeEvent(), this);

        // Command loader
        registerToolCommand();
        registerTabComplete();

        // Config loader
        saveDefaultConfig();
        getConfig();
        ConfigCreator.MESSAGES.create();
    }


    @Override
    public void onDisable() {
        // Plugin shutdown
        getServer().getConsoleSender().sendMessage(chatColor("&bPhoenix &6Tools &7> &4Disabled"));
    }

    /**
     * Registers the tool command
     */
    public void registerToolCommand() {
        new ToolCommand();
    }

    /**
     * Registers the tab completer
     */
    public void registerTabComplete() {
        new TabComplete();
    }
}
