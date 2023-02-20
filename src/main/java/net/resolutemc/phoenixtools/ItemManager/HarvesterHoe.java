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

public class HarvesterHoe {

    public static ItemStack getHarvesterHoe() {
        ItemStack item = new ItemStack(Material.DIAMOND_HOE, 1);
        ItemMeta meta = item.getItemMeta();
        List<String> lore = new ArrayList<>();

        for (String relLore : Main.plugin.getConfig().getStringList("HarvesterHoe-Lore")) {
            lore.add(Main.chatColor("" + relLore));
        }
        meta.setDisplayName(Main.chatColor("" + Main.plugin.getConfig().getString("HarvesterHoe-Name")));
        meta.setLore(lore);
        NamespacedKey key = new NamespacedKey(Main.plugin, "HarvesterHoe-Key");
        meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, "HarvesterHoe");
        meta.addEnchant(Enchantment.SILK_TOUCH, 1, true);
        item.setItemMeta(meta);
        return item;
    }

}
