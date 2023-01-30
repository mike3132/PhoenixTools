package net.resolutemc.phoenixtools.ConigManager;

import net.resolutemc.phoenixtools.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

public enum ConfigCreator {

    MESSAGES;

    /**
     *
     * @return Gets the plugins data folder and returns the corresponding .yml file
     */
    public File getFile() {
        return new File(Main.plugin.getDataFolder(), this.toString().toLowerCase(Locale.ROOT) + ".yml");
    }

    /**
     *
     * @return Loads the YamlConfiguration of the file
     */
    public FileConfiguration get() {
        return YamlConfiguration.loadConfiguration(getFile());
    }

    /**
     *
     * @param configuration Saves the file to the plugins data folder
     */
    public void save(FileConfiguration configuration) {
        try {
            configuration.save(getFile());
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates the file.
     */
    public void create() {
        Main.plugin.saveResource(this.toString().toLowerCase(Locale.ROOT) + ".yml", false);
    }
}
