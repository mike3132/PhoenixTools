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

public class CobblePickaxe {

    /**
     *
     * @return This returns the itemStack of the Cobble Pickaxe
     */
    public static ItemStack getCobblePickaxe() {
        ItemStack item = new ItemStack(Material.DIAMOND_PICKAXE, 1);
        ItemMeta meta = item.getItemMeta();
        List<String> lore = new ArrayList<>();

        for (String relLore : PhoenixTools.getInstance().getConfig().getStringList("CobblePickaxe-Lore")) {
            lore.add(ColorManager.chatColor("" + relLore));
        }
        meta.setDisplayName(ColorManager.chatColor("" + PhoenixTools.getInstance().getConfig().getString("CobblePickaxe-Name")));
        meta.setLore(lore);
        NamespacedKey key = new NamespacedKey(PhoenixTools.getInstance(), "Cobble-Pickaxe-Key");
        meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, "CobblePickaxe-Key");
        meta.addEnchant(Enchantment.DIG_SPEED, 10, true);
        meta.setCustomModelData(PhoenixTools.getInstance().getConfig().getInt("CobblePickaxe-Model-Data"));
        item.setItemMeta(meta);
        return item;
    }
}
