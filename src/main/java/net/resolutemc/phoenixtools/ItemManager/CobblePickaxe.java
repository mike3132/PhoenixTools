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

public class CobblePickaxe {

    public static ItemStack getCobblePickaxe() {
        ItemStack item = new ItemStack(Material.DIAMOND_PICKAXE, 1);
        ItemMeta meta = item.getItemMeta();
        List<String> lore = new ArrayList<>();

        for (String relLore : Main.plugin.getConfig().getStringList("CobblePickaxe-Lore")) {
            lore.add(Main.chatColor("" + relLore));
        }
        meta.setDisplayName(Main.chatColor("" + Main.plugin.getConfig().getString("CobblePickaxe-Name")));
        meta.setLore(lore);
        NamespacedKey key = new NamespacedKey(Main.plugin, "Cobble-Pickaxe-Key");
        meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, "Cobble-Pickaxe");
        meta.addEnchant(Enchantment.DIG_SPEED, 10, true);
        item.setItemMeta(meta);
        return item;
    }
}
