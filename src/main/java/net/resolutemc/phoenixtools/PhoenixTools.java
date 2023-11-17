package net.resolutemc.phoenixtools;

import net.resolutemc.phoenixtools.ChatManager.ColorManager;
import net.resolutemc.phoenixtools.CommandManager.TabComplete;
import net.resolutemc.phoenixtools.CommandManager.ToolCommand;
import net.resolutemc.phoenixtools.ConigManager.ConfigCreator;
import net.resolutemc.phoenixtools.EventManager.*;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class PhoenixTools extends JavaPlugin {

    private static PhoenixTools INSTANCE;


    @Override
    public void onEnable() {
        INSTANCE = this;
        // Plugin startup logic
        getServer().getConsoleSender().sendMessage(ColorManager.chatColor("&bPhoenix &6Tools &7> &2Enabled"));

        // Event loader
        Bukkit.getPluginManager().registerEvents(new MultiBlockPickaxeEvent(), this);
        Bukkit.getPluginManager().registerEvents(new MoltenPickaxeEvent(), this);
        Bukkit.getPluginManager().registerEvents(new SpawnerPickaxeEvent(), this);
        Bukkit.getPluginManager().registerEvents(new CobblePickaxeEvent(), this);
        Bukkit.getPluginManager().registerEvents(new HarvesterHoeEvent(), this);
        Bukkit.getPluginManager().registerEvents(new GlassBreakerEvent(), this);

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
        getServer().getConsoleSender().sendMessage(ColorManager.chatColor("&bPhoenix &6Tools &7> &4Disabled"));
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

    /**
     * Instance register
     */
    public static PhoenixTools getInstance() {
        return INSTANCE;
    }
}
