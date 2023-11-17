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

public class HarvesterHoe {

    /**
     *
     * @return This returns the itemStack of the Harvester Hoe
     */
    public static ItemStack getHarvesterHoe() {
        ItemStack item = new ItemStack(Material.DIAMOND_HOE, 1);
        ItemMeta meta = item.getItemMeta();
        List<String> lore = new ArrayList<>();

        for (String relLore : PhoenixTools.getInstance().getConfig().getStringList("HarvesterHoe-Lore")) {
            lore.add(ColorManager.chatColor("" + relLore));
        }
        meta.setDisplayName(ColorManager.chatColor("" + PhoenixTools.getInstance().getConfig().getString("HarvesterHoe-Name")));
        meta.setLore(lore);
        NamespacedKey key = new NamespacedKey(PhoenixTools.getInstance(), "HarvesterHoe-Key");
        meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, "HarvesterHoe");
        meta.addEnchant(Enchantment.SILK_TOUCH, 1, true);
        meta.setCustomModelData(PhoenixTools.getInstance().getConfig().getInt("HarvesterHoe-Model-Data"));
        item.setItemMeta(meta);
        return item;
    }

}
