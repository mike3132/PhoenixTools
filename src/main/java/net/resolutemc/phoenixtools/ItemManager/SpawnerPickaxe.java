package net.resolutemc.phoenixtools.ItemManager;

import net.resolutemc.phoenixtools.Main;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class SpawnerPickaxe {

    /**
     *
     * @return This returns the ItemStack of the Spawner Pickaxe
     */
    public static ItemStack getSpawnerPickaxe() {
        ItemStack item = new ItemStack(Material.GOLDEN_PICKAXE, 1);
        ItemMeta meta = item.getItemMeta();
        List<String> lore = new ArrayList<>();

        for (String realLore : Main.plugin.getConfig().getStringList("SpawnerPickaxe-Lore")) {
            lore.add(Main.chatColor("" + realLore));
        }
        meta.setDisplayName(Main.chatColor("" + Main.plugin.getConfig().getString("SpawnerPickaxe-Name")));
        meta.setLore(lore);
        NamespacedKey key = new NamespacedKey(Main.plugin, "Spawner-Pickaxe-Key");
        meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, "Spawner-Pickaxe");
        meta.addEnchant(Enchantment.SILK_TOUCH, 1, true);
        item.setItemMeta(meta);
        return item;
    }
}
