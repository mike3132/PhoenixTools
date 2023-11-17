package net.resolutemc.phoenixtools.ItemManager;

import net.resolutemc.phoenixtools.ChatManager.ColorManager;
import net.resolutemc.phoenixtools.PhoenixTools;
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

        for (String realLore : PhoenixTools.getInstance().getConfig().getStringList("SpawnerPickaxe-Lore")) {
            lore.add(ColorManager.chatColor("" + realLore));
        }
        meta.setDisplayName(ColorManager.chatColor("" + PhoenixTools.getInstance().getConfig().getString("SpawnerPickaxe-Name")));
        meta.setLore(lore);
        NamespacedKey key = new NamespacedKey(PhoenixTools.getInstance(), "SpawnerPickaxe-Key");
        meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, "SpawnerPickaxe");
        meta.addEnchant(Enchantment.SILK_TOUCH, 1, true);
        meta.setCustomModelData(PhoenixTools.getInstance().getConfig().getInt("SpawnerPickaxe-Model-Data"));
        item.setItemMeta(meta);
        return item;
    }
}
