package net.resolutemc.phoenixtools.GiveManager;

import net.resolutemc.phoenixtools.ItemManager.CobblePickaxe;
import net.resolutemc.phoenixtools.ItemManager.MoltenPickaxe;
import net.resolutemc.phoenixtools.ItemManager.MultiBlockPickaxe;
import net.resolutemc.phoenixtools.ItemManager.SpawnerPickaxe;
import org.bukkit.inventory.ItemStack;

import java.util.Locale;

public class ToolFactory {

    /**
     *
     * @param string The name of the tool that is being given.
     * @return Returns the ItemStack of the tool.
     */
    public static ItemStack getItem(String string) {
        switch (string.toUpperCase(Locale.ROOT)) {
            case "SPAWNERPICKAXE":
                return SpawnerPickaxe.getSpawnerPickaxe();
            case "MULTIBLOCKPICKAXE":
                return MultiBlockPickaxe.getMbTool();
            case "MOLTENPICKAXE":
                return MoltenPickaxe.getMoltenPickaxe();
            case  "COBBLEPICKAXE":
                return CobblePickaxe.getCobblePickaxe();
        }
        return null;
    }
}
