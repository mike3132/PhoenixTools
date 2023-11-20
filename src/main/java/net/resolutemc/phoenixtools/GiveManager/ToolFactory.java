package net.resolutemc.phoenixtools.GiveManager;

import net.resolutemc.phoenixtools.ItemManager.*;
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
            case "HARVESTERHOE":
                return HarvesterHoe.getHarvesterHoe();
            case "GLASSBREAKER":
                return GlassBreaker.getGlassBreaker();
            case "ITEMMAGNET":
                return ItemMagnet.getItemMagnet();
        }
        return null;
    }
}
