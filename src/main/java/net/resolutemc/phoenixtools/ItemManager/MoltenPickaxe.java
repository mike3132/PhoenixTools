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

public class MoltenPickaxe {

    /**
     *
     * @return This returns the ItemStack of the Molten Pickaxe
     */
    public static ItemStack getMoltenPickaxe() {
        ItemStack item = new ItemStack(Material.IRON_PICKAXE, 1);
        ItemMeta meta = item.getItemMeta();
        List<String> lore = new ArrayList<>();

        for (String realLore : Main.plugin.getConfig().getStringList("MoltenPickaxe-Lore")) {
            lore.add(Main.chatColor("" + realLore));
        }
        meta.setDisplayName(Main.chatColor("" + Main.plugin.getConfig().getString("MoltenPickaxe-Name")));
        meta.setLore(lore);
        NamespacedKey key = new NamespacedKey(Main.plugin, "Molten-Pickaxe-Key");
        meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, "Molten-Pickaxe");
        meta.addEnchant(Enchantment.DIG_SPEED, 5, true);
        item.setItemMeta(meta);
        return item;
    }
}
