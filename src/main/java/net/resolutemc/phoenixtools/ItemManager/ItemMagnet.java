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

public class ItemMagnet {


    /**
     *
     * @return This returns the itemStack of the Cobble Pickaxe
     */
    public static ItemStack getItemMagnet() {
        ItemStack item = new ItemStack(Material.DIAMOND, 1);
        ItemMeta meta = item.getItemMeta();
        List<String> lore = new ArrayList<>();

        for (String relLore : PhoenixTools.getInstance().getConfig().getStringList("ItemMagnet-Lore")) {
            lore.add(ColorManager.chatColor("" + relLore));
        }
        meta.setDisplayName(ColorManager.chatColor("" + PhoenixTools.getInstance().getConfig().getString("ItemMagnet-Name")));
        meta.setLore(lore);
        NamespacedKey key = new NamespacedKey(PhoenixTools.getInstance(), "ItemMagnet-Key");
        meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, "ItemMagnet-Key-Item");
        meta.setCustomModelData(PhoenixTools.getInstance().getConfig().getInt("ItemMagnet-Model-Data"));
        item.setItemMeta(meta);
        return item;
    }
}
